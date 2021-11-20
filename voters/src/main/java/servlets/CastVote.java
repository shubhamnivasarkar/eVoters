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
import com.core.pojos.Voter;

/**
 * Servlet implementation class CastVote
 */
@WebServlet("/castvote")
public class CastVote extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		try (PrintWriter pw = res.getWriter()) {
		  HttpSession session = req.getSession();
		  Voter v = (Voter)session.getAttribute("voterDetails");
		  if (v == null) {
		    res.sendRedirect("signin.html");
		    return;
		  }
		  pw.write("<h1>Welcome, " + v.getName() + "</h1>");
		  try {
        List<Candidate> candidates = ((CandidateDAOImpl)session.getAttribute("candidateDAO")).getCandidates();		    
        pw.write("<h3>Choose Candidate</h3>");
        pw.write("<form method='POST' action='./savevote'>");
        candidates.forEach(c -> {
          pw.write("<label for='" + c.getId() + "'>");
          pw.write("<input type='radio' name='candidate' value='" + c.getId() + "' id='" + c.getId() + "' />");
          pw.write("&nbsp; " + c + "</label><br/>");
        });
        pw.write("<input type='submit' value='Vote' />");
        pw.write("</form>");
        pw.write("<h4><a href='voterlogout'>Logout</a></h4>");
      }
		  catch (SQLException e) {
        pw.write("<h4>Oops....Failed to fetch candidates list.</h4>");
        pw.write("<h5><a href='./'>Back to Home</a></h5>");
      }
		}
	}

}
