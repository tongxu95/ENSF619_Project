package Model;

/*
 *  Seat.java
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

 public class Seat {
    private int booking_id;
    private int row;
    private int column;

    /**
     * Initialize a seat with a position and no booking id
     * @param position
     */
    public Seat(int row, int column) {
        this.booking_id = 0;
        this.row = row;
        this.column = column;
    }

    /**
     * @return true if the seat has no booking id
     */
    public boolean checkAvailability() {
        if (this.booking_id == 0)
            return true;

        return false;
    }

    /**
     * set the booking id
     * @param booking_id
     */
    public void bookSeat(int booking_id) {
        this.booking_id = booking_id;
    }

    /**
     * clear the booking_id
     */
    public void cancelSeat() {
        this.booking_id = 0;
    }  
    
    /*
        GET FUNCTIONS
    */
    public int getRow() {
        return this.row;
    }
    public int getColumn() {
        return this.column;
    }
    public int getBookingId() {
        return this.booking_id;
    }
}
