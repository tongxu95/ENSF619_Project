package Model;
/*
 *  Movies.java
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

public class Movies {
    private String name;

    public Movies(String name) {
        this.name = name;
    }
    public Movies() {
        this.name = "";
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
    	return "Movie: " + name + '\n';
    }
    
    
}
