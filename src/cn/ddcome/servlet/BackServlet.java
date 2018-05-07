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

import cn.ddcome.service.ArticlesService;
import cn.ddcome.service.ArticlesTagsService;
import cn.ddcome.service.BooksService;
import cn.ddcome.service.CommentsService;
import cn.ddcome.service.ManagersService;
import cn.ddcome.service.SpeakersService;
import cn.ddcome.service.TagsService;
import cn.ddcome.service.WordsService;
import net.sf.json.JSONObject;

@WebServlet(urlPatterns ="/goodLuckToDd/back", asyncSupported = true)
public class BackServlet extends HttpServlet implements Serializable {
	private static Logger logger = Logger.getLogger(BackServlet.class);

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
		//��ȡ·��
		String action = JSONObject.fromObject(req.getParameter("params")).getString("action");
		
		System.out.println(action);
		//���ֺ�˵�action
		switch (action) {
		case "back-login":
			//��ʼ������
			ManagersService.getInstance().init(req);
			//ִ��
			JSONObject loginRes = ManagersService.getInstance().login();
			pw.write(loginRes.toString());
			break;
		case "insert-manager":
			//��ʼ������
			ManagersService.getInstance().init(req);
			//ִ��
			JSONObject managersRes = ManagersService.getInstance().insert();
			pw.write(managersRes.toString());
			break;
		case "select-user":
			JSONObject userObj = SpeakersService.getInstance().select();
			pw.write(userObj.getString("data"));
			break;
		case "select-manager":
			JSONObject managersObj = ManagersService.getInstance().select();
			pw.write(managersObj.getString("data"));
			break;
		case "select-words":
			JSONObject wordsObj = WordsService.getInstance().select();
			pw.write(wordsObj.getString("data"));
			break;
		case "select-books":
			JSONObject booksObj = BooksService.getInstance().select();
			pw.write(booksObj.getString("data"));
			break;
		case "select-tag":
			System.out.println("select-tag");
			JSONObject tagsObj = TagsService.getInstance().select();
			pw.write(tagsObj.getString("data"));
			break;
		case "unchecked-comments":
			System.out.println("select-tag");
			JSONObject commentsObj = CommentsService.getInstance().selectSome();
			pw.write(commentsObj.getString("data"));
			break;
		case "insert-words":
			//��ʼ������
			WordsService.getInstance().init(req);
			//ִ��
			JSONObject wordsRes = WordsService.getInstance().insert();
			pw.write(wordsRes.toString());
			break;
		case "insert-books":
			//��ʼ������
			BooksService.getInstance().init(req);
			//ִ��
			JSONObject booksRes = BooksService.getInstance().insert();
			pw.write(booksRes.toString());
			break;
		case "insert-tag":
			System.out.println("insert-tag");
			//��ʼ������
			TagsService.getInstance().init(req);
			//ִ��
			TagsService.getInstance().insert();
			pw.write("[]");
			break;
		case "insert-ArticlesTags":
			ArticlesTagsService.getInstance().insert();
			pw.write("[]");
			break;
		case "select-articles-pdf":
			JSONObject pdfObj = ArticlesService.getInstance().selectAllPDF();
			pw.write(pdfObj.getString("data"));
			break;
		case "select-articles-text":
			JSONObject textObj = ArticlesService.getInstance().selectAllDefault();
			pw.write(textObj.getString("data"));
			break;
		case "select-articles-md":
			JSONObject mdObj = ArticlesService.getInstance().selectAllDefault();
			pw.write(mdObj.getString("data"));
			break;
		case "insert-md":
			System.out.println("1");
			ArticlesService.getInstance().init(req);
			System.out.println("1");
			ArticlesService.getInstance().insertMd();
			pw.write("[]");
			break;
		case "insert-articles":
			//��ʼ������
			ArticlesService.getInstance().init(req);
			ArticlesService.getInstance().insert();
			pw.write("[]");
			break;
		default:
			System.out.println("-default");
			break;
		}
		pw.flush();
		pw.close();
	}
	
}
