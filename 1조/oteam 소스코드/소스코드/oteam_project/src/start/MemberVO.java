package start;

import lombok.*;

/*코드 작성자 : 김대영*/

// members테이블에 들어가는 사용자 정보를 get/set메서드를 사용하여 활용할 수 있도록 하는 클래스 생성
// lombok을 사용하여 get/set 메서드 및 생성자 사용 가능한 상태
@Data
public class MemberVO {
	
	public int member_code, member_phone, member_age;
	public String member_id, member_pw, member_name, member_sex, member_birthday;
	public String region_code;
	
}// end MemberVO