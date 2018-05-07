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
		 * ʹ��Cookie�ķ�ʽ
		 */
		Cookie cookieDdcome_openid = null; 
		Cookie cookieDdcome_access_token = null;
		Cookie cookieDdcome_token_expirein = null;
		/*
		 * ʹ��seesion�ķ�ʽ
		 */
		// �������request�󶨵�session�������û�У��򴴽�һ��
		HttpSession session = request.getSession();
		
		try {
			AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);

			String accessToken = null, openID = null;
			long tokenExpireIn = 0L;

			
			
			if (accessTokenObj.getAccessToken().equals("")) {
				// ���ǵ���վ��CSRF�����˻����û�ȡ������Ȩ
				// ��һЩ����ͳ�ƹ���
				System.out.print("û�л�ȡ����Ӧ����");
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
				 * ʹ��session�ķ�ʽ
				 */
//				session.setAttribute("ddcome_access_token",accessToken);
//				session.setAttribute("ddcome_token_expirein",String.valueOf(tokenExpireIn));
				
				//���û�ȡ����accessToken ȥ��ȡ��ǰ�õ�openid -------- start
				OpenID openIDObj = new OpenID(accessToken);
				openID = openIDObj.getUserOpenID();
				//��¼�û�����
				qqUserData.put("openID", openID);
				cookieDdcome_openid = new Cookie("ddcome_openid",openID);
				cookieDdcome_openid.setPath("/");
				//cookieDdcome_openid.setMaxAge(3600);
				
				/*
				 * ʹ��session�ķ�ʽ
				 */
				//session.setAttribute("ddcome_openid",openID);
				
				// ���û�ȡ����accessToken ȥ��ȡ��ǰ�û���openid --------- end

				//���û�ȡ����accessToken,openid ȥ��ȡ�û���Qzone���ǳƵ���Ϣ
				UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
				UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
				
				if (userInfoBean.getRet() == 0) {
					qqUserData.put("name", userInfoBean.getNickname());
					qqUserData.put("sex", userInfoBean.getGender().equals("��")?"male":"female");
					qqUserData.put("yellowRank", userInfoBean.getLevel());
					qqUserData.put("yellowVip", userInfoBean.isYellowYearVip());
					qqUserData.put("vip", userInfoBean.isVip());
					qqUserData.put("imageUrl", userInfoBean.getAvatar().getAvatarURL100());
					
				} else {
					System.out.println("�ܱ�Ǹ������û����ȷ��ȡ��������Ϣ��ԭ���ǣ� " + userInfoBean.getMsg());
				}
				
				/*
				 * ���û�ȡ����accessToken,openid ȥ��ȡ�û���΢�����ǳƵ���Ϣ
				 */
				com.qq.connect.api.weibo.UserInfo weiboUserInfo = new com.qq.connect.api.weibo.UserInfo(accessToken,
						openID);
				com.qq.connect.javabeans.weibo.UserInfoBean weiboUserInfoBean = weiboUserInfo.getUserInfo();
				if (weiboUserInfoBean.getRet() == 0) {
					// ��ȡ�û���΢��ͷ��
					qqUserData.put("weiboImageUrl", weiboUserInfoBean.getAvatar().getAvatarURL100());
					
					// ��ȡ�û���������Ϣ
					qqUserData.put("weiboBirthday", weiboUserInfoBean.getBirthday().getYear()+"-"+weiboUserInfoBean.getBirthday().getMonth()+"-"+weiboUserInfoBean.getBirthday().getDay());
					
					//��ȡ���ڵ�
					qqUserData.put("weiboLocation",weiboUserInfoBean.getCountryCode()+weiboUserInfoBean.getCityCode()+weiboUserInfoBean.getProvinceCode()+weiboUserInfoBean.getLocation());

				} else {
					logger.info("�ܱ�Ǹ������û����ȷ��ȡ��������Ϣ��ԭ���ǣ� " + weiboUserInfoBean.getMsg());
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
		//������û��Ļ�����Ϣ�Ѿ���ȡ����������Ҫ��¼���
		SpeakersService.getInstance().insertQQUser(qqUserData);
		
		/*
		 * �ض����ҳ��Я��authority��Ŀ����Ϊ�ˣ�
		 * 1.Ϊǰ�˵��û��������ṩƾ֤
		 * 2.�洢�û���Ϣʹ��session
		 */
		//response.sendRedirect("/ddcomeNew/docs.html?authority=true");
		/*
		 * ���سɹ���ҳ����Ҫ��һ�¹�����
		 * 1.ˢ�¸�������
		 * 2.�ر��Լ�
		 */
		pw.print("<script>self.opener.location.reload();window.close();</script>");
		pw.flush();
		pw.close();
		
	}

}
