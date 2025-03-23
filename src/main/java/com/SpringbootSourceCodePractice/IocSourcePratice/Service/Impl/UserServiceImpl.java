package com.SpringbootSourceCodePractice.IocSourcePratice.Service.Impl;

import com.SpringbootSourceCodePractice.IocSourcePratice.Annotate.Mycomponent;
import com.SpringbootSourceCodePractice.IocSourcePratice.Entity.User;
@Mycomponent
public class UserServiceImpl  {
    public User getUser(String name) {
        return new User(name,"123456");
    }
}
