//김미리, 김학수, 배재연 페어 프로그래밍_pair programming

package main;

import java.io.IOException;//FXMLLoader클래스의 정적 메소드 load() 메소드에 필수적으로 필요한 예외 java.io.IOException import
import java.net.URL;//Initializable을 상속한 컨트롤러 클래스에서 오버라이딩한 initialize 메소드의 매개값으로 넣기 위해 URL import
import java.util.ArrayList;//한번에 주문할 때 여러가지 주문 메뉴를 하나의 배열에 넣기 위해 ArrayList를 사용하기 위해 java.util.ArrayList import
import java.util.ResourceBundle;//Initializable을 상속한 컨트롤러 클래스에서 오버라이딩한 initialize 메소드의 매개값으로 넣기 위해 ResourceBundle import

import data_model.KioskDAO;//DB에서 데이터를 가져오거나 DB에 데이터를 집어넣기 위해서 data_model.KioskDAO를 import
import data_model.KioskDTO;//DB에서 데이터를 가져오거나 DB에 데이터를 집어넣는 과정의 중간에 데이터를 담아놓고 일괄처리하기 위해 data_model.KioskDTO를 import
import javafx.application.Platform;/*작업 스레드가 직접 UI를 변경할 수 없기 때문에 UI 변경이 필요할 경우, 작업 스레드는 UI 변경 코드를 Runnable로 생성하고, 
이것을 매개값으로 해서 Platform의 정적 메소드인 runLater()를 호출할 수 있음.*/
import javafx.event.ActionEvent;//FXML 컨트롤인 버튼을 클릭시 발생한 ActionEvent를 매개값으로 받아 handler 메소드로 처리하기 위한 javafx.event.ActionEvent import
import javafx.fxml.FXML;//FXML 컨트롤 객체 주입을 위해 javafx.fxml.FXML import
import javafx.fxml.FXMLLoader;//주문버튼 클릭 시 포인트 적립 여부를 확인하는 커스텀 다이얼로그인 CheckController.fxml Stage로 직접 띄우기 위해 javafx.fxml.FXMLLoader import
import javafx.fxml.Initializable;//컨트롤러는 Initializable 인터페이스를 반드시 상속받아야하므로 javafx.fxml.Initializable import
import javafx.scene.Scene;//Stage에 매개변수로 심을 Scene 객체를 생성하기 위해 javafx.scene.Scene을 import
import javafx.scene.control.Button;//Root.fxml로부터 주입돼 객체 생성된 버튼 컨트롤을 조작하기 위해 javafx.scene.control.Button import
import javafx.scene.control.Label;//Root.fxml로부터 주입돼 객체 생성된 라벨 컨트롤을 조작하기 위해 javafx.scene.control.Label import
import javafx.scene.control.TextField;//Root.fxml로부터 주입돼 객체 생성된 텍스트필드 컨트롤을 조작하기 위해 javafx.scene.control.TextField import
import javafx.scene.layout.AnchorPane;//커스텀 다이얼로그인 CheckController.fxml의 컨테이너를 AnchorPane으로 삼기 위해서 javafx.scene.layout.AnchorPane import
import javafx.stage.Modality;//주문버튼 클릭 시 포인트 적립 여부를 확인하는 커스텀 다이얼로그인 CheckController.fxml을 Stage로 직접 띄우기 위해 javafx.stage.Modality import
import javafx.stage.Stage;//주문버튼 클릭 시 포인트 적립 여부를 확인하는 커스텀 다이얼로그인 CheckController.fxml을 Stage로 직접 띄우기 위해 javafx.stage.Stage import
import javafx.stage.StageStyle;/*주문버튼 클릭 시 포인트 적립 여부를 확인하는 커스텀 다이얼로그인 CheckController.fxml Stage로 직접 띄우기 위해 javafx.stage.StageStyle import
Stage의 생성자 매개값에는 윈도우 스타일을 결정짓는 StageStyle 열거 상수가 옴. 본 코드에서 쓴 StageStyle 열거상수 UTILITY는 배경이 흰 색이고, 제목줄에 타이틀, 종료 버튼만 있음.*/

public class RootController implements Initializable {//컨트롤러 클래스는 Initializable 인터페이스를 반드시 상속받아야함.
	@FXML private TextField americano;//아메리카노 개수 입력 텍스트 필드 객체 주입
	@FXML private TextField cafelatte;//카페라떼 개수 입력 텍스트 필드 객체 주입
	@FXML private TextField espresso;//에스프레소 개수 입력 텍스트 필드 객체 주입
	@FXML private TextField milk;//우유 개수 입력 텍스트 필드 객체 주입
	@FXML private TextField bread;//모닝빵 개수 입력 텍스트 필드 객체 주입
	@FXML private TextField cake;//조각케잌 개수 입력 텍스트 필드 객체 주입
	@FXML private TextField cookie;//쿠키 개수 입력 텍스트 필드 객체 주입
	@FXML private TextField macaroon;//마카롱 개수 입력 텍스트 필드 객체 주입
	@FXML private TextField icetea;//아이스티 개수 입력 텍스트 필드 객체 주입
	@FXML private TextField chocolatelatte;//초코라떼 개수 입력 텍스트 필드 객체 주입
	@FXML private Label orderlist;//주문내역 보여주는 라벨 객체 주입
	@FXML private Button order;//주문하기 버튼 객체 주입
	@FXML private Button exit;//종료하기 버튼 객체 주입

	int americanoCnt = 0;//아메리카노 주문 수량 입력할 필드 선언
	int cafelatteCnt = 0;//카페라테 주문 수량 입력할 필드 선언
	int espressoCnt = 0;//에스프레소 주문 수량 입력할 필드 선언
	int milkCnt = 0;//우유 주문 수량 입력할 필드 선언
	int breadCnt = 0;//모닝빵 주문 수량 입력할 필드 선언
	int cakeCnt = 0;//조각케잌 주문 수량 입력할 필드 선언
	int cookieCnt = 0;//쿠키 주문 수량 입력할 필드 선언
	int macaroonCnt = 0;//마카롱 주문 수량 입력할 필드 선언
	int iceteaCnt = 0;//아이스티 주문 수량 입력할 필드 선언
	int chocolatelatteCnt = 0;//초코라떼 주문 수량 입력할 필드 선언
	
	static int total = 0;/*주문액의 총 1%인 포인트값을 저장할 변수 total 선언. RootController뿐만 아니라 MemberController에서 포인트 적립 시에도 사용되는 필드값이므로 어디서나 값이 유지되도록 static으로 변수 선언.
	만약 이 값이 0이면 주문을 하지 않은 상태로 주문하기 버튼이나 포인트 적립하기 버튼을 누른 것이므로 경고문구를 띄울 수 있는 등 다양하게 이용가능.*/
	
	public void handleOrderAction(ActionEvent event) {//주문하기 버튼 클릭 시 발생하는 이벤트 조작 메소드

		try {
			americanoCnt = Integer.parseInt(americano.getText());//아메리카노 주문 개수로 입력한 String을 int값으로 변환해서 americanoCnt 변수에 대입.
			System.out.println("아메리카노 주문 개수 : " + americanoCnt);//주문이 잘 들어갔는지 콘솔창으로 확인하기 위해 임시 출력.
			// 아메리카노 주문 수량 입력
			cafelatteCnt = Integer.parseInt(cafelatte.getText());//카페라떼 주문 개수로 입력한 String을 int값으로 변환해서 cafelatteCnt 변수에 대입.
			System.out.println("카페라떼 주문 개수 : " + cafelatteCnt);//주문이 잘 들어갔는지 콘솔창으로 확인하기 위해 임시 출력.
			// 카페라떼 주문 수량 입력
			espressoCnt = Integer.parseInt(espresso.getText());//에스프레소 주문 개수로 입력한 String을 int값으로 변환해서 espressoCnt 변수에 대입.
			System.out.println("에스프레소 주문 개수 : " + espressoCnt);//주문이 잘 들어갔는지 콘솔창으로 확인하기 위해 임시 출력.
			// 에스프레소 주문 수량 입력
			milkCnt = Integer.parseInt(milk.getText());//우유 주문 개수로 입력한 String을 int값으로 변환해서 milkCnt 변수에 대입.
			System.out.println("우유 주문 개수 : " + milkCnt);//주문이 잘 들어갔는지 콘솔창으로 확인하기 위해 임시 출력.
			// 우유 주문 수량 입력
			breadCnt = Integer.parseInt(bread.getText());//모닝빵 주문 개수로 입력한 String을 int값으로 변환해서 breadCnt 변수에 대입.
			System.out.println("모닝빵 주문 개수 : " + breadCnt);//주문이 잘 들어갔는지 콘솔창으로 확인하기 위해 임시 출력.
			// 모닝빵 주문 수량 입력
			cakeCnt = Integer.parseInt(cake.getText());//조각케잌 주문 개수로 입력한 String을 int값으로 변환해서 cakeCnt 변수에 대입.
			System.out.println("조각케잌 주문 개수 : " + cakeCnt);//주문이 잘 들어갔는지 콘솔창으로 확인하기 위해 임시 출력.
			// 조각케잌 주문 수량 입력
			cookieCnt = Integer.parseInt(cookie.getText());//쿠키 주문 개수로 입력한 String을 int값으로 변환해서 cookieCnt 변수에 대입.
			System.out.println("쿠키 주문 개수 : " + cookieCnt);//주문이 잘 들어갔는지 콘솔창으로 확인하기 위해 임시 출력.
			// 쿠키 주문 수량 입력
			macaroonCnt = Integer.parseInt(macaroon.getText());//마카롱 주문 개수로 입력한 String을 int값으로 변환해서 macaroonCnt 변수에 대입.
			System.out.println("마카롱 주문 개수 : " + macaroonCnt);//주문이 잘 들어갔는지 콘솔창으로 확인하기 위해 임시 출력.
			// 마카롱 주문 수량 입력
			iceteaCnt = Integer.parseInt(icetea.getText());//아이스티 주문 개수로 입력한 String을 int값으로 변환해서 iceteaCnt 변수에 대입.
			System.out.println("아이스티 주문 개수 : " + iceteaCnt);//주문이 잘 들어갔는지 콘솔창으로 확인하기 위해 임시 출력.
			// 아이스티 주문 수량 입력
			chocolatelatteCnt = Integer.parseInt(chocolatelatte.getText());//초코라떼 주문 개수로 입력한 String을 int값으로 변환해서 chocolatelatteCnt 변수에 대입.
			System.out.println("초코라떼 주문 개수 : " + chocolatelatteCnt);//주문이 잘 들어갔는지 콘솔창으로 확인하기 위해 임시 출력.
			// 초코라떼 주문 수량 입력
		}
		catch(NumberFormatException e) {//만약에 주문 수량에 숫자가 아닌 문자를 입력했을 경우 발생하는 오류 대처
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					orderlist.setText("메뉴별 수량은 숫자값을 입력해주세요.");//경고문구
				}
				
			});
			return;//주문하기 버튼 클릭 이벤트로 발생한 메소드 종료
		}
		catch (Exception e) {//그외 예외 발생시 처리
			e.printStackTrace();
		}
		finally {
		}
		
		total = (int) (0.01*(4000*americanoCnt + 5000*cafelatteCnt + 6000*espressoCnt + 3000*milkCnt + 2000*breadCnt + 6000*cakeCnt + 4000*cookieCnt + 2500*macaroonCnt + 6000*iceteaCnt + 6000*chocolatelatteCnt));
		// 멤버십 포인트 적립 계산(1%)
		
		if(total == 0) {//이 값이 0이면 주문을 하지 않은 상태로 주문하기 버튼을 누른 것이므로 경고문구 띄우기.
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					orderlist.setText("주문할 메뉴를 골라주세요.");//경고문구
				}
				
			});
			return;//주문하기 버튼 클릭 이벤트로 발생한 메소드 종료
		}
		
		
		KioskDAO kioskDAO = new KioskDAO();//각종 데이터를 DB에 넣거나 DB로부터 가져오기 위해 KioskDAO 객체 선언.
		// 새로운 객체 생성
		
		ArrayList<KioskDTO> kioskArrayList = new ArrayList<KioskDTO>();//한번에 주문할 때 여러가지 주문 메뉴를 하나의 배열에 넣기 위해 ArrayList를 사용.
		ArrayList<KioskDTO> stockArrayList = new ArrayList<KioskDTO>();//한번에 주문할 때 여러가지 주문 메뉴에 따른 소진하는 원료들을 하나의 배열에 넣기 위해 ArrayList를 사용.
		
		KioskDTO checkAmericanoStockDTO = new KioskDTO(201);//아메리카노 재고 체크용 DTO 선언
		int americanoAvailable = kioskDAO.checkStock(checkAmericanoStockDTO)/2;//현재 주문 가능한 아메리카노 원두 원료 재고 개수를 kioskDAO 객체의 checkStock() 메소드로 받아와 계산하여 americanoAvailable 변수값에 대입.
		
		if(americanoCnt > americanoAvailable) {//주문수량이 재고수량보다 많으면 
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					orderlist.setText("아메리카노 재고가 " + (americanoCnt-americanoAvailable) + "개 부족합니다.");//경고문구 띄우고
				}
				
			});
			return;//주문하기 버튼 클릭 이벤트로 발생한 메소드 종료
		}
		
		KioskDTO checkCafelatteStockDTO01 = new KioskDTO(201);//카페라떼 재고 체크용 DTO 선언01
		KioskDTO checkCafelatteStockDTO02 = new KioskDTO(202);//카페라떼 재고 체크용 DTO 선언02

		int cafelatteAvailable01 = kioskDAO.checkStock(checkCafelatteStockDTO01)-americanoCnt/2;//현재 주문 가능한 카페라떼 원두 원료 재고 개수를 kioskDAO 객체의 checkStock() 메소드로 받아와 계산하여 cafelatteAvailable01 변수값에 대입.
		int cafelatteAvailable02 = kioskDAO.checkStock(checkCafelatteStockDTO02);//현재 주문 가능한 카페라떼 우유 원료 재고 개수를 kioskDAO 객체의 checkStock() 메소드로 받아와 계산하여 cafelatteAvailable01 변수값에 대입.
		int cafelatteAvailable = cafelatteAvailable01 < cafelatteAvailable02 ? cafelatteAvailable01 : cafelatteAvailable02;//두 재료 중 더 모자란 재고량에 맞춰서 현재 주문 가능한 수량 세팅.

		if(cafelatteCnt > cafelatteAvailable) {//주문수량이 재고수량보다 많으면 
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					orderlist.setText("카페라떼 재고가 " + (cafelatteCnt-cafelatteAvailable) + "개 부족합니다.");//경고문구 띄우고
				}
				
			});
			return;//주문하기 버튼 클릭 이벤트로 발생한 메소드 종료
		}
		
		KioskDTO checkEspressoStockDTO = new KioskDTO(201);//에스프레소 재고 체크용 DTO 선언
		int espressoAvailable = kioskDAO.checkStock(checkEspressoStockDTO)-americanoCnt*2-cafelatteCnt;//현재 주문 가능한 에스프레소 원두 원료 재고 개수를 kioskDAO 객체의 checkStock() 메소드로 받아와 계산하여 espressoAvailable 변수값에 대입.
		
		if(espressoCnt > espressoAvailable) {//주문수량이 재고수량보다 많으면 
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					orderlist.setText("에스프레소 재고가 " + (espressoCnt-espressoAvailable) + "개 부족합니다.");//경고문구 띄우고
				}
				
			});
			return;//주문하기 버튼 클릭 이벤트로 발생한 메소드 종료
		}
		
		KioskDTO checkMilkStockDTO = new KioskDTO(202);//우유 재고 체크용 DTO 선언
		int milkAvailable = kioskDAO.checkStock(checkMilkStockDTO)-cafelatteCnt;//현재 주문 가능한 우유 원료 재고 개수를 kioskDAO 객체의 checkStock() 메소드로 받아와 계산하여 milkAvailable 변수값에 대입.
		
		if(milkCnt > milkAvailable) {//주문수량이 재고수량보다 많으면 
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					orderlist.setText("우유 재고가 " + (milkCnt-milkAvailable) + "개 부족합니다.");//경고문구 띄우고
				}
				
			});
			return;//주문하기 버튼 클릭 이벤트로 발생한 메소드 종료
		}
		
		KioskDTO checkBreadStockDTO = new KioskDTO(203);//모닝빵 재고 체크용 DTO 선언
		int breadAvailable = kioskDAO.checkStock(checkBreadStockDTO);//현재 주문 가능한 모닝빵 재고 개수를 kioskDAO 객체의 checkStock() 메소드로 받아와 계산하여 breadAvailable 변수값에 대입.
		
		if(breadCnt > breadAvailable) {//주문수량이 재고수량보다 많으면 
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					orderlist.setText("모닝빵 재고가 " + (breadCnt-breadAvailable) + "개 부족합니다.");//경고문구 띄우고
				}
				
			});
			return;//주문하기 버튼 클릭 이벤트로 발생한 메소드 종료
		}
		
		KioskDTO checkCakeStockDTO = new KioskDTO(204);//조각케잌 재고 체크용 DTO 선언
		int cakeAvailable = kioskDAO.checkStock(checkCakeStockDTO);//현재 주문 가능한 조각케잌 재고 개수를 kioskDAO 객체의 checkStock() 메소드로 받아와 계산하여 cakeAvailable 변수값에 대입.
		
		if(cakeCnt > cakeAvailable) {//주문수량이 재고수량보다 많으면 
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					orderlist.setText("조각케잌 재고가 " + (cakeCnt-cakeAvailable) + "개 부족합니다.");//경고문구 띄우고
				}
				
			});
			return;//주문하기 버튼 클릭 이벤트로 발생한 메소드 종료
		}
		
		KioskDTO checkCookieStockDTO = new KioskDTO(205);//쿠키 재고 체크용 DTO 선언
		int cookieAvailable = kioskDAO.checkStock(checkCookieStockDTO);//현재 주문 가능한 쿠키 재고 개수를 kioskDAO 객체의 checkStock() 메소드로 받아와 계산하여 cookieAvailable 변수값에 대입.
		
		if(cookieCnt > cookieAvailable) {//주문수량이 재고수량보다 많으면 
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					orderlist.setText("쿠키 재고가 " + (cookieCnt-cookieAvailable) + "개 부족합니다.");//경고문구 띄우고
				}
				
			});
			return;//주문하기 버튼 클릭 이벤트로 발생한 메소드 종료
		}
		
		KioskDTO checkMacaroonStockDTO = new KioskDTO(206);//마카롱 재고 체크용 DTO 선언
		int macaroonAvailable = kioskDAO.checkStock(checkMacaroonStockDTO);//현재 주문 가능한 마카롱 재고 개수를 kioskDAO 객체의 checkStock() 메소드로 받아와 계산하여 macaroonAvailable 변수값에 대입.
		
		if(macaroonCnt > macaroonAvailable) {//주문수량이 재고수량보다 많으면 
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					orderlist.setText("마카롱 재고가 " + (macaroonCnt-macaroonAvailable) + "개 부족합니다.");//경고문구 띄우고
				}
				
			});
			return;//주문하기 버튼 클릭 이벤트로 발생한 메소드 종료
		}
		
		KioskDTO checkIceteaStockDTO = new KioskDTO(206);//아이스티 재고 체크용 DTO 선언
		int iceteaAvailable = kioskDAO.checkStock(checkIceteaStockDTO);//현재 주문 가능한 아이스티 복숭아 시럽 원료 재고 개수를 kioskDAO 객체의 checkStock() 메소드로 받아와 계산하여 iceteaAvailable 변수값에 대입.
		
		if(iceteaCnt > iceteaAvailable) {//주문수량이 재고수량보다 많으면 
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					orderlist.setText("아이스티 재고가 " + (iceteaCnt-iceteaAvailable) + "개 부족합니다.");//경고문구 띄우고
				}
				
			});
			return;//주문하기 버튼 클릭 이벤트로 발생한 메소드 종료
		}
		
		KioskDTO checkChocolatelatteStockDTO01 = new KioskDTO(202);//초코라떼 재고 체크용 DTO 선언01
		KioskDTO checkChocolatelatteStockDTO02 = new KioskDTO(207);//초코라떼 재고 체크용 DTO 선언01

		int chocolatelatteAvailable01 = kioskDAO.checkStock(checkChocolatelatteStockDTO01)-cafelatteCnt-milkCnt;//현재 주문 가능한 초코라떼 우유원료 재고 개수를 kioskDAO 객체의 checkStock() 메소드로 받아와 계산하여 chocolatelatteAvailable01 변수값에 대입.
		int chocolatelatteAvailable02 = kioskDAO.checkStock(checkChocolatelatteStockDTO02);//현재 주문 가능한 초코라떼 초코원료 재고 개수를 kioskDAO 객체의 checkStock() 메소드로 받아와 계산하여 chocolatelatteAvailable02 변수값에 대입.
		int chocolatelatteAvailable = chocolatelatteAvailable01 < chocolatelatteAvailable02 ? chocolatelatteAvailable01 : chocolatelatteAvailable02;//두 재료 중 더 모자란 재고량에 맞춰서 현재 주문 가능한 수량 세팅.

		if(chocolatelatteCnt > chocolatelatteAvailable) {//주문수량이 재고수량보다 많으면 
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					orderlist.setText("초코라떼 재고가 " + (chocolatelatteCnt-chocolatelatteAvailable) + "개 부족합니다.");//경고문구 띄우고
				}
				
			});
			return;//주문하기 버튼 클릭 이벤트로 발생한 메소드 종료
		}
		
		if (americanoCnt > 0) {//아메리카노 주문 수량이 1개 이상이면
			KioskDTO newOrderDTO = new KioskDTO("101", "101", "아메리카노", 4000, americanoCnt);//DB의 order_list table에 insert할 필드값 입력할 DTO 생성 후
			kioskArrayList.add(newOrderDTO);//생성한 DTO를 주문목록 ArrayList에 추가함.
			// 아메리카노 주문 내역 입력
			KioskDTO newStockDTO = new KioskDTO(2*americanoCnt, 201);//DB의 stock table에서 소모해서 update할 필드값 입력할 DTO 생성 후
			stockArrayList.add(newStockDTO);//생성한 DTO를 재고소진 ArrayList에 추가함.
			// 아메리카노 재고 수량 수정 (사용량 만큼 재고에서 차감)
		}
		if (cafelatteCnt > 0) {//카페라떼 주문 수량이 1개 이상이면
			KioskDTO newOrderDTO = new KioskDTO("102", "102", "카페라떼", 5000, cafelatteCnt);//DB의 order_list table에 insert할 필드값 입력할 DTO 생성 후
			kioskArrayList.add(newOrderDTO);//생성한 DTO를 주문목록 ArrayList에 추가함.
			// 카페라떼 주문 내역 입력
			KioskDTO newStockDTO01 = new KioskDTO(1*cafelatteCnt, 201);//DB의 stock table에서 소모해서 update할 필드값 입력할 DTO 생성 후
			KioskDTO newStockDTO02 = new KioskDTO(1*cafelatteCnt, 202);//DB의 stock table에서 소모해서 update할 필드값 입력할 DTO 생성 후
			stockArrayList.add(newStockDTO01);//생성한 DTO를 재고소진 ArrayList에 추가함.
			stockArrayList.add(newStockDTO02);//생성한 DTO를 재고소진 ArrayList에 추가함.
			// 카페라떼 재고 수량 수정 (사용량 만큼 재고에서 차감)
		}
		if (espressoCnt > 0) {//에스프레소 주문 수량이 1개 이상이면
			KioskDTO newOrderDTO = new KioskDTO("103", "103", "에스프레소", 6000, espressoCnt);//DB의 order_list table에 insert할 필드값 입력할 DTO 생성 후
			kioskArrayList.add(newOrderDTO);//생성한 DTO를 주문목록 ArrayList에 추가함.
			// 에스프레소 주문 내역 입력
			KioskDTO newStockDTO = new KioskDTO(1*espressoCnt, 201);//DB의 stock table에서 소모해서 update할 필드값 입력할 DTO 생성 후
			stockArrayList.add(newStockDTO);//생성한 DTO를 재고소진 ArrayList에 추가함.
			// 에스프레소 재고 수량 수정 (사용량 만큼 재고에서 차감)
		}
		if (milkCnt > 0) {//우유 주문 수량이 1개 이상이면
			KioskDTO newOrderDTO = new KioskDTO("104", "104", "우유", 3000, milkCnt);//DB의 order_list table에 insert할 필드값 입력할 DTO 생성 후
			kioskArrayList.add(newOrderDTO);//생성한 DTO를 주문목록 ArrayList에 추가함.
			// 우유 주문 내역 입력
			KioskDTO newStockDTO = new KioskDTO(1*milkCnt, 202);//DB의 stock table에서 소모해서 update할 필드값 입력할 DTO 생성 후
			stockArrayList.add(newStockDTO);//생성한 DTO를 재고소진 ArrayList에 추가함.
			// 우유 재고 재고 수량 수정 (사용량 만큼 재고에서 차감)
		}
		if (breadCnt > 0) {//모닝빵 주문 수량이 1개 이상이면
			KioskDTO newOrderDTO = new KioskDTO("105", "105", "모닝빵", 2000, breadCnt);//DB의 order_list table에 insert할 필드값 입력할 DTO 생성 후
			kioskArrayList.add(newOrderDTO);//생성한 DTO를 주문목록 ArrayList에 추가함.
			// 모닝빵 주문 내역 입력
			KioskDTO newStockDTO = new KioskDTO(1*breadCnt, 203);//DB의 stock table에서 소모해서 update할 필드값 입력할 DTO 생성 후
			stockArrayList.add(newStockDTO);//생성한 DTO를 재고소진 ArrayList에 추가함.
			// 모닝빵 재고 수량 수정 (사용량 만큼 재고에서 차감)
		}
		if (cakeCnt > 0) {//조각케잌 주문 수량이 1개 이상이면
			KioskDTO newOrderDTO = new KioskDTO("106", "106", "조각케잌", 6000, cakeCnt);//DB의 order_list table에 insert할 필드값 입력할 DTO 생성 후
			kioskArrayList.add(newOrderDTO);//생성한 DTO를 주문목록 ArrayList에 추가함.
			// 조각케잌 주문 내역 입력
			KioskDTO newStockDTO = new KioskDTO(1*cakeCnt, 204);//DB의 stock table에서 소모해서 update할 필드값 입력할 DTO 생성 후
			stockArrayList.add(newStockDTO);//생성한 DTO를 재고소진 ArrayList에 추가함.
			// 조각케잌 재고 수량 수정 (사용량 만큼 재고에서 차감)
		}
		if (cookieCnt > 0) {//쿠키 주문 수량이 1개 이상이면
			KioskDTO newOrderDTO = new KioskDTO("107", "107", "쿠키", 4000, cookieCnt);//DB의 order_list table에 insert할 필드값 입력할 DTO 생성 후
			kioskArrayList.add(newOrderDTO);//생성한 DTO를 주문목록 ArrayList에 추가함.
			// 쿠키 주문 내역 입력
			KioskDTO newStockDTO = new KioskDTO(1*cookieCnt, 205);//DB의 stock table에서 소모해서 update할 필드값 입력할 DTO 생성 후
			stockArrayList.add(newStockDTO);//생성한 DTO를 재고소진 ArrayList에 추가함.
			// 쿠키 재고 수량 수정 (사용량 만큼 재고에서 차감)
		}
		if (macaroonCnt > 0) {//마카롱 주문 수량이 1개 이상이면
			KioskDTO newOrderDTO = new KioskDTO("108", "108", "마카롱", 2500, macaroonCnt);//DB의 order_list table에 insert할 필드값 입력할 DTO 생성 후
			kioskArrayList.add(newOrderDTO);//생성한 DTO를 주문목록 ArrayList에 추가함.
			// 마카롱 주문 내역 입력
			KioskDTO newStockDTO = new KioskDTO(1*macaroonCnt, 206);//DB의 stock table에서 소모해서 update할 필드값 입력할 DTO 생성 후
			stockArrayList.add(newStockDTO);//생성한 DTO를 재고소진 ArrayList에 추가함.
			// 마카롱 재고 수량 수정 (사용량 만큼 재고에서 차감)
		}
		if (iceteaCnt > 0) {//아이스티 주문 수량이 1개 이상이면
			KioskDTO newOrderDTO = new KioskDTO("109", "109", "아이스티", 6000, iceteaCnt);//DB의 order_list table에 insert할 필드값 입력할 DTO 생성 후
			kioskArrayList.add(newOrderDTO);//생성한 DTO를 주문목록 ArrayList에 추가함.
			// 아이스티 주문 내역 입력
			KioskDTO newStockDTO = new KioskDTO(1*iceteaCnt, 208);//DB의 stock table에서 소모해서 update할 필드값 입력할 DTO 생성 후
			stockArrayList.add(newStockDTO);//생성한 DTO를 재고소진 ArrayList에 추가함.
			// 아이스티 재고 수량 수정 (사용량 만큼 재고에서 차감)
		}
		if (chocolatelatteCnt > 0) {//초코라떼 주문 수량이 1개 이상이면
			KioskDTO newOrderDTO = new KioskDTO("110", "110", "초코라떼", 6000, chocolatelatteCnt);//DB의 order_list table에 insert할 필드값 입력할 DTO 생성 후
			kioskArrayList.add(newOrderDTO);//생성한 DTO를 주문목록 ArrayList에 추가함.
			// 초코라떼 주문 내역 입력
			KioskDTO newStockDTO01 = new KioskDTO(1*chocolatelatteCnt, 202);//DB의 stock table에서 소모해서 update할 필드값 입력할 DTO 생성 후
			KioskDTO newStockDTO02 = new KioskDTO(1*chocolatelatteCnt, 207);//DB의 stock table에서 소모해서 update할 필드값 입력할 DTO 생성 후
			stockArrayList.add(newStockDTO01);//생성한 DTO를 재고소진 ArrayList에 추가함.
			stockArrayList.add(newStockDTO02);//생성한 DTO를 재고소진 ArrayList에 추가함.
			// 초코라떼 재고 수량 수정 (사용량 만큼 재고에서 차감)
		}
		
		for(int i = 0; i < kioskArrayList.size(); i++) {//주문목록 ArrayList를 사이즈만큼 for문 돌려서
			kioskDAO.orderInsert(kioskArrayList.get(i));//DAO의 DB의 order_list table에 insert하는 orderInsert 메소드에 매개값으로 넣어 실행
			kioskDAO.setSettlement(kioskArrayList.get(i));//DAO의 DB의 settlement table에 update하는 setSettlement 메소드에 매개값으로 넣어 실행
		}
		for(int i = 0; i < stockArrayList.size(); i++) {//재고소진 ArrayList를 사이즈만큼 for문 돌려서
			kioskDAO.setStock(stockArrayList.get(i));//DAO의 DB의 stock table에 update하는 setStock 메소드에 매개값으로 넣어 실행
		}
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				orderlist.setText("총 금액은 \n" + (4000*americanoCnt + 5000*cafelatteCnt + 6000*espressoCnt + 3000*milkCnt + 2000*breadCnt + 6000*cakeCnt + 4000*cookieCnt + 2500*macaroonCnt + 6000*iceteaCnt + 6000*chocolatelatteCnt) + "원 입니다.");//라벨에 주문결과 총 주문금액 출력
			}
			
		});
		
		Stage primaryStage = (Stage)order.getScene().getWindow();//커스텀 다이얼로그인 Membership.fxml을 띄우기 위한 사전 작업으로 Stage 필드값 직접 생성. 컨트롤러에서 다이얼로그를 실행할 때는 소유자 윈도우가 될 primaryStage가 필요.
		
		// 커스텀 다이얼로그 실행
		Stage dialog = new Stage(StageStyle.UTILITY);//다이얼로그 Stage 생성.
		dialog.initModality(Modality.WINDOW_MODAL);//모달 다이럴로그 설정. 이 설정을 하지 않을 시 기본적으로 모달리스가 됨.
		dialog.initOwner(primaryStage);//다이얼로그의 소유자 윈도우 설정.
		dialog.setTitle("적립 여부 확인");//다이얼로그의 제목 세팅.
		
		AnchorPane anchorPane = null;//다이얼로그의 컨테이너를 AnchorPane으로 설정하기 위해 변수 선언.
		try {
			anchorPane = (AnchorPane)FXMLLoader.load(getClass().getResource("Check.fxml"));//적립 여부 확인하는 FXML파일 로딩해서 Parent 타입을 AnchorPane 타입으로 형변환한 후 컨테이너 변수에 대입.
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
	public void initialize(URL location, ResourceBundle resources) {//컨트롤러는 Initializable 인터페이스를 반드시 상속받아야하므로 컨트롤러 class는 기본적으로 initialize 추상메서드를 오버라이딩 해야함.
		
	}

}
