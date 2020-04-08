package com.briup.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.briup.demo.bean.Article;
import com.briup.demo.service.IArticleService;
import com.briup.demo.utils.CustomerException;
import com.briup.demo.utils.Message;
import com.briup.demo.utils.MessageUtil;
import com.briup.demo.utils.StatusCodeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description="文章管理")
public class ArticleController {
	
	@Autowired
	private IArticleService articleService;
	
	@PostMapping("/addArticle")
	@ApiOperation("新增文章")
	public Message<String> addArticle(Article article){
		
	     try {
			articleService.saveOrUpdateArticle(article);
			return new MessageUtil().success();
		} catch (CustomerException e) {
			return new MessageUtil().error(StatusCodeUtil.ERROR_CODE, "系统错误: "+e.getMessage());
		}
	}	
	
	@PostMapping("/updateArticle")
	@ApiOperation("修改文章")
	public Message<String> updateArticle(Article article){
		try {
			articleService.saveOrUpdateArticle(article);
			return new MessageUtil().success();
		} catch (CustomerException e) {
			return new MessageUtil().error(StatusCodeUtil.ERROR_CODE, "系统错误: "+e.getMessage());
		}
	}	
	
	@GetMapping("/deleteArticleById")
	@ApiOperation("根据id删除文章")
	public Message<String> deleteArticleById(Integer id){
			articleService.deleteArticleById(id);
			return new MessageUtil().success();
	}	

	
	@GetMapping("/findArticleByCondition")
	@ApiOperation("根据条件查找文章")
	public Message<List<Article>> getArticleByCondition(String keyword,String condition){
		try {
			List<Article> list = articleService.findArticleByCondition(keyword, condition);
			return new MessageUtil().success(list);
		} catch (CustomerException e) {
			return new MessageUtil().error(StatusCodeUtil.ERROR_CODE, "系统错误 :  "+e.getMessage());
		}
	}	
	
	@GetMapping("/findArticleById")
	@ApiOperation("根据id查找文章")
	public Message<Article> getArticleByid(Integer id){
		return new MessageUtil().success(articleService.findArticleById(id));
	}	
	
	
	@GetMapping("/showArticleByName")
	@ApiOperation("根据id查找文章")
	public Message<Article> getArticleByName(String name){
		
		return new MessageUtil().success(articleService.showArticleInfoByName(name));
	}	
	
}
