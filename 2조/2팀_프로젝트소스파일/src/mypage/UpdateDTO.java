package mypage;

//회원정보 업데이트 DTO 11/22 손영석 작성	
public class UpdateDTO {
	public int user_code;
	public String user_id, user_pw, user_name, user_email, user_phone, user_address, user_fav_seat ;
	
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_address() {
		return user_address;
	}
	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}
	public String getUser_fav_seat() {
		return user_fav_seat;
	}
	public void setUser_fav_seat(String user_fav_seat) {
		this.user_fav_seat = user_fav_seat;
	}
	
}
