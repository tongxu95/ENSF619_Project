package View;
/*
 *  CancellationView
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class CancellationView extends JFrame{
	private JTextField bookingID = new JTextField(20); 
	private JButton cancel = new JButton("Cancel Ticket");
	private JTextArea display = new JTextArea("");
	
	private UserInfoView userInfo;
	
	public CancellationView() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridLayout(5, 1, 2, 2));
		
		JPanel Panel_0 = new JPanel();
		JLabel titlePrompt = new JLabel("Cancel Ticket");
		Panel_0.add(titlePrompt);
		contentPane.add(Panel_0);
		
		JPanel Panel_1 = new JPanel();
		JLabel bookingPrompt = new JLabel("BookingID:");
		Panel_1.add(bookingPrompt);
		Panel_1.add(bookingID);
		contentPane.add(Panel_1);

		JPanel Panel_2 = new JPanel();
		Panel_2.add(cancel);
		contentPane.add(Panel_2);	
		
		JPanel Panel_3=new JPanel();
		JLabel messagePrompt = new JLabel("Message:");	
		Panel_3.add(messagePrompt);	
		ScrollPane messageSp=new ScrollPane();
		Panel_3.add(messageSp);
		messageSp.add(display);
		messageSp.setSize(200,200);	
		contentPane.add(Panel_3);	
		
		setTitle("Cancellation GUI"); 
		setLocation(400, 200);
		pack();
		setVisible(true);		
	}
	
	public JTextField getBookingID() {
		return bookingID;
	}
	
	public JTextArea getDisplay() {
		return display;
	}

	public UserInfoView getUserInfo() {
		return userInfo;
	}
	
	public void setUserInfo(UserInfoView user) {
		userInfo = user;
	}	
	
	public JButton getCancel() {
		return cancel;
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
