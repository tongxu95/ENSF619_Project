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

		//get selected theater
		String theaterSelectedName = (String)(gui.getBookingPage().getTheaterList().getSelectedItem());	

		for(Theater theater: bookingManager.viewTheaters()) {
			if(theater.getName() == theaterSelectedName){
				//get theater room by selected theater
				for(TheaterRoom room: theater.getTheaterRooms()) {
					gui.getBookingPage().getTheaterRoom().addItem(String.valueOf(room.getNumber()));
				}
				break;
			}
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

		//get selected theater's name in the view
		String theaterSelected = (String)(gui.getBookingPage().getTheaterList().getSelectedItem());	
		//get selected theater room's number in the view
		String theaterRoomSelected = (String)(gui.getBookingPage().getTheaterRoom().getSelectedItem());	

		//get seatmap by using selected theater and selected theater room
		for(Theater theater: bookingManager.viewTheaters()) {
			if(theater.getName() == theaterSelected){			
				for(TheaterRoom theaterRoom: theater.getTheaterRooms()) {			
					if(theaterRoom.getNumber() == Integer.valueOf(theaterRoomSelected)) {
						for(Seat[] seat: theaterRoom.getSeatArrangement().displaySeats()) {
							gui.getBookingPage().getSeatdisplay().addItem(seat[0]+","+seat[1]);
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
		gui.getCancellPage().setUserInfo(new UserInfoView());

		gui.getCancellPage().getUserInfo().getLogin().addActionListener(this);
		gui.getCancellPage().getUserInfo().getRegister().addActionListener(this);
		gui.getCancellPage().getUserInfo().getNoAccount().addActionListener(this);	
		gui.getCancellPage().getUserInfo().getCancelSeat().addActionListener(this);

		//user info view for cancellation, the button of "Make Payment" is unavailabe.
		gui.getCancellPage().getUserInfo().getMakePayment().setEnabled(false);
	}

	/**
	 * registered user login
	 * @throws InvalidPasswordException 
	 * @throws InvalidUsernameException 
	 * @throws InvalidBankException 
	 */
	private void login(ActionEvent e) throws InvalidUsernameException, InvalidPasswordException, InvalidBankException{
		//open userinfo view from booking
		if(gui.getBookingPage() != null && gui.getBookingPage().getUserInfo() != null && e.getSource() == gui.getBookingPage().getUserInfo().getLogin())
		{
			//login
			String username = gui.getBookingPage().getUserInfo().getUserName().getText();
			String password = gui.getBookingPage().getUserInfo().getPassword().getText();
			RegisteredUser regestedUser = customerManager.login(username, password);

			if(regestedUser != null)
			{
				//check checkFeeRenewal
				if(!regestedUser.checkFeeRenewal())//renewal is required
				{
					//pay annual fee
					bankManager.processTransaction(regestedUser.getFinancial_institution(), regestedUser.getCredit_card_no(),
							regestedUser.getCard_exp(), regestedUser.getCard_cvv(), 20.00);
					gui.getBookingPage().getUserInfo().getFeeRenewalStatus().setText("AnnualFee was deducted!");
				}
				else
				{
					gui.getBookingPage().getUserInfo().getFeeRenewalStatus().setText("Account is still active!");
				}

				gui.getBookingPage().getUserInfo().getDisplay().setText("Registered User");	
			}
		}
		//open userinfo view from cancellation
		else
		{
			//login
			String username = gui.getCancellPage().getUserInfo().getUserName().getText();
			String password = gui.getCancellPage().getUserInfo().getPassword().getText();
			RegisteredUser regestedUser = customerManager.login(username, password);

			if(regestedUser != null)
			{
				//check checkFeeRenewal
				if(!regestedUser.checkFeeRenewal())//renewal is required
				{
					//pay annual fee
					bankManager.processTransaction(regestedUser.getFinancial_institution(), regestedUser.getCredit_card_no(),
							regestedUser.getCard_exp(), regestedUser.getCard_cvv(), 20.00);
					gui.getCancellPage().getUserInfo().getFeeRenewalStatus().setText("AnnualFee was deducted!");
				}
				else
				{
					gui.getCancellPage().getUserInfo().getFeeRenewalStatus().setText("Account is still active!");
				}

				gui.getCancellPage().getUserInfo().getDisplay().setText("Registered User");	
			}	
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
			String username = gui.getCancellPage().getUserInfo().getUserName().getText();
			String password = gui.getCancellPage().getUserInfo().getPassword().getText();			

			String name = gui.getCancellPage().getUserInfo().getNameOfUser().getText();
			String addr = gui.getCancellPage().getUserInfo().getAddr().getText();
			String bank = gui.getCancellPage().getUserInfo().getBank().getText();
			int card_no = Integer.valueOf(gui.getCancellPage().getUserInfo().getCard_no().getText());
			int expr_date = Integer.valueOf(gui.getCancellPage().getUserInfo().getExpr_date().getText());
			int cvv = Integer.valueOf(gui.getCancellPage().getUserInfo().getCvv().getText());
			String email = gui.getCancellPage().getUserInfo().getEmail().getText();

			//pay annual fee 20.00
			boolean payStat = bankManager.processTransaction(bank, card_no, expr_date, cvv, 20.00);
			if(payStat)//paid successfully
			{
				//register user
				RegisteredUser registeredUser = customerManager.registerUser(name, addr, bank, card_no, expr_date, cvv, email, username, password);
				//update feeRenewDate for register user
				if(registeredUser != null)
					registeredUser.paidAnnualFee();

				gui.getCancellPage().getUserInfo().getFeeRenewalStatus().setText("AnnualFee was deducted!");
				gui.getCancellPage().getUserInfo().getDisplay().setText("Registered User");	
			}
		}
	}

	/**
	 * create temporary user
	 * @param e
	 */
	private void createTempUser(ActionEvent e) {
		//open userinfo view from booking
		if(gui.getBookingPage() != null && gui.getBookingPage().getUserInfo() != null && e.getSource() == gui.getBookingPage().getUserInfo().getNoAccount())
		{
			String name = gui.getBookingPage().getUserInfo().getNameOfUser().getText();
			String addr = gui.getBookingPage().getUserInfo().getAddr().getText();
			String bank = gui.getBookingPage().getUserInfo().getBank().getText();
			int card_no = Integer.valueOf(gui.getBookingPage().getUserInfo().getCard_no().getText());
			int expr_date = Integer.valueOf(gui.getBookingPage().getUserInfo().getExpr_date().getText());
			int cvv = Integer.valueOf(gui.getBookingPage().getUserInfo().getCvv().getText());
			String email = gui.getBookingPage().getUserInfo().getEmail().getText();

			customerManager.createTempUser(name, addr, bank, card_no, expr_date, cvv, email);

			gui.getBookingPage().getUserInfo().getDisplay().setText("Temporary User");	
		}
		//open userinfo view from cancellation
		else
		{
			String name = gui.getCancellPage().getUserInfo().getNameOfUser().getText();
			String addr = gui.getCancellPage().getUserInfo().getAddr().getText();
			String bank = gui.getCancellPage().getUserInfo().getBank().getText();
			int card_no = Integer.valueOf(gui.getCancellPage().getUserInfo().getCard_no().getText());
			int expr_date = Integer.valueOf(gui.getCancellPage().getUserInfo().getExpr_date().getText());
			int cvv = Integer.valueOf(gui.getCancellPage().getUserInfo().getCvv().getText());
			String email = gui.getCancellPage().getUserInfo().getEmail().getText();

			customerManager.createTempUser(name, addr, bank, card_no, expr_date, cvv, email);			

			gui.getCancellPage().getUserInfo().getDisplay().setText("Temporary User");	
		}
	}	

	/**
	 * process payment
	 * @param e
	 * @throws InvalidBankException 
	 */
	private void processPayment(ActionEvent e) throws InvalidBankException {

		String bank = gui.getBookingPage().getUserInfo().getBank().getText();
		int card_no = Integer.valueOf(gui.getBookingPage().getUserInfo().getCard_no().getText());
		int expr_date = Integer.valueOf(gui.getBookingPage().getUserInfo().getExpr_date().getText());
		int cvv = Integer.valueOf(gui.getBookingPage().getUserInfo().getCvv().getText());
		double price = Double.valueOf(gui.getBookingPage().getPrice().getText());

		//pay booking fee
		boolean payStat = bankManager.processTransaction(bank, card_no, expr_date, cvv, price);
		if(payStat)//paid successfully
		{
			//get selected theater's name in the view
			String theaterSelected = (String)(gui.getBookingPage().getTheaterList().getSelectedItem());	
			//get selected theater room's number in the view
			String theaterRoomSelected = (String)(gui.getBookingPage().getTheaterRoom().getSelectedItem());	

			//get seatmap by using selected theater and selected theater room
			for(Theater theater: bookingManager.viewTheaters()) {
				if(theater.getName() == theaterSelected){		
					for(TheaterRoom theaterRoom: theater.getTheaterRooms()) {			
						if(theaterRoom.getNumber() == Integer.valueOf(theaterRoomSelected)) {
							//book a seat************************************************************
							String seatSelected = (String)(gui.getBookingPage().getSeatdisplay().getSelectedItem());
							String[] str = seatSelected.split(",");
							int row = Integer.valueOf(str[0]);
							int column = Integer.valueOf(str[1]);
							theaterRoom.getSeatArrangement().bookSeat(row, column, bookingID);
							bookingID += 1;//bookingID plus 1 if a seat was booked

							//create a ticket*********************************************************
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

									//get user type from view
									String userType = gui.getBookingPage().getUserInfo().getDisplay().getText();
									MovieTicket movieTicket = new MovieTicket(showtime, row, column, bookingID, userType);

									//show the ticket in booking view
									gui.getBookingPage().getTicket().setText(movieTicket.toString());

									//add the ticket to BookingManger
									bookingManager.addBooking(bookingID, movieTicket);							
								}			
							}
						}
					}					
				}
			}		
		}
		gui.getBookingPage().getUserInfo().getDisplay().setText("Paid Successfully!");	
	}	

	/**
	 * cancel ticket
	 * @param e
	 */
	private void cancelTicket(ActionEvent e) {

		int booking_id = Integer.valueOf(gui.getCancellPage().getBookingID().getText());
		//check if booking exist
		MovieTicket movieTicket = bookingManager.validateBooking(booking_id);
		if(movieTicket != null) {
			//check if the cancellation request is made up to 72 hours prior to the show
			if(bookingManager.verifyCancellation(movieTicket)) {
				//cancel the ticket
				bookingManager.cancelTicket(movieTicket);
				gui.getCancellPage().getDisplay().setText("Cancelled successfully");
			}
			else
				gui.getCancellPage().getDisplay().setText("Cancelled unsuccessfully");
		}
		else
			gui.getCancellPage().getDisplay().setText("Cancelled unsuccessfully");
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
		else if (gui.getCancellPage() != null && e.getSource() == gui.getCancellPage().getCancel()) {
			showUserInfo_Cancellation();
		}	
		else if((gui.getBookingPage() != null && gui.getBookingPage().getUserInfo() != null && e.getSource() == gui.getBookingPage().getUserInfo().getLogin())
				|| (gui.getCancellPage() != null && gui.getCancellPage().getUserInfo() != null && e.getSource() == gui.getCancellPage().getUserInfo().getLogin())) {
			try {
				login(e);
			} catch (InvalidUsernameException e1) {
				e1.printStackTrace();
			} catch (InvalidPasswordException e1) {
				e1.printStackTrace();
			} catch (InvalidBankException e1) {
				e1.printStackTrace();
			}
		}	
		else if((gui.getBookingPage() != null && gui.getBookingPage().getUserInfo() != null && e.getSource() == gui.getBookingPage().getUserInfo().getRegister())
				|| (gui.getCancellPage() != null && gui.getCancellPage().getUserInfo() != null && e.getSource() == gui.getCancellPage().getUserInfo().getRegister())){
			try {
				registerUser(e);
			} catch (InvalidBankException e1) {
				e1.printStackTrace();
			}
		}
		else if((gui.getBookingPage() != null && gui.getBookingPage().getUserInfo() != null && e.getSource() == gui.getBookingPage().getUserInfo().getNoAccount())
				|| (gui.getCancellPage() != null && gui.getCancellPage().getUserInfo() != null && e.getSource() == gui.getCancellPage().getUserInfo().getNoAccount())){
			createTempUser(e);
		}
		else if((gui.getBookingPage() != null && gui.getBookingPage().getUserInfo() != null && e.getSource() == gui.getBookingPage().getUserInfo().getMakePayment())){
			try {
				processPayment(e);
			} catch (InvalidBankException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if((gui.getCancellPage() != null && gui.getCancellPage().getUserInfo() != null && e.getSource() == gui.getCancellPage().getUserInfo().getCancelSeat())){
			cancelTicket(e);
		}
	}


}
