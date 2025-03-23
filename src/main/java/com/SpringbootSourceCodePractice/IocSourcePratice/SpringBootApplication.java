package com.SpringbootSourceCodePractice.IocSourcePratice;

import com.SpringbootSourceCodePractice.IocSourcePratice.Annotate.SpringBootScanner;
import com.SpringbootSourceCodePractice.IocSourcePratice.Controller.UserController;

@SpringBootScanner(basePath = "com.SpringbootSourceCodePractice.IocSourcePratice")
public class SpringBootApplication {
    public static void main(String[] args) throws Exception {
        MyApplicatioContext context = new MyApplicatioContext(SpringBootApplication.class);
        UserController userController =(UserController) context.getBean("UserController");
        System.out.println(userController.getUser("yyh"));
    }
}
