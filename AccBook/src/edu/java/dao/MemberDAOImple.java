package edu.java.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.java.query.OracleQuery;
import edu.java.vo.MemberVO;
import oracle.jdbc.driver.OracleDriver;

public class MemberDAOImple implements MemberDAO, OracleQuery {

	// singleton 패턴
	private static MemberDAOImple MemberInstance = null;

	// private 생성자
	private MemberDAOImple() {
		try {
			DriverManager.registerDriver(new OracleDriver());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// singleton pattern 인스턴스 생성
	public static MemberDAOImple getInstance() {
		if (MemberInstance == null) {
			MemberInstance = new MemberDAOImple();
		}
		return MemberInstance;
	}

	@Override
	public int insertMember(MemberVO vo) { // 회원 가입
		Connection conn = null;
		PreparedStatement pstmt = null;

		int result = 0;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_MEMBER_INSERT);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getPhone());
			pstmt.setString(5, vo.getEmail());
			try {
				result = pstmt.executeUpdate(); // 0 실패 , 1 성공
			} catch (Exception e) {
				return result;
			}

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
	} // end insertMember()

	@Override
	public int overlapId(String textId) { // 아이디 중복확인
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_ID_OVERlAP);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String name = rs.getString(COL_ID);
				if (name.equals(textId)) {
					return 1;
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	} // end overlapId()

	@Override
	public String selectId(String textName, String textPhone) { // id 찾기
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_FIND_ID);
			pstmt.setString(1, textName);
			pstmt.setString(2, textPhone);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getString(COL_ID);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	} // end selectId

	@Override
	public String selectPw(String textId, String textEmail) {// pw 찾기
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_FIND_PW);
			pstmt.setString(1, textId);
			pstmt.setString(2, textEmail);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getString(COL_PW);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;

	} // end selectPw

	@Override
	public int logIn(String textId, String textPw) { // 로그인
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_LOGIN);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String id = rs.getString(COL_ID);
				String pw = rs.getString(COL_PW);
				if (id.equals(textId) && pw.equals(textPw)) {
					return 1;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;

	} // end logIn()

	@Override
	public MemberVO selectMember(String logId) { // 회원정보 가져오기
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO vo = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_SELECT_MEMBER);
			pstmt.setString(1, logId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String id = rs.getString(COL_ID);
				String pw = rs.getString(COL_PW);
				String name = rs.getString(COL_NAME);
				String phone = rs.getString(COL_PHONE);
				String email = rs.getString(COL_EMAIL);
				int totalAsset = rs.getInt(COL_TOTAL_ASSET);

				vo = new MemberVO(id, pw, name, phone, email, totalAsset);
			}
			return vo;

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
	} // end selectMember()

	@SuppressWarnings("resource")
	public int deleteMember(String textId, String textPw) { // 회원탈퇴
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(SQL_DEL_MEM_DETAIL);
			pstmt.setString(1, textId);
			result = pstmt.executeUpdate();

			pstmt = conn.prepareStatement(SQL_DEL_MEM);
			pstmt.setString(1, textId);
			pstmt.setString(2, textPw);
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;

	} // end deleteMember()

	

	

	@Override
	public int updateMember(MemberVO vo) { // 회원정보 수정 > 업데이트
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
//			System.out.println("DB 연결 성공 > 수정 업데이트 준비");
			pstmt = conn.prepareStatement(SQL_UPDATE_MEM);
			pstmt.setString(1, vo.getPw());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getPhone());
			pstmt.setString(4, vo.getEmail());
			pstmt.setString(5, vo.getId());
			result = pstmt.executeUpdate(); // 0 실패 , 1 성공

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

	} // end updateMember()

} // end MemberDAOImple
