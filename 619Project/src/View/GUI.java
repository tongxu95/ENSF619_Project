package View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 *  GUI.java
 *  package: Views
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

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame{
	private CancellationView cancellPage;
	private BookingView bookingPage;
	
	private JButton booking = new JButton("Booking");
	private JButton cancellation = new JButton("Cancellation");
	
	public GUI() {	
		bookingPage = new BookingView();
		cancellPage = new CancellationView();
	}
	
	public void displayBookingPage() {
		bookingPage = new BookingView();
	}
	
	public void displayCancellationPage() {
		cancellPage = new CancellationView();
	}	

	public CancellationView getCancellPage() {
		return cancellPage;
	}

	public void setCancellPage(CancellationView cancellPage) {
		this.cancellPage = cancellPage;
	}

	public BookingView getBookingPage() {
		return bookingPage;
	}

	public void setBookingPage(BookingView bookingPage) {
		this.bookingPage = bookingPage;
	}

	public JButton getBooking() {
		return booking;
	}

	public void setBooking(JButton booking) {
		this.booking = booking;
	}

	public JButton getCancellation() {
		return cancellation;
	}

	public void setCancellation(JButton cancellation) {
		this.cancellation = cancellation;
	}
}
