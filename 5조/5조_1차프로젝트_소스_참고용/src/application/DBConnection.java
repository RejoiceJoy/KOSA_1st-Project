package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// 코드 참조 : JDBC 실습용 프로젝트 > JDBCAccount
// 주석 : 이수봉
public class DBConnection {

	private static Connection conn;		// DB 연결 객체 선언

	// 기본 생성자(private 지정하여 다른 클래스에서 객체 생성하지 못하도록 방지)
	private DBConnection() {
	}

	// static 블록(초기화 부분)
	static {
		// 환경설정 파일을 읽어오기 위한 객체 생성
		Properties properties  = new Properties();	// 환경설정 파일 참조용 객체
		Reader reader;								// 파일 읽기용 객체
		try {
			reader = new FileReader("lib/oracle.properties"); 	// 읽어올 파일 지정
			properties.load(reader);                           	// 환경설정 파일 로드
		} catch (FileNotFoundException e1) {					// 파일을 찾을 수 없을 시 예외처리
			System.out.println("예외: 지정한 파일을 찾을수없습니다 :" + e1.getMessage());
			e1.printStackTrace();
		} catch (IOException e) {								// 입출력 예외처리
			e.printStackTrace();
		}

		String driverName = properties.getProperty("driver");	// 드라이버 설정
		String url = properties.getProperty("url");				// URL 설정
		String user = properties.getProperty("user");			// 사용자 설정
		String pwd = properties.getProperty("password");		// 패스워드 설정

		try {
			Class.forName(driverName);							// 드라이버 로드
			conn = DriverManager.getConnection(url, user, pwd);	// 연결 객체 생성(DB 접속)
			System.out.println("connection success");			// 연결 성공 확인
		} catch (ClassNotFoundException e) {					// 드라이버 클래스 찾지 못할 시 예외처리
			System.out.println("예외: 드라이버로드 실패 :" + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {								// DB 연결 실패 시 예외처리(SQL오류)
			System.out.println("예외: connection fail :" + e.getMessage());
			e.printStackTrace();
		}
	}

	// DB 연결 객체를 반환하는 메소드 정의
	public static Connection getConnection() {
		return conn;	// 객체 반환
	}

}
