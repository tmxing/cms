package com.briup.demo.utils;

import java.util.Date;

/**
 * 返回消息的类型
 * @author Administrator
 *
 */
public class MessageUtil {
	/**
	 * 成功，并且返回数据
	 * 第一个E 表示后面一个E 是一个泛型
	 */
	public static <E>Message<E> success(E obj){
		return new Message<E>(200, "success", obj, new Date().getTime());
	}
	
	/**
	 * 成功，但无返回值
	 */
	public static <E>Message<E> success(){
		return new Message<E>(200, "success",null,new Date().getTime());
	}
	
	/**
	 * 失败，返回错误信息  自定义异常信息
	 */
	public static <E>Message<E> error(Integer code,String msg){
		return new Message<E>(code, msg,null,new Date().getTime());
	}
	
	
}
