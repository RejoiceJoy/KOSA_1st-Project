package itinerary;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.DAOInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// DB의 관광지 정보(SIGHTS_INfO 테이블)에 접근하기 위한 DAO 클래스(DAOInterface 구현)
// 작성자 : 이기쁨, 이수봉
public class SightInfoDAO implements DAOInterface <SightInfoVO, String> {
	// insert 수행 메소드 구현
	@Override
	public void insertData(SightInfoVO vo) {
	}

	// update 수행 메소드 구현
	@Override
	public void updateData(SightInfoVO vo) {
	}

	// 입력 매개변수를 기준으로 특정 행 검색하는 메소드 구현
	@Override
	public SightInfoVO selectOneRow(String sightName) {
		SightInfoVO vo = new SightInfoVO();												// SightInfoVO VO 객체 생성
		String sql = "select * from tour.sights_info where sight_name = ? ";			// 관광지명을 넣어서 해당 관광지명을 불러오는 sql문
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);						// sql 문을 preparedStatement로 받아서 실행
			pstmt.setString(1, sightName);												// 첫번째 물음표에 sight_name을 넣어서 select 해옴
			ResultSet rs = pstmt.executeQuery();										// 실행한 결과를 ResultSet에 담음
			rs.next();																	// ResultSet을 next()로 읽어옴
			// rs에 받은 관광지 정보 데이터들을 vo 객체에 저장
			vo.setSight_name(rs.getString("sight_name"));
			vo.setPrice(rs.getInt("price"));
			vo.setSight_data(rs.getString("sight_data"));
			vo.setAddress(rs.getString("address"));
			vo.setImage(rs.getString("image"));
			
		}catch (SQLException e) {
			System.out.println("\n에러 발생! >> showSightInfo");		// showSightInfo 메소드에서 SQLException 발생함을 알림
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}//end try	
		return vo;	// 관광지 정보가 담겨있는 VO 객체 반환
	}
	
	// 국가 리스트 출력 메서드
	public ObservableList<String> showCountryList() {
		ObservableList<String> countryList = FXCollections.observableArrayList();	// 국가 리스트 저장할 ObservableList 생성(JavaFX에서 제공하는 List)
		String sql = "select country from tour.sights_info group by country";		// tour.sights_info테이블에서 country 열을 group by로 묶어서 select 함
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);					// sql 문을 preparedStatement로 받아서 실행
			ResultSet rs = pstmt.executeQuery();									// 실행한 결과를 ResultSet에 담음
			while(rs.next()) {														// ResultSet을 next()로 읽어옴
				countryList.add(rs.getString("country"));							// 읽어온 country 열의 값을 countryList에 담아줌
			}//end while
		}catch (SQLException e) {
			System.out.println("\n에러 발생! >> showCountryList");					// showCountryList 메소드에서 SQLException 발생함을 알림
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());// sql문 불러올 때 오류 발생시 오류코드와 메세지 출력
		} catch (Exception e) {														// 남은 오류 처리
			e.printStackTrace();
		}//end try
		return countryList;	// 국가 리스트 반환
	}//end showRegionList

	//선택한 국가의 지역 리스트 출력 메서드
	public ObservableList<String> showRegionList(String country) {
		ObservableList<String> regionList = FXCollections.observableArrayList();				// 지역 리스트 저장할 ObservableList 생성(JavaFX에서 제공하는 List)
		String sql = "select region from tour.sights_info where country = ? group by region"; 	//tour.sights_info테이블에서 region 열을 group by로 묶어서 select 함
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);						// sql 문을 preparedStatement로 받아서 실행
			pstmt.setString(1, country);												// 첫번째 물음표에 country를 넣어서 select 해옴
			ResultSet rs = pstmt.executeQuery();										// 실행한 결과를 ResultSet에 담음
			while(rs.next()) {															// ResultSet을 next()로 읽어옴
				regionList.add(rs.getString("region"));									// 읽어온 region 열의 값을 regionList에 담아줌
			}//end while
		}catch (SQLException e) {
			System.out.println("\n에러 발생! >> showRegionList");							// showRegionList 메소드에서 SQLException 발생함을 알림
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());	// sql문 불러올 때 오류 발생시 오류코드와 메세지 출력
		} catch (Exception e) {															// 남은 오류 처리
			e.printStackTrace();
		}//end try
		return regionList;	// 지역 리스트 반환
	}//end showRegionList
	
	//관광지명 리스트 출력 메서드
	public ObservableList<String> showSightNameList(String region) {
		ObservableList<String> sightNameList = FXCollections.observableArrayList();		// 관광지명 리스트 저장할 ObservableList 생성(JavaFX에서 제공하는 List)
		String sql = "select sight_name from tour.sights_info where region = ? ";		//tour.sights_info테이블에서 sight_name 열을 select 해옴
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);						// sql 문을 preparedStatement로 받아서 실행
			pstmt.setString(1, region);													// 첫번째 물음표에 region을 넣어서 select 해옴
			ResultSet rs = pstmt.executeQuery();										// 실행한 결과를 ResultSet에 담음
			while(rs.next()) {															// ResultSet을 next()로 읽어옴
				sightNameList.add(rs.getString("sight_name"));							// 읽어온 sight_name 열의 값을 sightNameList에 담아줌
			}//end while
		}catch (SQLException e) {
			System.out.println("\n에러 발생! >> showSightNameList");						// showSightNameList 메소드에서 SQLException 발생함을 알림
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());	// sql문 불러올 때 오류 발생시 오류코드와 메세지 출력
		} catch (Exception e) {															// 남은 오류 처리
			e.printStackTrace();
		}//end try
		return sightNameList;	// 관광지명 리스트 반환
	}//end showSightInfoList
	

	// 관광지 세부 정보 출력 메서드
	public SightInfoVO showSightInfo(String sightName) {
		return selectOneRow(sightName);
	}//end showSightInfo
}//end class	
