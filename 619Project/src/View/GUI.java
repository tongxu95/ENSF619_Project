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
	private CancellationView cancelPage;
	private BookingView bookingPage;
	
	private JButton booking = new JButton("Booking Ticket");
	private JButton cancellation = new JButton(" Cancel Ticket  ");
	
	public GUI() {		
		Container contentPane = getContentPane();
		BorderLayout layout = new BorderLayout();
		layout.setVgap(20);
		contentPane.setLayout(layout);
		
		JPanel Panel_1 = new JPanel();
		JLabel space_1 = new JLabel("    ");
		Panel_1.add(space_1);
		Panel_1.add(booking);
		JLabel space_2 = new JLabel("    ");
		Panel_1.add(space_2);
		contentPane.add("Center", Panel_1);
	
		
		JPanel Panel_2 = new JPanel();
		JLabel space_3 = new JLabel("    ");
		Panel_2.add(space_3);
		Panel_2.add(cancellation);
		JLabel space_4 = new JLabel("    ");
		Panel_2.add(space_4);
		contentPane.add("South", Panel_2);
			
		setTitle("Ticket Reservation System");
		setSize(300, 120);    
		setLocation(450, 300);
		setVisible(true);
	}
	
	public void displayBookingPage() {
		bookingPage = new BookingView();
	}
	
	public void displayCancellationPage() {
		cancelPage = new CancellationView();
	}	

	public CancellationView getCancelPage() {
		return cancelPage;
	}

	public void setCancelPage(CancellationView cancelPage) {
		this.cancelPage = cancelPage;
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
	
	public JTextField getBookingID() {
		return getCancelPage().getBookingID();
	}
	
	public void setCancellationDisplay(String text) {
		getCancelPage().clearDisplay();
		getCancelPage().setDisplay(text);
	}
}
