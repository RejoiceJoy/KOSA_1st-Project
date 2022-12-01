package member;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.DAOInterface;

// DB의 회원 정보(MEMBERS 테이블)에 접근하기 위한 DAO 클래스(DAOInterface 구현)
public class MemberDAO implements DAOInterface<MemberVO, String> {

	// 회원 정보를 데이터베이스에 저장 - 작성자 : 최영광
	@Override
	public void insertData(MemberVO memberVO) {
		try {
			String sql = "insert into tour.members (MEMBER_CODE, MEMBER_ID, MEMBER_PW, "
					+ "FULL_NAME, PHONE_NUMBER) values(MEMBER_CODE_SEQ.nextval, ?, ?, ?, ?) ";
			// 회원가입 정보 기입 insert
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, memberVO.getMember_id());
			preparedStatement.setString(2, memberVO.getMember_pw());
			preparedStatement.setString(3, memberVO.getFullName());
			preparedStatement.setString(4, memberVO.getPhoneNumber());
			preparedStatement.executeUpdate();
			System.out.println("입력 성공");
		} catch (SQLException e) {		
			System.out.println("MEMBERS 테이블 insert 실패");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}//end try
	}//end method

	// update 수행 메소드 구현
	@Override
	public void updateData(MemberVO memberVO) {
	}

	// 회원ID를 입력받아 해당 회원 정보를 가져오는 메소드 구현
	// 사용자의 로그인 정보 저장 - 작성자 : 최영광, 이수봉
	@Override
	public MemberVO selectOneRow(String member_id) {
		// 지역변수 초기화
		int code = 0;			// 회원 코드
		String id = null;		// ID
		String pw = null;		// 비밀번호
		String name = null;		// 이름
		String phone = null;	// 휴대폰 번호
		try {
			// TOUR.MEMBERS_INFO 뷰에서 입력된 인수와 동일한 회원 ID를 갖는 행의 모든 열을 select하는 SQL 구문
			String sql = "select * from tour.members_info where (member_id = ?) ";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);	// 실행할 SQL 구문 설정
			preparedStatement.setString(1, member_id);							// PreparedStatement의 첫번째 인수로 입력받은 ID 지정
			ResultSet resultSet = preparedStatement.executeQuery();				// select 문을 실행하여 ResultSet으로 저장
			resultSet.next();	// ResultSet에서 한 행을 가져옴
			
			code = resultSet.getInt(1);		// 1번째 열의 숫자형 데이터를 회원 코드로 저장
			id = resultSet.getString(2);	// 2번째 열의 문자형 데이터를 ID로 저장
			pw = resultSet.getString(3);	// 3번째 열의 문자형 데이터를 비밀번호로 저장
			name = resultSet.getString(4);	// 4번째 열의 문자형 데이터를 이름으로 저장
			phone = resultSet.getString(5);	// 5번째 열의 문자형 데이터를 휴대폰 번호로 저장
			
		} catch (SQLException e) {
			System.out.println("MEMBERS 테이블 select 실패");	// 예외 발생 위치 알림
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new MemberVO(code, id, pw, name, phone);		// MemberVO 객체를 생성자로 초기화 및 생성하여 반환
	}

	// 사용자가 입력한 ID 및 PW를 데이터베이스 내의 기존 계정과 대조
	// 작성자 : 최영광, 이혜성
	public boolean compareAccount(String member_id, String member_pw) {
		boolean isExist = false;	// DB에 사용자가 입력한 계정정보가 존재하는지 여부
		
		// 계정정보 count, DB에 계정정보가 존재 시
		try {
			String sql = "select count(*) from tour.members_info where (member_id = ? and member_pw = ?) ";
			PreparedStatement preparedStatement = conn.prepareStatement(sql); 	// SQL 문의 정보를 저장하는 PreparedStatement 객체
			preparedStatement.setString(1, member_id);							// 1번째 물음표에 사용자의 ID 입력값 저장
			preparedStatement.setString(2, member_pw);							// 2번째 물음표에 사용자의 PW 입력값 저장
			ResultSet resultSet = preparedStatement.executeQuery();				// ResultSet에 사용자 입력값에 대한 DB의 결과값을 저장(반환된 결과 데이터)
			resultSet.next(); 													// 다음 입력값에 대한 결과값으로 커서 전환
			
			int result = resultSet.getInt(1);	// result = 상단의 SQL 문의 1열 값(count)
			if (result == 1) { 					// 계정정보 존재(아이디와 비밀번호가 모두 일치할 때 : 계정정보는 id가 유니크 값이기 때문에 1이다.)
				isExist = true;					// true로 변경(해당 계정정보 존재)
				System.out.println("t");
			} else {
				isExist = false;				
				// 계정정보 존재x(계정 정보가 없다고 판단될 때 : 사용자 입력값이 없는 경우, 계정정보 대조 시 불일치, 계정정보가 없을 경우
				System.out.println("f");
			}//end if
		} catch (SQLException e) {
			System.out.println("MEMBERS 테이블 DB 계정정보 대조 실패");
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isExist;		// 사용자 계정정보 존재여부 반환
	}//end compareAccount

	// 사용자가 입력한 ID를 데이터베이스의 기존 ID와 중복검사
	// 작성자 : 최영광, 이수봉
	public boolean idOverlapChk(String member_id) {
		boolean isOverlap = false;	// 사용자가 입력한 ID가 DB 정보와 중복되는지 여부(중복될 경우 true)
		try {
			// TOUR.MEMBERS_INFO 뷰에서 입력된 인수와 동일한 회원 ID를 갖는 행의 개수(count)를 select하는 SQL 구문
			String sql = "select count(*) from tour.members_info where (member_id = ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);	// 실행할 SQL 구문 설정
			preparedStatement.setString(1, member_id);							// PreparedStatement의 첫번째 인수로 입력받은 ID 지정
			ResultSet resultSet = preparedStatement.executeQuery();				// select 문을 실행하여 ResultSet으로 저장
			resultSet.next();	// ResultSet에서 한 행을 가져옴
			
			// ID 중복값이 없을 경우(count 결과 = 0)
			if (resultSet.getInt(1) == 0) {					
				isOverlap = false;
				System.out.println("ID 중복 f");
			} else {
				isOverlap = true; 
				// 계정정보(ID)가 존재할 경우(중복 체크 걸림)
				System.out.println("ID 중복 t");
			}//end if
		} catch (SQLException e) {
			System.out.println("MEMBERS 테이블 ID 중복 체크 실패");
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isOverlap;	// ID 중복여부 반환
	}//end idOverlapChk
}

