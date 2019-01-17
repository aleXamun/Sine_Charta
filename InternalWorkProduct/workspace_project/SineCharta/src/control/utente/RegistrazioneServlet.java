package control.utente;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import interfaces.UserModelI;
import manager.UsersManager;
/**
 * Servlet implementation class RegistrazioneServlet
 */
@WebServlet("/RegistrazioneServlet")
public class RegistrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	static UserModelI<User> user = new UsersManager();
	User usr = new User();
	
    public RegistrazioneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		String action = request.getParameter("action");

		try {
				if(action.equalsIgnoreCase("register")) {
					System.out.println("strunz");;
					String username = request.getParameter("username");
					String name = request.getParameter("name");
					String surname = request.getParameter("surname");
					String email = request.getParameter("email");
					String password = request.getParameter("password");
					
					if(username.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty() || email.isEmpty()) {
						System.out.println("babab");

						RequestDispatcher rd = request.getRequestDispatcher("../jsp_page/registrationPage.jsp");
						out.println("<font color=red>Please fill all the fields</font>");
						rd.forward(request, response);
					}
					System.out.println("kldbvib");
					usr.setUsername(username);
					usr.setPassword(password);
					usr.setName(name);
					usr.setSurname(surname);
					usr.setEmail(email);
					user.doSave(usr);	
			
				System.out.println("successfully inserted");
			//	RequestDispatcher rd = request.getRequestDispatcher("/jsp_page/index.jsp");
				System.out.println("OK");
				response.sendRedirect("jsp_page/index.jsp");
				//rd.forward(request, response);
				
				} 
		 } catch(SQLException e) {
			 e.printStackTrace();
			 HttpSession session = request.getSession();
			 String str = "true";
			 session.setAttribute("RegistrationError", str);
			 RequestDispatcher rd = request.getRequestDispatcher("");
			 rd.forward(request, response);
		 }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}