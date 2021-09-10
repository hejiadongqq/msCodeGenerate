package com.datingpass.utils.services;

import java.io.File;
import java.util.Map;

/**
 * @author: Albert
 * @date: 2021-08-27 3:02 PM
 * @desc:
 */
public interface TemplateServices {
    /**
     * 生成文件
     *
     * @param templateFileName
     * @param dataMap
     * @param outFile
     * @throws Exception
     */
    void makeFile(String templateFileName, Map<String, Object> dataMap, File outFile) throws Exception;
}
