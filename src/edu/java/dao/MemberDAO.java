package edu.java.dao;

import edu.java.vo.MemberVO;





public interface MemberDAO {
	// ȸ�����Ա�� 
	public abstract int insertMember(MemberVO vo);
	// ���̵� �ߺ� Ȯ��
	public abstract int overlapId(String textId);
	// ���̵� ã��
	public abstract String selectId(String textName, String textPhone);
	// ��й�ȣ ã��	
	public abstract String selectPw(String textId, String textEmail);
	// �α���
	public abstract int logIn(String textId, String textPw);
	
	// ȸ������ ����
	public abstract MemberVO selectMember(String logId);	
	// ȸ������ ����
	public abstract int deleteMember(String textId, String textPw);	
	// ȸ������ ����
	public abstract int updateMember(MemberVO vo);
	
	
	
	
	
} // end MemberDAO
