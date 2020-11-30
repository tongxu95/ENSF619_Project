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
import java.util.Date;
import java.sql.Time;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

import CustomException.InvalidBankException;
import CustomException.InvalidPasswordException;
import CustomException.InvalidUsernameException;
import CustomerModel.CustomerManager;
import CustomerModel.RegisteredUser;
import CustomerModel.User;
import FinancialInstitute.BankManager;
import Model.BookingManager;
import Model.MovieTicket;
import Model.Movies;
import Model.Seat;
import Model.ShowTime;
import Model.Theater;
import View.BookingView;
import View.CancellationView;
import View.GUI;
import View.UserInfoView;

public class ModelController implements ActionListener{
	private GUI gui;
	private BookingManager bookingManager;
	private CustomerManager customerManager;
	private BankManager bankManager;
	private RegisteredUser regUser;
	private User tempUser;
	private MovieTicket myTicket;
	static int bookingID = 1024;

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

		gui.getUserInfoFromBooking().getLogin().addActionListener(this); 
		gui.getUserInfoFromBooking().getRegister().addActionListener(this);
		gui.getUserInfoFromBooking().getNoAccount().addActionListener(this);
		gui.getUserInfoFromBooking().getMakePayment().addActionListener(this);
		gui.getUserInfoFromBooking().getRenewFee().addActionListener(this); 

		//user info view for booking, the button of "Cancel Seat" is unavailabe.
		gui.getUserInfoFromBooking().getCancelSeat().setEnabled(false);
	}

	/**
	 * registered user login
	 * @throws InvalidPasswordException 
	 * @throws InvalidUsernameException 
	 * @throws InvalidBankException 
	 */
	private boolean login(ActionEvent e) throws InvalidUsernameException, InvalidPasswordException {
		//open userinfo view from booking
		if(gui.getBookingPage() != null && gui.getUserInfoFromBooking() != null && e.getSource() == gui.getUserInfoFromBooking().getLogin())
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
	 */
	private void registerUser() {
		//open userinfo view from booking
		if(gui.getBookingPage() != null && gui.getUserInfoFromBooking() != null)
		{
			String username = gui.getUserInfoFromBooking().getUserName().getText();
			String password = gui.getUserInfoFromBooking().getPassword().getText();			

			String name = gui.getUserInfoFromBooking().getNameOfUser().getText();
			String addr = gui.getUserInfoFromBooking().getAddr().getText();
			String bank = gui.getUserInfoFromBooking().getBank().getText();
			long card_no = Long.valueOf(gui.getUserInfoFromBooking().getCard_no().getText());
			int expr_date = Integer.valueOf(gui.getUserInfoFromBooking().getExpr_date().getText());
			int cvv = Integer.valueOf(gui.getUserInfoFromBooking().getCvv().getText());
			String email = gui.getUserInfoFromBooking().getEmail().getText();

			//register user
			regUser = customerManager.registerUser(name, addr, bank, card_no, expr_date, cvv, email, username, password);
			
			gui.getUserInfoFromBooking().getRegister().setEnabled(false);
			gui.getUserInfoFromBooking().getLogin().setEnabled(false);
			gui.getUserInfoFromBooking().getNoAccount().setEnabled(false);
			gui.getUserInfoFromBooking().getMakePayment().setEnabled(false);
			
			gui.setBookingUserStatusDisplay("Renewal required");
			gui.setBookingUserInfoDisplay("To activate account, must pay $20.00 annual fee.\nClick Renew Fee Button to continue.");
		}
	}

	/**
	 * process payment
	 * @param e
	 * @throws InvalidBankException 
	 */
	private void processPayment() throws InvalidBankException {

		String bank = gui.getUserInfoFromBooking().getBank().getText();
		long card_no = Long.valueOf(gui.getUserInfoFromBooking().getCard_no().getText());
		int expr_date = Integer.valueOf(gui.getUserInfoFromBooking().getExpr_date().getText());
		int cvv = Integer.valueOf(gui.getUserInfoFromBooking().getCvv().getText());
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
							String userType = gui.getUserInfoFromBooking().getUserType().getText();
							MovieTicket movieTicket = showtime.selectSeat(row, column, bookingID, userType);
							
							if(movieTicket == null)
							{
								gui.getUserInfoFromBooking().getDisplay().setText("That seat is not available!");
							}
							else
							{
								gui.getUserInfoFromBooking().getDisplay().setText("Paid Successfully!");	
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
			gui.getUserInfoFromBooking().dispose();
		} else {
			gui.setBookingUserInfoDisplay("Credit Card not found!");
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
			validateBooking();
			if (myTicket != null) {
				if (myTicket.getUserType().equals("Registered")) {
					showUserInfoCancelReg();
				} else {
					showUserInfoCancelNoAccount();
				}
			}
		}	
		//login from booking page
		else if((gui.getBookingPage() != null && gui.getUserInfoFromBooking() != null && e.getSource() == gui.getUserInfoFromBooking().getLogin())) {			
			try {
				login(e);
			} catch (InvalidUsernameException e1) {
				gui.setBookingUserInfoDisplay("Invalid username!");
			} catch (InvalidPasswordException e2) {
				gui.setBookingUserInfoDisplay("Invalid password!");
			}
		} 
		//login from cancellation page
		else if ((gui.getCancelPage() != null && gui.getUserInfoCancel() != null && e.getSource() == gui.getUserInfoCancel().getLogin())) {	
			try {
				login(e);
			} catch (InvalidUsernameException e1) {
				gui.setCancelUserInfoDisplay("Invalid username!");
			} catch (InvalidPasswordException e2) {
				gui.setCancelUserInfoDisplay("Invalid password!");
			}
		} 
		//pay annual fee from booking page
		else if ((gui.getBookingPage() != null && gui.getUserInfoFromBooking() != null && e.getSource() == gui.getUserInfoFromBooking().getRenewFee())) {		
			if (gui.getUserInfoFromBooking().getFeeRenewalStatus().getText().equals("Renewal required")) {
				try {
					boolean transactionStatus = bankManager.processTransaction(regUser.getFinancial_institution(), regUser.getCredit_card_no(),
						regUser.getCard_exp(), regUser.getCard_cvv(), 20.00);
					if (! transactionStatus) {
						gui.setBookingUserInfoDisplay("Credit Card not found!");
					} else {
						gui.getUserInfoFromBooking().getMakePayment().setEnabled(true);
						regUser.paidAnnualFee();
						Date renewDate = regUser.getFeeRenewDate();
						gui.setBookingUserInfoDisplay("Payment successfully processed!\nAccount valid until: " + renewDate);
					}
				} catch (InvalidBankException e2) {
					gui.setBookingUserInfoDisplay("Invalid bank name!");
				}
			}

		}		
		//pay annual fee from cancellation page
		else if ((gui.getCancelPage() != null && gui.getUserInfoCancel() != null && e.getSource() == gui.getUserInfoCancel().getRenewFee())) {
			if (gui.getUserInfoCancel().getFeeRenewalStatus().getText().equals("Renewal required")) {
				try {
					boolean transactionStatus = bankManager.processTransaction(regUser.getFinancial_institution(), regUser.getCredit_card_no(),
						regUser.getCard_exp(), regUser.getCard_cvv(), 20.00);
					if (! transactionStatus) {
						gui.setCancelUserInfoDisplay("Credit Card not found!");
					} else {
						gui.getUserInfoCancel().getCancelSeat().setEnabled(true);
						regUser.paidAnnualFee();
						Date renewDate = regUser.getFeeRenewDate();
						gui.setCancelUserInfoDisplay("Payment successfully processed!\nAccount valid until: " + renewDate);
					}
				} catch (InvalidBankException e2) {
					gui.setCancelUserInfoDisplay("Invalid bank name!");
				}
			}

		} 
		//register user from booking page
		else if(gui.getBookingPage() != null && gui.getUserInfoFromBooking() != null && e.getSource() == gui.getUserInfoFromBooking().getRegister()){
			registerUser();
		}
		//create temporary user from booking page
		else if(gui.getBookingPage() != null && gui.getUserInfoFromBooking() != null && e.getSource() == gui.getUserInfoFromBooking().getNoAccount()){
			try {
				createTempUser(e);
			} catch (Exception e1) {
				gui.setBookingUserInfoDisplay("Invalid user information!");
			}				
		}
		//create temporary user from cancellation page
		else if(gui.getCancelPage() != null && gui.getUserInfoCancel() != null && e.getSource() == gui.getCancelPage().getUserInfo().getNoAccount()){
			try {
				createTempUser(e);
			} catch (Exception e1) {
				gui.setCancelUserInfoDisplay("Invalid user information!");
			}				
		}	
		//make payment from booking page
		else if((gui.getBookingPage() != null && gui.getUserInfoFromBooking() != null && e.getSource() == gui.getUserInfoFromBooking().getMakePayment())){
			try {
				processPayment();
			} catch (InvalidBankException e1) {
				gui.setBookingUserInfoDisplay("Invalid bank information!");
			}
		}
		//cancel ticket from cancellation page
		else if((gui.getCancelPage() != null && gui.getUserInfoCancel() != null && e.getSource() == gui.getUserInfoCancel().getCancelSeat())){
			gui.getUserInfoCancel().dispose();
			cancelTicket(e);
		}
	}


	/**
	 * Check if booking exists and can be cancelled.
	 */
	private void validateBooking() {
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
					myTicket = ticket;
				} else {
					gui.setCancellationDisplay("Cannot cancel booking within 72 hours to showtime!");
				}
			}
		}
	}
	
	private void getRegUser(String fromPage) throws InvalidUsernameException,
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

		regUser = customerManager.login(username, password);
		
	}	
		
	private boolean loginFromBooking() throws InvalidUsernameException,
	InvalidPasswordException {
		
		getRegUser("Booking");
		
		if(regUser != null)	{
			gui.getUserInfoFromBooking().getUserType().setText("Registered User");
			gui.getUserInfoFromBooking().getNameOfUser().setText(regUser.getName());
			gui.getUserInfoFromBooking().getAddr().setText(regUser.getName());
			gui.getUserInfoFromBooking().getBank().setText(regUser.getFinancial_institution());
			gui.getUserInfoFromBooking().getCard_no().setText(String.valueOf(regUser.getCredit_card_no()));
			gui.getUserInfoFromBooking().getExpr_date().setText(String.valueOf(regUser.getCard_exp()));
			gui.getUserInfoFromBooking().getCvv().setText(String.valueOf(regUser.getCard_cvv()));
			gui.getUserInfoFromBooking().getEmail().setText(regUser.getEmail());
			gui.getUserInfoFromBooking().getDisplay().setText("Registed User logined successfully!");
				
			//check checkFeeRenewal
			if(!regUser.checkFeeRenewal()) {//renewal is required
				gui.setBookingUserStatusDisplay("Renewal required");
				gui.setBookingUserInfoDisplay("Click Renew Fee Button to pay account fee of $20.00!");
				gui.getUserInfoFromBooking().getMakePayment().setEnabled(false);
				gui.getUserInfoFromBooking().getRegister().setEnabled(false);
				gui.getUserInfoFromBooking().getNoAccount().setEnabled(false);
				return false;
			} else {
				gui.setBookingUserStatusDisplay("Account active");
				gui.getUserInfoFromBooking().getRegister().setEnabled(false);
				gui.getUserInfoFromBooking().getNoAccount().setEnabled(false);
				return true;
			}
		}	
		return false;
	}	

	private boolean loginFromCancellation() throws InvalidUsernameException,
	InvalidPasswordException {
		
		getRegUser("Cancellation");
		
		if(regUser != null)	{
			gui.getUserInfoCancel().getUserType().setText("Registered User");
			gui.getUserInfoCancel().getNameOfUser().setText(regUser.getName());
			gui.getUserInfoCancel().getAddr().setText(regUser.getName());
			gui.getUserInfoCancel().getBank().setText(regUser.getFinancial_institution());
			gui.getUserInfoCancel().getCard_no().setText(String.valueOf(regUser.getCredit_card_no()));
			gui.getUserInfoCancel().getExpr_date().setText(String.valueOf(regUser.getCard_exp()));
			gui.getUserInfoCancel().getCvv().setText(String.valueOf(regUser.getCard_cvv()));
			gui.getUserInfoCancel().getEmail().setText(regUser.getEmail());	
			gui.setCancelUserInfoDisplay("Registed User logined successfully!");
			
			//check checkFeeRenewal
			if(!regUser.checkFeeRenewal()) {//renewal is required
				gui.setCancelUserStatusDisplay("Renewal required");
				gui.setCancelUserInfoDisplay("Click Renew Fee Button to pay account fee of $20.00!");
				gui.getUserInfoCancel().getCancelSeat().setEnabled(false);
				return false;
			} else {
				gui.setCancelUserStatusDisplay("Account active");
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

		// booking already validated
		double credit = bookingManager.cancelTicket(myTicket);
		String voucher = customerManager.creatVoucher(credit);
		
		if (myTicket.getUserType().equals("Registered")) {
			gui.setCancellationDisplay("Cancelled successfully!\n" + regUser.sendVoucher(voucher));
		} else {
			gui.setCancellationDisplay("Cancelled successfully!\n" + tempUser.sendVoucher(voucher));
		}

	}	

	/**
	 * show user information view for cancellation
	 */
	private void showUserInfoCancelReg() {
		gui.getCancelPage().setUserInfo(new UserInfoView());

		gui.getUserInfoCancel().getLogin().addActionListener(this);
		gui.getUserInfoCancel().getCancelSeat().addActionListener(this);
		gui.getUserInfoCancel().getRenewFee().addActionListener(this);

		//user info view for cancellation, the button of "Make Payment", "Register", "No Account" are unavailabe.
		gui.getUserInfoCancel().getMakePayment().setEnabled(false);
		gui.getUserInfoCancel().getRegister().setEnabled(false);
		gui.getUserInfoCancel().getNoAccount().setEnabled(false);	
		
		gui.setCancelUserInfoDisplay("Please login.");
	}
	
	/**
	 * show user information view for cancellation
	 */
	private void showUserInfoCancelNoAccount() {
		gui.getCancelPage().setUserInfo(new UserInfoView());

		gui.getUserInfoCancel().getNoAccount().addActionListener(this);
		gui.getUserInfoCancel().getCancelSeat().addActionListener(this);
		gui.getUserInfoCancel().getRenewFee().addActionListener(this);

		//user info view for cancellation, the button of "Make Payment", "Register", "No Account" are unavailabe.
		gui.getUserInfoCancel().getMakePayment().setEnabled(false);
		gui.getUserInfoCancel().getRegister().setEnabled(false);
		gui.getUserInfoCancel().getLogin().setEnabled(false);
		gui.getUserInfoCancel().getRenewFee().setEnabled(false);
		
		gui.setCancelUserInfoDisplay("Please enter your information.");
	}
	
	/**
	 * create temporary user
	 * @param e
	 */
	private User createTempUser(ActionEvent e) throws NumberFormatException {
		//open userinfo view from booking
		if(gui.getBookingPage() != null && gui.getUserInfoFromBooking() != null && e.getSource() == gui.getUserInfoFromBooking().getNoAccount())
		{			
			String name = gui.getUserInfoFromBooking().getNameOfUser().getText();
			String addr = gui.getUserInfoFromBooking().getAddr().getText();
			String bank = gui.getUserInfoFromBooking().getBank().getText();
			long card_no = Long.valueOf(gui.getUserInfoFromBooking().getCard_no().getText());
			int expr_date = Integer.valueOf(gui.getUserInfoFromBooking().getExpr_date().getText());
			int cvv = Integer.valueOf(gui.getUserInfoFromBooking().getCvv().getText());
			String email = gui.getUserInfoFromBooking().getEmail().getText();

			tempUser = customerManager.createTempUser(name, addr, bank, card_no, expr_date, cvv, email);
			gui.getUserInfoFromBooking().getLogin().setEnabled(false);
			gui.getUserInfoFromBooking().getRegister().setEnabled(false);
			gui.getUserInfoFromBooking().getUserType().setText("Temporary User");	
			gui.getUserInfoFromBooking().getDisplay().setText("Temporary User created successfully!");
		}
		//open userinfo view from cancellation
		else
		{
			String name = gui.getUserInfoCancel().getNameOfUser().getText();
			String addr = gui.getUserInfoCancel().getAddr().getText();
			String bank = gui.getUserInfoCancel().getBank().getText();
			long card_no = Long.valueOf(gui.getUserInfoCancel().getCard_no().getText());
			int expr_date = Integer.valueOf(gui.getUserInfoCancel().getExpr_date().getText());
			int cvv = Integer.valueOf(gui.getUserInfoCancel().getCvv().getText());
			String email = gui.getUserInfoCancel().getEmail().getText();

			tempUser = customerManager.createTempUser(name, addr, bank, card_no, expr_date, cvv, email);			
			gui.getUserInfoCancel().getUserType().setText("Temporary User");	
			gui.setCancelUserInfoDisplay("Temporary User created successfully!");
		}
		return tempUser;
	}	
	
	
}