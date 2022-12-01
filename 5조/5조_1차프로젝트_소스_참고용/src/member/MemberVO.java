package member;

// 사용자 정보 관리 - 작성자 : 최영광
public class MemberVO {
	private int member_code;		// 회원 고유번호(시퀀스)
	private String member_id;		// 회원 아이디
	private String member_pw;		// 회원 비밀번호
	private String fullName;		// 회원 성명
	private String phoneNumber;		// 회원 전화번호
	
	// 기본생성자 필수
	public MemberVO() {	
	}
	
	// 생성자 오버로드(초기화에 사용)
	public MemberVO(int member_code, String member_id, String member_pw, String fullName, String phoneNumber) {
		super();
		this.member_code = member_code;
		this.member_id = member_id;
		this.member_pw = member_pw;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
	}
	
	// getter, setter 정의
	public int getMember_code() {
		return member_code;
	}
	public void setMember_code(int member_code) {
		this.member_code = member_code;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getMember_pw() {
		return member_pw;
	}
	public void setMember_pw(String member_pw) {
		this.member_pw = member_pw;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
