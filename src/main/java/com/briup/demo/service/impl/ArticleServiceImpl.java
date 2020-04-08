package com.briup.demo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.demo.bean.Article;
import com.briup.demo.bean.ArticleExample;
import com.briup.demo.bean.ArticleExample.Criteria;
import com.briup.demo.bean.Category;
import com.briup.demo.bean.CategoryExample;
import com.briup.demo.mapper.ArticleMapper;
import com.briup.demo.mapper.CategoryMapper;
import com.briup.demo.service.IArticleService;
import com.briup.demo.utils.CustomerException;
import com.briup.demo.utils.StatusCodeUtil;

/**
 * 实现文章管理相关的逻辑类
 * @author Administrator
 *
 */
@Service
public class ArticleServiceImpl implements IArticleService {

	@Autowired
	private ArticleMapper articleMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	
	
	
	@Override
	public void saveOrUpdateArticle(Article article) throws CustomerException {
		if(article==null) {
			throw new CustomerException(StatusCodeUtil.ERROR_CODE, "参数为空");
		}
		
		if(article.getId()==null) {
//			需要额外添加两条数据
			article.setPublishdate(new Date());   //发布日期
			article.setClicktimes(0);			  //点赞次数
			articleMapper.insert(article);
		}else {
			//article.setPublishdate(new Date());   //如果是作者修改，则更新日期
			articleMapper.updateByPrimaryKey(article);
		}
		
	}

	@Override
	public void deleteArticleById(int id) throws CustomerException {
		articleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Article> findArticleByCondition(String keyword, String condition) throws CustomerException {
		/*
		 * 三种情况：   1.没有添加任何条件，则查询所有文章。
		 * 			2.没指定栏目，但指定了关键字，则根据关键字查询满足条件的所有文章。
		 * 			3.指定栏目，没指定了关键字，则根据栏目查询满足条件的所有文章。
		 * 			4.指定栏目，同时指定查询的关键字，则根据栏目和关键字查询满足条件的文章。
		 */
		ArticleExample example = new ArticleExample();
		keyword =  keyword==null?"":keyword.trim();
		condition =  condition==null?"":condition.trim();
		
		if("".equals(keyword) && "".equals(condition)) {
			//情况1
			return articleMapper.selectByExample(example);
			
		}else if(!"".equals(keyword)&&"".equals(condition)) {
			//情况2
			example.createCriteria().andTitleLike("%"+keyword+"%");
			return articleMapper.selectByExample(example);
			
		}else if(!"".equals(condition)) {
			
			CategoryExample categoryexample = new CategoryExample();
			categoryexample.createCriteria().andNameEqualTo(condition);    //根据栏目名查栏目id
			List<Category> list = categoryMapper.selectByExample(categoryexample);
			if(list.size()>0) {
				
				if("".equals(keyword)) {
					//情况3
					Criteria criteria = example.createCriteria().andCategoryIdEqualTo(list.get(0).getId());
					// or方式，拼接条件
					//example.or().andXXXX  或
				}else {
					//情况4
					Criteria criteria = example.createCriteria().andCategoryIdEqualTo(list.get(0).getId()).andTitleLike(keyword);
				}
				return articleMapper.selectByExample(example);
			}else throw new CustomerException(StatusCodeUtil.ERROR_CODE, "没有指定栏目");
		}
		return null;
	}

	@Override
	public Article findArticleById(int id) throws CustomerException {
		Article article = articleMapper.selectByPrimaryKey(id);
		Integer clickTime = article.getClicktimes()== null?0:(article.getClicktimes()+1);
		article.setClicktimes(clickTime);
		saveOrUpdateArticle(article);
		return article;
	}

	@Override
	public Article showArticleInfoByName(String name) throws CustomerException {
		List<Article> list = findArticleByCondition(name,"");
		Article article = list.get(0);
		article.setClicktimes(article.getClicktimes()+1);
		articleMapper.updateByPrimaryKey(article);
		return article;
	}

}
