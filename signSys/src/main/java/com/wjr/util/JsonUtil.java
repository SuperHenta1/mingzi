package com.wjr.util;

import com.wjr.vo.JsonBean;

public class JsonUtil {
	
	public static JsonBean JsonBeanS(Integer code, Object msg) {
		
		JsonBean bean = new JsonBean();
		bean.setCode(code);
		bean.setMsg(msg);
		return bean;
	}

}
