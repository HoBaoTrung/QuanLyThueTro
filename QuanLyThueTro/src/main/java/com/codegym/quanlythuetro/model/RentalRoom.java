package com.codegym.quanlythuetro.model;

import java.sql.Date;

public class RentalRoom {
    private int roomId;
    private String tenantName;
    private String phoneNumber;
    private java.sql.Date startRentDate;
    private int paymentMethodId;
    private String paymentMethodName;
    private String note;

    public RentalRoom(){}

    public RentalRoom(int roomId, String tenantName, String phoneNumber, Date startRentDate, int paymentMethodId, String note) {
        this.roomId = roomId;
        this.tenantName = tenantName;
        this.phoneNumber = phoneNumber;
        this.startRentDate = startRentDate;
        this.paymentMethodId = paymentMethodId;
        this.note = note;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getStartRentDate() {
        return startRentDate;
    }

    public void setStartRentDate(Date startRentDate) {
        this.startRentDate = startRentDate;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }
}
