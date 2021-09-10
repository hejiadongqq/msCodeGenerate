package com.datingpass.utils.utils;

import com.datingpass.utils.vo.Field;
import com.google.common.collect.Lists;
import com.service.commons.tools.utils.GsonUtils;
import com.sun.tools.javac.resources.javac;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author: Albert
 * @date: 2021-08-16 11:50 AM
 * @desc:
 */
@Slf4j
public class ClassUtils {
    public static Class getClass(String packageName, String className) {
        String classPath = packageName + "." + className;
        try {
            return Class.forName(classPath);

        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * 获取数据库的描述定义
     *
     * @param column
     * @return
     */
    private static String getCOMMENT(Column column) {
        if (column == null) {
            return null;
        }
        String columnDefinition = column.columnDefinition();
//        columnDefinition = "VARCHAR(50) NOT NULL DEFAULT 'CAPTCHA' COMMENT '邮件模板类型'";
        String pattern0 = " COMMENT '";
        String pattern = pattern0 + ".*'";

        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(columnDefinition);
        if (matcher.find()) {
            String group = matcher.group(0).substring(pattern0.length());
            group = group.substring(0, group.length() - 1);
            if (StringUtils.isNotBlank(group)) {
                return group;
            }
        }
        return null;
    }

    public static Collection<Field> getFields(Class classObject) {
        return Lists.newArrayList(classObject.getDeclaredFields()).stream().map(field -> {
            Column column = field.getAnnotation(Column.class);
            String comment = getCOMMENT(column);
            if (Objects.isNull(comment)) {
                // 默认为字段名
                comment = field.getName();
            }

            boolean isEnum = field.getType().isEnum();

            return Field.builder()
                    .name(field.getName())
                    .type(field.getType().getName())
                    .desc(comment)
                    .isEnum(isEnum)
                    .build();
        }).collect(Collectors.toList());
    }

    /**
     * 编译并运行
     * @param packageClassName      包和类名
     * @param javaFilePath          java源码路径
     * @throws Exception
     */
    public static void complierAndRun(String packageClassName, String javaFilePath) throws Exception {
//        log.info(System.getProperty("user.dir"));
// swagger-annotations-1.5.14.jar

        String p = System.getProperty("java.class.path").replaceAll(":", ";").replaceAll("/\"", "");
        p += ";/Users/hejiadong/src/java/maosong/datingpaas/datingpaas-service/target/classes/*";

        log.info("--{}", p);
        //动态编译
        // make sure tools.jar is in this path
        String classpath = p;
        // path to your sources
        String sourcepath = javaFilePath;
        // directory for generated class files
        String putputpath = System.getProperty("user.dir") + "/datingpaas-service/target/classes";
        // file path the file you want to compile
        String filePath = javaFilePath;

        String[] args = new String[] {
                "-classpath", classpath,
                "-sourcepath", sourcepath,
                "-d", putputpath,
                filePath
        };
        int status = com.sun.tools.javac.Main.compile(args);


//        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
//        log.info("{}", GsonUtils.toJson(System.getProperty("java.class.path")).replaceAll(":",";"));
//        String arguments = "-cp"+ System.getProperty("java.class.path").replaceAll(":", ";")+ "\" -d \""+
//                System.getProperty("user.dir") + "/datingpaas-service/target/classes\"";

//        int status = javac.run(null, null, null,
////                "-classpath", System.getProperty("java.class.path").replaceAll(":",";")
////                , "-d",System.getProperty("user.dir") + "/datingpaas-service/target/classes"
////                , javaFilePath);
        if (status != 0) {
            log.error("没有编译成功！");
            throw new RuntimeException("类编译不成功!");
        }

        //动态测试执行
        Class clz = Class.forName(packageClassName);
        Object o = clz.newInstance();
        if (Objects.isNull(o)) {
            log.error("类加载失败!");
            throw new RuntimeException("类加载不成功！");
        }
    }
}
