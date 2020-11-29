package DBController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import Model.MovieCatalogue;
import Model.MovieTicket;
import Model.Movies;
import Model.SeatMap;
import Model.ShowTime;
import Model.Theater;
import Model.TheaterCatalogue;
import Model.TheaterRoom;

public class MovieDBController {
    public Connection jdbc_connection;
    public PreparedStatement statement;
    public String databaseName = "bookingmodel";

    public String connectionInfo = "jdbc:mysql://localhost:3306/bookingmodel",  
            login          = "",
            password       = "";
	// TODO: fill in login and password

    /**
     * connect to the database labelled project
     */
    public MovieDBController() {
        try{
            // If this throws an error, make sure you have added the mySQL connector JAR to the project
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // If this fails make sure your connectionInfo and login/password are correct
            jdbc_connection = DriverManager.getConnection(connectionInfo, login, password);
            System.out.println("Connected to: " + connectionInfo + "\n");
        }
        catch(SQLException e) { e.printStackTrace(); }
        catch(Exception e) { e.printStackTrace(); }
    } 

    /**
     * @return an arraylist of movies with the intent of populating a movie catalogue
     */
    public ArrayList<Movies> selectMovies()
	{
        ArrayList<Movies> output = new ArrayList<Movies>();
		try {
			String sql = "SELECT * FROM MOVIES";
			statement = jdbc_connection.prepareStatement(sql);
			
			ResultSet movies = statement.executeQuery();
			System.out.println("Movies:");
			while(movies.next())
			{
				System.out.println(movies.getString("Name"));
                output.add(new Movies(movies.getString("Name")));   
			}
			movies.close();
		} catch (SQLException e) {
			e.printStackTrace();
        }
        
        return output;
    }

    /**
     * @return an arraylist of theaternames in theater objects from the database
     */
	public ArrayList<Theater> selectTheaters()
	{

        ArrayList<Theater> output = new ArrayList<Theater>();
		try {
			String sql = "SELECT * FROM THEATER";
			statement = jdbc_connection.prepareStatement(sql);
			
			ResultSet theaters = statement.executeQuery();
			System.out.println("Theaters:");
			while(theaters.next())
			{
                System.out.println(theaters.getString("Name"));    
                output.add(new Theater(theaters.getString("Name"), new ArrayList<ShowTime>(), new ArrayList<TheaterRoom>()));
			}
			theaters.close();
		} catch (SQLException e) {
			e.printStackTrace();
        }
        return output;
    } 

    /**
     * With a given theater catalogue, refers to the database and adds an initialized showtime to the correct theater 
     * @param TheaterCatalogue
     */
    public void selectAndAddShowTimes(TheaterCatalogue theaters, MovieCatalogue movies) {

        // check if the database showtime entry name = theater.name
        // if so, pull the corresponding theater room and initialize and add a showtime object to the theater

        try {
            String sql = "SELECT * FROM SHOWTIME";
            statement = jdbc_connection.prepareStatement(sql);
            ResultSet showtimes = statement.executeQuery();
            System.out.println("ShowTimes: ");
            while (showtimes.next()) {
                // retrieve Movie object from movieCatalogue
                Movies movie = new Movies();
                for (int i = 0; i < movies.viewMovies().size(); i++) {
                    if (movies.viewMovies().get(i).getName().equals(showtimes.getString("MovieName"))) {
                        movie = movies.viewMovies().get(i);
                    }
                }

                // iterate through the theaters looking for theater.name = dbEntry.name
                for (int i = 0; i < theaters.viewTheaters().size(); i++) {
                    if (theaters.viewTheaters().get(i).getName().equals(showtimes.getString("TheaterName"))) {
                        // with correct theater, retrieve TheaterRoom with same number
                        for (int j = 0; j < theaters.viewTheaters().get(i).getTheaterRooms().size(); j++) {
                            if (theaters.viewTheaters().get(i).getTheaterRooms().get(j).getNumber() == showtimes.getInt("TheaterNumber")) {
                                // add showtime to theater
                                theaters.viewTheaters().get(i).addShowTime(new ShowTime(movie, showtimes.getString("TheaterName"), showtimes.getInt("TheaterNumber"), showtimes.getDate("ShowDate"), showtimes.getTime("ShowTime"), new SeatMap(theaters.viewTheaters().get(i).getTheaterRooms().get(j).getSeatArrangement())));
                                System.out.println("Movie '" + movie.getName() + "' Showtime Added to Theater");
                                break;
                            }
                        }
                        break;
                    }
                }   
            }
        }catch (SQLException e) {
			e.printStackTrace();
        }
    }

}