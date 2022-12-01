package reservation;
//(11/20 윤준호 작성)
public class ReserveDTO {
	
	public String getUser_fav_seat() {
		return user_fav_seat;
	}

	public void setUser_fav_seat(String user_fav_seat) {
		this.user_fav_seat = user_fav_seat;
	}

	public String getBus_code() {
		return bus_code;
	}

	public void setBus_code(String bus_code) {
		this.bus_code = bus_code;
	}

	public String getLocation_code() {
		return location_code;
	}

	public void setLocation_code(String location_code) {
		this.location_code = location_code;
	}

	public String getRes_date() {
		return res_date;
	}

	public void setRes_date(String res_date) {
		this.res_date = res_date;
	}

	public String res_code, user_fav_seat, bus_code, location_code, res_date;
}
