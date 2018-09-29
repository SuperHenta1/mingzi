package com.wjr.service;

import com.wjr.pojo.SignData;
import com.wjr.vo.PageBean;

public interface ILoginlogService {

	public void add(String ip, String no);

	PageBean<SignData> findLoginlogByPage(int page, int limit);

}
