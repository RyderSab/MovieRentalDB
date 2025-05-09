package myDB.servlets;

import myDB.dao.MemberDAO;
import myDB.dao.ReviewDAO;
import myDB.dao.MovieDAO;
import myDB.model.Review;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.List;
import java.util.Date;
import java.text.*;

@WebServlet("/reviews")
public class ReviewServlet extends HttpServlet {
    private ReviewDAO reviewDAO;
    private MovieDAO movieDAO;
    private MemberDAO memberDAO;

    @Override
    public void init() throws ServletException {
        try {
            this.reviewDAO = new ReviewDAO();
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
            } else {
                listReviews(request, response);
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
            if ("add".equals(action)) {
                addReview(request);
            } else if ("delete".equals(action)) {
                deleteReview(request);
            }
            response.sendRedirect("reviews");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listReviews(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Review> reviews = reviewDAO.getAllReviews();
        request.setAttribute("reviews", reviews);
        request.getRequestDispatcher("/WEB-INF/views/reviews.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        request.setAttribute("movies", movieDAO.getAllMovies());
        request.setAttribute("members", memberDAO.getAllMembers());
        request.getRequestDispatcher("/WEB-INF/views/newReview.jsp").forward(request, response);
    }

    private void addReview(HttpServletRequest request)
            throws SQLException {
        Review review = new Review();
        review.setMovieID(Integer.parseInt(request.getParameter("movieId")));
        review.setMemberID(Integer.parseInt(request.getParameter("memberId")));
        review.setRating(Float.parseFloat(request.getParameter("rating")));
        review.setReviewText(request.getParameter("reviewText"));

        reviewDAO.addReview(review);
    }

    private void deleteReview(HttpServletRequest request)
            throws SQLException {
        int reviewId = Integer.parseInt(request.getParameter("reviewId"));
        reviewDAO.deleteReview(reviewId);
    }
}
