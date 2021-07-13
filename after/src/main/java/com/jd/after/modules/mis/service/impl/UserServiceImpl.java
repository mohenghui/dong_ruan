package com.jd.after.modules.mis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jd.after.modules.mis.dao.UserDao;
import com.jd.after.modules.mis.entity.User;
import com.jd.after.modules.mis.service.UserService;

import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}
