package com.SpringbootSourceCodePractice.IocSourcePratice;

import com.SpringbootSourceCodePractice.IocSourcePratice.Annotate.MyAutoWrited;
import com.SpringbootSourceCodePractice.IocSourcePratice.Annotate.Mycomponent;
import com.SpringbootSourceCodePractice.IocSourcePratice.Annotate.SpringBootScanner;
import com.SpringbootSourceCodePractice.IocSourcePratice.Controller.UserController;
import com.SpringbootSourceCodePractice.IocSourcePratice.Service.Impl.UserServiceImpl;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简化版IoC容器实现
 *
 * 核心功能：
 * 1. 模拟包扫描（需配合@SpringBootScanner注解）
 * 2. 自动创建Bean实例（标记@Mycomponent的类）
 * 3. 依赖注入（标记@MyAutoWrited的字段）
 *
 * 与Spring源码主要差异：
 * - 包扫描为硬编码实现，非动态加载
 * - 依赖查找仅支持简单类名匹配
 * - 不支持作用域/循环依赖等复杂特性
 */
public class MyApplicatioContext  {
    // 存储Bean名称与实例的映射关系（Key为类简单名称，如"UserServiceImpl"）
    private final Map<String, Object> beanMap = new ConcurrentHashMap<>();
    // 存储扫描到的类集合（模拟包扫描结果）
    private final Set<Class<?>> ClassSet = new HashSet<>();

    /**
     * 容器初始化入口
     * @param classes 配置类（需标注@SpringBootScanner）
     */
    public MyApplicatioContext(Class<?> classes) throws Exception {
        // 获取包扫描配置
        SpringBootScanner scanner = classes.getAnnotation(SpringBootScanner.class);
        String basePackage=scanner.basePath();

        // 初始化流程
        getClasses(basePackage);      // 模拟包扫描（实际需动态加载）
        addBean();                    // 创建Bean实例
        injectDependency();           // 依赖注入
    }

    /**
     * 模拟包扫描过程（实际应通过类加载器读取包路径）
     * 当前为硬编码实现，直接添加预设类
     *
     * @param basePackage 扫描基础包路径（本实现未实际使用）
     */
    public void getClasses(String basePackage){
        // TODO 实际应扫描basePackage路径下的.class文件
        ClassSet.add(UserServiceImpl.class);  // 添加Service实现类
        ClassSet.add(UserController.class);   // 添加Controller类
    }

    /**
     * 创建Bean实例并注册到容器
     * 仅处理标记@Mycomponent的类
     */
    public void addBean() throws Exception {
        for(Class<?> clazz : ClassSet){
            if(clazz.isAnnotationPresent(Mycomponent.class)){
                // 通过反射创建实例（仅支持无参构造器）
                Object instance = clazz.getDeclaredConstructor().newInstance();
                // 使用类简单名称作为Bean名称（如UserServiceImpl -> "UserServiceImpl"）
                String beanName = clazz.getSimpleName();
                beanMap.put(beanName,instance);
            }
        }
    }

    /**
     * 依赖注入核心方法
     * 遍历所有Bean的字段，为标记@MyAutoWrited的字段注入依赖
     *
     * 已知限制：
     * 1. 依赖查找仅通过字段类型的简单名称匹配（如UserDao字段查找"UserDao"）
     * 2. 不支持同一类型的多个Bean（如多个UserDao实现类）
     * 3. 未处理循环依赖问题
     */
    private void injectDependency() throws Exception {
        // 遍历所有Bean实例
        for (Object bean : beanMap.values()) {
            // 反射获取所有字段（包括私有字段）
            for (Field field : bean.getClass().getDeclaredFields()) {
                // 检查@MyAutoWrited注解
                if (field.isAnnotationPresent(MyAutoWrited.class)) {
                    // 根据字段类型名称查找依赖（如UserDao.class -> "UserDao"）
                    Object dependency = beanMap.get(field.getType().getSimpleName());

                    // 突破私有字段访问限制
                    field.setAccessible(true);

                    // 注入依赖（相当于 field.set(bean, dependency)）
                    field.set(bean, dependency);
                }
            }
        }
    }

    /**
     * 从容器获取Bean实例
     * @param beanName 类简单名称（如"UserServiceImpl"）
     */
    public Object getBean(String beanName) {
        return beanMap.get(beanName);
    }
}