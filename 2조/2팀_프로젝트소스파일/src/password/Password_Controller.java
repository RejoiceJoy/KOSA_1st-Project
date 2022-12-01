package password;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Password_Controller implements Initializable{
	
	@FXML
	private Label UserLogin;
	@FXML
	private TextField getID, getPhone, getName, inputID;
	@FXML
	private PasswordField getPW, inputPW;
	@FXML
	private Button chk1, chk, back, cancel1, goback1;
	
	public static String lid = null;	//회원정보/예약내역 조회 위한 전역변수 lid
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	//마이페이지 회원정보 확인 클릭시 보안상 비밀번호 요구 및 확인 11/21 손영석 작성
	//(11/22 문수지 추가) css 등록, 창 아이콘 삽입, else if문 추가
	public void chk(ActionEvent event) throws Exception {
		System.out.println("성공1");
		passwordDTO pDTO = new passwordDTO();	//객체 생성
		
		try {
			pDTO.setUser_pw(inputPW.getText());
			String getPW = inputPW.getText().toString();//비밀번호 입력창에 작성된 비밀번호 입력	
			passwordDAO obj = new passwordDAO();						

			if (obj.login(getPW)==1) {			//로그인 비밀번호와 동일하면 성공						
				try {
					Parent members = FXMLLoader.load(getClass().getResource("/mypage/Mypage.fxml"));
					Scene scene = new Scene(members);
					Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					//(11/22 문수지) css 등록
					scene.getStylesheets().add(getClass().getResource("/main/app.css").toString());
					//(11/21 문수지) 창 아이콘 이미지 삽입
					primaryStage.getIcons().add(new Image("file:lib/bus_image.png"));
					primaryStage.setTitle("버스 예약 프로그램 / 마이페이지");
					primaryStage.setScene(scene);
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			} else if (getPW.isEmpty() || getPW.contains(" ")) {//(11/22 문수지) 마이페이지에 들어갈 시 패스워드를 입력하지 않을 경우 알림창 추가

					
				Alert alert = new Alert(AlertType.WARNING);	// WARNING타입 알림창 생성
				alert.setTitle("PW를 입력해주세요.");			// 알림창 title 지정
				alert.setHeaderText(null);					// 알림창 headertext 지정
				alert.setContentText("PW를 입력해 주십시오.");	// 알림창에 뜰 문자 지정
				alert.show();								// 알림창 보여줌
			}	// else if 닫음
			else {
				System.out.println("실패");
				System.out.println(obj.login(getPW));

				try {
					Alert alert = new Alert(AlertType.WARNING);
				    alert.setTitle("비밀번호 불일치");
				    alert.setHeaderText(null);
				    alert.setContentText("비밀번호가 틀렸습니다.");
				    alert.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
				System.out.println("contentPW: " + getPW);
				
				
				
			} catch (IllegalArgumentException e) {
				System.out.println("IllegalArgumentException caught"); // 예외처리 발생!!
			}
		
		
		}
	   
	
		// 닫기 버튼 누르면 홈화면으로 이동
		//(11/22 문수지 추가) css 등록, 창 아이콘 삽입, title 일치작업 수정
		public void back() throws Exception {
	      Stage primaryStage = new Stage();
	      Stage stage = (Stage)cancel1.getScene().getWindow();
	      
	      Parent second = FXMLLoader.load(getClass().getResource("/main/home_main.fxml"));  
	      Scene sc = new Scene(second);
	      //(11/22 문수지) css 등록
	      sc.getStylesheets().add(getClass().getResource("/main/app.css").toString());
	      //(11/21 문수지) 창 아이콘 이미지 삽입
	      primaryStage.getIcons().add(new Image("file:lib/bus_image.png"));
	      primaryStage.setTitle("버스 예약 프로그램");
	      primaryStage.setScene(sc);
	      primaryStage.show();
	      stage.close();
	   }
}