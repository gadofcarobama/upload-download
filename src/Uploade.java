import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "Uploade")
public class Uploade extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       //获取磁盘文件工厂
        DiskFileItemFactory diskFileItem=new DiskFileItemFactory();
        //获取核心上传类
        ServletFileUpload servletFileUpload=new ServletFileUpload(diskFileItem);
        servletFileUpload.setHeaderEncoding("utf-8");
        //解析request
        try {
            List<FileItem> list=servletFileUpload.parseRequest(request);
            //创建Resource对象
            Resource resource=new Resource();
            //遍历list集合
            for (FileItem fileTtem : list){
              //判断是否是普通输入项
                if (fileTtem.isFormField()){
                   String desc=fileTtem.getString("utf-8");
                   //将文件描述赋值给Resource
                    resource.setDesc(desc);
                }else{
                   //获取上传文件的名称 有可能是c:\a\b.txt
                   String  filename = fileTtem.getName();
                    int len=filename.indexOf("\\");
                   if (len==-1){
                       filename = filename.substring(len+1);
                   }
                     //防止重名生成随机的id
                   String uuid= UUID.randomUUID().toString();
                   String uuidname=uuid+"_"+filename;
                   //获取上传文件夹的绝对路径
                    String path= getServletContext().getRealPath("/uploadfile");
                    //分离路径
                    String url=UploadeUtils.getPath(uuidname);
                    File file=new File(path+url);
                    if (!file.exists()){
                        //错误示范file.mkdir(),因为这里为多级目录，所以不能用创建创建单级目录的方法进行创建
                        //file.mkdirs创建父目录可以不存在，因为该方法可以创建父目录，而file.mkdir(0方法要求必须其父目录存在才可以创建
                        file.mkdirs();
                    }
                    //获取上传文件的流
                    InputStream in=fileTtem.getInputStream();
                    OutputStream out=new FileOutputStream(path+url+"/"+uuidname);
                    //流对接
                    int lens=0;
                    byte[] b=new byte[1024];
                 while((len=in.read(b))!=-1){
                     out.write(b);
                 }
                 in.close();
                 out.close();

                    //设置上传的信息
                    resource.setUuidname(uuidname);
                    resource.setRealname(filename);
                    resource.setSavepath(path+url);
                }
            }
             //将文件设置进数据库中
            QueryRunner queryRunner=new QueryRunner(MyJDBCUtils.getDataSource());
            Object[] obj={resource.getUuidname(),resource.getRealname(),resource.getDesc(),resource.getSavepath()};
            queryRunner.update("insert into resource values(null,?,?,?,?)",obj);
            response.getWriter().write("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       doPost(request,response);
    }
}
