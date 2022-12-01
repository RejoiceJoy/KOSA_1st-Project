package main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Main extends Application {

	//초기 화면
	//(11/22 문수지 추가) css 등록, 창 아이콘 삽입
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = 
					FXMLLoader.load(getClass().getResource("login.fxml"));
			Scene scene = new Scene(root);
			
			//(11/22 문수지) css 등록
			scene.getStylesheets().add(getClass().getResource("app.css").toString());
			//(11/21 문수지) 창 아이콘 이미지 삽입
			primaryStage.getIcons().add(new Image("file:lib/bus_image.png"));
			
			primaryStage.setTitle("버스 예약 프로그램 / 로그인");
			primaryStage.setScene(scene);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}	//end try
	}//end start
	
	
	public static void main(String[] args) {
		launch(args);
		
	}

}