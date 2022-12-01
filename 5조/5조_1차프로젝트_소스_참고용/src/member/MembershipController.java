package member;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// 회원가입 창 제어하는 컨트롤러 클래스 정의
// 작성자 : 최영광
public class MembershipController implements Initializable {
	
	@FXML private TextField idField, nameField, phoneField;		// ID 입력칸, 이름 입력칸, 전화번호 입력칸
	@FXML private PasswordField pwField1, pwField2;				// PW 입력칸, PW 확인용 입력칸
	@FXML private Button applyBtn;		// 가입하기 버튼
	@FXML private Button backBtn;		// 돌아가기 버튼
	
	private Stage thisStage = StageManager.primaryStage;		// primaryStage 참조	- 추가 : 이수봉
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// 람다식을 사용할 때는, fxml에 onAction 값이 있다면 에러가 발생합니다(onAction을 뺍니다).
		applyBtn.setOnAction(event->applyBtnAction(event));
		backBtn.setOnAction(event->backBtnAction(event));
	}
	
	// 가입하기 버튼 이벤트처리 - 작성자 : 최영광, 이수봉
	public void applyBtnAction(ActionEvent event) { 
		MemberVO memberVO = new MemberVO();
		MemberDAO memberDAO = new MemberDAO();
		try {
			String id = idField.getText();			// ID 입력값 저장
			String pw = pwField1.getText();			// PW 입력값 저장
			String pw_ck = pwField2.getText(); 		// PW 확인 입력값 저장
			String name = nameField.getText(); 		// 이름 입력값 저장
			String phoneNum = phoneField.getText();	// 전화번호 입력값 저장
			
			// 1. 아무 값이나 빈 칸이 있을 때
			if (id.equals("") || pw.equals("") || pw_ck.equals("") || name.equals("") || phoneNum.equals("")) { 
				// String은 기본 자료형이 아니기 때문에 == 비교연산자로 하는 것이 아니라 equals() 메서드를 사용한다.
				try { 
					//Stage dialog1 = StageManager.createAlertDiolog();	// 알림용 다이얼로그 생성
					Parent root1 = FXMLLoader.load(getClass().getResource("layout/MembershipErr1.fxml"));	// fxml 파일 로드
					StageManager.showAlertDiolog(root1);	// 알림창 표시
				}catch(IOException e) {
					System.out.println("fxml 파일 로드 실패 >> applyBtnAction >> MembershipErr1.fxml 로드 실패");		// 예외 발생 위치 알림
					e.printStackTrace();
				}				
			} else if(memberDAO.idOverlapChk(id)) { 	// 2. 아이디 중복 시
				try { 
					Parent root2 = FXMLLoader.load(getClass().getResource("layout/MembershipErr2.fxml")); 	// fxml 파일 로드
					StageManager.showAlertDiolog(root2);	// 알림창 표시
				}catch(IOException e) {
					System.out.println("fxml 파일 로드 실패 >> applyBtnAction >> MembershipErr2.fxml 로드 실패");		// 예외 발생 위치 알림
					e.printStackTrace();
				}
			} else if(!pw.equals(pw_ck)) { 		// 3. 비밀번호가 비밀번호 확인과 같지 않을 때
				// String형은 비교 시 equals를 사용하고, !(Not 연산자)를 앞에 붙여 !=의 의미로 사용합니다.
				try { 
					Parent root3 = FXMLLoader.load(getClass().getResource("layout/MembershipErr3.fxml")); // fxml 파일 로드
					StageManager.showAlertDiolog(root3);	// 알림창 표시
				}catch(IOException e) {
					System.out.println("fxml 파일 로드 실패 >> applyBtnAction >> MembershipErr3.fxml 로드 실패");		// 예외 발생 위치 알림
					e.printStackTrace();
				}
			} else { 
				// 4. 회원가입 조건이 전부 충족되었을 때
				// DB에 저장하기 위해 VO에 set을 한다.
				memberVO.setMember_id(id);
				memberVO.setMember_pw(pw);
				memberVO.setFullName(name);
				memberVO.setPhoneNumber(phoneNum);
				
				memberDAO.insertData(memberVO);		// DB에 데이터를 insert하는 메소드 호출
				
				try { 
					Parent root4 = FXMLLoader.load(getClass().getResource("layout/MembershipPass.fxml")); // fxml 파일 로드
					StageManager.showAlertDiolog(root4);	// 알림창 표시
				}catch(IOException e) {
					System.out.println("fxml 파일 로드 실패 >> applyBtnAction >> MembershipPass.fxml 로드 실패");		// 예외 발생 위치 알림
					e.printStackTrace();
				}
					Parent root = FXMLLoader.load(getClass().getResource("layout/Login.fxml"));	// fxml 파일 로드
					StageManager.changeScene(thisStage, root, "로그인");	// scene 전환
			} //else 종료
		} catch (IOException e) {
			System.out.println("fxml 파일 로드 실패 >> applyBtnAction");		// 예외 발생 위치 알림
			e.printStackTrace();
		} catch (IllegalArgumentException e) {  //입력값이 변수의 데이터 범위를 넘거나, 포맷이 일치하지 않을 때 예외처리
			System.out.println("IllegalArgumentException caught"); // 예외처리 발생!!
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 돌아가기 버튼 이벤트 처리
	public void backBtnAction(ActionEvent event) { 	
		try {
			Parent root = FXMLLoader.load(getClass().getResource("layout/Login.fxml"));
			StageManager.changeScene(thisStage, root, "로그인");	// scene 전환
		} catch (IOException e) {
			System.out.println("fxml 파일 로드 실패 >> backBtnAction >> Login.fxml 로드 실패");		// 예외 발생 위치 알림
			e.printStackTrace();
		} 
	}
}
	
