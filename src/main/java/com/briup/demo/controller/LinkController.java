package com.briup.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.briup.demo.bean.Link;
import com.briup.demo.service.ILinkService;
import com.briup.demo.utils.CustomerException;
import com.briup.demo.utils.Message;
import com.briup.demo.utils.MessageUtil;
import com.briup.demo.utils.StatusCodeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 与链接相关的  和前端交互的web层
 * @author Administrator
 *
 */
@RestController
@Api(description="链接管理")
public class LinkController {
	
	//在 @Service 将对象放入ioc容器中  
	@Autowired
	private ILinkService linkService;   
	
	//Get 查询 删除   Post 插入，修改
	@PostMapping("/addLink")
	@ApiOperation("新增链接")
	public Message<String> addLink(Link link){
		try {
			linkService.saveOrUpdateLink(link);
			return MessageUtil.success();
		} catch (CustomerException e) {
			return MessageUtil.error(StatusCodeUtil.ERROR_CODE,"系统错误 ： "+e.getMessage());
		}
		
	}
	
	@PostMapping("/updateLink")
	@ApiOperation("修改链接")
	public Message<String> updateLink(Link link){
			try {
				linkService.saveOrUpdateLink(link);
				return MessageUtil.success();
			} catch (CustomerException e) {
				return MessageUtil.error(StatusCodeUtil.ERROR_CODE,"系统错误 ： "+e.getMessage());
			}
			
	}
	
	
	@GetMapping("/deleteLinks")
	@ApiOperation("根据id删除链接")
	public Message<String> deleteById(int id){
		linkService.deleteLinkById(id);
		return MessageUtil.success();
		
	}
	
	
	@GetMapping("/selectLinks")
	@ApiOperation("查询链接")
	public Message<List<Link>> selectLinks(){
		List<Link> list = linkService.findAllLinks();
		return MessageUtil.success(list);
		
	}
	
	@GetMapping("/selectLinkByName")
	@ApiOperation("根据名字查询链接")
	public Message<List<Link>> selectLinkByName(String name){
		return MessageUtil.success(linkService.findByName(name));
	}
	
	
	
	
}
