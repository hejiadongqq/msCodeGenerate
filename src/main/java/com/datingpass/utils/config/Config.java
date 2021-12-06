package com.datingpass.utils.config;

import com.datingpass.utils.utils.ClassUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Collection;

/**
 * @author: Albert
 * @date: 2021-08-30 4:52 PM
 * @desc:
 */
@Component
@Data
@Slf4j
public class Config {

    /**
     * 资源目录的路径
     */
    private String resourceDirectoryPath;

    // 控制器版本号
    @Value("${controller.version}")
    private String version;

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

    /**
     * 第三方依赖包
     */
    @Value("${libs.jars}")
    private String[] jars;

    @PostConstruct
    public void init() {
        resourceDirectoryPath = Thread.currentThread().getContextClassLoader()
                .getResource("").getPath() + "/";
        templateDirectoryPath = resourceDirectoryPath + templateDirectoryName + "/";
        for (String jar : jars) {
            log.info("开始装载第三方jar包:-->{}", jar);
            File file = new File(jar);
            if (!file.exists() || !file.isFile()) {
                throw new RuntimeException("加载第三方jar包失败，不存在或非文件!");
            }
            ClassUtils.loadJar(jar);
        }
    }
}
