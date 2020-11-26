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

public class Theater {
    private String name;
    private MovieCatalogue movies;
    private ArrayList<ShowTime> showtimes;
    private ArrayList<TheaterRoom> rooms;

    /**
     * Initialize the theater
     */
    public Theater(String name, MovieCatalogue movies, ArrayList<ShowTime> showtimes, ArrayList<TheaterRoom> rooms) {
        this.name = name;
        this.movies = movies;
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

    /*
        GET FUNCTIONS
    */

    public String getName() {
        return this.name;
    }
    public MovieCatalogue getMovies() {
        return this.movies;
    }
    public ArrayList<ShowTime> getShowtimes() {
        return this.showtimes;
    }
    public ArrayList<TheaterRoom> getTheaterRooms() {
        return this.rooms;
    }
}
