package com.wjr.dao;

import java.util.List;
import java.util.Map;

import com.wjr.pojo.SignData;

public interface SignDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SignData record);

    int insertSelective(SignData record);

    SignData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SignData record);

    int updateByPrimaryKey(SignData record);

	int count();

	List<SignData> findByIndexAndSize(Map<String, Object> map);

	int count(Map<String, Object> map);
}