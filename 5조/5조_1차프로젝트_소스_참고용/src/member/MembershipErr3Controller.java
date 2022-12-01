package member;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

// 사용자가 입력한 ID값이 데이터베이스의 기존 아이디와 중복되었을 경우의 다이얼로그 창 컨트롤러
// 작성자 : 최영광
public class MembershipErr3Controller implements Initializable{
	@FXML private Button closeBtn1;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		closeBtn1.setOnAction(event->closeBtn1Action(event));	
	}
	
	public void closeBtn1Action(ActionEvent event) {
		Stage stage = (Stage) closeBtn1.getScene().getWindow();
		// close메서드: 창 닫힘
		stage.close();
	}
}


