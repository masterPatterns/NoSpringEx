package whiteship;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement;

public class DBA {
	final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; // jdbc 드라이버 주소
	final String DB_URL = "jdbc:mysql://localhost:3306/databasename?useSSL=false"; // DB 접속 주소
	//localhost는 접속하려는 데이터베이스 주소를 입력하시면 됩니다. localhost를 사용하면 됩니다.
	//3306은 데이터베이스에 접속할때 사용하는 포터번호입니다. 설치할때 설정한 포트번호를 사용합니다.
	//databasename에는 접속하려는 database의 name을 입력해줍니다.
	final String USERNAME = "root"; // DB ID 
	final String PASSWORD = "root"; // DB Password
	Connection conn = null;

	public void connection() {
		// MySql에 사용하는여러 객체를 만들어줍니다.
//		Statement stmt = null; 
//		ResultSet rs = null;

		System.out.print("User Table 접속 : ");
		try {
			Class.forName(JDBC_DRIVER); //Class 클래스의 forName()함수를 이용해서 해당 클래스를 메모리로 로드 하는 것입니다.
			//URL, ID, password를 입력하여 데이터베이스에 접속합니다.
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

			// 접속결과를 출력합니다. 
			if (conn != null) {
				System.out.println("성공");
			} else {
				System.out.println("실패");
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Class Not Found Exection"); 
			e.printStackTrace(); 
		} catch (SQLException e) {
			System.out.println("SQL Exception : " + e.getMessage()); 
			e.printStackTrace(); 
		}
	}
	
	public int insert(String sql) {
		int id = -1;

		// SQL을 적는 문서파일
		Statement statement = null;
		// SQL의 실행결과 보고서
		ResultSet rs = null;

		try {
			statement = conn.createStatement();
			statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			rs = statement.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("[INSERT 쿼리 오류]\n" + e.getStackTrace());
		}

		try {
			if (statement != null) {
				statement.close();
			}

			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			System.out.println("[INSERT 종료 오류]\n" + e.getStackTrace());
		}

		return id;
	}
	
	public MemberVo select(String sql) {
		MemberVo memberVo = new MemberVo();
		// SQL을 적는 문서파일
		Statement statement = null;
		// SQL의 실행결과 보고서
		ResultSet rs = null;

		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			if (rs.next()) {
				String id = rs.getString("id"); 
				String pass = rs.getString("pass"); 
				String name = rs.getString("name"); 
				String phone = rs.getString("phone"); 
				String email = rs.getString(5); //rs.getString("email");
				memberVo.setId(id);
				memberVo.setPass(pass);
				memberVo.setName(name);
				memberVo.setPhone(phone);
				memberVo.setEmail(email);
			}
		} catch (SQLException e) {
			System.out.println("[SELECT 쿼리 오류]\n" + e.getStackTrace());
		}

		try {
			if (statement != null) {
				statement.close();
			}

			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			System.out.println("[SELECT 종료 오류]\n" + e.getStackTrace());
		}

		return memberVo;
	}

	public void close() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("[닫기 오류]\n" + e.getStackTrace());
		}
	}
}
