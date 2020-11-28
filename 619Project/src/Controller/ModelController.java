package Controller;
/*
 *  ModelController.java
 *  package: Controllers
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import CustomerModel.CustomerManager;
import CustomerModel.RegisteredUser;
import CustomerModel.Voucher;
import Model.BookingManager;
import Model.MovieCatalogue;
import Model.MovieTicket;
import Model.Movies;
import Model.Seat;
import Model.ShowTime;
import Model.Theater;
import Model.TheaterCatalogue;
import Model.TheaterRoom;
import View.BookingView;
import View.CancellationView;
import View.GUI;
import View.UserInfoView;

public class ModelController implements ActionListener{
	private GUI gui;
	private BookingManager bookingManager;
	private CustomerManager customerManager;
	
	/**
	 * constructor
	 * @param guiIn
	 * @param bookingManagerIn
	 */
	public ModelController(GUI guiIn, BookingManager bookingManagerIn, CustomerManager customerManagerIn) {
		gui = guiIn;	
		gui.getBooking().addActionListener(this);
		gui.getCancellation().addActionListener(this);
		
		bookingManager = bookingManagerIn;
		customerManager = customerManagerIn;
	}
	
	/**
	 * search movie
	 */
	private void searchMovies() {
		gui.getBookingPage().getMovieList().removeAllItems();
		
		String movieName = gui.getBookingPage().getSearchMovie().getText();
		
		for(Movies movie: bookingManager.searchMovie(movieName)) {
			gui.getBookingPage().getMovieList().addItem(movie.getName());		
		}		
	}

	/**
	 * view all movies
	 */
	private void viewMovies() {
		gui.getBookingPage().getMovieList().removeAllItems();
		
		for(Movies movie: bookingManager.viewMovies()) {
			gui.getBookingPage().getMovieList().addItem(movie.getName());		
		}
	}
	
	/**
	 * search theather
	 */
	private void searchTheather() {
		gui.getBookingPage().getTheaterList().removeAllItems();
		
		String movieSelectedName = (String)(gui.getBookingPage().getMovieList().getSelectedItem());	
		Movies movieSelected = null;
		for(Movies movie: bookingManager.viewMovies()) {
			if(movie.getName() == movieSelectedName)
				movieSelected = movie;
		}
		
		for(Theater theater: bookingManager.searchTheaters(movieSelected)) {
			gui.getBookingPage().getTheaterList().addItem(theater.getName());		
		}
	}
	
	/**
	 * view all theaters
	 */
	private void viewTheathers() {
		gui.getBookingPage().getTheaterList().removeAllItems();
		
		for(Theater theater: bookingManager.viewTheaters()) {
			gui.getBookingPage().getTheaterList().addItem(theater.getName());		
		}
	}
	
	/**
	 * view theater
	 */
	private void viewTheaterRoom() {
		gui.getBookingPage().getTheaterRoom().removeAllItems();

		String theaterSelectedName = (String)(gui.getBookingPage().getTheaterList().getSelectedItem());	

		for(Theater theater: bookingManager.viewTheaters()) {
			if(theater.getName() == theaterSelectedName){
				for(TheaterRoom room: theater.getTheaterRooms()) {
					gui.getBookingPage().getTheaterRoom().addItem(room.getNumber()+"");
				}
				break;
			}
		}		
	}
		
	/**
	 * get showtimes
	 */
	private void getShowTimes() {
		gui.getBookingPage().getShowTimes().removeAllItems();
				
		String theaterSelected = (String)(gui.getBookingPage().getTheaterList().getSelectedItem());	
		for(Theater theater: bookingManager.viewTheaters()) {
			if(theater.getName() == theaterSelected)
			{
				for(ShowTime showTime: theater.getShowtimes()) {		
					SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
					String dateString = dateFormatter.format(showTime.getDate());
					SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
					String timeString = timeFormatter.format(showTime.getTime());				
					gui.getBookingPage().getShowTimes().addItem(dateString + " "+ timeString);
				}				
				
				break;
			}
		}		
	}
		
	/**
	 * display seat
	 */
	private void displaySeat() {
		gui.getBookingPage().getSeatdisplay().removeAllItems();
		
		String theaterSelected = (String)(gui.getBookingPage().getTheaterList().getSelectedItem());	
		String theaterRoomSelected = (String)(gui.getBookingPage().getTheaterRoom().getSelectedItem());	

		for(Theater theater: bookingManager.viewTheaters()) {
			if(theater.getName() == theaterSelected){
				for(TheaterRoom theaterRoom: theater.getTheaterRooms()) {
					if(theaterRoom.getNumber() == Integer.valueOf(theaterRoomSelected)) {
						for(Seat seat: theaterRoom.getSeatArrangement().displaySeats()) {
							gui.getBookingPage().getSeatdisplay().addItem(seat.getRow()+","+seat.getColumn());
						}
						break;
					}
				}
			}
		}		
	}
	
	/**
	 * get price
	 */
	private void getPrice() {
		
		String theaterSelected = (String)(gui.getBookingPage().getTheaterList().getSelectedItem());	
		for(Theater theater: bookingManager.viewTheaters()) {
			if(theater.getName() == theaterSelected)
			{
				String showTimeSelected = (String)(gui.getBookingPage().getShowTimes().getSelectedItem());
				String[] str = showTimeSelected.split(" ");
				
				SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
				ParsePosition pos_date = new ParsePosition(0);
				Date dateSelected = (Date)(dateFormatter.parse(str[0], pos_date));
				
				SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
				ParsePosition pos_time = new ParsePosition(0);
				Time timeSelected = (Time)(dateFormatter.parse(str[1], pos_time));	
				
				for(ShowTime showtime: theater.getShowtimes()) {
					if(showtime.getDate() == dateSelected && showtime.getTime() == timeSelected) {
						double price = showtime.getPrice();
						gui.getBookingPage().getPrice().setText(price+"");
						break;
					}			
				}
			}
		}
	}
	
	/**
	 * show user information view for booking
	 */
	private void showUserInfo_Booking() {
		gui.getBookingPage().setUserInfo(new UserInfoView());
		
		gui.getBookingPage().getUserInfo().getPayAnnualFee().addActionListener(this); 
		gui.getBookingPage().getUserInfo().getLogin().addActionListener(this); 
		gui.getBookingPage().getUserInfo().getRegister().addActionListener(this);
		gui.getBookingPage().getUserInfo().getNoAccount().addActionListener(this);
		gui.getBookingPage().getUserInfo().getMakePayment().addActionListener(this);
	}
	
	/**
	 * show user information view for cancellation
	 */
	private void showUserInfo_Cancellation() {
		gui.getCancellPage().setUserInfo(new UserInfoView());

		gui.getCancellPage().getUserInfo().getPayAnnualFee().addActionListener(this); 
		gui.getCancellPage().getUserInfo().getLogin().addActionListener(this);
		gui.getCancellPage().getUserInfo().getRegister().addActionListener(this);
		gui.getCancellPage().getUserInfo().getNoAccount().addActionListener(this);	
		gui.getCancellPage().getUserInfo().getCancelSeat().addActionListener(this);
	}

	/**
	 * registered user login
	 */
	private void login(ActionEvent e) {
		if(gui.getBookingPage() != null && gui.getBookingPage().getUserInfo() != null && e.getSource() == gui.getBookingPage().getUserInfo().getLogin())
		{
			gui.getBookingPage().getUserInfo().getFeeRenewalStatus().setText("Owing Fee!");
			gui.getBookingPage().getUserInfo().getNameOfUser().setText("test");
			gui.getBookingPage().getUserInfo().getAddr().setText("test");
			gui.getBookingPage().getUserInfo().getBank().setText("test");
			gui.getBookingPage().getUserInfo().getCard_no().setText("test");
			gui.getBookingPage().getUserInfo().getExpr_date().setText("test");
			gui.getBookingPage().getUserInfo().getCvv().setText("test");
			gui.getBookingPage().getUserInfo().getEmail().setText("test");
			
			gui.getBookingPage().getUserInfo().getDisplay().setText("Login Successfully!");	
		}
		else
		{
			gui.getCancellPage().getUserInfo().getFeeRenewalStatus().setText("Owing Fee!");
			gui.getCancellPage().getUserInfo().getNameOfUser().setText("test");
			gui.getCancellPage().getUserInfo().getAddr().setText("test");
			gui.getCancellPage().getUserInfo().getBank().setText("test");
			gui.getCancellPage().getUserInfo().getCard_no().setText("test");
			gui.getCancellPage().getUserInfo().getExpr_date().setText("test");
			gui.getCancellPage().getUserInfo().getCvv().setText("test");
			gui.getCancellPage().getUserInfo().getEmail().setText("test");	
			
			gui.getCancellPage().getUserInfo().getDisplay().setText("Login Successfully!");	
		}
	}	
	
	/**
	 * pay annual fee
	 * @param e
	 */
	private void payAnnualFee(ActionEvent e) {
		if(gui.getBookingPage() != null && gui.getBookingPage().getUserInfo() != null && e.getSource() == gui.getBookingPage().getUserInfo().getPayAnnualFee())
		{
			gui.getBookingPage().getUserInfo().getDisplay().setText("Annual Fee Paid!");	
		}
		else
		{
			gui.getCancellPage().getUserInfo().getDisplay().setText("Annual Fee Paid!");		
		}
	}
	
	/**
	 * register user
	 * @param e
	 */
	private void registerUser(ActionEvent e) {
		if(gui.getBookingPage() != null && gui.getBookingPage().getUserInfo() != null && e.getSource() == gui.getBookingPage().getUserInfo().getRegister())
		{
			gui.getBookingPage().getUserInfo().getDisplay().setText("Register Successfully! Please Login and Pay Annual Fee!");	
		}
		else
		{
			gui.getCancellPage().getUserInfo().getDisplay().setText("Register Successfully! Please Login and Pay Annual Fee!");	
			
		}
	}
	
	/**
	 * create temporary user
	 * @param e
	 */
	private void createTempUser(ActionEvent e) {
		if(gui.getBookingPage() != null && gui.getBookingPage().getUserInfo() != null && e.getSource() == gui.getBookingPage().getUserInfo().getNoAccount())
		{
			gui.getBookingPage().getUserInfo().getDisplay().setText("Create Temporary User Successfully!");	
		}
		else
		{
			gui.getCancellPage().getUserInfo().getDisplay().setText("Create Temporary User Successfully!");	
		}
	}	

	/**
	 * process payment
	 * @param e
	 */
	private void processPayment(ActionEvent e) {
		if(gui.getBookingPage() != null && gui.getBookingPage().getUserInfo() != null && e.getSource() == gui.getBookingPage().getUserInfo().getMakePayment())
		{
			gui.getBookingPage().getUserInfo().getDisplay().setText("Paid Successfully!");	
		}
		else
		{
			gui.getCancellPage().getUserInfo().getDisplay().setText("Paid Successfully!");	
			
		}
	}	

	/**
	 * cancel ticket
	 * @param e
	 */
	private void cancelTicket(ActionEvent e) {
		if(gui.getBookingPage() != null && gui.getBookingPage().getUserInfo() != null && e.getSource() == gui.getBookingPage().getUserInfo().getCancelSeat())
		{
			gui.getBookingPage().getUserInfo().getDisplay().setText("Cancel Ticket Successfully!");	
		}
		else
		{
			gui.getCancellPage().getUserInfo().getDisplay().setText("Cancel Ticket Successfully!");	
			
		}		
	}	
		
	/**
	 * actionPerformed
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == gui.getBooking()) {
			gui.setBookingPage(new BookingView());
			
			gui.getBookingPage().getViewMovie().addActionListener(this);
			gui.getBookingPage().getViewAllMovie().addActionListener(this);
			gui.getBookingPage().getSearchTheater().addActionListener(this);
			gui.getBookingPage().getViewAllTheater().addActionListener(this);
			gui.getBookingPage().getViewTheaterRoom().addActionListener(this);
			gui.getBookingPage().getSearchShowTimes().addActionListener(this);
			gui.getBookingPage().getSelectSeat().addActionListener(this);
			gui.getBookingPage().getGetPrice().addActionListener(this);
			gui.getBookingPage().getProcessPayment().addActionListener(this);
		}
		else if(e.getSource() == gui.getCancellation()) {
			gui.setCancellPage(new CancellationView());
			
			gui.getCancellPage().getCancel().addActionListener(this);
		}		
		else if (gui.getBookingPage() != null && e.getSource() == gui.getBookingPage().getViewMovie()) {
			searchMovies();
		}	
		else if (gui.getBookingPage() != null && e.getSource() == gui.getBookingPage().getViewAllMovie()) {
			viewMovies();
		}
		else if (gui.getBookingPage() != null && e.getSource() == gui.getBookingPage().getSearchTheater()) {
			searchTheather();
		}		
		else if (gui.getBookingPage() != null && e.getSource() == gui.getBookingPage().getViewAllTheater()) {
			viewTheathers();
		}
		else if (gui.getBookingPage() != null && e.getSource() == gui.getBookingPage().getViewTheaterRoom()) {
			viewTheaterRoom();
		}		
		else if (gui.getBookingPage() != null && e.getSource() == gui.getBookingPage().getSearchShowTimes()) {
			getShowTimes();
		}
		else if (gui.getBookingPage() != null && e.getSource() == gui.getBookingPage().getSelectSeat()) {
			displaySeat();
		}
		else if (gui.getBookingPage() != null && e.getSource() == gui.getBookingPage().getGetPrice()) {
			getPrice();
		}		
		else if (gui.getBookingPage() != null && e.getSource() == gui.getBookingPage().getProcessPayment()) {
			showUserInfo_Booking();
		}	
		else if (gui.getCancellPage() != null && e.getSource() == gui.getCancellPage().getCancel()) {
			showUserInfo_Cancellation();
		}	
		else if((gui.getBookingPage() != null && gui.getBookingPage().getUserInfo() != null && e.getSource() == gui.getBookingPage().getUserInfo().getLogin())
				|| (gui.getCancellPage() != null && gui.getCancellPage().getUserInfo() != null && e.getSource() == gui.getCancellPage().getUserInfo().getLogin())) {
			login(e);
		}	
		else if((gui.getBookingPage() != null && gui.getBookingPage().getUserInfo() != null && e.getSource() == gui.getBookingPage().getUserInfo().getPayAnnualFee())
				|| (gui.getCancellPage() != null && gui.getCancellPage().getUserInfo() != null && e.getSource() == gui.getCancellPage().getUserInfo().getPayAnnualFee())) {
			payAnnualFee(e);
		}
		else if((gui.getBookingPage() != null && gui.getBookingPage().getUserInfo() != null && e.getSource() == gui.getBookingPage().getUserInfo().getRegister())
				|| (gui.getCancellPage() != null && gui.getCancellPage().getUserInfo() != null && e.getSource() == gui.getCancellPage().getUserInfo().getRegister())){
			registerUser(e);
		}
		else if((gui.getBookingPage() != null && gui.getBookingPage().getUserInfo() != null && e.getSource() == gui.getBookingPage().getUserInfo().getNoAccount())
				|| (gui.getCancellPage() != null && gui.getCancellPage().getUserInfo() != null && e.getSource() == gui.getCancellPage().getUserInfo().getNoAccount())){
			createTempUser(e);
		}
		else if((gui.getBookingPage() != null && gui.getBookingPage().getUserInfo() != null && e.getSource() == gui.getBookingPage().getUserInfo().getMakePayment())
				|| (gui.getCancellPage() != null && gui.getCancellPage().getUserInfo() != null && e.getSource() == gui.getCancellPage().getUserInfo().getMakePayment())){
			processPayment(e);
		}
		else if((gui.getBookingPage() != null && gui.getBookingPage().getUserInfo() != null && e.getSource() == gui.getBookingPage().getUserInfo().getCancelSeat())
				|| (gui.getCancellPage() != null && gui.getCancellPage().getUserInfo() != null && e.getSource() == gui.getCancellPage().getUserInfo().getCancelSeat())){
			cancelTicket(e);
		}
	}
	
	/**
	 * main function
	 * @param args
	 * @throws IOException
	 */
	public static void main (String [] args) throws IOException {
		MovieCatalogue movies = new MovieCatalogue();
		TheaterCatalogue theaters = new TheaterCatalogue();
		HashMap<Integer, MovieTicket> bookings = new HashMap<Integer, MovieTicket>();
		BookingManager bookingManager = new BookingManager(movies, theaters, bookings);
		
		Map<String, RegisteredUser> registeredUser = null;
		Map<Integer, Voucher> voucher = null;
		CustomerManager customerManager = new CustomerManager(registeredUser, voucher);
		
		ModelController modelController = new ModelController(new GUI(), bookingManager, customerManager);
	}	
}
