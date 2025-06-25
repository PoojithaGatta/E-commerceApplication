/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pooji
 */
public class deleteProductServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String URL = "jdbc:mysql://localhost/login";
            String user = "root";
            String password = "";
            Connection con = DriverManager.getConnection(URL, user, password);
            String p1 = request.getParameter("productName");
            String sq1 = "delete from products where name='" + p1 + "'";
            Statement st = con.createStatement();
            int n = st.executeUpdate(sq1);

            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Product Deleted Successfully</title>");
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

            // Success Card
            out.println("<div class='container mt-5'>");
            out.println("<div class='card mx-auto p-4 shadow' style='max-width: 500px;'>");
            out.println("<div class='card-body text-center'>");
            out.println("<i class='bi bi-trash-fill text-danger' style='font-size: 3rem;'></i>");
            out.println("<h2 class='card-title mt-3'>Product Deleted Successfully</h2>");
            out.println("<div class='text-start mt-4'>");
            out.println("<p><strong><i class='bi bi-box-seam'></i> Product Name:</strong> " + p1 + "</p>");
            out.println("</div>");
            out.println("<a href='productManagement.html' class='btn btn-primary mt-4'><i class='bi bi-list-ul'></i> View All Products</a>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");

            out.println("<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js'></script>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }
}