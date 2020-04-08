package com.briup.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.demo.bean.Link;
import com.briup.demo.bean.LinkExample;
import com.briup.demo.bean.LinkExample.Criteria;
import com.briup.demo.mapper.LinkMapper;
import com.briup.demo.service.ILinkService;
import com.briup.demo.utils.CustomerException;
import com.briup.demo.utils.StatusCodeUtil;
/**
 * 操作链接service功能类
 * 
 * 	@Service:1.将对象添加到ioc容器中 2.自动进行事务管理
 * @author Administrator
 *
 */
@Service   //service层 对象
public class LinkServiceImpl implements ILinkService {

	@Autowired
	private LinkMapper linkMapper;   //dao层对象
	
	@Override
	public void saveOrUpdateLink(Link link) throws CustomerException {
		//参数为引用类型，要做判空处理
		if(link==null) {
			throw new CustomerException(StatusCodeUtil.ERROR_CODE, "参数为空");
		}
		//判断link对象id是否为空，如果为空--》新增，不为空--》修改
		if(link.getId()==null) {
			linkMapper.insert(link);
		}else {
			linkMapper.updateByPrimaryKey(link);
		}
			
	}

	@Override
	public List<Link> findAllLinks() throws CustomerException {
		//LinkExample example = new LinkExample();
						//example.createCriteria();  //创建sql条件,如果有条件的话，可以进行创建拼接
		//List<Link> list = linkMapper.selectByExample(example);
		//return list;
		return linkMapper.selectByExample(new LinkExample());
	}

	@Override
	public void deleteLinkById(int id) throws CustomerException {
		linkMapper.deleteByPrimaryKey(id);
		
	}

	@Override
	public List<Link> findByName(String name) throws CustomerException {
		name= name==null?"":name.trim();
		LinkExample example = new LinkExample();
		
		if("".equals(name)) {
			//如果搜索条件没写，则返回所有数据
			return linkMapper.selectByExample(example);
		}else {
			//如果写了搜索条件，则返回满足条件的数据
			Criteria criteria = example.createCriteria().andNameLike("%"+name+"%");   //模糊查询  创建条件  拼接sql
			return linkMapper.selectByExample(example);
			
		}
	
	}
	
	


}
