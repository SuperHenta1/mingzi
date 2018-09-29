package com.wjr.service;

import com.wjr.pojo.SignData;
import com.wjr.pojo.User;
import com.wjr.vo.PageBean;

public interface IUserService {

	/**
	 * 分页查询
	 * @param page 页码
	 * @param limit 每页数据数
	 * @return 返回 分页数据
	 */
	public PageBean<SignData> findUserByPage(int page, int limit, int uid);
	
	
	/**
	 * 根据id查询用户
	 * @param id 用户id
 	 */
	public User findById(int id);


	public void add(User user);


	public User findByNo(String no);


	public int findSignInBy(int uid);


	public int findSignOutBy(int uid);


	public void signIn(int uid);


	public void update(int id, User user);


	public User findPassword(String email);


	
	
}
