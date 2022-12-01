package chart;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import application.DBConnection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import login.loginuserAct;
import javafx.collections.FXCollections;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

/*코드 작성자: 김종원*/
public class ChartController implements Initializable {

	//자바에서 사용할 FX기능들에 이름 속성(fx:id)을 붙여 변수로 만들기
	@FXML
	private ToggleGroup toggleGroup;
	@FXML
	private ToggleButton householdButton;
	@FXML
	private ToggleButton chartButton;
	@FXML
	private ToggleButton accountButton;
	@FXML
	private PieChart pieChart;
	@FXML
	private AreaChart areaChart;
	@FXML
	private DatePicker start_date;
	@FXML
	private DatePicker end_date;
	@FXML
	private	Label label1;
	@FXML
	private	Label label2;
	@FXML
	private	Label label3;
	@FXML
	private	Label label4;
	@FXML
	private	Label label5;
	@FXML
	private	Label label6;
	@FXML
	private	Label label7;
	@FXML
	private	Label label8;
	@FXML
	private	Label label9;
	@FXML
	private	Label label11;
	@FXML
	private	Label label21;
	@FXML
	private	Label label31;
	@FXML
	private	Label label41;
	@FXML
	private	Label label51;
	@FXML
	private	Label label61;
	@FXML
	private	Label label71;
	@FXML
	private	Label Mlabel0;
	@FXML
	private	Label Mlabel1;
	@FXML
	private	Label Mlabel2;
	@FXML
	private	Label Mlabel3;
	@FXML
	private	Label Mlabel4;
	@FXML
	private	Label Mlabel5;
	
	//DB와 연결하기 위한 변수 설정
	Connection conn = null;
	//SQL문을 처리하기 위한 Statement 변수 설정
	Statement stmt = null;
	
	//PieChart에 값을 할당하기 위해 결과값을 담아 둘 ResultSet 변수 설정
	ResultSet rs1 = null;
	ResultSet rs2 = null;
	ResultSet rs3 = null;
	ResultSet rs4 = null;
	ResultSet rs5 = null;
	ResultSet rs6 = null;
	ResultSet rs7 = null;
	ResultSet rs8 = null;
	ResultSet rs9 = null;

	
	//AreaChart에 값을 할당하기 위해 결과값을 담아 둘 ResultSet 변수 설정
	ResultSet rss1 = null;
	ResultSet rss2 = null;
	ResultSet rss3 = null;
	ResultSet rss4 = null;
	ResultSet rss5 = null;
	ResultSet rss6 = null;
	ResultSet rss7 = null;
	


	//기본 화면 메서드
	@Override	
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			
			int lMember_code = loginuserAct.whoIsMember();		//로그인한 사용자를 식별하기 위해 메서드 호출
			conn = DBConnection.getConnection();				//DB 연결
			System.out.println("DB연결 성공");
						
			start_date.setValue(LocalDate.now().minusDays(30));			//시작 날짜 입력란에 기본값 설정 -> 현재날짜 - 30일
			end_date.setValue(LocalDate.now());							//마지막 날짜 입력란에 기본값 설정 -> 현재날짜
			
//----------------------------------------------------PieChart 시작(기본)-----------------------------------------------------------	
			
			//PieChart에 넣을 값에 대한 SQL문 작성
			//SQL문 --> "로그인 사용자의 최근 30일 동안 각 카테고리에서의 지출 합을 구해라"
			stmt = conn.createStatement();						//데이터베이스로 SQL 문을 보내기 위한 SQLServerStatement 개체 생성
			rs1 = stmt.executeQuery("Select sum(Household_Money) from household"
					+ " where Member_Code = " + lMember_code + " and HouseHold_Category = '패션/미용'"
					+ " and (household_date between sysdate-30 and sysdate)");
			
			stmt = conn.createStatement();
			rs2 = stmt.executeQuery("Select sum(Household_Money) from household"
					+ " where Member_Code = " + lMember_code + " and HouseHold_Category = '생활비'"
					+ " and (household_date between sysdate-30 and sysdate)");
			
			stmt = conn.createStatement();
			rs3 = stmt.executeQuery("Select sum(Household_Money) from household"
					+ " where Member_Code = " + lMember_code + " and HouseHold_Category = '공과금'"
					+ " and (household_date between sysdate-30 and sysdate)");
			
			stmt = conn.createStatement();
			rs4 = stmt.executeQuery("Select sum(Household_Money) from household"
					+ " where Member_Code = " + lMember_code + " and HouseHold_Category = '식비'"
					+ " and (household_date between sysdate-30 and sysdate)");
			
			stmt = conn.createStatement();
			rs5 = stmt.executeQuery("Select sum(Household_Money) from household"
					+ " where Member_Code = " + lMember_code + " and HouseHold_Category = '교통비'"
					+ " and (household_date between sysdate-30 and sysdate)");
			
			stmt = conn.createStatement();
			rs6 = stmt.executeQuery("Select sum(Household_Money) from household"
					+ " where Member_Code = " + lMember_code + " and HouseHold_Category = '여가생활'"
					+ " and (household_date between sysdate-30 and sysdate)");
			
			stmt = conn.createStatement();
			rs7 = stmt.executeQuery("Select sum(Household_Money) from household"
					+ " where Member_Code = " + lMember_code + " and HouseHold_Category = '교육'"
					+ " and (household_date between sysdate-30 and sysdate)");
			
			stmt = conn.createStatement();
			rs8 = stmt.executeQuery("Select sum(Household_Money) from household"
					+ " where Member_Code = " + lMember_code + " and HouseHold_Category = '기타'"
					+ " and (household_date between sysdate-30 and sysdate)");
			
			//SQL문 --> "로그인 사용자의 최근 30일 동안 지출 합을 구해라"
			stmt = conn.createStatement();
			rs9 = stmt.executeQuery("Select sum(Household_Money) from household\r\n"
					+ " where Member_Code = " + lMember_code + " and household_type = '지출' "
					+ " and (household_date between sysdate-30 and sysdate)");
			
			//값이 있는 곳으로 이동시키기
			rs1.next();
			rs2.next();
			rs3.next();
			rs4.next();
			rs5.next();
			rs6.next();
			rs7.next();
			rs8.next();
			rs9.next();
			
				//항목과 값을 넣으면 PieChart로 구현됨
				pieChart.setData(FXCollections.observableArrayList(
						new PieChart.Data("패션/미용", rs1.getInt(1)),		
						new PieChart.Data("생활비", rs2.getInt(1)),
						new PieChart.Data("공과금", rs3.getInt(1)),
						new PieChart.Data("식비", rs4.getInt(1)),
						new PieChart.Data("교통비", rs5.getInt(1)),
						new PieChart.Data("여가생활", rs6.getInt(1)),
						new PieChart.Data("교육", rs7.getInt(1)),
						new PieChart.Data("기타", rs8.getInt(1))
						
						));
				
				//PieChart항목별 값 나타내기
				label1.setText(rs9.getInt(1) + " 원");
				label2.setText(rs3.getInt(1) + " 원");
				label3.setText(rs4.getInt(1) + " 원");
				label4.setText(rs6.getInt(1) + " 원");
				label5.setText(rs5.getInt(1) + " 원");
				label6.setText(rs2.getInt(1) + " 원");
				label7.setText(rs1.getInt(1) + " 원");
				label8.setText(rs7.getInt(1) + " 원");
				label9.setText(rs8.getInt(1) + " 원");
				
//----------------------------------------------------AreaChart 시작-----------------------------------------------------------
					
						//SQL문 실행에 대한 결과값을 ResultSet 객체에 담기
						//SQL문 --> "로그인 사용자의 월별 지출액 합을 구해라"
				
						stmt = conn.createStatement();
						rss1 = stmt.executeQuery("Select sum(Household_Money) from household\r\n" 
								+ "where Member_Code = " + lMember_code + " and household_type = '지출' " 
								+ " and (household_date between trunc(add_months(sysdate,-5), 'month') and (trunc((add_months(sysdate,-4)), 'month')-1))");
						
						stmt = conn.createStatement();
						rss2 = stmt.executeQuery("Select sum(Household_Money) from household\r\n" 
								+ "where Member_Code = " + lMember_code + " and household_type = '지출' " 
								+ " and (household_date between trunc(add_months(sysdate,-4), 'month') and (trunc((add_months(sysdate,-3)), 'month')-1))");
						
						stmt = conn.createStatement();
						rss3 = stmt.executeQuery("Select sum(Household_Money) from household\r\n" 
								+ "where Member_Code = " + lMember_code + " and household_type = '지출' " 
								+ " and (household_date between trunc(add_months(sysdate,-3), 'month') and (trunc((add_months(sysdate,-2)), 'month')-1))");
						
						stmt = conn.createStatement();
						rss4 = stmt.executeQuery("Select sum(Household_Money) from household\r\n"
								+ "where Member_Code = " + lMember_code + " and household_type = '지출' " 
								+ " and (household_date between trunc(add_months(sysdate,-2), 'month') and (trunc((add_months(sysdate,-1)), 'month')-1))");
						
						stmt = conn.createStatement();
						rss5 = stmt.executeQuery("Select sum(Household_Money) from household\r\n"
								+ "where Member_Code = " + lMember_code + " and household_type = '지출' " 
								+ " and (household_date between trunc(add_months(sysdate,-1), 'month') and (trunc(sysdate, 'month')-1))");
						
						stmt = conn.createStatement();
						rss6 = stmt.executeQuery("Select sum(Household_Money) from household\r\n"
								+ "where Member_Code = " + lMember_code + " and household_type = '지출' " 
								+ " and (household_date between trunc(sysdate, 'month') and (trunc((add_months(sysdate,+1)), 'month')-1)) ");

						
						//SQL문 --> "로그인 사용자의 6개월간 지출액 합을 구해라"
						stmt = conn.createStatement();
						rss7 = stmt.executeQuery("Select sum(Household_Money) from household\r\n"
								+ " where Member_Code = " + lMember_code + " and household_type = '지출' "
								+ " and (household_date between trunc(add_months(sysdate,-5), 'month') and sysdate)");
						
						//값이 있는 곳으로 이동시키기
						rss1.next();
						rss2.next();
						rss3.next();
						rss4.next();
						rss5.next();
						rss6.next();
						rss7.next();
						
						//XY유형에 데이터 넣을 객체 생성
						XYChart.Series series = new XYChart.Series();
						
						
						//AreaChart X축에 표시될 월 ( 포맷 변환 : 월 )
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월");
						String area_Month0 = LocalDate.now().format(formatter);
						String area_Month1 = LocalDate.now().minusMonths(1).format(formatter);
						String area_Month2 = LocalDate.now().minusMonths(2).format(formatter);
						String area_Month3 = LocalDate.now().minusMonths(3).format(formatter);
						String area_Month4 = LocalDate.now().minusMonths(4).format(formatter);
						String area_Month5 = LocalDate.now().minusMonths(5).format(formatter);
						
						//객체에 데이터 값 넣어두기
						series.setData(FXCollections.observableArrayList(
								new XYChart.Data(area_Month5, rss1.getInt(1)),
								new XYChart.Data(area_Month4, rss2.getInt(1)),
								new XYChart.Data(area_Month3, rss3.getInt(1)),
								new XYChart.Data(area_Month2, rss4.getInt(1)),
								new XYChart.Data(area_Month1, rss5.getInt(1)),
								new XYChart.Data(area_Month0, rss6.getInt(1))
							));
						
							//AreaChart에 데이터 값 삽입
							areaChart.getData().add(series);
						
						//AreaChart 항목별 이름 지정 ( 포맷 변환 : 년 월 )
						DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("YY년 MM월");
						Mlabel0.setText(LocalDate.now().format(formatter2) + "");
						Mlabel1.setText(LocalDate.now().minusMonths(1).format(formatter2) + "");
						Mlabel2.setText(LocalDate.now().minusMonths(2).format(formatter2) + "");
						Mlabel3.setText(LocalDate.now().minusMonths(3).format(formatter2) + "");
						Mlabel4.setText(LocalDate.now().minusMonths(4).format(formatter2) + "");
						Mlabel5.setText(LocalDate.now().minusMonths(5).format(formatter2) + "");
							
							
						
						//AreaChart 항목별 값 나타내기
						label11.setText(rss7.getInt(1) + " 원");
						label21.setText(rss1.getInt(1) + " 원");
						label31.setText(rss2.getInt(1) + " 원");
						label41.setText(rss3.getInt(1) + " 원");
						label51.setText(rss4.getInt(1) + " 원");
						label61.setText(rss5.getInt(1) + " 원");
						label71.setText(rss6.getInt(1) + " 원");
					
				} catch (SQLException e) {
					System.out.println("DB연결 실패하거나, SQL문이 틀렸습니다.");
					System.out.print("사유 : " + e.getMessage());
				}catch (Exception e) {
					e.printStackTrace();
				}

	}
	
//------------------------------------------------------------------------------------------------------------------------------------
	
	
	//조회버튼 클릭 시 작동하는 메서드
	public void search_act(ActionEvent actionEvent) {
		
				try {
							conn = DBConnection.getConnection();		//DB 연결
							System.out.println("DB연결 성공");
							
							int lMember_code = loginuserAct.whoIsMember();		//로그인한 사용자의 코드를 담은 변수 설정
							LocalDate startDate = start_date.getValue();		//시작 날짜 입력 값을 담은 변수 설정
							LocalDate endDate = end_date.getValue();			//마지막 날짜 입력 값을 담은 변수 설정
							
//----------------------------------------------------PieChart 시작(조회시 출력되는)-----------------------------------------------------------	
							
							//PieChart에 넣을 값에 대한 SQL문 작성
							//SQL문 --> "로그인 사용자가 지정한 날짜 구간의 카테고리별 합을 구해라"
							stmt = conn.createStatement();						//데이터베이스로 SQL 문을 보내기 위한 SQLServerStatement 개체 생성
							rs1 = stmt.executeQuery("Select sum(Household_Money) from household"
									+ " where Member_Code = " + lMember_code + " and HouseHold_Category = '패션/미용'"
									+ " and (household_date between '" + startDate + "' and '" + endDate + "')");
							
							stmt = conn.createStatement();
							rs2 = stmt.executeQuery("Select sum(Household_Money) from household"
									+ " where Member_Code = " + lMember_code + " and HouseHold_Category = '생활비'"
									+ " and (household_date between '" + startDate + "' and '" + endDate + "')");
							
							stmt = conn.createStatement();
							rs3 = stmt.executeQuery("Select sum(Household_Money) from household"
									+ " where Member_Code = " + lMember_code + " and HouseHold_Category = '공과금'"
									+ " and (household_date between '" + startDate + "' and '" + endDate + "')");
							
							stmt = conn.createStatement();
							rs4 = stmt.executeQuery("Select sum(Household_Money) from household"
									+ " where Member_Code = " + lMember_code + " and HouseHold_Category = '식비'"
									+ " and (household_date between '" + startDate + "' and '" + endDate + "')");
							
							stmt = conn.createStatement();
							rs5 = stmt.executeQuery("Select sum(Household_Money) from household"
									+ " where Member_Code = " + lMember_code + " and HouseHold_Category = '교통비'"
									+ " and (household_date between '" + startDate + "' and '" + endDate + "')");
							
							stmt = conn.createStatement();
							rs6 = stmt.executeQuery("Select sum(Household_Money) from household"
									+ " where Member_Code = " + lMember_code + " and HouseHold_Category = '여가생활'"
									+ " and (household_date between '" + startDate + "' and '" + endDate + "')");
							
							stmt = conn.createStatement();
							rs7 = stmt.executeQuery("Select sum(Household_Money) from household"
									+ " where Member_Code = " + lMember_code + " and HouseHold_Category = '교육'"
									+ " and (household_date between '" + startDate + "' and '" + endDate + "')");
							
							stmt = conn.createStatement();
							rs8 = stmt.executeQuery("Select sum(Household_Money) from household"
									+ " where Member_Code = " + lMember_code + " and HouseHold_Category = '기타'"
									+ " and (household_date between '" + startDate + "' and '" + endDate + "')");
							
							//SQL문 --> "로그인 사용자가 지정한 날짜 구간의 지출 합을 구해라"
							stmt = conn.createStatement();
							rs9 = stmt.executeQuery("Select sum(Household_Money) from household\r\n"
									+ " where Member_Code = " + lMember_code + " and household_type = '지출' "
									+ " and (household_date between '" + startDate + "' and '" + endDate + "')");
							
							//값이 있는 곳으로 이동시키기
							rs1.next();
							rs2.next();
							rs3.next();
							rs4.next();
							rs5.next();
							rs6.next();
							rs7.next();
							rs8.next();
							rs9.next();
							
								//PieChart
								pieChart.setData(FXCollections.observableArrayList(
										new PieChart.Data("패션/미용", rs1.getInt(1)),		
										new PieChart.Data("생활비", rs2.getInt(1)),
										new PieChart.Data("공과금", rs3.getInt(1)),
										new PieChart.Data("식비", rs4.getInt(1)),
										new PieChart.Data("교통비", rs5.getInt(1)),
										new PieChart.Data("여가생활", rs6.getInt(1)),
										new PieChart.Data("교육", rs7.getInt(1)),
										new PieChart.Data("기타", rs8.getInt(1))
										));
										
								//PieChart항목별 값 나타내기
								label1.setText(rs9.getInt(1) + " 원");
								label2.setText(rs3.getInt(1) + " 원");
								label3.setText(rs4.getInt(1) + " 원");
								label4.setText(rs6.getInt(1) + " 원");
								label5.setText(rs5.getInt(1) + " 원");
								label6.setText(rs2.getInt(1) + " 원");
								label7.setText(rs1.getInt(1) + " 원");
								label8.setText(rs7.getInt(1) + " 원");
								label9.setText(rs8.getInt(1) + " 원");
								
							
						} catch (SQLException e) {
							System.out.println("DB연결 실패하거나, SQL문이 틀렸습니다.");
							System.out.print("사유 : " + e.getMessage());
						}catch (Exception e) {
							e.printStackTrace();
						}
		
	}
//------------------------------------------------------------------------------------------------------------------------------------
	


//---------------------------------------------------화면 하단 메뉴버튼 클릭 시 작동에 대한 메서드----------------------------------------------------------
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
				root = FXMLLoader.load(getClass().getResource("ChartPage.fxml"));
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
	}// end toggle

}