package myDB.servlets;

import myDB.dao.MemberDAO;
import myDB.model.Member;
import myDB.dao.RentalDAO;
import myDB.model.Rental;
import myDB.dao.MovieDAO;
import myDB.model.Movie;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.util.List;
import java.sql.Date;
import java.text.*;
import java.time.temporal.ChronoUnit;
import java.time.ZoneId;
import java.time.Instant;

@WebServlet("/rentals")
public class RentalServlet extends HttpServlet {
    private RentalDAO rentalDAO;
    private MovieDAO movieDAO;
    private MemberDAO memberDAO;

    @Override
    public void init() throws ServletException {
        try {
            this.rentalDAO = new RentalDAO();
            this.movieDAO = new MovieDAO();
            this.memberDAO = new MemberDAO();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("new".equals(action)) {
                showNewForm(request, response);
            } else if ("return".equals(action)) {
                showReturnForm(request, response);
            } else {
                listRentals(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("rent".equals(action)) {
                rentMovie(request);
            } else if ("return".equals(action)) {
                returnMovie(request);
            }
            response.sendRedirect("rentals");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listRentals(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Rental> rentals = rentalDAO.getAllRentals();
        request.setAttribute("rentals", rentals);
        request.getRequestDispatcher("/WEB-INF/views/rentals.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        request.setAttribute("movies", movieDAO.getAvailableMovies());
        request.setAttribute("members", memberDAO.getAllMembers());
        request.getRequestDispatcher("/WEB-INF/views/newRental.jsp").forward(request, response);
    }

    private void rentMovie(HttpServletRequest request)
            throws SQLException {
        Rental rental = new Rental();
        rental.setMovieID(Integer.parseInt(request.getParameter("movieId")));
        rental.setMemberID(Integer.parseInt(request.getParameter("memberId")));
        LocalDate localDate = LocalDate.now();
        Date rentalDate = (Date) Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        rental.setRentalDate(rentalDate);

        rentalDAO.addRental(rental);
        movieDAO.updateAvailability(rental.getMovieID(), false);
    }

    private void returnMovie(HttpServletRequest request)
            throws SQLException {
        int rentalId = Integer.parseInt(request.getParameter("rentalId"));
        Rental rental = rentalDAO.getRentalById(rentalId);

        rentalDAO.updateRentalStatus(rentalId, "Returned");
        movieDAO.updateAvailability(rental.getMovieID(), true);
    }

    private void showReturnForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int rentalId = Integer.parseInt(request.getParameter("id"));

        Rental rental = rentalDAO.getRentalById(rentalId);
        Movie movie = movieDAO.getMovieById(rental.getMovieID());
        Member member = memberDAO.getMemberById(rental.getMemberID());

        // Calculate potential late fee (example: $1 per day late)
        long daysLate = ChronoUnit.DAYS.between(
                rental.getRentalDate().toLocalDate(),
                LocalDate.now()
        ) - 7; // 7-day rental period

        double lateFee = daysLate > 0 ? daysLate * 1.0 : 0;

        request.setAttribute("rental", rental);
        request.setAttribute("movie", movie);
        request.setAttribute("member", member);
        request.setAttribute("lateFee", lateFee);
        request.getRequestDispatcher("/WEB-INF/views/returnRental.jsp").forward(request, response);
    }
}
