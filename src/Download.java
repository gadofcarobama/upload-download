import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.SQLException;

@WebServlet(name = "Download")
public class Download extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     //得到文件的id
        String id=request.getParameter("id");
        //通过id查询数据库
        QueryRunner queryRunner=new QueryRunner();
        try {
            Resource resource=queryRunner.query("select * from resource where id=?",new BeanHandler<Resource>(Resource.class),id);
            //判断id能否查询到用户
            if (resource!=null){
                throw new RuntimeException("文件不存在");
            }else{
                String realname=resource.getRealname();
                String uuidname=resource.getUuidname();
                String savepath=resource.getSavepath();
                //得到当前浏览器类型
                String agent=request.getHeader("User-Agent");
                if (agent.contains("Firefox")){

                    //base64编码
                    realname = "=?UTF-8?B?"+
                            new BASE64Encoder().encode(realname.getBytes("utf-8"))+"?=";
                } else {//ie浏览器
                    realname = URLEncoder.encode(realname, "utf-8");

                }
                //实现下载操作
                //设置mime类型
                response.setContentType(getServletContext().getMimeType(realname));
                //设置头信息
                response.setHeader("Content-Disposition","attachment,filename="+realname);
                //从服务器获取输入流
                InputStream in=new FileInputStream("savepath"+"/"+uuidname);
                //使用输出流将文件写入到浏览器
                OutputStream out=response.getOutputStream();
                //流对接

                byte[] b=new byte[1024];
                int len=0;
                while ((len=in.read(b))!=-1){
                    out.write(b,0,len);
                }
                in.close();
            }
        } catch (SQLException e) {
           throw new RuntimeException("文件下载失败");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
