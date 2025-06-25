import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.jdbc.Driver");

            // Database connection details
            String url = "jdbc:mysql://localhost/login";
            String dbUsername = "root";
            String dbPassword = "";

            // Establish connection
            Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Get user input
            String un1 = request.getParameter("Username");
            String pw1 = request.getParameter("Password");

            // Query to check if the user exists
            String sql = "SELECT * FROM users WHERE username='" + un1 + "' AND password='" + pw1 + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

             if (rs.next()) {
            // Login successful - Store username in session
            HttpSession session = request.getSession();
            session.setAttribute("username", un1);

            // Redirect to dashboard
            response.sendRedirect("dashboard.html");
        } else {
            // Login failed
            response.sendRedirect("index.html?error=1");
        }

            // Close resources
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }
}