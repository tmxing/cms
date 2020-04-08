package com.briup.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.briup.demo.bean.Article;
import com.briup.demo.bean.Category;
import com.briup.demo.bean.ex.CategoryEx;
import com.briup.demo.service.ICategoryService;
import com.briup.demo.utils.CustomerException;
import com.briup.demo.utils.Message;
import com.briup.demo.utils.MessageUtil;
import com.briup.demo.utils.StatusCodeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 与栏目相关的Controller
 * @author Administrator
 *
 */
@RestController
@Api(description="栏目管理")
public class CategoryController {
	
	
	@Autowired
	private ICategoryService  categoryService;
	
	//Get 查询 删除   Post 插入，修改
	@PostMapping("/addCategory")
	@ApiOperation("新增栏目")
	public Message<String> addCategory(Category category) {
		try {
			categoryService.saveOrUpdateCategory(category);
			return MessageUtil.success();
		} catch (CustomerException e) {
			if(e.getCode()==StatusCodeUtil.RENAME_CODE) return MessageUtil.error(StatusCodeUtil.RENAME_CODE,"系统错误 : "+e.getMessage());
			return MessageUtil.error(StatusCodeUtil.ERROR_CODE,"系统错误 : "+e.getMessage());
		}
	}
	
	@PostMapping("/updateCategory")
	@ApiOperation("修改栏目")
	public Message<String> updateCategory(Category category) {
		try {
			categoryService.saveOrUpdateCategory(category);
			return new MessageUtil().success();
		} catch (CustomerException e) {
			return MessageUtil.error(StatusCodeUtil.ERROR_CODE,"系统错误 : "+e.getMessage());
		}
	}
	
	
	@GetMapping("/deleteCategory")
	@ApiOperation("删除栏目")
	public Message<String> deleteCategoryById(int id) {
		//在Service接口实现类中    删除栏目的同时，还要将栏目下面的所有文章也删除
		categoryService.deleteCategoryById(id);
		return new MessageUtil().success();
	}
	
	
	@GetMapping("/findAllCategorys")
	@ApiOperation("查询所有栏目")
	public Message<List<Category>> findAllCategorys() {
		List<Category> list = categoryService.findAllCategorys();
		return new MessageUtil().success(list);
	}
	
	@GetMapping("/findCategoryById")
	@ApiOperation("根据id查询栏目")
	public Message<Category> findCategoryById(int id) {
		Category category = categoryService.findCategorysById(id);
		return new MessageUtil().success(category);
		
	}
	
	//法1.
	@GetMapping("/showArticleByCategoryName")
	@ApiOperation("根据栏目名查询其所有文章")
	public Message<List<Article>> findArticleByTitle(String name) {
		List<Article> articleByTitle = categoryService.showArticleByName(name);
		return new MessageUtil().success(articleByTitle);
		
	}
	
	
	//法2.
	@GetMapping("/findCategoryExById")
	@ApiOperation("根据栏目id查询栏目其所有文章")
	public Message<CategoryEx> findCategoryExById(Integer id) {
		CategoryEx categoryEx = categoryService.findCategoryExById(id);
		return new MessageUtil().success(categoryEx);
	}
	
	
	
	
}
