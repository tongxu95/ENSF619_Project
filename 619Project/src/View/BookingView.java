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
	private JTextField theater = new JTextField(20);
	private JButton searchTheater = new JButton("search Theater");
	private JButton viewAllTheater = new JButton("view All Theaters");
	private JComboBox<String> theaterList = new JComboBox<String>();
	private JComboBox<String> theaterRoom = new JComboBox<String>();
	private JButton viewTheaterRoom = new JButton("view Theater Room");
	private JComboBox<String> date = new JComboBox<String>();
	private JComboBox<String> timeOfDate = new JComboBox<String>();
	private JButton searchShowTimes = new JButton("  view ShowTimes");	
	private JButton selectSeat = new JButton("         view Seat      ");
	private JButton[][] seatMap = new JButton[5][10];
	private JComboBox<String> seatdisplay = new JComboBox<String>();
	private JButton getPrice = new JButton("get Price");
	private JTextField price = new JTextField(25);
	private JButton login = new JButton("Login");
	private JButton processPayment = new JButton("Process Payment");
	private JTextArea ticket = new JTextArea("");
		
	private UserInfoView userInfo;
	
	public BookingView() {	
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridLayout(11, 1, 2, 2));
		
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
		
		JPanel Panel_3 = new JPanel();
		/*
		JLabel theaterPrompt = new JLabel("Input Theater Name:");
		Panel_3.add(theaterPrompt);
		Panel_3.add(theater);*/
		Panel_3.add(searchTheater);
		Panel_3.add(viewAllTheater);
		contentPane.add(Panel_3);
		

		JPanel Panel_4=new JPanel();
		ScrollPane theaterSp=new ScrollPane();
		Panel_4.add(theaterSp);
		theaterSp.add(theaterList);
		theaterSp.setSize(428,40);	
		contentPane.add(Panel_4);
			
		JPanel Panel_6_1=new JPanel();
		ScrollPane roomSp=new ScrollPane();
		Panel_6_1.add(roomSp);
		roomSp.add(theaterRoom);
		roomSp.setSize(280,40);	
		Panel_6_1.add(viewTheaterRoom);
		contentPane.add(Panel_6_1);	
		
		JPanel Panel_5=new JPanel();
		JLabel datePrompt = new JLabel("Select Date:");
		Panel_5.add(datePrompt);	
		Calendar calendar = new GregorianCalendar();		
		long time=System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		date.addItem(sdf.format(new Date(time)));		
		date.addItem(sdf.format(new Date(time + 86400000)));	
		date.addItem(sdf.format(new Date(time + 86400000*2)));
		date.addItem(sdf.format(new Date(time + 86400000*3)));
		date.addItem(sdf.format(new Date(time + 86400000*4)));
		date.addItem(sdf.format(new Date(time + 86400000*5)));
		date.addItem(sdf.format(new Date(time + 86400000*6)));
		ScrollPane dateSp=new ScrollPane();
		Panel_5.add(dateSp);
		dateSp.add(date);
		dateSp.setSize(100,40);	
		ScrollPane timeSp=new ScrollPane();
		Panel_5.add(timeSp);
		timeSp.add(timeOfDate);
		timeSp.setSize(100,40);			
		Panel_5.add(searchShowTimes);
		contentPane.add(Panel_5);		

		JPanel Panel_7=new JPanel();
		ScrollPane seatSp=new ScrollPane();
		Panel_7.add(seatSp);
		seatSp.add(seatdisplay);
		seatSp.setSize(280,40);	
		Panel_7.add(selectSeat);
		contentPane.add(Panel_7);		
/*		
		JPanel Panel_8=new JPanel();
		Panel_8.setLayout(new GridLayout(seatMap.length, seatMap[0].length));	
		Panel_8.setPreferredSize(new Dimension(200, 200));
		for(int i = 0; i < seatMap.length; i++)
			for(int j = 0; j < seatMap[i].length; j++)
			{
				seatMap[i][j] = new JButton(); 
				Panel_8.add(seatMap[i][j]);
			}		
		contentPane.add(Panel_8);
	*/		
		JPanel Panel_9=new JPanel();
		Panel_9.add(price);
		Panel_9.add(getPrice);
		contentPane.add(Panel_9);	
		
		JPanel Panel_10=new JPanel();
		Panel_10.add(processPayment);		
		contentPane.add(Panel_10);	
		
		JPanel Panel_11=new JPanel();
		JLabel ticketPrompt = new JLabel("Ticket and Receipt:");
		Panel_11.add(ticketPrompt);
		ScrollPane ticketSp=new ScrollPane();
		Panel_11.add(ticketSp);
		ticketSp.add(ticket);
		ticketSp.setSize(320,55);			
		contentPane.add(Panel_11);		

		setTitle("Booking GUI");
		setSize(800, 800);  
		setLocation(250, 0);
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

	public JTextField getTheater() {
		return theater;
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

	public JComboBox<String> getSeatdisplay() {
		return seatdisplay;
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

	public JButton getViewAllTheater() {
		return viewAllTheater;
	}

	public JComboBox<String> getTheaterRoom() {
		return theaterRoom;
	}

	public JButton getViewTheaterRoom() {
		return viewTheaterRoom;
	}	
	
	public JComboBox getDate() {
		return date;
	}
	
	public JComboBox getTimeOfDate() {
		return timeOfDate;
	}
	
	public JTextArea getTicket() {
		return ticket;
	}
}
