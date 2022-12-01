package reserveSearch;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

//예약현황 조회를 위한 FXML(busshow.fxml) 컨트롤러(이하 클래스 별도 표기가 없을시 11.17, 손영석 김유진 공동작성)
public class ReserveSearch_controller implements Initializable {

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      
   }
   
   //FXML 어노테이션 설정 (확인 버튼을 누르면 로그인 아이디에 맞는 예약정보 출력)
   @FXML
   private Label res_date,location_code,bus_code,fav_seat;
   @FXML
   private Button res_close;
   
   
   // 예약현황을 가져와 화면표시하도록 하는 메소드 
   // (11/22 문수지 추가) 예약내역 없을 경우 알림창 추가
   public void getReservecheck(ActionEvent event) throws Exception {
	   
      ReserveSearch obj_batch = new ReserveSearch();
      
      obj_batch.reserveView();
      //(11/22 문수지) 조회하는 날짜에 예약내역이 없을 경우 if문 추가
      if (obj_batch.myres_date==null) {
    	Alert alert = new Alert(AlertType.WARNING);	// WARNING타입 알림창 생성
		alert.setTitle("예약내역");					// 알림창 title 지정
		alert.setHeaderText(null);					// 알림창 headertext 지정
		alert.setContentText("예약내역이 없습니다.");		// 알림창에 뜰 문자 지정
		alert.show();								// 알림창 출력
      }
      // 예약한 일자, 장소, 버스번호, 선호좌석을 가져와 라벨에 입력 
      else {
	      res_date.setText(obj_batch.myres_date);
	      location_code.setText(obj_batch.mylocation_code);
	      bus_code.setText(obj_batch.mybus_code);
	      fav_seat.setText(obj_batch.myUSER_FAV_SEAT);
      }
   }
   
   
   
   
   // 닫기 버튼 누르면 홈화면으로 이동
   //(11/22 문수지 추가) css 등록, 창 아이콘 삽입, title 일치작업 수정
   public void reserveClose(ActionEvent event) throws Exception {
      Stage primaryStage = new Stage();
      Stage stage = (Stage) res_close.getScene().getWindow();
      
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
   }//end method
}// end class