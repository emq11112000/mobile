package com.example.palestinehotel;


import java.util.Date;

public class Reservation {

    private int ReservationId ;
    private int Adults;
    private int Children;
    private Date CheckInDate;
    private Date CheckOutDate;
    private double Total;

    public Reservation(int reservationId, int adults, int children, Date checkInDate, Date checkOutDate, double total) {
        ReservationId = reservationId;
        Adults = adults;
        Children = children;
        CheckInDate = checkInDate;
        CheckOutDate = checkOutDate;
        Total = total;
    }



    public int getReservationId() {
        return ReservationId;
    }

    public void setReservationId(int reservationId) {
        ReservationId = reservationId;
    }

    public int getAdults() {
        return Adults;
    }

    public void setAdults(int adults) {
        Adults = adults;
    }

    public int getChildren() {
        return Children;
    }

    public void setChildren(int children) {
        Children = children;
    }

    public Date getCheckInDate() {
        return CheckInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        CheckInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return CheckOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        CheckOutDate = checkOutDate;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }
}

