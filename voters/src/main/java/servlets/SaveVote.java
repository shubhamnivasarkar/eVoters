package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.core.dao.CandidateDAOImpl;
import com.core.dao.VoterDAOImpl;
import com.core.pojos.Voter;
import static com.core.utils.DBUtils.getConnection;
/**
 * Servlet implementation class SaveVote
 */
@WebServlet("/savevote")
public class SaveVote extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn;
	@Override
	  public void init() throws ServletException {
	    try {
        conn = getConnection();
      }
	    catch (Exception e) {
        throw new ServletException("Failed to initialize save vote servlet.", e);
      }
	  }

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	  
	  res.setContentType("text/html");
    try (PrintWriter pw = res.getWriter()) {
      HttpSession session = req.getSession();
      Voter voter = (Voter) session.getAttribute("voterDetails");
      if (voter == null) {
        res.sendRedirect("signin.html");
        return;
      }
      if (voter.getStatus() == 1) {
        res.sendRedirect("status");
        return;
      }
      try {
        int candidateID = Integer.parseInt(req.getParameter("candidate"));
        VoterDAOImpl voterDAO = (VoterDAOImpl) session.getAttribute("voterDAO");
        CandidateDAOImpl candidateDAO = (CandidateDAOImpl) session.getAttribute("candidateDAO");
        
        boolean castStatus = false;
        conn.setAutoCommit(false);
        if (candidateDAO.updateVote(candidateID)) {
          if (voterDAO.updateStatus(voter.getId(), true)) {
            castStatus = true;
            conn.commit();
          }
          else {
            conn.rollback();
          }
        }
        conn.setAutoCommit(true);
        if (castStatus) {
          pw.write("<h1>Thank you "+voter.getName()+" for voting.</h1>");
          pw.write("<h1>Your vote has been saved successfully.</h1>");
          pw.write("<h5><a href='./voterlogout'>Logout</a></h5>");
        }
        else {
          pw.write("<h1>Failed to save your vote. Something went wrong.</h1>");
          pw.write("<h5><a href='./'>Back to Home</a></h5>");
        }
      }
      catch (Exception e) {
        pw.write("<h1>Failed to save your vote. Something went wrong.</h1>");
        pw.write("<h5><a href='./'>Back to Home</a></h5>");
      }
    }
	}

}
