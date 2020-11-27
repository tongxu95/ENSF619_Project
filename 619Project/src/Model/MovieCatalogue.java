package Model;
/*
 *  MovieCatalogue.java
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

public class MovieCatalogue {
    private ArrayList<Movies> movies;

    /**
     * populated movie catalogue
     * @param movies
     */
    public MovieCatalogue(ArrayList<Movies> movies) {
        this.movies = movies;
    }
    /**
     * blank movie catalogue
     */
    public MovieCatalogue(){
        this.movies = new ArrayList<Movies>();
    }

    /**
     * add a movie to the list
     */
    public void addMovie(Movies movie) {
        this.movies.add(movie);
    }

    /**
     * return the list of movies
     * @return output
     */
    public ArrayList<Movies> viewMovies() {
        return movies;
    }

    /**
     * return a list of movies that contains the search term name
     * @return output
     */
    public ArrayList<Movies> searchMovie(String name) {
        ArrayList<Movies> output = new ArrayList<Movies>();

        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getName().contains(name)) {
                output.add(movies.get(i));
            }
        }

        return output;   
    }

    // test movie catalogue
    public static void main(String[] args) {
        MovieCatalogue test = new MovieCatalogue();
        test.addMovie(new Movies("Lord of the Rings"));
        test.addMovie(new Movies("Lord of the Rings 2"));
        test.addMovie(new Movies("Django"));

        ArrayList<Movies> search1 = test.searchMovie("Lor");
        ArrayList<Movies> search2 = test.searchMovie("Django");

        for (int i = 0; i < search1.size(); i++){
            System.out.println(search1.get(i).getName());
        }
        for (int i = 0; i < search2.size(); i++){
            System.out.println(search2.get(i).getName());
        }
    }
}
