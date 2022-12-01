package application;

import java.sql.Connection;

// DAO 클래스에서 공통적으로 사용할 필드 및 메소드를 추상화한 인터페이스
// 작성자 : 이수봉
public interface DAOInterface <VO, T> {		// VO : VO 클래스 타입 , T : DB에서 검색 기준(where 절 조건)으로 삼을 열의 데이터 타입
	public static final Connection conn = DBConnection.getConnection();		// DB 연결 객체
	
	public abstract void insertData(VO vo);		// DB에 데이터 insert 하는 메소드(구현 위치에서 쓰는 VO 객체 입력)
	public abstract void updateData(VO vo);		// DB에 데이터 update 하는 메소드(구현 위치에서 쓰는 VO 객체 입력)
	public abstract VO selectOneRow(T t);		// 입력값(T)을 기준으로 해당하는 데이터 한 행을 select하는 메소드(구현 위치에서 쓰는 VO 객체로 반환)
}
