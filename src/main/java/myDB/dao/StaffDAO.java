package myDB.dao;

import myDB.model.Staff;
import myDB.model.StaffRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {
    private Connection connection;

    public StaffDAO(Connection connection) {
        this.connection = connection;
    }

    public StaffDAO() throws SQLException {
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
    public boolean addStaff(Staff staff) throws SQLException {
        String sql = "INSERT INTO Staff (FirstName, LastName, Email, Role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, staff.getFirstName());
            statement.setString(2, staff.getLastName());
            statement.setString(3, staff.getEmail());
            statement.setString(4, staff.getRole().toString());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    staff.setStaffID(generatedKeys.getInt(1));
                }
            }
            return true;
        }
    }

    // Read
    public Staff getStaffById(int staffId) throws SQLException {
        String sql = "SELECT * FROM Staff WHERE StaffID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, staffId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractStaffFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    public List<Staff> getAllStaff() throws SQLException {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT * FROM Staff";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                staffList.add(extractStaffFromResultSet(resultSet));
            }
        }
        return staffList;
    }

    public Staff getStaffByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Staff WHERE Email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractStaffFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    // Update
    public boolean updateStaff(Staff staff) throws SQLException {
        String sql = "UPDATE Staff SET FirstName = ?, LastName = ?, Email = ?, Role = ? WHERE StaffID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, staff.getFirstName());
            statement.setString(2, staff.getLastName());
            statement.setString(3, staff.getEmail());
            statement.setString(4, staff.getRole().toString());
            statement.setInt(5, staff.getStaffID());

            return statement.executeUpdate() > 0;
        }
    }

    // Delete
    public boolean deleteStaff(int staffId) throws SQLException {
        String sql = "DELETE FROM Staff WHERE StaffID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, staffId);
            return statement.executeUpdate() > 0;
        }
    }

    // Helper method
    private Staff extractStaffFromResultSet(ResultSet resultSet) throws SQLException {
        Staff staff = new Staff();
        staff.setStaffID(resultSet.getInt("StaffID"));
        staff.setFirstName(resultSet.getString("FirstName"));
        staff.setLastName(resultSet.getString("LastName"));
        staff.setEmail(resultSet.getString("Email"));
        staff.setRole(StaffRole.valueOf(resultSet.getString("Role")));
        return staff;
    }

    public Staff authenticate(String email, String staffId) throws SQLException {
        String sql = "SELECT * FROM staff WHERE email = ? AND StaffID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setString(2, staffId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String role = rs.getString("role");
                    StaffRole staffRole = StaffRole.valueOf(role);
                    return new Staff(
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Email"),
                        staffRole
                    );
                }
            }
        }
        return null;
    }
}