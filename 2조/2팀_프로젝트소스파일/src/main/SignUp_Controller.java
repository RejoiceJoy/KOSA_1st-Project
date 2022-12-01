package main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

//(11/17 문수지, 윤준호 공동 작업)
public class SignUp_Controller implements Initializable {


	// Sign_up
	@FXML
	private TextField sid, sname, sphone, semail, saddress, sfav_seat;
	@FXML
	private PasswordField spw;
	@FXML
	private Button goUserSignUp, signupcompleteBtn, signUpClose, idchkBtn, pwchkBtn;
	@FXML
	private ComboBox<String> addresscombo, favseatcombo;
	
	public String strAddress = null;
	public String strFavseat = null;
	public String getID = null;
	public String getPW = null;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	// 탑승예상지역 ComboBox String형으로 변환(11/19 문수지 작성)
	public void addressComboChange(ActionEvent event) {
		strAddress = addresscombo.getValue();
	}
	
	// 선호좌석 ComboBox String형으로 변환(11/19 문수지 작성)
	public void favseatComboChange(ActionEvent event) {
		strFavseat = favseatcombo.getValue();
	}
	
	// id 중복 체크 (11/19 문수지 ID 중복 체크 이벤트 작성)
	// (11/22 문수지 추가) 아이디 입력 없거나 공란이 포함되었을 경우 알림창 추가
	public void idChk(ActionEvent event) throws Exception {
		UserDTO uDTO = new UserDTO();	//객체 생성
		
		try {
			uDTO.setUser_id(sid.getText());	// 입력한 ID값 setting
			getID = uDTO.getUser_id();		// 입력한 ID값 할당
			UserDAO obj = new UserDAO();	// 객체 생성
			
			if (getID.isEmpty() || getID.contains(" ")) {	// (11/22 문수지) ID 입력 없거나 공란이 포함되었을 경우 if문 추가
								
				Alert alert = new Alert(AlertType.WARNING);	// WARNING타입 알림창 생성
			    alert.setTitle("ID를 입력해주세요.");			// 알림창 title 지정
			    alert.setHeaderText(null);					// 알림창 headertext 지정
			    alert.setContentText("ID를 입력해 주십시오.");	// 알림창에 뜰 문자 지정
			    alert.show();								// 알림창 출력
			    
			    sid.clear();				// ID 입력창 비움
				
			}else if (obj.IdChk(getID)) {	// 입력한 ID값이 존재할 경우 (boolean)
				
				Alert alert = new Alert(AlertType.WARNING);	// WARNING타입 알림창 생성
			    alert.setTitle("ID 중복 확인");				// 알림창 title 지정
			    alert.setHeaderText(null);					// 알림창 headertext 지정
			    alert.setContentText("중복된 ID가 존재합니다.");	// 알림창에 뜰 문자 지정
			    alert.show();								// 알림창 출력
			    
			    sid.clear();				// ID 입력창 비움
				
			} else {						// 공백이 포함되지 않고 중복된 ID가 없을 경우 ID생성
					
				Alert alert = new Alert(AlertType.INFORMATION);	// INFORMATION타입 알림창 생성
			    alert.setTitle("ID 중복 확인");					// 알림창 title 지정
			    alert.setHeaderText(null);						// 알림창 headertext 지정
			    alert.setContentText("사용가능한 ID입니다.");			// 알림창에 뜰 문자 지정
			    alert.show(); 									// 알림창 출력
			}	//end else
		} catch (IllegalArgumentException e) {
			System.out.println("IllegalArgumentException caught"); // 예외처리 발생!!
		}	//end catch
	}//end method
	
	// pw 조건확인 (11/19 문수지 PW 조건 메소드 작성)
	// (11/21 문수지 추가) 조건에 대문자 포함
	public int validPW(String inputPW) {
		getPW = spw.getText();	// 입력한 PW값 할당
		int inspect;			// 변수 선언
		
		String regExp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W).{8,20}$"; // 정규표현식으로 암호패턴 만드는 방법
		/*
		(?=.*[0-9]) >> 0~9까지 숫자 최소 한개
		(?=.*[a-zA-Z]) >> 영소대문자 a~z / A~Z 문자 최소 한개
		(?=.*\\W) >> 특수문자 최소 한개
		.{8,20} >> 선행 3가지 표현식(숫자, 영소문자, 특수문자)에 맞는 8~20자리 문자열
		*/
		
		if (getPW.isEmpty() || getPW.contains(" ")) { // PW창에 공백이 있을 경우
			inspect=0;	// 0반환
		} else if (getPW.matches(regExp)) { // 정규표현식 적용 결과 true >> 조건 충족
			inspect=2;	// 2반환
		} else { // 공백은 없으면서 정규표현식 조건을 충족하지 않을 경우
			inspect=1;	// 1반환
		}// end else
		return inspect;	//검사 결과 반환
	}// end method
	
	//btn 클릭 시 pw 조건확인 (11/19 문수지 PW 조건 이벤트 작성)
	public void pwChk(ActionEvent event) throws Exception {
		UserDTO uDTO = new UserDTO();	//객체 생성
		
		try {
			uDTO.setUser_pw(spw.getText());	//입력한 PW값 setting
			getPW = uDTO.getUser_pw();		// PW값 할당
			
			if (validPW(getPW)==0) {		// PW창에 공백이 있을 경우
									
				Alert alert = new Alert(AlertType.WARNING);	// WARNING타입 알림창 생성
			    alert.setTitle("PW 조건충족 확인");				// 알림창 title 지정
			    alert.setHeaderText(null);					// 알림창 headertext 지정
			    alert.setContentText("공란이 포함되어 있습니다.");	// 알림창에 뜰 문자 지정
			    alert.show();								// 알림창 출력
			    
			    spw.clear();				// PW 입력창 비움

			} else if (validPW(getPW)==1){	// 공백은 없으면서 정규표현식 조건을 충족하지 않을 경우

				Alert alert = new Alert(AlertType.WARNING);	// WARNING타입 알림창 생성
			    alert.setTitle("PW 조건충족 확인");				// 알림창 title 지정
			    alert.setHeaderText(null);					// 알림창 headertext 지정
			    alert.setContentText("영문/숫자/특수문자를 혼용하여 8자리 이상인지 확인해주십시오.");// 알림창에 뜰 문자 지정
			    alert.show();								// 알림창 출력
			    
			    spw.clear();				// PW 입력창 비움
				    
			} else {	// 조건 충족할 경우
					
				Alert alert = new Alert(AlertType.INFORMATION);	// INFORMATION타입 알림창 생성
			    alert.setTitle("PW 조건충족 확인");					// 알림창 title 지정
			    alert.setHeaderText(null);						// 알림창 headertext 지정
			    alert.setContentText("사용가능한 비밀번호입니다.");		// 알림창에 뜰 문자 지정
			    alert.show();									// 알림창 출력
			}	// end else
		} catch (IllegalArgumentException e) {
			System.out.println("IllegalArgumentException caught"); // 예외처리 발생!!
		}// end catch
	}// end method
	
	
	//(11/17 문수지, 윤준호 작성)회원가입 화면에서 닫기 버튼
	//(11/22 문수지 추가) css 등록, 창 아이콘 삽입
	public void signUpClose(ActionEvent event) throws Exception {
		      Stage primaryStage = new Stage();
		      Stage stage = (Stage) signUpClose.getScene().getWindow();
		      
		      Parent login = FXMLLoader.load(getClass().getResource("login.fxml"));  
		      Scene sc = new Scene(login);
		      //(11/22 문수지) css 등록
		      sc.getStylesheets().add(getClass().getResource("app.css").toString());
		      //(11/21 문수지) 창 아이콘 이미지 삽입
		      primaryStage.getIcons().add(new Image("file:lib/bus_image.png"));
		      primaryStage.setTitle("버스 예약 프로그램 / 로그인");
		      primaryStage.setScene(sc);
		      primaryStage.show();
		      stage.close();
	}
		   
	//(11.17 문수지, 윤준호 작성) 회원가입 기능 구현
	//(11/19 문수지 추가) 회원가입 시 입력값 없을 경우 알림창(if문) 추가
	//(11/22 문수지 추가) css 등록, 창 아이콘 삽입, 휴대전화 번호가 11자리를 초과했을 경우 알림창 추가
	public void signupcompleteBtn(ActionEvent event) throws Exception {
		UserDTO uDTO = new UserDTO();	//객체 생성
		UserDAO obj = new UserDAO();
		uDTO.setUser_id(sid.getText());
		getID = uDTO.getUser_id();
		getPW = uDTO.getUser_pw();
		String getPhone = sphone.getText();
		
		if(sid.getText().isEmpty()||spw.getText().isEmpty()||			// (11/19 문수지) 입력값 중 하나라도 비어있을 경우 if문 추가
				sname.getText().isEmpty()||sphone.getText().isEmpty()||
				semail.getText().isEmpty()||strAddress.isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);					// WARNING타입 알림창 생성
			alert.setTitle("비어있는 정보가 있습니다.");						// 알림창 title 지정
			alert.setHeaderText(null);									// 알림창 headertext 지정
			alert.setContentText("정보를 모두 입력해주세요.");					// 알림창에 뜰 문자 지정
			alert.show();				// 알림창 보여줌
		} else if (obj.IdChk(getID)) {	// 중복체크 없이 확인 버튼 클릭시, 중복ID일 시
						
			Alert alert = new Alert(AlertType.WARNING);	// WARNING타입 알림창 생성
			alert.setTitle("아이디 중복확인");				// 알림창 title 지정
			alert.setHeaderText(null);					// 알림창 headertext 지정
			alert.setContentText("아이디 중복확인해주십시오.");	// 알림창에 뜰 문자 지정
			alert.show();				// 알림창 출력
		} else if(validPW(getPW)==0 || validPW(getPW)==1) {	 	// 비밀번호 조건 불충족한데 확인버튼 눌렀을 시
			Alert alert = new Alert(AlertType.WARNING);			// WARNING타입 알림창 생성
			alert.setTitle("비밀번호 조건충족 확인");					// 알림창 title 지정
			alert.setHeaderText(null);							// 알림창 headertext 지정
			alert.setContentText("비밀번호 조건충족 확인해주십시오.");	// 알림창에 뜰 문자 지정
			alert.show();				// 알림창 출력
		} else if (getPhone.length()>11) {	// (11/22 문수지) 휴대전화 번호가 11자리를 초과했을 경우 else if문 추가
			Alert alert = new Alert(AlertType.WARNING);			// WARNING타입 알림창 생성
			alert.setTitle("휴대전화 번호");						// 알림창 title 지정
			alert.setHeaderText(null);							// 알림창 headertext 지정
			alert.setContentText("휴대전화 번호를 숫자 11자리 이하로 작성해주십시오.");	// 알림창에 뜰 문자 지정
			alert.show();										// 알림창 출력
		} else {
		
			try {	//(11.17 윤준호 작성) 회원가입 시 입력한 정보 setter 활용 값할당
				uDTO.setUser_id(sid.getText());			
				uDTO.setUser_pw(spw.getText());
				uDTO.setUser_name(sname.getText());
				uDTO.setUser_phone(sphone.getText());
				uDTO.setUser_email(semail.getText());
				uDTO.setUser_address(strAddress);
				uDTO.setUser_fav_seat(strFavseat);
				
				UserDAO.SignupInsert(uDTO);	//(11.17 윤준호 작성 상기 할당된 값 데이터베이스에 저장하는 메소드 호출 파라미터로 uDTO 사용해 해당 값들 전달)
				//(11.17 문수지) 팝업 알림창 추가
			      Alert alert = new Alert(AlertType.INFORMATION);	// INFORMATION타입 알림창 생성
			      alert.setTitle("가입 알림창");				// 알림창 title 지정
			      alert.setHeaderText(null);				// 알림창 headertext 지정
			      alert.setContentText("회원가입 완료되었습니다.");// 알림창에 뜰 문자 지정
			      alert.show();			// 알림창 출력
		      

			try {
					Parent members = FXMLLoader.load(getClass().getResource("signup.fxml"));
					Scene scene = new Scene(members);
					Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					//(11/22 문수지) css 등록
				    scene.getStylesheets().add(getClass().getResource("app.css").toString());
				    //(11/21 문수지) 창 아이콘 이미지 삽입
				    primaryStage.getIcons().add(new Image("file:lib/bus_image.png"));
					primaryStage.setTitle("버스 예약 프로그램 / 로그인");
					primaryStage.setScene(scene);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (IllegalArgumentException e) {
				System.out.println("IllegalArgumentException caught"); // 예외처리 발생!!
			} catch (Exception e) {
				e.printStackTrace();
			}//end catch
		}//end if
	}
}