package com.datingpass.utils.services;

import com.datingpass.utils.config.Config;
import com.datingpass.utils.config.EntityConfig;
import com.datingpass.utils.utils.ClassUtils;
import com.datingpass.utils.utils.Utils;
import com.datingpass.utils.vo.Field;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.service.commons.model.enums.Deleted;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author: Albert
 * @date: 2021-08-16 10:34 AM
 * @desc:
 */
@Service
@Slf4j
public class EntityServicesImpl implements EntityServices {
    @Autowired
    private EntityConfig entityConfig;
    @Autowired
    private Config config;
    @Autowired
    private TemplateServices templateServices;

    /**
     * 生成的dto类型
     */
    public static List<String> DTOList =
            Lists.newArrayList("Persist", "Search", "Update");

    public static List<String> StringFiledList =
            Lists.newArrayList("appId", "groupId", "id");


    @Override
    public void makeEntity(MakeEntityRequest request) throws Exception {
        // 检测类是否存在
        Class entity = ClassUtils.getClass(entityConfig.getEntityPackage(), request.getEntityName());
        if (Objects.isNull(entity)) {
            throw new RuntimeException(request.getEntityName() + " 类不存在！");
        }

        // 获取字段列表
        Collection<Field> fields = getFields(entity);

        // 生成dto相关的类
        makeDTO(request, fields);
        // 生成vo相关的类
        makeVO(request, fields);
        // 生成converter相关的类
        makeConverter(request);
        // 生成dao相关的类
        makeDAO(request);
        // 生成service相关的类
        makeService(request);


    }

    private void makeVO(MakeEntityRequest request, Collection<Field> fields) throws Exception {
        Map<String, Object> templateValue = makeTemplateValue(request);
        String className = request.getEntityName() + "VO";
        templateValue.put("packageName", entityConfig.getVoTemplatePackageName());
        templateValue.put("className", className);
        templateValue.put("fields", fields);
        templateValue.put("extends", "extends BaseVO");

        // 生成文件
        String fileName = entityConfig.getVoDirectoryPath() + "/" + className + ".java";

        File file = backupFile(fileName);
        templateServices.makeFile(entityConfig.getVoTemplateFileName(), templateValue, file);
    }

    private void makeConverter(MakeEntityRequest request) throws Exception {
        Map<String, Object> templateValue = makeTemplateValue(request);
        String className = request.getEntityName() + "Converter";
        templateValue.put("className", className);
        templateValue.put("packageName", entityConfig.getConverterTemplatePackageName());

        // 生成文件
        String fileName = entityConfig.getConverterDirectoryPath() + "/" + className + ".java";

        File file = backupFile(fileName);
        templateServices.makeFile(entityConfig.getConverterTemplateFileName(), templateValue, file);
    }

    private void makeDAO(MakeEntityRequest request) throws Exception {
        Map<String, Object> templateValue = makeTemplateValue(request);
        String className = request.getEntityName() + "DAO";
        templateValue.put("className", className);
        templateValue.put("packageName", entityConfig.getDaoTemplatePackageName());
        templateValue.put("dtoName", request.getEntityName() + "SearchDTO");
        templateValue.put("entityName", request.getEntityName());

        // 生成文件
        String fileName = entityConfig.getDaoDirectoryPath() + "/" + className + ".java";

        File file = backupFile(fileName);
        templateServices.makeFile(entityConfig.getDaoTemplateFileName(), templateValue, file);
    }

    private void makeService(MakeEntityRequest request) throws Exception {
        Map<String, Object> templateValue = makeTemplateValue(request);
        String className = request.getEntityName() + "Service";
        templateValue.put("className", className);
        templateValue.put("packageName", entityConfig.getServiceTemplatePackageName());

        templateValue.put("dtoName", request.getEntityName() + "SearchDTO");
        templateValue.put("entityName", request.getEntityName());

        // 生成文件
        String fileName = entityConfig.getServiceDirectoryPath() + "/" + className + ".java";

        File file = backupFile(fileName);
        templateServices.makeFile(entityConfig.getServiceTemplateFileName(), templateValue, file);
    }

    private Map<String, Object> makeTemplateValue(MakeEntityRequest request) {
        Map<String, Object> templateValue = Maps.newHashMap();
        templateValue.put("dateTime", LocalDateTime.now());
        templateValue.put("entityName", request.getEntityName());
        return templateValue;
    }

    /**
     * @param request
     * @param fields
     * @throws Exception
     */
    private void makeDTO(MakeEntityRequest request, Collection<Field> fields) throws Exception {
        makeDTO(request, fields, DTOList);
    }

    private Map<String, Object> makeDTOTemplateValue(MakeEntityRequest request, String templateName) {
        Map<String, Object> templateValue = makeTemplateValue(request);
        templateValue.put("packageName", entityConfig.getDtoTemplatePackageName());
        String extend = null;
        if ("Persist".equals(templateName)) {
            extend = "extends com.datingpaas.common.dto.DefinitionPersistDTO";
        } else if ("Search".equals(templateName)) {
            extend = "extends com.service.commons.model.dto.SearchDTO";
        } else if ("Update".equals(templateName)) {
            extend = "extends com.service.commons.model.dto.IdDTO";
        }
        templateValue.put("extends", extend);

        return templateValue;
    }

    private void makeDTO(MakeEntityRequest request, Collection<Field> fields, Collection<String> temples) throws Exception {
        for (String templateName : temples) {
            Map<String, Object> templateValue = makeDTOTemplateValue(request, templateName);
            templateValue.put("fields", fields);
            String className = request.getEntityName() + templateName + "DTO";
            templateValue.put("className", className);

            if (templateName.equals("Search")) {
                // 手动增加groupId和appId
                fields.add(Field.builder()
                        .isEnum(false)
                        .desc("appId")
                        .type("String")
                        .name("appId")
                        .build());
                fields.add(Field.builder()
                        .isEnum(false)
                        .desc("groupId")
                        .type("String")
                        .name("groupId")
                        .build());
                fields.add(Field.builder()
                        .isEnum(true)
                        .desc("删除标记YES，NO")
                        .type(Deleted.class.getName())
                        .name("deleted")
                        .build());
            }
            try {
                // 生成文件
                String fileName = entityConfig.getDtoDirectoryPath() + "/" + className + ".java";
                File file = backupFile(fileName);
                templateServices.makeFile(entityConfig.getDtoTemplateFileName(), templateValue, file);
            } catch (IOException | TemplateException e) {
                throw new Exception("生成模版 Exception", e);
            }
        }
    }

    private File backupFile(String fileName) throws Exception {
        String backupName = fileName + "." + LocalDateTime.now() + ".bak";
        return Utils.backupFile(fileName, backupName);
    }

    @Override
    public void moveBackup() throws Exception {
        Utils.moveBackupFile(config.getProjectBackupDirectoryPath(),
                config.getProjectDirectoryPath());
    }

    private Collection<Field> getFields(Class entity) {
        Collection<Field> fields = ClassUtils.getFields(entity);
        fields.forEach(x-> {
            String type = x.getType().trim();
            if ("java.lang.Long".equalsIgnoreCase(type) || "Long".equalsIgnoreCase(type)) {
                x.setType("String");
            }
        });

        fields.forEach(x->{
            String name = x.getName();
            // 将特定字段的数值型设定为为字符串
            if (StringFiledList.contains(name)) {
                x.setType("String");
            }
        });
        return fields;
    }

    @Override
    public void updateEntity(MakeEntityRequest request) throws Exception {
        // 检测类是否存在
        Class entity = ClassUtils.getClass(entityConfig.getEntityPackage(), request.getEntityName());
        if (Objects.isNull(entity)) {
            throw new RuntimeException(request.getEntityName() + " 类不存在！");
        }

        // 获取字段列表
        Collection<Field> fields = getFields(entity);
        updateDTO(request, fields, DTOList);

    }

    /**
     * 删除或替换字段
     * @param fields
     * @param fileContent
     * @param isDelete
     * @return
     */
    private static StringBuffer replaceField(Collection<NewOldField> fields, StringBuffer fileContent, boolean isDelete) {
        for (NewOldField field : fields) {
            String pattern = "((\\n)*.*@.*(\\n)*)*  (.)*(\\s)*"
                    + field.oldField.getType()
                    + "\\s+" + field.getOldField().getName()
                    + "\\s*;";
            Pattern p = Pattern.compile(pattern);
            Matcher matcher = p.matcher(fileContent);
            StringBuffer sb = new StringBuffer();
            while (matcher.find()) {
                String v = "";
                if (!isDelete) {
                    v = "\r\n    @ApiModelProperty(value = \""
                            +field.newField.getDesc()+"\")"+"\r\n";
                    v += "    private "+field.newField.getType()
                            +" "+field.oldField.getName()+";\r\n";
                    v = v.replace("\\", "\\\\")
                            .replace("$", "\\$");
                }

                matcher.appendReplacement(sb, v);
            }
            matcher.appendTail(sb);

            fileContent = new StringBuffer(sb);
        }
        return fileContent;
    }

    private void updateDTO(MakeEntityRequest request, Collection<Field> fields, Collection<String> temples) throws Exception {
        for (String templateName : temples) {
            String className = request.getEntityName() + templateName + "DTO";
            String fileName = entityConfig.getDtoDirectoryPath() + "/" + className + ".java";

            Class old = ClassUtils.getClass(entityConfig.getDtoTemplatePackageName(), className);
            if (Objects.isNull(old)) {
                log.error("老类不存在/或有错误，跳过处理!-->{}", fileName);
                continue;
            }

            // 老类的字段
            Collection<Field> oldFields = getFields(old);

            Collection<Field> addFields = Lists.newArrayList();
            Collection<NewOldField> updateFields = Sets.newHashSet();
            Collection<NewOldField> deleteFields = Lists.newArrayList();

            // 保留新增的字段
            if (!CollectionUtils.isEmpty(oldFields)) {
                Map<String, Field> oldMap = oldFields.stream()
                        .collect(Collectors.toMap(x -> x.getName(), y -> y));
                Map<String, Field> newMap = fields.stream()
                        .collect(Collectors.toMap(x -> x.getName(), y -> y));
                fields.forEach(field -> {
                    Field oldField = oldMap.get(field.getName());
                    // 老字段中没有为新增
                    if (Objects.isNull(oldField)) {
                        addFields.add(field);
                        return;
                    }
                    // 有，并且类型不一样，为修改
                    if (!field.getType().equals(oldField.getType())) {
                        NewOldField build = NewOldField.builder()
                                .newField(field)
                                .oldField(oldField)
                                .build();
                        updateFields.add(build);
                        return;
                    }
                });

                // 删除字段为新的字段在老字段中没有
                deleteFields.addAll(oldFields.stream().filter(x -> {
                    Field field = newMap.get(x.getName());
                    if (Objects.isNull(field)) {
                        return true;
                    }
                    return false;
                }).distinct().map(x->{
                    return NewOldField.builder()
                            .oldField(x)
                            .build();
                }).collect(Collectors.toList()));

            } else {
                // 所有都为新增
                addFields.addAll(fields);
            }
            log.info("新增字段{}个,修改字段{}个，删除字段{}个",
                    addFields.size(), updateFields.size(), deleteFields.size());

            // 读取老类文件
            StringBuffer fileContent = new StringBuffer(
                    FileUtils.readFileToString(new File(fileName), "utf-8"));
            // 备份
            File file = backupFile(fileName);

            // 处理删除字段的
            fileContent = replaceField(deleteFields, fileContent, true);

            // 处理修改字段
            fileContent = replaceField(updateFields, fileContent, false);

            //处理新增字段
            for (Field field : addFields) {
                String pattern = ";[\\s]*}";
                Pattern p = Pattern.compile(pattern);
                Matcher matcher = p.matcher(fileContent);
                StringBuffer sb = new StringBuffer();
                while (matcher.find()) {
                    String v = ";\r\n    @ApiModelProperty(value = \""
                            + field.getDesc()
                            + "\")\r\n    private " + field.getType() + " " + field.getName() + ";\r\n}";
                    v = v.replace("\\", "\\\\").replace("$", "\\$");
                    matcher.appendReplacement(sb, v);
                }
                matcher.appendTail(sb);
                fileContent = new StringBuffer(sb);
            }

            try {
                // 生成文件
                FileUtils.writeStringToFile(file, fileContent.toString(), "utf-8");
            } catch (IOException e) {
                throw new Exception("生成模版 Exception", e);
            }
        }
    }


    public static void main(String[] args) throws Exception {
        String filename = "/Users/hejiadong/src/java/maosong/datingpaas/datingpaas-service/src/main/java/com/datingpaas/model/dto/AppEmailTemplatePersistDTO.java";
        StringBuffer fileContent = new StringBuffer(FileUtils.readFileToString(new File(filename), "utf-8"));

//        ClassUtils.complierAndRun("com.datingpaas.model.dto.AppEmailTemplatePersistDTO"
//        , filename);

        Collection<NewOldField> updateFields = Sets.newHashSet();
        Field old = Field.builder()
                .name("type")
                .type("com.datingpaas.model.enums.EmailType")
                .build();
        Field newf = Field.builder()
                .name("type")
                .type("String")
                .desc("邮件类型")
                .build();
        NewOldField build = NewOldField.builder()
                .oldField(old)
                .newField(newf)
                .build();

        updateFields.add(build);

        old = Field.builder()
                .name("test2")
                .type("Integer")
                .desc("测试2")
                .build();
        newf = Field.builder()
                .name("test3")
                .type("Integer")
                .desc("测试3")
                .build();
        build = NewOldField.builder()
                .oldField(old)
                .newField(newf)
                .build();
        updateFields.add(build);

        old = Field.builder()
                .name("subject1")
                .type("java.lang.String")
                .desc("主题1")
                .build();
        newf = Field.builder()
                .name("test3")
                .type("String")
                .desc("主题3")
                .build();
        build = NewOldField.builder()
                .oldField(old)
                .newField(newf)
                .build();
        updateFields.add(build);

//        Collection<Field> addFields = Sets.newHashSet();
//        Field old = Field.builder()
//                .name("type2")
//                .type("com.datingpaas.model.enums.EmailType")
//                .build();
//
//        addFields.add(old);
//        old = Field.builder()
//                .name("type3")
//                .type("com.datingpaas.model.enums.EmailType")
//                .build();
//        addFields.add(old);
        //处理新增字段

        fileContent = replaceField(updateFields, fileContent, true);



        log.info("{}", fileContent);

    }
}
