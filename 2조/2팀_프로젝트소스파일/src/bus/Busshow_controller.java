package bus;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

//배자조회를 위한 FXML(busshow.fxml) 컨트롤러(이하 클래스 별도 표기가 없을시 11.21, 손영석 김유진 공동작성)
public class Busshow_controller implements Initializable {

	//FXML 어노테이션 설정
	@FXML
	private Label first_bustime,second_bustime,third_bustime,fourth_bustime,first_buscode, second_buscode, third_buscode, fourth_buscode, leftseat_bus1, leftseat_bus2, leftseat_bus3, leftseat_bus4 ;
	@FXML
	private Button bus_show, bus_close;
	@FXML
	private ComboBox<String> bus_showlocation;
	ObservableList<String> loc_list = FXCollections.observableArrayList("강남", "강북", "강동", "강서");
	@FXML
	private DatePicker bus_rDatePicker;// 배차버스를 조회하고 싶은 날짜는 DatePicker를 활용하여 선택
   
	//지역선택 콤보박스에 4개지역을 초기설정
  	@Override
  	public void initialize(URL location, ResourceBundle resources) {
  		bus_showlocation.setItems(loc_list);
  	}
   
  	//버스장소 및 일자를 저장할 변수를 생성
  	public String bus_loc = null;
  	public String bus_strDate = null;
   
  		//콤보박스에서 버스탑승장소 선택한 값을  버스장소 변수에 저장 
	   	public void bus_comboChanged(ActionEvent event){    
	   		bus_loc = bus_showlocation.getValue();
	   	}
	
	   	//DatePicker에서 선택한 값을 버스일자 변수에 저장
	   	public void bus_selectDate(ActionEvent event) {
	   		//선택된 날짜의 값을 문자열로 변환해서 문자열에 저장
	   		bus_strDate = bus_rDatePicker.getValue().toString();
	   	}
	   
	
	   	// 배차정보 및 잔여좌석현황을 가져와 화면표시하도록 하는 메소드
	   	//(11/22 문수지 추가) if와 else if문 추가
	   	public void getBustime(ActionEvent event) throws Exception {
	
	   		Busshow obj_bus = new Busshow();
	   		obj_bus.allBusView(bus_loc);		//버스장소 String값을 입력받아 배차현황 자바파일에 저장된 버스배차 조회하는 기능을 가진 allBusView 메소드를 객체생성 후 실행
	   		//버스일자나 탑승장소가 공란일 경우 경고 메시지를 호출 + ui 추가(11.22, 문수지 추가)
	   		if (bus_strDate==null) {
	   			Alert alert = new Alert(AlertType.WARNING);// WARNING타입 알림창 생성
	   			alert.setTitle("버스탑승날짜");						// 알림창 title 지정
	   			alert.setHeaderText(null);						// 알림창 headertext 지정
		      	alert.setContentText("버스 탑승 날짜를 선택해주십시오.");// 알림창에 뜰 문자 지정
		      	alert.show(); 									// 알림창 출력
	   		} else if (bus_loc == null) {		// (11/22 문수지) 버스 탑승장소 미지정 시 알림창
	   			Alert alert = new Alert(AlertType.WARNING);		// WARNING타입 알림창 생성
	   			alert.setTitle("버스탑승장소");						// 알림창 title 지정
	   			alert.setHeaderText(null);						// 알림창 headertext 지정
	   			alert.setContentText("버스 탑승 장소를 선택해주십시오.");// 알림창에 뜰 문자 지정
	   			alert.show();									// 알림창 보여줌
	   		} else {	// 정보를 모두 입력했을 경우
	   			// 각 해당 라벨에 buslist 배열에 저장되어있던 값을 불러와 입력
	   			first_bustime.setText(obj_bus.buslist.get(0)[2]);	//첫번째 버스 시간
	   			second_bustime.setText(obj_bus.buslist.get(1)[2]);	//두번째 버스 시간
	   			third_bustime.setText(obj_bus.buslist.get(2)[2]);	//세번째 버스 시간
	   			fourth_bustime.setText(obj_bus.buslist.get(3)[2]);	//네번째 버스 시간
	      
	   			first_buscode.setText(obj_bus.buslist.get(0)[0]);	//첫번째 버스 번호
	   			second_buscode.setText(obj_bus.buslist.get(1)[0]);	//두번째 버스 번호
	   			third_buscode.setText(obj_bus.buslist.get(2)[0]);	//세번째 버스 번호
	   			fourth_buscode.setText(obj_bus.buslist.get(3)[0]);	//네번째 버스 번호
	   
	   			// 선택한 탑승장소, 일자에 해당하는 배차정보에서 이미 예약된 좌석수를 제외한 잔여좌석수를 가져옴, 잔여좌석수는 적색으로 보이도록 조치	
	   			obj_bus.left_seat_bus("Bus001", bus_loc, bus_strDate);
	   			leftseat_bus1.setText("잔여좌석현황 " + Integer.toString(obj_bus.bus_remainseat) + " / " + Integer.toString(obj_bus.bus_totalseat) );
	   			leftseat_bus1.setTextFill(Color.web("#FF0000"));	//추가
	      
	   			obj_bus.left_seat_bus("Bus002", bus_loc, bus_strDate);
	   			leftseat_bus2.setText("잔여좌석현황 " + Integer.toString(obj_bus.bus_remainseat) + " / " + Integer.toString(obj_bus.bus_totalseat) );
	   			leftseat_bus2.setTextFill(Color.web("#FF0000"));	//추가
	      
	   			obj_bus.left_seat_bus("Bus003", bus_loc, bus_strDate);
	   			leftseat_bus3.setText("잔여좌석현황 " + Integer.toString(obj_bus.bus_remainseat) + " / " + Integer.toString(obj_bus.bus_totalseat) );
	   			leftseat_bus3.setTextFill(Color.web("#FF0000"));	//추가
	      
	   			obj_bus.left_seat_bus("Bus004", bus_loc, bus_strDate);
	   			leftseat_bus4.setText("잔여좌석현황 " + Integer.toString(obj_bus.bus_remainseat) + " / " + Integer.toString(obj_bus.bus_totalseat) );
	   			leftseat_bus4.setTextFill(Color.web("#FF0000"));	//추가
	   		}
	   	}
	   
	
	   	// 닫기 버튼 누르면 홈화면으로 이동 (11.22, 문수지 추가) css 등록, 창 아이콘 삽입, title 일치작업 수정
	   	public void busClose(ActionEvent event) throws Exception {
	   		Stage primaryStage = new Stage();
	   		Stage stage = (Stage) bus_close.getScene().getWindow();
	      
	   		Parent second = FXMLLoader.load(getClass().getResource("/main/home_main.fxml"));  
	   		Scene sc = new Scene(second);
	   		//(11/22 문수지) css 등록
	   		sc.getStylesheets().add(getClass().getResource("/main/app.css").toString());
	   		//(11/21 문수지) 창 아이콘 이미지 삽입
	   		primaryStage.getIcons().add(new Image("file:lib/bus_image.png"));
	   		//(11/21 문수지) title 일치작업을 위한 수정
	   		primaryStage.setTitle("버스 예약 프로그램 / 로그인");
	   		primaryStage.setScene(sc);
	   		primaryStage.show();
	   		stage.close();
	   	}
}