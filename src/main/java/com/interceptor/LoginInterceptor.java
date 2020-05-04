package com.interceptor;

import com.model.user.UserExt;
import com.result.Result;
import com.result.ResultStatus;
import com.utils.HttpUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录拦截器
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("拦截到一个用户操作请求");

		HttpSession session=request.getSession();

		UserExt loginUser = (UserExt) session.getAttribute("loginUser");
		if(loginUser == null){
//			String path = request.getContextPath();
			request.setAttribute("error", "未登陆，请先登陆");

			HttpUtil.returnJson(response, Result.fail(0, ResultStatus.ERROR_No_Login));
			return false;
		}

		System.out.println(request.getRequestURL());
		return true;
	}
}
