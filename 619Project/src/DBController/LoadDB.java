package DBController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.sql.Date;

import FinancialInstitute.*;
import CustomerModel.*;
import Model.*;

public class LoadDB {
	
	private static UserBankDBController userDB = new UserBankDBController();
	private static MovieDBController movieDB = new MovieDBController();
	
	public static Map<String, Bank> LoadBanks() {
		ResultSet rs = userDB.getBanks();
		ArrayList<String> bankNames = new ArrayList<>();
		Map<String, Bank> banks = new HashMap<>();
		ArrayList<CreditCard> cards;
		
		try {
			do {
				bankNames.add(rs.getString("Name"));
			} while (rs.next());
			
			for (int i = 0; i < bankNames.size(); i++) {
				
				String bankName = bankNames.get(i);
				rs = userDB.getCreditCards(bankName);
				
				cards  = new ArrayList<>();
				do {
					long card_no = Long.parseLong(rs.getString("Card_no"));
					int card_cvv = rs.getInt("Card_cvv");
					int card_exp = rs.getInt("Card_exp");
					cards.add(new CreditCard(card_no, card_exp, card_cvv));
				} while (rs.next());
				
				banks.put(bankName, new Bank(bankName, cards));
				
			}
			
			return banks;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	} 
	
	
	public static Map<Integer, Voucher> loadVouchers() {
		ResultSet rs = userDB.getVouchers();
		Map<Integer, Voucher> vouchers = new HashMap<>();
		
		try {
			do {
				int voucherID = rs.getInt("VoucherID");
				int credit = rs.getInt("Credit");
				Date expr_date = rs.getDate("Expr_Date");
				vouchers.put(voucherID, new Voucher(voucherID, credit, expr_date));
			} while (rs.next());
			
			return vouchers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static Map<String, RegisteredUser> loadRegUsers() {
		ResultSet rs = userDB.getRegUsers();
		Map<String, RegisteredUser> regUsers = new HashMap<>();
		
		try {
			do {
				String username = rs.getString("Username");
				String password = rs.getString("Password");
				Date renewDate = rs.getDate("Fee_renew_date");
				String name = rs.getString("Name");
				String addr = rs.getString("Address");
				String bank = rs.getString("Bank");
				String email = rs.getString("Email");
				long card_no = Long.parseLong(rs.getString("Card_no"));
				int card_cvv = rs.getInt("Card_cvv");
				int card_exp = rs.getInt("Card_exp");
				regUsers.put(username, new RegisteredUser(name, addr, bank, card_no, card_exp, card_cvv, email, username, password, renewDate));

			} while (rs.next());
			
			return regUsers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


    /**
     * uses the database to populate the moviecatalogue
     * @param movies
     */
    private static void populateMovieCatalogue(MovieCatalogue movies) {
        // populate movie catalogue with movies from db
        System.out.println("\nPopulateMovieCatalogue");
        MovieDBController db = new MovieDBController();
        ArrayList<Movies> movieList = db.selectMovies();

        for (int i = 0; i< movieList.size(); i++) {
            movies.addMovie(movieList.get(i));
        }
    }

    /**
     * uses the database to populate the theatercatalogue
     * @param theaters
     * @param movies
     */
    private static void populateTheaterCatalogue(TheaterCatalogue theaters, MovieCatalogue movies) {
        // populate theater catalogue with theaters from db
        // add showtimes to each theater
        System.out.println("\nPopulateTheaterCatalogue");
        MovieDBController db = new MovieDBController();

        // populate TheaterCatalogue with theaters
        ArrayList<Theater> theaterList = db.selectTheaters();
        for (int i = 0; i < theaterList.size(); i++) {
            theaters.addTheater(theaterList.get(i));
        }

        // populate theaters in TheaterCatalogue with default theaterRooms
        for (int i = 0; i < theaters.viewTheaters().size(); i++) {
            addDefaultTheaterRooms(theaters.viewTheaters().get(i));
        }

        // populate theatures in TheaterCatalogue with showtimes
        db.selectAndAddShowTimes(theaters, movies);       
    }
    /**
     * Add 5 default theaterrooms to a theater
     * @return
     */
    private static void addDefaultTheaterRooms(Theater theater) {
        theater.addTheaterRoom(new TheaterRoom(1, new SeatMap())); // generic 10 x 8 seatmap
        theater.addTheaterRoom(new TheaterRoom(2, new SeatMap())); // generic 10 x 8 seatmap
        theater.addTheaterRoom(new TheaterRoom(3, new SeatMap())); // generic 10 x 8 seatmap
        theater.addTheaterRoom(new TheaterRoom(4, new SeatMap())); // generic 10 x 8 seatmap
        theater.addTheaterRoom(new TheaterRoom(5, new SeatMap())); // generic 10 x 8 seatmap
    }

    /**
     * populate seatmap of showtimes with entries from the database
     * @param bookings
     * @param theaters
     */
    private static void populateAndRegisterBookings(HashMap<Integer, MovieTicket> bookings, TheaterCatalogue theaters) {
        // register bookings into the seatmap, then add into the hashtable with bookings from db
        System.out.println("\nPopulateAndRegisterBookings");
        MovieDBController db = new MovieDBController();
        db.retrieveBookings(bookings, theaters);
    }

    public static BookingManager loadBookingManager() {
    	MovieCatalogue movies = new MovieCatalogue();
        TheaterCatalogue theaters = new TheaterCatalogue();
        HashMap<Integer, MovieTicket> bookings = new HashMap<>();

        populateMovieCatalogue(movies);                     
        populateTheaterCatalogue(theaters, movies);         
        populateAndRegisterBookings(bookings, theaters);   


        BookingManager manager = new BookingManager(movies, theaters, bookings);
        return manager;
    }
	
	public static void main (String[] args) {
		BankManager bm = new BankManager(LoadDB.LoadBanks());
		
		Map<Integer, Voucher> vouchers = LoadDB.loadVouchers();
		Map<String, RegisteredUser> regUsers = LoadDB.loadRegUsers();
		CustomerManager cm = new CustomerManager(regUsers, vouchers);
		
        MovieCatalogue movies = new MovieCatalogue();
        TheaterCatalogue theaters = new TheaterCatalogue();
        HashMap<Integer, MovieTicket> bookings = new HashMap<>();

        populateMovieCatalogue(movies);                     
        populateTheaterCatalogue(theaters, movies);         
        populateAndRegisterBookings(bookings, theaters);   


        BookingManager manager = new BookingManager(movies, theaters, bookings);

        // test some functions of the booking manager here:

        System.out.println("\nTesting BookingManager functions");
        
        System.out.println("BookingManager.viewMovies()");
        ArrayList<Movies> movieList = manager.viewMovies();
        for (int i = 0; i < movieList.size(); i++) {
            System.out.println(movieList.get(i).getName());
        }

        System.out.println("BookingManager.viewTheaters()"); 
        ArrayList<Theater> theaterList = manager.viewTheaters();
        for (int i = 0; i < theaterList.size(); i++) {
            System.out.println(theaterList.get(i).getName());
        }

        System.out.println("BookingManager.displaySeat(ShowTime showtime)");
        MovieTicket ticket = manager.validateBooking(1018);
        Seat[][] seats = manager.displaySeat(ticket.getShowTime());
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j].checkAvailability()) {
                    System.out.print("X");
                }
                else {
                    System.out.print("O");
                }
                
            }
            System.out.println();
        }
	}
	
	
}