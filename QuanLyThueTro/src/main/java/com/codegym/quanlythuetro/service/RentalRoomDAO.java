package com.codegym.quanlythuetro.service;

import com.codegym.quanlythuetro.model.RentalRoom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentalRoomDAO implements ReadAllDAO<RentalRoom>, InsertDAO<RentalRoom>, DeleteDAO{
    public List<RentalRoom> searchRentalRooms(String keyword) throws SQLException {
        List<RentalRoom> list = new ArrayList<>();
        String sql = "SELECT r.*, p.method_name FROM rental_room r " +
                "JOIN payment_method p ON r.payment_method_id = p.payment_method_id " +
                "WHERE CAST(r.room_id AS CHAR) LIKE ? OR r.tenant_name LIKE ? OR r.phone_number LIKE ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            String likeKeyword = "%" + keyword + "%";
            ps.setString(1, likeKeyword);
            ps.setString(2, likeKeyword);
            ps.setString(3, likeKeyword);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RentalRoom room = new RentalRoom();
                room.setRoomId(rs.getInt("room_id"));
                room.setTenantName(rs.getString("tenant_name"));
                room.setPhoneNumber(rs.getString("phone_number"));
                room.setStartRentDate(rs.getDate("start_rent_date"));
                room.setPaymentMethodId(rs.getInt("payment_method_id"));
                room.setPaymentMethodName(rs.getString("method_name"));
                room.setNote(rs.getString("note"));
                list.add(room);
            }
            rs.close();
        }
        return list;
    }

    public List<RentalRoom> findAll() throws SQLException {
        return searchRentalRooms(""); // Trả về tất cả nếu keyword rỗng
    }

    @Override
    public boolean insert(RentalRoom room){
        String sql = "INSERT INTO rental_room (tenant_name, phone_number, start_rent_date, payment_method_id, note) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, room.getTenantName());
            ps.setString(2, room.getPhoneNumber());
            ps.setDate(3, room.getStartRentDate());
            ps.setInt(4, room.getPaymentMethodId());
            ps.setString(5, room.getNote());
            ps.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public void delete(int id) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement("DELETE FROM rental_room WHERE room_id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
