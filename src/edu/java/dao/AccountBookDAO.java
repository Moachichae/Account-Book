package edu.java.dao;

import java.util.ArrayList;

import edu.java.vo.AccountBookVO;

public interface AccountBookDAO {
	    // 입출금
		public abstract int insertAccountBook(AccountBookVO vo);
		// 전체 조회
		public abstract ArrayList<AccountBookVO> selectAccountBook();	
		// cid 번호 조회
		public abstract AccountBookVO selectAccountBook(int cid);
		// 해당 날짜 조회
		public abstract ArrayList<AccountBookVO> selectAccountBook(String date);
		// 수정
		public abstract int updateAccountBook(AccountBookVO vo , int cid);
		// 삭제
		public abstract int deleteAccountBook(AccountBookVO vo);
	
	
	
	
	
} // end AccountBookDAO
