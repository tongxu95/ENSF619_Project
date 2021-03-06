package Model;
/*
 *  MovieTicket.java
 *  package: Model
 *  ENSF 619 - Term Project - Movie Theater Ticket Reservation App
 * 
 *  Completed by: Group #14
 *  Haixia Wu
 *  Jenny Tong Xu
 *  John Van Heurn
 *  Javier Vite
 * 
 *  Date: November 30 2020
 */

import java.util.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MovieTicket {
    private ShowTime showtime;
    private int row;
    private int column;
    private int booking_id;
    private String userType;

    /**
     * Initialize all MovieTicket variables
     */
    public MovieTicket(ShowTime showtime, int row, int column, int booking_id, String userType) {
        this.showtime = showtime;
        this.row = row;
        this.column = column;
        this.booking_id = booking_id;
        this.userType = userType;
    }

    /**
     * Cancel the movie ticket
     */
    public double cancel() {
        return showtime.cancelSeat(this.row, this.column, userType);
    }

    /*
        GET FUNCTIONS
    */
    public ShowTime getShowTime() {
        return this.showtime;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public int getBookingId() {
        return this.booking_id;
    }

    public String getUserType() {
        return this.userType;
    }
    
    public boolean checkCancellation() {
    	LocalDate date = LocalDate.parse(showtime.getDate().toString());
    	LocalTime time = LocalTime.parse(showtime.getTime().toString());
    	LocalDateTime dt = LocalDateTime.of(date, time);
    	return LocalDateTime.now().plusHours(72).compareTo(dt) <= 0;
    }
    
    @Override
    public String toString() {
    	String info = "Booking ID: " + booking_id + "\n";
    	info += showtime;
    	info += "Seat: \n\tRow: " + row + "\n\tColumn: " + column; 
    	return info;
    }
}
