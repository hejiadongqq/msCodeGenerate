package com.datingpass.utils.config;

/**
 * @author: Albert
 * @date: 2021-09-10 5:57 PM
 * @desc:
 */
public interface ModuleConfig {
    String getDtoDirectoryPath();

    String getDtoTemplatePackageName();

    String getDtoTemplateFileName();


    String getVoDirectoryPath();

    String getVoTemplatePackageName();

    String getVoTemplateFileName();


    String getConverterDirectoryPath();

    String getConverterTemplateFileName();

    String getConverterTemplatePackageName();


}
