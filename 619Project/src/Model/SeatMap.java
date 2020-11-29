package Model;

/*
 *  SeatMap.java
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

public class SeatMap {
    private Seat[][] seats;

    /**
     * Initialize a default seatmap
     */
    public SeatMap() {
        this.seats = new Seat[8][10]; // default size
        
        for (int i = 0; i < this.seats.length; i++) {
            for (int j = 0; j < this.seats[i].length; j++) {
                this.seats[i][j] = new Seat();
            }
        }
    }
    /**
     * Initalize a seatmap given another seatmap
     */
    public SeatMap(SeatMap toCopy) {
        int rows = toCopy.displaySeats().length;
        int cols = toCopy.displaySeats()[0].length;

        this.seats = new Seat[rows][cols];
        for (int i = 0; i < this.seats.length; i++) {
            for (int j = 0; j < this.seats[i].length; j++) {
                this.seats[i][j] = new Seat();
            }
        }
    }

    /**
     * Return the arraylist of seats for display by GUI
     */
    public Seat[][] displaySeats() {
        return seats;
    }

    /**
     * check the availability of a seat at a position
     * @return true if the seat is available
     */
    public boolean checkSeatAvailability(int row, int column) {
        return this.seats[row][column].checkAvailability();
    }

    /**
     * book the seat at a position with a booking id
     */
    public void bookSeat(int row, int column, int booking_id) {
        this.seats[row][column].bookSeat(booking_id);
    }

    /**
     * cancel the seat at a position
     */
    public void cancelSeat(int row, int column) {
        this.seats[row][column].cancelSeat();
    }

    // test seatmap functionality
    public static void main(String[] args) {
        SeatMap seats = new SeatMap();       

        System.out.println(seats.checkSeatAvailability(5,5));
        seats.bookSeat(5,5,1000);
        System.out.println(seats.checkSeatAvailability(5,5));
        seats.cancelSeat(5,5);
        System.out.println(seats.checkSeatAvailability(5,5));
    }
}
