package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

// 프로그램 실행 시작 지점
// 작성자 : 최영광
public class Intro extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			StageManager.primaryStage = primaryStage;			// StageManager 클래스에 primaryStage 세팅(초기화)	- 추가 : 이수봉

			Parent root = FXMLLoader.load(getClass().getResource("/member/layout/Login.fxml")); 
	        StageManager.changeScene(primaryStage, root, "로그인");
			
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
