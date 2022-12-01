package application;

import itinerary.ItineraryVO;
import member.MemberVO;

// 로그인한 사용자의 정보를 VO 객체 형태로 담아 프로그램 종료 시까지 유지하는 클래스 정의
// 작성자 : 이수봉
public class UserInfo {
	public static MemberVO memberVO;			// 로그인한 사용자의 회원정보 VO
	public static ItineraryVO itineraryVO;		// 로그인한 사용자가 가장 최근 생성한 여행 일정 정보 VO
}
