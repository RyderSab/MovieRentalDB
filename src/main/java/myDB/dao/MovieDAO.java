package myDB.dao;

import myDB.model.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
    private Connection connection;

    public MovieDAO(Connection connection) {
        this.connection = connection;
    }

    // Create
    public boolean addMovie(Movie movie) throws SQLException {
        String sql = "INSERT INTO Movies (Title, Genre, ReleaseDate, Rating, AvailabilityStatus) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getGenre());
            statement.setDate(3, new Date(movie.getReleaseDate().getTime()));
            statement.setFloat(4, movie.getRating());
            statement.setBoolean(5, movie.isAvailabilityStatus());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    movie.setMovieID(generatedKeys.getInt(1));
                }
            }
            return true;
        }
    }

    // Read
    public Movie getMovieById(int movieId) throws SQLException {
        String sql = "SELECT * FROM Movies WHERE MovieID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, movieId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractMovieFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    public List<Movie> getAllMovies() throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM Movies";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                movies.add(extractMovieFromResultSet(resultSet));
            }
        }
        return movies;
    }

    // Update
    public boolean updateMovie(Movie movie) throws SQLException {
        String sql = "UPDATE Movies SET Title = ?, Genre = ?, ReleaseDate = ?, Rating = ?, AvailabilityStatus = ? WHERE MovieID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getGenre());
            statement.setDate(3, new Date(movie.getReleaseDate().getTime()));
            statement.setFloat(4, movie.getRating());
            statement.setBoolean(5, movie.isAvailabilityStatus());
            statement.setInt(6, movie.getMovieID());

            return statement.executeUpdate() > 0;
        }
    }

    // Delete
    public boolean deleteMovie(int movieId) throws SQLException {
        String sql = "DELETE FROM Movies WHERE MovieID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, movieId);
            return statement.executeUpdate() > 0;
        }
    }

    // Helper method
    private Movie extractMovieFromResultSet(ResultSet resultSet) throws SQLException {
        Movie movie = new Movie();
        movie.setMovieID(resultSet.getInt("MovieID"));
        movie.setTitle(resultSet.getString("Title"));
        movie.setGenre(resultSet.getString("Genre"));
        movie.setReleaseDate(resultSet.getDate("ReleaseDate"));
        movie.setRating(resultSet.getFloat("Rating"));
        movie.setAvailabilityStatus(resultSet.getBoolean("AvailabilityStatus"));
        return movie;
    }
}