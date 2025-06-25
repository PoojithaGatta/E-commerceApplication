import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RemoveFromCartServlet")
public class RemoveCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("id"));

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.jdbc.Driver");

            // Database connection details
            String url = "jdbc:mysql://localhost:3306/login";
            String dbUsername = "root";
            String dbPassword = "";

            // Establish connection
            Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Query to remove item from cart
            String sql = "DELETE FROM cart WHERE id=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, productId);
            pst.executeUpdate();

            // Close resources
            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}