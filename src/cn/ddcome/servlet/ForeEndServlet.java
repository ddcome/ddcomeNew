package cn.ddcome.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.qq.connect.QQConnectException;
import com.qq.connect.oauth.Oauth;

import cn.ddcome.service.ArticlesService;
import cn.ddcome.service.BooksService;
import cn.ddcome.service.CommentsService;
import cn.ddcome.service.EmailIdentifyCodeService;
import cn.ddcome.service.SpeakersService;
import cn.ddcome.service.VisitIPCollectionService;
import cn.ddcome.service.WordsService;
import cn.ddcome.test.TestGetIP;
import cn.ddcome.util.AddressTaoBaoUtil;
import cn.ddcome.util.AddressUtils;
import cn.ddcome.util.SendMail;
import net.sf.json.JSONObject;

@WebServlet(urlPatterns = {"/goodLuckToDd/forend"}, asyncSupported = true)
public class ForeEndServlet extends HttpServlet implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ForeEndServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = resp.getWriter();
		//获取路径
		String action = JSONObject.fromObject(req.getParameter("params")).getString("action");
		
		System.out.println(action);
		
		//区分后端的action
		switch (action) {
		case "record-client-ip":
			VisitIPCollectionService.getInstance().init(req);
			VisitIPCollectionService.getInstance().insert();
			pw.write("");
			break;
		case "search":
			ArticlesService.getInstance().init(req);
			JSONObject articlesObj = ArticlesService.getInstance().search();
			pw.write(articlesObj.getString("data"));
			break;
		case "get-user-info":
			SpeakersService.getInstance().init(req);
			JSONObject userObj = SpeakersService.getInstance().selectOneByOpenId();
			pw.write(userObj.getString("data"));
			break;
		case "identify-code-mail":
			EmailIdentifyCodeService.getInstance().init(req);
			JSONObject identifyCode = EmailIdentifyCodeService.getInstance().select(resp);
			pw.write(identifyCode.getJSONArray("data").toString());
			break;
		case "send-mail":
			//发送验证邮件
			SendMail.sendMail();
			pw.write("");
			break;
		case "latest-data":
			int limitNum = JSONObject.fromObject(req.getParameter("params")).getJSONObject("data").getInt("limitNum");
			JSONObject latestArticles = ArticlesService.getInstance().select(limitNum);
			pw.write(latestArticles.getString("data"));
			break;
		case "all-books":
			JSONObject booksObj = BooksService.getInstance().select();
			pw.write(booksObj.getString("data"));
			break;
		case "add-reply":
			CommentsService.getInstance().init(req);
			JSONObject commentsObj = CommentsService.getInstance().insert();
			pw.write("[]");
			break;
		case "add-QQ-reply":
			CommentsService.getInstance().init(req);
			CommentsService.getInstance().insert();
			pw.write("[]");
			break;
		case "add-Email-reply":
			CommentsService.getInstance().init(req);
			CommentsService.getInstance().insert();
			pw.write("[]");
			break;
		case "all-words":
			JSONObject wordsObj = WordsService.getInstance().select();
			pw.write(wordsObj.getString("data"));
			break;
		case "all-data":
			JSONObject aticlesObj = ArticlesService.getInstance().select();
			pw.write(aticlesObj.getString("data"));
			break;
		case "one-pdf":
			ArticlesService.getInstance().init(req);
			JSONObject onePdf = ArticlesService.getInstance().selectOnePdf();
			pw.write(onePdf.getString("data"));
			break;
		case "one-text":
			ArticlesService.getInstance().init(req);
			JSONObject oneText = ArticlesService.getInstance().selectOneText();
			pw.write(oneText.getString("data"));
			break;
		case "one-md":
			ArticlesService.getInstance().init(req);
			JSONObject onemd = ArticlesService.getInstance().selectOneMarkdown();
			pw.write(onemd.getString("data"));
			break;
		default:
			System.out.println("default");
			break;
		}
		pw.flush();
		pw.close();
	}
	
}
