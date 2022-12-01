package mypage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import dbconnection.DBConnection;
import main.login_Controller;



public class UpdateDAO {	
	//(11/22 손영석 작성)회원 정보 페이지에 새롭게 입력된 정보를 DB에 업데이트 
	public static void Signupupdate(UpdateDTO uDTO) {

			String sql = "update user_main " //누락된 set값 발생, sql문 작성시 띄어쓰기 안함으로 구문이 붙어 오류발생
					+ " SET user_pw = ?, user_name = ?, user_phone = ?, user_email = ? " //, user_address = ?, user_fav_seat = ? "
					+ " where user_id = ? ";

	 	try{
				Connection conn = DBConnection.getConnection();			//DB연결
				PreparedStatement prst = conn.prepareStatement(sql);	//SQL문 실행
				
				
				prst.setString(1, uDTO.getUser_pw());		//SQL문 1번째 ?에 새로입력받은 비밀번호 입력
				prst.setString(2, uDTO.getUser_name());		//SQL문 2번째 ?에 새로입력받은 이름 입력
				prst.setString(3, uDTO.getUser_phone());	//SQL문 3번째 ?에 새로입력받은 번호 입력
				prst.setString(4, uDTO.getUser_email());	//SQL문 4번째 ?에 새로입력받은 이메일 입력	
				prst.setString(5, login_Controller.lid);	//SQL문 5번째 ?에 회원정보 전역변수 사전에 할당된 로그온 유저아이디 입력
				prst.executeUpdate();						//DB 업데이트

	 	} catch(SQLException e) {
	 		System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
