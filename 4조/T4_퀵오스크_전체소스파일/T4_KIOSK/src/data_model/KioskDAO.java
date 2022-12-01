//배재연, 윤희진 페어 프로그래밍_pair programming

package data_model;

import java.sql.Connection;//JDBC API 사용을 위한 인터페이스. createStatement()/preparedStatement()/collableStatement() 메소드 사용하여 Statement/preparedStatement 개체 참조 위함.
import java.sql.PreparedStatement;//SQL문을 실행하고 결과를 반환하기 위해 제공되는 Statement 인터페이스의 하위 인터페이스로, 미리 컴파일된 SQL문에 인수를 전달하도록 허용하는 역할을 함.
import java.sql.ResultSet;//Statement에서 executeQuery()메소드나 반환된
import java.sql.SQLException;/*드라이버 메소드, 데이터베이스에 액세스하는 메소드 또는 데이터베이스 연결을 가져오려는 시도 중 하나에 발생하는 오류에서 발생. 
사용자 이름이나 암호 정보가 잘못되어 데이터 베이스에 연결할 수 없거나 데이터베이스가 오프라인일 경우, SQL 쿼리에 포함되지 않은 열 이름에 액세스를 시도할 경우*/

import util.DBConnection;//util package의 DB연결 클래스인 DBConnection을 import함.

public class KioskDAO implements KioskDAOInterface {//KioskDAOInterface를 상속받아 구체적으로 구현
	
	private Connection conn = DBConnection.getConnection();//DBConnection의 static getConnection() 메소드로부터 Connection conn을 반환받아 Connection conn에 대입.
	
	@Override
	public void orderInsert(KioskDTO newKioskOrderDTO) {//KioskDTO 객체를 받아 DB 주문내역 table에 insert 하는 메소드
		
		int orderRow = 0;//가장 최근 주문번호의 9~11번째에 넣을 주문순서번호를 select로 꺼내와 저장해서 +1하여 새 주문번호의 9~11번째에 insert할 때 사용할 변수.
		
		String sqlFind = "SELECT order_id FROM T4.order_list ORDER BY order_id DESC ";//가장 최근 주문이 맨 윗행으로 오도록 select문 작성
		
		try {
			PreparedStatement preparedStatementFind = conn.prepareStatement(sqlFind);/*query를 String sqlFind로 받아 Connection 객체 conn의 prepareStatement() 메소드의 매개변수로 넣어
			PreparedStatement type 객체인 preparedStatement로 반환 대입*/
			ResultSet resultSet =  preparedStatementFind.executeQuery();//preparedStatement의 select query문 최종 실행하여 반환되는 테이블을 ResultSet resultSet에 대입하여 저장.
			resultSet.next();//resultSet의 첫번째 행 맨 앞에 커서를 갖다놓고 읽어들일 준비함.
			String orderNum = resultSet.getString(1);//resultSet의 가장 첫번째행에 있는 최근 주문번호열을 읽어와서 orderNum에 저장.
			if(orderNum == null|| orderNum.isEmpty()) {//이전 주문내역이 없어서 resultSet이 비어있을 경우
				orderRow = 1;//주문순서번호 orderRow은 1이 됨.
			}
			else {//이전 주문내역이 있어서 resultSet에 반환된 데이터가 있어질 경우
				orderRow = Integer.parseInt(orderNum.substring(8,11));//가장 최근 주문번호의 9~11번째 문자를 읽어와 숫자로 변환해 orderRow에 저장.
			}
		} catch (SQLException e) {/*PreparedStatement 클래스 객체의 setString() 메소드, setDouble() 메소드 실행 시 필수 예외.
			드라이버 메소드, 데이터베이스에 액세스하는 메소드 또는 데이터베이스 연결을 가져오려는 시도 중 하나에 발생하는 오류에서 발생. 
			사용자 이름이나 암호 정보가 잘못되어 데이터 베이스에 연결할 수 없거나 데이터베이스가 오프라인일 경우, SQL 쿼리에 포함되지 않은 열 이름에 액세스를 시도할 경우 catch로 예외 처리*/
			System.out.println("에러 발생!");//에러발생 안내문 명령 프롬프트창에 출력
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());//SQL에 뜬 예외 메세지와 JAVA에 뜬 예외 메세지 문자열로 출력
		} catch (Exception e) {//SQLException 외의 예외 상황 발생 대비.
			e.printStackTrace();//예외가 발생한 내역 추척하여 출력.
		} finally {
		}//end try
		
		//이전 쿼리를 통해 받아온 주문순서번호를 바탕으로 새 주문순서번호(주문날짜+주문순서번호+메뉴번호)를 구성해서 쿼리문 작성해서 DB에 전달.
		String sql = "insert into T4.order_list(order_id, menu_id, menu_name, unit_price, num_of_sales) values(TO_CHAR(SYSDATE, 'YYYYMMDD')||LPAD(?, 3, '0')||?, ?, ?, ?, ?) ";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);/*query를 String sql로 받아 Connection 객체 conn의 prepareStatement() 메소드의 매개변수로 넣어
			PreparedStatement type 객체인 preparedStatement로 반환 대입*/
			preparedStatement.setString(1, ""+(++orderRow));//이전 쿼리를 통해 받아온 주문순서번호에서 +1을 하여 쿼리의 1번째 물음표에 대입하여 새 주문순서번호를 구성하는데 포함시킴.
			preparedStatement.setString(2, newKioskOrderDTO.getMenu_id());//쿼리의 2번째 물음표에 대입하여 새 주문번호를 구성하는 데에 포함시킴.
			preparedStatement.setString(3, newKioskOrderDTO.getMenu_id());//쿼리의 3번째 물음표에 대입하여 주문내역의 주문메뉴번호값 insert
			preparedStatement.setString(4, newKioskOrderDTO.getMenu_name());//쿼리의 4번째 물음표에 대입하여 주문내역의 주문메뉴이름값 insert
			preparedStatement.setInt(5, newKioskOrderDTO.getUnit_price());//쿼리의 5번째 물음표에 대입하여 주문내역의 주문메뉴 단가 insert
			preparedStatement.setInt(6, newKioskOrderDTO.getNum_of_sales());//쿼리의 6번째 물음표에 대입하여 주문내역의 주문개수 insert
			preparedStatement.executeUpdate();//preparedStatement의 insert query문 최종 실행.
			System.out.println("T4.ORDER_LIST 입력 성공");//예외 발생 없이 성공할 시 성공했다는 메세지 출력.
		} catch (SQLException e) {/*PreparedStatement 클래스 객체의 setString() 메소드, setDouble() 메소드, executeUpdate() 메소드 실행시 필수 예외.
			드라이버 메소드, 데이터베이스에 액세스하는 메소드 또는 데이터베이스 연결을 가져오려는 시도 중 하나에 발생하는 오류에서 발생. 
			사용자 이름이나 암호 정보가 잘못되어 데이터 베이스에 연결할 수 없거나 데이터베이스가 오프라인일 경우, SQL 쿼리에 포함되지 않은 열 이름에 액세스를 시도할 경우 catch로 예외 처리*/
			e.printStackTrace();//예외가 발생한 내역 추척하여 출력.
		} catch (Exception e) {//SQLException 외의 예외 상황 발생 대비.
			e.printStackTrace();//예외가 발생한 내역 추척하여 출력.
		} finally {
		}//end try	
	}
	
	@Override
	public int checkStock(KioskDTO checkStockDTO) {//KioskDTO 객체를 받아 DB의 재고 table에서 해당 원료의 현재고상황을 select하여 반환하는 메소드
		int available_stock = 0;//원료의 재고량을 저장할 변수 선언.
		String sqlFind = "select available_stock FROM T4.stock where ingredient_id = ? ";//특정 원료의 재료 id값을 받아 재고 상황을 확인하는 select문 쿼리
		try {
			PreparedStatement preparedStatementFind = conn.prepareStatement(sqlFind);/*query를 String sqlFind로 받아 Connection 객체 conn의 prepareStatement() 메소드의 매개변수로 넣어
			PreparedStatement type 객체인 preparedStatement로 반환 대입*/
			preparedStatementFind.setInt(1, checkStockDTO.getIngredient_id());//매개값 KioskDTO 객체의 재료id값을 받아와 쿼리의 1번째 물음표에 대입저장.
			ResultSet resultSet =  preparedStatementFind.executeQuery();//preparedStatement의 select query문 최종 실행하여 반환되는 테이블을 ResultSet resultSet에 대입하여 저장.
			resultSet.next();//resultSet의 첫번째 행 맨 앞에 커서를 갖다놓고 읽어들일 준비함.
			available_stock = resultSet.getInt(1);//resultSet의 가장 첫번째행에 있는 해당 원재료의 재고량열을 읽어와서 available_stock 변수에 저장.
		} catch (SQLException e) {/*PreparedStatement 클래스 객체의 setString() 메소드, setDouble() 메소드 실행 시 필수 예외.
			드라이버 메소드, 데이터베이스에 액세스하는 메소드 또는 데이터베이스 연결을 가져오려는 시도 중 하나에 발생하는 오류에서 발생. 
			사용자 이름이나 암호 정보가 잘못되어 데이터 베이스에 연결할 수 없거나 데이터베이스가 오프라인일 경우, SQL 쿼리에 포함되지 않은 열 이름에 액세스를 시도할 경우 catch로 예외 처리*/
			System.out.println("에러 발생!");//에러발생 안내문 명령 프롬프트창에 출력
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());//SQL에 뜬 예외 메세지와 JAVA에 뜬 예외 메세지 문자열로 출력
		} catch (Exception e) {//SQLException 외의 예외 상황 발생 대비.
			e.printStackTrace();//예외가 발생한 내역 추척하여 출력.
		} finally {
		}//end try
		return available_stock;//원료의 재고량을 반환하고 메소드 종료.
	}

	@Override
	public void setStock(KioskDTO setStockDTO) {//KioskDTO 객체를 받아 DB의 재고 table에서 해당 원료를 해당분량만큼 소모하는 update문 실행 메소드
		String sql = "update T4.stock set available_stock = (available_stock - ?) where ingredient_id = ? ";//특정 원료의 재료 id값을 받아 현 재고량에서 소모하는 update문 쿼리
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);/*query를 String sql로 받아 Connection 객체 conn의 prepareStatement() 메소드의 매개변수로 넣어
			PreparedStatement type 객체인 preparedStatement로 반환 대입*/
			preparedStatement.setInt(1, setStockDTO.getAvailable_stock());//주문받은만큼 소모하는 재고량을 KioskDTO에서 받아 실제 현 재고량에서 소모하는 첫번째 물음표에 대입.
			preparedStatement.setInt(2, setStockDTO.getIngredient_id());//주문받은 소모 원료의 id값을 KioskDTO에서 받아 where문 두번째 물음표에 대입.
			preparedStatement.executeUpdate();//preparedStatement의 insert query문 최종 실행.
			System.out.println("T4.STOCK 입력 성공");//예외 발생 없이 성공할 시 성공했다는 메세지 출력.
		} catch (SQLException e) {/*PreparedStatement 클래스 객체의 setString() 메소드, setDouble() 메소드, executeUpdate() 메소드 실행시 필수 예외.
			드라이버 메소드, 데이터베이스에 액세스하는 메소드 또는 데이터베이스 연결을 가져오려는 시도 중 하나에 발생하는 오류에서 발생. 
			사용자 이름이나 암호 정보가 잘못되어 데이터 베이스에 연결할 수 없거나 데이터베이스가 오프라인일 경우, SQL 쿼리에 포함되지 않은 열 이름에 액세스를 시도할 경우 catch로 예외 처리*/
			e.printStackTrace();//예외가 발생한 내역 추척하여 출력.
		} catch (Exception e) {//SQLException 외의 예외 상황 발생 대비.
			e.printStackTrace();//예외가 발생한 내역 추척하여 출력.
		} finally {
		}//end try	
	}
	
	@Override
	public void setSettlement(KioskDTO setSettlementDTO) {//KioskDTO 객체를 받아 DB 최종정산 table에 메뉴별 판매량을 update 하는 메소드
		//최종정산 table에 해당 메뉴의 판매량과 판매이익액을 추가하여 update하는 쿼리
		String sql = "update T4.settlement set num_of_sales = (num_of_sales + ?), profit_margin = (profit_margin + ?) where menu_id = ? ";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);/*query를 String sql로 받아 Connection 객체 conn의 prepareStatement() 메소드의 매개변수로 넣어
			PreparedStatement type 객체인 preparedStatement로 반환 대입*/
			preparedStatement.setInt(1, setSettlementDTO.getNum_of_sales());
			preparedStatement.setInt(2, setSettlementDTO.getUnit_price() - setSettlementDTO.getUnit_cost());
			preparedStatement.setString(3, setSettlementDTO.getMenu_id());
			preparedStatement.executeUpdate();//preparedStatement의 insert query문 최종 실행.
			System.out.println("T4.SETTLEMENT 입력 성공");//예외 발생 없이 성공할 시 성공했다는 메세지 출력.
		} catch (SQLException e) {/*PreparedStatement 클래스 객체의 setString() 메소드, setDouble() 메소드, executeUpdate() 메소드 실행시 필수 예외.
			드라이버 메소드, 데이터베이스에 액세스하는 메소드 또는 데이터베이스 연결을 가져오려는 시도 중 하나에 발생하는 오류에서 발생. 
			사용자 이름이나 암호 정보가 잘못되어 데이터 베이스에 연결할 수 없거나 데이터베이스가 오프라인일 경우, SQL 쿼리에 포함되지 않은 열 이름에 액세스를 시도할 경우 catch로 예외 처리*/
			e.printStackTrace();//예외가 발생한 내역 추척하여 출력.
		} catch (Exception e) {//SQLException 외의 예외 상황 발생 대비.
			e.printStackTrace();//예외가 발생한 내역 추척하여 출력.
		} finally {
		}//end try
	}
	
	@Override
	public boolean memberFindOne(String guest_phone) {//휴대전화번호값을 매개변수로 받아 DB 회원정보 table에서 추출한 view에 해당 정보의 회원이 이미 존재하는지 확인하는 메소드.
		boolean isExist = false;//일치하는 회원정보 존재 여부를 저장할 변수 isExist. 기본값 false.
		String sql = "select count(*) from T4.view1 where column2 = ? ";//view로부터 메소드 매개값인 휴대전화번호(guest_phone)를 가진 회원이 몇명 있는지 그룹함수 count로 조회하는 select문 쿼리
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);/*query를 String sql로 받아 Connection 객체 conn의 prepareStatement() 메소드의 매개변수로 넣어
			PreparedStatement type 객체인 preparedStatement로 반환 대입*/
			preparedStatement.setString(1, guest_phone);//매개변수로 받은 사용자가 입력한 값인 String 객체 guest_phone를 preparedStatement의 1번째 ?에 대입하여 select query 실행
			ResultSet resultSet =preparedStatement.executeQuery();//preparedStatement의 select query문 최종 실행하여 결과값으로 반환된 ResultSet을 resultSet에 대입
			resultSet.next();//resultSet의 첫번째 행 맨 앞에 커서를 갖다놓고 읽어들일 준비함.
			
			int result = resultSet.getInt(1);//select문으로부터 1열의 count(*) 함수 결과값을 받아 int result에 대입하여 저장.
			if (result ==1 ) {//result값이 1이면 count(*) 함수 결과값이 1이므로 일치하는 휴대전화번호가 단 1개 있다는 결과가 나온 것.
				isExist = true;//따라서 isExist는 true값으로 변경.
			} else {//result값이 1이 아니면 count(*) 함수 결과값이 1이 아니므로 일치하는 휴대전화번호가 존재하지 않다는 결과가 나온 것. 휴대전화열은 primary key로서 중복된 값이 존재할 수 없으므로 else에 해당하는 경우는 이 결과 분임.
				isExist = false;//따라서 isExist는 그대로 false값 유지.
			}//end if
			System.out.println("T4.view1 조회 성공");//예외 발생 없이 성공할 시 성공했다는 메세지 출력.
		} catch (SQLException e) {/*PreparedStatement 클래스 객체의 setString() 메소드, ResultSet 클래스 객체의 next() 메소드, getInt() 메소드 실행 시 필수 예외.
			드라이버 메소드, 데이터베이스에 액세스하는 메소드 또는 데이터베이스 연결을 가져오려는 시도 중 하나에 발생하는 오류에서 발생.
			사용자 이름이나 암호 정보가 잘못되어 데이터 베이스에 연결할 수 없거나 데이터베이스가 오프라인일 경우, SQL 쿼리에 포함되지 않은 열 이름에 액세스를 시도할 경우 catch로 예외 처리*/
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());/*java.lang 패키지의 System 클래스의 필드인 PrintStream 클래스형의 err를
			PrintStream 클래스의 format() 메소드로 실행. format() 메소드의 매개변수는 format(String format, Object... args)로 Format String Syntax를 따름.
			formatted string인 "SQL State: %s\n%s"의 %s 2곳에 e.getSQLState()와 e.getMessage() 문자열을 받아 출력.
			내용은 인수로 받은 SQLException 클래스의 객체 e의 메소드인 getSQLState() public String getSQLState() Retrieves the SQLState for this SQLException object.와
			SQLException 클래스가 java.lang 패키지의 Throwable로부터 상속받은 메소드인 getMessage() public String getMessage() Returns the detail message string of this throwable.
			두 String을 %s 형식으로 출력함.*/
			System.out.println();
		} catch (Exception e) {//SQLException 외의 예외 상황 발생 대비.
			e.printStackTrace();//예외가 발생한 내역 추척하여 출력.
		} finally {
		}//end try		
		return isExist;//최종적으로 일치하는 계좌가 있으면 1->true 반환, 없으면 0->false 반환.
	}
	
	@Override
	public void setMember(KioskDTO setMemberDTO) {//KioskDTO 객체를 받아 DB 회원정보 table에서 추출한 view에 새로운 회원을 insert 하는 메소드
		String sql = "insert into T4.view1(column1, column2) values(?, ?) ";//view1의 회원이름과 회원휴대전화번호값을 넣은 새로운 행을 삽입하는 쿼리.
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);/*query를 String sql로 받아 Connection 객체 conn의 prepareStatement() 메소드의 매개변수로 넣어
			PreparedStatement type 객체인 preparedStatement로 반환 대입*/
			preparedStatement.setString(1, setMemberDTO.getColumn1());//setMemberDTO로부터 회원이름값을 받아서 쿼리의 1번째 물음표에 대입.
			preparedStatement.setString(2, setMemberDTO.getColumn2());//setMemberDTO로부터 회원휴대전화번호값을 받아서 쿼리의 2번째 물음표에 대입.
			preparedStatement.executeUpdate();//preparedStatement의 insert query문 최종 실행.
			System.out.println("T4.view1 입력 성공");//예외 발생 없이 성공할 시 성공했다는 메세지 출력.
		} catch (SQLException e) {/*PreparedStatement 클래스 객체의 setString() 메소드, setDouble() 메소드, executeUpdate() 메소드 실행시 필수 예외.
			드라이버 메소드, 데이터베이스에 액세스하는 메소드 또는 데이터베이스 연결을 가져오려는 시도 중 하나에 발생하는 오류에서 발생. 
			사용자 이름이나 암호 정보가 잘못되어 데이터 베이스에 연결할 수 없거나 데이터베이스가 오프라인일 경우, SQL 쿼리에 포함되지 않은 열 이름에 액세스를 시도할 경우 catch로 예외 처리*/
			e.printStackTrace();//예외가 발생한 내역 추척하여 출력.
		} catch (Exception e) {//SQLException 외의 예외 상황 발생 대비.
			e.printStackTrace();//예외가 발생한 내역 추척하여 출력.
		} finally {
		}//end try
	}

	@Override
	public void savePoint(KioskDTO savePointDTO) {//KioskDTO 객체를 받아 DB 회원정보 table에서 추출한 view에 회원의 포인트 내역을 update하는 메소드
		String sql = "update T4.view1 set column3 = (column3 + ?) where column2 = ? ";//view1의 해당 회원휴대전화번호값을 가진 회원의 포인트를 증가시키는 쿼리
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);/*query를 String sql로 받아 Connection 객체 conn의 prepareStatement() 메소드의 매개변수로 넣어
			PreparedStatement type 객체인 preparedStatement로 반환 대입*/
			preparedStatement.setInt(1, savePointDTO.getColumn3());//savePointDTO로부터 증가시킬 포인트값을 받아서 쿼리의 1번째 물음표에 대입.
			preparedStatement.setString(2, savePointDTO.getColumn2());//savePointDTO로부터 회원이름값을 받아서 쿼리의 where문에 있는 2번째 물음표에 대입.
			preparedStatement.executeUpdate();//preparedStatement의 insert query문 최종 실행.
			System.out.println("T4.view1 입력 성공");//예외 발생 없이 성공할 시 성공했다는 메세지 출력.
		} catch (SQLException e) {/*PreparedStatement 클래스 객체의 setString() 메소드, setDouble() 메소드, executeUpdate() 메소드 실행시 필수 예외.
			드라이버 메소드, 데이터베이스에 액세스하는 메소드 또는 데이터베이스 연결을 가져오려는 시도 중 하나에 발생하는 오류에서 발생. 
			사용자 이름이나 암호 정보가 잘못되어 데이터 베이스에 연결할 수 없거나 데이터베이스가 오프라인일 경우, SQL 쿼리에 포함되지 않은 열 이름에 액세스를 시도할 경우 catch로 예외 처리*/
			e.printStackTrace();//예외가 발생한 내역 추척하여 출력.
		} catch (Exception e) {//SQLException 외의 예외 상황 발생 대비.
			e.printStackTrace();//예외가 발생한 내역 추척하여 출력.
		} finally {
		}//end try	
		
	}
	
	@Override
	public String viewPoint(KioskDTO viewPointDTO) {//KioskDTO 객체를 받아 DB 회원정보 table에서 추출한 view에서 해당하는 회원의 포인트 내역을 select하여 FXML 라벨에 출력될 문자열을 반환하는 메소드
		String sqlFind = "SELECT column3 FROM T4.view1 WHERE column2 = ? ";//view1의 해당 회원휴대전화번호값을 가진 회원의 포인트를 조회하는 select문 쿼리
		String notice = "";//FXML의 라벨에 출력될 문자열을 담을 String 변수.
		
		try {
			PreparedStatement preparedStatementFind = conn.prepareStatement(sqlFind);/*query를 String sqlFind로 받아 Connection 객체 conn의 prepareStatement() 메소드의 매개변수로 넣어
			PreparedStatement type 객체인 preparedStatement로 반환 대입*/
			preparedStatementFind.setString(1, viewPointDTO.getColumn2());///viewPointDTO로부터 회원이름값을 받아서 쿼리의 where문에 있는 1번째 물음표에 대입.
			ResultSet resultSet =  preparedStatementFind.executeQuery();//preparedStatement의 select query문 최종 실행하여 반환되는 테이블을 ResultSet resultSet에 대입하여 저장.
			int point = 0;//select문으로부터 조회된 회원의 포인트값을 담을 변수 point 선언.
			if(resultSet.next()) {//select문으로부터 반환된 ResultSet이 있다면
				point = resultSet.getInt(1);//반환된 ResultSet으로부터 1열 1행의 포인트값을 읽어들여 변수 point에 저장.
				notice = viewPointDTO.getColumn1() + "님,\n적립된 포인트 " + Integer.toString(point) + "원입니다.";//label에 출력될 문구 구성.
			}
			else {//select문으로부터 반환된 ResultSet이 없다면
				notice = "회원 정보가 없습니다.";//label에 출력될 문구 구성.
			}
		} catch (SQLException e) {/*PreparedStatement 클래스 객체의 setString() 메소드, setDouble() 메소드 실행 시 필수 예외.
			드라이버 메소드, 데이터베이스에 액세스하는 메소드 또는 데이터베이스 연결을 가져오려는 시도 중 하나에 발생하는 오류에서 발생. 
			사용자 이름이나 암호 정보가 잘못되어 데이터 베이스에 연결할 수 없거나 데이터베이스가 오프라인일 경우, SQL 쿼리에 포함되지 않은 열 이름에 액세스를 시도할 경우 catch로 예외 처리*/
			System.out.println("에러 발생!");//에러발생 안내문 명령 프롬프트창에 출력
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());//SQL에 뜬 예외 메세지와 JAVA에 뜬 예외 메세지 문자열로 출력
		} catch (Exception e) {//SQLException 외의 예외 상황 발생 대비.
			e.printStackTrace();//예외가 발생한 내역 추척하여 출력.
		} finally {
		}//end try
		return notice;//FXML의 라벨에 출력될 문자열을 담은 변수 notice 반환하고 메소드 종료.
		
	}

}
