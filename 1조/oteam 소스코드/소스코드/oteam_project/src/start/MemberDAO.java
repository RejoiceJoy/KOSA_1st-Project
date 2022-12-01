package start;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import application.DBConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/*코드 작성자 : 김대영*/

// 사용자 정보를 DB에 접근하여 활용하기 위해 MemberDAO 클래스 생성
public class MemberDAO {
		
	// 회원가입을 진행하는 메서드
	// 회원가입 페이지에 입력된 사용자 데이터를 데이터베이스의 members 테이블에 저장
	public static void JoinInsert(MemberVO mVO) {

		// 시퀀스로 값을 DB가 생성하기 때문에 사용자로부터 입력받지 않아도 되는 member_code는 
		// 시퀀스 실행문을 쿼리에 넣고 사용자로부터 입력받을 값을 나머지 값들을 ? 에 담는다
		String run = "INSERT INTO MEMBERS"
				+ "(member_code, member_id, member_pw, member_name, member_phone, member_birthday, member_sex, region_code) "
				+ "VALUES(member_seq.nextval,?,?,?,?,?,?,?)";

		try {
			// 데이터베이스 연결
			Connection conn = DBConnection.getConnection();
			// PreparedStatement에 작성한 쿼리문 대입
			PreparedStatement prst = conn.prepareCall(run);
			// 1번째 ? 에 해당하는 member_id를 입력받아 전달
			prst.setString(1, mVO.getMember_id());
			// 2번째 ? 에 해당하는 member_pw를 입력받아 전달
			prst.setString(2, mVO.getMember_pw());
			// 3번째 ? 에 해당하는 member_name을 입력받아 전달
			prst.setString(3, mVO.getMember_name());
			// 4번째 ? 에 해당하는 member_phone을 입력받아 전달
			prst.setInt(4, mVO.getMember_phone());
			// 5번째 ? 에 해당하는 member_age를 입력받아 전달
			prst.setString(5, mVO.getMember_birthday());
			// 6번째 ? 에 해당하는 member_sex를 입력받아 전달
			prst.setString(6, mVO.getMember_sex());
			// 7번째 ? 에 해당하는 region_code를 입력받아 전달
			prst.setString(7, mVO.getRegion_code());

			// 실행
			prst.executeUpdate();
			
			// 위 과정에서 에러가 발생하지 않았다면 성공 메세지 출력
			System.out.println("성공");
			
			// 회원가입이 문제없이 완료된 것이므로 안내창 출력
			Alert joinSuccess = new Alert(AlertType.CONFIRMATION);
			joinSuccess.setHeaderText("환영합니다!");
			joinSuccess.setContentText("가입되셨습니다.");
			joinSuccess.showAndWait();
		} catch (SQLException e) {
			Alert Fail = new Alert(AlertType.ERROR);
			Fail.setHeaderText("잘못된 입력입니다.");
			Fail.setContentText("존재하는 회원이거나, 형식에 맞지 않은 입력입니다.");
			Fail.showAndWait();
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			e.printStackTrace();
			return;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		} // end try
	}// end JoinInsert

	
	// 계정정보 조회를 위한 메서드
	// 앱의 메인 실행 화면에서 하단의 계정관리 버튼을 누를 때 호출된다
	public static MemberVO MyPage(int mem_code) {
		
		// 사용자 개인 정보를 불러올 객체 생성
		MemberVO mVO = new MemberVO();
		// members테이블에서 현재 사용자의 모든 정보를 불러오기 위한 쿼리 작성
		String run = "SELECT * FROM MEMBERS WHERE member_code = ?";
		
		try {
			
			// 데이터베이스 연결
			Connection conn = DBConnection.getConnection();
			// PreparedStatement에 쿼리문 전달
			PreparedStatement prst = conn.prepareCall(run);
			// 입력받은 멤버 코드를 PreparedStatement에 전달
			prst.setInt(1, mem_code);
			// 실행
			ResultSet rs = prst.executeQuery();	
			
			// member테이블의 불러올 열이 더 이상 없을 때 까지 반복
			while (rs.next()) {
					mVO.setMember_code(rs.getInt(1));
					mVO.setMember_id(rs.getString(2));
					mVO.setMember_pw(rs.getString(3));
					mVO.setMember_name(rs.getString(4));
					mVO.setMember_phone(rs.getInt(5));
					mVO.setMember_birthday(rs.getString(6));
					mVO.setMember_sex(rs.getString(7));
					mVO.setRegion_code(rs.getString(8));
				}
			System.out.println("계정정보 조회 성공");

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}// end try
		return mVO;
	}// end MyPage
}// end MemberDAO
