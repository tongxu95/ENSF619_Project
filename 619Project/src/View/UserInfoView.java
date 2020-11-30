package View;
/*
 *  UserInfoView.java
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

public class UserInfoView extends JFrame{
	private JTextField userName = new JTextField(10);
	private JTextField password = new JTextField(10);
	private JButton login = new JButton("Login");
	private JTextField feeRenewalStatus = new JTextField(10);
	private JLabel userType = new JLabel("");
	private JTextField nameOfUser = new JTextField(20);
	private JTextField addr = new JTextField(20);
	private JTextField bank = new JTextField(20);
	private JTextField card_no = new JTextField(20);
	private JTextField expr_date = new JTextField(20);
	private JTextField cvv = new JTextField(20);
	private JTextField email = new JTextField(20);
	private JButton register = new JButton("Register");
	private JButton noAccount = new JButton("noAccount");
	private JButton renewFee = new JButton("Renew Fee");
	private JButton makePayment = new JButton("make Payment");
	private JButton cancelSeat = new JButton("cancel Seat");	
	private JTextField display = new JTextField(20);
	
	public UserInfoView(){
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridLayout(15, 1));
		contentPane.setSize(200, 200);
		
		JPanel Panel_0 = new JPanel();
		JLabel registeredPrompt = new JLabel("----------------------------------------------------------------------------------------------------------");
		Panel_0.add(registeredPrompt);
		contentPane.add(Panel_0);
		
		JPanel Panel_1 = new JPanel();
		JLabel userNamePrompt = new JLabel("UserName:");
		Panel_1.add(userNamePrompt);
		Panel_1.add(userName);	
		JLabel spacePrompt_1 = new JLabel("                         ");
		Panel_1.add(spacePrompt_1);
		JLabel feeStatusPrompt = new JLabel("Fee Renewal Status:");
		Panel_1.add(feeStatusPrompt);
		Panel_1.add(feeRenewalStatus);		
		contentPane.add(Panel_1);
		
		JPanel Panel_2 = new JPanel();
		JLabel passwordPrompt = new JLabel("Password:");
		Panel_2.add(passwordPrompt);
		Panel_2.add(password);
		Panel_2.add(login);	
		JLabel spacePrompt_2 = new JLabel("                                     ");
		Panel_2.add(spacePrompt_2);	
		Panel_2.add(renewFee);	
		contentPane.add(Panel_2);	
		
		JPanel Panel_3 = new JPanel();
		JLabel informationPrompt = new JLabel("------------------------------------");
		Panel_3.add(informationPrompt);
		Panel_3.add(userType);
		JLabel informationPrompt_1 = new JLabel("------------------------------------");
		Panel_3.add(informationPrompt_1);
		contentPane.add(Panel_3);
		
		JPanel Panel_4 = new JPanel();
		JLabel namePrompt = new JLabel("     Name:");
		Panel_4.add(namePrompt);
		Panel_4.add(nameOfUser);
		contentPane.add(Panel_4);	
		
		JPanel Panel_5 = new JPanel();
		JLabel addressPrompt = new JLabel("  Address:");
		Panel_5.add(addressPrompt);
		Panel_5.add(addr);
		contentPane.add(Panel_5);
		
		JPanel Panel_6 = new JPanel();
		JLabel bankPrompt = new JLabel("      Bank:");
		Panel_6.add(bankPrompt);
		Panel_6.add(bank);
		contentPane.add(Panel_6);	
		
		JPanel Panel_7 = new JPanel();
		JLabel card_noPrompt = new JLabel("  Card_no:");
		Panel_7.add(card_noPrompt);
		Panel_7.add(card_no);
		contentPane.add(Panel_7);	
		
		JPanel Panel_8 = new JPanel();
		JLabel expr_datePrompt = new JLabel("Expr_date:");
		Panel_8.add(expr_datePrompt);
		Panel_8.add(expr_date);
		contentPane.add(Panel_8);		
		
		JPanel Panel_9 = new JPanel();
		JLabel cvvPrompt = new JLabel("        Cvv:");
		Panel_9.add(cvvPrompt);
		Panel_9.add(cvv);
		contentPane.add(Panel_9);
		
		JPanel Panel_10 = new JPanel();
		JLabel emailPrompt = new JLabel("      Email:");
		Panel_10.add(emailPrompt);
		Panel_10.add(email);
		contentPane.add(Panel_10);		

		/*
		JPanel Panel_12 = new JPanel();
		JLabel actionPrompt = new JLabel("Action:");
		Panel_12.add(actionPrompt);
		contentPane.add(Panel_12);
		*/
		
		JPanel Panel_13 = new JPanel();
		Panel_13.add(register);
		JLabel spacePrompt_3 = new JLabel("                  ");
		Panel_13.add(spacePrompt_3);
		Panel_13.add(noAccount);
		contentPane.add(Panel_13);	
		
		JPanel Panel_14 = new JPanel();
		Panel_14.add(makePayment);
		JLabel spacePrompt_4 = new JLabel("        ");
		Panel_14.add(spacePrompt_4);
		Panel_14.add(cancelSeat);
		contentPane.add(Panel_14);	
		
		JPanel Panel_15=new JPanel();
		JLabel messagePrompt = new JLabel("Message:");
		Panel_15.add(messagePrompt);
		ScrollPane messageSp=new ScrollPane();
		Panel_15.add(messageSp);
		messageSp.add(display);
		messageSp.setSize(320,40);			
		contentPane.add(Panel_15);			
		
		setTitle("UserInfoView");
		setSize(800, 800);   
		setLocation(725, 0);
		pack();
		setVisible(true);
	}

	public JTextField getUserName() {
		return userName;
	}

	public JTextField getPassword() {
		return password;
	}
	
	public JTextField getFeeRenewalStatus() {
		return feeRenewalStatus;
	}
	
	public JLabel getUserType() {
		return userType;
	}

	public JTextField getNameOfUser() {
		return nameOfUser;
	}

	public JTextField getAddr() {
		return addr;
	}

	public JTextField getBank() {
		return bank;
	}

	public JTextField getCard_no() {
		return card_no;
	}

	public JTextField getExpr_date() {
		return expr_date;
	}

	public JTextField getCvv() {
		return cvv;
	}

	public JTextField getEmail() {
		return email;
	}

	public JButton getLogin() {
		return login;
	}

	public JButton getNoAccount() {
		return noAccount;
	}

	public JButton getRegister() {
		return register;
	}

	public JTextField getDisplay() {
		return display;
	}

	public JButton getMakePayment() {
		return makePayment;
	}
	
	public JButton getCancelSeat() {
		return cancelSeat;
	}

	public void setDisplay(String text) {
		display.setText(text);
	}

	public void setStatus(String text) {
		feeRenewalStatus.setText(text);
	}	
	
	public JButton getRenewFee() {
		return renewFee;
	}
	
	public void clearDisplay() {
		display.setText("");
	}
}
