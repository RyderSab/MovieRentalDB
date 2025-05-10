package myDB.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

import myDB.dao.StaffDAO;
import myDB.model.Staff;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private StaffDAO staffDAO;

    @Override
    public void init() throws ServletException {
        try {
            this.staffDAO = new StaffDAO();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String staffId = request.getParameter("staffId");

        try {
            Staff staff = staffDAO.authenticate(email, staffId);

            if (staff != null) {
                HttpSession session = request.getSession();
                session.setAttribute("staff", staff);
                session.setAttribute("role", "staff");
                session.setMaxInactiveInterval(30 * 60); // 30 minutes

                response.sendRedirect("rentals");
            } else {
                request.setAttribute("error", "Invalid email or staff ID");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Login error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}