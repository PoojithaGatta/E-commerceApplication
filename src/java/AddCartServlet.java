import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            // Retrieve form data from hidden fields
            String productName = request.getParameter("product_name");
            double productPrice = Double.parseDouble(request.getParameter("product_price"));
            String productDescription = request.getParameter("product_description");
            String productImageUrl = request.getParameter("product_image_url");

            // Debugging: Print received data
            System.out.println("Product Name: " + productName);
            System.out.println("Product Price: " + productPrice);
            System.out.println("Product Description: " + productDescription);
            System.out.println("Product Image URL: " + productImageUrl);

            // Load MySQL JDBC Driver
            Class.forName("com.mysql.jdbc.Driver");

            // Database connection details
            String url = "jdbc:mysql://localhost:3306/login";
            String dbUsername = "root";
            String dbPassword = ""; // Use your actual password

            // Establish connection
            con = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Insert product into the cart table (ignoring product_id)
            String sql = "INSERT INTO cart (name, price, description, image_url) VALUES (?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, productName);
            pstmt.setDouble(2, productPrice);
            pstmt.setString(3, productDescription);
            pstmt.setString(4, productImageUrl);

            // Execute the query
            int rowsInserted = pstmt.executeUpdate();

            // Display success message
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Product Added to Cart</title>");
            out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
            out.println("<style>");
            out.println("body { background-color: #f8f9fa; }");
            out.println(".container { margin-top: 50px; }");
            out.println(".card { max-width: 500px; margin: 0 auto; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            out.println("<div class='container'>");
            out.println("<div class='card shadow'>");
            out.println("<div class='card-body text-center'>");
            out.println("<h2 class='card-title text-success'><i class='bi bi-check-circle-fill'></i> Product Added to Cart</h2>");
            out.println("<p class='card-text'>The product has been successfully added to your cart.</p>");
            out.println("<a href='productManagement.html' class='btn btn-primary'>Continue Shopping</a>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");

            out.println("</body>");
            out.println("</html>");

        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();

            // Display error message
            out.println("<div class='alert alert-danger'>An error occurred while adding the product to the cart. Please try again later.</div>");
        } finally {
            // Close resources
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}