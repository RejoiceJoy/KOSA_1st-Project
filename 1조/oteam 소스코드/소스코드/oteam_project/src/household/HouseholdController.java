package household;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


/*코드 작성자 : 김준영*/
//HouseHoldController.fxml을 Controller할 클래스(fx:controller="household.HouseHoldController")
public class HouseholdController implements Initializable  {
	
	//fxml의 fx:id 속성과 어노테이션 컨트롤 주입 > fx:id 속성값과 필드명은 동일 해야함
	@FXML private ToggleGroup toggleGroup;
	@FXML private ToggleButton household_button;   
	@FXML private ToggleButton stat_button;
	@FXML private ToggleButton account_button;
	@FXML private Button insertmenu_button;
	@FXML private Button delete_button;
	@FXML private DatePicker start_date;   
	@FXML private DatePicker end_date;
	@FXML private TableView<HouseholdDataProperty> table_view;
	@FXML private TableColumn<HouseholdDataProperty, String> type_column;  
	@FXML private TableColumn<HouseholdDataProperty, String> category_column;
	@FXML private TableColumn<HouseholdDataProperty, Date> date_column;
	@FXML private TableColumn<HouseholdDataProperty, Integer> money_column;
	@FXML private TableColumn<HouseholdDataProperty, String> memo_column;
	
	
	//select----------------------------------------------------------------------
	//처음 페이지가 시잘될 때 실행됨
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//tableView의 열의 데이터(Cell) 타입을 정해줌
		type_column.setCellValueFactory(cellData -> cellData.getValue().Household_TypeProperty());
		category_column.setCellValueFactory(cellData -> cellData.getValue().Household_CategoryProperty());
		date_column.setCellValueFactory(cellData -> cellData.getValue().Household_DateProperty());
		money_column.setCellValueFactory(cellData -> cellData.getValue().Household_MoneyProperty().asObject());
		memo_column.setCellValueFactory(cellData -> cellData.getValue().Household_MemoProperty());
		
		//DB데이터 값을 받을 javaFX의 콜렉션타입의 변수 선언
		ObservableList<HouseholdDataProperty> sHH = null;
		try {
			//한달전~현재의 가계부내역을 미리 보여줌
			sHH = HouseholdDAO.searchHousehold(LocalDate.now().minusMonths(1), LocalDate.now());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		//tableView에 출력함
		table_view.setItems(sHH);
		//사용자가 볼수있게 DatePicker에 나타냄
		start_date.setValue(LocalDate.now().minusMonths(1));
		end_date.setValue(LocalDate.now());
	}//initialize
	
	
	//fxml의 fx:id를 읽어 필드 생성해줌, 새로운 데이터 입력 이벤트핸들러를 @통해 생성,등록
	//Search 버튼 클릭 이벤트 처리 메소드 정의
	@FXML   
	private void search_act(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
		//사용자 정의 DatePicker에 대한 가계부 DB의 정보를 가져옴, DB데이터 값을 받을 javaFX의 콜렉션
		ObservableList<HouseholdDataProperty> sHH = HouseholdDAO.searchHousehold(start_date.getValue(),end_date.getValue());
		//tableView에 출력함
		table_view.setItems(sHH);
	}//search_act
	
	
	
	//insert, delete---------------------------------------------------------------
	//입력 클릭시 입력창을 띄움
	@FXML
	private void insertmenu_act(ActionEvent actionEvent) throws  ClassNotFoundException, SQLException {
		
		//새로운 스테이지 생성함
		Stage primaryStage = new Stage();
		Parent root;
		try {
			//FXMLLoader로 입력창을 로드함
			root = FXMLLoader.load(getClass().getResource("householdinsert.fxml"));
			//Scene에 담음
			Scene scene = new Scene(root);
			//Stage에 올림
			primaryStage.setTitle("가계부 입력");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
		
	//tableView의 행을 클릭이벤트 발생시 해당 데이터를 가져옴
	@FXML
	private HouseholdDataProperty click_tableview(MouseEvent event) {
		//클릭된 행을 HouseholdDataProperty클래스의 필드에 담음
		HouseholdDataProperty click_tv = table_view.getSelectionModel().getSelectedItem();
		return click_tv;
	}
	
	
	//클릭된 행을 삭제 메소드
	@FXML
	private void delete_act(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
		try {
			MouseEvent event = null;
			//클릭으로 얻은 행의 데이터를 삭제 메소드로 보냄
			HouseholdDAO.deleteHousehold(click_tableview(event));
			//삭제된 후의 tableView 출력을 다시함
			ObservableList<HouseholdDataProperty> sHH = HouseholdDAO.searchHousehold(start_date.getValue(),end_date.getValue());
			table_view.setItems(sHH);
		//행을 선택하지 않고 삭제 클릭시 ERROR 띄움
		} catch (NullPointerException e) {
			Alert fail = new Alert(AlertType.ERROR);
			fail.setHeaderText("올바르지 않은 형식입니다.");
			fail.setContentText("지울 데이터를 테이블에서 선택하십시오");
			fail.showAndWait();
			throw e;
		}
	}

	
	
	//아래 메뉴버튼-------------------------------------------------------------------
	//household_menu 클릭시 household_menu창을 띄움
	@FXML
	private void household_menu(ActionEvent actionEvent) {
		//JavaFX 종료> X버튼, Stage close()(스테이지), 메소드 Platform.exit()(윈도우창), System.exit()(시스템)
		Stage stage = (Stage) household_button.getScene().getWindow();
		Platform.runLater(() -> {
			stage.close();
		});
		
		//새로운 스테이지 생성함
		Stage primaryStage = new Stage();
		Parent root;
		try {
			//FXMLLoader로 입력창을 로드함
			root = FXMLLoader.load(getClass().getResource("HouseholdPage.fxml"));
			//Scene에 담음
			Scene scene = new Scene(root);
			//Stage에 올림
			primaryStage.setTitle("나의 가계부");
			primaryStage.setScene(scene);
			primaryStage.show();
			stage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//stats_menu 클릭시 stats_menu창을 띄움 //위와 동일
	@FXML
	private void stats_menu(ActionEvent actionEvent) {
		Stage stage = (Stage) stat_button.getScene().getWindow();
		Platform.runLater(() -> {
			stage.close();
		});
		
		Stage primaryStage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/chart/ChartPage.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("나의 가계부");
			primaryStage.setScene(scene);
			primaryStage.show();
			stage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//account_menu 클릭시 account_menu창을 띄움 //위와 동일
	@FXML
	private void account_menu(ActionEvent actionEvent) {
		Stage stage = (Stage) stat_button.getScene().getWindow();
		Platform.runLater(() -> {
			stage.close();
		});
		
		Stage primaryStage = new Stage();
		Parent root;
		try {
		root = FXMLLoader.load(getClass().getResource("/account/AccountPage.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("나의 가계부");
			primaryStage.setScene(scene);
			primaryStage.show();
			stage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}