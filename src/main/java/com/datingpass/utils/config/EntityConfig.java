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
public class EntityConfig {

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


    //--------------- 生成文件 包名 ------------------//
    /**
     * entity包名
     */
    private String entityPackage = "com.datingpaas.model.entity";
    /**
     * dto包名
     */
    private String dtoTemplatePackageName = "com.datingpaas.model.dto";
    /**
     * vo包名
     */
    private String voTemplatePackageName = "com.datingpaas.model.vo";
    /**
     * converter包名
     */
    private String converterTemplatePackageName = "com.datingpaas.model.converter.mapstruct";
    /**
     * dao包名
     */
    private String daoTemplatePackageName = "com.datingpaas.dao";
    /**
     * service包名
     */
    private String serviceTemplatePackageName = "com.datingpaas.service";


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
