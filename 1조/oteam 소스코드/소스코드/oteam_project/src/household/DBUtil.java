package household;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;




/*코드 작성자 : 김준영*/
//DB연결과정과 SQL문의 실행과정의 묶음
public class DBUtil {  
	
	
	//DB연결 준비
	private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver"; //보안설정된 드라이버이름
	private static final String url = "jdbc:oracle:thin:@localhost:1521/xepdb1"; //보안설정된 DB주소
	private static final String id = "oteam";									//보안설정된 id
	private static final String pwd = "oteam";									//보안설정된 id   
	private static Connection conn = null; 										//보안설정된 연결 객체 생성
	
	
	//DB연결 메소드만듬 > 호출해 연결
	public static void dbConnect() throws SQLException, ClassNotFoundException {  
		try {
			Class.forName(JDBC_DRIVER); 								//자바API로 오라클DB 불러옴 
			System.out.println("Oracle JDBC Driver 등록");				//jar파일 찾을시 콘솔창 문구 출력
		} catch (ClassNotFoundException e) {
			System.out.println("Oracle JDBC Driver 실패"); 			//파일 못찾을시 콘솔창 문구 출력
			e.printStackTrace();                                     //예외전가 전체확인
			throw e;
		}
		
		try {
			conn = DriverManager.getConnection(url, id, pwd); //DriverManager를 통해 DB주소와 사용자 정보로 DB접속
			System.out.println("Oracle 연결");				 
		} catch (SQLException e) {							  
			System.out.println("Oracle 연결실패");
			e.printStackTrace();											
			throw e;
		}
	}
	
	
	//SQL문 실행과정-----------------------------------------------------------------------------------
	//select 실행문
	public static ResultSet dbExecuteQuery(String sqlStmt) throws SQLException, ClassNotFoundException {
		Statement stmt = null;			//SQL 문을 실행하고 결과를 반환하기 위해 클래스, SQL을 받은 Statement객체선언
		ResultSet resultSet = null;		//Statement에서 반환된 결과 데이터를 ResultSet으로 받음
			
		try {
			dbConnect();								//실행전 DB와 접속
			stmt = conn.createStatement();			//SQL받은 객체에 연결객체 넣어줌
			resultSet = stmt.executeQuery(sqlStmt);	//SQL문을 실행함
			
		} catch (SQLException e) {
			System.out.println("select의 SQL문 실패");
			System.out.println("검색 실패");
			Alert fail = new Alert(AlertType.ERROR);
			fail.setHeaderText("올바르지 않는 형식입니다.");
			fail.setContentText("시작과 마지막 날짜를 선택하십시요");
			fail.showAndWait();
			throw e;
		} 
		return resultSet;							//ResultSet 리턴
	}
	
	//insert, update, delete 실행문
	public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
		
		Statement stmt = null;
		try {
			dbConnect();
			stmt = conn.createStatement();
			stmt.executeUpdate(sqlStmt);    //insert, delete 실행 때
		} catch (SQLException e) {
			System.out.println("insert, delete의 SQL문 실패");
			throw e;
		}
	}
}







