import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updateProductServlet")
public class updateProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Get form data
        String productName = request.getParameter("productName");
        double existingPrice = Double.parseDouble(request.getParameter("Existing"));
        double newPrice = Double.parseDouble(request.getParameter("new"));

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.jdbc.Driver");

            // Database connection details
            String url = "jdbc:mysql://localhost:3306/login";
            String dbUsername = "root";
            String dbPassword = "";

            // Establish connection
            Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Query to update product price
            String sql = "UPDATE products SET price = ? WHERE name = ? AND price = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setDouble(1, newPrice);
            pst.setString(2, productName);
            pst.setDouble(3, existingPrice);

            // Execute the query
            int rowsUpdated = pst.executeUpdate();

            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Product Update Status</title>");
            out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
            out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css' rel='stylesheet'>");
            out.println("<style>");
            out.println(".card { transition: transform 0.2s, box-shadow 0.2s; }");
            out.println(".card:hover { transform: translateY(-5px); box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2); }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body class='bg-light'>");

            // Navbar
            out.println("<nav class='navbar navbar-expand-lg navbar-dark bg-dark'>");
            out.println("<div class='container-fluid'>");
            out.println("<a class='navbar-brand' href='#'>Product Management</a>");
            out.println("<button class='navbar-toggler' type='button' data-bs-toggle='collapse' data-bs-target='#navbarNav' aria-controls='navbarNav' aria-expanded='false' aria-label='Toggle navigation'>");
            out.println("<span class='navbar-toggler-icon'></span>");
            out.println("</button>");
            out.println("<div class='collapse navbar-collapse' id='navbarNav'>");
            out.println("<ul class='navbar-nav ms-auto'>");
            out.println("<li class='nav-item'>");
            out.println("<a class='nav-link' href='productManagement.html'>View All Products</a>");
            out.println("</li>");
            out.println("</ul>");
            out.println("</div>");
            out.println("</div>");
            out.println("</nav>");

            // Success/Failure Card
            out.println("<div class='container mt-5'>");
            out.println("<div class='card mx-auto p-4 shadow' style='max-width: 500px;'>");
            out.println("<div class='card-body text-center'>");

            if (rowsUpdated > 0) {
                out.println("<i class='bi bi-check-circle-fill text-success' style='font-size: 3rem;'></i>");
                out.println("<h2 class='card-title mt-3'>Price Updated Successfully</h2>");
                out.println("<div class='text-start mt-4'>");
                out.println("<p><strong><i class='bi bi-box-seam'></i> Product Name:</strong> " + productName + "</p>");
                out.println("<p><strong><i class='bi bi-currency-rupee'></i> Old Price:</strong> ₹" + existingPrice + "</p>");
                out.println("<p><strong><i class='bi bi-currency-rupee'></i> New Price:</strong> ₹" + newPrice + "</p>");
                out.println("</div>");
            } else {
                out.println("<i class='bi bi-exclamation-circle-fill text-danger' style='font-size: 3rem;'></i>");
                out.println("<h2 class='card-title mt-3'>Update Failed</h2>");
                out.println("<p class='card-text'>No product found with the given name and price.</p>");
            }

            out.println("<a href='productManagement.html' class='btn btn-primary mt-4'><i class='bi bi-list-ul'></i> View All Products</a>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");

            out.println("<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js'></script>");
            out.println("</body>");
            out.println("</html>");

            // Close resources
            pst.close();
            con.close();
        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
            e.printStackTrace();
        }
    }
}