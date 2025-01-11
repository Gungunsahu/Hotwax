package com.java.in;

	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;

	public class OrderHeaderDAO {
	    public int createOrder(OrderHeader order) throws Exception {
	        String sql = "INSERT INTO Order_Header (order_date, customer_id, shipping_contact_mech_id, billing_contact_mech_id) VALUES (?, ?, ?, ?)";
	        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
	            ps.setString(1, order.getOrderDate());
	            ps.setInt(2, order.getCustomerId());
	            ps.setInt(3, order.getShippingContactMechId());
	            ps.setInt(4, order.getBillingContactMechId());
	            ps.executeUpdate();

	            ResultSet rs = ps.getGeneratedKeys();
	            if (rs.next()) {
	                return rs.getInt(1); // Return generated order ID
	            }
	        }
	        return 0;
	    }

	    public OrderHeader getOrderById(int orderId) throws Exception {
	        String sql = "SELECT * FROM Order_Header WHERE order_id = ?";
	        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setInt(1, orderId);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                OrderHeader order = new OrderHeader();
	                order.setOrderId(rs.getInt("order_id"));
	                order.setOrderDate(rs.getString("order_date"));
	                order.setCustomerId(rs.getInt("customer_id"));
	                order.setShippingContactMechId(rs.getInt("shipping_contact_mech_id"));
	                order.setBillingContactMechId(rs.getInt("billing_contact_mech_id"));
	                return order;
	            }
	        }
	        return null;
	    }
	}


