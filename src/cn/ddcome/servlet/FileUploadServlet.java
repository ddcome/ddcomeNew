package cn.ddcome.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import cn.ddcome.service.ArticlesService;
import cn.ddcome.util.FileUtil;
import cn.ddcome.util.TimeUtil;
import net.sf.json.JSONObject;
/**
 * �ļ��ϴ���Servlet
 * 
 * @author DDCOME
 *
 */
@WebServlet(urlPatterns = "/goodLuckToDd/upload-file", asyncSupported = true)
public class FileUploadServlet extends HttpServlet implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(FileUploadServlet.class);
//	private static String UPLOAD_DIRECTORY = "D:/softwares/apache-tomcat-7.0.67-windows-x64/apache-tomcat-7.0.67/webapps/upload";
	//private static String UPLOAD_DIRECTORY = "G:/UPLOADS";
	private static String UPLOAD_DIRECTORY = "/home/ddcome/project/uploads";//��������Ĭ��·��
//	private static String filePath = "http://www.goodforyou.com.cn/ddcomeNew/Uploads";
	private static String filePath = "http://127.0.0.1:8080/ddcomeNew/Uploads/";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		
		//�ȼ���ļ����Ƿ���ڣ��������򴴽��ļ���
		FileUtil.createViableFolder(getServletContext().getRealPath("/")+"Uploads");
		
		//�ļ��Ĵ洢λ�ã�It`s very Important!
		UPLOAD_DIRECTORY = getServletContext().getRealPath("/Uploads");
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		
		//Process only if it is multipart content
		if(isMultipart) {
			//Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory();
			//Create a new File upload handle
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				//Parse the request
				List<FileItem> multiparts = upload.parseRequest(req);
				
				for(FileItem item : multiparts) {
					if(!item.isFormField()) {
						//ԭ���ļ���
						String filename = new File(item.getName()).getName();
						//�����Ĵ洢���ļ���
						String tempFileName = FileUtil.getRandomFileName()+filename.substring(filename.lastIndexOf("."),filename.length());
						//��ʼ�洢�ļ�
						item.write(new File(UPLOAD_DIRECTORY+File.separator+tempFileName));
						
						//��ʼִ�н���Ӧ�����ݲ��뵽���ݿ�
						ArticlesService.getInstance().insertPdf(filePath+"/"+tempFileName, filename,item.getSize());
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	
}
