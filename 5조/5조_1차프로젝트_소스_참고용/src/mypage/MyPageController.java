package mypage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.StageManager;
import application.UserInfo;
import itinerary.SightInfoDAO;
import itinerary.SightInfoVO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

// 마이페이지 창을 제어하는 컨트롤러 클래스
// 작성자 : 이수봉, 최영광
public class MyPageController implements Initializable {
	@FXML private Label user;				// 사용자 표시 라벨
	@FXML private Label startdate;			// 출발 날짜 표시 라벨
	@FXML private Label totalprice;			// 총 가격 표시 라벨
	@FXML private Label choice1;			// 선택 관광지 1
	@FXML private Label choice2;			// 선택 관광지 2
	@FXML private Label choice3;			// 선택 관광지 3
	@FXML private Label choice4;			// 선택 관광지 4
	@FXML private Label choice5;			// 선택 관광지 5
	@FXML private Button createItiBtn;		// 일정 생성 버튼
	@FXML private Button btn1, btn2, btn3, btn4, btn5;	// 상세 정보 버튼(1~5)
	@FXML private ImageView image;						// 관광지 이미지
	@FXML private Label sightName;			// 관광지명
	@FXML private Label price;				// 가격
	@FXML private Label sightData;			// 관광지 설명
	@FXML private Label address;			// 관광지 주소
	
	private Stage thisStage = StageManager.primaryStage;	// primaryStage 참조
	
	SightInfoDAO sightInfoDAO = new SightInfoDAO();			// sights_info 테이블에 접근하는 DAO 객체 생성
	
	@Override
	// 컨트롤 초기화를 위한 메소드(오버라이드)
	public void initialize(URL location, ResourceBundle resources) {
		// 로그인한 사용자의 이름 출력
		user.setText(UserInfo.memberVO.getFullName()+"님 환영합니다!");
		
		// 로그인한 사용자가 가장 최근 선택한 일정 출력
		if(UserInfo.itineraryVO.getStart_date() != null) {	// 일정이 존재하는 경우
			// 출발 날짜, 총 예산 출력
			startdate.setText("출발 날짜 ▶  " + UserInfo.itineraryVO.getStart_date());
			totalprice.setText("총 예산 ▶     " + Integer.toString(UserInfo.itineraryVO.getTotalPrice()) + "원");
			// 선택한 관광지 차례대로 출력
			choice1.setText(UserInfo.itineraryVO.getChoice1());				// 첫번째 관광지명
			choice2.setText(UserInfo.itineraryVO.getChoice2());				// 두번째 관광지명
			choice3.setText(UserInfo.itineraryVO.getChoice3());				// 세번째 관광지명
			choice4.setText(UserInfo.itineraryVO.getChoice4());				// 네번째 관광지명
			choice5.setText(UserInfo.itineraryVO.getChoice5());				// 다섯번째 관광지명
		} else {
			// 일정이 존재하지 않는 경우
			startdate.setText("");		// 출발 날짜 표시하는 Label을 빈 값으로 초기화
			totalprice.setText("");		// 총 예산 표시하는 Label을 빈 값으로 초기화
			UserInfo.itineraryVO.setMember_id(UserInfo.memberVO.getMember_id());	// 사용자 ID를 세팅
		}
			
		// createItiBtn 버튼 이벤트 핸들러 등록
		createItiBtn.setOnAction(new EventHandler<ActionEvent>() {		
			@Override
			public void handle(ActionEvent event) {
				createItiBtnAction(event);				// 일정 생성 버튼(createItiBtn)눌렀을 때 동작시킬 메소드 호출
			}//end handle
		});
		
		// 상세정보 버튼 1~5에 이벤트 핸들러 등록
		btn1.setOnAction(new EventHandler<ActionEvent>() {		
			@Override
			public void handle(ActionEvent event) {
				infoBtnAction(event, UserInfo.itineraryVO.getChoice1());	// 상세정보1(btn1)눌렀을 때 동작시킬 메소드 호출
			}//end handle
		});
		btn2.setOnAction(new EventHandler<ActionEvent>() {		
			@Override
			public void handle(ActionEvent event) {
				infoBtnAction(event, UserInfo.itineraryVO.getChoice2());	// 상세정보2(btn2)눌렀을 때 동작시킬 메소드 호출
			}//end handle
		});
		btn3.setOnAction(new EventHandler<ActionEvent>() {		
			@Override
			public void handle(ActionEvent event) {
				infoBtnAction(event, UserInfo.itineraryVO.getChoice3());	// 상세정보3(btn3)눌렀을 때 동작시킬 메소드 호출
			}//end handle
		});
		btn4.setOnAction(new EventHandler<ActionEvent>() {		
			@Override
			public void handle(ActionEvent event) {
				infoBtnAction(event, UserInfo.itineraryVO.getChoice4());	// 상세정보4(btn4)눌렀을 때 동작시킬 메소드 호출
			}//end handle
		});
		btn5.setOnAction(new EventHandler<ActionEvent>() {		
			@Override
			public void handle(ActionEvent event) {
				infoBtnAction(event, UserInfo.itineraryVO.getChoice5());	// 상세정보5(btn5)눌렀을 때 동작시킬 메소드 호출
			}
		});
	}
	
	// 일정 생성 버튼(createItiBtn)눌렀을 때 이벤트 처리
	public void createItiBtnAction(ActionEvent event) {
		try {	
			Parent root = FXMLLoader.load(getClass().getResource("/itinerary/layout/Choice.fxml"));	// choice.fxml 로드
			Scene scene = new Scene(root);															// scene 생성
			
			thisStage.setTitle("일정 선택");	// 창 제목 설정
			thisStage.setScene(scene);		// scene 세팅(일정 선택 scene으로 전환)
			thisStage.show();				// 창 표시
		} catch (IOException e) {
			System.out.println("fxml 파일 로드 실패 >> createItiBtnAction");		// 예외 발생 위치 알림
			e.printStackTrace();
		}
	}
	
	// 상세정보 버튼 이벤트 처리(상세정보 1~5 버튼마다 만들어놓은 메서드 간략화)
	public void infoBtnAction(ActionEvent event, String sightName) {
		if(sightName != null) {
			// DB에 접근하여 해당 관광지의 상세정보 획득
			printInfo(sightInfoDAO.showSightInfo(sightName));	// 관광지 상세정보 표시
		}
	}
	
	// '상세정보' 출력
	public void printInfo(SightInfoVO sightInfoVO) {
		// 관광지 상세 정보를 SightInfoVO 객체의 getter를 통해 읽어옴 
		String txt_sightName = sightInfoVO.getSight_name();
		int num_price = sightInfoVO.getPrice();
		String txt_sightData = sightInfoVO.getSight_data();
		String txt_address = sightInfoVO.getAddress();
		String txt_image = sightInfoVO.getImage();
		
		// 관광지명 출력
		sightName.setText(txt_sightName);	
		// 가격 출력(0일시 무료 표시)
		if(num_price > 0) {
			price.setText(Integer.toString(num_price) + "원");
		} else {
			price.setText("무료");
		}
		// 관광지 설명 출력(null값일 시 별도 문구 출력)
		if(txt_sightData != null) {
			sightData.setText(txt_sightData);
		} else {
			sightData.setText("정보가 없습니다.");
		}
		// 관광지 주소 출력
		address.setText(txt_address);
		// 이미지 출력
		try {
			image.setImage(new Image(getClass().getResource("/itinerary/image/" + txt_image).toString()));
		}catch(NullPointerException e) {
			// null값이 발생하는 경우(이미지 파일이 없거나, 경로가 틀린 경우) 'no_image.png' 출력
			image.setImage(new Image(getClass().getResource("/itinerary/image/no_image.png").toString()));
		}	
	}
}
