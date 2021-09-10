package com.datingpass.utils.services;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Map;
import java.util.Objects;

/**
 * @author: Albert
 * @date: 2021-08-27 2:32 PM
 * @desc:
 */
@Service
public class TemplateServicesImpl implements TemplateServices {

    @Autowired
    private Configuration configuration;

    /**
     * 生成文件
     *
     * @param templateFileName
     * @param dataMap
     * @param outFile
     * @throws Exception
     */
    @Override
    public void makeFile(String templateFileName, Map<String, Object> dataMap, File outFile) throws Exception {
        Template template = getTemplate(templateFileName);
        makeFile(template, dataMap, outFile);
    }

    /**
     * 获取模版对象
     *
     * @param templateFileName 模版文件名
     * @return
     * @throws Exception
     */
    private Template getTemplate(String templateFileName) throws Exception {
        return configuration.getTemplate(templateFileName);
    }

    /**
     * 生成文件
     *
     * @param template
     * @param dataMap
     * @param outFile
     * @throws Exception
     */
    private void makeFile(Template template, Map<String, Object> dataMap, File outFile) throws Exception {
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
            template.process(dataMap, out);
        } catch (Exception e) {
            if (Objects.nonNull(out)) {
                out.flush();
                out.close();
            }
        }
    }
}
