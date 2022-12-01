package itinerary;

// 관광지 정보를 담는 VO 클래스
// 작성자 : 이기쁨
public class SightInfoVO {
	private int sight_code;			// 관광지 코드
	private String country;			// 국가
	private String region;			// 지역
	private String sight_name;		// 관광지명
	private int price;				// 가격
	private String sight_data;		// 관광지 소개
	private String address;			// 주소지
	private String image;			// 이미지 파일명

	// 기본생성자
	public SightInfoVO() {
	}
	
	// 생성자(매개변수 5개)
	public SightInfoVO(String sight_name, int price, String sight_data, String address, String image) {
		super();
		this.sight_name = sight_name;
		this.price = price;
		this.sight_data = sight_data;
		this.address = address;
		this.image = image;
	}

	// getter, setter
	public int getSight_code() {
		return sight_code;
	}
	public void setSight_code(int sight_code) {
		this.sight_code = sight_code;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getSight_name() {
		return sight_name;
	}
	public void setSight_name(String sight_name) {
		this.sight_name = sight_name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getSight_data() {
		return sight_data;
	}
	public void setSight_data(String sight_data) {
		this.sight_data = sight_data;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

} //end class



