package Model;
/*
 *  ShowTime.java
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

public class ShowTime implements Comparable<ShowTime> {
    private Movies movie;
    private String theater_name;
    private int theater_num;
    private Date date;
    private Time time;
    private SeatMap seats_offered;
    private double price;

    /**
     * Initialize all ShowTime variables
     * @param movie
     * @param theater_name
     * @param theater_num
     * @param date
     * @param time
     * @param seats_offered
     */
    public ShowTime(Movies movie, String theater_name, int theater_num, Date date, Time time, SeatMap seats_offered) {
        this.movie = movie; 
        this.theater_name = theater_name;
        this.theater_num = theater_num;
        this.date = date;
        this.time = time;
        this.seats_offered = seats_offered;
    }

    /** 
     * Return the seatmap for the showtime for the GUI
     */
    public SeatMap displaySeats() {
        return this.seats_offered;
    }

    /**
     * return the seat at the input position
     * @param row
     * @param column
     */
    public MovieTicket selectSeat(int row, int column, int booking_id, String userType) {
        if (seats_offered.checkSeatAvailability(row, column)) {
            seats_offered.bookSeat(row, column, booking_id);
            return new MovieTicket(this, row, column, booking_id, userType);
        }

        System.out.println("That seat is not available");
        return null;
    }

    /**
     * cancel the seat at the input position
     */
    public double cancelSeat(int row, int column, String userType) {
    	System.out.println(price);
        seats_offered.cancelSeat(row, column);
        if (userType.equals("Registered")) return price;
        else return price*0.85;
    }

    /*
        GET FUNCTIONS
    */
    public Movies getMovie() {
        return this.movie;
    }
    public String getTheaterName() {
        return this.theater_name;
    }
    public int getTheaterNum() {
        return this.theater_num;
    }
    public Date getDate() {
        return this.date;
    }
    public Time getTime() {
        return this.time;
    }
    public double getPrice() {
        return this.price;
    }
    @Override
    public String toString() {
    	String info = movie + "Theater: " + theater_name + "\n";
    	info += "Date: " + date + "\n";
    	info += "Showtime: " + time + "\n";
    	info += "Theater room: " + theater_num + "\n";
    	return info;
    }

    @Override public int compareTo(ShowTime showtime) {
        // sort in ascending order
        return this.getDate().compareTo(showtime.getDate());

        // // sort in descending order
        // return showtime.getDate().compareTo(this.getDate());
    }

}
