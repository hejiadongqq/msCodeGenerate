package com.datingpass.utils.services;

import com.datingpaas.common.annotation.SwaggerIgnoreProperty;
import com.datingpass.utils.config.*;
import com.datingpass.utils.utils.ClassUtils;
import com.datingpass.utils.utils.Utils;
import com.datingpass.utils.vo.Field;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.oracle.tools.packager.Log;
import com.service.commons.model.enums.Deleted;
import freemarker.template.TemplateException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author: Albert
 * @date: 2021-09-10 3:51 PM
 * @desc:
 */
@Slf4j
public abstract class BaseServices {

    @Autowired
    protected EntityConfig entityConfig;
    @Autowired
    protected Config config;
    @Autowired
    protected TemplateServices templateServices;
    @Autowired
    protected ControllerConfig controllerConfig;

    /**
     * 生成的dto类型
     */
    final static List<String> DTOList =
            Lists.newArrayList("Persist", "Search", "Update");

    @Data
    static abstract class EntityRequest {
        /**
         * entity的类名
         */
        @ApiModelProperty("entity名,类名 区分大小写")
        @NotBlank(message = "entityName不能为空!")
        private String entityName;

        @ApiModelProperty(value = "类名的开头部分，默认为空", hidden = true)
        @SwaggerIgnoreProperty
        private String classNameBefore = "";

    }

    final static List<String> StringFiledList =
            Lists.newArrayList("appId", "groupId", "id");

    /**
     * 读取类字段
     *
     * @param entity
     * @return
     */
    Collection<Field> getFields(Class entity) {
        Collection<Field> fields = ClassUtils.getFields(entity);
        fields.forEach(x -> {
            String type = x.getType().trim();
            if ("java.lang.Long".equalsIgnoreCase(type) || "Long".equalsIgnoreCase(type)) {
                x.setType("String");
            }
        });

        fields.forEach(x -> {
            String name = x.getName();
            // 将特定字段的数值型设定为为字符串
            if (StringFiledList.contains(name)) {
                x.setType("String");
            }
        });
        return fields;
    }

    /**
     * 生成模版的变量
     *
     * @param request
     * @return
     */
    Map<String, Object> makeTemplateValue(EntityRequest request) {
        Map<String, Object> templateValue = Maps.newHashMap();
        templateValue.put("dateTime", LocalDateTime.now());
        templateValue.put("entityName", request.getEntityName());
        // 类名前缀
        templateValue.put("before", request.getClassNameBefore());
        templateValue.put("_config", config);
        return templateValue;
    }

    Map<String, Object> makeDTOTemplateValue(EntityRequest request, String templateName, String packageName) {
        Map<String, Object> templateValue = makeTemplateValue(request);
        templateValue.put("packageName", packageName);
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

    /**
     * 备份文件
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    File backupFile(String fileName) throws Exception {
        String backupName = fileName + "." + LocalDateTime.now() + ".bak";
        return Utils.backupFile(fileName, backupName);
    }

    /**
     * 生成dto
     *
     * @param request
     * @param fields
     * @param temples
     * @param moduleConfig
     * @throws Exception
     */
    void makeDTO(EntityRequest request, Collection<Field> fields, Collection<String> temples
            , ModuleConfig moduleConfig) throws Exception {
        for (String templateName : temples) {
            Map<String, Object> templateValue = makeDTOTemplateValue(
                    request, templateName, moduleConfig.getDtoTemplatePackageName());
            templateValue.put("fields", fields);
            String className = request.getClassNameBefore() + request.getEntityName() + templateName + "DTO";
            templateValue.put("className", className);

            if (templateName.equals("Search")) {
                // 手动增加groupId和appId
                fields.add(Field.builder()
                        .isEnum(false)
                        .isDate(false)
                        .isDateTime(false)
                        .desc("appId")
                        .type("String")
                        .name("appId")
                        .build());
                fields.add(Field.builder()
                        .isEnum(false)
                        .isDate(false)
                        .isDateTime(false)
                        .desc("groupId")
                        .type("String")
                        .name("groupId")
                        .build());
                fields.add(Field.builder()
                        .isEnum(true)
                        .isDate(false)
                        .isDateTime(false)
                        .desc("删除标记YES，NO")
                        .type(Deleted.class.getName())
                        .name("deleted")
                        .build());
            }
            try {
                // 生成文件
                String fileName = moduleConfig.getDtoDirectoryPath() + "/" + className + ".java";
                File file = backupFile(fileName);
                templateServices.makeFile(moduleConfig.getDtoTemplateFileName(), templateValue, file);
            } catch (IOException | TemplateException e) {
                throw new Exception("生成模版 Exception", e);
            }
        }
    }

    /**
     * @param request
     * @param fields
     * @param moduleConfig
     * @throws Exception
     */
    void makeVO(EntityRequest request, Collection<Field> fields, ModuleConfig moduleConfig) throws Exception {
        Map<String, Object> templateValue = makeTemplateValue(request);
        String className = request.getClassNameBefore() + request.getEntityName() + "VO";
        templateValue.put("packageName", moduleConfig.getVoTemplatePackageName());
        templateValue.put("className", className);
        templateValue.put("fields", fields);
        templateValue.put("extends", "extends BaseVO");

        // 生成文件
        String fileName = moduleConfig.getVoDirectoryPath() + "/" + className + ".java";

        File file = backupFile(fileName);
        templateServices.makeFile(moduleConfig.getVoTemplateFileName(), templateValue, file);
    }

    /**
     * @param request
     * @param moduleConfig
     * @throws Exception
     */
    void makeConverter(EntityRequest request, ModuleConfig moduleConfig) throws Exception {
        Map<String, Object> templateValue = makeTemplateValue(request);
        String className = request.getClassNameBefore() + request.getEntityName() + "Converter";
        templateValue.put("className", className);
        templateValue.put("packageName", moduleConfig.getConverterTemplatePackageName());

        // 生成文件
        String fileName = moduleConfig.getConverterDirectoryPath() + "/" + className + ".java";

        File file = backupFile(fileName);
        templateServices.makeFile(moduleConfig.getConverterTemplateFileName(), templateValue, file);
    }

    Class checkClass(EntityRequest request) throws Exception {
        String err = "";
        Class entity = ClassUtils.getClass(entityConfig.getEntityPackage(), request.getEntityName());
        if (Objects.isNull(entity)) {
            throw new RuntimeException(request.getEntityName() + " 类不存在！");
//            log.info("类不存在！开始动态加载...");
//            String classPath = entityConfig.getEntityDirectoryPath() + "/" + request.getEntityName()+".java";
//            return null;
//            String jarPath = entityConfig.getEntityJarPath();
//            Class aClass = ClassUtils.getClass(entityConfig.getEntityPackage(), request.getEntityName());
//            if (Objects.isNull(aClass)) {
//                throw new RuntimeException(request.getEntityName() + " 类不存在！");
//            }
//            return aClass;
        }
        return entity;
    }



    void makeStrategy(ProjectBffServices.MakeBffRequest request, BffConfig moduleConfig) throws Exception {
        if (StringUtils.isAnyBlank(moduleConfig.getStrategyDirectoryPath(),
                moduleConfig.getStrategyTemplatePackageName())) {
            throw new RuntimeException("验单工程路径没有配置！");
        }
        String projectName = StringUtils.capitalize(request.getName());
        Map<String, Object> templateValue = Maps.newHashMap();
        templateValue.put("dateTime", LocalDateTime.now());
        templateValue.put("projectName", projectName);
        // 组件名
        templateValue.put("componentName", StringUtils.uncapitalize(projectName));

        // 类名前缀
        templateValue.put("before", "");

        String className = projectName + "Strategy";
        templateValue.put("className", className);
        templateValue.put("packageName", moduleConfig.getStrategyTemplatePackageName());

        // 生成文件
        String fileName = moduleConfig.getStrategyDirectoryPath() + "/" + className + ".java";

        File file = backupFile(fileName);
        templateServices.makeFile(moduleConfig.getStrategyTemplateFileName(), templateValue, file);
    }

}
