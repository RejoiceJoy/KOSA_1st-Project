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
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

//홈화면을 위한 FXML(hoem_main.fxml) 컨트롤러(이하 클래스 별도 표기가 없을시 11.18, 손영석 김유진 공동작성)
public class Home_Controller implements Initializable {

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      
   }
   //FXML 어노테이션 설정  
   @FXML
   private Button reservego, reserveshow, showcar, showmypage, logoutBtn;
   
   
   // 예약하기(reservation.fxml) 화면으로 가기
   //(11/22 문수지 추가) css 등록, 창 아이콘 삽입, title 일치작업 수정
   public void reservego_me() throws Exception {
      Stage primaryStage = new Stage();
      Stage stage = (Stage) reservego.getScene().getWindow();
      //(11/22 문수지) css 등록
      Parent second = FXMLLoader.load(getClass().getResource("/reservation/reservation.fxml"));  //예약하기 화면으로 변경해주기!!!!!!!!!!
      Scene sc = new Scene(second);
      //(11/21 문수지) 창 아이콘 이미지 삽입
      sc.getStylesheets().add(getClass().getResource("app.css").toString());
      //(11/22 문수지) title 일치작업을 위한 수정
      primaryStage.getIcons().add(new Image("file:lib/bus_image.png"));
      primaryStage.setTitle("버스 예약 프로그램 / 예약하기");
      primaryStage.setScene(sc);
      primaryStage.show();
      stage.close();
   }
   
   // 예약현황 가기(reservechk.fxml)
   //(11/22 문수지 추가) css 등록, 창 아이콘 삽입, title 일치작업 수정
   public void reserveshow_me() throws Exception {
      Stage primaryStage = new Stage();
      Stage stage = (Stage) reserveshow.getScene().getWindow();
      
      Parent second = FXMLLoader.load(getClass().getResource("/reserveSearch/reserveChk.fxml"));
      Scene sc = new Scene(second);
      //(11/22 문수지) css 등록
      sc.getStylesheets().add(getClass().getResource("app.css").toString());
      //(11/21 문수지) 창 아이콘 이미지 삽입
      primaryStage.getIcons().add(new Image("file:lib/bus_image.png"));
      //(11/21 문수지) title 일치작업을 위한 수정
      primaryStage.setTitle("버스 예약 프로그램 / 예약확인");
      primaryStage.setScene(sc);
      primaryStage.show();
      stage.close();
   }
   
   // 배차조회 가기(busshow.fxml)
   //(11/22 문수지 추가) css 등록, 창 아이콘 삽입, title 일치작업 수정
   public void showcar_me() throws Exception {
      Stage primaryStage = new Stage();
      Stage stage = (Stage) showcar.getScene().getWindow();
      
      Parent second = FXMLLoader.load(getClass().getResource("/bus/busshow.fxml"));  //배차하기 화면으로 변경해주기!!!!!!!!!!
      Scene sc = new Scene(second);
      //(11/22 문수지) css 등록
      sc.getStylesheets().add(getClass().getResource("app.css").toString());
      //(11/21 문수지) 창 아이콘 이미지 삽입
      primaryStage.getIcons().add(new Image("file:lib/bus_image.png"));
      //(11/21 문수지) title 일치작업을 위한 수정
      primaryStage.setTitle("버스 예약 프로그램 / 배차현황");
      primaryStage.setScene(sc);
      primaryStage.show();
      stage.close();
   }
   
   // 마이페이지 조회를 위한 비밀번호 재확인화면(password.fxml) 가기
   public void showmypage_me(ActionEvent event) throws Exception {
	  Stage primaryStage = new Stage();
	  Stage stage = (Stage) showmypage.getScene().getWindow();
      Parent second = FXMLLoader.load(getClass().getResource("/password/password.fxml"));  
      Scene sc = new Scene(second);
      
      //css 등록(11.22, 문수지 추가)
      sc.getStylesheets().add(getClass().getResource("app.css").toString());
      //창 아이콘 이미지 삽입(11.22, 문수지 추가)
      primaryStage.getIcons().add(new Image("file:lib/bus_image.png"));
      primaryStage.setTitle("비밀번호 입력");
      primaryStage.setScene(sc);
      primaryStage.show();
      stage.close();
   }

   // 로그아웃 (11/21 문수지 작성)
   //(11/22 문수지 추가) css 등록, 창 아이콘 삽입, title 일치작업 수정
   public void logout(ActionEvent event) throws Exception { // 로그아웃 버튼을 누를 경우 이벤트     
	    Parent members = FXMLLoader.load(getClass().getResource("login.fxml"));//FXML파일을 읽어들여 선언된 내용을 객체화
	    // fxml 파일이 클래스와 동일한 패키지에 있을 경우 정적 load() 메서드로 fxml파일 로딩
	    Scene scene = new Scene(members);	// Scene 생성
		Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();	// Stage 생성
	    scene.getStylesheets().add(getClass().getResource("app.css").toString());		//css 등록
	    primaryStage.getIcons().add(new Image("file:lib/bus_image.png"));				//창 아이콘 이미지 삽입
		primaryStage.setTitle("버스 예약 프로그램 / 로그인");									// 로그인 페이지 타이틀 지정
		primaryStage.setScene(scene);
		primaryStage.show(); //login 윈도우 창 보여줌
   }	//end method
}