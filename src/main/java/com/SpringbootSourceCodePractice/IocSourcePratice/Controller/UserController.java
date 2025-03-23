package com.SpringbootSourceCodePractice.IocSourcePratice.Controller;

import com.SpringbootSourceCodePractice.IocSourcePratice.Annotate.MyAutoWrited;
import com.SpringbootSourceCodePractice.IocSourcePratice.Annotate.Mycomponent;
import com.SpringbootSourceCodePractice.IocSourcePratice.Service.Impl.UserServiceImpl;

@Mycomponent
public class UserController {
    @MyAutoWrited
    private UserServiceImpl UserServiceImpl;
    public String getUser(String name){
        return UserServiceImpl.getUser(name).toString();
    }
}
