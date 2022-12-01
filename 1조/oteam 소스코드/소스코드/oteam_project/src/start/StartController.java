package start;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import login.loginuserAct;

/*코드 작성자 : 김대영*/

public class StartController implements Initializable {

	// 로그인 화면의 구성 요소들
	@FXML
	private Label UserLogin;
	@FXML
	private Button user, UserSignUp, home, login, loginOK;
	@FXML
	private TextField getID, getPhoneNum, getName, inputID;
	@FXML
	private PasswordField getPW, inputPW;

	// 회원가입 화면의 구성 요소들
	@FXML
	private TextField jid, jname, jphone, jbirthday, jsex, jregion;
	@FXML
	private PasswordField jpw, jpwc;
	@FXML
	private Button submitBtn, cancelBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	// 첫 화면에서 로그인 버튼을 눌렀을 때 로그인 페이지로 이동하는 동작을 구현한 메서드
	public void moveUser() throws Exception {
		Stage loginStage = new Stage();
		Stage stage = (Stage) user.getScene().getWindow();

		Parent second = FXMLLoader.load(getClass().getResource("view/login.fxml"));
		loginStage.setTitle("oteam_LoginPage");
		Scene sc = new Scene(second);
		loginStage.setScene(sc);
		loginStage.show();
		stage.close();
	}// end moveUser

	// 로그인 버튼을 눌렀을 때 상황에 맞는 동작을 하도록 구현한 메서드
	public void UserLogin(ActionEvent event) throws Exception {
		
		// mVO 생성자 사용하기 위한 객체 생성
		MemberVO mVO = new MemberVO();
		// 입력받은 id값 할당
		mVO.setMember_id(inputID.getText());
		// 입력받은 pw값 할당
		mVO.setMember_pw(inputPW.getText());
		
		/*
		로그인된 상태로 화면전환
		1. 아이디 또는 비밀번호를 입력하지 않았다면 경고창 출력
		2. 아이디 또는 비밀번호가 틀렸다면 경고창 출력
		3. 정상 입력이라면 로그인 완료
		*/
		// 1. 아이디 또는 비밀번호를 입력하지 않았을 때 동작
		if (inputPW.getText().equals("") || inputID.getText().equals("")) {
			AlertError("입력되지 않은 항목이 있습니다.","아이디와 비밀번호를 모두 입력하세요.");
		}
		// 2. 아이디 또는 비밀번호가 틀렸을때 동작
		else if(loginuserAct.loginCheck(mVO.getMember_id(), mVO.getMember_pw()) != 1){
			AlertError("사용자 정보가 일치하지 않습니다.","아이디 또는 비밀번호를 확인하세요.");
		} 
		// 3. 정상 입력일 때 동작
		else {
			// 올바르게 로그인이 되었다면, userupdate메서드에 로그인한 현재 사용자의 member_id를 넘겨준다
			loginuserAct.userupdate(mVO.getMember_id());
				
			Stage primaryStage = new Stage();
			Stage stage = (Stage) loginOK.getScene().getWindow();
			
			// Main 패키지의 AppMain.fxml파일을 로딩
			Parent second = FXMLLoader.load(getClass().getResource("/Main/AppMain.fxml"));
			Scene sc = new Scene(second);
			primaryStage.setTitle("oteam_AppMain");
			primaryStage.setScene(sc);
			primaryStage.show();
			stage.close();
		}
	}// end UserLogin

	// 회원가입 버튼을 눌렀을 때 회원가입 페이지로 이동하는 동작을 구현한 메서드
	public void goUserSignUp() throws Exception {
		Stage primaryStage = new Stage();
		Stage stage = (Stage) UserSignUp.getScene().getWindow();
		
		// 회원가입을 진행하는 페이지를 로드
		Parent second = FXMLLoader.load(getClass().getResource("view/join.fxml"));
		Scene sc = new Scene(second);
		primaryStage.setTitle("oteam_Join");
		primaryStage.setScene(sc);
		primaryStage.show();
		stage.close();
	}// end goUserSignUp

	// 가입 완료하기 버튼 이벤트
	// 각 입력 사항이 올바르게 들어왔는지 모두 체크하도록 조건문 생성
	@FXML
	public void submitUserSignup(ActionEvent event) throws Exception {
		
		MemberVO mVO = new MemberVO();

		try {
			
			// 아이디 체크
			// 사용자가 화면에 입력한 아이디값을 받을 변수 생성
			String inputid = jid.getText();
			
			// 만약 입력이 비었거나, 입력된 아이디의 문자열 길이가 20보다 크다면 경고창 출력
			if(inputid.equals("")==true) {
				// 입력이 비었을 경우 경고창 출력
				AlertError("입력되지 않은 항목이 있습니다.","모든 항목을 입력 하셔야 합니다.");
				return;
			}else if(inputid.length() > 20) {
				AlertError("아이디 입력값 오류","영어,숫자를 포함한 20자 이내의 아이디를 사용하세요.");
				return;
			}else {
				// 제한사항에 위배되지 않는다면 입력받은 값을 DB로 전달하도록 set메서드 호출
				mVO.setMember_id(inputid);
			}
			
			// 비밀번호 체크
			// 사용자가 화면에 입력한 비밀번호값을 받을 변수 생성
			String inputpw = jpw.getText();
			
			// 만약 입력이 비었거나, 입력된 비밀번호의 길이가 20보다 크다면 올바르지 않은 입력
			if(inputpw.equals("")==true) {
				// 입력이 비었을 경우 경고창 출력
				AlertError("입력되지 않은 항목이 있습니다.","모든 항목을 입력 하셔야 합니다.");
				return;
			}else if(inputpw.length()>20) {
				AlertError("비밀번호 입력값 오류","영어,숫자를 포함한 20자 이내의 비밀번호를 사용하세요.");
				return;
			}else {
				// 제한사항에 위배되지 않는다면 입력받은 값을 DB로 전달하도록 set메서드 호출
				mVO.setMember_pw(inputpw);
			}
			
			// 비밀번호 재확인
			// 사용자가 화면에 입력한 비밀번호값을 받을 변수 생성
			String inputpwc = jpwc.getText();
			
			// 만약 입력이 비었거나, 입력된 비밀번호의 길이가 20보다 크다면 올바르지 않은 입력
			if(inputpwc.equals("")==true) {
				// 입력이 비었을 경우 경고창 출력
				AlertError("입력되지 않은 항목이 있습니다.","모든 항목을 입력 하셔야 합니다.");
				return;
			}else if(inputpwc.equals(inputpw)==false) {
				// 재입력한 비밀번호가 처음 사용자가 입력했던 비밀번호와 값이 다른 경우 경고창 출력
				AlertError("비밀번호 재확인 입력값 오류","입력하신 비밀번호가 맞는지 확인하세요.");
				return;
			}
			
			// 이름 체크
			// 사용자가 화면에 입력한 이름을 받을 변수 생성
			String inputname = jname.getText();
			
			/*
			 * 한글 이름을 가진 사용자를 대상으로 제작했기 때문에, 입력된 한글의 바이트 수를 계산해서 글자수를 제한해야 한다
			 * 한 글자당 3바이트 이므로 DB에서 테이블 설계시 최대 바이트 수를 20으로 제한했기 때문에 6글자를 넘는 이름은 사용할 수 없다
			 * 또한 입력이 비었다면 별도로 경고창 출력
			 */
			if(inputpw.equals("")==true) {
				// 입력이 비었을 경우 경고창 출력
				AlertError("입력되지 않은 항목이 있습니다.","모든 항목을 입력 하셔야 합니다.");
				return;
			}else if(inputname.getBytes().length>20) {
				AlertError("이름 입력값 오류","여섯글자를 초과하는 한글 이름은 사용할 수 없습니다.");
				return;
			}else {
				// 제한사항에 위배되지 않는다면 입력받은 값을 DB로 전달하도록 set메서드 호출
				mVO.setMember_name(inputname);
			}
			
			// 폰번호 체크
			// 사용자가 화면에 입력한 휴대폰 번호를 문자열로 읽어들어와서 변수에 저장
			String inputphone = jphone.getText();
			
			// 정상적인 휴대폰 번호 입력은 총 11개의 숫자로 이루어져 있으므로 입력이 아예 없거나, 11자 이외의 다른 길이라면 모두 올바르지 않은 입력			
			if(inputphone.equals("")==true) {
				// 입력이 비었을 경우 경고창 출력
				AlertError("입력되지 않은 항목이 있습니다.","모든 항목을 입력 하셔야 합니다.");
				return;
			}else if(inputphone.length() != 11) {
				AlertError("휴대폰 번호 입력값 오류","주어진 형식과 동일하게 11자의 번호를 입력하세요.");
				return;
			}
			else {
				// 제한사항에 위배되지 않는다면 입력받은 값을 DB로 전달하도록 set메서드 호출
				mVO.setMember_phone(Integer.parseInt(inputphone));
			}			
			
			// 생년월일 체크
			// 사용자가 화면에 입력한 생년월일 형식을 모두 문자열로 저장
			// YYYY-MM-BB 형식은 총 10개의 문자로 이루어졌으므로 문자열의 길이가 10이 아니거나 입력이 비었다면 올바르지 않은 입력
			String inputbirthday = jbirthday.getText();
			
			// 만약 입력받은 생년월일의 길이가 10이 아니라면 형식에 위배된 입력		
			if(inputbirthday.equals("")==true) {
				// 입력이 비었을 경우 경고창 출력
				AlertError("입력되지 않은 항목이 있습니다.","모든 항목을 입력 하셔야 합니다.");
				return;
			}else if(inputbirthday.length() != 10) {
				AlertError("생년월일 입력값 오류","주어진 형식과 동일하게 생년월일을 입력하세요.");
				return;
			}else {
				// 제한사항에 위배되지 않는다면 입력받은 값을 DB로 전달하도록 set메서드 호출
				mVO.setMember_birthday(inputbirthday);
			}			
			
			// 성별 체크
			// 입력받은 알파벳을 대문자로 변환하여 문자열에 저장
			String inputsex = (jsex.getText()).toUpperCase();
			
			// 남성M , 여성F 외에 다른 알파벳을 입력값으로 받지 않아야 하므로 조건문 생성
			// 1. 입력 문자가 M이 아닌 경우 -> F를 입력했거나, 그 이외의 입력이 들어온 것
			if(inputsex.equals("M")==false) {
				// 2. M이 아닌 입력이 F인 경우 -> 올바른 입력이므로 해당 값을 DB에 전달하도록 set메서드 호출 
				if(inputsex.equals("F")==true) {
					mVO.setMember_sex(inputsex);
				}else if(inputsex.equals("")==true) {
					// 입력이 비었을 경우 경고창 출력
					AlertError("입력되지 않은 항목이 있습니다.","모든 항목을 입력 하셔야 합니다.");
					return;
				}
				else {
					// 3. M과 F 모두 아니라면 올바르지 않은 입력이므로 경고창 출력
					AlertError("성별 입력값 오류","남성(M),여성(F)이외의 값은 사용할 수 없습니다.");
					return;
				}
			}else if(inputsex.equals("")==true) {
				// 입력이 비었을 경우 경고창 출력
				AlertError("입력되지 않은 항목이 있습니다.","모든 항목을 입력 하셔야 합니다.");
				return;
			}
			else {
				// 4. 입력이 M으로 들어왔다면 올바른 입력이므로 해당 값을 DB에 전달하도록 set메서드 호출 
				mVO.setMember_sex(inputsex);
			}
			
			// 지역번호 확인
			/*
			 * 지역번호(region_code)는 DB에서 region 테이블의 PK이고, members테이블에서 이를 가져와 FK로 사용하고 있다
			 * 따라서 FK에 PK로 참조하고있는 값이 없는 경우 오류가 발생하므로 테이블에 존재하는 지역번호 값 중에서 입력이 들어와야 한다
			 * 이를 확인하기 위해 테이블에 담겨있는 지역번호를 담을 문자열 배열을 생성하고, 반복문을 돌며 일치하는 값이 있는지 확인한다
			 * 만약 존재한다면, 올바른 입력이므로 값을 DB에 넘겨주고 
			 * 올바르지 않다면 경고창을 출력한다
			 */
			// 지역번호를 담아둔 문자열 배열
			String[] regionarr = new String[] {"02","051","053","032","062","042","052","044","031","033","043","041","063","061","054","055","064"};
			// 사용자가 화면에 입력한 지역번호를 문자열에 담기위한 변수
			String inputregion = jregion.getText();
			// 존재하는 지역번호와 입력받은 값이 있는지 확인하고, 그 결과를 담을 불리언타입의 변수 
			boolean check = false;
			
			// 지역번호가 담긴 문자열 배열의 길이만큼 반복
			for(int i=0; i<regionarr.length; i++) {
				// 만약 문자열 내에 사용자의 입력값과 동일한 값이 있다면 올바른 입력
				if(regionarr[i].equals(inputregion)) {
					// 입력의 옳고 그름의 판별 결과를 담는 변수에 true 반환
					check = true;
					// 나머지 배열의 요소와 비교할 필요가 없으므로 break로 반복문 탈출
					break;
				}else {
					// 배열의 모든 값들과 비교를 했는데 없다면 올바르지 않은 입력이므로 false
					check = false;
				}
			}
			
			// 만약 입력이 비었다면 경고창을, 올바른 입력이라면 값 전달을, 올바르지 않은 입력이라면 경고창을 출력
			if(inputregion.equals("")==true) {
				// 입력이 비었을 경우 경고창 출력
				AlertError("입력되지 않은 항목이 있습니다.","모든 항목을 입력 하셔야 합니다.");
				return;
			}else if(check == true) {
				// DB에 데이터 전달을 위해 set메서드 호출
				mVO.setRegion_code(inputregion);
			}else {
				// 올바르지 않은 입력이라면(check == false) 경고창 출력
				AlertError("지역번호 오류","존재하지 않는 지역번호의 값은 사용할 수 없습니다.");
				return;
			}
			
			// 위 과정으로 모든 제한사항을 확인하여
			// 올바르지 않은 입력이 없는 상태가 되면, DB에 값을 전달하도록 MemberDao의 JoinInsert메서드 호출
			MemberDAO.JoinInsert(mVO);
			
			// 회원 가입이 완료 되면, 다시 로그인 화면으로 자동 전환 되도록 설정
			try {
				Parent members = FXMLLoader.load(getClass().getResource("view/login.fxml"));
				Scene scene = new Scene(members);
				Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				primaryStage.setTitle("oteam_login");
				primaryStage.setScene(scene);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// 회원가입시 입력하지 않은 항목이 있을 때 에러 메세지 출력
		} catch (IllegalArgumentException e) {
			System.out.println("IllegalArgumentException caught");
		} catch (Exception e) {
			e.printStackTrace();
		}// end try
	}// end submitUserSignup
	
	// 회원가입창 내부 뒤로가기 버튼 동작
	public void cancelAction(ActionEvent event) throws Exception {
		try {
			// 뒤로가기 버튼을 누르면 다시 login.fxml 화면으로 이동하도록 설정
			Parent members = FXMLLoader.load(getClass().getResource("view/login.fxml"));
			Scene scene = new Scene(members);
			Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			primaryStage.setTitle("oteam_login");
			primaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// end cancelAction
	
	// 경고창 출력용 메서드 생성
	public void AlertError(String HeaderText, String ContentText) {
		Alert Fail = new Alert(AlertType.ERROR);
		Fail.setHeaderText(HeaderText);
		Fail.setContentText(ContentText);
		Fail.showAndWait();
	}//end AlertError
	
}// end RootController