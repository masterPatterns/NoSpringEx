package whiteship;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("*.do") 
public class MemberController extends HttpServlet {
    
	// http://xxx.com/member?action=create, http://xxx.com/member?action=login
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String conPath = req.getContextPath();
        String command = uri.substring(conPath.length());
        
        String action = req.getParameter("action");

        switch(action) {
            case "create": 
            	createMember();
            	break;
            case "login": 
            	loginMember();
            	break;
            
        }
    }
	
	public void createMember() {
		DBA dbConn = new DBA();

		dbConn.connection();
		dbConn.insert("INSERT INTO article SET regDate = NOW(), title = '제목', `body` = '내용';");
		dbConn.close();
	}
	
	public void loginMember() {
		MemberVo memberVo = new MemberVo();
		DBA dbConn = new DBA();

		dbConn.connection();
		memberVo = dbConn.select("SELECT * FROM member WHERE id = 'xxxxx';");
		dbConn.close();
		
		// 로그인 확인 로직
		if (memberVo.getId() != null) {
			
		} else {

		}
	}
	
	
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String uri = req.getRequestURI();
//        String conPath = req.getContextPath();
//        String command = uri.substring(conPath.length());
//
//        switch(command) {
//            case "create.do": 
//            	createMember();
//            	break;
//            case "login.do": 
				// LoginService loginService = new LoginService();
				// loginService.loginMember();
//            	loginMember();
//            	break;
//            
//        }
//    }
}
