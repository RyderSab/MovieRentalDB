package myDB.dao;

import myDB.model.Rental;
import myDB.model.RentalStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalDAO {
    private Connection connection;

    public RentalDAO(Connection connection) {
        this.connection = connection;
    }

    public RentalDAO() throws SQLException {
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
    public boolean addRental(Rental rental) throws SQLException {
        String sql = "INSERT INTO Rentals (MemberID, MovieID, RentalDate, ReturnDate, Status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, rental.getMemberID());
            statement.setInt(2, rental.getMovieID());
            statement.setDate(3, new Date(rental.getRentalDate().getTime()));
            if (rental.getReturnDate() != null) {
                statement.setDate(4, new Date(rental.getReturnDate().getTime()));
            } else {
                statement.setNull(4, Types.DATE);
            }
            statement.setString(5, rental.getStatus().toString());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    rental.setRentalID(generatedKeys.getInt(1));
                }
            }
            return true;
        }
    }

    // Read
    public Rental getRentalById(int rentalId) throws SQLException {
        String sql = "SELECT * FROM Rentals WHERE RentalID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, rentalId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractRentalFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    public List<Rental> getRentalsByMember(int memberId) throws SQLException {
        List<Rental> rentals = new ArrayList<>();
        String sql = "SELECT * FROM Rentals WHERE MemberID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, memberId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    rentals.add(extractRentalFromResultSet(resultSet));
                }
            }
        }
        return rentals;
    }

    public List<Rental> getRentalsByMovie(int movieId) throws SQLException {
        List<Rental> rentals = new ArrayList<>();
        String sql = "SELECT * FROM Rentals WHERE MovieID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, movieId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    rentals.add(extractRentalFromResultSet(resultSet));
                }
            }
        }
        return rentals;
    }

    public List<Rental> getActiveRentals() throws SQLException {
        List<Rental> rentals = new ArrayList<>();
        String sql = "SELECT * FROM Rentals WHERE Status = 'Rented'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                rentals.add(extractRentalFromResultSet(resultSet));
            }
        }
        return rentals;
    }

    // Update
    public boolean updateRental(Rental rental) throws SQLException {
        String sql = "UPDATE Rentals SET MemberID = ?, MovieID = ?, RentalDate = ?, ReturnDate = ?, Status = ? WHERE RentalID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, rental.getMemberID());
            statement.setInt(2, rental.getMovieID());
            statement.setDate(3, new Date(rental.getRentalDate().getTime()));
            if (rental.getReturnDate() != null) {
                statement.setDate(4, new Date(rental.getReturnDate().getTime()));
            } else {
                statement.setNull(4, Types.DATE);
            }
            statement.setString(5, rental.getStatus().toString());
            statement.setInt(6, rental.getRentalID());

            return statement.executeUpdate() > 0;
        }
    }

    // Delete
    public boolean deleteRental(int rentalId) throws SQLException {
        String sql = "DELETE FROM Rentals WHERE RentalID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, rentalId);
            return statement.executeUpdate() > 0;
        }
    }

    // Helper method
    private Rental extractRentalFromResultSet(ResultSet resultSet) throws SQLException {
        Rental rental = new Rental();
        rental.setRentalID(resultSet.getInt("RentalID"));
        rental.setMemberID(resultSet.getInt("MemberID"));
        rental.setMovieID(resultSet.getInt("MovieID"));
        rental.setRentalDate(resultSet.getDate("RentalDate"));
        rental.setReturnDate(resultSet.getDate("ReturnDate"));
        rental.setStatus(RentalStatus.valueOf(resultSet.getString("Status")));
        return rental;
    }

    public boolean updateRentalStatus(int rentalId, String status) throws SQLException {
        String sql = "UPDATE Rentals SET Status = ?, ReturnDate = ? WHERE RentalID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setDate(2, "Returned".equals(status) ? new java.sql.Date(System.currentTimeMillis()) : null);
            stmt.setInt(3, rentalId);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Rental> getAllRentals() {
        return null;
    }
}