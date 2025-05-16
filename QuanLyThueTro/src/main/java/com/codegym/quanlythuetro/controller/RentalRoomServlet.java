package com.codegym.quanlythuetro.controller;

import com.codegym.quanlythuetro.model.RentalRoom;
import com.codegym.quanlythuetro.service.PaymentMethodDAO;
import com.codegym.quanlythuetro.service.RentalRoomDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/rental")
public class RentalRoomServlet extends HttpServlet {
    private RentalRoomDAO rentalRoomDAO;
    private PaymentMethodDAO paymentMethodDAO;

    @Override
    public void init() throws ServletException {
        paymentMethodDAO = new PaymentMethodDAO();
        rentalRoomDAO = new RentalRoomDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        if (keyword == null) keyword = "";

        try {
            List<RentalRoom> rentalRooms = rentalRoomDAO.searchRentalRooms(keyword);
            request.setAttribute("paymentMethods", paymentMethodDAO.findAll());
            request.setAttribute("rentalRooms", rentalRooms);
            request.setAttribute("keyword", keyword);
            request.getRequestDispatcher("rental/list.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "";
        switch (action) {
            case "create":
                request.setCharacterEncoding("UTF-8");

                String tenantName = request.getParameter("tenantName");
                String phone = request.getParameter("phoneNumber");
                String dateStr = request.getParameter("startRentDate");
                String paymentIdStr = request.getParameter("paymentMethodId");
                String note = request.getParameter("note");

                List<String> errors = new ArrayList<>();

                if (!tenantName.matches("^.{5,50}$")) {
                    errors.add("Tên người thuê phải có độ dài từ 5 đến 50 ký tự.");
                }

                if (!phone.matches("^\\d{10}$")) {
                    errors.add("Số điện thoại phải là 10 chữ số.");
                }
                LocalDate startDate = null;
                try {
                    startDate = LocalDate.parse(dateStr);
                    if (startDate.isBefore(LocalDate.now())) {
                        errors.add("Không được chọn ngày quá khứ.");
                    }
                } catch (Exception e) {
                    errors.add("Ngày không hợp lệ.");
                }

                if (errors.isEmpty()) {
                    try {
                        RentalRoom room = new RentalRoom();
                        room.setTenantName(tenantName);
                        room.setPhoneNumber(phone);
                        room.setStartRentDate(Date.valueOf(startDate));
                        room.setPaymentMethodId(Integer.parseInt(paymentIdStr));
                        room.setNote(note);
                        rentalRoomDAO.insert(room);
                        response.sendRedirect("/rental");
                        return;
                    } catch (Exception e) {
                        throw new ServletException(e);
                    }
                }

                // Nếu có lỗi
                request.setAttribute("errors", errors);
                request.setAttribute("keyword", "");
                try {
                    request.setAttribute("rentalRooms", rentalRoomDAO.findAll());
                    request.setAttribute("paymentMethods", paymentMethodDAO.findAll());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                request.getRequestDispatcher("rental/list.jsp").forward(request, response);
                break;

                case "delete":
                    String[] ids = request.getParameterValues("roomIds");
                    try {
                        for (String id : ids) {
                            rentalRoomDAO.delete(Integer.parseInt(id));
                        }
                    } catch (SQLException e) {
                        throw new ServletException(e);
                    }
                    response.sendRedirect("/rental");
                    break;
        }

    }
}


