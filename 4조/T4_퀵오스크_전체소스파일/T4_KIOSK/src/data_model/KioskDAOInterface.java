//배재연, 윤희진 페어 프로그래밍_pair programming

package data_model;

public interface KioskDAOInterface {
	
	void orderInsert(KioskDTO newKioskOrderDTO);//KioskDTO 객체를 받아 DB 주문내역 table에 insert 하는 추상메소드
	int checkStock(KioskDTO checkStockDTO);//KioskDTO 객체를 받아 DB의 재고 table에서 해당 원료의 현재고상황을 select하여 반환하는 추상메소드
	void setStock(KioskDTO setStockDTO);//KioskDTO 객체를 받아 DB의 재고 table에서 해당 원료를 해당분량만큼 소모하는 update문 실행 추상메소드
	void setSettlement(KioskDTO setSettlementDTO);//KioskDTO 객체를 받아 DB 최종정산 table에 메뉴별 판매량을 update 하는 추상메소드
	boolean memberFindOne(String guest_phone);//KioskDTO 객체를 받아 DB 회원정보 table에 해당 정보의 회원이 이미 존재하는지 확인하는 추상메소드
	void setMember(KioskDTO setMemberDTO);//KioskDTO 객체를 받아 DB 회원정보 table에 새로운 회원을 insert 하는 추상메소드
	void savePoint(KioskDTO savePointDTO);//KioskDTO 객체를 받아 DB 회원정보 table에 회원의 포인트 내역을 update하는 추상메소드
	String viewPoint(KioskDTO savePointDTO);//KioskDTO 객체를 받아 DB 회원정보 table에서 해당하는 회원의 포인트 내역을 select하여 반환하는 추상메소드
	
}
