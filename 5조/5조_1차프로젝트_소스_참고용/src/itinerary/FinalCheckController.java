package itinerary;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.StageManager;
import application.UserInfo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

// 최종 확인 창을 제어하는 컨트롤러 클래스
// 작성자 : 이기쁨
public class FinalCheckController implements Initializable{
	
	@FXML private Label choice1;		// 관광지 선택 1
	@FXML private Label choice2;		// 관광지 선택 2
	@FXML private Label choice3;		// 관광지 선택 3
	@FXML private Label choice4;		// 관광지 선택 4
	@FXML private Label choice5;		// 관광지 선택 5
	@FXML private Label startdate;		// 출발 날짜
	@FXML private Label totalprice;		// 총 예산
	@FXML private Button saveBtn;		// 일정 확정 버튼
	@FXML private Button reSelectBtn;	// 다시 담기 버튼
	
	ItineraryDAO itineraryDAO = new ItineraryDAO();			// itinerary 테이블 접근을 위한 DAO 객체 생성
	
	private Stage thisStage = StageManager.primaryStage;	// primaryStage 참조
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	// 컨트롤 초기화
	
		// getter로 선택된 관광지명 가져와서 출력
		choice1.setText(UserInfo.itineraryVO.getChoice1());
		choice2.setText(UserInfo.itineraryVO.getChoice2());
		choice3.setText(UserInfo.itineraryVO.getChoice3());
		choice4.setText(UserInfo.itineraryVO.getChoice4());
		choice5.setText(UserInfo.itineraryVO.getChoice5());
		// getter로 출발 날짜, 총 예산 가져와서 출력
		startdate.setText("출발 날짜 ▶  " + UserInfo.itineraryVO.getStart_date());
		totalprice.setText("총 예산 ▶     " + Integer.toString(UserInfo.itineraryVO.getTotalPrice()) + "원");
		
		// 최종 확정(SaveBtn) 버튼 이벤트 등록
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {		
			@Override
			public void handle(ActionEvent event) {
				saveBtnAction(event);
			}//end handle
		});//end saveBtn
		
		// 다시 담기(reSelectBtn) 버튼 이벤트등록
		reSelectBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				reSelectBtnAction(event);
			}//end handle
		});//ends reSelectBtn
		
	}//end initialize

	// 최종 확정 버튼(saveBtn) 눌렀을 때 이벤트 처리
	public void saveBtnAction(ActionEvent event) {		
		itineraryDAO.insertData(UserInfo.itineraryVO);	// DB의 ITINERARIES 테이블에 일정 정보 insert	
		try {
			Parent root;
			root = FXMLLoader.load(getClass().getResource("/mypage/layout/MyPage.fxml"));	// fxml 로드
			StageManager.changeScene(thisStage, root, "마이페이지");	// scene 전환	
		} catch (IOException e) {
			System.out.println("fxml 파일 로드 실패 >> saveBtnAction >> MyPage.fxml 로드 실패");	// 예외 발생 위치 알림
			e.printStackTrace();
		}//end try
	}//end saveBtnAction

	// 다시 담기 버튼(reSelectBtn) 눌렀을 때 이벤트 처리
	public void reSelectBtnAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("layout/Choice.fxml"));	// fxml 로드
			StageManager.changeScene(thisStage, root, "일정 선택");
		} catch (IOException e) {
			System.out.println("fxml 파일 로드 실패 >> reSelectBtnAction >> Choice.fxml 로드 실패");	// 예외 발생 위치 알림
			e.printStackTrace();
		} //end try		
	}//end reSelectBtnAction
}
