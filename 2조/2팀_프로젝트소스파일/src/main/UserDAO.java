package main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dbconnection.DBConnection;



public class UserDAO {	//사용할 DB는 userDTO

	
	
	//로그인(11/17 김유진, 손영석 작성)
	public int login(String inputID, String inputPW) { 
		String sql = "SELECT user_pw FROM USER_main WHERE user_ID = ?"; // USER_ID
		int inspect = 0;
		try {
			Connection con = DBConnection.getConnection();
			PreparedStatement prst = con.prepareStatement(sql);
			prst.setString(1, inputID);
			ResultSet rs = prst.executeQuery();// 어떠한 결과를 받아오는 ResultSet 타입의 rs 변수에 쿼리문을 실행한 결과를 넣어줌
			if(rs.next()) {
			
			if (rs.getString(1).equals(inputPW)) {
				inspect = 1;
			}else {
				inspect = 0; // 비밀번호 불일치
			}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return inspect;
	}
	
	//로그아웃---

	
	//(11/17 윤준호 작성)회원가입 페이지에 입력된 정보를 DB에 저장
	//(11/21 문수지 수정) sql문 수정
	public static void SignupInsert(UserDTO uDTO) {
			// (11/21 문수지) user_code가 문자형으로 되어있기 때문에 to_number로 숫자형으로 변환
			String sql = "INSERT INTO user_main"
					+ "(user_code, user_id, user_pw, user_name, user_phone, user_email, user_address, user_fav_seat)"
					+ "VALUES((select 'user'||nvl(max(to_number(substr(user_code, 5)))+1,1) from user_main),?,?,?,?,?,?,?)";
	 	try{	
	 		//DB 접속
			Connection conn = DBConnection.getConnection();
			PreparedStatement prst = conn.prepareStatement(sql);
			
			//회원가입창에 입력된 정보를 토대로 할당된 변수를 ?에 입력해줌
			prst.setString(1, uDTO.getUser_id());
			prst.setString(2, uDTO.getUser_pw());
			prst.setString(3, uDTO.getUser_name());
			prst.setString(4, uDTO.getUser_phone());
			prst.setString(5, uDTO.getUser_email());
			prst.setString(6, uDTO.getUser_address());
			prst.setString(7, uDTO.getUser_fav_seat());
			prst.executeUpdate();	//sql 구문 실행
				
	 	} catch(SQLException e) {
	 		System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//회원가입 시 중복ID 체크(11/22 문수지 작성)
	public boolean IdChk(String inputID) { 
		String sql = "SELECT count(user_id) FROM project_first.user_main WHERE user_id=?"; // USER_ID
		boolean isExist = false;
		
		try {
			Connection conn = DBConnection.getConnection();	// Connection 연결
			PreparedStatement prst = conn.prepareStatement(sql);
			prst.setString(1, inputID); // 1번 물음표에 문자열로 입력받은 id 저장
			ResultSet rs = prst.executeQuery();// 쿼리문 실행 결과를 저장 및 실행
			rs.next(); //레코드가 있는 경우 읽음
			
			int result = rs.getInt(1);
			if(result == 1) {
				isExist = true; //중복된 ID 존재  
			}else {
				isExist = false; //중복된 ID 없음
			}//end else
			
		}catch (Exception e) {
			e.printStackTrace();
		}//end catch
		return isExist;	//실행된 검사값 반환
	}//end boolean
	
}//end class
