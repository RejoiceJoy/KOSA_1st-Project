package household;

import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/*코드 작성자 : 김준영*/
//household page의 test를 위한 Main실행
public class TestMain extends Application{
	

	//start에서 fxml 읽어옴
	public void start(Stage primaryStage) throws Exception {
		//fxml 파일을 FXMLLoader클래스의 메소드를 이용하여 읽어옴
		Parent root = FXMLLoader.load(getClass().getResource("HouseholdPage.fxml"));
		//일어온 fxml파일을 Scene에 넣어 줌
		Scene scene = new Scene(root);
		
		//Stage에 scene을 올림
		primaryStage.setTitle("나의 가계부");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);
	}//start
	
	//fxml를 실행함
	public static void main(String[] args) {
		launch(args);

	}

}
