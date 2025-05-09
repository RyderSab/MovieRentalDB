package myDB.service;

import myDB.dao.MemberDAO;
import myDB.dao.MovieDAO;
import myDB.dao.RentalDAO;
import myDB.model.Member;
import myDB.model.Movie;
import myDB.model.Rental;
import myDB.model.RentalStatus;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class RentalService {
    private RentalDAO rentalDAO;
    private MovieDAO movieDAO;
    private MemberDAO memberDAO;

    public RentalService(RentalDAO rentalDAO, MovieDAO movieDAO, MemberDAO memberDAO) {
        this.rentalDAO = rentalDAO;
        this.movieDAO = movieDAO;
        this.memberDAO = memberDAO;
    }

    public void rentMovie(int memberId, int movieId) throws Exception {
        // Check member exists
        Member member = memberDAO.getMemberById(memberId);
        if (member == null) {
            throw new Exception("myDB.model.Member not found");
        }

        // Check movie exists and is available
        Movie movie = movieDAO.getMovieById(movieId);
        if (movie == null) {
            throw new Exception("myDB.model.Movie not found");
        }
        if (!movie.isAvailabilityStatus()) {
            throw new Exception("myDB.model.Movie not available");
        }

        // Create rental
        Rental rental = new Rental();
        rental.setMemberID(memberId);
        rental.setMovieID(movieId);
        LocalDate localDate = LocalDate.now();
        java.sql.Date rentalDate = (java.sql.Date) java.sql.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        rental.setRentalDate(rentalDate);
        rental.setStatus(RentalStatus.Rented);

        // Update movie availability
        movie.setAvailabilityStatus(false);
        movieDAO.updateMovie(movie);

        // Save rental
        rentalDAO.addRental(rental);
    }

    public void returnMovie(int rentalId) throws Exception {
        Rental rental = rentalDAO.getRentalById(rentalId);
        if (rental == null) {
            throw new Exception("myDB.model.Rental not found");
        }

        // Update rental
        LocalDate localDate = LocalDate.now();
        java.sql.Date returnDate = (java.sql.Date) Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        rental.setRentalDate(returnDate);

        rental.setStatus(RentalStatus.Returned);
        rentalDAO.updateRental(rental);

        // Update movie availability
        Movie movie = movieDAO.getMovieById(rental.getMovieID());
        movie.setAvailabilityStatus(true);
        movieDAO.updateMovie(movie);
    }
}
