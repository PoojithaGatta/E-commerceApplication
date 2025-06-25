import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ViewCartServlet")
public class ViewCartServlet extends HttpServlet {

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

            // Query to fetch cart items
            String sql = "SELECT * FROM cart";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            // Start HTML structure
            out.println("<html><head>");
            out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css'>");
            out.println("<style>");
            out.println("body { background-color: #f8f9fa; font-family: Arial, sans-serif; }");
            out.println(".cart-container { max-width: 800px; margin: 0 auto; padding: 20px; }");
            out.println(".cart-item { background: #fff; border-radius: 10px; box-shadow: 0px 4px 8px rgba(0,0,0,0.1); margin-bottom: 20px; padding: 15px; display: flex; align-items: center; }");
            out.println(".cart-item img { width: 100px; height: 100px; object-fit: cover; border-radius: 8px; margin-right: 20px; }");
            out.println(".cart-item .details { flex: 1; }");
            out.println(".cart-item .details h5 { margin: 0; font-size: 1.2rem; color: #333; }");
            out.println(".cart-item .details p { margin: 5px 0; color: #555; }");
            out.println(".cart-item .price { font-size: 1.1rem; color: #28a745; font-weight: bold; }");
            out.println(".cart-item .remove-btn { background-color: #dc3545; color: #fff; border: none; padding: 8px 12px; border-radius: 5px; cursor: pointer; }");
            out.println(".cart-item .remove-btn:hover { background-color: #c82333; }");
            out.println(".total { text-align: right; font-size: 1.2rem; font-weight: bold; margin-top: 20px; }");
            out.println("</style>");
            out.println("</head><body>");

            out.println("<div class='cart-container'>");
            out.println("<h1 class='text-center mb-4'>Shopping Cart</h1>");

            double totalPrice = 0;
            boolean isEmpty = true;

            // Loop through the cart items and generate HTML
            while (rs.next()) {
                isEmpty = false;
                out.println("<div class='cart-item'>");
                out.println("<img src='" + rs.getString("image_url") + "' alt='" + rs.getString("name") + "'>");
                out.println("<div class='details'>");
                out.println("<h5>" + rs.getString("name") + "</h5>");
                out.println("<p>" + rs.getString("description") + "</p>");
                out.println("<p class='price'>₹" + rs.getDouble("price") + "</p>");
                out.println("</div>");
                out.println("<button class='remove-btn' onclick='removeFromCart(" + rs.getInt("id") + ")'>Remove</button>");
                out.println("</div>");

                // Calculate total price
                totalPrice += rs.getDouble("price");
            }

            if (isEmpty) {
                out.println("<p class='text-center'>Your cart is empty.</p>");
            } else {
                out.println("<div class='total'>Total: ₹" + totalPrice + "</div>");
            }

            out.println("</div>");

            // Add JavaScript for removing items
            out.println("<script>");
            out.println("function removeFromCart(productId) {");
            out.println("  if (confirm('Are you sure you want to remove this item?')) {");
            out.println("    fetch('/E-commerceApplication/RemoveCartServlet?id=' + productId, {");
            out.println("      method: 'POST'");
            out.println("    }).then(() => location.reload());");
            out.println("  }");
            out.println("}");
            out.println("</script>");

            // Close HTML tags
            out.println("</body></html>");

            // Close resources
            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            out.println("<div class='cart-container'>");
            out.println("<h1 class='text-center mb-4'>Shopping Cart</h1>");
            out.println("<p class='text-center text-danger'>Error: " + e.getMessage() + "</p>");
            out.println("</div>");
        }
    }
}