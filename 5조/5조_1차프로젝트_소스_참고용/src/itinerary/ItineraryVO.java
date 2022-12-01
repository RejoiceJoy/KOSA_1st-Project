package itinerary;

// 사용자가 선택한 일정 정보를 담는 VO 클래스
// 작성자 : 이기쁨
public class ItineraryVO {
	private int itinerary_code;
	private String member_id;
	private String start_date;
	private String choice1;
	private String choice2;
	private String choice3;
	private String choice4;
	private String choice5;
	private int totalPrice;
	
	// 기본 생성자
	public ItineraryVO() {
		super();
	}

	// 생성자 오버로드(초기화에 사용)
	public ItineraryVO(int itinerary_code, String member_id, String start_date, String choice1, String choice2,
			String choice3, String choice4, String choice5, int totalPrice) {
		super();
		this.itinerary_code = itinerary_code;
		this.member_id = member_id;
		this.start_date = start_date;
		this.choice1 = choice1;
		this.choice2 = choice2;
		this.choice3 = choice3;
		this.choice4 = choice4;
		this.choice5 = choice5;
		this.totalPrice = totalPrice;
	}
	
	//getter, setter
	public int getItinerary_code() {
		return itinerary_code;
	}
	public void setItinerary_code(int itinerary_code) {
		this.itinerary_code = itinerary_code;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getChoice1() {
		return choice1;
	}
	public void setChoice1(String choice1) {
		this.choice1 = choice1;
	}
	public String getChoice2() {
		return choice2;
	}
	public void setChoice2(String choice2) {
		this.choice2 = choice2;
	}
	public String getChoice3() {
		return choice3;
	}
	public void setChoice3(String choice3) {
		this.choice3 = choice3;
	}
	public String getChoice4() {
		return choice4;
	}
	public void setChoice4(String choice4) {
		this.choice4 = choice4;
	}
	public String getChoice5() {
		return choice5;
	}
	public void setChoice5(String choice5) {
		this.choice5 = choice5;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

}
