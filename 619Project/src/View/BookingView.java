package View;
/*
 *  BookingView.java
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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.Date;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;

import Model.ShowTime;

public class BookingView extends JFrame{
	private JButton viewMovie = new JButton("search Movie");
	private JTextField searchMovie = new JTextField(10);
	private JButton viewAllMovie = new JButton("view All Movies");
	private JComboBox<String> movieList = new JComboBox<String>();
	private JButton searchTheater = new JButton("  search Theater ");
	private JComboBox<String> theaterList = new JComboBox<String>();
	private JComboBox<String> date = new JComboBox<String>();
	private JComboBox<String> timeOfDate = new JComboBox<String>();
	private JButton searchShowTimes = new JButton("view ShowTimes");	
	private JButton selectSeat = new JButton("view Seat");
	private JButton[][] seatMap = new JButton[8][10];	
	private JButton getPrice = new JButton("get Price");
	private JTextField price = new JTextField(25);
	private JButton login = new JButton("Login");
	private JButton processPayment = new JButton("Process Payment");
	private JTextArea display = new JTextArea("");
		
	private UserInfoView userInfo;
	
	public BookingView() {	
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridLayout(8, 1, 2, 2));
		
		JPanel Panel_0 = new JPanel();
		JLabel titlePrompt = new JLabel("Booking Ticket");
		Panel_0.add(titlePrompt);
		contentPane.add(Panel_0);
		
		JPanel Panel_1 = new JPanel();
		JLabel moviePrompt = new JLabel("Movie Name:");
		Panel_1.add(moviePrompt);
		Panel_1.add(searchMovie);
		Panel_1.add(viewMovie);
		Panel_1.add(viewAllMovie);
		contentPane.add(Panel_1);
		
		JPanel Panel_2=new JPanel();
		ScrollPane movieSp=new ScrollPane();
		Panel_2.add(movieSp);
		movieSp.add(movieList);
		movieSp.setSize(428,40);
		contentPane.add(Panel_2);	

		JPanel Panel_4=new JPanel();
		ScrollPane theaterSp=new ScrollPane();
		Panel_4.add(theaterSp);
		theaterSp.add(theaterList);
		theaterSp.setSize(280,40);
		Panel_4.add(searchTheater);
		contentPane.add(Panel_4);
		
		JPanel Panel_5=new JPanel();
		JLabel datePrompt = new JLabel("Select Date:");
		Panel_5.add(datePrompt);
		date.addItem("2020-12-05");
		date.addItem("2020-12-06");
		date.addItem("2020-12-07");
		date.addItem("2020-12-08");
		date.addItem("2020-12-09");
		ScrollPane dateSp=new ScrollPane();
		Panel_5.add(dateSp);
		dateSp.add(date);
		dateSp.setSize(100,40);	
		ScrollPane timeSp=new ScrollPane();
		Panel_5.add(timeSp);
		timeSp.add(timeOfDate);
		timeSp.setSize(100,40);			
		Panel_5.add(searchShowTimes);
		Panel_5.add(selectSeat);
		contentPane.add(Panel_5);	
		
		
		JPanel Panel_8=new JPanel();
		Panel_8.setLayout(new GridLayout(seatMap.length, seatMap[0].length));	
		Panel_8.setPreferredSize(new Dimension(200, 200));
		for(int i = 0; i < seatMap.length; i++)
			for(int j = 0; j < seatMap[i].length; j++)
			{
				seatMap[i][j] = new JButton(); 
				seatMap[i][j].setEnabled(false);
				Panel_8.add(seatMap[i][j]);
			}		
		contentPane.add(Panel_8);				
		
		JPanel Panel_9=new JPanel();
		Panel_9.add(price);
		Panel_9.add(getPrice);
		Panel_9.add(processPayment);
		contentPane.add(Panel_9);	
		
		JPanel Panel_11=new JPanel();
		JLabel ticketPrompt = new JLabel("Ticket and Receipt:");
		Panel_11.add(ticketPrompt);
		ScrollPane ticketSp=new ScrollPane();
		Panel_11.add(ticketSp);
		ticketSp.add(display);
		ticketSp.setSize(320,80);			
		contentPane.add(Panel_11);		

		setTitle("Booking GUI");
		setSize(800, 800);  
		setLocation(200, 0);
		pack();
		setVisible(true);
	}

	public JButton getViewMovie() {
		return viewMovie;
	}
	
	public JButton getViewAllMovie() {
		return viewAllMovie;
	}	

	public JTextField getSearchMovie() {
		return searchMovie;
	}
	
	public JComboBox<String> getMovieList() {
		return movieList;
	}

	public JComboBox<String> getTheaterList() {
		return theaterList;
	}		

	public JButton getSearchTheater() {
		return searchTheater;
	}
	
	public JButton getSearchShowTimes() {
		return searchShowTimes;
	}	
		
	public JButton getSelectSeat() {
		return selectSeat;
	}		

	public JButton[][] getSeatMap(){
		return seatMap;
	}
	
	public JButton getGetPrice() {
		return getPrice;
	}	
	
	public JTextField getPrice() {
		return price;
	}	
	
	public JButton getProcessPayment() {
		return processPayment;
	}
	
	public UserInfoView getUserInfo() {
		return userInfo;
	}
	
	public void setUserInfo(UserInfoView user) {
		userInfo = user;
	}
	
	public JComboBox getDate() {
		return date;
	}
	
	public JComboBox getTimeOfDate() {
		return timeOfDate;
	}
	
	public void clearDisplay() {
		display.setText("");
	}

	public void setDisplay(String text) {
		display.setText(text);
	}

	public String getUserName() {
		return userInfo.getUserName().getText();
	}

	public String getPassword() {
		return userInfo.getPassword().getText();
	}

	public void clearUserDisplay() {
		userInfo.setDisplay("");
	}

	public void setUserDisplay(String text) {
		userInfo.setDisplay(text);
	}

	public void clearStatusDisplay() {
		userInfo.setStatus("");
	}

	public void setStatusDisplay(String text) {
		userInfo.setStatus(text);
	}
}
