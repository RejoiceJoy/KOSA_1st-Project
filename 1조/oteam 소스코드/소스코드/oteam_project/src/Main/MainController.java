package Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;


/*코드 작성자 : 김대영*/

public class MainController implements Initializable {

	@FXML
	private ToggleGroup toggleGroup;

	@FXML
	private ToggleButton householdButton;

	@FXML
	private ToggleButton chartButton;

	@FXML
	private ToggleButton accountButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	void selectToggleButton(ActionEvent event) {
		
		// 가계부(household) 버튼이 선택되었을 때 실행할 동작
		if (householdButton.isSelected()) {
			
			Stage primaryStage = new Stage();
			Stage stage = (Stage) householdButton.getScene().getWindow();
			Parent root;
			
			try {
				root = FXMLLoader.load(getClass().getResource("/household/HouseholdPage.fxml"));
				Scene scene = new Scene(root);
				// 실행 윈도우 화면의 이름
				primaryStage.setTitle("Household_Page");
				primaryStage.setScene(scene);
				primaryStage.show();
				stage.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		  // 통계(chart) 버튼이 선택되었을 때 실행할 동작 
		} else if (chartButton.isSelected()) {
			
			Stage primaryStage = new Stage();
			Stage stage = (Stage) chartButton.getScene().getWindow();
			Parent root;
			
			try {
				root = FXMLLoader.load(getClass().getResource("/chart/ChartPage.fxml"));
				Scene scene = new Scene(root);
				// 실행 윈도우 화면의 이름
				primaryStage.setTitle("Chart_Page");
				primaryStage.setScene(scene);
				primaryStage.show();
				stage.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		  
		  // 계정관리(account) 버튼이 선택되었을 때 실행할 동작	
		} else if (accountButton.isSelected()) {
			
			Stage primaryStage = new Stage();
			Stage stage = (Stage) accountButton.getScene().getWindow();
			Parent root;
			
			try {
				root = FXMLLoader.load(getClass().getResource("/account/AccountPage.fxml"));
				Scene scene = new Scene(root);
				// 실행 윈도우 화면의 이름
				primaryStage.setTitle("Account_Page");
				primaryStage.setScene(scene);
				primaryStage.show();
				stage.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}		

}
