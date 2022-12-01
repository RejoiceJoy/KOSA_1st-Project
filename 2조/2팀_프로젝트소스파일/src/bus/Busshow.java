package bus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbconnection.DBConnection;

//버스배차현황 및 잔여좌석 호출용도(이하 별도 표기 없을시 11.21, 손영석 김유진 공동작성)
public class Busshow {

    // 데이터베이스 값 저장할 buslist 배열 생성
    List<String[]> buslist = new ArrayList<String[]>();
    // 잔여좌석 계산을 위한 변수 생성  
    int bus_totalseat;
    int bus_occupiedseat;
    int bus_remainseat;
    
    //버스배차현황 및 잔여좌석 호출용도(이하 별도 표기 없을시 11.21, 손영석 김유진 공동작성)
    public void allBusView(String bus_loc) {
                    
      // sql문 작성
        String sql = "SELECT * FROM bus where bus_location = ?"; 
      
      try {
    	 //DB연결
         Connection con = DBConnection.getConnection();
         PreparedStatement prst = con.prepareStatement(sql);
         //sql문 실행
         prst.setString(1, bus_loc);   
         ResultSet rs = prst.executeQuery();
         //sql문을 통해 불러온 값들은 buslist 배열에 저장
         while (rs.next())
         {
            String[] arrStr = { rs.getString("bus_code"), rs.getString("bus_size"),rs.getString("bus_time"), rs.getString("bus_location")};
            
            buslist.add(arrStr);
            
         }   // end while       
      } catch (Exception e) {
         System.out.println("DB 연결에 문제가 있습니다.");
         e.printStackTrace();
      } // end try
   }// end method
     

     // 버스잔여 좌석 가져오기
     public int left_seat_bus(String bus_code, String bus_loc, String bus_date) {
         
         
        // sql문 작성
        String sql = "SELECT count(*) as reservecount FROM reserve where bus_code = ? and location_code = ? and res_date = ? "; 
        String sql2 = "SELECT bus_size FROM bus where bus_code = ? and bus_location = ?  "; 
                
         try {
        	//DB연결 및 sql문 실행 : 탑승지역, 일자, 버스번호와 일치하는 예약현황 개수를 가져오기
            Connection con = DBConnection.getConnection();
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setString(1, bus_code);
            prst.setString(2, bus_loc);
            prst.setString(3, bus_date);
            ResultSet rs = prst.executeQuery();
            //DB연결 및 sql문 실행 : 버스번호에 해당하는 버스의 버스크기(총좌석수) 가져오기
            Connection con2 = DBConnection.getConnection();
            PreparedStatement prst2 = con2.prepareStatement(sql2);
            prst2.setString(1, bus_code);
            prst2.setString(2, bus_loc);
            ResultSet rs2 = prst2.executeQuery();
            
            // 버스 총좌석수 호출
            while (rs2.next())
            {
               bus_totalseat = rs2.getInt("bus_size");
            }         
            
            // 예약현황 좌석개수를 호출
            while (rs.next())
            {
               bus_occupiedseat = rs.getInt("reservecount");
            }
            //전체좌석수에서 예약된좌석수를 차감하여 잔여좌석수를 산출
            bus_remainseat = bus_totalseat - bus_occupiedseat;
    
         } catch(SQLException e) {
             System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
         } catch (Exception e) {
            System.out.println("DB 연결에 문제가 있습니다.");
            e.printStackTrace();
         } // end try
         return bus_remainseat;   
      }// end method
     
}// end class