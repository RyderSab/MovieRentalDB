package myDB.servlets;

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
import java.util.List;
import java.util.Date;
import java.text.*;

@WebServlet("/movies")
public class MovieServlet extends HttpServlet {
    private MovieDAO movieDAO;

    @Override
    public void init() throws ServletException {
        try {
            this.movieDAO = new MovieDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            if ("edit".equals(action)) {
                // 1. Get movie ID
                int movieId = Integer.parseInt(request.getParameter("id"));

                // 2. Fetch movie from DB
                Movie movie = movieDAO.getMovieById(movieId);

                // 3. Forward to edit form
                request.setAttribute("movie", movie);
                request.getRequestDispatcher("/WEB-INF/views/editMovie.jsp")
                        .forward(request, response);
                return;
            }

            List<Movie> movies = movieDAO.getAllMovies();
            request.setAttribute("movies", movies);
            request.getRequestDispatcher("/WEB-INF/views/movies.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                handleAddMovie(request);
            } else if ("delete".equals(action)) {
                handleDeleteMovie(request);
            } else if ("update".equals(action)) {
                handleUpdateMovie(request);
            }
            response.sendRedirect("movies");
        } catch (Exception e) {
            throw new ServletException("Operation failed: " + e.getMessage(), e);
        }
    }

    private void handleAddMovie(HttpServletRequest request)
            throws ParseException, SQLException {
        Movie movie = new Movie();
        movie.setTitle(request.getParameter("title"));
        movie.setGenre(request.getParameter("genre"));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate = df.parse(request.getParameter("releaseDate"));
        movie.setReleaseDate(releaseDate);
        movie.setRating(Float.parseFloat(request.getParameter("rating")));

        movieDAO.addMovie(movie);
    }

    private void handleDeleteMovie(HttpServletRequest request)
            throws SQLException, NumberFormatException {
        int movieId = Integer.parseInt(request.getParameter("movieId"));
        movieDAO.deleteMovie(movieId);
    }

    private void handleUpdateMovie(HttpServletRequest request)
            throws ParseException, SQLException, NumberFormatException {
        Movie movie = new Movie();
        movie.setMovieID(Integer.parseInt(request.getParameter("movieId")));
        movie.setTitle(request.getParameter("title"));
        movie.setGenre(request.getParameter("genre"));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate = df.parse(request.getParameter("releaseDate"));
        movie.setReleaseDate(releaseDate);
        movie.setRating(Float.parseFloat(request.getParameter("rating")));

        movieDAO.updateMovie(movie);
    }
}


