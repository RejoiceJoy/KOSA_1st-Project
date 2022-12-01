package mypage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import dbconnection.DBConnection;
import main.login_Controller;

//(11/21 손영석 작성)
public class Mypage {
	// 데이터베이스 값 저장할 변수
	String userid; 
	String userpw;
	String userName;
	String userPn;
	String usereEmail;
	String userAddress;
	String UserFavSeat;
               
      
	//(11/21 손영석 작성)
	public void mypagesonView() {
		// sql문 작성
		String sql = "SELECT * FROM userview WHERE user_ID = ?" ; 
      
		try {
			Connection con = DBConnection.getConnection();		//DB연결
			PreparedStatement prst = con.prepareStatement(sql);	//SQL문 입력
			prst.setString(1, login_Controller.lid);			//SQL문 1번째 ?에 로그인 회원정보 들어있는 전역변수 입력
			ResultSet rs = prst.executeQuery();					//excuteQuery 통해 쿼리 실행, ResultSet 타입으로 결과값 반환
         
			if (rs.next()) { //회원정보 호출
	            userid = rs.getString("user_id"); 			//아이디
	            userpw = rs.getString("user_pw");			//비밀번호
	            userName = rs.getString("user_name");		//이름
	            userPn = rs.getString("user_phone");		//번호
	            usereEmail = rs.getString("user_email");	//이메일
	            userAddress = rs.getString("user_address");	//지역
	            UserFavSeat = rs.getString("user_fav_seat");//선호좌석
         }          
      } catch (Exception e) {
         System.out.println("DB 연결에 문제가 있습니다.");
         e.printStackTrace();
      } // end try
   }// end main
}// end class