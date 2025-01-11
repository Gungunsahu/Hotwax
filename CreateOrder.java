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
import java.sql.SQLException;

import com.google.gson.Gson;
import dao.OrderHeaderDAO;
import model.OrderHeader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/orders")
public class CreateOrder extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3307/hotwax_assignment";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "@24March2003";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderDate = request.getParameter("order_date");
        String customerId = request.getParameter("customer_id");
        String shippingContactMechId = request.getParameter("shipping_contact_mech_id");
        String billingContactMechId = request.getParameter("billing_contact_mech_id");
        String orderItems = request.getParameter("order_items");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "INSERT INTO orders (order_date, customer_id, shipping_contact_mech_id, billing_contact_mech_id) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, orderDate);
            stmt.setString(2, customerId);
            stmt.setString(3, shippingContactMechId);
            stmt.setString(4, billingContactMechId);
            stmt.executeUpdate();

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("Order created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error while creating order: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
