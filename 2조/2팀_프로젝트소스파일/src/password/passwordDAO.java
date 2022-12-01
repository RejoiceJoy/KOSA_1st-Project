package password;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dbconnection.DBConnection;
import main.login_Controller;



public class passwordDAO {	//사용할 DB는 paswordDTO

	
	
	//마이페이지 입장시 비밀번호 확인 DAO 11/21 손영석 작성		
	public int login(String inputPW) { 
		String sql = "SELECT user_pw FROM USER_main where user_id like ?"; // USER_PW
		int inspect = 0;
		try {
			Connection con = DBConnection.getConnection();		//DB연결
			System.out.println("연결성공");						//SQL 문 전달 실행
			PreparedStatement prst = con.prepareStatement(sql);	//1번째 ?에 inputPW 입력
			prst.setString(1, login_Controller.lid);			//excuteQuery 통해 쿼리 실행, ResultSet 타입으로 결과값 반환
			ResultSet rs = prst.executeQuery();
			if(rs.next()) {
			
			if (rs.getString(1).equals(inputPW)) {				
				inspect = 1;									//결과값이 inputPW와 일치 1
			}else {
				inspect = 0; 									//결과값이 inputPW와 불일치 0
			}
			}
		}catch (Exception e) {			
			e.printStackTrace();
		}
		return inspect;
	}
}