package com.datingpass.utils.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.service.commons.config.auth.UserToken;
import com.service.commons.config.auth.UserTokenUtils;
import com.service.commons.tools.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: Albert
 * @date: 2021-07-27 2:48 PM
 * @desc:
 */
@Slf4j
public class Utils {
    static Long tokenExpire = 604800000L;
    private static final Long EXPIRE_TIME = tokenExpire * 1000L;
    private static final Algorithm ALGORITHM = Algorithm.HMAC256("78cIYsXMF9TLDCPy");
    private static PasswordEncoder _passwordEncoder;


    public static String makeToken(Long userId) {
        UserToken token = new UserToken();
        token.setId(Long.valueOf(userId));

        Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        return JWT.create().withIssuer("datingpaas").withExpiresAt(expiresAt)
                .withClaim("id", token.getId())
                .withClaim("timestamp", token.getTimestamp()).sign(ALGORITHM);
    }

    public static UserToken getUserToken(String token) {
        UserToken decode = UserTokenUtils.decode(token);
        if (Objects.isNull(decode)) {
            decode = new UserToken();
        }
        return decode;
    }

    public static PasswordEncoder get_passwordEncoder() {
        if (_passwordEncoder == null) {
            _passwordEncoder = new BCryptPasswordEncoder(4);
        }
        return _passwordEncoder;
    }

    public static String encode(String pwd) {
        PasswordEncoder passwordEncoder = get_passwordEncoder();
        return passwordEncoder.encode(pwd);
    }

    /**
     * 备份文件
     *
     * @param fileName 原地址
     * @param bakName  备份文件名
     * @return
     * @throws Exception
     */
    public static File backupFile(String fileName, String bakName) throws Exception {
        File file = new File(fileName);
        if (file.exists()) {
            // 备份文件
            log.info("文件/目录已存在!备份名为-->{}", bakName);
            File bak = new File(bakName);
            FileUtils.moveFile(file, bak);
            log.info("重新生成-->{}", fileName);
        }
        return file;
    }

    /**
     * 备份并且创建目录
     *
     * @param fileName
     * @param bakName
     * @return
     * @throws Exception
     */
    public static File backupDirect(String fileName, String bakName) throws Exception {
        File file = backupFile(fileName, bakName);
        file.mkdirs();
        return file;
    }

    public static void moveBackupFile(String backupDirectoryPath, String projectPath) throws Exception {
        log.info("移动所有备份文件到指定的备份目录");

        Collection<File> baks = FileUtils.listFiles(new File(projectPath),
                new String[]{"bak"}, true);

        File backupFile = new File(backupDirectoryPath);

        // 排除掉已经备份了的文件
        baks = baks.stream().filter(x -> !backupFile.getPath().equals(FilenameUtils.getPath(x.getPath())))
                .collect(Collectors.toList());
        for (File bak : baks) {
            String name = bak.getName();
            String targetPath = backupDirectoryPath + "/" + name;
            log.info("源文件{} 移动到 {}", bak.getPath(), targetPath);
            FileUtils.moveFile(bak, new File(targetPath));
        }
    }

    public static void main(String[] args) {
        Long userId = 634386221732204544L;
        String userToken = makeToken(userId);
        log.info("userToken--->{}", userToken);

        userToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkYXRpbmdwYWFzIiwiaWQiOjYzNjU5MTA3NDMyNzkyNDczNiwiZXhwIjoxNjYxNTA1ODg1LCJ0aW1lc3RhbXAiOjE2Mjk5Njk4ODU1MTR9.dHzrbx8MgrFiNAqqgKoCtxHj9QbG_4B6g9TgqyvomXg";
        log.info("user---->{}", GsonUtils.toJson(getUserToken(userToken)));

        String pwd = "sdfjkxvn5";
//        pwd = "111111";
//        log.info("-->{}", encode(pwd));

        String md5 = "$2a$04$/iN8p5qSD.4PSwE6foAL2.3ILFFchemxCYtSS1MvtjhxTWEbsRa8W";
        log.info("-->{}", get_passwordEncoder().matches(pwd, md5));

        // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkYXRpbmdwYWFzIiwiaWQiOjYzMzk4NjE2ODE1MjY1NzkyMCwiZXhwIjoxNjYwNjE5Njg0LCJ0aW1lc3RhbXAiOjE2MjkwODM2ODQ0Njd9.A-yyJzkL3jAt1onENRv28a9VOgLDyI4ko6GAJhXuqaA

    }
}
