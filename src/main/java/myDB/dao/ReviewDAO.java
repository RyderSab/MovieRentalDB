package myDB.dao;

import myDB.model.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {
    private Connection connection;

    public ReviewDAO(Connection connection) {
        this.connection = connection;
    }

    public ReviewDAO() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }

        this.connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/movierentaldb",
                "root",
                "Ryder$abale148"
        );
    }

    // Create
    public boolean addReview(Review review) throws SQLException {
        String sql = "INSERT INTO Reviews (MemberID, MovieID, ReviewDate, Rating, ReviewText) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, review.getMemberID());
            statement.setInt(2, review.getMovieID());
            statement.setDate(3, new Date(review.getReviewDate().getTime()));
            statement.setFloat(4, review.getRating());
            statement.setString(5, review.getReviewText());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    review.setReviewID(generatedKeys.getInt(1));
                }
            }
            return true;
        }
    }

    // Read
    public Review getReviewById(int reviewId) throws SQLException {
        String sql = "SELECT * FROM Reviews WHERE ReviewID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, reviewId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractReviewFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    public List<Review> getReviewsByMovie(int movieId) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM Reviews WHERE MovieID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, movieId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    reviews.add(extractReviewFromResultSet(resultSet));
                }
            }
        }
        return reviews;
    }

    public List<Review> getReviewsByMember(int memberId) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM Reviews WHERE MemberID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, memberId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    reviews.add(extractReviewFromResultSet(resultSet));
                }
            }
        }
        return reviews;
    }

    // Update
    public boolean updateReview(Review review) throws SQLException {
        String sql = "UPDATE Reviews SET MemberID = ?, MovieID = ?, ReviewDate = ?, Rating = ?, ReviewText = ? WHERE ReviewID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, review.getMemberID());
            statement.setInt(2, review.getMovieID());
            statement.setDate(3, new Date(review.getReviewDate().getTime()));
            statement.setFloat(4, review.getRating());
            statement.setString(5, review.getReviewText());
            statement.setInt(6, review.getReviewID());

            return statement.executeUpdate() > 0;
        }
    }

    // Delete
    public boolean deleteReview(int reviewId) throws SQLException {
        String sql = "DELETE FROM Reviews WHERE ReviewID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, reviewId);
            return statement.executeUpdate() > 0;
        }
    }

    // Helper method
    private Review extractReviewFromResultSet(ResultSet resultSet) throws SQLException {
        Review review = new Review();
        review.setReviewID(resultSet.getInt("ReviewID"));
        review.setMemberID(resultSet.getInt("MemberID"));
        review.setMovieID(resultSet.getInt("MovieID"));
        review.setReviewDate(resultSet.getDate("ReviewDate"));
        review.setRating(resultSet.getFloat("Rating"));
        review.setReviewText(resultSet.getString("ReviewText"));
        return review;
    }

    public List<Review> getAllReviews() throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT r.*, m.Title, mem.FirstName, mem.LastName " +
                "FROM Reviews r " +
                "JOIN Movies m ON r.MovieID = m.MovieID " +
                "JOIN Members mem ON r.MemberID = mem.MemberID";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Review review = new Review();
                review.setReviewID(rs.getInt("ReviewID"));
                review.setMovieID(rs.getInt("MovieID"));
                review.setMemberID(rs.getInt("MemberID"));
                review.setReviewDate(rs.getDate("ReviewDate"));
                review.setRating(rs.getFloat("Rating"));
                review.setReviewText(rs.getString("ReviewText"));

                reviews.add(review);
            }
        }
        return reviews;
    }
}