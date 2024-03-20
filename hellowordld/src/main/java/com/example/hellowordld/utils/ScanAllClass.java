package com.example.hellowordld.utils;
import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

public class ScanAllClass {


    private static final String CLASS_FILE_EXTENSION = ".class";
    private static final String TEST_CLASS_SUFFIX = "Tests";
    private static final String CLASSES_PATH = ".\\target\\classes";
    private static final String TEST_CLASSES_PATH = ".\\target\\test-classes\\";
    //"target/test-classes/";
    public static void main(String[] args) {
       //String projectPath = args.length > 0 ? args[0] : "."; // 可以从命令行参数获取项目路径
        File classesDir = new File(CLASSES_PATH);
        File testClassesDir = new File(TEST_CLASSES_PATH);
        if (classesDir.exists() && classesDir.isDirectory() && testClassesDir.exists() && testClassesDir.isDirectory()) {
            scanClasses(classesDir, testClassesDir);
        } else {
            System.out.println("提供的路径不是有效的目录");
        }
    }
    private static void scanClasses(File dir, File testClassesDir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 如果是目录，递归调用
                    scanClasses(file, testClassesDir);
                } else {
                    // 如果是文件，检查是否以.CLASS_FILE_EXTENSION结尾
                    if (file.getName().endsWith(CLASS_FILE_EXTENSION)) {
                        String className = classNameFromFile(file, CLASSES_PATH);
                        //String testClassName = testClassNameFromClassName(className) + TEST_CLASS_SUFFIX;
                        String testClassPath = TEST_CLASSES_PATH + className.replace('.', '/') + TEST_CLASS_SUFFIX +  CLASS_FILE_EXTENSION;
                        //System.out.println("测试类路径:"+testClassPath);
                        File testClassFile = new File(testClassPath);
                        if (!testClassFile.exists()) {
                            System.out.println("没有找到对应的单元测试类: " + className);
                        }
                    }
                }
            }
        }
    }

    private static String testClassNameFromClassName(String className) {
        int dotIndex = className.lastIndexOf('.');
        if (dotIndex != -1) {
            className = className.substring(dotIndex+1);
        }
        return className;
    }
    private static String classNameFromFile(File classFile, String pathPrefix) {
        String path = classFile.getAbsolutePath();
        //System.out.println("path:"+path);
        String className = path.substring(path.indexOf(pathPrefix) + pathPrefix.length()+1, path.indexOf(CLASS_FILE_EXTENSION));
        className = className.replace(File.separatorChar, '.');
        return className;
    }

}
