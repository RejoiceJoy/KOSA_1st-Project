package reservation;
//(11/21 윤준호 작성)
public class BusListData {
	
	private String bus_code;
	private String bus_realcode;
	private String bus_time;
	private String bus_size;
	private String bus_location;
	public String getBus_code() {
		return bus_code;
	}
	public void setBus_code(String bus_code) {
		this.bus_code = bus_code;
	}
	public String getBus_realcode() {
		return bus_realcode;
	}
	public void setBus_realcode(String bus_realcode) {
		this.bus_realcode = bus_realcode;
	}
	public String getBus_time() {
		return bus_time;
	}
	public void setBus_time(String bus_time) {
		this.bus_time = bus_time;
	}
	public String getBus_size() {
		return bus_size;
	}
	public void setBus_size(String bus_size) {
		this.bus_size = bus_size;
	}
	public String getBus_location() {
		return bus_location;
	}
	public void setBus_location(String bus_location) {
		this.bus_location = bus_location;
	}
	/**
	 * @param bus_code
	 * @param bus_realcode
	 * @param bus_time
	 * @param bus_size
	 * @param bus_location
	 */
	public BusListData(String bus_code, String bus_realcode, String bus_time, String bus_size, String bus_location) {
		super();
		this.bus_code = bus_code;
		this.bus_realcode = bus_realcode;
		this.bus_time = bus_time;
		this.bus_size = bus_size;
		this.bus_location = bus_location;
	}
}