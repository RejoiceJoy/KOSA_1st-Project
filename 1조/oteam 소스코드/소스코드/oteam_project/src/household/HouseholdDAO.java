package household;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import login.loginuserAct;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;



/*코드 작성자 : 김준영*/
//JDBX > 자바로 DB에 접속, 컨트롤해주는 클래스
public class HouseholdDAO {   
	
	//select----------------------------------------------------------------------
	//조회 버튼 클릭시 호출메소드, 사용자 범위지정 날짜를 매개변수로 받음, DB데이터 값을 받을 javaFX의 콜렉션타입
	public static ObservableList<HouseholdDataProperty> searchHousehold(LocalDate start_date,LocalDate end_date) 
			throws SQLException, ClassNotFoundException {
		
		//회원가입시 부여되는 사용자의 코드를 식별함
		int lMember_code = loginuserAct.whoIsMember();
		//조회에 대한 SQL문 작성
		String stmtSHH = "SELECT HOUSEHOLD_TYPE, HOUSEHOLD_CATEGORY, HOUSEHOLD_DATE,"
						+ "HOUSEHOLD_MONEY, HOUSEHOLD_MEMO FROM household "
						+ "WHERE HOUSEHOLD_DATE between '" + start_date + "' and '" + end_date
						+ "' and member_code = " + lMember_code
						+ "ORDER by HOUSEHOLD_DATE DESC"; 
		try {
			//SQL문 실행 < DB접속과 SQL문 실행에 대해 DBUtil클래스로 따로 만들어줌
			ResultSet rsSHH = DBUtil.dbExecuteQuery(stmtSHH); 
			//DB데이터 값을 받을 javaFX의 콜렉션타입의 변수 선언
			ObservableList<HouseholdDataProperty> empData = FXCollections.observableArrayList();
			//ResultSet에 반환된 DB의 데이터를 모두 출력
			while(rsSHH.next()) {
			//tableView의 열 생성
			HouseholdDataProperty sHH = new HouseholdDataProperty();
			//출력된 tableView의 한 행을 HouseholdDataProperty클래스의 필드에 담음
			sHH.setHousehold_Type(rsSHH.getString("HOUSEHOLD_TYPE"));
			sHH.setHousehold_Category(rsSHH.getString("HOUSEHOLD_CATEGORY"));
			sHH.setHousehold_Date(rsSHH.getDate("HOUSEHOLD_DATE"));
			sHH.setHousehold_Money(rsSHH.getInt("HOUSEHOLD_MONEY"));
			sHH.setHousehold_Memo(rsSHH.getString("HOUSEHOLD_MEMO"));
			//콜렉션 변수에 담음
			empData.add(sHH);
			}
			//tableView에 출력하기 위한 리턴
			return empData;
		} catch (SQLException e) {
			throw e;
		}
	}//searchHousehold


	//insert, delete---------------------------------------------------------------
	//입력창에서 입력 클릭시 호출됨, 각 입력창의 데이터를 매개변수로 받음
	public static void insertHousehold(String combo1_menu, String combo2_menu, LocalDate now_date, 
			String money_text, String etc_text) throws SQLException, ClassNotFoundException{
		
		//회원가입시 부여되는 사용자의 코드를 식별함
		int lMember_code = loginuserAct.whoIsMember();
		//입력에 대한 SQL문 작성
		String StmtIHH = "insert into oteam.household (HOUSEHOLD_CODE, HOUSEHOLD_TYPE, HOUSEHOLD_CATEGORY,"
				+ "HOUSEHOLD_DATE, HOUSEHOLD_MONEY, HOUSEHOLD_MEMO, MEMBER_CODE)"
				+ " values(household_seq.nextval,'"+ combo1_menu + "','" + combo2_menu + "','" + now_date 
				+ "'," + money_text + ",'" + etc_text + "'," + lMember_code + ")";
		try {
			//SQL문 실행 < DB접속과 SQL문 실행에 대해 DBUtil클래스로 따로 만들어줌
			DBUtil.dbExecuteUpdate(StmtIHH);
		//DB의 열의 타입, 제약조건에 맞지 않으면 ERROR창을 띄움
		} catch (SQLException e) {
			System.out.print("입력 실패" + e);
			Alert fail = new Alert(AlertType.ERROR);
			fail.setHeaderText("올바르지 않은 형식입니다.");
			fail.setContentText("항목에 맞게 모두 입력해 주십시오.");
			fail.showAndWait();
			throw e;
		}
	}//insertHousehold
	
	
	//tableView의 행 삭제시 호출됨, 클릭이벤트에 대한 tableView의 데이터를 매개변수로 받음
	public static void deleteHousehold(HouseholdDataProperty click_tv) throws SQLException, ClassNotFoundException {
		
		//회원가입시 부여되는 사용자의 코드를 식별함
		int lMember_code = loginuserAct.whoIsMember();
		//삭제에 대한 SQL문 작성
		String StmtDHH = "delete from household "
				+ "where household_type = '"+click_tv.getHousehold_Type()+"' and household_category = '"+click_tv.getHousehold_Category()+"'"
				+ "and household_date = '"+click_tv.getHousehold_Date()+"' and household_money = "+click_tv.getHousehold_Money()+ " "
				+ "and member_code = " + lMember_code;
		try {
			//SQL문 실행 < DB접속과 SQL문 실행에 대해 DBUtil클래스로 따로 만들어줌
			DBUtil.dbExecuteUpdate(StmtDHH);
		} catch (NullPointerException e) {
			throw e;
		}
	}//deleteHousehold
	
	

	
}







