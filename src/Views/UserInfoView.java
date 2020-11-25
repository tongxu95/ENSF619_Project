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
	private JTextField userName = new JTextField(20);
	private JTextField password = new JTextField(20);
	private JTextField nameOfUser = new JTextField(20);
	private JTextField addr = new JTextField(20);
	private JTextField bank = new JTextField(20);
	private JTextField card_no = new JTextField(20);
	private JTextField expr_date = new JTextField(20);
	private JTextField cvv = new JTextField(20);
	private JTextField email = new JTextField(20);
	private JButton login = new JButton("");
	private JButton noAccount = new JButton("");
	private JButton register = new JButton("");
	private JTextField display = new JTextField(20);
	private JButton makePayment = new JButton("");
	
	public UserInfoView(){

		setTitle("UserInfoView");
		setSize(800, 800);   
		setLocation(730, 0);
		pack();
		setVisible(true);
	}

	public JTextField getUserName() {
		return userName;
	}

	public void setUserName(JTextField userName) {
		this.userName = userName;
	}

	public JTextField getPassword() {
		return password;
	}

	public void setPassword(JTextField password) {
		this.password = password;
	}

	public JTextField getNameOfUser() {
		return nameOfUser;
	}

	public void setNameOfUser(JTextField nameOfUser) {
		this.nameOfUser = nameOfUser;
	}

	public JTextField getAddr() {
		return addr;
	}

	public void setAddr(JTextField addr) {
		this.addr = addr;
	}

	public JTextField getBank() {
		return bank;
	}

	public void setBank(JTextField bank) {
		this.bank = bank;
	}

	public JTextField getCard_no() {
		return card_no;
	}

	public void setCard_no(JTextField card_no) {
		this.card_no = card_no;
	}

	public JTextField getExpr_date() {
		return expr_date;
	}

	public void setExpr_date(JTextField expr_date) {
		this.expr_date = expr_date;
	}

	public JTextField getCvv() {
		return cvv;
	}

	public void setCvv(JTextField cvv) {
		this.cvv = cvv;
	}

	public JTextField getEmail() {
		return email;
	}

	public void setEmail(JTextField email) {
		this.email = email;
	}

	public JButton getLogin() {
		return login;
	}

	public void setLogin(JButton login) {
		this.login = login;
	}

	public JButton getNoAccount() {
		return noAccount;
	}

	public void setNoAccount(JButton noAccount) {
		this.noAccount = noAccount;
	}

	public JButton getRegister() {
		return register;
	}

	public void setRegister(JButton register) {
		this.register = register;
	}

	public JTextField getDisplay() {
		return display;
	}

	public void setDisplay(JTextField display) {
		this.display = display;
	}

	public JButton getMakePayment() {
		return makePayment;
	}

	public void setMakePayment(JButton makePayment) {
		this.makePayment = makePayment;
	}
}
