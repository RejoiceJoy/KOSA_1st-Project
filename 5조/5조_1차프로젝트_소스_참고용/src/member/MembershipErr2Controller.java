package member;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

// 입력한 '비밀번호'와 '비밀번호 확인'이 다른 상태로 회원가입을 시도했을 경우의 다이얼로그 창 컨트롤러 
// 작성자 : 최영광
public class MembershipErr2Controller implements Initializable{
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


