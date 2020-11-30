package App;

import java.io.IOException;
import java.util.Map;

import Controller.ModelController;
import CustomerModel.CustomerManager;
import CustomerModel.RegisteredUser;
import CustomerModel.Voucher;
import DBController.LoadDB;
import FinancialInstitute.BankManager;
import Model.BookingManager;
import View.GUI;

public class App {
	/**
	 * main function
	 * @param args
	 * @throws IOException
	 */
	public static void main (String [] args) throws IOException {
		
		//build BookingManager object
		BookingManager bookingManager = LoadDB.loadBookingManager();

		//build CustomerManager object
		Map<Integer, Voucher> voucher = LoadDB.loadVouchers();
		Map<String, RegisteredUser> registeredUser = LoadDB.loadRegUsers();
		CustomerManager customerManager = new CustomerManager(registeredUser, voucher);

		//build BankManager object
		BankManager bankManager = new BankManager(LoadDB.LoadBanks());

		//build modelController object
		ModelController modelController = new ModelController(new GUI(), bookingManager, customerManager, bankManager);
	}	
	
}
