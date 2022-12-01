package household;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/*코드 작성자 : 김준영*/
//HouseholdInsert.fxml을 Controller할 클래스(fx:controller="household.HouseholdInsertController")
public class HouseholdInsertController implements Initializable{
	
	
	//fxml의 fx:id 속성과 어노테이션 컨트롤 주입 > fx:id 속성값과 필드명은 동일 해야함
	@FXML private ComboBox<String> combo1_menu;  //ComboBox는 컬렉션
	@FXML private ComboBox<String> combo2_menu;
	@FXML private DatePicker now_date;
	@FXML private TextField money_text;
	@FXML private TextArea etc_text;
	@FXML private Button insert_button;   
	
	// 수입,카테고리의 초기값을 설정해서 입력이 들어오지 않아도 null값을 받지 않도록 java에서 제어
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		combo1_menu.setValue("수입");
		combo2_menu.setValue("기타");
	}//initialize
	
	//fxml의 fx:id를 읽어 필드 생성해줌, 새로운 데이터 입력 이벤트핸들러를 @통해 생성,등록
	//insert 버튼 클릭 이벤트 처리 메소드 정의
	@FXML
	private void insert_act(ActionEvent actionEvent) throws ClassNotFoundException, SQLException { 
		//입력창에서 입력버튼 누를시 각각의 컨트롤 값을 DAO의 입력메소드에 전달해줌
		HouseholdDAO.insertHousehold(combo1_menu.getValue(), combo2_menu.getValue(), now_date.getValue() 
									,money_text.getText(), etc_text.getText());
		//기존창을 닫음
		Stage stage = (Stage) insert_button.getScene().getWindow();
		Platform.runLater(() -> {
			stage.close();
		});
	
	}//insert_act
	

}
