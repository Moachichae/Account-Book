package edu.java.dao;

import edu.java.vo.MemberVO;





public interface MemberDAO {
	// 회원가입기능 
	public abstract int insertMember(MemberVO vo);
	// 아이디 중복 확인
	public abstract int overlapId(String textId);
	// 아이디 찾기
	public abstract String selectId(String textName, String textPhone);
	// 비밀번호 찾기	
	public abstract String selectPw(String textId, String textEmail);
	// 로그인
	public abstract int logIn(String textId, String textPw);
	
	// 회원정보 선택
	public abstract MemberVO selectMember(String logId);	
	// 회원정보 삭제
	public abstract int deleteMember(String textId, String textPw);	
	// 회원정보 수정
	public abstract int updateMember(MemberVO vo);
	
	
	
	
	
} // end MemberDAO
