package myDB.service;

import myDB.dao.MovieDAO;
import myDB.dao.ReviewDAO;
import myDB.model.Movie;
import myDB.model.Review;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerMovieService {
    private MovieDAO movieDAO;
    private ReviewDAO reviewDAO;

    public CustomerMovieService(MovieDAO movieDAO, ReviewDAO reviewDAO) {
        this.movieDAO = movieDAO;
        this.reviewDAO = reviewDAO;
    }

    // Browse available movies
    public List<Movie> getAvailableMovies() throws SQLException {
        return movieDAO.getAllMovies().stream()
                .filter(Movie::isAvailabilityStatus)
                .collect(Collectors.toList());
    }

    // Leave a review
    public void submitReview(int memberId, int movieId, float rating, String reviewText) throws SQLException {
        Review review = new Review();
        review.setMemberID(memberId);
        review.setMovieID(movieId);
        review.setReviewDate(new Date());
        review.setRating(rating);
        review.setReviewText(reviewText);

        reviewDAO.addReview(review);
    }
}
