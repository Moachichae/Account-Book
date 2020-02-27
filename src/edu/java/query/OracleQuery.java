package edu.java.query;

//JDBC에서 사용될 상수들, SQL 문장들을 정의
public interface OracleQuery {

	public static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl"; // 접속할 오라클 DB 경로 ..??
	public static final String USER = "scott";
	public static final String PASSWORD = "tiger";
	// meberTable
	public static final String MEMBER_TABLE_NAME = "member";
	public static final String COL_ID = "id";
	public static final String COL_PW = "pw";
	public static final String COL_NAME = "name";
	public static final String COL_PHONE = "phone";
	public static final String COL_EMAIL = "email";
	public static final String COL_TOTAL_ASSET = "total_asset";
	public static final String ACCOUNT_BOOK_TABLE_NAME = "account_book";
	// 회원가입
	public static final String SQL_MEMBER_INSERT = "insert into " + MEMBER_TABLE_NAME + " values(?, ?, ?, ?, ?, 0)"; // insert
	// 아이디 중복 확인
	public static final String SQL_ID_OVERlAP = "select " + COL_ID + " FROM " + MEMBER_TABLE_NAME;
	// 로그인체크
	public static final String SQL_LOGIN = "select * " + " FROM " + MEMBER_TABLE_NAME;
	// 아이디 찾기
	public static final String SQL_FIND_ID = "select " + COL_ID + " from " + MEMBER_TABLE_NAME + " where " + COL_NAME
			+ " = ? and " + COL_PHONE + " = ?";
	// 비밀번호 찾기
	public static final String SQL_FIND_PW = "select " + COL_PW + " from " + MEMBER_TABLE_NAME + " where " + COL_ID
			+ " = ? and " + COL_EMAIL + " = ?";
	// 회원정보삭제
	public static final String SQL_DEL_MEM = "delete from " + MEMBER_TABLE_NAME + " where " + COL_ID + " = ? and "
			+ COL_PW + " = ?";
	public static final String SQL_DEL_MEM_DETAIL = "delete from " + ACCOUNT_BOOK_TABLE_NAME + " where " + COL_ID
			+ " = ?";
	// 회원정보수정 > 가져오기
	public static final String SQL_EDIT_MEM = "select * from " + MEMBER_TABLE_NAME + " where " + COL_ID + " = ?";
	// 회원정보수정>업데이트
	public static final String SQL_UPDATE_MEM = "update " + MEMBER_TABLE_NAME + " set " + COL_PW + " = ?, " + COL_NAME
			+ " = ?, " + COL_PHONE + " = ?, " + COL_EMAIL + " = ? where " + COL_ID + " = ?";

//	delete from member where id = '?' and pw = '?';
	// 회원 정보 조회
	public static final String SQL_SELECT_MEMBER = "select * from " + MEMBER_TABLE_NAME + " where " + COL_ID + " = ?";
	// 잔액 수정
	public static final String SQL_UPDATE_TOTAL_ASSET = "update " + MEMBER_TABLE_NAME + " set " + COL_TOTAL_ASSET
			+ " = ? where " + COL_ID + " = ?";

	// account_bookTable
	// String COL_ID 아이디(생성 완료)
	public static final String COL_DATE = "date_";
	public static final String COL_MONEY = "money";
	public static final String COL_INCOME_TYPE = "income_type";
	public static final String COL_ASSET_TYPE = "asset_type";
	public static final String COL_DETAIL = "detail";
	public static final String COL_CID = "cid";

	// 입출금
	public static final String SQL_ACCOUNT_BOOK_INSERT = "insert into " + ACCOUNT_BOOK_TABLE_NAME
			+ " values (?,?,?,?,?,?,cid_pk.nextval)"; // id,날짜,income_type,account_type,detail,money
	// 전체 검색
	public static final String SQL_ACCOUNT_BOOK_SELECT = "select * from " + ACCOUNT_BOOK_TABLE_NAME + " where " + COL_ID
			+ " = ? order by " + COL_DATE + " desc";
	// cid 번호로 검색
	public static final String SQL_ACCOUNT_BOOK_SELECT_BY_CID = "select * from " + ACCOUNT_BOOK_TABLE_NAME + " where "
			+ COL_CID + " = ? ";
	// 해당 날짜로 검색
	public static final String SQL_ACCOUNT_BOOK_SELECT_BY_DATE = "select * from " + ACCOUNT_BOOK_TABLE_NAME + " where "
			+ COL_DATE + " = ? and " + COL_ID + " = ? order by " + COL_DATE + " desc";
	// 수정
	public static final String SQL_ACCOUNT_BOOK_UPDATE = "update " + ACCOUNT_BOOK_TABLE_NAME + " set " + COL_DATE
			+ " = ?, " + COL_INCOME_TYPE + " = ?, " + COL_ASSET_TYPE + " = ?, " + COL_DETAIL + " = ?, " + COL_MONEY
			+ " = ? where " + COL_CID + " = ? ";

	// 삭제
//	public static final String SQL_ACCOUNT_BOOK_DELETE = "delete from " + ACCOUNT_BOOK_TABLE_NAME
//			+ " where " + COL_ID + " = ? " + COL_ID + " = ? " + COL_ID + " = ? " + COL_ID + " = ? ";

	public static final String SQL_ACCOUNT_BOOK_DELETE = "delete from " + ACCOUNT_BOOK_TABLE_NAME + " where " + COL_CID
			+ " = ? ";

} // end OracleQuery
