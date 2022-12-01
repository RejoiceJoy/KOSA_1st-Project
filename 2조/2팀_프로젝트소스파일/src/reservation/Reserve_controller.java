package reservation;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

//(11/18 윤준호 작성)
public class Reserve_controller implements Initializable {

   //reservation
   @FXML
   private Label UserReservation, label1, reserve_selected, availableseat;
   @FXML
   private ComboBox<String> HopOnLocation;
   ObservableList<String> list = FXCollections.observableArrayList("강남", "강북", "강동", "강서");
   @FXML
   private RadioButton seat1, seat2;
   @FXML
   private DatePicker rDatePicker;
   @FXML
   private Button reserveclose;
   @FXML
   private ToggleGroup group;
   @FXML 
   private TableView<BusListData> table;
   @FXML
   private TableColumn<BusListData, String> columnbusRealcode;
   @FXML
   private TableColumn<BusListData, String> columnbusCode;
   @FXML
   private TableColumn<BusListData, String> columnbusLocation;
   @FXML
   private TableColumn<BusListData, String> columnbusTime;
   @FXML
   private TableColumn<BusListData, String> columnbusSize;
   
   //DB값 저장할 변수
   private ObservableList<BusListData> Blist;
   
   private Connection con = null;
   private PreparedStatement prst=null;
   private ResultSet rs = null;
   
   public String loc = null;
   public String strDate = null;
   public String seat = null;
   public String reserveBuscode = null;
   public String textAvailable =null;
   public boolean available = false;
   int reservedCount =0;

   //최초 세팅(11/18 윤준호 작성 + 11/22 윤준호 추가)
   @Override
   public void initialize(URL location, ResourceBundle resources) {
      HopOnLocation.setItems(list);      //탑승지 콤보박스 리스트
      con = DBConnection.getConnection();   //DB접속
      Blist = FXCollections.observableArrayList();   //리스트초기화
        
        //테이블뷰 선택시 이벤트(11/22 윤준호 작성) -> 선택날짜, 선택 탑승지, 선택 시간, 선택 좌석 출력)
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {	//

            	reserve_selected.setText(rDatePicker.getValue().toString()+" / " +
                table.getSelectionModel().getSelectedItem().getBus_location() + " / " +
                table.getSelectionModel().getSelectedItem().getBus_time() + " / " + 
                seat);											//리스트뷰에서 선택된 값 라벨에 출력
            	//버스코드 데이터 변수에 저장
                reserveBuscode = table.getSelectionModel().getSelectedItem().getBus_code();
                searchpossible();								//잔여좌석 여부 확인 메소드 실행
                availableseat.setText(textAvailable);			//잔여좌석 표시
                availableseat.setTextFill(Color.web("FF0000")); //UI 디자인(11/22 문수지 추가)
            }
          });
   }

   //테이블뷰의 칼럼 설정(11/22 윤준호 작성)
   private void setCellTable() {      
      columnbusRealcode.setCellValueFactory(new PropertyValueFactory<>("bus_realcode"));
      columnbusCode.setCellValueFactory(new PropertyValueFactory<>("bus_code"));
      columnbusLocation.setCellValueFactory(new PropertyValueFactory<>("bus_location"));
      columnbusTime.setCellValueFactory(new PropertyValueFactory<>("bus_time"));
      columnbusSize.setCellValueFactory(new PropertyValueFactory<>("bus_size"));
   }
   
   //테이블 뷰에 세팅할 데이터 SQL DB에서 불러오기(11/21윤준호 작성)  
   public void BusDataView() {
       // sql문 작성
       String sql = "SELECT * FROM BUS WHERE Bus_location = '" +loc +"'"; 
       
       try {
          prst = con.prepareStatement(sql);   //sql문 실행
          rs = prst.executeQuery();         //sql문 실행결과 반환
          while (rs.next()) { //테이블뷰에 사용할 리스트 생성 리얼코드, 코드, 탑승지, 시간, 인승              
             Blist.add(new BusListData(rs.getString(2),rs.getString(1),rs.getString(4)
            		 ,rs.getString(3),rs.getString(5)));
           
          }//end while          
       } catch (Exception e) {
          System.out.println("DB 연결에 문제가 있습니다.");
          e.printStackTrace();
       } // end try
       table.setItems(Blist);
   }// end method

   //탑승지 정보 콤보박스에서 선택할 때 이벤트(11/18 윤준호 작성 + 11/22수정)
    public void comboChanged(ActionEvent event){	//fxml 에서 연결
       
       table.getItems().clear();      //테이블 뷰 초기화(11/22 윤준호)
       loc = HopOnLocation.getValue();   //선택된 값 loc에 저장(11/18 윤준호)
       
       BusDataView();               //테이블뷰 데이터 출력(11/22 윤준호)
       setCellTable();               //테이블뷰 칼럼 설정(11/22 윤준호)
    }
    
    
    //radio button(11/19 문수지 작성)
    public void selectSeat(ActionEvent event) {
    	//선택한 좌석의 값을 문자열로 변환해서 저장
    	group = new ToggleGroup();
    	
    	seat1.setToggleGroup(group);
    	seat1.setSelected(true);	// seat1 버튼 >> 기본값
    	seat2.setToggleGroup(group);
    	
    	if(seat1.isSelected()) {		// seat1 버튼 선택할 경우
    		seat = seat1.getText();		// seat에 seat1 값 저장
    	} else if(seat2.isSelected()) {	// seat2 버튼 선택할 경우
    		seat = seat2.getText();		// seat에 seat2 값 저장
    	}	// end else if
    }		// end method
    
    //DatePicker(11/17 윤준호 작성)
	public void selectDate(ActionEvent event) {
		//선택된 날짜의 값을 문자열로 변환해서 문자열에 저장
		strDate = rDatePicker.getValue().toString();
	}
	
	
   //예약 버튼 클릭 시 이벤트(11/18 윤준호 작성) -> DB에 설정된 값 저장
	// (11/22 문수지 추가) 입력값 채우지 않았을 시 알림창, 중복예약할 경우 알림창, css 등록, 창 아이콘 삽입, title 일치작업 수정 추가
	@FXML
	public void reserveBtn(ActionEvent event) throws Exception {
		ReserveDTO rDTO = new ReserveDTO();   //객체 생성
		//중복예약 방지 위한 구문(11/22 문수지 추가)
		if (strDate==null||loc==null||reserveBuscode==null) {	// (11/22 문수지) 입력값 채우지 않았을 경우 if문 추가
			Alert alert = new Alert(AlertType.WARNING);			// WARNING타입 알림창 생성
			alert.setTitle("비어있는 정보가 있습니다.");				// 알림창 title 지정
			alert.setHeaderText(null);							// 알림창 headertext 지정
			alert.setContentText("정보를 모두 입력해주세요.");			// 알림창에 뜰 문자 지정
			alert.show();										// 알림창 출력
		} else if(ReserveDAO.OnedayReserve()==1) {				// 한사람당 하루 예약이 2건 이상인 경우 else if문 추가
			Alert alert = new Alert(AlertType.WARNING);			// WARNING타입 알림창 생성
			alert.setTitle("중복 예약 알림");						// 알림창 title 지정
			alert.setHeaderText(null);							// 알림창 headertext 지정
			alert.setContentText("해당 날짜는 이미 예약하셨습니다.");	// 알림창에 뜰 문자 지정
			alert.show();										// 알림창 출력
		} else {
		}	
			try {
				if(available == true) {			//잔여좌석 정보 변수가 true 이면
					rDTO.setBus_code(reserveBuscode);	//선택 버스 코드 할당
					rDTO.setLocation_code(loc);		//선택한 탑승지정보 할당
					rDTO.setUser_fav_seat(seat);		//선택한 좌석 정보 할당
					rDTO.setRes_date(strDate);  		//선택한 날짜를 예약 날짜 할당
					//DAO의 DB연결 메소드 실행(예약정보 DB에 저장)
					ReserveDAO.ReserveInsert(rDTO);
		         
					Alert alert = new Alert(AlertType.INFORMATION);
		            alert.setTitle("예약 알림창");
		            alert.setHeaderText(null);
		            alert.setContentText("예약 완료되었습니다.");
		            
		            alert.show();
		         }  else {   //잔여좌석이 없어 예약할 수 없을때
		            Alert alert = new Alert(AlertType.WARNING);
		               alert.setTitle("예약 알림창");
		               alert.setHeaderText(null);
		               alert.setContentText("예약 할 수 없습니다.");
		               alert.show();
	         }
	         
	            try {
	               Parent members = FXMLLoader.load(getClass().getResource("reservation.fxml"));
	               Scene scene = new Scene(members);
	               Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	            	//(11/22 문수지) css 등록
				    scene.getStylesheets().add(getClass().getResource("/main/app.css").toString());
			    	//(11/21 문수지) 창 아이콘 이미지 삽입
				    primaryStage.getIcons().add(new Image("file:lib/bus_image.png"));
			    	//(11/21 문수지) title 일치작업을 위한 수정
				    primaryStage.setTitle("예약하기");
				    primaryStage.setScene(scene);
	            } catch (Exception e) {
	               e.printStackTrace();
	            }
	      } catch (IllegalArgumentException e) {
	         System.out.println("IllegalArgumentException caught"); // 예외처리 발생!!
	
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }

	
   //잔여 좌석 확인용 메서드
   public void searchpossible() {
      String sql = "SELECT COUNT(*) FROM RESERVE WHERE BUS_CODE = '" + reserveBuscode + "' and LOCATION_CODE = '" 
    		  + loc + "' and USER_FAV_SEAT = '" + seat +"'" + " and res_date = '" + strDate +"'"; 
      try {         
         prst = con.prepareStatement(sql);  	//sql문 실행
          rs = prst.executeQuery();         	//sql문 실행결과 반환
          if(rs.next()) {
             reservedCount = rs.getInt(1);		//반환 값
             int counting = (22-reservedCount);	//총 좌석수(44인승 버스라 가정했고 창측/복도측 좌석 : 22개) - 반환값 = 잔여좌석
             if (counting >0) {					//잔여좌석 있는 경우
                textAvailable = seat + "잔여 좌석 : " +counting + "/22 예약 가능";	//구문 출력
                available = true;				//예약 가능 여부 판단 변수 true로 설정
             } else {							//잔여좌석 없는 경우
                textAvailable = "예약 불가";		//구문 출력
                available = false;				//예약 가능 여부 판단 변수 false로 설정
             }
          }
      }catch (Exception e) {
              System.out.println("DB 연결에 문제가 있습니다.");
             e.printStackTrace();
          } // end try
      }
    
	   
	//닫기 버튼 클릭시 이벤트 홈화면으로 이동
   	//(11/22 문수지 수정) css 등록, 창 아이콘 삽입, title 일치작업 수정
	@FXML
	public void reservecloseBtn(ActionEvent event) throws Exception {
			Stage primaryStage = new Stage();
			Stage stage = (Stage) reserveclose.getScene().getWindow();
	      
			Parent members = FXMLLoader.load(getClass().getResource("/main/home_main.fxml"));
			Scene scene = new Scene(members);
			//(11/22 문수지) css 등록
		    scene.getStylesheets().add(getClass().getResource("/main/app.css").toString());
		    //(11/21 문수지) 창 아이콘 이미지 삽입
		    primaryStage.getIcons().add(new Image("file:lib/bus_image.png"));
		    //(11/21 문수지) title 일치작업을 위한 수정
		    primaryStage.setTitle("버스 예약 프로그램");
			primaryStage.setScene(scene);
			primaryStage.show();
			stage.close();

	}		   
}