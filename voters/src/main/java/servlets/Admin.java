package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.core.dao.CandidateDAOImpl;
import com.core.pojos.Candidate;

/**
 * Servlet implementation class Admin
 */
@WebServlet("/admin")
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	  res.setContentType("text/html");
		try (PrintWriter out = res.getWriter()) {
		  HttpSession session = req.getSession();
		  
		  CandidateDAOImpl candidateDAO = (CandidateDAOImpl) session.getAttribute("candidateDAO");
		  try {
        List<Candidate> ranking = candidateDAO.getRanking();
        out.write("<h1>Candidates Ranking</h1>");
        out.write("<ol>");
        ranking.forEach(c -> {
          out.write("<li>"+c+"</li>");
        });
        out.write("</ol>");
      }
		  catch (SQLException e) {
		    out.write("<h4>Oops....Failed to fetch candidates list.</h4>");
        out.write("<h5><a href='./'>Back to Home</a></h5>");
      }
		}
	}

}
