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
import java.util.Date;
import java.sql.Time;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import CustomException.InvalidBankException;
import CustomException.InvalidPasswordException;
import CustomException.InvalidUsernameException;
import CustomerModel.CustomerManager;
import CustomerModel.RegisteredUser;
import CustomerModel.Voucher;
import DBController.LoadDB;
import DBController.MovieDBController;
import FinancialInstitute.Bank;
import FinancialInstitute.BankManager;
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
	private BankManager bankManager;

	static int bookingID = 1000000;//bookingID plus 1 if a seat was booked 

	/**
	 * constructor
	 * @param guiIn
	 * @param bookingManagerIn
	 */
	public ModelController(GUI guiIn, BookingManager bookingManagerIn, CustomerManager customerManagerIn, BankManager bankManagerIn) {
		gui = guiIn;	
		gui.getBooking().addActionListener(this);
		gui.getCancellation().addActionListener(this);

		bookingManager = bookingManagerIn;
		customerManager = customerManagerIn;
		bankManager = bankManagerIn;
	}

	/**
	 * search movie
	 */
	private void searchMovies() {
		gui.getBookingPage().getMovieList().removeAllItems();

		String movieName = gui.getBookingPage().getSearchMovie().getText();

		for(Movies movie: bookingManager.searchMovie(movieName)) {
			if(movie.getName() == movieName)
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

		//get selected movie's name in the view
		String movieSelectedName = (String)(gui.getBookingPage().getMovieList().getSelectedItem());	
		if(movieSelectedName != null)
		{
			Movies movieSelected = null;
			//get the selected movie's object
			for(Movies movie: bookingManager.viewMovies()) {
				if(movie.getName() == movieSelectedName)
					movieSelected = movie;
			}
	
			//get the theater by the selected movie's object 
			for(Theater theater: bookingManager.searchTheaters(movieSelected)) {
				gui.getBookingPage().getTheaterList().addItem(theater.getName());		
			}
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
	 * view showtimes by selected date in the view
	 */
	private void viewShowTimes() {
		gui.getBookingPage().getTimeOfDate().removeAllItems();

		//get selected theater
		String theaterSelected = (String)(gui.getBookingPage().getTheaterList().getSelectedItem());	
		for(Theater theater: bookingManager.viewTheaters()) {
			if(theater.getName() == theaterSelected)
			{				
				//get selected movie's name
				String movieSelectedName = (String)(gui.getBookingPage().getMovieList().getSelectedItem());	
				//get the selected movie's object
				Movies movieSelected = null;
				for(Movies movie: bookingManager.viewMovies()) {
					if(movie.getName() == movieSelectedName) {	
						movieSelected = movie;

						//construct a Date object by using selected date in the view(String to Date)
						String strDate = (String)(gui.getBookingPage().getDate().getSelectedItem());		
						SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
						ParsePosition pos_date = new ParsePosition(0);
						Date dateSelected = (Date)(dateFormatter.parse(strDate, pos_date));

						//get showtime by selected movie and selected date	
						for(ShowTime showTime: theater.searchShowTimesByDate(movieSelected, dateSelected)) {	
							SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
							String timeString = timeFormatter.format(showTime.getTime());				
							gui.getBookingPage().getTimeOfDate().addItem(timeString);
						}	

						break;
					}
				}				
			}
		}		
	}

	/**
	 * view seat
	 */
	private void viewSeat() {
		gui.getBookingPage().getSeatdisplay().removeAllItems();
		
		//get selected theater
		String theaterSelected = (String)(gui.getBookingPage().getTheaterList().getSelectedItem());	
		for(Theater theater: bookingManager.viewTheaters()) {			
			if(theater.getName() == theaterSelected)
			{		
				//get showtime
				for(ShowTime showtime: theater.getShowtimes())
				{				
					//get selected date
					String strDate = (String)(gui.getBookingPage().getDate().getSelectedItem());			
					SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
					ParsePosition pos_date = new ParsePosition(0);
					Date dateSelected = (Date)(dateFormatter.parse(strDate, pos_date));

					//get selected time
					String strTime = (String)(gui.getBookingPage().getTimeOfDate().getSelectedItem());	
					SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
					ParsePosition pos_time = new ParsePosition(0);
					Time timeSelected = new java.sql.Time(((Date)(timeFormatter.parse(strTime, pos_time))).getTime());				
										
					//compare year, month, day of two dates, compare hour, minute, second of two times
					if(showtime.getDate().getYear() == dateSelected.getYear()
							&& showtime.getDate().getMonth() == dateSelected.getMonth()
							&& showtime.getDate().getDay() == dateSelected.getDay()
							&& showtime.getTime().getHours() == timeSelected.getHours()
							&& showtime.getTime().getMinutes() == timeSelected.getMinutes()
							&& showtime.getTime().getSeconds() == timeSelected.getSeconds()) 
					{
						Seat[][] seat = showtime.displaySeats().displaySeats();
						
						for(int i = 0; i < seat.length; i++)
							for(int j = 0; j < seat[i].length; j++){
							if(seat[i][j].checkAvailability())
								gui.getBookingPage().getSeatdisplay().addItem(i + "," + j);
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

		//get selected theater
		String theaterSelected = (String)(gui.getBookingPage().getTheaterList().getSelectedItem());	
		for(Theater theater: bookingManager.viewTheaters()) {
			if(theater.getName() == theaterSelected)
			{
				//get selected date
				String strDate = (String)(gui.getBookingPage().getDate().getSelectedItem());			
				SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
				ParsePosition pos_date = new ParsePosition(0);
				Date dateSelected = (Date)(dateFormatter.parse(strDate, pos_date));

				//get selected time
				String strTime = (String)(gui.getBookingPage().getTimeOfDate().getSelectedItem());	
				SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
				ParsePosition pos_time = new ParsePosition(0);
				Time timeSelected = new java.sql.Time(((Date)(timeFormatter.parse(strTime, pos_time))).getTime());	

				//get price by using selected date and selected time
				for(ShowTime showtime: theater.getShowtimes()) {
					//compare year, month, day of two dates, compare hour, minute, second of two times
					if(showtime.getDate().getYear() == dateSelected.getYear()
							&& showtime.getDate().getMonth() == dateSelected.getMonth()
							&& showtime.getDate().getDay() == dateSelected.getDay()
							&& showtime.getTime().getHours() == timeSelected.getHours()
							&& showtime.getTime().getMinutes() == timeSelected.getMinutes()
							&& showtime.getTime().getSeconds() == timeSelected.getSeconds()) {
						double price = showtime.getPrice();
						gui.getBookingPage().getPrice().setText(String.valueOf(price));
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

		gui.getBookingPage().getUserInfo().getLogin().addActionListener(this); 
		gui.getBookingPage().getUserInfo().getRegister().addActionListener(this);
		gui.getBookingPage().getUserInfo().getNoAccount().addActionListener(this);
		gui.getBookingPage().getUserInfo().getMakePayment().addActionListener(this);

		//user info view for booking, the button of "Cancel Seat" is unavailabe.
		gui.getBookingPage().getUserInfo().getCancelSeat().setEnabled(false);
	}

	/**
	 * show user information view for cancellation
	 */
	private void showUserInfo_Cancellation() {
		gui.getCancelPage().setUserInfo(new UserInfoView());

		gui.getCancelPage().getUserInfo().getLogin().addActionListener(this);
		gui.getCancelPage().getUserInfo().getRegister().addActionListener(this);
		gui.getCancelPage().getUserInfo().getNoAccount().addActionListener(this);	
		gui.getCancelPage().getUserInfo().getCancelSeat().addActionListener(this);
		gui.getCancelPage().getUserInfo().getRenewFee().addActionListener(this);

		//user info view for cancellation, the button of "Make Payment" is unavailabe.
		gui.getCancelPage().getUserInfo().getMakePayment().setEnabled(false);
	}

	/**
	 * registered user login
	 * @throws InvalidPasswordException 
	 * @throws InvalidUsernameException 
	 * @throws InvalidBankException 
	 */
	private boolean login(ActionEvent e) throws InvalidUsernameException, InvalidPasswordException {
		//open userinfo view from booking
		if(gui.getBookingPage() != null && gui.getBookingPage().getUserInfo() != null && e.getSource() == gui.getBookingPage().getUserInfo().getLogin())
		{
			return loginFromBooking();
		}
		//open userinfo view from cancellation
		else {
			return loginFromCancellation();
		}
	}	


	/**
	 * register user
	 * @param e
	 * @throws InvalidBankException 
	 */
	private void registerUser(ActionEvent e) throws InvalidBankException {
		//open userinfo view from booking
		if(gui.getBookingPage() != null && gui.getBookingPage().getUserInfo() != null && e.getSource() == gui.getBookingPage().getUserInfo().getRegister())
		{
			String username = gui.getBookingPage().getUserInfo().getUserName().getText();
			String password = gui.getBookingPage().getUserInfo().getPassword().getText();			

			String name = gui.getBookingPage().getUserInfo().getNameOfUser().getText();
			String addr = gui.getBookingPage().getUserInfo().getAddr().getText();
			String bank = gui.getBookingPage().getUserInfo().getBank().getText();
			int card_no = Integer.valueOf(gui.getBookingPage().getUserInfo().getCard_no().getText());
			int expr_date = Integer.valueOf(gui.getBookingPage().getUserInfo().getExpr_date().getText());
			int cvv = Integer.valueOf(gui.getBookingPage().getUserInfo().getCvv().getText());
			String email = gui.getBookingPage().getUserInfo().getEmail().getText();

			//pay annual fee 20.00
			boolean payStat = bankManager.processTransaction(bank, card_no, expr_date, cvv, 20.00);
			if(payStat)//paid successfully
			{
				//register user
				RegisteredUser registeredUser = customerManager.registerUser(name, addr, bank, card_no, expr_date, cvv, email, username, password);
				//update feeRenewDate for register user
				if(registeredUser != null)
					registeredUser.paidAnnualFee();

				gui.getBookingPage().getUserInfo().getFeeRenewalStatus().setText("AnnualFee was deducted!");
				gui.getBookingPage().getUserInfo().getDisplay().setText("Registered User");	
			}	
		}
		//open userinfo view from cancellation
		else
		{
			String username = gui.getCancelPage().getUserInfo().getUserName().getText();
			String password = gui.getCancelPage().getUserInfo().getPassword().getText();			

			String name = gui.getCancelPage().getUserInfo().getNameOfUser().getText();
			String addr = gui.getCancelPage().getUserInfo().getAddr().getText();
			String bank = gui.getCancelPage().getUserInfo().getBank().getText();
			int card_no = Integer.valueOf(gui.getCancelPage().getUserInfo().getCard_no().getText());
			int expr_date = Integer.valueOf(gui.getCancelPage().getUserInfo().getExpr_date().getText());
			int cvv = Integer.valueOf(gui.getCancelPage().getUserInfo().getCvv().getText());
			String email = gui.getCancelPage().getUserInfo().getEmail().getText();

			//pay annual fee 20.00
			boolean payStat = bankManager.processTransaction(bank, card_no, expr_date, cvv, 20.00);
			if(payStat)//paid successfully
			{
				//register user
				RegisteredUser registeredUser = customerManager.registerUser(name, addr, bank, card_no, expr_date, cvv, email, username, password);
				//update feeRenewDate for register user
				if(registeredUser != null)
					registeredUser.paidAnnualFee();

				gui.getCancelPage().getUserInfo().getFeeRenewalStatus().setText("AnnualFee was deducted!");
				gui.getCancelPage().getUserInfo().getDisplay().setText("Registered User");	
			}
		}
	}

	/**
	 * create temporary user
	 * @param e
	 */
	private void createTempUser(ActionEvent e) throws NumberFormatException{
		//open userinfo view from booking
		if(gui.getBookingPage() != null && gui.getBookingPage().getUserInfo() != null && e.getSource() == gui.getBookingPage().getUserInfo().getNoAccount())
		{			
			String name = gui.getBookingPage().getUserInfo().getNameOfUser().getText();
			String addr = gui.getBookingPage().getUserInfo().getAddr().getText();
			String bank = gui.getBookingPage().getUserInfo().getBank().getText();
			long card_no = Long.valueOf(gui.getBookingPage().getUserInfo().getCard_no().getText());
			int expr_date = Integer.valueOf(gui.getBookingPage().getUserInfo().getExpr_date().getText());
			int cvv = Integer.valueOf(gui.getBookingPage().getUserInfo().getCvv().getText());
			String email = gui.getBookingPage().getUserInfo().getEmail().getText();

			customerManager.createTempUser(name, addr, bank, card_no, expr_date, cvv, email);

			gui.getBookingPage().getUserInfo().getUserType().setText("Temporary User");	
			gui.getBookingPage().getUserInfo().getDisplay().setText("Temporary User created successfully!");
		}
		//open userinfo view from cancellation
		else
		{
			String name = gui.getCancelPage().getUserInfo().getNameOfUser().getText();
			String addr = gui.getCancelPage().getUserInfo().getAddr().getText();
			String bank = gui.getCancelPage().getUserInfo().getBank().getText();
			int card_no = Integer.valueOf(gui.getCancelPage().getUserInfo().getCard_no().getText());
			int expr_date = Integer.valueOf(gui.getCancelPage().getUserInfo().getExpr_date().getText());
			int cvv = Integer.valueOf(gui.getCancelPage().getUserInfo().getCvv().getText());
			String email = gui.getCancelPage().getUserInfo().getEmail().getText();

			customerManager.createTempUser(name, addr, bank, card_no, expr_date, cvv, email);			

			gui.getCancelPage().getUserInfo().getUserType().setText("Temporary User");	
			gui.getCancelPage().getUserInfo().getDisplay().setText("Temporary User created successfully!");
		}
	}	

	/**
	 * process payment
	 * @param e
	 * @throws InvalidBankException 
	 */
	private void processPayment(ActionEvent e) throws InvalidBankException {

		String bank = gui.getBookingPage().getUserInfo().getBank().getText();
		long card_no = Long.valueOf(gui.getBookingPage().getUserInfo().getCard_no().getText());
		int expr_date = Integer.valueOf(gui.getBookingPage().getUserInfo().getExpr_date().getText());
		int cvv = Integer.valueOf(gui.getBookingPage().getUserInfo().getCvv().getText());
		double price = Double.valueOf(gui.getBookingPage().getPrice().getText());

		//pay booking fee
		boolean payStat = bankManager.processTransaction(bank, card_no, expr_date, cvv, price);
		if(payStat)//paid successfully
		{
			//get selected theater
			String theaterSelected = (String)(gui.getBookingPage().getTheaterList().getSelectedItem());	
			for(Theater theater: bookingManager.viewTheaters()) {
				if(theater.getName() == theaterSelected)
				{
					//get selected date
					String strDate = (String)(gui.getBookingPage().getDate().getSelectedItem());			
					SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
					ParsePosition pos_date = new ParsePosition(0);
					Date dateSelected = (Date)(dateFormatter.parse(strDate, pos_date));

					//get selected time
					String strTime = (String)(gui.getBookingPage().getTimeOfDate().getSelectedItem());	
					SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
					ParsePosition pos_time = new ParsePosition(0);
					Time timeSelected = new java.sql.Time(((Date)(timeFormatter.parse(strTime, pos_time))).getTime());	

					//get showtime
					for(ShowTime showtime: theater.getShowtimes()) {
						//compare year, month, day of two dates, compare hour, minute, second of two times
						if(showtime.getDate().getYear() == dateSelected.getYear()
								&& showtime.getDate().getMonth() == dateSelected.getMonth()
								&& showtime.getDate().getDay() == dateSelected.getDay()
								&& showtime.getTime().getHours() == timeSelected.getHours()
								&& showtime.getTime().getMinutes() == timeSelected.getMinutes()
								&& showtime.getTime().getSeconds() == timeSelected.getSeconds()) {
							
							//book a seat and get a ticket*******************************************
							String seatSelected = (String)(gui.getBookingPage().getSeatdisplay().getSelectedItem());
							String[] str = seatSelected.split(",");
							int row = Integer.valueOf(str[0]);
							int column = Integer.valueOf(str[1]);
							String userType = gui.getBookingPage().getUserInfo().getUserType().getText();
							MovieTicket movieTicket = showtime.selectSeat(row, column, bookingID, userType);
							
							if(movieTicket == null)
							{
								gui.getBookingPage().getUserInfo().getDisplay().setText("That seat is not available!");	
								return;
							}
							else
							{
								gui.getBookingPage().getUserInfo().getDisplay().setText("Paid Successfully!");	
							}
							
							bookingID += 1;//bookingID plus 1 if a seat was booked

							//show the ticket in booking view
							gui.getBookingPage().setDisplay(movieTicket.toString()); 

							//add the ticket to BookingManger
							bookingManager.addBooking(bookingID, movieTicket);	
							
							break;
						}			
					}
				}			
			}		
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
			gui.getBookingPage().getSearchShowTimes().addActionListener(this);
			gui.getBookingPage().getSelectSeat().addActionListener(this);
			gui.getBookingPage().getGetPrice().addActionListener(this);
			gui.getBookingPage().getProcessPayment().addActionListener(this);
		}
		else if(e.getSource() == gui.getCancellation()) {
			gui.setCancelPage(new CancellationView());

			gui.getCancelPage().getBookingID().addActionListener(this);
			
			gui.getCancelPage().getCancel().addActionListener(this);
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
		else if (gui.getBookingPage() != null && e.getSource() == gui.getBookingPage().getSearchShowTimes()) {
			viewShowTimes();
		}
		else if (gui.getBookingPage() != null && e.getSource() == gui.getBookingPage().getSelectSeat()) {
			viewSeat();
		}
		else if (gui.getBookingPage() != null && e.getSource() == gui.getBookingPage().getGetPrice()) {
			getPrice();
		}		
		else if (gui.getBookingPage() != null && e.getSource() == gui.getBookingPage().getProcessPayment()) {
			showUserInfo_Booking();
		}	
		else if (gui.getCancelPage() != null && e.getSource() == gui.getCancelPage().getCancel()) {
			if (validateBooking()) showUserInfo_Cancellation();
		}	
		//login from booking page
		else if((gui.getBookingPage() != null && gui.getBookingPage().getUserInfo() != null && e.getSource() == gui.getUserInfoFromBooking().getLogin())) {			
			gui.setBookingUserInfoDisplay("");
			gui.setBookingUserStatusDisplay("");
			try {
				login(e);
			} catch (InvalidUsernameException e1) {
				gui.setBookingUserInfoDisplay("Invalid username!");
			} catch (InvalidPasswordException e2) {
				gui.setBookingUserInfoDisplay("Invalid password!");
			}
		} 
		//login from cancellation page
		else if ((gui.getCancelPage() != null && gui.getUserInfoFromCancellation() != null && e.getSource() == gui.getUserInfoFromCancellation().getLogin())) {
			gui.setCancellationUserInfoDisplay("");
			gui.setCancellationUserStatusDisplay("");
			try {
				login(e);
			} catch (InvalidUsernameException e1) {
				gui.setCancellationUserInfoDisplay("Invalid username!");
			} catch (InvalidPasswordException e2) {
				gui.setCancellationUserInfoDisplay("Invalid password!");
			}
		} 
		//pay annual fee from booking page
		else if ((gui.getBookingPage() != null && gui.getUserInfoFromBooking() != null && e.getSource() == gui.getUserInfoFromBooking().getRenewFee())) {		
			if (gui.getUserInfoFromBooking().getFeeRenewalStatus().getText().equals("Renewal required")) {
				try {
					RegisteredUser regUser = getRegUser("Booking");
					boolean transactionStatus = bankManager.processTransaction(regUser.getFinancial_institution(), regUser.getCredit_card_no(),
						regUser.getCard_exp(), regUser.getCard_cvv(), 20.00);
					if (! transactionStatus) {
						gui.setBookingUserInfoDisplay("Credit Card not found!");
					} else {
						regUser.paidAnnualFee();
						Date renewDate = regUser.getFeeRenewDate();
						gui.setBookingUserInfoDisplay("Payment successfully processed!\nAccount valid until: " + renewDate);
					}
				} catch (InvalidUsernameException e1) {
					gui.setBookingUserInfoDisplay("Invalid username!");
				} catch (InvalidPasswordException e2) {
					gui.setBookingUserInfoDisplay("Invalid password!");
				} catch (InvalidBankException e2) {
					gui.setBookingUserInfoDisplay("Invalid bank name!");
				}
			}

		}		
		//pay annual fee from cancellation page
		else if ((gui.getCancelPage() != null && gui.getUserInfoFromCancellation() != null && e.getSource() == gui.getUserInfoFromCancellation().getRenewFee())) {
			if (gui.getUserInfoFromCancellation().getFeeRenewalStatus().getText().equals("Renewal required")) {
				try {
					RegisteredUser regUser = getRegUser("Cancellation");
					boolean transactionStatus = bankManager.processTransaction(regUser.getFinancial_institution(), regUser.getCredit_card_no(),
						regUser.getCard_exp(), regUser.getCard_cvv(), 20.00);
					if (! transactionStatus) {
						gui.setCancellationUserInfoDisplay("Credit Card not found!");
					} else {
						regUser.paidAnnualFee();
						Date renewDate = regUser.getFeeRenewDate();
						gui.setCancellationUserInfoDisplay("Payment successfully processed!\nAccount valid until: " + renewDate);
					}
				} catch (InvalidUsernameException e1) {
					gui.setCancellationUserInfoDisplay("Invalid username!");
				} catch (InvalidPasswordException e2) {
					gui.setCancellationUserInfoDisplay("Invalid password!");
				} catch (InvalidBankException e2) {
					gui.setCancellationUserInfoDisplay("Invalid bank name!");
				}
			}

		} 
		//register user from booking page
		else if(gui.getBookingPage() != null && gui.getUserInfoFromBooking() != null && e.getSource() == gui.getUserInfoFromBooking().getRegister()){
			try {
				registerUser(e);
			} catch (InvalidBankException e1) {
				gui.setBookingUserInfoDisplay("Invalid username!");
			} catch (Exception e1) {
				gui.setBookingUserInfoDisplay("Invalid user information!");
			}
		}
		//register user from cancellation page
		else if(gui.getCancelPage() != null && gui.getUserInfoFromCancellation() != null && e.getSource() == gui.getUserInfoFromCancellation().getRegister()){
			try {
				registerUser(e);
			} catch (InvalidBankException e1) {
				gui.setCancellationUserInfoDisplay("Invalid username!");
			} catch (Exception e1) {
				gui.setCancellationUserInfoDisplay("Invalid user information!");
			}
		}	
		//create temporary user from booking page
		else if(gui.getBookingPage() != null && gui.getUserInfoFromBooking() != null && e.getSource() == gui.getBookingPage().getUserInfo().getNoAccount()){
			try {
				createTempUser(e);
			} catch (Exception e1) {
				gui.setBookingUserInfoDisplay("Invalid user information!");
			}				
		}
		//create temporary user from cancellation page
		else if(gui.getCancelPage() != null && gui.getUserInfoFromCancellation() != null && e.getSource() == gui.getCancelPage().getUserInfo().getNoAccount()){
			try {
				createTempUser(e);
			} catch (Exception e1) {
				gui.setCancellationUserInfoDisplay("Invalid user information!");
			}				
		}	
		//make payment from booking page
		else if((gui.getBookingPage() != null && gui.getUserInfoFromBooking() != null && e.getSource() == gui.getBookingPage().getUserInfo().getMakePayment())){
			try {
				processPayment(e);
			} catch (InvalidBankException e1) {
				gui.setBookingUserInfoDisplay("Invalid user information!");
			}
		}
		//cancel ticket from cancellation page
		else if((gui.getCancelPage() != null && gui.getUserInfoFromCancellation() != null && e.getSource() == gui.getUserInfoFromCancellation().getCancelSeat())){
			gui.getUserInfoFromCancellation().dispose();
			cancelTicket(e);
		}
	}


	/**
	 * Check if booking exists and can be cancelled.
	 * @return Return true if booking exist and can be cancelled (cancellation request 72 hours before showtime) and false otherwise
	 */
	private boolean validateBooking() {
		String bookingID = gui.getBookingID().getText();
		if (! bookingID.matches("[0-9]{4}")) {
			gui.setCancellationDisplay("Invalid entry, enter your 4-digit booking ID!");
		}
		else {
			MovieTicket ticket = bookingManager.validateBooking(Integer.parseInt(bookingID));
			if (ticket == null) {
				gui.setCancellationDisplay("Not a valid booking ID!");
			} else {
				if(bookingManager.verifyCancellation(ticket)) {
					gui.setCancellationDisplay("Booking ID found!");
					return true;
				} else {
					gui.setCancellationDisplay("Cannot cancel booking within 72 hours to showtime!");
				}
			}
		}
		return false;
	}
	
	private RegisteredUser getRegUser(String fromPage) throws InvalidUsernameException,
	InvalidPasswordException {
		String username = "";
		String password = "";
		
		//login
		if(fromPage == "Booking")
		{
			username = gui.getUsernameFromBookingPage();
			password = gui.getPasswordFromBookingPage();			
		}
		else if(fromPage == "Cancellation")
		{
			username = gui.getUsernameFromCancelPage();
			password = gui.getPasswordFromCancelPage();
		}

		RegisteredUser regUser = customerManager.login(username, password);
		
		return regUser;
	}	
		
	private boolean loginFromBooking() throws InvalidUsernameException,
	InvalidPasswordException {
		
		RegisteredUser regUser = getRegUser("Booking");
		
		if(regUser != null)	{
			gui.getBookingPage().getUserInfo().getUserType().setText("Registered User");
			gui.getBookingPage().getUserInfo().getNameOfUser().setText(regUser.getName());
			gui.getBookingPage().getUserInfo().getAddr().setText(regUser.getName());
			gui.getBookingPage().getUserInfo().getBank().setText(regUser.getFinancial_institution());
			gui.getBookingPage().getUserInfo().getCard_no().setText(String.valueOf(regUser.getCredit_card_no()));
			gui.getBookingPage().getUserInfo().getExpr_date().setText(String.valueOf(regUser.getCard_exp()));
			gui.getBookingPage().getUserInfo().getCvv().setText(String.valueOf(regUser.getCard_cvv()));
			gui.getBookingPage().getUserInfo().getEmail().setText(regUser.getEmail());
			gui.getBookingPage().getUserInfo().getDisplay().setText("Registed User logined successfully!");
				
			//check checkFeeRenewal
			if(!regUser.checkFeeRenewal()) {//renewal is required
				gui.setBookingUserStatusDisplay("Renewal required");
				gui.setBookingUserInfoDisplay("Click Renew Fee Button to pay account fee of $20.00!");
				return false;
			} else {
				gui.setBookingUserStatusDisplay("Account active");
				return true;
			}
		}	
		return false;
	}	

	private boolean loginFromCancellation() throws InvalidUsernameException,
	InvalidPasswordException {
		
		RegisteredUser regUser = getRegUser("Cancellation");
		
		if(regUser != null)	{
			gui.getBookingPage().getUserInfo().getUserType().setText("Registered User");
			gui.getCancelPage().getUserInfo().getNameOfUser().setText(regUser.getName());
			gui.getCancelPage().getUserInfo().getAddr().setText(regUser.getName());
			gui.getCancelPage().getUserInfo().getBank().setText(regUser.getFinancial_institution());
			gui.getCancelPage().getUserInfo().getCard_no().setText(String.valueOf(regUser.getCredit_card_no()));
			gui.getCancelPage().getUserInfo().getExpr_date().setText(String.valueOf(regUser.getCard_exp()));
			gui.getCancelPage().getUserInfo().getCvv().setText(String.valueOf(regUser.getCard_cvv()));
			gui.getCancelPage().getUserInfo().getEmail().setText(regUser.getEmail());	
			gui.getCancelPage().getUserInfo().getDisplay().setText("Registed User logined successfully!");
			
			//check checkFeeRenewal
			if(!regUser.checkFeeRenewal()) {//renewal is required
				gui.setCancellationUserStatusDisplay("Renewal required");
				gui.setCancellationUserInfoDisplay("Click Renew Fee Button to pay account fee of $20.00!");
				return false;
			} else {
				gui.setCancellationUserStatusDisplay("Account active");
				return true;
			}
		}	
		return false;
	}

	/**
	 * cancel ticket
	 * @param e
	 */
	private void cancelTicket(ActionEvent e) {

		int booking_id = Integer.valueOf(gui.getBookingID().getText());

		MovieTicket movieTicket = bookingManager.validateBooking(booking_id);

		// booking already validated
		double credit = bookingManager.cancelTicket(movieTicket);
		
		gui.setCancellationDisplay("Cancelled successfully!\n" + customerManager.creatVoucher(credit));

	}	
}

	
