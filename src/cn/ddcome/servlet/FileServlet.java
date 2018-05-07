package cn.ddcome.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import cn.ddcome.service.ArticlesService;

@WebServlet(urlPatterns = "/goodLuckToDd/file", asyncSupported = true)
public class FileServlet extends HttpServlet implements Serializable {
    private static Logger logger = Logger.getLogger(FileServlet.class);
    // �����ļ���Ŀ¼
    private static String PATH_FOLDER = "/";
    // �����ʱ�ļ���Ŀ¼
    private static String TEMP_FOLDER = "/";

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletCtx = config.getServletContext();
        // ��ʼ��·��
        // �����ļ���Ŀ¼
        PATH_FOLDER = servletCtx.getRealPath("/");
        System.out.println(PATH_FOLDER);
        // �����ʱ�ļ���Ŀ¼,���xxx.tmp�ļ���Ŀ¼
        TEMP_FOLDER = servletCtx.getRealPath("/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        run(req,resp);
    }

    public void run(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        // ��ô����ļ���Ŀ����
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // ���û�����������õĻ����ϴ���� �ļ� ��ռ�� �ܶ��ڴ棬
        // ������ʱ��ŵ� �洢�� , ����洢�ң����Ժ� ���մ洢�ļ� ��Ŀ¼��ͬ
        /**
         * ԭ�� �����ȴ浽 ��ʱ�洢�ң�Ȼ��������д�� ��ӦĿ¼��Ӳ���ϣ�
         * ������˵ ���ϴ�һ���ļ�ʱ����ʵ���ϴ������ݣ���һ������ .tem��ʽ��
         * Ȼ���ٽ�������д��,��ӦĿ¼��Ӳ����
         */
        factory.setRepository(new File(TEMP_FOLDER));
        // ���� ����Ĵ�С�����ϴ��ļ������������û���ʱ��ֱ�ӷŵ� ��ʱ�洢��
        factory.setSizeThreshold(1024 * 1024);

        // ��ˮƽ��API�ļ��ϴ�����
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            // �ύ��������Ϣ�������list����
            // ����ζ�ſ����ϴ�����ļ�
            // ��������֯����
            List<FileItem> list = upload.parseRequest(request);
            // ��ȡ�ϴ����ļ�
            FileItem item = getUploadFileItem(list);
            // ��ȡ�ļ���
            String filename = getUploadFileName(item);
            // �������ļ���
            String saveName = new Date().getTime() + filename.substring(filename.lastIndexOf("."));
            // �����ͼƬ�����������·��
            String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + "/upload/" + saveName;

            //PATH_FOLDER = this.getServletContext().getRealPath("/WEB-INF/upload");

            System.out.println("���Ŀ¼:" + PATH_FOLDER);
            System.out.println("�ļ���:" + filename);
            System.out.println("���������·��:" + filePath);

            // ����д��������
            item.write(new File(PATH_FOLDER, saveName)); // �������ṩ��

            PrintWriter writer = response.getWriter();

            //��ʼִ�н���Ӧ�����ݲ��뵽���ݿ�
            ArticlesService.getInstance().insertPdf(filePath, filename,item.getSize());

            writer.print("{");
            writer.print("msg:\"�ļ���С:" + item.getSize() + ",�ļ���:" + filename + "\"");
            writer.print(",picUrl:\"" + filePath + "\"");
            writer.print("}");

            writer.close();

        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private FileItem getUploadFileItem(List<FileItem> list) {
        for (FileItem fileItem : list) {
            if (!fileItem.isFormField()) {
                return fileItem;
            }
        }
        return null;
    }

    private String getUploadFileName(FileItem item) {
        // ��ȡ·����
        String value = item.getName();
        // ���������һ����б��
        int start = value.lastIndexOf("/");
        // ��ȡ �ϴ��ļ��� �ַ������֣���1�� ȥ����б�ܣ�
        String filename = value.substring(start + 1);

        return filename;
    }

}
