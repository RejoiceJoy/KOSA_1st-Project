package itinerary;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

// 여행 출발 날짜를 선택하지 않은 채로 일정 확정 시도 시 표시할 오류 창 제어
// 작성자 : 이혜성
public class ConfirmError2Controller implements Initializable{
	@FXML private Button closeBtn1;		// 확인 버튼(창 닫기)
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		closeBtn1.setOnAction(event->closeBtn1Action(event));		// 버튼 이벤트 등록
	}
	
	public void closeBtn1Action(ActionEvent event) {
		Stage stage = (Stage) closeBtn1.getScene().getWindow();
		// close메서드: 창 닫힘
		stage.close();
	}
}
