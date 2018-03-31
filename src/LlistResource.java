import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "LlistResource")
public class LlistResource extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QueryRunner queryRunner=new QueryRunner(MyJDBCUtils.getDataSource());
        try {
            List<Resource> list= queryRunner.query("select * from resource",new BeanListHandler<Resource>(Resource.class));
            request.setAttribute("list",list);
            request.getRequestDispatcher("/list.jsp").forward(request,response);
        } catch (SQLException e) {
           throw new RuntimeException("查询失败");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          doPost(request,response);
    }
}
