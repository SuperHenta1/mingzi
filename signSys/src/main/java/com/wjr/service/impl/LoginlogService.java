package com.wjr.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wjr.dao.SignDataMapper;
import com.wjr.pojo.SignData;
import com.wjr.service.ILoginlogService;
import com.wjr.util.AddressUtils;
import com.wjr.vo.PageBean;

@Service
public class LoginlogService implements ILoginlogService{

	@Autowired
	private SignDataMapper loginlogDao;
	
	@Override
	public PageBean<SignData> findLoginlogByPage(int page, int limit) {
		PageBean<SignData> pageInfo = new PageBean<>();

		pageInfo.setPageSize(limit);

		pageInfo.setCurrentPage(page);

		// 获取表中的记录总数
		int count = loginlogDao.count();
		pageInfo.setCount(count);
		// 计算总页数
		if(count % pageInfo.getPageSize() == 0){
			pageInfo.setTotalPage(count / pageInfo.getPageSize());
		}else{
			pageInfo.setTotalPage(count / pageInfo.getPageSize()+ 1);
		}
		// 根据页码计算分页查询时的开始索引
		int index = (page - 1) * pageInfo.getPageSize();
		Map<String, Object> map = new HashMap<>();
		map.put("index", index);
		map.put("size", pageInfo.getPageSize());
		List<SignData> list = loginlogDao.findByIndexAndSize(map);
		pageInfo.setPageInfos(list);

		return pageInfo;
	}

	@Override
	public void add(String ip, String no) {
		SignData l = new SignData();

		AddressUtils addressUtils = new AddressUtils();
		try {
			String address = addressUtils.getAddresses("ip="+ip, "utf-8");
			System.out.println(address);
			Date createtime = new Date();
			/*l.setCreatetime(createtime);
			l.setIp(ip);
			l.setLocation(address);
			l.setNo(no);
			loginlogDao.add(l);*/
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

}
