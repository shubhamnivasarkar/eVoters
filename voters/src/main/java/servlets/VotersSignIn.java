package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.core.dao.CandidateDAOImpl;
import com.core.dao.VoterDAOImpl;
import com.core.exception.InvalidVoterException;
import com.core.pojos.Voter;
import com.core.utils.DBUtils;

/**
 * Servlet implementation class SignIn
 * @author Anshuman Gupta
 */
@WebServlet(urlPatterns = {"/signin"}, loadOnStartup = 1)
public class VotersSignIn extends HttpServlet {
	private static final long serialVersionUID = 3L;
	private VoterDAOImpl voters;
	private CandidateDAOImpl candidates;

	public void init() throws ServletException {
	  try {
      voters = new VoterDAOImpl();
      candidates = new CandidateDAOImpl();
    } 
	  catch (Exception ex) {
      throw new ServletException("Failed to initialize Voter's DAO " + getClass().getName(), ex);
    }
	}

	public void destroy() {
		try {
		  voters.close();
		  candidates.close();
		  DBUtils.closeConnection();
		}
		catch (Exception ex) {
		  throw new RuntimeException("Failed to cleanup Voter's DAO " + getClass().getName(), ex);
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	  res.setContentType("text/html");
	   
	  try (PrintWriter pw = res.getWriter()) {
	    String email = req.getParameter("email");
	    String password = req.getParameter("password");
	    try {
        Voter v = voters.authenticate(email, password);

        HttpSession session = req.getSession();
        session.setAttribute("voterDetails", v);
        session.setAttribute("candidateDAO", candidates);
        session.setAttribute("voterDAO", voters);
        
        if (v.getRole().equals("admin")) {
          res.sendRedirect("admin");
          return;
        }
        
        if (v.getStatus() == 1) {
          res.sendRedirect("status");
          return;
        }
        
        //req.getServletContext().getSessionCookieConfig().setMaxAge(30*60);
        //session.setMaxInactiveInterval(60*60);
        res.sendRedirect("castvote");
      }
	    catch (SQLException e) {
	      pw.write("Can not process your request.");
	      pw.write("<h4><a href='signin.html'>Try Again</a></h4>");
      }
	    catch (InvalidVoterException ex) {
        pw.write("<h1>Invalid email or password.<h1>");
        pw.write("<h4><a href='signin.html'>Try Again</a></h4>");
      }
	  }
	}

}
