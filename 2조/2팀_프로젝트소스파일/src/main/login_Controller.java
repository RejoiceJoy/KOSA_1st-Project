package main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class login_Controller implements Initializable{
	
	//Login
	@FXML
	private Label UserLogin;
	@FXML
	private TextField getID, getPhone, getName, inputID;
	@FXML
	private PasswordField getPW, inputPW;
	@FXML
	private Button loginActionBtn, UserSignUp, Cancel, goback;
	
	public static String lid = null;	//회원정보/예약내역 조회 위한 전역변수 lid
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	//(11/17 로그인 버튼 김유진, 손영석 작성) 
	//(11/22 문수지 추가) css 등록, 창 아이콘 삽입, title 일치작업 수정
	public void loginActionBtn(ActionEvent event) throws Exception {
		UserDTO uDTO = new UserDTO();	//객체 생성
		
		try {
		
			uDTO.setUser_id(inputID.getText());
			uDTO.setUser_pw(inputPW.getText());
			String getID = inputID.getText();
			String getPW = inputPW.getText().toString();
			UserDAO obj = new UserDAO();
			lid = uDTO.getUser_id();
			
			// 로그인하지 않고 버튼 눌렀을 때 오류 출력
			if (obj.login(getID,getPW)==1) {

				System.out.println("성공");
				
				try {
					Parent members = FXMLLoader.load(getClass().getResource("home_main.fxml"));
					Scene scene = new Scene(members);
					Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					//(11/22 문수지) css 등록
				    scene.getStylesheets().add(getClass().getResource("app.css").toString());
				    //(11/21 문수지) 창 아이콘 이미지 삽입
				    primaryStage.getIcons().add(new Image("file:lib/bus_image.png"));
				    //(11/21 문수지) title 일치작업을 위한 수정
				    primaryStage.setTitle("버스 예약 프로그램");
					primaryStage.setScene(scene);
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			} else {
				
				try {
					Parent members = FXMLLoader.load(getClass().getResource("error.fxml"));
					Scene scene = new Scene(members);
					Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					//(11/22 문수지) css 등록
				    scene.getStylesheets().add(getClass().getResource("app.css").toString());
				    //(11/21 문수지) 창 아이콘 이미지 삽입
				    primaryStage.getIcons().add(new Image("file:lib/bus_image.png"));
					primaryStage.setTitle("error");
					primaryStage.setScene(scene);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			} catch (IllegalArgumentException e) {
				System.out.println("IllegalArgumentException caught"); // 예외처리 발생!!

			}
		}
	
	//로그인 오류시 닫기버튼 누르면 로그인창으로 다시 이동
	//(11/22 문수지 추가) css 등록, 창 아이콘 삽입
	public void goback() throws Exception {
		Stage primaryStage = new Stage();
		Stage stage = (Stage) Cancel.getScene().getWindow();			
		Parent second = FXMLLoader.load(getClass().getResource("login.fxml"));
		Scene sc = new Scene(second);
		//(11/22 문수지) css 등록
	    sc.getStylesheets().add(getClass().getResource("app.css").toString());
	    //(11/21 문수지) 창 아이콘 이미지 삽입
	    primaryStage.getIcons().add(new Image("file:lib/bus_image.png"));
		primaryStage.setTitle("버스 예약 프로그램 / 로그인");
		primaryStage.setScene(sc);
		primaryStage.show();
		stage.close();
	}
	
	//로그인 화면에서 회원가입 버튼을 눌렀을 때 Sign_up 페이지로 이동
	//(11/22 문수지 추가) css 등록, 창 아이콘 삽입
	public void goUserSignUp() throws Exception {
		Stage primaryStage = new Stage();
		Stage stage = (Stage) UserSignUp.getScene().getWindow();
		
		Parent second = FXMLLoader.load(getClass().getResource("signup.fxml"));
		Scene sc = new Scene(second);
		//(11/22 문수지) css 등록
	    sc.getStylesheets().add(getClass().getResource("app.css").toString());
	    //(11/21 문수지) 창 아이콘 이미지 삽입
	    primaryStage.getIcons().add(new Image("file:lib/bus_image.png"));
		primaryStage.setTitle("버스 예약 프로그램 / 회원가입");
		primaryStage.setScene(sc);
		primaryStage.show();
		stage.close();
	}
}
