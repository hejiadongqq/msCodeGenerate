package com.datingpass.utils.services;

import com.datingpass.utils.vo.Field;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collection;
import java.util.Map;

/**
 * @author: Albert
 * @date: 2021-09-09 5:41 PM
 * @desc:
 */
@Service
public class ControllerServicesImpl extends BaseServices implements ControllerServices {



    @Override
    public void makeController(MakeBffRequest request) throws Exception {
        // 检测类是否存在
        Class entity = checkClass(request);

        // 获取字段列表
        Collection<Field> fields = getFields(entity);

        // 生成dto相关的类
        makeDTO(request, fields, DTOList, controllerConfig);

        // 生成vo相关的类
//        makeVO(request, fields, controllerConfig);
//
//        // 生成converter相关的类
//        makeConverter(request, controllerConfig);
////
//        // 生成vo相关的类
        privateMakeController(request);

    }

    void privateMakeController(EntityRequest request) throws Exception {
        Map<String, Object> templateValue = makeTemplateValue(request);
        String className = request.getEntityName() + "Controller";
        templateValue.put("className", className);
        templateValue.put("packageName", controllerConfig.getControllerTemplatePackageName());
        templateValue.put("controllerUrl", controllerConfig.getControllerUrl()+"/"+className.toLowerCase());

        // 生成文件
        String fileName = controllerConfig.getRootDirectoryPath() + "/" + className + ".java";

        File file = backupFile(fileName);
        templateServices.makeFile(controllerConfig.getControllerTemplateFileName(), templateValue, file);
    }


}
