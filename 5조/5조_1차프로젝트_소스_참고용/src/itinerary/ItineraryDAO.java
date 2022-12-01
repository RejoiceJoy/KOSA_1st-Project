package itinerary;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.DAOInterface;

// DB의 여행 일정 정보(ITINERARIES 테이블)에 접근하기 위한 DAO 클래스(DAOInterface 구현)
// 작성자 : 이기쁨
public class ItineraryDAO implements DAOInterface<ItineraryVO, String>{
	
	//선택 일정 insert 메서드
	@Override
	public void insertData(ItineraryVO itineraryVO) {
		// SQL insert 문 작성
		String sql = "insert into tour.itineraries(itinerary_code, member_id, "
					+ "start_date, choice1, choice2, choice3, choice4, choice5, total_price) "
					+ "values(itinerary_code_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";
		// 일정이 5개 다 차 있을 경우 itinerary 테이블에 일정 정보 삽입
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);	// SQL 문 세팅
			// insert할 값들을 차례대로 인수에 지정
			pstmt.setString(1, itineraryVO.getMember_id());	
			pstmt.setString(2, itineraryVO.getStart_date());			
			pstmt.setString(3, itineraryVO.getChoice1());	
			pstmt.setString(4, itineraryVO.getChoice2());
			pstmt.setString(5, itineraryVO.getChoice3());
			pstmt.setString(6, itineraryVO.getChoice4());
			pstmt.setString(7, itineraryVO.getChoice5());
			pstmt.setInt(8, itineraryVO.getTotalPrice());
			pstmt.executeUpdate();							// insert문 실행
			System.out.println("일정이 확정되었습니다.");
		
		}catch (SQLException e) {
			System.out.println("ITINERARIES 테이블 insert 실패");
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}//end try		
	}//end insertItinerary

	// update 수행 메소드 구현
	@Override
	public void updateData(ItineraryVO vo) {
	}

	// 회원 ID를 입력받아 해당 회원이 가장 최근 생성한 일정 정보를 가져오는 메소드 구현
	// 작성자 : 최영광, 이수봉
	@Override
	public ItineraryVO selectOneRow(String member_id) {
		int code = 0;			// 일정 코드
		String id = null;		// 회원 ID
		String date = null;		// 출발 날짜
		String cho1 = null;		// 선택 관광지 1
		String cho2 = null;		// 선택 관광지 2
		String cho3 = null;		// 선택 관광지 3
		String cho4 = null;		// 선택 관광지 4
		String cho5 = null;		// 선택 관광지 5
		int tot_price = 0;		// 총 예산
		
		try {
			// ID 기준 가장 최신의 일정(itinerary_code는 시퀀스를 사용하므로 값이 가장 큰 행이 가장 최신에 해당)을 사용합니다.
			// 회원 ID가 입력값과 일치하는 행을 일정 코드 기준으로 내림차순 했을 때 가장 첫번째 행을 select하는 SQL 문
			String sql ="SELECT * FROM tour.ITINERARIES WHERE member_id = ? and ROWNUM = 1 order by itinerary_code desc";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);	// SQL 문 세팅
			preparedStatement.setString(1, member_id);							// 인수로 회원 ID를 입력
			ResultSet resultSet = preparedStatement.executeQuery();				// select 문 실행
			
			// select된 행이 있는 경우 값들을 지역변수에 저장
			if(resultSet.next()) {
				code = resultSet.getInt(1);
				id = resultSet.getString(2);
				date = resultSet.getString(3).substring(0, 11);
				cho1 = resultSet.getString(4);
				cho2 = resultSet.getString(5);
				cho3 = resultSet.getString(6);
				cho4 = resultSet.getString(7);
				cho5 = resultSet.getString(8);
				tot_price = resultSet.getInt(9);
			}
			
		} catch (SQLException e) {
			System.out.println("ITINERARIES 테이블 select 실패");
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ItineraryVO(code, id, date, cho1, cho2, cho3, cho4, cho5, tot_price);	// ItineraryVO 객체를 생성자로 초기화 및 생성하여 반환
	}
}
