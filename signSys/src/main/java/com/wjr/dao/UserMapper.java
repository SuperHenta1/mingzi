package com.wjr.dao;


import com.wjr.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	User findByNo(String no);

	int findSignIn(int uid);

	int findSignOut(int uid);


	User findPassword(String email);

}