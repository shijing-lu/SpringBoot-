# SpringBoot 源码分析和实现
2025-03-23
### IOC控制反转
> [!info]
> IOC 的全称是Inversion of Control，控制反转，它一种设计原则，通过将对对象的创建，依赖管理和生命周期交给 SpringBoot 中的 IOC 容器管理，以实现代码的解耦。

IOC 原理：
1. 反射：IOC 的实现是利用了 Java 的反射机制，在代码运行时动态地获取以及创建新的对象，访问对象的属性、方法等信息，实现灵活的对象实例化和管理
2. 依赖注入：IOC 的核心是依赖注入，容器负责管理应用程序之间的依赖管理。Spring 通过构造函数注入、属性注入、方法注入，将组件之间的依赖关系描述在注解或配置文件中。
3. 设计模式-工厂模式：IOC 容器通过使用工厂模式来管理和生产对象。

### 实践

> [!info]
> 模拟业务场景，查询用户相关信息
##### 1 . 自定义依赖注解

![image.png](https://raw.githubusercontent.com/shijing-lu/typora-Picture/main/2024-10/20250323190623.png)
##### 2. 自定义应用上下文（IOC 容器和管理）

``` java
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
```

##### 3. 新建项目结构
![image.png](https://raw.githubusercontent.com/shijing-lu/typora-Picture/main/2024-10/20250323190342.png)

##### 4. 新建 Service 类

``` java
@Mycomponent  
public class UserServiceImpl  {  
    public User getUser(String name) {  
        return new User(name,"123456");  
    }  
}
```

##### 5. 新建 controller、Entity
###### 5.1 Entity

``` java
public class User {  
    private String name;  
    private String password;
    }

```

###### 5.2 controller

``` java
@Mycomponent  
public class UserController {  
    @MyAutoWrited  
    private UserServiceImpl UserServiceImpl;  
    public String getUser(String name){  
        return UserServiceImpl.getUser(name).toString();  
    }  
}
```

##### 6. 启动 项目

``` java

@SpringBootScanner(basePath = "com.SpringbootSourceCodePractice.IocSourcePratice")  
public class SpringBootApplication {  
    public static void main(String[] args) throws Exception {  
        MyApplicatioContext context = new MyApplicatioContext(SpringBootApplication.class);  
        UserController userController =(UserController) context.getBean("UserController");  
        System.out.println(userController.getUser("yyh"));  
    }  
}
```

##### 7. 结果

![image.png](https://raw.githubusercontent.com/shijing-lu/typora-Picture/main/2024-10/20250323191138.png)





