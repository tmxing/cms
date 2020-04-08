package com.briup.demo.service;
/**
 * 栏目相关的Service层
 * @author Administrator
 *
 */

import java.util.List;

import com.briup.demo.bean.Article;
import com.briup.demo.bean.Category;
import com.briup.demo.bean.ex.CategoryEx;
import com.briup.demo.utils.CustomerException;

public interface ICategoryService {
	/**
	 * 添加或修改栏目信息
	 */
	void saveOrUpdateCategory(Category category) throws CustomerException;
	
	/**
	 * 根据id删除栏目信息
	 */
	void deleteCategoryById(int id) throws CustomerException;
	
	/**
	 * 查询所有栏目
	 */
	List<Category> findAllCategorys() throws CustomerException;
	
	/**
	 * 根据栏目id查询指定栏目
	 */
	Category findCategorysById(int id) throws CustomerException;
	
	/**
	 * 根据栏目名查询指定栏目
	 * 当插入栏目时，如果表中已经有了该栏目名，就抛出异常给前台
	 * 
	 */
	List<Category> findCategorysByNameAndCode(String name,Long code) throws CustomerException;
	
	
	/**
	 * 查询所有栏目信息并且级联查询包含的文章信息
	 */
	List<CategoryEx> findAllCategoryEx() throws CustomerException;
	
	//1.
	List<Article> showArticleByName(String name) throws CustomerException;
	
	//2.
	/**
	 * 查询栏目所包含的文章的所有数据
	 */
	CategoryEx findCategoryExById(Integer id) throws CustomerException;
	
	
	
	
}
