package com.java.in;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.Gson;
@WebServlet("/orders/*")
public class GetOrderServlet extends HttpServlet {

        private static final String DB_URL = "jdbc:mysql://localhost:3307/hotwax_assignment";
        private static final String DB_USER = "root";
        private static final String DB_PASSWORD = "@24March2003";

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String pathInfo = request.getPathInfo();
            String orderId = pathInfo != null ? pathInfo.substring(1) : null;

            if (orderId == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Order ID is required");
                return;
            }

            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                String sql = "SELECT * FROM orders WHERE order_id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, orderId);
                rs = stmt.executeQuery();

                if (rs.next()) {
                    // Fetch order details, customer info, and order items
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("Order details: " + rs.getString("order_date"));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("Order not found");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Error while retrieving order details: " + e.getMessage());
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}


