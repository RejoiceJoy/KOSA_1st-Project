package start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*코드 작성자 : 김대영*/

public class Main extends Application {
	
	//프로그램 첫 시작화면 호출
	@Override
	public void start(Stage primaryStage) {
		try {
			// FXMLLoder를 사용하여 같은 패키지 내 view폴더의 first.fxml을 로드
			Parent root = FXMLLoader.load(getClass().getResource("view/first.fxml"));
			// 로드한 fxml파일을 scene에 할당
			Scene scene = new Scene(root);
			// 실행 윈도우 화면의 이름
			primaryStage.setTitle("Oteam_Project");
			primaryStage.setScene(scene);
			// 화면 출력
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}	//end try
	}//end start
	
	public static void main(String[] args) {
		launch(args);
	}//end main
}//end Main
