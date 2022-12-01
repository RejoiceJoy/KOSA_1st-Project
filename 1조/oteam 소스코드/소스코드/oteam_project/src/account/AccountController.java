package account;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import login.loginuserAct;
import start.MemberDAO;
import start.MemberVO;

/*코드 작성자 : 김대영*/

// 계정 관리 버튼을 누를 때 출력되는 mypage.fxml의 화면을 제어하기 위한 클래스
public class AccountController implements Initializable {

	// mypage.fxml 파일의 구성 요소들
	@FXML
	private ToggleGroup toggleGroup;
	@FXML
	private ToggleButton householdButton,chartButton,accountButton;
	@FXML
	private Button assetButton;
	@FXML
	private TextField selectID, selectPW, selectName, selectPhone, selectbirthday, selectage, selectSex, selectRegion;
	@FXML
	private ListView<String> listView;
	@FXML
	private TextArea textArea;
	@FXML
	private TableView<MemberVO> tableMemberView;
	@FXML
	private TableColumn<MemberVO, Integer> tableMemberList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 메서드를 호출하여 계정 관리 페이지 이동시 값이 들어간 상태의 화면을 출력
		getinfo();
	}// end initialize
	
	
	// 계정관리 페이지에 나타나는 화면에 값 불러오는 메서드
	public void getinfo() {
		try {
			// userup메서드를 통해 member_code값을 반환하고, 그 값을 아래의 getMember메서드에 전달
			MemberVO mVO = getMember(loginuserAct.whoIsMember());
			
			// 화면에 있는 각 TextField에 현재 사용자의 정보를 get메서드를 사용하여 출력
			selectID.setText(mVO.getMember_id());
			selectPW.setText(mVO.getMember_pw());
			selectName.setText(mVO.getMember_name());
			// 사용자의 휴대폰 번호는 DB에서 NUMBER타입으로 되어있기 때문에 그냥 불러오면 앞의 0이 없어진 채로 오게된다
			// 따라서 직접 0을 추가하여 올바른 출력이 될 수 있도록 설정한다
			selectPhone.setText('0'+String.valueOf(mVO.getMember_phone()));
			selectbirthday.setText(mVO.getMember_birthday());
			selectage.setText(String.valueOf(loginuserAct.memberAge()));
			selectSex.setText(mVO.getMember_sex());
			selectRegion.setText(String.valueOf(mVO.getRegion_code()));
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}// end getinfo
	
	// member_code를 입력 매개변수로 받아 MemberDao클래스의 MyPage 메서드에 그 값을 전달하고
	// 전달받은 member_code로 MyPage 메서드를 실행한 결과를 반환하는 메서드
	public MemberVO getMember(int mem_code) {
		return MemberDAO.MyPage(mem_code);
	}

	// 나의 자산 버튼을 눌렀을 때 해당 페이지로 이동하는 동작을 구현한 메서드
	public void goAsset() throws Exception {
		
		Stage primaryStage = new Stage();
		Parent root;
		
		try {
			root = FXMLLoader.load(getClass().getResource("/asset/AssetPage.fxml"));
			Scene scene = new Scene(root);
			// 실행 윈도우 화면의 이름
			primaryStage.setTitle("Asset_Page");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// end goUserSignUp
	
	@FXML
	void selectToggleButton(ActionEvent event) {
		
		// 가계부(household) 버튼이 선택되었을 때 실행할 동작
		if (householdButton.isSelected()) {
			
			Stage primaryStage = new Stage();
			Stage stage = (Stage) householdButton.getScene().getWindow();
			Parent root;
			
			try {
				root = FXMLLoader.load(getClass().getResource("/household/HouseholdPage.fxml"));
				Scene scene = new Scene(root);
				// 실행 윈도우 화면의 이름
				primaryStage.setTitle("Household_Page");
				primaryStage.setScene(scene);
				primaryStage.show();
				stage.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		  // 통계(chart) 버튼이 선택되었을 때 실행할 동작 
		} else if (chartButton.isSelected()) {
			
			Stage primaryStage = new Stage();
			Stage stage = (Stage) chartButton.getScene().getWindow();
			Parent root;
			
			try {
				root = FXMLLoader.load(getClass().getResource("/chart/ChartPage.fxml"));
				Scene scene = new Scene(root);
				// 실행 윈도우 화면의 이름
				primaryStage.setTitle("Chart_Page");
				primaryStage.setScene(scene);
				primaryStage.show();
				stage.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		  
		  // 계정관리(account) 버튼이 선택되었을 때 실행할 동작	
		} else if (accountButton.isSelected()) {
			
			Stage primaryStage = new Stage();
			Stage stage = (Stage) accountButton.getScene().getWindow();
			Parent root;
			
			try {
				root = FXMLLoader.load(getClass().getResource("AccountPage.fxml"));
				Scene scene = new Scene(root);
				// 실행 윈도우 화면의 이름
				primaryStage.setTitle("Account_Page");
				primaryStage.setScene(scene);
				primaryStage.show();
				stage.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}	

}