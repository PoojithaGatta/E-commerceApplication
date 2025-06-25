import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpServlet extends HttpServlet {

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
            String username = "root";
            String password = "";

            // Establish connection
            Connection con = DriverManager.getConnection(url, username, password);

            // Get user input
            String un1 = request.getParameter("Username");
            String pw1 = request.getParameter("Password");
            String email=request.getParameter("email");

            // Insert user into the database
            String sql = "INSERT INTO users (username, password,email) VALUES ('" + un1 + "', '" + pw1 + "', '" + email +"')";
            Statement st = con.createStatement();
            int n = st.executeUpdate(sql);

            // Display success message
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Sign Up Success</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f9; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }");
            out.println(".success-container { background: #fff; padding: 2rem; border-radius: 8px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); text-align: center; max-width: 400px; width: 100%; }");
            out.println(".success-container h1 { color: #28a745; margin-bottom: 1rem; }");
            out.println(".success-container p { color: #333; font-size: 1.1rem; }");
            out.println(".success-container a { display: inline-block; margin-top: 1rem; padding: 0.75rem 1.5rem; background-color: #007bff; color: white; text-decoration: none; border-radius: 4px; }");
            out.println(".success-container a:hover { background-color: #0056b3; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='success-container'>");
            out.println("<h1>Sign Up Successful!</h1>");
            out.println("<p>Your account has been created successfully...!</p>");
            out.println("<a href='index.html'>Login</a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

            // Close resources
            st.close();
            con.close();
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }
}