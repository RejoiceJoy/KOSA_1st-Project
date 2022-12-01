//김미리, 배재연 페어 프로그래밍_pair programming

package main;

import java.net.URL;//Initializable을 상속한 컨트롤러 클래스에서 오버라이딩한 initialize 메소드의 매개값으로 넣기 위해 URL import
import java.util.ResourceBundle;//Initializable을 상속한 컨트롤러 클래스에서 오버라이딩한 initialize 메소드의 매개값으로 넣기 위해 ResourceBundle import

import data_model.KioskDAO;//DB에서 데이터를 가져오거나 DB에 데이터를 집어넣기 위해서 data_model.KioskDAO를 import
import data_model.KioskDTO;//DB에서 데이터를 가져오거나 DB에 데이터를 집어넣는 과정의 중간에 데이터를 담아놓고 일괄처리하기 위해 data_model.KioskDTO를 import
import javafx.application.Platform;//종료버튼 클릭 시 프로그램을 빠져나갈 exit() 메소드 사용을 위해 javafx.application.Platform import
import javafx.event.ActionEvent;//FXML 컨트롤인 버튼을 클릭시 발생한 ActionEvent를 매개값으로 받아 handler 메소드로 처리하기 위한 javafx.event.ActionEvent import
import javafx.fxml.FXML;//FXML 컨트롤 객체 주입을 위해 javafx.fxml.FXML import
import javafx.fxml.Initializable;//컨트롤러는 Initializable 인터페이스를 반드시 상속받아야하므로 javafx.fxml.Initializable import
import javafx.scene.control.Button;//Membership.fxml로부터 주입돼 객체 생성된 버튼 컨트롤을 조작하기 위해 javafx.scene.control.Button import
import javafx.scene.control.Label;//Membership.fxml로부터 주입돼 객체 생성된 라벨 컨트롤을 조작하기 위해 javafx.scene.control.Button import
import javafx.scene.control.TextField;//Membership.fxml로부터 주입돼 객체 생성된 텍스트필드 컨트롤을 조작하기 위해 javafx.scene.control.Button import

public class MemberController implements Initializable {//컨트롤러 클래스는 Initializable 인터페이스를 반드시 상속받아야함.
	
	@FXML private Label memberlist;//포인트 적립 안내 및 적립된 포인트 조회, 회원가입 알림 등 각종 메세지를 보여주는 라벨 객체 주입
	@FXML private TextField name;//회원 이름 입력하는 텍스트 필드 객체 주입
	@FXML private TextField phonenumber;//회원 휴대전화번호 입력하는 텍스트 필드 객체 주입
	@FXML private Button savepoint;//적립하기 버튼 객체 주입
	@FXML private Button joinmember;//회원가입 버튼 객체 주입
	@FXML private Button viewpoint;//조회하기 버튼 객체 주입
	
	public void handleSavepointAction(ActionEvent event) {//적립하기 버튼 클릭 시 발생하는 이벤트 조작 메소드
		if(name.getText().trim().isEmpty()) {//이름 텍스트필드에 공백 입력 시
			Platform.runLater(new Runnable() {
				@Override
				public void run() {		
					memberlist.setText("공백은 입력이 되지 않습니다.\n제대로된 값을 입력하세요.");//경고문구 출력하고
				}
			});
			return;//handleSavepointAction 메소드 종료
		}
		else if(phonenumber.getText().trim().isEmpty()) {//휴대전화번호 텍스트필드에 공백 입력 시
			Platform.runLater(new Runnable() {
				@Override
				public void run() {		
					memberlist.setText("공백은 입력이 되지 않습니다.\n제대로된 값을 입력하세요.");//경고문구 출력하고
				}
			});
			return;//handleSavepointAction 메소드 종료
		}
		else {
			String guest_name = name.getText();//텍스트필드에 입력된 문자열을 String으로 받아서 guest_name에 대입
			String guest_phone = phonenumber.getText();//텍스트필드에 입력된 문자열을 String으로 받아서 guest_phone에 대입
			
			KioskDAO kioskDAO = new KioskDAO();//DAO의 memberFindOne() 메소드 및 savePoint() 메소드를 이용하기 위해 DAO 객체 생성
			
			if(!(kioskDAO.memberFindOne(guest_phone))) {//회원 휴대전화번호가 조회되지 않는다면
				Platform.runLater(new Runnable() {
					@Override
					public void run() {		
						memberlist.setText("회원을 조회할 수 없습니다. 회원가입을 먼저 해주세요.");//경고문구 출력하고
					}
				});
				return;//handleSavepointAction 메소드 종료
			}
	
			int point = RootController.total;//RootController에서 생성되어 대입된 주문액의 1% 금액인 적립될 포인트값 받아옴.
			KioskDTO savePointDTO = new KioskDTO(guest_phone, point);//DAO의 savePoint() 메소드에 넣어 DB의 member table을 업데이트할 회원휴대전화번호, 회원포인트 필드값 가진 DTO 생성
			kioskDAO.savePoint(savePointDTO);//생성한 DTO를 kioskDAO의 savePoint() 메소드에 매개값으로 넣음.
			
			Platform.runLater(new Runnable() {
	
				@Override
				public void run() {
					if(point > 0) {//포인트가 0보다 크다면,
						memberlist.setText(guest_name + "님,\n멤버십 포인트 " + point + "원 적립되었습니다.");//알림문구 출력 후에
						RootController.total = 0;//RootController의 total 필드값을 0으로 만듦.
					}
					else {//포인트가 0이면 주문을 하지 않고 적립을 시도한 것이므로,
						memberlist.setText("주문을 먼저 해주세요.");//경고문구 출력
					}
				}
			});
		}
	}
	
	public void handleJoinmemberAction(ActionEvent event) {//회원가입 버튼 클릭 시 발생하는 이벤트 조작 메소드
		if(name.getText().trim().isEmpty()) {//이름 텍스트필드에 공백 입력 시
			Platform.runLater(new Runnable() {
				@Override
				public void run() {		
					memberlist.setText("공백은 입력이 되지 않습니다.\n제대로된 값을 입력하세요.");//경고문구 출력
				}
			});
		}
		else if(phonenumber.getText().trim().isEmpty()) {//휴대전화번호 텍스트필드에 공백 입력 시
			Platform.runLater(new Runnable() {
				@Override
				public void run() {		
					memberlist.setText("공백은 입력이 되지 않습니다.\n제대로된 값을 입력하세요.");//경고문구 출력
				}
			});
		}
		else {
			String guest_name = name.getText();//텍스트필드에 입력된 문자열을 String으로 받아서 guest_name에 대입
			String guest_phone = phonenumber.getText();//텍스트필드에 입력된 문자열을 String으로 받아서 guest_phone에 대입
			
			KioskDAO kioskDAO = new KioskDAO();//DAO의 memberFindOne() 메소드 및 setMember() 메소드를 이용하기 위해 DAO 객체 생성
			
			if(!(kioskDAO.memberFindOne(guest_phone))) {//회원 휴대전화번호가 조회되지 않는다면
				KioskDTO setMemberDTO = new KioskDTO(guest_name, guest_phone);//DAO의 setMember() 메소드에 넣어 DB의 member table에 insert할 회원이름, 회원휴대전화번호 필드값 가진 DTO 생성
				kioskDAO.setMember(setMemberDTO);//생성한 DTO를 kioskDAO의 setMember() 메소드에 매개값으로 넣음.
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						memberlist.setText(guest_name + "님, 회원가입되었습니다.");//안내문구 출력
					}
				});
			}
			else {//회원 휴대전화번호가 조회된다면
				Platform.runLater(new Runnable() {
					@Override
					public void run() {		
						memberlist.setText("이미 가입된 회원입니다.");//경고문구 출력
					}
				});
			}
		}

	}
	
	public void handleViewpointAction(ActionEvent event) {//조회하기 버튼 클릭 시 발생하는 이벤트 조작 메소드
		if(name.getText().trim().isEmpty()) {//이름 텍스트필드에 공백 입력 시
			Platform.runLater(new Runnable() {
				@Override
				public void run() {		
					memberlist.setText("공백은 입력이 되지 않습니다.\n제대로된 값을 입력하세요.");//경고문구 출력
				}
			});
		}
		else if(phonenumber.getText().trim().isEmpty()) {//휴대전화번호 텍스트필드에 공백 입력 시
			Platform.runLater(new Runnable() {
				@Override
				public void run() {		
					memberlist.setText("공백은 입력이 되지 않습니다.\n제대로된 값을 입력하세요.");//경고문구 출력
				}
			});
		}
		else {
			String guest_name = name.getText();//텍스트필드에 입력된 문자열을 String으로 받아서 guest_name에 대입
			String guest_phone = phonenumber.getText();//텍스트필드에 입력된 문자열을 String으로 받아서 guest_phone에 대입
			
			KioskDAO kioskDAO = new KioskDAO();//DAO의 memberFindOne() 메소드 및 viewPoint() 메소드를 이용하기 위해 DAO 객체 생성
			
			if(kioskDAO.memberFindOne(guest_phone)) {//회원 휴대전화번호가 조회된다면
				KioskDTO viewMemberDTO = new KioskDTO(guest_name, guest_phone);//DAO의 viewPoint() 메소드에 넣어 DB의 member table에 update할 회원이름, 회원휴대전화번호 필드값 가진 DTO 생성
				String point = kioskDAO.viewPoint(viewMemberDTO);//생성한 DTO를 kioskDAO의 viewPoint() 메소드에 매개값으로 넣음.
				Platform.runLater(new Runnable() {
					@Override
					public void run() {		
						memberlist.setText(point);//포인트 안내 문구 출력
					}
				});
			}
			else {//회원 휴대전화번호가 조회되지 않는다면
				Platform.runLater(new Runnable() {
					@Override
					public void run() {		
						memberlist.setText("회원을 조회할 수 없습니다.");//경고문구 출력
					}
				});
			}
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}
