package com.codegym.quanlythuetro.service;

import com.codegym.quanlythuetro.model.PaymentMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaymentMethodDAO implements ReadAllDAO<PaymentMethod> {
    @Override
    public List<PaymentMethod> findAll() throws SQLException {
        String sql = "select * from payment_method";
        List<PaymentMethod> paymentMethods = new ArrayList<PaymentMethod>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                PaymentMethod paymentMethod = new PaymentMethod();
                paymentMethod.setId(rs.getInt("payment_method_id"));
                paymentMethod.setName(rs.getString("method_name"));
                paymentMethods.add(paymentMethod);
            }
        }
        return paymentMethods;
    }
}
