import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class productManagementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.jdbc.Driver");

            // Database connection details
            String url = "jdbc:mysql://localhost:3306/login";
            String dbUsername = "root";
            String dbPassword = "";

            // Establish connection
            Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Query to fetch products
            String sql = "SELECT * FROM products";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            // Start HTML structure
            out.println("<html><head>");
            out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css'>");
            out.println("<style>");
            out.println("<h1>Product Management</h1>");
            out.println("body { background-color: #E6E6FA; font-family: Arial, sans-serif; }");
            out.println(".product-container { display: flex; flex-wrap: wrap; gap: 20px; justify-content: center; padding: 20px; }");
            out.println(".product-card { background: #fff; border-radius: 10px; box-shadow: 0px 4px 8px rgba(0,0,0,0.2); transition: 0.3s; width: 300px; }");
            out.println(".product-card:hover { transform: translateY(-5px); }");
            out.println(".product-card img { width: 100%; height: 200px; object-fit: cover; border-top-left-radius: 10px; border-top-right-radius: 10px; }");
            out.println(".card-body { padding: 15px; text-align: center; }");
            out.println(".card-title { font-weight: bold; font-size: 1.2rem; color: #333; }");
            out.println(".card-text { color: #555; font-size: 0.95rem; }");
            out.println(".price { font-size: 1rem; color: #28a745; font-weight: bold; }");
            out.println("</style>");
            out.println("</head><body>");
            out.println("<div class='product-container'>");

            // Loop through the products and generate HTML
            while (rs.next()) {
                out.println("<div class='product-card'>");
                out.println("<img src='" + rs.getString("image_url") + "' alt='" + rs.getString("name") + "'>");
                out.println("<div class='card-body'>");
                out.println("<h5 class='card-title'><strong>" + rs.getString("name") + "</strong></h5>");
                out.println("<p class='price'>₹" + rs.getDouble("price") + "</p>");
                out.println("<p class='card-text'>" + rs.getString("description") + "</p>");

                // Add a form with hidden fields and an "Add to Cart" button
                out.println("<form action='AddCartServlet' method='post'>");
                
                out.println("<input type='hidden' name='product_name' value='" + rs.getString("name") + "'>");
                out.println("<input type='hidden' name='product_price' value='" + rs.getDouble("price") + "'>");
                out.println("<input type='hidden' name='product_description' value='" + rs.getString("description") + "'>");
                out.println("<input type='hidden' name='product_image_url' value='" + rs.getString("image_url") + "'>");
                out.println("<button type='submit' class='btn btn-primary'>Add to Cart</button>");
                out.println("</form>");

                out.println("</div>");
                out.println("</div>");
            }

            // Close HTML tags
            out.println("</div>");
            out.println("</body></html>");

            // Close resources
            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
