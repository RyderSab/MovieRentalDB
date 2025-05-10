package myDB.servlets;

import myDB.dao.MemberDAO;
import myDB.model.*;
import myDB.dao.RentalDAO;
import myDB.dao.MovieDAO;
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
import java.util.Date;
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
            }
                listRentals(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                rentMovie(request);
            } else if ("return".equals(action)) {
                returnMovie(request);
            } else if ("delete".equals(action)) {
                handleDeleteRental(request);
            }
            response.sendRedirect("rentals");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listRentals(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        List<Rental> rentals = rentalDAO.getAllRentals();
        List<Movie> movies = movieDAO.getAvailableMovies();
        List<Member> members = memberDAO.getAllMembers();

        request.setAttribute("rentals", rentals);
        request.setAttribute("movies", movies);
        request.setAttribute("members", members);
        request.getRequestDispatcher("/WEB-INF/views/rentals.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        request.setAttribute("movies", movieDAO.getAvailableMovies());
        request.setAttribute("members", memberDAO.getAllMembers());
        request.getRequestDispatcher("/WEB-INF/views/newRental.jsp").forward(request, response);
    }

    private void rentMovie(HttpServletRequest request)
            throws SQLException, ParseException {
        Rental rental = new Rental();
        rental.setMovieID(Integer.parseInt(request.getParameter("movieId")));
        rental.setMemberID(Integer.parseInt(request.getParameter("memberId")));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date rentalDate = df.parse(request.getParameter("rentalDate"));
        rental.setRentalDate(rentalDate);
        rental.setStatus(RentalStatus.valueOf("Rented"));

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

        request.setAttribute("rental", rental);
        request.setAttribute("movie", movie);
        request.setAttribute("member", member);
        request.getRequestDispatcher("/WEB-INF/views/returnRental.jsp").forward(request, response);
    }

    private void handleDeleteRental(HttpServletRequest request)
            throws SQLException, NumberFormatException {
        int rentalID = Integer.parseInt(request.getParameter("rentalId"));
        rentalDAO.deleteRental(rentalID);
    }
}
