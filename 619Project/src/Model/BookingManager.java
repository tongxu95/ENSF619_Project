package Model;
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
import java.util.Date;

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
     * @param movie
     * @param theater
     * @param date
     * @return showtimes for a particular movie at a theater by date
     */
    public ArrayList<ShowTime> getShowTimesByDate(Movies movie, Theater theater, Date date) {
        return theater.searchShowTimesByDate(movie, date);
    }

    /**
     * @return the seatmap of a showtime
     */
    public Seat[][] displaySeat(ShowTime showtime) {
        return showtime.displaySeats();
    }

    /**
     * @return the price of a showtime
     */
    public double getPrice(ShowTime showtime) {
        return showtime.getPrice();
    }

    /**
     * @return boolean if the ticket can be cancelled (cancellation request made 72 hours before showtime)
     */
    public boolean verifyCancellation(MovieTicket ticket) {
        return ticket.checkCancellation();
    }

    /**
     * cancel the input ticket
     */
    public double cancelTicket(MovieTicket ticket) {
        bookings.remove(ticket.getBookingId());
        double ticketprice = ticket.cancel();
        System.out.println(ticketprice);
        return ticketprice;
    }

    /**
     * @return select seat returns a movieTicket if the selected seat is available
     */
    public MovieTicket selectSeat(ShowTime showtime, int row, int column, int booking_id, String userType) {
        return showtime.selectSeat(row, column, booking_id, userType);
    }


    public MovieTicket validateBooking(int booking_id) {
        return bookings.get(booking_id); // returns null if no mapping for booking_id, returns movieticket if exists
    }
    
    public void addBooking(int bookingID, MovieTicket ticket) {
    	bookings.put(bookingID, ticket);
    }

    /*
        TODO LIST_OF_ITEMS
            - database WRT model movies / showtimes / etc...
            - modelcontroller WRT model
    */
}
