//김미리, 배재연 페어 프로그래밍_pair programming

package main;

import java.io.IOException;//FXMLLoader클래스의 정적 메소드 load() 메소드에 필수적으로 필요한 예외 java.io.IOException import
import java.net.URL;//Initializable을 상속한 컨트롤러 클래스에서 오버라이딩한 initialize 메소드의 매개값으로 넣기 위해 URL import
import java.util.ResourceBundle;//Initializable을 상속한 컨트롤러 클래스에서 오버라이딩한 initialize 메소드의 매개값으로 넣기 위해 ResourceBundle import

import javafx.application.Platform;//종료버튼 클릭 시 프로그램을 빠져나갈 exit() 메소드 사용을 위해 javafx.application.Platform import
import javafx.event.ActionEvent;//FXML 컨트롤인 버튼을 클릭시 발생한 ActionEvent를 매개값으로 받아 handler 메소드로 처리하기 위한 javafx.event.ActionEvent import
import javafx.fxml.FXML;//FXML 컨트롤 객체 주입을 위해 javafx.fxml.FXML import
import javafx.fxml.FXMLLoader;//주문버튼 클릭 시 포인트 적립 여부를 확인하는 커스텀 다이얼로그인 Membership.fxml을 Stage로 직접 띄우기 위해 javafx.fxml.FXMLLoader import
import javafx.fxml.Initializable;//컨트롤러는 Initializable 인터페이스를 반드시 상속받아야하므로 javafx.fxml.Initializable import
import javafx.scene.Scene;//Stage에 매개변수로 심을 Scene 객체를 생성하기 위해 javafx.scene.Scene을 import
import javafx.scene.control.Button;//Check.fxml로부터 주입돼 객체 생성된 버튼 컨트롤을 조작하기 위해 javafx.scene.control.Button import
import javafx.scene.layout.AnchorPane;//커스텀 다이얼로그인 Membership.fxml의 컨테이너를 AnchorPane으로 삼기 위해서 javafx.scene.layout.AnchorPane import
import javafx.stage.Modality;//적립하기버튼 클릭 시 포인트 적립 조회 및 회원가입하는 커스텀 다이얼로그인 Membership.fxml을 Stage로 직접 띄우기 위해 javafx.stage.Modality import
import javafx.stage.Stage;//적립하기버튼 클릭 시 포인트 적립 조회 및 회원가입하는 커스텀 다이얼로그인 Membership.fxml을 Stage로 직접 띄우기 위해 javafx.stage.Stage import
import javafx.stage.StageStyle;/*적립하기버튼 클릭 시 포인트 적립 조회 및 회원가입하는 커스텀 다이얼로그인 Membership.fxml을 Stage로 직접 띄우기 위해 javafx.stage.StageStyle import
Stage의 생성자 매개값에는 윈도우 스타일을 결정짓는 StageStyle 열거 상수가 옴. 본 코드에서 쓴 StageStyle 열거상수 UTILITY는 배경이 흰 색이고, 제목줄에 타이틀, 종료 버튼만 있음.*/

public class CheckController implements Initializable {
	
	@FXML private Button savepoint;//적립하기 버튼 객체 주입
	@FXML private Button exit;//종료하기 버튼 객체 주입
		
	public void handleSavepointAction(ActionEvent event) throws Exception {//적립하기 버튼 클릭 시 발생하는 이벤트 조작 메소드

		Stage primaryStage = (Stage)savepoint.getScene().getWindow();//커스텀 다이얼로그인 Membership.fxml을 띄우기 위한 사전 작업으로 Stage 필드값 직접 생성. 컨트롤러에서 다이얼로그를 실행할 때는 소유자 윈도우가 될 primaryStage가 필요.

		// 커스텀 다이얼로그 실행
		Stage dialog = new Stage(StageStyle.UTILITY);//다이얼로그 Stage 생성.
		dialog.initModality(Modality.WINDOW_MODAL);//모달 다이럴로그 설정. 이 설정을 하지 않을 시 기본적으로 모달리스가 됨.
		dialog.initOwner(primaryStage);//다이얼로그의 소유자 윈도우 설정.
		dialog.setTitle("멤버십 포인트");//다이얼로그의 제목 세팅.
		
		AnchorPane anchorPane = null;//다이얼로그의 컨테이너를 AnchorPane으로 설정하기 위해 변수 선언.
		try {
			anchorPane = (AnchorPane)FXMLLoader.load(getClass().getResource("Membership.fxml"));//포인트 적립 조회 및 회원가입하는 FXML파일 로딩해서 Parent 타입을 AnchorPane 타입으로 형변환한 후 컨테이너 변수에 대입.
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Scene scene = new Scene(anchorPane);//컨테이너를 씬에 매개변수로 주어 장면 설정.
		dialog.setScene(scene);//커스텀 다이얼로그 스테이지에 앵커패널로 설정한 장면(씬)을 주입해 장면 설정.
		dialog.setResizable(false);//적립 여부 확인 창은 사이즈 변경 불가.
		dialog.show();//stage show!

	}
		
	public void handleExitAction(ActionEvent event) {//종료하기 버튼 클릭 시 발생하는 이벤트 조작 메소드
		Platform.exit();//프로그램 종료
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}
