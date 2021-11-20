package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.core.pojos.Voter;

/**
 * Servlet implementation class VoteStatus
 */
@WebServlet("/status")
public class VoteStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	  res.setContentType("text/html");
	  try (PrintWriter pw = res.getWriter()) {
	    HttpSession session = req.getSession();
	    Voter v = (Voter)session.getAttribute("voterDetails");
	    if (v == null) {
	      res.sendRedirect("signin.html");
	      return;
	    }
	    session.invalidate();
	    pw.write("<h1>Dear, " + v.getName() + "</h1>");
	    pw.write("<h2>You've already voted.</h2>");
	    pw.write("<h4><a href='./voterlogout'>Logout</a></h4>");
	  }
	}

}
