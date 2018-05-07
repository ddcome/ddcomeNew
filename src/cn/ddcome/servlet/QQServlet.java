package cn.ddcome.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;

import cn.ddcome.service.SpeakersService;
import net.sf.json.JSONObject;

@WebServlet(urlPatterns = { "/goodLuckToDd/qqLogin" }, asyncSupported = true)
public class QQServlet extends HttpServlet implements Serializable {
	private static Logger logger = Logger.getLogger(QQServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		JSONObject qqUserData = new JSONObject();
		PrintWriter pw = response.getWriter();
		/*
		 * 使用Cookie的方式
		 */
		Cookie cookieDdcome_openid = null; 
		Cookie cookieDdcome_access_token = null;
		Cookie cookieDdcome_token_expirein = null;
		/*
		 * 使用seesion的方式
		 */
		// 返回这个request绑定的session对象，如果没有，则创建一个
		HttpSession session = request.getSession();
		
		try {
			AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);

			String accessToken = null, openID = null;
			long tokenExpireIn = 0L;

			
			
			if (accessTokenObj.getAccessToken().equals("")) {
				// 我们的网站被CSRF攻击了或者用户取消了授权
				// 做一些数据统计工作
				System.out.print("没有获取到响应参数");
			} else {
				accessToken = accessTokenObj.getAccessToken();
				tokenExpireIn = accessTokenObj.getExpireIn();

				cookieDdcome_access_token = new Cookie("ddcome_access_token",accessToken);
				cookieDdcome_token_expirein = new Cookie("ddcome_token_expirein",String.valueOf(tokenExpireIn));
				cookieDdcome_access_token.setPath("/");
				//cookieDdcome_access_token.setMaxAge(3600);
				cookieDdcome_token_expirein.setPath("/");
				//cookieDdcome_token_expirein.setMaxAge(3600);
				
				/*
				 * 使用session的方式
				 */
//				session.setAttribute("ddcome_access_token",accessToken);
//				session.setAttribute("ddcome_token_expirein",String.valueOf(tokenExpireIn));
				
				//利用获取到的accessToken 去获取当前用的openid -------- start
				OpenID openIDObj = new OpenID(accessToken);
				openID = openIDObj.getUserOpenID();
				//记录用户代号
				qqUserData.put("openID", openID);
				cookieDdcome_openid = new Cookie("ddcome_openid",openID);
				cookieDdcome_openid.setPath("/");
				//cookieDdcome_openid.setMaxAge(3600);
				
				/*
				 * 使用session的方式
				 */
				//session.setAttribute("ddcome_openid",openID);
				
				// 利用获取到的accessToken 去获取当前用户的openid --------- end

				//利用获取到的accessToken,openid 去获取用户在Qzone的昵称等信息
				UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
				UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
				
				if (userInfoBean.getRet() == 0) {
					qqUserData.put("name", userInfoBean.getNickname());
					qqUserData.put("sex", userInfoBean.getGender().equals("男")?"male":"female");
					qqUserData.put("yellowRank", userInfoBean.getLevel());
					qqUserData.put("yellowVip", userInfoBean.isYellowYearVip());
					qqUserData.put("vip", userInfoBean.isVip());
					qqUserData.put("imageUrl", userInfoBean.getAvatar().getAvatarURL100());
					
				} else {
					System.out.println("很抱歉，我们没能正确获取到您的信息，原因是： " + userInfoBean.getMsg());
				}
				
				/*
				 * 利用获取到的accessToken,openid 去获取用户在微博的昵称等信息
				 */
				com.qq.connect.api.weibo.UserInfo weiboUserInfo = new com.qq.connect.api.weibo.UserInfo(accessToken,
						openID);
				com.qq.connect.javabeans.weibo.UserInfoBean weiboUserInfoBean = weiboUserInfo.getUserInfo();
				if (weiboUserInfoBean.getRet() == 0) {
					// 获取用户的微博头像
					qqUserData.put("weiboImageUrl", weiboUserInfoBean.getAvatar().getAvatarURL100());
					
					// 获取用户的生日信息
					qqUserData.put("weiboBirthday", weiboUserInfoBean.getBirthday().getYear()+"-"+weiboUserInfoBean.getBirthday().getMonth()+"-"+weiboUserInfoBean.getBirthday().getDay());
					
					//获取所在地
					qqUserData.put("weiboLocation",weiboUserInfoBean.getCountryCode()+weiboUserInfoBean.getCityCode()+weiboUserInfoBean.getProvinceCode()+weiboUserInfoBean.getLocation());

				} else {
					logger.info("很抱歉，我们没能正确获取到您的信息，原因是： " + weiboUserInfoBean.getMsg());
				}
				/*
				 * end
				 */
			}
		} catch (QQConnectException e) {
			logger.error(e.getMessage());
		}
		
		System.out.println(cookieDdcome_openid.getName());
		System.out.println(cookieDdcome_openid.getValue());
		System.out.println(cookieDdcome_access_token.getName());
		System.out.println(cookieDdcome_token_expirein.getName());

		response.addCookie(cookieDdcome_openid);
		response.addCookie(cookieDdcome_access_token);
		response.addCookie(cookieDdcome_token_expirein);
		
		System.out.println(qqUserData.toString());
		//到这里，用户的基本信息已经获取，接下来需要记录入库
		SpeakersService.getInstance().insertQQUser(qqUserData);
		
		/*
		 * 重定向的页面携带authority的目的是为了：
		 * 1.为前端的用户版块加载提供凭证
		 * 2.存储用户信息使用session
		 */
		//response.sendRedirect("/ddcomeNew/docs.html?authority=true");
		/*
		 * 返回成功的页面需要做一下工作：
		 * 1.刷新父级窗口
		 * 2.关闭自己
		 */
		pw.print("<script>self.opener.location.reload();window.close();</script>");
		pw.flush();
		pw.close();
		
	}

}
