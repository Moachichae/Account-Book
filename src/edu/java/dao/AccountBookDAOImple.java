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
	
	
	// singleton 패턴
	private static AccountBookDAOImple accountInstance = null;

	// private 생성자
	private AccountBookDAOImple() {	 	
		try {
			DriverManager.registerDriver(new OracleDriver());		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// singleton pattern 인스턴스 생성
	public static AccountBookDAOImple getInstance() {		
		if (accountInstance == null) {			
			accountInstance = new AccountBookDAOImple();
		}
		return accountInstance;
	}
	
	
	
	
	
	
	

	@SuppressWarnings("resource")
	@Override
	public int insertAccountBook(AccountBookVO vo) { // 입출금
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		int totalAsset = logMain.memberDAO.selectMember(logMain.id).getTotalAsset(); // TODO : 총액 불러오기
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_ACCOUNT_BOOK_INSERT);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getDate_());
			pstmt.setString(3, vo.getIncomeType());
			pstmt.setString(4, vo.getAssetType());
			pstmt.setString(5, vo.getDetail());
			pstmt.setInt(6, vo.getMoney());							
			result = pstmt.executeUpdate();	// 입출금 완료 								

			if (vo.getIncomeType().equals("수입")) { // incomType이 입금이면
				totalAsset = totalAsset + vo.getMoney();
			} else if (vo.getIncomeType().equals("지출")) { // incomType이 출금이면
				totalAsset = totalAsset - vo.getMoney();
			}
			pstmt = conn.prepareStatement(SQL_UPDATE_TOTAL_ASSET); // 잔액수정
			pstmt.setInt(1, totalAsset); // 잔액 입력			
			pstmt.setString(2, logMain.id); // 수정할 회원정보의 Id
			result = result * pstmt.executeUpdate(); // 수정 완료 값 * 잔액 수정 완료 값 (둘다 1이 나와야 처리)
		
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
	public ArrayList<AccountBookVO> selectAccountBook() { // 로그인 id 가계부 내역 전체 조회
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<AccountBookVO> list = new ArrayList<AccountBookVO>();
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_ACCOUNT_BOOK_SELECT);
			pstmt.setString(1, logMain.id); // 로그인한 회원 아이디
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
	public AccountBookVO selectAccountBook(int cid) { // 해당 cid 번호 조회
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AccountBookVO vo = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_ACCOUNT_BOOK_SELECT_BY_CID);
			pstmt.setInt(1, cid); // 로그인한 회원 아이디
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
	public ArrayList<AccountBookVO> selectAccountBook(String date) { // 해당 날짜 조회
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<AccountBookVO> list = new ArrayList<AccountBookVO>();
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_ACCOUNT_BOOK_SELECT_BY_DATE);
			pstmt.setString(1, date); // 로그인한 회원 아이디
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
	public int updateAccountBook(AccountBookVO vo, int cid) { // 수정
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		int totalAsset = logMain.memberDAO.selectMember(logMain.id).getTotalAsset(); // TODO : 총액 불러오기
		AccountBookVO beforeVo = selectAccountBook(cid); // 수정 전 accBookVo
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
						
			
			

			if (vo.getIncomeType().equals("수입")) {
				if (beforeType.equals("수입")) {
					totalAsset = totalAsset - (beforeMoney - vo.getMoney());
				} else if (beforeType.equals("지출")) {
					totalAsset = totalAsset - (-beforeMoney - vo.getMoney());
				}

			} else if (vo.getIncomeType().equals("지출")) {
				if (beforeType.equals("수입")) {
					totalAsset = totalAsset - (beforeMoney + vo.getMoney());
				} else if (beforeType.equals("지출")) {
					totalAsset = totalAsset - (-beforeMoney + vo.getMoney());
				}

			}
			pstmt = conn.prepareStatement(SQL_UPDATE_TOTAL_ASSET); // 잔액수정
			pstmt.setInt(1, totalAsset); // 잔액 입력

			pstmt.setString(2, logMain.id); // 수정할 회원정보의 Id
			result = result * pstmt.executeUpdate(); // 수정 완료 값 * 잔액 수정 완료 값 (둘다 1이 나와야 처리)

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
	public int deleteAccountBook(AccountBookVO vo) { // 삭제
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		int totalAsset = logMain.memberDAO.selectMember(logMain.id).getTotalAsset(); // TODO : 총액 불러오기
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_ACCOUNT_BOOK_DELETE);
			pstmt.setInt(1, vo.getcid()); // 삭제할 회원 cid number
			result = pstmt.executeUpdate();

			if (vo.getIncomeType().equals("수입")) { // incomType이 수입이면
				totalAsset = totalAsset - vo.getMoney();
			} else if (vo.getIncomeType().equals("지출")) { // incomType이 지출이면
				totalAsset = totalAsset + vo.getMoney();
			}
			pstmt = conn.prepareStatement(SQL_UPDATE_TOTAL_ASSET); // 잔액수정
			pstmt.setInt(1, totalAsset); // 잔액 입력
			pstmt.setString(2, logMain.id); // 수정할 회원정보의 Id
			result = result * pstmt.executeUpdate(); // 삭제 완료 값 * 잔액 수정 완료 값 (둘다 1이 나와야 처리)
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












