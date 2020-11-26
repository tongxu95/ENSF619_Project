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

import javax.swing.*;

public class BookingView extends JFrame{
	private JButton viewMovie = new JButton("search Movie");
	private JTextField searchMovie = new JTextField(10);
	private JButton viewAllMovie = new JButton("view All Movies");
	private JComboBox<String> movieList = new JComboBox<String>();
	private JTextField theater = new JTextField(20);
	private JButton searchTheater = new JButton("search Theater");
	private JComboBox<String> theaterList = new JComboBox<String>();
	private JComboBox<String> date = new JComboBox<String>();
	private JButton searchShowTimes = new JButton("search ShowTimes");
	private JComboBox<String> showTimes = new JComboBox<String>();	
	private JButton selectSeat = new JButton("select Seat");
	private JButton[][] seatMap = new JButton[5][10];
	private JComboBox<String> display = new JComboBox<String>();
	private JButton getPrice = new JButton("get Price");
	private JTextField price = new JTextField(10);
	private JButton login = new JButton("Login");
	private JButton processPayment = new JButton("Process Payment");
	private JTextArea ticket = new JTextArea();
		
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
		movieSp.setSize(400,40);
		contentPane.add(Panel_2);
		
		JPanel Panel_3 = new JPanel();
		JLabel theaterPrompt = new JLabel("Input Theater Name:");
		Panel_3.add(theaterPrompt);
		Panel_3.add(theater);
		Panel_3.add(searchTheater);
		contentPane.add(Panel_3);
		

		JPanel Panel_4=new JPanel();
		ScrollPane theaterSp=new ScrollPane();
		Panel_4.add(theaterSp);
		theaterSp.add(theaterList);
		theaterSp.setSize(400,40);	
		contentPane.add(Panel_4);
		
		JPanel Panel_5=new JPanel();
		JLabel datePrompt = new JLabel("Select Date:");
		Panel_5.add(datePrompt);
		date.addItem("2020-12-01");		
		date.addItem("2020-12-02");	
		date.addItem("2020-12-03");	
		date.addItem("2020-12-04");	
		date.addItem("2020-12-05");	
		date.addItem("2020-12-06");	
		date.addItem("2020-12-07");	
		ScrollPane showtimeSp=new ScrollPane();
		Panel_5.add(showtimeSp);
		showtimeSp.add(date);
		showtimeSp.setSize(200,40);	
		Panel_5.add(searchShowTimes);
		contentPane.add(Panel_5);
		
		JPanel Panel_6=new JPanel();
		ScrollPane timesSp=new ScrollPane();
		Panel_6.add(timesSp);
		timesSp.add(showTimes);
		timesSp.setSize(400,40);	
		contentPane.add(Panel_6);

		JPanel Panel_7=new JPanel();
		Panel_7.add(selectSeat);
		ScrollPane seatSp=new ScrollPane();
		Panel_7.add(seatSp);
		seatSp.add(display);
		seatSp.setSize(320,40);			
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
		Panel_9.add(getPrice);	
		Panel_9.add(price);	
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
		ticketSp.setSize(320,40);			
		contentPane.add(Panel_11);		

		setTitle("Booking GUI");
		setSize(800, 800);              
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
	
	public JComboBox<String> getShowTimes() {
		return showTimes;
	}
	
	public JButton getSelectSeat() {
		return selectSeat;
	}		

	public JComboBox<String> getDisplay() {
		return display;
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
}
