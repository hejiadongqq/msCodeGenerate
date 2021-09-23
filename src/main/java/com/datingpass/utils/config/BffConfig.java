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

    private String strategyTemplatePackageName = "com.datingpaas.common.strategy.impl";

    /**
     * converter目录路径
     */
    @Value("${strategy.root.directory}")
    private String strategyDirectoryPath;
}
