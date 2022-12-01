package itinerary;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.StageManager;
import application.UserInfo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

// 일정 선택 창을 제어하는 컨트롤러 클래스
// 작성자 : 이기쁨, 이수봉
public class ChoiceController implements Initializable {		// 인터페이스 구현
	//choice.fxml 에서 찾아서 등록
	@FXML private Button addBtn;					// 일정 담기 버튼
	@FXML private Button confirmBtn;				// 일정 확정 버튼
	@FXML private Button backToMainBtn;				// 돌아가기 버튼
	
	@FXML private DatePicker startDate;				// 출발 날짜 선택
	@FXML private ListView<String> countryList;		// 국가 리스트(String 담는 리스트뷰)
	@FXML private ListView<String> regionList;		// 지역 리스트(String 담는 리스트뷰)
	@FXML private ListView<String> sightNameList;	// 관광지명 리스트(String 담는 리스트뷰)
	
	@FXML private Label sightName;		// 관광지명
	@FXML private Label price;			// 가격
	@FXML private Label sightData;		// 관광지 설명
	@FXML private Label address;		// 관광지 주소
	@FXML private ImageView image;		// 이미지 파일명
	
	// 선택한 관광지명 표시할 Label(1~5)
	@FXML private Label choice1;		
	@FXML private Label choice2;		
	@FXML private Label choice3;		
	@FXML private Label choice4;		
	@FXML private Label choice5;
	// 선택한 관광지의 이미지를 표시할 ImageView(1~5)
	@FXML private ImageView image1;		
	@FXML private ImageView image2;		
	@FXML private ImageView image3;
	@FXML private ImageView image4;
	@FXML private ImageView image5;
	
	private int totalPrice;							//총 예산

	SightInfoVO sightInfoVO = new SightInfoVO();	// sights_info 테이블 정보를 담는 VO 객체 생성
	SightInfoDAO sightInfoDAO = new SightInfoDAO();	// sights_info 테이블 접근을 위한 DAO 객체 생성
	ItineraryDAO itineraryDAO = new ItineraryDAO();	// itinerary 테이블 접근을 위한 DAO 객체 생성
	
	private Stage thisStage = StageManager.primaryStage;	// primaryStage 참조
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {		// 클래스로 구현해서 오버라이딩(컨트롤 초기화)
		
		// 국가명 표시할 리스트뷰 세팅
		countryList.setItems(sightInfoDAO.showCountryList());

		//countryList 선택 시 속성 감시 -> regionList 출력
		countryList.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					regionList.setItems(sightInfoDAO.showRegionList(newValue));			// 선택한 국가의 지역을 저장한 observableArrayList를 지역 ListView에 출력
				}
			});
		
		//regionList 선택 시 속성 감시 -> sightNameList 출력
		regionList.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					sightNameList.setItems(sightInfoDAO.showSightNameList(newValue));	// 선택한 지역의 관광지명 저장한 observableArrayList를 관광지명 ListView에 출력
				}
			});
		
		//sightNameList 선택 시 속성 감시 -> 관광지 정보 출력
		sightNameList.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// newValue가 null이 아닐 때(=관광지명이 선택된 상태일 때)만 if문 실행
					if(newValue != null) {
						sightInfoVO = sightInfoDAO.showSightInfo(newValue);		// DAO 클래스의 showSightInfo로 VO 객체 반환
						// VO 객체의 getter를 이용해 필요한 데이터를 각 변수에 저장
						String txt_sightName = sightInfoVO.getSight_name();
						int num_price = sightInfoVO.getPrice();
						String txt_sightData = sightInfoVO.getSight_data();
						String txt_address = sightInfoVO.getAddress();
						String txt_image = sightInfoVO.getImage();

						//관광지명 출력
						sightName.setText(txt_sightName);	
						//가격 출력(0일시 무료 표시)
						if(num_price > 0) {
							price.setText(Integer.toString(num_price) + "원");
						} else {
							price.setText("무료");
						}
						//관광지 설명 출력(null값일 시 별도 문구 출력)
						if(txt_sightData != null) {
							sightData.setText(txt_sightData);
						} else {
							sightData.setText("정보가 없습니다.");
						}
						//관광지 주소 출력
						address.setText(txt_address);
						//이미지 출력(오류 시(=null값 발생 시) no_image 출력)
						try {
							image.setImage(new Image(getClass().getResource("image/" + txt_image).toString()));
						}catch(NullPointerException e) {
							// null값이 발생하는 경우(이미지 파일이 없거나, 경로가 틀린 경우) 실행
							image.setImage(new Image(getClass().getResource("image/no_image.png").toString()));
						}	
					} else {	// newValue가 null인 경우(=관광지명이 선택되지 않은 상태)일 때 각 컨트롤을 빈 값으로 초기화
						sightName.setText("");
						price.setText("");
						sightData.setText("");
						address.setText("");
						image.setImage(null);
					}
				}//end changed
			});//end Listener
		
		// 일정 담기(addBtn) 버튼 이벤트 등록
		addBtn.setOnAction(new EventHandler<ActionEvent>() {		//익명클래스		
			@Override
			public void handle(ActionEvent event) {		
				// 관광지 선택란이 공백이 아닐 경우 addBtnAction 실행
				if((sightName.getText().equals("")) == false ) {
					addBtnAction(event);
				} else {
					// 관광지 선택란이 공백일 경우 오류메세지 띄우기
					try { 
						//Stage dialog = StageManager.createAlertDiolog();	// 알림용 다이얼로그 생성
						Parent root = FXMLLoader.load(getClass().getResource("layout/AddError3.fxml")); // fxml 로드
						StageManager.showAlertDiolog(root);		// 알림창 표시
					}catch(IOException e) {
						System.out.println("fxml 파일 로드 실패 >> addBtn.setOnAction >> AddError3.fxml 로드 실패");	// 예외 발생 위치 알림
						e.printStackTrace();
					}//end try					
				}
			}//end handle	
		});
		
		// 일정 확정(confirmBtn) 버튼 이벤트 등록
		confirmBtn.setOnAction(new EventHandler<ActionEvent>() {		
			@Override
			public void handle(ActionEvent event) {
				confirmBtnAction(event);
			}//end handle
		});
		
		// 마이페이지로 돌아가기(backToMainBtn) 버튼 이벤트 등록
		backToMainBtn.setOnAction(new EventHandler<ActionEvent>() {		
			@Override
			public void handle(ActionEvent event) {
				backToMainBtnAction(event);
			}//end handle
		});
		
	}//end initialize
	
	private String[] sightBasket = new String[5];		// 담기 버튼 눌렀을 때 관광지명 담아놓을 배열
	private String[] imageBasket = new String[5];		// 담기 버튼 눌렀을 때 관광지 사진 담아놓을 배열
	
	// 담기 버튼(addBtn)눌렀을 때 이벤트 처리
	public void addBtnAction(ActionEvent event) { 	
		String selectedSight = sightName.getText();		// 현재 표시중인 관광지명 받아오기
		String txt_image = sightInfoVO.getImage();
		try {
			// 버튼을 누를 때마다 for문 실행 >> 배열의 첫 index부터 차례로 탐색
			for(int i = 0; i <= sightBasket.length; i++) {
				// 해당 index의 값이 null인 경우 현재 선택된 관광지 정보(이름, 사진) 할당
				if (sightBasket[i] == null) {				
					sightBasket[i] = selectedSight;
					imageBasket[i] = txt_image;
					// 일정 선택 시 화면 아래쪽에 선택 아이템 출력하기
					choice1.setText(sightBasket[0]);
					choice2.setText(sightBasket[1]);
					choice3.setText(sightBasket[2]);
					choice4.setText(sightBasket[3]);
					choice5.setText(sightBasket[4]);					
					// 일정 선택 시 화면 아래쪽에 사진 출력하기
					try {
						image1.setImage(new Image(getClass().getResource("image/" + imageBasket[0]).toString()));
					}catch(NullPointerException e) {
					}	
					try {
						image2.setImage(new Image(getClass().getResource("image/" + imageBasket[1]).toString()));
					}catch(NullPointerException e) {
					}
					try {
						image3.setImage(new Image(getClass().getResource("image/" + imageBasket[2]).toString()));
					}catch(NullPointerException e) {
					}
					try {
						image4.setImage(new Image(getClass().getResource("image/" + imageBasket[3]).toString()));
					}catch(NullPointerException e) {
					}
					try {
						image5.setImage(new Image(getClass().getResource("image/" + imageBasket[4]).toString()));
					}catch(NullPointerException e) {
					}
										
					// 총 예산 구하기(버튼을 누를 때마다 누적 연산)
					totalPrice += sightInfoVO.getPrice();
					break;
				}else if(sightBasket[i].equals(selectedSight)){		// null이 아닌 index는 바로 객체값 비교를 통해 중복 체크
					try { 
						// 값이 동일한 경우 중복임을 알리는 다이얼로그 표시
						//Stage dialog = StageManager.createAlertDiolog();	// 알림용 다이얼로그 생성
						Parent root = FXMLLoader.load(getClass().getResource("layout/AddError1.fxml")); // fxml 로드
						StageManager.showAlertDiolog(root);	// 알림창 표시
					}catch(IOException e) {
						System.out.println("fxml 파일 로드 실패 >> addBtnAction >> AddError1.fxml 로드 실패");
						e.printStackTrace();
					}//end try						
					System.out.println("중복된 일정 입니다.");
					break;
				}//end if
			}//end for
			
			// 선택된 일정 setter로 저장해놓기	
			UserInfo.itineraryVO.setChoice1(sightBasket[0]);
			UserInfo.itineraryVO.setChoice2(sightBasket[1]);
			UserInfo.itineraryVO.setChoice3(sightBasket[2]);
			UserInfo.itineraryVO.setChoice4(sightBasket[3]);
			UserInfo.itineraryVO.setChoice5(sightBasket[4]);
					
			UserInfo.itineraryVO.setTotalPrice(totalPrice);		// setter로 총 예산 저장
			
		}catch(ArrayIndexOutOfBoundsException e){
			// 초과 선택시 알림 메세지 다이얼로그
			System.out.println("5개 초과하여 선택했습니다.");
			try {		
				//Stage dialog = StageManager.createAlertDiolog();	// 알림용 다이얼로그 생성
				Parent root = FXMLLoader.load(getClass().getResource("layout/AddError2.fxml")); // fxml 로드
				StageManager.showAlertDiolog(root);	// 알림창 표시		
			} catch (IOException exception) {
				System.out.println("fxml 파일 로드 실패 >> addBtnAction >> AddError2.fxml 로드 실패");		
				exception.printStackTrace();
			}//end try			
		}//end try
	}//end addBtnAction
	
	// 확정 버튼(confirmBtn)눌렀을 때 이벤트 처리
	public void confirmBtnAction(ActionEvent event) {	
		// 5개 선택이 모두 되었을 경우
		if (sightBasket[4] != null) {
			// DatePicker로 선택한 날짜 값이 존재(null이 아닌 경우 = 날짜를 선택한 경우)
			if(startDate.getValue() != null) {
				UserInfo.itineraryVO.setStart_date(startDate.getValue().toString());	// 선택한 날짜를 출발 날짜로 설정(setter 사용)
				try {		
					Parent root = FXMLLoader.load(getClass().getResource("layout/FinalCheck.fxml"));	// fxml 로드
					StageManager.changeScene(thisStage, root, "일정 확인");	// scene 전환			
				} catch (IOException e) {
					System.out.println("fxml 파일 로드 실패 >> confirmBtnAction >> FinalCheck.fxml 로드 실패");		// 예외 발생 위치 알림
					e.printStackTrace();
				}//end try
			} else {		// 날짜를 선택하지 않은 경우(null일 경우) >> 오류 창 생성
				System.out.println("날짜를 선택하지 않았습니다.");
				try {		
					//Stage dialog = StageManager.createAlertDiolog();		// 알림용 다이얼로그 생성
					Parent root = FXMLLoader.load(getClass().getResource("layout/ConfirmError2.fxml")); // fxml 로드
					StageManager.showAlertDiolog(root);	// 알림창 표시	
				} catch (IOException e) {
					System.out.println("fxml 파일 로드 실패 >> confirmBtnAction >> ConfirmError2.fxml 로드 실패");	// 예외 발생 위치 알림
					e.printStackTrace();
				}//end try
			}
		} else {		// 5개 미만이 선택된 상태로 일정 확정 버튼을 눌렀을 경우
			try { 
				Parent root = FXMLLoader.load(getClass().getResource("layout/ConfirmError1.fxml")); // fxml 로드
				StageManager.showAlertDiolog(root);	// 알림창 표시
			}catch(IOException e) {
				System.out.println("fxml 파일 로드 실패 >> confirmBtnAction >> ConfirmError1.fxml 로드 실패");	// 예외 발생 위치 알림
				e.printStackTrace();
			}//end try		
		}//end if	
	}//end confirmBtnAction
	
	// 돌아가기 버튼(backToMainBtn) 눌렀을 때 이벤트 처리
	public void backToMainBtnAction(ActionEvent event) {
		UserInfo.itineraryVO = itineraryDAO.selectOneRow(UserInfo.memberVO.getMember_id());	// 사용자가 마지막으로 확정했던 일정을 DB에서 불러옴
		try {	
			Parent root = FXMLLoader.load(getClass().getResource("/mypage/layout/MyPage.fxml"));	// fxml 로드
			StageManager.changeScene(thisStage, root, "마이페이지");	// scene 전환
		} catch (IOException e) {
			System.out.println("fxml 파일 로드 실패 >> backToMainBtnAction >> MyPage.fxml 로드 실패");		
			e.printStackTrace();
		}//end try
	}//end backToMainBtn
}//end class
