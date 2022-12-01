package password;

//마이페이지 입장시 비밀번호 확인 DTO 11/21 손영석 작성	
public class passwordDTO {
	public int user_code;
	public String  user_pw;

	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	
	
}
