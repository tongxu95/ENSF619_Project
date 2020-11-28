// package Model;
/*
 *  TheaterCatalogue.java
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

public class TheaterCatalogue {
    private ArrayList<Theater> theaters;

    /**
     * populated theater catalogue
     * @param theaters
     */
    public TheaterCatalogue(ArrayList<Theater> theaters) {
        this.theaters = theaters;
    }

    /**
     * blank theater catalogue
     */
    public TheaterCatalogue() {
        this.theaters = new ArrayList<Theater>();
    }

    /**
     * add a theater to the list
     */
    public void addTheater(Theater theater) {
        this.theaters.add(theater);
    }

    /**
     * return all theaters
     */
    public ArrayList<Theater> viewTheaters() {
        return this.theaters;
    }

    /**
     * return list of theaters that offer a movie
     */
    public ArrayList<Theater> searchTheaterForMovie(Movies movie) {
        ArrayList<Theater> output = new ArrayList<>();

        for (int i = 0; i < theaters.size(); i++) {
            if (theaters.get(i).searchShowTimes(movie).size() > 0) 
                output.add(theaters.get(i));
        }
        
        return output;
    }
}
