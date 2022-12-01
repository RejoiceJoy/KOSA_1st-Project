//배재연, 윤희진 페어 프로그래밍_pair programming

package data_model;

public class KioskDTO {   

	private String menu_id;  //메뉴ID 
	private String menu_name;  //메뉴명 필드
	private int unit_cost; //단가 필드
	private int unit_price; //판매가 필드
	private int ingredient_id; //재료ID 필드
	private String ingredient_name; //원재료명 필드
	private int available_stock; // 수량 필드
	private String order_id; // 주문번호 필드
	private int num_of_sales; //판매건수 필드
	private String column1; // 주문자 필드
	private String column2; // 주문자핸드폰 필드
	private int column3; // 회원포인트 필드
	private int profit_margin; //마진 필드
	
	public String getMenu_id() {                   //메뉴ID 필드값 getter
		return menu_id;                                //리턴받을 메뉴ID
	}
	public void setMenu_id(String menu_id) {                                   //메뉴ID 필드값 setter
		this.menu_id = menu_id;                                                         //전역변수의 menu_id = 매개변수로 값을 입력받은 menu_id
	}
	public String getMenu_name() {                                               //메뉴명 필드값 getter
		return menu_name;                                                            //값을 리턴받을 menu_name
	}
	public void setMenu_name(String menu_name) {                //메뉴명 필드값 setter
		this.menu_name = menu_name;                                      //전역변수의 menu_name= 매개변수로 값을 입력받은 menu_name
	}
	public int getUnit_cost() {                                            //단가 필드 getter
		return unit_cost;                                                    //값을 리턴받을 unit_cost
	}
	public void setUnit_cost(int unit_cost) {                 //단가 필드 setter
		this.unit_cost = unit_cost;                                 //전역변수의 unit_cost= 매개변수로 값을 입력받은 unit_cost
	}
	public int getUnit_price() {                               //판매가 필드 getter
		return unit_price;                                       //값을 리턴받을 unit_price
	}
	public void setUnit_price(int unti_price) {                  //판매가 필드 setter
		this.unit_price = unti_price;                                  //전역변수의 unit_price= 매개변수로 값을 입력받은 unit_price
	}
	public int getIngredient_id() {                               //재료ID 필드 getter
		return ingredient_id;                                        //값을 리턴받을 ingredient_id
	}
	public void setIngredient_id(int ingredient_id) {                   //재료ID 필드 setter
		this.ingredient_id = ingredient_id;                                   //전역변수의 ingredient_id = 매개변수로 값을 입력받은 ingredient_id
	}
	public String getIngredient_name() {                               //원재료명 필드 getter
		return ingredient_name;                                             //값을 리턴받을 ingredient_name
	}
	public void setIngredient_name(String ingredient_name) {             //원재료명 필드 setter
		this.ingredient_name = ingredient_name;                                   //전역변수의 ingredient_name = 매개변수로 값을 입력받은 ingredient_name
	}
	public int getAvailable_stock() {                                                  //수량 필드 getter
		return available_stock;                                                          //값을 리턴받을 available_stock
	}
	public void setAvailable_stock(int available_stock) {             //수량 필드 setter
		this.available_stock = available_stock;                             //전역변수의 available_stock = 매개변수로 값을 입력받은 available_stock
	}
	public String getOrder_id() {                                            //주문번호 필드 getter
		return order_id;                                                          //값을 리턴받을 order_id
	}
	public void setOrder_id(String order_id) {                    //주문번호 필드 setter
		this.order_id = order_id;                                           //전역변수의  order_id = 매개변수로 값을 입력받은  order_id
	}
	public int getNum_of_sales() {                                    //판매건수 필드 getter
		return num_of_sales;                                            //값을 리턴받을 num_of_sales
	}
	public void setNum_of_sales(int num_of_sales) {               //판매건수 필드 setter
		this.num_of_sales = num_of_sales;                               //전역변수의  num_of_sales = 매개변수로 값을 입력받은  num_of_sales
	}
	public String getColumn1() {                                         //주문자 필드 getter
		return column1;                                                      //값을 리턴받을 column1
	}
	public void setColumn1(String column1) {                          //주문자 필드 setter
		this.column1 = column1;                                              //전역변수의  column1 = 매개변수로 값을 입력받은 column1
	}
	public String getColumn2() {                                     // 주문자핸드폰 필드 getter
		return column2;                                                   //값을 리턴받을 column2
	}
	public void setColumn2(String column2) {             // 주문자핸드폰 필드 setter
		this.column2 = column2;                                   //전역변수의 column2 = 매개변수로 값을 입력받은  column2
	}
	public int getColumn3() {                                    // 회원포인트 필드 getter
		return column3;                                           //값을 리턴받을 column3
	}
	public void setColumn3(int column3) {           // 회원포인트 필드 setter
		this.column3 = column3;                           //전역변수의 column3 = 매개변수로 값을 입력받은  column3
	}
	public int getProfit_margin() {                                        //마진 필드 getter
		return profit_margin;                                              //값을 리턴받을 profit_margin
	}
	public void setProfit_margin(int profit_margin) {               //마진 필드 setter
		this.profit_margin = profit_margin;                              //전역변수의 profit_margin = 매개변수로 값을 입력받은 profit_margin
	}
	public KioskDTO() {                                                      //KioskDTO 클래스 이름의 디폴트 생성자 생성 
	}
	public KioskDTO(String menu_id) {                            //메뉴ID 매개변수 1개를 받아 값을 집어넣을 setter역할의 KioskDTO 생성자 생성
		super();                                       
		this.menu_id = menu_id;   //전역변수의  menu_id = 매개변수로 값을 입력받은  menu_id
	}
	public KioskDTO(int ingredient_id) {                            //재료ID 매개변수 1개를 받아 값을 집어넣을 setter역할의 KioskDTO 생성자 생성
		super();
		this.ingredient_id = ingredient_id;   //전역변수의  ingredient_id = 매개변수로 값을 입력받은  ingredient_id
	}
	public KioskDTO(String order_id, String menu_id, String menu_name, int unit_price, int num_of_sales) { //메뉴ID,메뉴명,판매가,주문번호,판매건수의 매개변수 5개를 받아 값을 넣을 setter 역활 생성자
		super();
		this.menu_id = menu_id;                           //전역변수의  menu_id = 매개변수로 값을 입력받은  menu_id
		this.menu_name = menu_name;               //전역변수의  menu_name = 매개변수로 값을 입력받은  menu_name
		this.unit_price = unit_price;                 //전역변수의  unit_price = 매개변수로 값을 입력받은 unit_price
		this.order_id = order_id;                   //전역변수의 order_id = 매개변수로 값을 입력받은 order_id
		this.num_of_sales = num_of_sales; //전역변수의   num_of_sales = 매개변수로 값을 입력받은  num_of_sales
	}
	public KioskDTO(String menu_id, String menu_name, int unit_price, int num_of_sales) { //메뉴ID,메뉴명,판매가,판매건수의 매개변수 4개를 받아 값을 넣을 setter 역활의 생성자 
		super();
		this.menu_id = menu_id;                                //전역변수의  menu_id = 매개변수로 값을 입력받은  menu_id
		this.menu_name = menu_name;                    //전역변수의  menu_name = 매개변수로 값을 입력받은 menu_name
		this.unit_price = unit_price;                     //전역변수의  unit_price = 매개변수로 값을 입력받은  unit_price
		this.num_of_sales = num_of_sales;      //전역변수의 num_of_sales = 매개변수로 값을 입력받은  menu_id
	}
	public KioskDTO(int ingredient_id, String ingredient_name, int unit_cost, int available_stock) { //재료ID,원재료명,원가,수량의 매개변수를 받아 값을 넣을 setter역활의 생성자
		super();
		this.unit_cost = unit_cost;                                  //전역변수의 unit_cost = 매개변수로 값을 입력받은  unit_cost
		this.ingredient_id = ingredient_id;                  //전역변수의  ingredient_id = 매개변수로 값을 입력받은  ingredient_id
		this.ingredient_name = ingredient_name;      //전역변수의   ingredient_name = 매개변수로 값을 입력받은   ingredient_name
		this.available_stock = available_stock;        //전역변수의  available_stock = 매개변수로 값을 입력받은  available_stock
	}
	public KioskDTO(String menu_id, String menu_name, int unit_cost, int unit_price, int num_of_sales, 
			int profit_margin) {           //메뉴ID,메뉴명,원가,판매가,판매건수,마진 매개변수를 받아 값을 넣을 setter역활의 생성자
		super();
		this.menu_id = menu_id;                                   //전역변수의 menu_id = 매개변수로 값을 입력받은 menu_id
		this.menu_name = menu_name;                       //전역변수의 menu_name = 매개변수로 값을 입력받은 menu_name
		this.unit_cost = unit_cost;                           //전역변수의 unit_cost = 매개변수로 값을 입력받은 unit_cost
		this.unit_price = unit_price;                       //전역변수의 unit_price = 매개변수로 값을 입력받은 unit_price
		this.num_of_sales = num_of_sales;         //전역변수의 num_of_sales = 매개변수로 값을 입력받은 num_of_sales
		this.profit_margin = profit_margin;       //전역변수의 profit_margin = 매개변수로 값을 입력받은  profit_margin
	}
	public KioskDTO(String column1, String column2) { //주문자,주문자핸드폰 2개의 매개변수를 받아 값을 넣을 setter역활의 생성자
		super();
		this.column1 = column1;                     //전역변수의  column1(주문자) = 매개변수로 값을 입력받은 column1(주문자)
		this.column2 = column2;                  //전역변수의 column2(주문자핸드폰) = 매개변수로 값을 입력받은 column2(주문자핸드폰)
	}
	public KioskDTO(int available_stock, int ingredient_id) { //수량,원재료명 매개변수를 받아 값을 넣을 setter역활의 생성자
		super();
		this.ingredient_id = ingredient_id;           //전역변수의 ingredient_id = 매개변수로 값을 입력받은 ingredient_id
		this.available_stock = available_stock;   //전역변수의 available_stock = 매개변수로 값을 입력받은 available_stock
	}
	public KioskDTO(String column2, int column3) { //주문자핸드폰,회원포인트 매개변수를 받아 값을 넣을 setter역활의 생성자 
		super();
		this.column2 = column2;           //전역변수의 column2(주문자핸드폰) = 매개변수로 값을 입력받은 column2(주문자핸드폰)
		this.column3 = column3;         //전역변수의 column3(회원포인트) = 매개변수로 값을 입력받은 column3(회원포인트)
	}
	
}
