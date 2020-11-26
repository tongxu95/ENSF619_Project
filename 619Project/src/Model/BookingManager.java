// package Model;
/*
 *  BookingManager.java
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

import java.util.HashMap;
import java.util.ArrayList;

public class BookingManager {
    private MovieCatalogue movies;
    private TheaterCatalogue theaters;
    private HashMap<Integer, MovieTicket> bookings;

    /**
     * Initialize booking manager
     */
    public BookingManager(MovieCatalogue movies, TheaterCatalogue theaters, HashMap<Integer, MovieTicket> bookings) {
        this.movies = movies;
        this.theaters = theaters;
        this.bookings = bookings;
    }

    /**
     * @return returns the movies within the movie catalogue
     */
    public ArrayList<Movies> viewMovies() {
        return movies.viewMovies();
    }

    /**
     * @return returns the movies with the given string name
     */
    public ArrayList<Movies> searchMovie(String name) {
        return movies.searchMovie(name);
    }

    /**
     * @return returns the theaters within the theater catalogue
     */
    public ArrayList<Theater> viewTheaters() {
        return theaters.viewTheaters();
    }

    /**
     * @param movie
     * @return returns the theaters with the given movie
     */
    public ArrayList<Theater> searchTheaters(Movies movie) {
        return theaters.searchTheaterForMovie(movie);
    }

    /**
     * @param movie
     * @param theater
     * @return showtimes for a particular movie at a theater
     */
    public ArrayList<ShowTime> getShowTimes(Movies movie, Theater theater) {
        return theater.searchShowTimes(movie);
    }

    /**
     * @return the seatmap of a showtime
     */
    public SeatMap displaySeat(ShowTime showtime) {
        return showtime.displaySeats();
    }

    /**
     * @return the price of a showtime
     */
    public double getPrice(ShowTime showtime) {
        return showtime.getPrice();
    }

    /**
     * @return boolean if thet ticket is cancelled, indicated by '0' in the bookingid
     */
    public boolean verifyCancellation(MovieTicket ticket) {
        if (ticket.getBookingId() == 0) {
            return true;
        }
        else return false;
    }

    /**
     * cancel the input ticket
     */
    public void cancelTicket(MovieTicket ticket) {
        ticket.cancel();
    }

    /**
     * @return boolean if the seat can be selected
     */
    public boolean selectSeat(ShowTime showtime, int row, int column, int booking_id) {
        return showtime.selectSeat(row, column, booking_id);
    }

    /*
        TODO LIST_OF_ITEMS
        - items reliant on date or time - sort showtimes by date and time
            - don't know if its easier to sort on the modelcontroller / view side
        - booking manager 
            - addBooking(ticket:MovieTicket) <-- not sure if required CHECK ShowTime.selectSeat() logic
            - any other functions required by the model controller <-- TBD
            - figure out how the bookings in bookingManager gets populated / DB gets populated
            - validateBooking(bookingID:int) RETURNS MovieTicket
    */
}
