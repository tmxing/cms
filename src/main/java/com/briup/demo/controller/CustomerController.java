package com.briup.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.briup.demo.bean.Customer;
import com.briup.demo.bean.CustomerExample;
import com.briup.demo.service.CustomerDao;
import com.briup.demo.utils.CustomerException;
import com.briup.demo.utils.Message;
import com.briup.demo.utils.MessageUtil;
import com.briup.demo.utils.StatusCodeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description="用户管理")
public class CustomerController {
	
	@Autowired
	private CustomerDao customer;
	
	@PostMapping("/addCostomer")
	@ApiOperation("注册用户")
	public Message<String> addCustomer(String username,String password){
		Customer cus = new Customer();
		cus.setPassword(password);
		cus.setUsername(username);
		customer.save(cus);
		return MessageUtil.success();
	}
	
	@GetMapping("/getAllCostomer")
	@ApiOperation("获得所有用户信息")
	public Message<List<Customer>> getAllCostomer(){
		return MessageUtil.success(customer.findAll());
	}
	
	
	@GetMapping("/loginCostomer")
	@ApiOperation("登录用户")
	public Message<String> loginCostomer(String username,String password){
		Customer cus = customer.findByUsernameAndPassword(username, password);
		Customer cusByUsername = customer.findByUsername(username);
		if(cus==null){
			if(cusByUsername==null) {    //已设定用户名唯一，根据用户名查找不到
				return MessageUtil.error(StatusCodeUtil.ERROR_CODE, "系统错误 : "
						+(new CustomerException(StatusCodeUtil.ERROR_CODE, "无此用户信息"))); 
			}else{       //根据用户名查找到了,说明密码错误
				return MessageUtil.error(StatusCodeUtil.ERROR_CODE, "系统错误 : "
						+(new CustomerException(StatusCodeUtil.ERROR_CODE, "用户密码错误"))); 
			}
		}
		return MessageUtil.success();
	}
	
	
	
	
	
	
	
}
