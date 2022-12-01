package mypage;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.UserDTO;


public class Update_Controller implements Initializable {


	// Sign_up
	@FXML
	private TextField asid, asname, asphone, asemail, asaddress, asfav_seat;
	@FXML
	private PasswordField aspw;
	@FXML
	private Button goUserSignUp, asignupcompleteBtn, asignUpClose, idchkBtn, pwchkBtn, close;
	@FXML
	private ComboBox<String> aaddresscombo, afavseatcombo;
	
	public String astrAddress = null;
	public String astrFavseat = null;
	public String getID = null;
	public String getPW = null;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
    // 탑승예상지역 ComboBox String형으로 변환
	public void aaddressComboChange(ActionEvent event) {
		astrAddress = aaddresscombo.getValue();
		System.out.println("변환성공");
	}
	// 선호좌석 ComboBox String형으로 변환
	public void afavseatComboChange(ActionEvent event) {
		astrFavseat = afavseatcombo.getValue();
		System.out.println("변환성공2");
	}
   
	// pw 조건확인(11/22 문수지 작성)
		public int validPW(String inputPW) {
			getPW = aspw.getText();	// 입력한 PW값 할당
			int inspect;			// 변수 선언
			
			String regExp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W).{8,20}$"; // 정규표현식으로 암호패턴 만드는 방법
			/*
			(?=.*[0-9]) >> 0~9까지 숫자 최소 한개
			(?=.*[a-zA-Z]) >> 영소대문자 a~z / A~Z 문자 최소 한개
			(?=.*\\W) >> 특수문자 최소 한개
			.{8,20} >> 선행 3가지 표현식(숫자, 영소문자, 특수문자)에 맞는 8~20자리 문자열
			*/
			
			if (getPW.isEmpty() || getPW.contains(" ")) {	// PW창에 공백이 있을 경우
				inspect=0;	// 0반환
			} else if (getPW.matches(regExp)) { // 정규표현식 적용 결과 true >> 조건 충족
				inspect=2;	// 2반환
			} else { // 공백은 없으면서 정규표현식 조건을 충족하지 않을 경우
				inspect=1;	// 1반환
			}// end else
			return inspect;
		}// end method
		
		//btn 클릭 시 pw 조건확인(11/22 문수지 작성)
		@FXML
		public void pwChk(ActionEvent event) throws Exception {
			UserDTO uDTO = new UserDTO();	//객체 생성
			
			try {
				uDTO.setUser_pw(aspw.getText());
				getPW = uDTO.getUser_pw();	// PW값 할당
				
				if (validPW(getPW)==0) {	// PW창에 공백이 있을 경우

					Alert alert = new Alert(AlertType.WARNING);	// WARNING타입 알림창 생성
				    alert.setTitle("PW 조건충족 확인");				// 알림창 title 지정
				    alert.setHeaderText(null);					// 알림창 headertext 지정
				    alert.setContentText("공란이 포함되어 있습니다.");	// 알림창에 뜰 문자 지정
				    alert.show();			// 알림창 출력
				    
				    aspw.clear();			// PW 입력창 비움

				} else if (validPW(getPW)==1){	//  공백은 없으면서 정규표현식 조건을 충족하지 않을 경우
					
					Alert alert = new Alert(AlertType.WARNING);		// WARNING타입 알림창 생성
				    alert.setTitle("PW 조건충족 확인");					// 알림창 title 지정
				    alert.setHeaderText(null);						// 알림창 headertext 지정
				    alert.setContentText("영문/숫자/특수문자를 혼용하여 8자리 이상인지 확인해주십시오.");// 알림창에 뜰 문자 지정
				    alert.show();			// 알림창 출력
				    
				    aspw.clear();			//PW 입력창 비움
					    
				} else {	// 조건 충족할 경우
						
					Alert alert = new Alert(AlertType.INFORMATION);	// INFORMATION타입 알림창 생성
				    alert.setTitle("PW 조건충족 확인");					// 알림창 title 지정
				    alert.setHeaderText(null);						// 알림창 headertext 지정
				    alert.setContentText("사용가능한 비밀번호입니다.");		// 알림창에 뜰 문자 지정
				    alert.show();			// 알림창 출력
				}// end else
			} catch (IllegalArgumentException e) {
				System.out.println("IllegalArgumentException caught"); // 예외처리 발생!!
			}// end catch
		}// end method
	
	//회원정보 페이지의 수정 버튼 수행
	public void updatecom(ActionEvent event) throws Exception {
		UpdateDTO uDTO = new UpdateDTO();	//객체 생성
		 
		
		if(aspw.getText().isEmpty()||asname.getText().isEmpty()||
				asphone.getText().isEmpty()||asemail.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("비어있는 정보가 있습니다.");
			alert.setHeaderText(null);
			alert.setContentText("정보를 모두 입력해주세요.");
			alert.show();
		} else if (validPW(getPW)==0||validPW(getPW)==1) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("비밀번호 조건충족 확인");
			alert.setHeaderText(null);
			alert.setContentText("비밀번호 조건충족 확인해주십시오.");
			alert.show();
		} else {
			//회원정보 업데이트 버튼 수행 11/22 손영석 작성
			try {
				
				uDTO.setUser_pw(aspw.getText());		//새로운 비밀번호 입력
				uDTO.setUser_name(asname.getText());	//새로운 이름 입력
				uDTO.setUser_phone(asphone.getText());	//새로운 번호 입력
				uDTO.setUser_email(asemail.getText());	//새로운 이메일 입력
				
				UpdateDAO.Signupupdate(uDTO);			//DB전달
			      Alert alert = new Alert(AlertType.INFORMATION);
			      alert.setTitle("가입 알림창");
			      alert.setHeaderText(null);
			      alert.setContentText("회원정보 수정 완료되었습니다.");
			      alert.show();
			} catch (IllegalArgumentException e) {
				System.out.println("IllegalArgumentException caught"); // 예외처리 발생!!
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}
	//회원정보수정창에서 닫기 버튼
	public void close(ActionEvent event) throws Exception {
      Stage primaryStage = new Stage();
      Stage stage = (Stage) close.getScene().getWindow();
      
      Parent login = FXMLLoader.load(getClass().getResource("/mypage/mypage.fxml"));  
      Scene sc = new Scene(login);
      //(11/22 문수지) css 등록
      sc.getStylesheets().add(getClass().getResource("/main/app.css").toString());
      //(11/21 문수지) 창 아이콘 이미지 삽입
      primaryStage.getIcons().add(new Image("file:lib/bus_image.png"));
      primaryStage.setTitle("버스 예약 프로그램 / 마이페이지");
      primaryStage.setScene(sc);
      primaryStage.show();
      stage.close();
	}

}