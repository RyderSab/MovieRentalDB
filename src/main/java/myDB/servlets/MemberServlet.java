package myDB.servlets;

import myDB.dao.MemberDAO;
import myDB.model.Member;
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

@WebServlet("/members")
public class MemberServlet extends HttpServlet {
    private MemberDAO memberDAO;

    @Override
    public void init() throws ServletException {
        try {
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
            if ("edit".equals(action)) {
                showEditForm(request, response);
            } else {
                listMembers(request, response);
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
                addMember(request);
            } else if ("update".equals(action)) {
                updateMember(request);
            } else if ("delete".equals(action)) {
                deleteMember(request);
            }
            response.sendRedirect("members");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listMembers(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Member> members = memberDAO.getAllMembers();
        request.setAttribute("members", members);
        request.getRequestDispatcher("/WEB-INF/views/members.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int memberId = Integer.parseInt(request.getParameter("id"));
        Member member = memberDAO.getMemberById(memberId);
        request.setAttribute("member", member);
        request.getRequestDispatcher("/WEB-INF/views/editMember.jsp").forward(request, response);
    }

    private void addMember(HttpServletRequest request)
            throws SQLException, ParseException {
        Member member = new Member();
        member.setFirstName(request.getParameter("firstName"));
        member.setLastName(request.getParameter("lastName"));
        member.setEmail(request.getParameter("email"));
        member.setPhone(request.getParameter("phone"));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date membershipDate = df.parse(request.getParameter("membershipDate"));
        member.setMembershipDate(membershipDate);

        memberDAO.addMember(member);
    }

    private void updateMember(HttpServletRequest request)
            throws SQLException, ParseException {
        Member member = new Member();
        member.setMemberID(Integer.parseInt(request.getParameter("memberId")));
        member.setFirstName(request.getParameter("firstName"));
        member.setLastName(request.getParameter("lastName"));
        member.setEmail(request.getParameter("email"));
        member.setPhone(request.getParameter("phone"));

        memberDAO.updateMember(member);
    }

    private void deleteMember(HttpServletRequest request)
            throws SQLException {
        int memberId = Integer.parseInt(request.getParameter("memberId"));
        memberDAO.deleteMember(memberId);
    }
}
