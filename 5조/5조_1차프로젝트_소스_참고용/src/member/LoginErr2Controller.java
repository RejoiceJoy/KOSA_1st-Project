package member;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

// 로그인 창 >> ID or PW가 DB에 저장된 정보와 불일치 할 시 생성되는 오류 다이얼로그 제어
// 작성자 : 이혜성
public class LoginErr2Controller implements Initializable{
	@FXML private Button closeBtn2;		// 확인 버튼(창 닫기)
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		closeBtn2.setOnAction(event->closeBtn2Action(event));	// 버튼 이벤트 등록
	}
	
	public void closeBtn2Action(ActionEvent event) {
		Stage stage = (Stage) closeBtn2.getScene().getWindow();	// 현재 컨트롤이 위치한 Stage 객체를 반환
		// close메서드: 창 닫힘
		stage.close();
	}
}