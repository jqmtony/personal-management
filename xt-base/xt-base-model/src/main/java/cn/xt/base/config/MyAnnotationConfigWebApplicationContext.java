package cn.xt.base.config;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.asm.ClassReader;
import org.springframework.core.NestedIOException;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.type.classreading.AnnotationMetadataReadingVisitor;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.*;

/**
 * 解决@Order在@Component无法一起使用的问题
 * Created by heshun on 2017/3/6.
 */
public class MyAnnotationConfigWebApplicationContext extends AnnotationConfigWebApplicationContext {

    /**
     * key是bean的名称
     * value是Order注解
     */
    private Map<String, Order> beanNameOrderCache;

    /**
     * 扫描类上有Order的注解的class
     */
    private void scanOrderAnnotationType() {
        if (beanNameOrderCache == null) {
            beanNameOrderCache = new HashMap<>();
            Reflections reflections = new Reflections(new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forPackage("cn.xt"))
                    .setScanners(new SubTypesScanner(),
                            new TypeAnnotationsScanner()));

            Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Order.class);
            typesAnnotatedWith.forEach(clazz -> {
                Order order = clazz.getAnnotation(Order.class);
                String canonicalName = clazz.getCanonicalName();
                beanNameOrderCache.put(canonicalName, order);
            });
        }

    }

    @Override
    public Resource[] getResources(String locationPattern) throws IOException {

        scanOrderAnnotationType();

        Resource[] resources = super.getResources(locationPattern);
        Arrays.sort(resources, new Comparator<Resource>() {
            @Override
            public int compare(Resource o1, Resource o2) {
                try {
                    String classNameByResource = getClassNameByResource(o1);
                    Order order1 = beanNameOrderCache.get(classNameByResource);
                    Order order2 = beanNameOrderCache.get(getClassNameByResource(o2));
                    int orderValue1 = 0;
                    int orderValue2 = 0;
                    if (order1 != null) {
                        orderValue1 = order1.value();
                    }
                    if (order2 != null) {
                        orderValue2 = order2.value();
                    }
                    return orderValue1 - orderValue2;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }
        });
        return resources;
    }

    /**
     * 从Resource中获取class的名称
     *
     * @param resource
     * @return
     * @throws IOException
     */
    private String getClassNameByResource(Resource resource) throws IOException {
        BufferedInputStream is = new BufferedInputStream(resource.getInputStream());

        ClassReader classReader;
        try {
            classReader = new ClassReader(is);
        } catch (IllegalArgumentException var9) {
            throw new NestedIOException("ASM ClassReader failed to parse class file - probably due to a new Java class file version that isn\'t supported yet: " + resource, var9);
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        } finally {
            is.close();
        }
        ClassLoader classLoader = this.getClassLoader();
        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor(classLoader);
        classReader.accept(visitor, ClassReader.SKIP_DEBUG);

        String beanClassName = visitor.getClassName();
        return beanClassName;
    }

}
