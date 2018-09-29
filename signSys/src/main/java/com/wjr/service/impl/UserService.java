package com.wjr.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wjr.dao.SignDataMapper;
import com.wjr.dao.UserMapper;
import com.wjr.pojo.SignData;
import com.wjr.pojo.User;
import com.wjr.service.IUserService;
import com.wjr.util.MD5Utils;
import com.wjr.vo.PageBean;


@Service
public class UserService implements IUserService{

	@Autowired
	private UserMapper userDao;
	
	@Autowired
	private SignDataMapper signDataDao;
	
	/*@Override
	public PageBean<User> findUserByPage(int page, int limit, String no, int flag) {
		if (no != null && !no.equals("")) {
			no = "%" + no + "%";
		} else {
			no=null;
		}
		
		PageBean<User> pageInfo = new PageBean<>();
		
		pageInfo.setPageSize(limit);
		
		pageInfo.setCurrentPage(page);
		
		Map<String, Object> fmap = new HashMap<>();
		fmap.put("flag", flag);
		fmap.put("no", no);
		// 获取表中的记录总数
		int count = userDao.count(fmap);
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
		map.put("no", no);
		map.put("flag", flag);
		List<User> list = userDao.findByIndexAndSize(map);
		pageInfo.setPageInfos(list);
		
		return pageInfo;
	}


	@Override
	public void delete(int id, String no) {
	
		try {
			//判断能否删除
			JsonBean bean = deleteJudge(id, no);
			if(bean.getCode() != 1) {
				throw new RuntimeException((String) bean.getMsg());
			}
			
			userDao.delete(id);
			userRoleDao.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public User findById(int id) {
		User user = null;
		
		try {
			user = userDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}

	@Override
	public JsonBean deleteJudge(int id, String no) {
		User user = null;
		List<UserRole> userRoleList = null;
		
		
		try {
			//查询用户是否存在
			user = userDao.findById(id);
			if (user == null) {
				return JsonUtil.JsonBeanS(0, "用户不存在，删除失败");
			}
			//判断删除的是否是自己
			if (user.getNo().equals(no)) {
				return JsonUtil.JsonBeanS(0, "不能删除自己");
			}
			//判读是否是管理员
			user = userDao.findByNo(no);
			List<UserRole> userRole = userRoleDao.findByUserId(user.getId());
			int i = 0;
			for (UserRole userRole2 : userRole) {
				if (userRole2.getRid() == 1) {
					i = 1;
				}
			}
			if (i != 1) {
				return JsonUtil.JsonBeanS(0, "抱歉，你不是管理员");
			}
			//判断删除的是否管理员
			List<UserRole> userRole1 = userRoleDao.findByUserId(id);
			int a = 0;
			for (UserRole userRole2 : userRole1) {
				if (userRole2.getRid() == 1) {
					a = 1;
				}
			}
			if (a == 1) {
				return JsonUtil.JsonBeanS(0, "不能删除管理员");
			}
			//根据用户id查询权限
			userRoleList = userRoleDao.findByUserId(id);
			if (userRoleList.size() > 0) {
				return JsonUtil.JsonBeanS(0, "用户正在任职");
			}
			
			return JsonUtil.JsonBeanS(1, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
	}
*/

	@Override
	public PageBean<SignData> findUserByPage(int page, int limit, int uid) {
		Map<String, Object> map = new HashMap<>();
		
		PageBean<SignData> pageInfo = new PageBean<>();

		pageInfo.setPageSize(limit);

		pageInfo.setCurrentPage(page);

		map.put("uid", uid);
		// 获取表中的记录总数
		int count = signDataDao.count(map);
		pageInfo.setCount(count);
		// 计算总页数
		if(count % pageInfo.getPageSize() == 0){
			pageInfo.setTotalPage(count / pageInfo.getPageSize());
		}else{
			pageInfo.setTotalPage(count / pageInfo.getPageSize()+ 1);
		}
		// 根据页码计算分页查询时的开始索引
		int index = (page - 1) * pageInfo.getPageSize();
		map.put("index", index);
		map.put("size", pageInfo.getPageSize());
		List<SignData> list = signDataDao.findByIndexAndSize(map);
		pageInfo.setPageInfos(list);

		return pageInfo;
	}


	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}


	 //注册用户
	@Override
	public void add(User user) {
		try {
			
			String password = MD5Utils.getMd5(user.getPassword(), 1);
			
			user.setPassword(password);
			
			userDao.insertSelective(user);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	//根据用户名查找用户
	@Override
	public User findByNo(String no) {
		// TODO Auto-generated method stub
		
		User user = new User();
		try {
			user = userDao.findByNo(no);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		return user;
	}


	//查找签到次数
	@Override
	public int findSignInBy(int uid) {
		// TODO Auto-generated method stub
		
		int count = userDao.findSignIn(uid);
		
		return count;
	}


	//查询签退次数
	@Override
	public int findSignOutBy(int uid) {
		// TODO Auto-generated method stub
		int count = userDao.findSignOut(uid);
		
		return count;
	}


	@Override
	public void signIn(int uid) {
		// TODO Auto-generated method stub
		Date nowtime = new Date();
		
		
		/*sign.setFlag(flag);
		
		
		signStatusDao.signIn(uid);*/
		
	}


	//修改信息
	@Override
	public void update(int id, User user) {
		// TODO Auto-generated method stub
		
		if(user.getPassword() != null) {
			String password = MD5Utils.getMd5(user.getPassword(), 1);
			
			user.setPassword(password);
		}
		user.setId(id);
		
		try {
			userDao.updateByPrimaryKeySelective(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	//根据邮箱查找用户密码
	@Override
	public User findPassword(String email) {
		User user = new User();
		
		try {
			user = userDao.findPassword(email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return user;
	}
}
