package myDB.model;

import java.sql.Date;

public class Rental {
    private int rentalID;
    private int memberID;
    private int movieID;
    private Date rentalDate;
    private Date returnDate;
    private RentalStatus status;

    // Constructors
    public Rental() {
    }

    public Rental(int memberID, int movieID, Date rentalDate, Date returnDate, RentalStatus status) {
        this.memberID = memberID;
        this.movieID = movieID;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    // Getters and Setters
    public int getRentalID() {
        return rentalID;
    }

    public void setRentalID(int rentalID) {
        this.rentalID = rentalID;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }
}