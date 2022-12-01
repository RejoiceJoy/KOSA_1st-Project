package asset;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import application.DBConnection;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import login.loginuserAct;

/*코드 작성자 : 박주영*/

public class AssetController implements Initializable {

	@FXML
	private TextField totalincome;
	@FXML
	private TextField totalexpense;
	@FXML
	private TextField remainasset;
	@FXML
	private TextField startdate,todaydate;
	@FXML
	private Button cancelBtn;
	
	
	// 메서드를 호출하여 계정 관리 페이지 이동시 값이 들어간 상태의 화면을 출력
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// 현재 사용자의 멤버코드를 loginuserAct 클래스 파일에서 받아와 변수에 대입
		int Connected_Member_code = loginuserAct.whoIsMember();
		
		// java에서 DB로 넘겨줄 sql문 작성 - 총 수입, 총 지출, 총 자산 구하기
		// 가계부에 작성한 수입의 총 합과, 지출의 총 합을 구하는 쿼리문 작성
		String sql = "select to_char(sum(household_money),'FM999,999,999,999') from household where household_type='수입' and member_code = " + Connected_Member_code
				+ "union all "
				+ "select to_char(sum(household_money),'FM999,999,999,999') from household where household_type = '지출' and member_code = " + Connected_Member_code; 
		
		// 총 수입과 총 지출의 차이를 총 자산으로 사용할 것 이므로 이를 구하는 쿼리문 작성 
		String sql2 = "select to_char("
				+ "(select sum(household_money) from household where household_type = '수입' and member_code = " + Connected_Member_code + ")-(select sum(household_money) from household where household_type = '지출' and member_code = " + Connected_Member_code+ ")"
				+ ",'FM999,999,999,999') from dual";
		
		// 가계부에 작성된 가장 과거의 경제활동 날짜를 구하는 쿼리문 작성
		String sql3 = "select to_char(household_date,'YYYY-MM-DD') from household "
				+ "order by 1 asc";
		
		// 사용자가 프로그램을 사용하는 시점의 날짜를 구하는 쿼리문 작성
		String sql4 = "select to_char(sysdate,'YYYY-MM-DD') from dual";
		
		// try ~ catch를 이용한 쿼리문 실행
		try {
			
			// DB연결
			Connection conn = DBConnection.getConnection();
			
			// 오라클 db쪽에 statement를 가져올 수 있도록 함
			Statement stmt = conn.createStatement(); 
			
			// sql문을 db에서 실행해서 resultset으로 그 결과를 받아옴
			ResultSet rs = stmt.executeQuery(sql);
			
			// 첫 번쨰 next는 resultset의 첫 번쨰 행을 가리키도록 설정해주는 것
			// rs에 받아온 결과의 첫 행은 수입의 총 합 
		    rs.next(); 
		    // 총 수입 출력칸에 값 넘겨주기
		    totalincome.setText(rs.getString(1)+" 원");
		    // rs에 받아온 결과의 두 번째 행이 지출의 총 합이므로 next를 사용해 가리키는 행을 다음으로 이동
		    rs.next(); 
		    // 총 지출 출력칸에 값 넘겨주기
		    totalexpense.setText(rs.getString(1)+" 원");
		    
		    
		    // 총 자산 가져오기
		    Statement stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery(sql2);
			rs2.next();
			remainasset.setText(rs2.getString(1)+" 원");
			
			// 시작 날짜 가져오기
			Statement stmt3 = conn.createStatement();
			ResultSet rs3 = stmt3.executeQuery(sql3);
			rs3.next();
			startdate.setText(rs3.getString(1));	
			
			// 오늘 날짜 가져오기
			Statement stmt4 = conn.createStatement();
			ResultSet rs4 = stmt4.executeQuery(sql4);
			rs4.next();
			todaydate.setText(rs4.getString(1));
			 
			 
			} catch (Exception e) {
				System.out.println("DB 연결에 문제가 있습니다.");
				e.printStackTrace();
			} // end try
		}// end initialize
		
		 
	// 뒤로가기 버튼에 기능 부여
	public void cancelAction () {
		Stage stage1 = (Stage) cancelBtn.getScene().getWindow();
		Platform.runLater(() -> {
			stage1.close();
		});
	}
		
}// end AssetController
