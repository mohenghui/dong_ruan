package com.jd.after.modules.mis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jd.after.modules.mis.dao.BlogDao;
import com.jd.after.modules.mis.entity.Blog;
import com.jd.after.modules.mis.service.BlogService;

import org.springframework.stereotype.Service;

/**
 * @Service==》自动注解==》spring管理==》controller  @autowried 注入
 * 
 * 业务方法，事务
 * ServiceImpl<BlogDao, Blog>
 * 
 * 
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogDao, Blog> implements BlogService {

}
