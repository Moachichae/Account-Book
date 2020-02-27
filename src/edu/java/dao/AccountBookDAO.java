package edu.java.dao;

import java.util.ArrayList;

import edu.java.vo.AccountBookVO;

public interface AccountBookDAO {
	    // �����
		public abstract int insertAccountBook(AccountBookVO vo);
		// ��ü ��ȸ
		public abstract ArrayList<AccountBookVO> selectAccountBook();	
		// cid ��ȣ ��ȸ
		public abstract AccountBookVO selectAccountBook(int cid);
		// �ش� ��¥ ��ȸ
		public abstract ArrayList<AccountBookVO> selectAccountBook(String date);
		// ����
		public abstract int updateAccountBook(AccountBookVO vo , int cid);
		// ����
		public abstract int deleteAccountBook(AccountBookVO vo);
	
	
	
	
	
} // end AccountBookDAO
