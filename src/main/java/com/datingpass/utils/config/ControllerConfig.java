package com.datingpass.utils.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: Albert
 * @date: 2021-09-09 5:43 PM
 * @desc:
 */
@Component
@Data
public class ControllerConfig implements ModuleConfig {

    @PostConstruct
    private void init() {
        controllerUrl = "/" + config.getVersion();
    }

    @Autowired
    private Config config;

    /**
     * controller模版文件
     */
    private String controllerTemplateFileName = "controller.ftl";

    private String controllerUrl;

    /**
     * converter模版文件
     */
    private String converterTemplateFileName = "bffConverter.ftl";
    /**
     * vo模版文件
     */
    private String voTemplateFileName = "bffVo.ftl";

    /**
     * dao模版文件
     */
    private String dtoTemplateFileName = "bffDto.ftl";

    /**
     * dto包名
     */
    private String dtoTemplatePackageName = "com.datingpaas.dto";
    /**
     * 控制器包名
     */
    private String controllerTemplatePackageName = "com.datingpaas.api";
    /**
     * vo包名
     */
    private String voTemplatePackageName = "com.datingpaas.vo";
    /**
     * converter包名
     */
    private String converterTemplatePackageName = "com.datingpaas.converter";

    /**
     * 控制器目录路径
     */
    @Value("${controller.root.directory}")
    private String rootDirectoryPath;

    /**
     * 控制器dto目录路径
     */
    @Value("${controller.dto.directory}")
    private String dtoDirectoryPath;

    /**
     * 控制器vo目录路径
     */
    @Value("${controller.vo.directory}")
    private String voDirectoryPath;

    /**
     * 控制器converter目录路径
     */
    @Value("${controller.converter.directory}")
    private String converterDirectoryPath;

}
