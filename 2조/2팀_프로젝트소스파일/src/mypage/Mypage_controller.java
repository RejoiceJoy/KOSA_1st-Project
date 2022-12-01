package mypage;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

//(11/21 손영석 작성)
public class Mypage_controller implements Initializable {

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      
   }
   
   // 확인 버튼을 누르면 로그인 아이디에 맞는 예약정보 출력
   @FXML
   private Label useridlabel, userpwlabel, usernamelabel, userphonelabel, useremaillabel, userlocationlabel, userseatlabel;
   @FXML
   private Button userinfo, userclose, fix, upbtn;
   //마이페이지 회원정보확인 11/21 손영석 작성	
   public void getMypagecheck() throws Exception {
	   Mypage obj_batch = new Mypage();	//객체생성
      
	   obj_batch.mypagesonView();
      
	   //회원정보 호출
	   useridlabel.setText(obj_batch.userid);				//아이디
	   userpwlabel.setText(obj_batch.userpw);				//비번
	   usernamelabel.setText(obj_batch.userName);			//이름
	   userphonelabel.setText(obj_batch.userPn);			//핸드폰번호
	   useremaillabel.setText(obj_batch.usereEmail);		//이메일
	   userlocationlabel.setText(obj_batch.userAddress);	//지역
	   userseatlabel.setText(obj_batch.UserFavSeat);		//선호좌석
            
   }
   
   
   // 업데이트 버튼 누르면 회원정보 업데이트 화면으로 이동 11/22 손영석 작성
   public void update() throws Exception {
	   Stage primaryStage = new Stage();
	   Stage stage = (Stage) upbtn.getScene().getWindow();	//업데이트 버튼 클릭시
      
	   Parent second = FXMLLoader.load(getClass().getResource("update.fxml"));//update.fxml실행  
	   Scene sc = new Scene(second);
	   //(11/22 문수지) css 등록
	   sc.getStylesheets().add(getClass().getResource("/main/app.css").toString());
	   //(11/21 문수지) 창 아이콘 이미지 삽입
	   primaryStage.getIcons().add(new Image("file:lib/bus_image.png"));
	   //(11/21 문수지) title 일치작업을 위한 수정
	   primaryStage.setTitle("버스 예약 프로그램 / 회원정보수정");
	   primaryStage.setScene(sc);
	   primaryStage.show();
	   stage.close();
   }

   
   // 닫기 버튼 누르면 홈화면으로 이동
   public void mypageClose() throws Exception {
      Stage primaryStage = new Stage();
      Stage stage = (Stage) userclose.getScene().getWindow();
      
      Parent second = FXMLLoader.load(getClass().getResource("/main/home_main.fxml"));  
      Scene sc = new Scene(second);
      //(11/22 문수지) css 등록
      sc.getStylesheets().add(getClass().getResource("/main/app.css").toString());
      //(11/21 문수지) 창 아이콘 이미지 삽입
      primaryStage.getIcons().add(new Image("file:lib/bus_image.png"));
      //(11/21 문수지) title 일치작업을 위한 수정
      primaryStage.setTitle("버스 예약 프로그램");
      primaryStage.setScene(sc);
      primaryStage.show();
      stage.close();
   }
   
   // 수정 버튼 누르면 홈화면으로 이동
   public void goupdate() throws Exception {
      Stage primaryStage = new Stage();
      Stage stage = (Stage) fix.getScene().getWindow();
      
      Parent second = FXMLLoader.load(getClass().getResource("/main/home_main.fxml"));  
      Scene sc = new Scene(second);
      //(11/22 문수지) css 등록
      sc.getStylesheets().add(getClass().getResource("/main/app.css").toString());
      //(11/21 문수지) 창 아이콘 이미지 삽입
      primaryStage.getIcons().add(new Image("file:lib/bus_image.png"));
      //(11/21 문수지) title 일치작업을 위한 수정
      primaryStage.setTitle("버스 예약 프로그램");
      primaryStage.setScene(sc);
      primaryStage.show();
      stage.close();
   }
  
}