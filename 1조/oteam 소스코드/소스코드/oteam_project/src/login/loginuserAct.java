package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.DBConnection;

/*코드 작성자 : 김대영*/

public class loginuserAct {
	
	// 사용자가 누구인지 인식하기 위해 현재 사용자의 member_code를 받을 변수를 전역변수로 선언
	static int member_code;
	
	// 생년월일로 입력력받은 정보를 통해 사용자의 나이를 숫자로 바꿔서 활용하기 위한 변수
	static int member_age;

	// 사용자가 로그인을 성공했을 때 호출되는 메서드
	// 입력 매개변수로 로그인 당시 사용자가 입력했던 member_id를 받는다
	public static void userupdate(String id) {
		
		// 사용자의 id와 일치하는 member_code를 찾기 위한 sql쿼리문 작성
		String run = "SELECT member_code FROM MEMBERS WHERE member_id = ?";
	
		try {
			// db연결
			Connection conn = DBConnection.getConnection();
			
			// PreparedStatement에 쿼리문 전달
			PreparedStatement prst = conn.prepareCall(run);
			
			// 매개변수로 입력받은 member_id를 PreparedStatement의 1번 ?에 전달
			prst.setString(1, id);
			
			// resultset을 사용하기위해 쿼리 실행 결과로 얻는 값을 rs에 전달
			ResultSet rs = prst.executeQuery();
			
			// resultset은 첫 포인터가 열 이름을 가리키기 때문에 rs.next()를 통해 값을 가지고있는 첫 행으로 값을 읽어올 위치를 이동
			rs.next();
			
			// resultset에 저장된 첫 열(member_code)의 값을 받아와 전역변수에 저장
			// 만약 쿼리문이 제대로 실행되었다면 최초 로그인을 한 사용자의 member_code가 반환된 상태일 것
			member_code = rs.getInt(1);
			
			// 일반 Statememt를 사용하기 위해 생성
			Statement stmt = conn.createStatement();
			
			// resultset에 stmt로 뷰 활용한 쿼리문 실행 결과를 반환
			ResultSet rs1 = stmt.executeQuery("select * from member_age_view where member_code = " + member_code);
			
			// 결과로 나온 값을 사용하기 위해 resultset이 가리키는 위치를 행으로 이동
			rs1.next();
			
			// 사용자 나이를 반환
			member_age = rs1.getInt(1);			
			
			// 로그인을 진행한 사용자 아이디가 제대로 전달되었는지 확인하기 위해 출력
			// 사용자가 입력한 아이디 출력
			System.out.println(id);	
			// 사용자가 입력한 id와 일치하는 member_code를 출력 
			System.out.println(member_code);
			
			// 로그인 성공 문구 출력
			System.out.println("로그인성공");			
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}// end try
	}// end userupdate

	// 사용자가 로그인을 시도했을 때 호출되는 메서드
	// 입력 매개변수로 로그인 당시 사용자가 입력했던 member_id,member_pw를 받는다
	public static int loginCheck(String id, String pw) {
		
		// DB에 사용자가 있다면 1을, 아니라면 0을 반환할 것이므로 
		// 0또는 1을 담을 변수 선언 및 0으로 초기화
		int check = 0;
		
		String run = "SELECT count(*) FROM members " + "WHERE MEMBER_ID = ? AND MEMBER_PW = ?";
	
		try {
			// db연결
			Connection conn = DBConnection.getConnection();
			
			// PreparedStatement에 쿼리문 전달
			PreparedStatement prst = conn.prepareCall(run);
			
			// 매개변수로 입력받은 member_id를 PreparedStatement의 1번 ?에 전달
			prst.setString(1, id);
			
			// 매개변수로 입력받은 member_pw를 PreparedStatement의 2번 ?에 전달
			prst.setString(2, pw);
			
			// resultset을 사용하기위해 쿼리 실행 결과로 얻는 값을 rs에 전달
			ResultSet rs = prst.executeQuery();
			
			// resultset은 첫 포인터가 열 이름을 가리키기 때문에 rs.next()를 통해 값을 가지고있는 첫 행으로 값을 읽어올 위치를 이동
			rs.next();
			
			// resultset에 저장된 첫 열(member_code)의 값을 받아와 전역변수에 저장
			// 만약 쿼리문이 제대로 실행되었다면 최초 로그인을 한 사용자의 member_code가 반환된 상태일 것
			check = rs.getInt(1);
			
			// 문구 출력
			System.out.println("로그인 시도 계정 조회 완료");			
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}// end try
		return check;
	}// end loginCheck
	
	// 현재 접속중인 사용자의 member_code를 반환하여 
	// 프로그램에서 사용자 인식이 필요한 시점에 호출하기 위한 메서드 생성
	public static int whoIsMember() {
		return member_code;
	}
	
	// 현재 접속 사용자의 나이를 반환
	public static int memberAge() {
		return member_age;
	}
	
}// end loginuserAct