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

import View.GUI;
import View.UserInfoView;

public class ModelController implements ActionListener{
	private GUI gui;
	
	public ModelController(GUI guiIn) {
		gui = guiIn;
		
		gui.getBooking().addActionListener(this);
		gui.getBookingPage().getViewMovie().addActionListener(this);
		gui.getBookingPage().getViewAllMovie().addActionListener(this);
		gui.getBookingPage().getSearchTheater().addActionListener(this);
		gui.getBookingPage().getSearchShowTimes().addActionListener(this);
		gui.getBookingPage().getSelectSeat().addActionListener(this);
		gui.getBookingPage().getGetPrice().addActionListener(this);
		gui.getBookingPage().getProcessPayment().addActionListener(this);
		
		gui.getCancellation().addActionListener(this);		
		gui.getCancellPage().getCancel().addActionListener(this);
	}
	
	private void searchMovies() {
		
	}

	private void viewMovies() {
		gui.getBookingPage().getMovieList().addItem("1");
		gui.getBookingPage().getMovieList().addItem("2");
	}
	
	private void searchTheather() {
		gui.getBookingPage().getTheaterList().addItem("111");
	}
	
	private void getShowTimes() {
		gui.getBookingPage().getShowTimes().addItem("10:00:00");
		gui.getBookingPage().getShowTimes().addItem("11:00:00");
		gui.getBookingPage().getShowTimes().addItem("12:00:00");
	}
	
	private void displaySeat() {
		gui.getBookingPage().getDisplay().addItem("1,1");
		gui.getBookingPage().getDisplay().addItem("1,2");
		gui.getBookingPage().getDisplay().addItem("1,3");
		gui.getBookingPage().getDisplay().addItem("2,1");
		gui.getBookingPage().getDisplay().addItem("2,2");
		gui.getBookingPage().getDisplay().addItem("2,3");		
	}
	
	private void getPrice() {
		gui.getBookingPage().getPrice().setText("100");
	}		
	
	private void processPayment() {
		gui.getBookingPage().setUserInfo(new UserInfoView());
	}	
	
	private void cancelTicket() {
		gui.getBookingPage().setUserInfo(new UserInfoView());
	}	
		   
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getSource());
		if (e.getSource() == gui.getBookingPage().getViewMovie()) {
			searchMovies();
		}	
		else if (e.getSource() == gui.getBookingPage().getViewAllMovie()) {
			viewMovies();
		}	
		else if (e.getSource() == gui.getBookingPage().getSearchTheater()) {
			searchTheather();
		}
		else if (e.getSource() == gui.getBookingPage().getSearchShowTimes()) {
			getShowTimes();
		}
		else if (e.getSource() == gui.getBookingPage().getSelectSeat()) {
			displaySeat();
		}
		else if (e.getSource() == gui.getBookingPage().getGetPrice()) {
			getPrice();
		}		
		else if (e.getSource() == gui.getBookingPage().getProcessPayment()) {
			processPayment();
		}	
		else if (e.getSource() == gui.getCancellPage().getCancel()) {
			cancelTicket();
		}	
	}
	
	public static void main (String [] args) throws IOException {
		ModelController modelController = new ModelController(new GUI());
	}	
}
