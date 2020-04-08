package com.briup.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.demo.bean.Article;
import com.briup.demo.bean.ArticleExample;
import com.briup.demo.bean.ArticleExample.Criteria;
import com.briup.demo.bean.Category;
import com.briup.demo.bean.CategoryExample;
import com.briup.demo.bean.ex.CategoryEx;
import com.briup.demo.mapper.ArticleMapper;
import com.briup.demo.mapper.CategoryMapper;
import com.briup.demo.mapper.ex.CategoryExMapper;
import com.briup.demo.service.IArticleService;
import com.briup.demo.service.ICategoryService;
import com.briup.demo.utils.CustomerException;
import com.briup.demo.utils.StatusCodeUtil;

@Service
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	private CategoryMapper categoryMapper;
	
	@Autowired
	private ArticleMapper articleMapper;
	
	@Autowired
	private CategoryExMapper categoryExMapper;
	
	@Autowired
	private IArticleService articleService;
	
	@Override
	public void saveOrUpdateCategory(Category category) throws CustomerException {
		if(category==null) {
			throw new CustomerException(StatusCodeUtil.ERROR_CODE,"参数为空");   //如果参数为空，则抛出异常
		}
		
		if(category.getId()==null) {  //插入
			
			 //栏目名相同
			if((findCategorysByNameAndCode(category.getName(),category.getCode())).size()>0) {
				//当插入栏目时，如果表中已经有了该栏目名，就抛出异常给客户端
				throw new CustomerException(StatusCodeUtil.RENAME_CODE,"已有该栏目名");  
			}else {    //栏目名不同
				categoryMapper.insert(category);  //新增
			}
			
		}else {						//修改
			categoryMapper.updateByPrimaryKey(category);   // 加入说数据库中没有id 为 10的，传过来的category也没有值呢 ??
		}
		
	}

	@Override
	public void deleteCategoryById(int id) throws CustomerException {
		//删除栏目的同时，还要将栏目下面的所有文章也删除
		//categoryMapper.deleteByPrimaryKey(id);
		
		ArticleExample example = new ArticleExample();
		Criteria criteria = example.createCriteria().andCategoryIdEqualTo(id);
		articleMapper.deleteByExample(example);   //删除为该id的栏目下删文章
		
		categoryMapper.deleteByPrimaryKey(id);   //删栏目
	}

	@Override
	public List<Category> findAllCategorys() throws CustomerException {
		return categoryMapper.selectByExample(new CategoryExample());
	
	}

	@Override
	public Category findCategorysById(int id) throws CustomerException {
		Category category = categoryMapper.selectByPrimaryKey(id);
		return category;
	}
	
	
	@Override
	public List<CategoryEx> findAllCategoryEx() throws CustomerException {
		List<CategoryEx> list = categoryExMapper.findAllCategoryExs();
		return list;
	}
	
	
	
	
	/**
	 * 根据栏目名查询指定栏目
	 * 当插入栏目时，如果表中已经有了该栏目名，就抛出异常给客户端
	 * 
	 */
	@Override
	public List<Category> findCategorysByNameAndCode(String name,Long code) throws CustomerException {
		CategoryExample example = new CategoryExample();
		com.briup.demo.bean.CategoryExample.Criteria createCriteria = example.createCriteria().andNameEqualTo(name);
		example.or().andCodeEqualTo(code);
		List<Category> list = categoryMapper.selectByExample(example);
		return list;
	}

	//1.
	@Override
	public List<Article> showArticleByName(String name) throws CustomerException {
		List<Article> list = articleService.findArticleByCondition("", name);
		return list;
	}

	
	//2.
	@Override
	public CategoryEx findCategoryExById(Integer id) throws CustomerException {
		return categoryExMapper.findCategoryById(id);
	}


}
