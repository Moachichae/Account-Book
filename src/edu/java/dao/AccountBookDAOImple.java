package edu.java.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.java.accountBook.logMain;
import edu.java.query.OracleQuery;
import edu.java.vo.AccountBookVO;
import oracle.jdbc.driver.OracleDriver;

public class AccountBookDAOImple implements AccountBookDAO,OracleQuery {
	
//	private int totalAsset = logMain.memberDAO.selectMember(logMain.id).getTotalAsset();
	
	
	// singleton ����
	private static AccountBookDAOImple accountInstance = null;

	// private ������
	private AccountBookDAOImple() {	 	
		try {
			DriverManager.registerDriver(new OracleDriver());		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// singleton pattern �ν��Ͻ� ����
	public static AccountBookDAOImple getInstance() {		
		if (accountInstance == null) {			
			accountInstance = new AccountBookDAOImple();
		}
		return accountInstance;
	}
	
	
	
	
	
	
	

	@SuppressWarnings("resource")
	@Override
	public int insertAccountBook(AccountBookVO vo) { // �����
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		int totalAsset = logMain.memberDAO.selectMember(logMain.id).getTotalAsset(); // TODO : �Ѿ� �ҷ�����
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_ACCOUNT_BOOK_INSERT);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getDate_());
			pstmt.setString(3, vo.getIncomeType());
			pstmt.setString(4, vo.getAssetType());
			pstmt.setString(5, vo.getDetail());
			pstmt.setInt(6, vo.getMoney());							
			result = pstmt.executeUpdate();	// ����� �Ϸ� 								

			if (vo.getIncomeType().equals("����")) { // incomType�� �Ա��̸�
				totalAsset = totalAsset + vo.getMoney();
			} else if (vo.getIncomeType().equals("����")) { // incomType�� ����̸�
				totalAsset = totalAsset - vo.getMoney();
			}
			pstmt = conn.prepareStatement(SQL_UPDATE_TOTAL_ASSET); // �ܾ׼���
			pstmt.setInt(1, totalAsset); // �ܾ� �Է�			
			pstmt.setString(2, logMain.id); // ������ ȸ�������� Id
			result = result * pstmt.executeUpdate(); // ���� �Ϸ� �� * �ܾ� ���� �Ϸ� �� (�Ѵ� 1�� ���;� ó��)
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	} // end insertAccountBook

	@Override
	public ArrayList<AccountBookVO> selectAccountBook() { // �α��� id ����� ���� ��ü ��ȸ
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<AccountBookVO> list = new ArrayList<AccountBookVO>();
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_ACCOUNT_BOOK_SELECT);
			pstmt.setString(1, logMain.id); // �α����� ȸ�� ���̵�
			rs = pstmt.executeQuery();

			String date = null;
			String[] splitDate = null;
			String incomType = null;
			String assetType = null;
			String detail = null;
			int money = 0;
			int cid = 0;
			while (rs.next()) {
				date = rs.getString(COL_DATE); // YY-MM-DD HH:MM:SS
				splitDate = date.split(" "); // splitDate[0] : YY-MM-DD splitDate[1] : HH:MM:SS
				incomType = rs.getString(COL_INCOME_TYPE);
				assetType = rs.getString(COL_ASSET_TYPE);
				detail = rs.getString(COL_DETAIL);
				money = rs.getInt(COL_MONEY);
				cid = rs.getInt(COL_CID);
				list.add(new AccountBookVO(logMain.id, splitDate[0], incomType, assetType, detail, money, cid));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	} // end selectAccountBook()

	@SuppressWarnings("null")
	@Override
	public AccountBookVO selectAccountBook(int cid) { // �ش� cid ��ȣ ��ȸ
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AccountBookVO vo = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_ACCOUNT_BOOK_SELECT_BY_CID);
			pstmt.setInt(1, cid); // �α����� ȸ�� ���̵�
			rs = pstmt.executeQuery();
			String date = null;
			String[] splitDate = null;
			String incomType = null;
			String assetType = null;
			String detail = null;
			int money = 0;
			if (rs.next()) {
				date = rs.getString(COL_DATE); // YY-MM-DD HH:MM:SS
				splitDate = date.split(" "); // splitDate[0] : YY-MM-DD splitDate[1] : HH:MM:SS
				incomType = rs.getString(COL_INCOME_TYPE);
				assetType = rs.getString(COL_ASSET_TYPE);
				detail = rs.getString(COL_DETAIL);
				money = rs.getInt(COL_MONEY);
				vo = new AccountBookVO(logMain.id, splitDate[0], incomType, assetType, detail, money, cid);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return vo;
	} // end selectAccountBook

	@SuppressWarnings("null")
	public ArrayList<AccountBookVO> selectAccountBook(String date) { // �ش� ��¥ ��ȸ
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<AccountBookVO> list = new ArrayList<AccountBookVO>();
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_ACCOUNT_BOOK_SELECT_BY_DATE);
			pstmt.setString(1, date); // �α����� ȸ�� ���̵�
			pstmt.setString(2, logMain.id);
			rs = pstmt.executeQuery();
			String[] splitDate = null;
			String incomType = null;
			String assetType = null;
			String detail = null;
			int money = 0;
			int cid = 0;
			while (rs.next()) {
				date = rs.getString(COL_DATE); // YY-MM-DD HH:MM:SS
				splitDate = date.split(" "); // splitDate[0] : YY-MM-DD splitDate[1] : HH:MM:SS
				incomType = rs.getString(COL_INCOME_TYPE);
				assetType = rs.getString(COL_ASSET_TYPE);
				detail = rs.getString(COL_DETAIL);
				money = rs.getInt(COL_MONEY);
				cid = rs.getInt(COL_CID);
				list.add(new AccountBookVO(logMain.id, splitDate[0], incomType, assetType, detail, money, cid));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	} // end selectAccountBook

	@SuppressWarnings("resource")
	@Override
	public int updateAccountBook(AccountBookVO vo, int cid) { // ����
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		int totalAsset = logMain.memberDAO.selectMember(logMain.id).getTotalAsset(); // TODO : �Ѿ� �ҷ�����
		AccountBookVO beforeVo = selectAccountBook(cid); // ���� �� accBookVo
		String beforeType = beforeVo.getIncomeType();
		int beforeMoney = beforeVo.getMoney();
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_ACCOUNT_BOOK_UPDATE);
			pstmt.setString(1, vo.getDate_());
			pstmt.setString(2, vo.getIncomeType());
			pstmt.setString(3, vo.getAssetType());
			pstmt.setString(4, vo.getDetail());
			pstmt.setInt(5, vo.getMoney());
			pstmt.setInt(6, cid);
			
			result = pstmt.executeUpdate();				
						
			
			

			if (vo.getIncomeType().equals("����")) {
				if (beforeType.equals("����")) {
					totalAsset = totalAsset - (beforeMoney - vo.getMoney());
				} else if (beforeType.equals("����")) {
					totalAsset = totalAsset - (-beforeMoney - vo.getMoney());
				}

			} else if (vo.getIncomeType().equals("����")) {
				if (beforeType.equals("����")) {
					totalAsset = totalAsset - (beforeMoney + vo.getMoney());
				} else if (beforeType.equals("����")) {
					totalAsset = totalAsset - (-beforeMoney + vo.getMoney());
				}

			}
			pstmt = conn.prepareStatement(SQL_UPDATE_TOTAL_ASSET); // �ܾ׼���
			pstmt.setInt(1, totalAsset); // �ܾ� �Է�

			pstmt.setString(2, logMain.id); // ������ ȸ�������� Id
			result = result * pstmt.executeUpdate(); // ���� �Ϸ� �� * �ܾ� ���� �Ϸ� �� (�Ѵ� 1�� ���;� ó��)

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	} // end updateAccountBook()

	@SuppressWarnings("resource")
	@Override
	public int deleteAccountBook(AccountBookVO vo) { // ����
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		int totalAsset = logMain.memberDAO.selectMember(logMain.id).getTotalAsset(); // TODO : �Ѿ� �ҷ�����
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_ACCOUNT_BOOK_DELETE);
			pstmt.setInt(1, vo.getcid()); // ������ ȸ�� cid number
			result = pstmt.executeUpdate();

			if (vo.getIncomeType().equals("����")) { // incomType�� �����̸�
				totalAsset = totalAsset - vo.getMoney();
			} else if (vo.getIncomeType().equals("����")) { // incomType�� �����̸�
				totalAsset = totalAsset + vo.getMoney();
			}
			pstmt = conn.prepareStatement(SQL_UPDATE_TOTAL_ASSET); // �ܾ׼���
			pstmt.setInt(1, totalAsset); // �ܾ� �Է�
			pstmt.setString(2, logMain.id); // ������ ȸ�������� Id
			result = result * pstmt.executeUpdate(); // ���� �Ϸ� �� * �ܾ� ���� �Ϸ� �� (�Ѵ� 1�� ���;� ó��)
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	} // end deleteAccountBook()

	
	
	
	
	
	
} // end AccountBookDAOImple












