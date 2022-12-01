package reserveSearch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbconnection.DBConnection;
import main.login_Controller;
//예약정보 호출용도(이하 클래스 별도 표기가 없을시 11.17, 손영석 김유진 공동작성)
public class ReserveSearch {
	// 저장할 변수 생성
	String myres_date = null;
	String mylocation_code = null;
	String mybus_code = null;
	String myUSER_FAV_SEAT = null;
	String myUser_code = null;
	// DB의 user_main 테이블에서 사용자 로그인 id를 바탕으로 user_code를 확인, user_code를 활용하여 reserve 테이블에서 예약정보 호출   
	public void reserveView() {
    

		//sql문 작성
		String sql = "select to_char(res_date, 'YYYY-MM-DD') as res_date , location_code, bus_code, USER_FAV_SEAT from reserve "
				+ " where user_code = (select user_code from user_main where user_id = ?) and (res_date > sysdate) order by res_date " ;      
		String sql2 = "select user_code from user_main where user_id = ? " ;  
      
   
		// 로그인한 user_id를 바탕으로 내부 DB에서 상응하는 user_code 가져오기
		try{
			//DB연결 및 실행
			Connection conn2 = DBConnection.getConnection();
			PreparedStatement prst2 = conn2.prepareStatement(sql2);      
         
			prst2.setString(1, login_Controller.lid);    // 로그인한 id는 lid 변수에서 기저장, lid와 일치하는(즉 로그인id와 일치하는) user_code를 호출      
          
			ResultSet rs2 = prst2.executeQuery();   
          if (rs2.next()) {
        	  myUser_code = rs2.getString("user_code");
          }
		} catch(SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
          
      
      
		// 위의 user_code를 활용하여 내부 DB의 예약 테이블에서 예약정보를 가져오기
		try{   
	    	//DB연결 및 실행
			Connection conn = DBConnection.getConnection();
			PreparedStatement prst = conn.prepareStatement(sql);
         
         
			prst.setString(1, login_Controller.lid);     // lid 받아온것 활용 
               
         
         ResultSet rs = prst.executeQuery();
         // 위에서 생성한 변수에 가져온 DB 예약정보를 저장
         if (rs.next()) {
        	 myres_date = rs.getString("res_date");
        	 mylocation_code = rs.getString("location_code");
        	 mybus_code = rs.getString("bus_code");
        	 myUSER_FAV_SEAT = rs.getString("USER_FAV_SEAT");
         }
         
         
   } catch(SQLException e) {
      System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
   } catch (Exception e) {
      e.printStackTrace();
   }//end try
   }//end method
}//end class
   
   