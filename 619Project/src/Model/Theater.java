package Model;
/*
 *  Theater.java
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Collections;

public class Theater {
    private String name;
    private ArrayList<ShowTime> showtimes;
    private ArrayList<TheaterRoom> rooms;

    /**
     * Initialize the theater
     */
    public Theater(String name, ArrayList<ShowTime> showtimes, ArrayList<TheaterRoom> rooms) {
        this.name = name;
        this.showtimes = showtimes;
        this.rooms = rooms;
    }

    /**
     * Return showtimes that are for a given movie
     * @param movie
     * @return
     */
    public ArrayList<ShowTime> searchShowTimes(Movies movie) {
        ArrayList<ShowTime> output = new ArrayList<>();

        for (int i = 0; i < showtimes.size(); i++) {
            if (showtimes.get(i).getMovie().getName() == movie.getName()) {
                output.add(showtimes.get(i));
            }
        }
        return output;
    }

    /**
     * Return showtimes that are for a given movie sorted by date
     * @param movie
     * @param date
     * @return 
     */
    public ArrayList<ShowTime> searchShowTimesByDate(Movies movie, Date date) {
        ArrayList<ShowTime> output = new ArrayList<>();

        for (int i = 0; i < showtimes.size(); i++) {
            if (showtimes.get(i).getMovie().getName() == movie.getName()) {
                if (date.after(showtimes.get(i).getDate())) {
                    output.add(showtimes.get(i));
                }
            }
        }

        // sort arraylist by date
        Collections.sort(output);

        return output;
    }

    /**
     * Add a showtime to the movie theater
     */
    public void addShowTime(ShowTime showtime) {
        showtimes.add(showtime);
        return;
    }

    /*
        GET FUNCTIONS
    */

    public String getName() {
        return this.name;
    }
    public ArrayList<ShowTime> getShowtimes() {
        return this.showtimes;
    }
    public ArrayList<TheaterRoom> getTheaterRooms() {
        return this.rooms;
    }
}
