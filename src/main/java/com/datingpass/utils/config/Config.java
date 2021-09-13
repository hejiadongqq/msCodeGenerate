package com.datingpass.utils.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: Albert
 * @date: 2021-08-30 4:52 PM
 * @desc:
 */
@Component
@Data
public class Config {

    /**
     * 资源目录的路径
     */
    private String resourceDirectoryPath;

    private String version = "v1";

    /**
     * 模版目录名
     */
    private String templateDirectoryName = "templates";
    /**
     * 模版目录路径
     */
    private String templateDirectoryPath;

    /**
     * 备份目录
     */
    @Value("${project.root.backupDirectory}")
    private String projectBackupDirectoryPath;
    /**
     * 项目目录
     */
    @Value("${project.root.directory}")
    private String projectDirectoryPath;

    @PostConstruct
    public void init() {
        resourceDirectoryPath = Thread.currentThread().getContextClassLoader()
                .getResource("").getPath() + "/";
        templateDirectoryPath = resourceDirectoryPath + templateDirectoryName + "/";
    }
}
