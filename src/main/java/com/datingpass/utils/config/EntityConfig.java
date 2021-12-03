package com.datingpass.utils.config;

import freemarker.template.Configuration;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author: Albert
 * @date: 2021-08-16 10:37 AM
 * @desc:
 */
@Component
@Data
public class EntityConfig implements ModuleConfig {

    @Autowired
    private Config config;


    //--------------- 生成文件 目录路径 ------------------//
    /**
     * dto目录路径
     */
    @Value("${dto.root.directory}")
    private String dtoDirectoryPath;
    /**
     * converter目录路径
     */
    @Value("${converter.root.directory}")
    private String converterDirectoryPath;
    /**
     * vo目录路径
     */
    @Value("${vo.root.directory}")
    private String voDirectoryPath;
    /**
     * dao目录路径
     */
    @Value("${dao.root.directory}")
    private String daoDirectoryPath;
    /**
     * service目录路径
     */
    @Value("${service.root.directory}")
    private String serviceDirectoryPath;

    /**
     * entity的目录路径
     */
    @Value("${entity.root.directory}")
    private String entityDirectoryPath;


    //--------------- 生成文件 包名 ------------------//
    /**
     * entity包名
     */
    @Value("${entity.root.package_name}")
    private String entityPackage;
    /**
     * dto包名
     */
    @Value("${dto.root.package_name}")
    private String dtoTemplatePackageName;
    /**
     * vo包名
     */
    @Value("${vo.root.package_name}")
    private String voTemplatePackageName;
    /**
     * converter包名
     */
    @Value("${converter.root.package_name}")
    private String converterTemplatePackageName;
    /**
     * dao包名
     */
    @Value("${dao.root.package_name}")
    private String daoTemplatePackageName;
    /**
     * service包名
     */
    @Value("${service.root.package_name}")
    private String serviceTemplatePackageName;


    //--------------- 模版文件名 ------------------//
    /**
     * dao模版文件
     */
    private String dtoTemplateFileName = "dto.ftl";
    /**
     * converter模版文件
     */
    private String converterTemplateFileName = "converter.ftl";
    /**
     * vo模版文件
     */
    private String voTemplateFileName = "vo.ftl";
    /**
     * dao模版文件
     */
    private String daoTemplateFileName = "dao.ftl";
    /**
     * service模版文件
     */
    private String serviceTemplateFileName = "service.ftl";

    @Bean
    public Configuration configuration() throws Exception {
        // step1 创建freeMarker配置实例
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        // step2 获取模版路径
        configuration.setDirectoryForTemplateLoading(
                new File(config.getTemplateDirectoryPath()));
        return configuration;
    }
}
