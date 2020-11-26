// package Model;

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

import java.util.ArrayList;

public class SeatMap {
    private ArrayList<Seat> seats;

    /**
     * Initialize a seatmap with an arraylist of seats
     */
    public SeatMap(ArrayList<Seat> seats) {
        this.seats = seats;
    }

    /**
     * Return the arraylist of seats for display by GUI
     */
    public ArrayList<Seat> displaySeats() {
        return seats;
    }

    /**
     * check the availability of a seat at a position
     * @return true if the seat is available
     */
    public boolean checkSeatAvailability(int row, int column) {
        return seats.get(searchForPosition(row, column)).checkAvailability();
    }

    /**
     * book the seat at a position with a booking id
     */
    public void bookSeat(int row, int column, int booking_id) {
        seats.get(searchForPosition(row, column)).bookSeat(booking_id);
    }

    /**
     * cancel the seat at a position
     */
    public void cancelSeat(int row, int column) {
        seats.get(searchForPosition(row, column)).cancelSeat();
    }

    /**
     * Return the index 
     */
    private int searchForPosition(int row, int column) {
        for (int i = 0; i < seats.size(); i++) {
            if (seats.get(i).getRow() == row && seats.get(i).getColumn() == column) {
                return i; 
            }
        }

        System.out.println("The seat at the given position was not found within the SeatMap");
        return -1;
    }

    // test seatmap functionality
    public static void main(String[] args) {
        ArrayList<Seat> seat_list = new ArrayList<>();
        seat_list.add(new Seat(1,1));
        seat_list.add(new Seat(1,2));
        seat_list.add(new Seat(1,3));

        SeatMap seats = new SeatMap(seat_list);

        System.out.println(seats.searchForPosition(1,1));
        System.out.println(seats.searchForPosition(1,2));
        System.out.println(seats.searchForPosition(1,3));
        System.out.println(seats.searchForPosition(1,4));         

        System.out.println(seats.checkSeatAvailability(1,1));
        seats.bookSeat(1,1, 3000);
        System.out.println(seats.checkSeatAvailability(1,1));
        seats.cancelSeat(1,1);
        System.out.println(seats.checkSeatAvailability(1,1));
    }
}
