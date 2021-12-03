package com.datingpass.utils.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: Albert
 * @date: 2021-08-30 5:04 PM
 * @desc:
 */
@Component
@Data
public class BffConfig {
    private String strategyTemplateFileName = "strategy.ftl";

    /**
     * 包名
     */
    @Value("${strategy.root.package_name:}")
    private String strategyTemplatePackageName;

    /**
     * 验单目录路径
     */
    @Value("${strategy.root.directory:}")
    private String strategyDirectoryPath;
}
