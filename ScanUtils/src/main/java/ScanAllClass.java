package ScanUtils.src.main.java;
import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

public class ScanAllClass {


    private static final String CLASS_FILE_EXTENSION = ".class";
    private static final String TEST_CLASS_SUFFIX = "Test";
    public static void main(String[] args) {
        String projectPath = args.length > 0 ? args[0] : "."; // 可以从命令行参数获取项目路径
        File projectDir = new File(projectPath);
        if (projectDir.exists() && projectDir.isDirectory()) {
            scanClasses(projectDir);
        } else {
            System.out.println("提供的路径不是有效的项目目录: " + projectPath);
        }
    }
    private static void scanClasses(File dir) {
        File[] classFiles = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(CLASS_FILE_EXTENSION);
            }
        });
        if (classFiles != null) {
            for (File classFile : classFiles) {
                String className = classNameFromFile(classFile);
                String testClassName = testClassNameFromClassName(className);
                if (!hasClassFile(dir, testClassName)) {
                    System.out.println("没有找到对应的单元测试类: " + className);
                }
            }
        }
    }
    private static String classNameFromFile(File classFile) {
        String path = classFile.getAbsolutePath();
        String className = path.substring(path.indexOf("classes/") + "classes/".length(), path.indexOf(CLASS_FILE_EXTENSION));
        className = className.replace(File.separatorChar, '.');
        return className;
    }
    private static String testClassNameFromClassName(String className) {
        int dotIndex = className.lastIndexOf('.');
        if (dotIndex != -1) {
            className = className.substring(dotIndex + 1);
        }
        return className + TEST_CLASS_SUFFIX;
    }
    private static boolean hasClassFile(File dir, String className) {
        String classFilePath = "classes/" + className.replace('.', File.separatorChar) + CLASS_FILE_EXTENSION;
        File testClassFile = new File(dir, classFilePath);
        return testClassFile.exists();
    }
}
