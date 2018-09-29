package com.wjr.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wjr.pojo.SignData;
import com.wjr.pojo.User;
import com.wjr.service.IUserService;
import com.wjr.util.JsonUtil;
import com.wjr.util.MD5Utils;
import com.wjr.vo.JsonBean;
import com.wjr.vo.PageBean;


@Controller
public class UserController {

	@Autowired
	private IUserService userService;
	
	/*@Autowired
	private ILoginlogService loginlogService;*/
	
	@RequestMapping("/login")
	public String login(String no, String password, HttpSession session, HttpServletRequest request){
		
		UsernamePasswordToken token = new UsernamePasswordToken(no, MD5Utils.getMd5(password, 1));
		// 设置 记住我=true
//		token.setRememberMe(true);
		Subject subject = SecurityUtils.getSubject();

		try {
			subject.login(token);
			session.setAttribute("no", no);
			User user = userService.findByNo(no);
			
			session.setAttribute("uid", user.getId());
			/*IpGet ipget = new IpGet();
			//获取ip地址
			String ip = ipget.getIpAddr(request);
			//添加登录日志
			loginlogService.add(ip, no);*/
			return "redirect:index.html";
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return "redirect:login.html";
		}
		
	}
	
	
	/**
	 * 分页查询用户
	 * @param page 页码
	 * @param limit 每页数据
	 * @return 返回用户
	 */
	@RequestMapping("/loginloglist")
	@ResponseBody
	public Map<String, Object> findAllUser(int page, int limit, HttpSession session) {
		
		
		int uid = (int) session.getAttribute("uid");
		
		PageBean<SignData> pageInfo = null;
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> datamap = null;
		try {
			//查找分页数据
			pageInfo = userService.findUserByPage(page, limit, uid);
			List<SignData> list = pageInfo.getPageInfos();
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			for (SignData user : list) {
				datamap = new HashMap<String, Object>();
				datamap.put("flag", user.getFlag());
				datamap.put("sign", user.getSign());
				datamap.put("createdate", user.getCreatedate());
				
				data.add(datamap);
			}
			//设置状态（查找成功）
			map.put("code", 0);
			//设置提示信息
			map.put("msg", "");
			//设置用户总数
			map.put("count", pageInfo.getCount());
			//设置用户信息
			map.put("data", data);
			//返回分页信息
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			//返回非成功状态
			map.put("code", 1);
			return map;
		}
	}
	@RequestMapping("/sign")
	@ResponseBody
	public JsonBean add() {
				return JsonUtil.JsonBeanS(1, null);
		}
		
	
	/**
	 * 员工图片上传
	 * @param file
	 * @return
	 */
	@RequestMapping("/photoupload.do")
	@ResponseBody
	public JsonBean uploadImg(MultipartFile file) {
		// 获取上传的文件的文件名
		String fileName = file.getOriginalFilename();
		
		//文件保存目录
		String path = "E:\\Eclipse java\\signSys\\src\\main\\webapp\\upload";
		File pathFile = new File(path);
		
		if(!pathFile.exists()) {
			pathFile.mkdirs();
		}
		File file1 = new File(path, fileName);
		
		try {
			file.transferTo(file1);
			User user = new User();
			user.setHeadphoto(fileName);
			return JsonUtil.JsonBeanS(1000, user);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, "图片上传失败");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, "图片上传失败");
		}
		
	}
	/**
	 * 删除用户
	 * @param id
	 * @param session
	 * @return
	 *//*
	@RequestMapping("/userdel.do")
	@ResponseBody
	public JsonBean delete(int id, HttpSession session) {
		
		String no = (String) session.getAttribute("no");
		try {
			JsonBean bean = userService.deleteJudge(id, no);
			if(bean.getCode() != 1) {
				return JsonUtil.JsonBeanS(0, bean.getMsg());
			}
			
			userService.delete(id, no);
			return JsonUtil.JsonBeanS(1000, null);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
		
	}*/
	@RequestMapping("/useradd.do")
	@ResponseBody
	public JsonBean add(User user, HttpSession session) {
		
		try {
			
			user.setFlag(1);
			userService.add(user);
			return JsonUtil.JsonBeanS(1, null);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
		
	}
	@RequestMapping("/signin.do")
	@ResponseBody
	public JsonBean signin(User user, HttpSession session) {
		
		try {
			
			user.setFlag(1);
			userService.add(user);
			return JsonUtil.JsonBeanS(1, null);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
		
	}
	@RequestMapping("/findsignin.do")
	@ResponseBody
	public JsonBean findSignIn(HttpSession session) {
		
		int uid = (int) session.getAttribute("uid");
		
		try {
			int count = userService.findSignInBy(uid);
			return JsonUtil.JsonBeanS(1, count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
		
		
	}
	@RequestMapping("/findsignout.do")
	@ResponseBody
	public JsonBean findSignOut(HttpSession session) {
		
		int uid = (int) session.getAttribute("uid");
		
		try {
			int count = userService.findSignOutBy(uid);
			return JsonUtil.JsonBeanS(1, count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
		
		
	}
	@RequestMapping("/signinorout.do")
	@ResponseBody
	public JsonBean SignIn(HttpSession session) {
		
		int uid = (int) session.getAttribute("uid");
		
		try {
			userService.signIn(uid);
			return JsonUtil.JsonBeanS(1, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
		
		
	}
	@RequestMapping("/update.do")
	@ResponseBody
	public JsonBean updateUser(User user, HttpSession session) {
		
		int id = (int) session.getAttribute("uid");
		
		try {
			userService.update(id, user);
			return JsonUtil.JsonBeanS(1, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
		
		
	}
	@RequestMapping("/findpassword")
	@ResponseBody
	public JsonBean findPassword(String email) {
		User user = new User();
		
		try {
			user = userService.findPassword(email);
			return JsonUtil.JsonBeanS(1, user.getPassword());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonUtil.JsonBeanS(0, e.getMessage());
		}
		
		
		
	}
		
}
