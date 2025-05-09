package myDB.model;

import java.util.Date;

public class Review {
    private int reviewID;
    private int memberID;
    private int movieID;
    private Date reviewDate;
    private float rating;
    private String reviewText;

    // Constructors
    public Review() {
    }

    public Review(int memberID, int movieID, Date reviewDate, float rating, String reviewText) {
        this.memberID = memberID;
        this.movieID = movieID;
        this.reviewDate = reviewDate;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    // Getters and Setters
    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
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

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}