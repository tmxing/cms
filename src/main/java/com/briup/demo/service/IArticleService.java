package com.briup.demo.service;

import java.util.List;

import com.briup.demo.bean.Article;
import com.briup.demo.utils.CustomerException;

/**
  *  文章相关的Service层
 * @author Administrator
 *
 */
public interface IArticleService {
	/**
	 * 新增或修改文章
	 * 
	 */
	void saveOrUpdateArticle(Article article) throws CustomerException;
	
	/**
	 * 删除文章
	 */
	void deleteArticleById(int id) throws CustomerException;
	
	
	/**
	 * 查询文章
	 * @param keyword     关键字
	 * @param condition   栏目
	 * @return
	 * @throws CustomerException
	 */
	List<Article> findArticleByCondition(String keyword,String condition) throws CustomerException;
	Article findArticleById(int id) throws CustomerException;
	
	
	Article showArticleInfoByName(String name) throws CustomerException;
	
}
