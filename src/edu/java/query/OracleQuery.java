package edu.java.query;

//JDBC���� ���� �����, SQL ������� ����
public interface OracleQuery {

	public static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl"; // ������ ����Ŭ DB ��� ..??
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
	// ȸ������
	public static final String SQL_MEMBER_INSERT = "insert into " + MEMBER_TABLE_NAME + " values(?, ?, ?, ?, ?, 0)"; // insert
	// ���̵� �ߺ� Ȯ��
	public static final String SQL_ID_OVERlAP = "select " + COL_ID + " FROM " + MEMBER_TABLE_NAME;
	// �α���üũ
	public static final String SQL_LOGIN = "select * " + " FROM " + MEMBER_TABLE_NAME;
	// ���̵� ã��
	public static final String SQL_FIND_ID = "select " + COL_ID + " from " + MEMBER_TABLE_NAME + " where " + COL_NAME
			+ " = ? and " + COL_PHONE + " = ?";
	// ��й�ȣ ã��
	public static final String SQL_FIND_PW = "select " + COL_PW + " from " + MEMBER_TABLE_NAME + " where " + COL_ID
			+ " = ? and " + COL_EMAIL + " = ?";
	// ȸ����������
	public static final String SQL_DEL_MEM = "delete from " + MEMBER_TABLE_NAME + " where " + COL_ID + " = ? and "
			+ COL_PW + " = ?";
	public static final String SQL_DEL_MEM_DETAIL = "delete from " + ACCOUNT_BOOK_TABLE_NAME + " where " + COL_ID
			+ " = ?";
	// ȸ���������� > ��������
	public static final String SQL_EDIT_MEM = "select * from " + MEMBER_TABLE_NAME + " where " + COL_ID + " = ?";
	// ȸ����������>������Ʈ
	public static final String SQL_UPDATE_MEM = "update " + MEMBER_TABLE_NAME + " set " + COL_PW + " = ?, " + COL_NAME
			+ " = ?, " + COL_PHONE + " = ?, " + COL_EMAIL + " = ? where " + COL_ID + " = ?";

//	delete from member where id = '?' and pw = '?';
	// ȸ�� ���� ��ȸ
	public static final String SQL_SELECT_MEMBER = "select * from " + MEMBER_TABLE_NAME + " where " + COL_ID + " = ?";
	// �ܾ� ����
	public static final String SQL_UPDATE_TOTAL_ASSET = "update " + MEMBER_TABLE_NAME + " set " + COL_TOTAL_ASSET
			+ " = ? where " + COL_ID + " = ?";

	// account_bookTable
	// String COL_ID ���̵�(���� �Ϸ�)
	public static final String COL_DATE = "date_";
	public static final String COL_MONEY = "money";
	public static final String COL_INCOME_TYPE = "income_type";
	public static final String COL_ASSET_TYPE = "asset_type";
	public static final String COL_DETAIL = "detail";
	public static final String COL_CID = "cid";

	// �����
	public static final String SQL_ACCOUNT_BOOK_INSERT = "insert into " + ACCOUNT_BOOK_TABLE_NAME
			+ " values (?,?,?,?,?,?,cid_pk.nextval)"; // id,��¥,income_type,account_type,detail,money
	// ��ü �˻�
	public static final String SQL_ACCOUNT_BOOK_SELECT = "select * from " + ACCOUNT_BOOK_TABLE_NAME + " where " + COL_ID
			+ " = ? order by " + COL_DATE + " desc";
	// cid ��ȣ�� �˻�
	public static final String SQL_ACCOUNT_BOOK_SELECT_BY_CID = "select * from " + ACCOUNT_BOOK_TABLE_NAME + " where "
			+ COL_CID + " = ? ";
	// �ش� ��¥�� �˻�
	public static final String SQL_ACCOUNT_BOOK_SELECT_BY_DATE = "select * from " + ACCOUNT_BOOK_TABLE_NAME + " where "
			+ COL_DATE + " = ? and " + COL_ID + " = ? order by " + COL_DATE + " desc";
	// ����
	public static final String SQL_ACCOUNT_BOOK_UPDATE = "update " + ACCOUNT_BOOK_TABLE_NAME + " set " + COL_DATE
			+ " = ?, " + COL_INCOME_TYPE + " = ?, " + COL_ASSET_TYPE + " = ?, " + COL_DETAIL + " = ?, " + COL_MONEY
			+ " = ? where " + COL_CID + " = ? ";

	// ����
//	public static final String SQL_ACCOUNT_BOOK_DELETE = "delete from " + ACCOUNT_BOOK_TABLE_NAME
//			+ " where " + COL_ID + " = ? " + COL_ID + " = ? " + COL_ID + " = ? " + COL_ID + " = ? ";

	public static final String SQL_ACCOUNT_BOOK_DELETE = "delete from " + ACCOUNT_BOOK_TABLE_NAME + " where " + COL_CID
			+ " = ? ";

} // end OracleQuery
