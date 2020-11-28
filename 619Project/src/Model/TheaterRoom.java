// package Model;
/*
 *  TheaterRoom.java
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

public class TheaterRoom {
    private int number;
    private SeatMap seat_arrangement;

    /**
     * Initialize a theater room object
     * @param number
     * @param seat_arrangement
     */
    public TheaterRoom(int number, SeatMap seat_arrangement) {
        this.number = number;
        this.seat_arrangement = seat_arrangement;
    }

    /**
     * return the theater number
     * @return number
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * return then seatmap of the theater
     * @return seatmap
     */
    public SeatMap getSeatArrangement() {
        return this.seat_arrangement;
    }
}
