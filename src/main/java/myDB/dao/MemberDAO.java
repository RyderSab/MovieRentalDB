package myDB.dao;

import myDB.model.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    private Connection connection;

    public MemberDAO(Connection connection) {
        this.connection = connection;
    }

    // Create
    public boolean addMember(Member member) throws SQLException {
        String sql = "INSERT INTO Members (FirstName, LastName, Email, Phone, MembershipDate) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, member.getFirstName());
            statement.setString(2, member.getLastName());
            statement.setString(3, member.getEmail());
            statement.setString(4, member.getPhone());
            statement.setDate(5, new Date(member.getMembershipDate().getTime()));

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    member.setMemberID(generatedKeys.getInt(1));
                }
            }
            return true;
        }
    }

    // Read
    public Member getMemberById(int memberId) throws SQLException {
        String sql = "SELECT * FROM Members WHERE MemberID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, memberId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractMemberFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    public List<Member> getAllMembers() throws SQLException {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM Members";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                members.add(extractMemberFromResultSet(resultSet));
            }
        }
        return members;
    }

    // Update
    public boolean updateMember(Member member) throws SQLException {
        String sql = "UPDATE Members SET FirstName = ?, LastName = ?, Email = ?, Phone = ?, MembershipDate = ? WHERE MemberID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, member.getFirstName());
            statement.setString(2, member.getLastName());
            statement.setString(3, member.getEmail());
            statement.setString(4, member.getPhone());
            statement.setDate(5, new Date(member.getMembershipDate().getTime()));
            statement.setInt(6, member.getMemberID());

            return statement.executeUpdate() > 0;
        }
    }

    // Delete
    public boolean deleteMember(int memberId) throws SQLException {
        String sql = "DELETE FROM Members WHERE MemberID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, memberId);
            return statement.executeUpdate() > 0;
        }
    }

    // Helper method
    private Member extractMemberFromResultSet(ResultSet resultSet) throws SQLException {
        Member member = new Member();
        member.setMemberID(resultSet.getInt("MemberID"));
        member.setFirstName(resultSet.getString("FirstName"));
        member.setLastName(resultSet.getString("LastName"));
        member.setEmail(resultSet.getString("Email"));
        member.setPhone(resultSet.getString("Phone"));
        member.setMembershipDate(resultSet.getDate("MembershipDate"));
        return member;
    }
}