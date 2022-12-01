package reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbconnection.DBConnection;
import main.login_Controller;
//(11/20 윤준호 작성)
public class ReserveDAO {


	//예약 정보를 테이블에 저장
	//(11/21 문수지 수정) sql문 수정
	public static void ReserveInsert(ReserveDTO rDTO) {
		String uid = login_Controller.lid;
		// (11/21 문수지) res_code가 문자형으로 되어있기 때문에 to_number로 숫자형으로 변환
		String sql = "INSERT INTO RESERVE"
				+ "(res_code, user_code, user_fav_seat, bus_code, location_code, res_date)"
				+ "VALUES((select 'res'||nvl(max(to_number(substr(res_code, 4)))+1,1) from RESERVE),"
				+ " (select user_code from user_main where user_id ='"+ uid +"'),?,?,?,?)";
		try {
			Connection conn = DBConnection.getConnection();
			PreparedStatement prst = conn.prepareStatement(sql);
			
			prst.setString(1, rDTO.getUser_fav_seat());
			prst.setString(2, rDTO.getBus_code());
			prst.setString(3, rDTO.getLocation_code());
			prst.setString(4, rDTO.getRes_date());
			prst.executeUpdate();
			System.out.println("예약 정보 입력 성공");
	 	} catch(SQLException e) {
	 		System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//한사람당 하루에 한 좌석만 예약 (11/22 문수지 작성)
	public static int OnedayReserve() {
		String uid = login_Controller.lid;	//로그인 한 User_ID 정보 할당
		String sql = "SELECT max(count(*)) FROM reserve WHERE user_code=(Select user_code FROM user_main WHERE user_id='"+uid+"') "
				+ "GROUP BY res_Date";		// 로그인한 user_id의 user_code와 일치하는 정보의 예약정보에서 예약된 날짜의 예약 개수의 max값 도출한 sql문
		int inspect=0;
		
		try {
			Connection con = DBConnection.getConnection();
			PreparedStatement prst = con.prepareStatement(sql);
			ResultSet rs = prst.executeQuery();
			
			rs.next();
			int result = rs.getInt(1);
			if(result > 1) {	// sql문에서 max값이 1을 초과할 경우 하루에 중복된 예약건이 있다는 뜻
				inspect=1;		// 1반환
			}else {
				inspect=0;		// 0반환
			}//end else
			
		}catch (Exception e) {
			e.printStackTrace();
		}// end catch
		return inspect;
	}	// end method

	
}//end class
