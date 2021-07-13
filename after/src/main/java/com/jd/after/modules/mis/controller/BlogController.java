package com.jd.after.modules.mis.controller;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jd.after.common.core.P;
import com.jd.after.common.core.R;
import com.jd.after.modules.mis.entity.Blog;
import com.jd.after.modules.mis.service.BlogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RestController：返回json 
 * 
 * @PostMapping
 * @GetMapping
 * 
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    //注入
    @Autowired
    BlogService blogService;

    
   /**
     * 列表
     * http://localhost:8081/blog/list?limit=5&page=1
     * 
     * @RequestParam=>map   {k1:v1,k2:v2,k3:v3........}
     */
    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
         //分页参数
        //  long curPage = 1;
        //  long limit = 10;
        // if(params.get("page") != null){
        //     curPage = Long.parseLong((String)params.get("page"));
        // }
        // if(params.get("limit") != null){
        //     limit = Long.parseLong((String)params.get("limit"));
        // }
        //  //分页对象
        // Page page = new Page(curPage, limit);
        System.out.println(params);

        IPage<Blog> pageData = blogService.page(P.page(params),new QueryWrapper<Blog>().orderByDesc("created"));
        return R.ok(pageData);
    }


    /**
     * 信息
     * 
     *      /info/3   ==>get
     * @PathVariable
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		Blog blog = blogService.getById(id);
        return R.ok(blog);
    }

    /**
     * 保存
     * http://localhost:8081/blog/save
     * 
     * "Content-Type": "application/json"
     * 
     * @RequestBody
     */
    @PostMapping("/save")
    public R save(@Validated @RequestBody Blog blog){
        System.out.println(blog);
        blog.setUserId(1L);
        blog.setCreated(LocalDateTime.now());
        blog.setStatus(0);
		blogService.save(blog);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@Validated @RequestBody Blog blog){
		blogService.updateById(blog);
        return R.ok();
    }

    /**
     * 删除    [4,7,100,99] ==>ids  
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        System.out.println(ids);
		blogService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }







}
