package application;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// Stage 관리 클래스
// 작성자 : 이수봉
public class StageManager {
	public static Stage primaryStage;						// primaryStage 객체(프로그램 시작 지점의 start 메소드에서 초기화)

	public static void changeScene(Stage stage, Parent root, String title) {
		Scene scene = new Scene(root);	// 로드한 fxml을 scene으로 생성
		stage.setTitle(title);			// 창 제목 설정
		stage.setScene(scene);			// scene 전환
		stage.show();					// 창 표시
	}
	
	// 확인, 닫기 외에 UI가 없는 알림용 다이얼로그 생성하는 메소드 정의
	public static Stage createAlertDiolog() {
		Stage alertDialog = new Stage();					// Stage 객체 생성
		alertDialog.initStyle(StageStyle.UTILITY);			// 다이얼로그 스타일을 흰 배경과 타이틀, 닫기 버튼만 있는 창으로 설정
		alertDialog.initModality(Modality.WINDOW_MODAL);	// 다이얼로그를 모달(소유자 창 사용 불가)로 설정
		alertDialog.initOwner(primaryStage);				// 소유자 창 설정
		alertDialog.setTitle("Notice"); 					// 다이얼로그 창 제목 설정
		
		return alertDialog;	// Stage 객체 반환
	}
	
	// 입력한 root를 다이얼로그의 scene으로 올려 표시하는 메소드
	public static void showAlertDiolog(Parent root) {
		Stage dialog = createAlertDiolog();	// 알림용 다이얼로그 생성
		Scene scene = new Scene(root);		// 로드한 fxml을 scene으로 생성
		
		dialog.setScene(scene); 			// scene 설정
		dialog.show(); 						// 다이얼로그 보여주기
	}
}
