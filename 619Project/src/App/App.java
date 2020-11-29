package App;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Controller.ModelController;
import CustomerModel.CustomerManager;
import CustomerModel.RegisteredUser;
import CustomerModel.Voucher;
import DBController.LoadDB;
import DBController.MovieDBController;
import FinancialInstitute.BankManager;
import Model.BookingManager;
import Model.MovieCatalogue;
import Model.MovieTicket;
import Model.Movies;
import Model.Theater;
import Model.TheaterCatalogue;
import View.GUI;

public class App {
	/**
	 * main function
	 * @param args
	 * @throws IOException
	 */
	public static void main (String [] args) throws IOException {
		MovieDBController db = new MovieDBController();
		
		//load movies from mysql
		MovieCatalogue movies = new MovieCatalogue();
		
        ArrayList<Movies> movieList = db.selectMovies();
        for (int i = 0; i< movieList.size(); i++) {
            movies.addMovie(movieList.get(i));
        }		
		
		//load theaters from mysql
		TheaterCatalogue theaters = new TheaterCatalogue();
        ArrayList<Theater> theaterList = db.selectTheaters();
        for (int i = 0; i < theaterList.size(); i++) {
            theaters.addTheater(theaterList.get(i));
        }
		
		//load booking from mysql 
		HashMap<Integer, MovieTicket> bookings = new HashMap<Integer, MovieTicket>();
		
		
		//build BookingManager object
		BookingManager bookingManager = new BookingManager(movies, theaters, bookings);

		//build CustomerManager object
		Map<Integer, Voucher> voucher = LoadDB.loadVouchers();
		Map<String, RegisteredUser> registeredUser = LoadDB.loadRegUsers();
		CustomerManager customerManager = new CustomerManager(registeredUser, voucher);

		//build BankManager object
		BankManager bankManager = new BankManager(LoadDB.LoadBanks());

		//build modelController object
		ModelController modelController = new ModelController(new GUI(), bookingManager, customerManager, bankManager);
	}	
	
}
