package com.datingpass.utils.services;

import com.datingpass.utils.config.BffConfig;
import com.datingpass.utils.config.Config;
import com.datingpass.utils.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;

/**
 * @author: Albert
 * @date: 2021-08-30 4:01 PM
 * @desc:
 */
@Service
@Slf4j
public class ProjectBffServicesImpl extends BaseServices implements ProjectBffServices {
    @Autowired
    private Config config;
    @Autowired
    private BffConfig bffConfig;

    @Override
    public void makeBff(MakeBffRequest request) throws Exception {
        // 检测项目是否存在
        // 项目名
        String projectName = request.getName();
        // 项目路径
        String projectPath = config.getProjectDirectoryPath() + "/" + projectName;

        check(projectName, projectPath);
    }

    private File check(String projectName, String projectPath) throws Exception {
        // 备份创建目录
        String backupName = projectPath + "_" + LocalDateTime.now() + "_bak";
        return Utils.backupDirect(projectName, backupName);
    }

    private void makePom(MakeBffRequest request, String projectPath) throws Exception {
        String pomPath = projectPath + "pom.xml";
    }

    @Override
    public void makeStrategy(MakeBffRequest request) throws Exception {
        super.makeStrategy(request, bffConfig);
    }
}
