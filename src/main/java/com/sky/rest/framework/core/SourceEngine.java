package com.sky.rest.framework.core;

import com.sky.rest.framework.annotation.inject.Inject;
import io.vertx.core.impl.ConcurrentHashSet;

import javax.naming.spi.ObjectFactory;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SourceEngine {

    private static Set<Class<?>> classSet = new ConcurrentHashSet<>();

    private static Map<String, Object> container;

    public static Set<Class<?>> classSetView;

    private static ClassLoader classLoader;

    public static void iterator(String basePackagePath) throws IOException, ClassNotFoundException {
        classLoader = SourceEngine.class.getClassLoader();
        Enumeration<URL> urlEnumeration = classLoader.getResources(basePackagePath);
        while (urlEnumeration.hasMoreElements()) {
            URL url = urlEnumeration.nextElement();
            if (url != null) {
                if (url.getProtocol().equals("file")) {
                    // class所在的实际路径
                    String packagePath = url.getPath().replaceAll("%20", "");
                    handlerClass(packagePath, basePackagePath);
                } else if (url.getProtocol().equals("jar")) {
                    JarURLConnection urlConnection = (JarURLConnection) url.openConnection();
                    JarFile jarFile = urlConnection.getJarFile();
                    if (jarFile != null) {
                        Enumeration<JarEntry> entries = jarFile.entries();

                        while (entries.hasMoreElements()) {
                            JarEntry jarEntry = entries.nextElement();
                            String name = jarEntry.getName();
                            if (name.endsWith(".class")) {
                                String className = name.substring(0, name.lastIndexOf(".")).replace(".", "/");
                                classSet.add(classLoader.loadClass(className));
                            }
                        }
                    }
                }
            }
        }
        newInstance();
        classSetView = Collections.unmodifiableSet(classSet);
    }

    private static void newInstance() {
        //TODO 解决对象依赖问题
        classSet.forEach(cls -> {
            try {

                Object newInstance = cls.newInstance();
                List<Field> fields = Arrays.asList(cls.getDeclaredFields()).stream().filter(f -> f.getAnnotation(Inject.class) != null).collect(Collectors.toList());
                for (Field field : fields) {
                    ApplicationContext.injectField(newInstance,field);


                }

                container.put(cls.getName(), cls.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

    }


    private static void handlerClass(String packagePath, String basePackagePath) throws ClassNotFoundException {
        File file = new File(packagePath);

        File[] files = file.listFiles(pathname -> (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory());

        for (File targetFile : files) {
            if (targetFile.isDirectory()) {
                handlerClass(targetFile.getPath(), basePackagePath + "/" + targetFile.getName());
            } else {
                String className = basePackagePath + "/" + targetFile.getName();
                className = className.substring(0, className.lastIndexOf(".")).replace("/", ".");
                classSet.add(classLoader.loadClass(className));
            }
        }
    }

}
