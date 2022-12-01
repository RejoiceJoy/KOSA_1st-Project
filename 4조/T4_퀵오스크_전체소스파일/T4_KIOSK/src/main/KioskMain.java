//김미리, 김학수, 배재연 페어 프로그래밍_pair programming

package main;

import javafx.application.Application;//메인 클래스는 반드시 Application class를 상속해야 하므로 javafx.application.Application을 import
import javafx.fxml.FXMLLoader;//FXMLLoader의 정적 메소드인 load를 이용해 FXML을 사용하기 위해 javafx.fxml.FXMLLoader를 import
import javafx.scene.Parent;//Scene에 매개변수로 넣을 매개값인 Parent 객체를 생성하기 위해 javafx.scene.Parent를 import
import javafx.scene.Scene;//Stage에 매개변수로 심을 Scene 객체를 생성하기 위해 javafx.scene.Scene을 import
import javafx.stage.Stage;//윈도우창 객체인 Stage를 이용하기 위해 javafx.stage.Stage

public class KioskMain extends Application {//메인 클래스는 반드시 Application class를 상속해야 함.

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("Root.fxml"));/*FXMLLoader의 정적 메소드인 load(매개값으로 URL 객체가 옴.)를 이용해서 FXML파일을 읽어들여 선언된 내용을 객체화.
		getClass()는 현재 class에 대한 class 객체를 반환하고, getResource()는 FXML의 위치정보를 갖고 있는 URL 객체를 반환한다. 따라서 load() 메소드의 매개값으로 사용 가능.
		getClass() : 현재 class가 존재하는 위치에서
		getResource() : 매개변수값으로 주어진 resource를 찾아라.
		load() 메소드가 반환하는 실제 객체는 FXML 파일에서 루트 태그로 선언된 컨테이너이며, Scene을 생성할 때 매개값으로 사용됨.*/ 
		Scene scene = new Scene(root);//Root.fxml파일의 Anchorpane을 루트컨테이너로 해서 Scene 생성

		scene.getStylesheets().add(getClass().getResource("app.css").toString());//css 등록
		
		primaryStage.setTitle("T4 cafe Kiosk");//윈도우창 제목 설정
		primaryStage.setScene(scene);//윈도우창에 장면 설정
		primaryStage.show();	// 윈도우 보여주기
		
	}
	
	public static void main(String[] args) {

		launch(args);	// KioskMain 객체 생성 및 메인 윈도우 생성.
		/*start 메소드는 직접 호출할 수 X. 메인 클래스에서 main() 메소드를 만들어서 main() 메소드에서 Application class가 갖고 있는 static method인 launch()를 호출하게 해서 내부적으로 start()를 호출하게 해줘야 함.
		이유 : javafx Application은 UI를 생성하는 쓰레드가 별도로 있음. 그래서 main을 실행시켜주는 main 쓰레드 이외에 UI 생성용 쓰레드를 별도로 가지고 있음. 그게 javafx Application 쓰레드.
		그 쓰레드를 launch() 메소드 안에서 만듦. -> 만들어서 javafx Application 쓰레드가 start() 메소드를 호출.*/
	}

}
