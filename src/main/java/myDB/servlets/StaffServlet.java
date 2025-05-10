package myDB.servlets;

import myDB.dao.StaffDAO;
import myDB.model.Staff;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import myDB.model.StaffRole;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/staff")
public class StaffServlet extends HttpServlet {
    private StaffDAO staffDAO;

    @Override
    public void init() throws ServletException {
        try {
            this.staffDAO = new StaffDAO();
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
                // Handle edit form display
                int staffId = Integer.parseInt(request.getParameter("id"));
                Staff staff = staffDAO.getStaffById(staffId);
                request.setAttribute("staff", staff);
                request.getRequestDispatcher("/WEB-INF/views/editStaff.jsp")
                        .forward(request, response);
                return;
            }

            // Default action: list all staff
            List<Staff> staffList = staffDAO.getAllStaff();
            request.setAttribute("staffList", staffList);
            request.getRequestDispatcher("/WEB-INF/views/staff.jsp").forward(request, response);

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
                handleAddStaff(request);
            } else if ("delete".equals(action)) {
                handleDeleteStaff(request);
            } else if ("update".equals(action)) {
                handleUpdateStaff(request);
            }

            response.sendRedirect("staff");
        } catch (Exception e) {
            throw new ServletException("Staff operation failed: " + e.getMessage(), e);
        }
    }

    private void handleAddStaff(HttpServletRequest request)
            throws SQLException {
        Staff staff = new Staff();
        staff.setFirstName(request.getParameter("firstName"));
        staff.setLastName(request.getParameter("lastName"));
        staff.setEmail(request.getParameter("email"));
        staff.setRole(StaffRole.valueOf(request.getParameter("role")));

        staffDAO.addStaff(staff);
    }

    private void handleDeleteStaff(HttpServletRequest request)
            throws SQLException, NumberFormatException {
        int staffId = Integer.parseInt(request.getParameter("staffId"));
        staffDAO.deleteStaff(staffId);
    }

    private void handleUpdateStaff(HttpServletRequest request)
            throws SQLException, NumberFormatException {
        Staff staff = new Staff();
        staff.setStaffID(Integer.parseInt(request.getParameter("staffId")));
        staff.setFirstName(request.getParameter("firstName"));
        staff.setLastName(request.getParameter("lastName"));
        staff.setEmail(request.getParameter("email"));
        staff.setRole(StaffRole.valueOf(request.getParameter("role")));

        staffDAO.updateStaff(staff);
    }
}
