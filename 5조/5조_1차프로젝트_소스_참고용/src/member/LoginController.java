package member;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.StageManager;
import application.UserInfo;
import itinerary.ItineraryDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// 로그인 창 제어하는 컨트롤러 클래스 정의
// 작성자 : 이혜성, 최영광
public class LoginController implements Initializable {
	
	@FXML private TextField idField;		// ID 정보를 입력받는 TextField
	@FXML private PasswordField pwField;	// PW 정보를 입력받는 PasswordField
	@FXML private Button loginBtn;			// login Action을 받는 Button
	@FXML private Button membershipBtn;		// 회원가입 Action을 받는 Button
	
	private Stage thisStage = StageManager.primaryStage;		// primaryStage 참조	- 추가 : 이수봉
	
	MemberDAO memberDAO = new MemberDAO(); 				// 사용자 정보에 접근하는 DAO 객체 생성
	ItineraryDAO itineraryDAO = new ItineraryDAO();		// 사용자 일정 정보에 접근하는 DAO 객체 생성
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 각 버튼에 Action Event 수용 등록
		loginBtn.setOnAction(event->loginBtnAction(event));
		membershipBtn.setOnAction(event->membershipBtnAction(event));
		// 람다식을 사용할 때는, fxml에 onAction 값이 있다면 에러가 발생합니다(onAction을 뺍니다).
	}

	// 로그인 이벤트 처리(로그인 버튼)	 - 작성자 : 이혜성, 이수봉
	public void loginBtnAction(ActionEvent event) { 
		//사용자 입력값 받기(ID/PW)
		String id = idField.getText();
		String pw = pwField.getText();
		
		// 사용자 입력값이 없을 때(빈 칸일 때)
		if((id.trim().equals("")) || (pw.trim().equals(""))) {
			try { 
				Parent root = FXMLLoader.load(getClass().getResource("layout/LoginErr1.fxml")); 		// 오류창 fxml 로드
				StageManager.showAlertDiolog(root);		// 오류 알림창 표시
			}catch(IOException e) {
				System.out.println("fxml 파일 로드 실패 >> loginBtnAction >> LoginErr1.fxml 로드 실패");		// 예외 발생 위치 알림
				e.printStackTrace();
			}
		}else {
			// 사용자 입력값이 있으면 DB 정보와 대조하여 회원 여부 확인
			boolean isLogin = memberDAO.compareAccount(id, pw); // 로그인 계정정보 확인
			
			// 사용자 입력과 DB의 계정정보가 일치할 경우(isLogin = true)
			if(isLogin) {
				System.out.println("로그인 성공");
				
				// 최초 로그인 할 때 DB에서 사용자 정보 가져오기(회원정보, 해당 회원이 선택한 일정)
				UserInfo.memberVO = memberDAO.selectOneRow(id); 		// 해당 회원의 계정 정보 저장
				UserInfo.itineraryVO = itineraryDAO.selectOneRow(id);	// 해당 회원의 가장 최근 일정 정보 저장
				
				// 창 전환(로그인->마이페이지)
				try {
					Parent root = FXMLLoader.load(getClass().getResource("/mypage/layout/MyPage.fxml"));	// 마이페이지 fxml 로드
					StageManager.changeScene(thisStage, root, "마이페이지");	// scene 전환
				} catch (IOException e) {
					System.out.println("fxml 파일 로드 실패 >> 마이페이지로 scene 전환 시");		// 예외 발생 위치 알림
					e.printStackTrace();
				}
			} else {
				// 사용자 입력값이 DB의 회원 정보와 불일치 할 경우 : ID/PW 불일치 알림 창 표시
				try { 
					Parent root = FXMLLoader.load(getClass().getResource("layout/LoginErr2.fxml"));		// 오류창 fxml 로드
					StageManager.showAlertDiolog(root);		// 오류 알림창 표시
				}catch (Exception e) {
					System.out.println("fxml 파일 로드 실패 >> loginBtnAction >> LoginErr2.fxml 로드 실패");		// 예외 발생 위치 알림
					e.printStackTrace();
				}
			}//else2 end
		}//else1 end	 
	}
	
	// '회원가입' 버튼 이벤트 처리 - 작성자 : 최영광
	public void membershipBtnAction(ActionEvent event) { 
		// 창 전환(로그인->회원가입)
		try {
			Parent root = FXMLLoader.load(getClass().getResource("layout/Membership.fxml"));	// 회원가입 fxml 로드
			StageManager.changeScene(thisStage, root, "회원가입");	// scene 전환
		} catch (IOException e) {
			System.out.println("fxml 파일 로드 실패 >> membershipBtnAction >> Membership.fxml 로드 실패");		// 예외 발생 위치 알림 
			e.printStackTrace();
		} 
	}
}
