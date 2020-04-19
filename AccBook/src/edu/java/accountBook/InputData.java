package edu.java.accountBook;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import edu.java.vo.AccountBookVO;

@SuppressWarnings("serial")
public class InputData extends JDialog {

	private JPanel contentPane;
	private int index;
	private String[] IMAGES = { "res/1.png" };
	private JTable table;
	private String[] colNames = { "No", "날짜", "수입 / 지출", "자산형태", "내역", "금액", "cid" };
	private Object[] records = new Object[colNames.length];
	private DefaultTableModel tableModel;
	private JTextField textDetail;
	private JTextField textMoney;
	private JTextField textdate;
	private int year, month, date;
	private AccountBookVO selectVO;
	private JTextArea areaLog;
	private JButton btnDatesearch, btnAllSearch, confirmbtn;
	ArrayList<AccountBookVO> selectList = new ArrayList<AccountBookVO>();
	int selectNo = 0;

	public InputData(int year, int month, int date) {
		this.year = year; // 현재 날짜
		this.month = month;
		this.date = date;
		initialize();
	}

	private void initialize() {

		setModal(true);
		contentPane = new JPanel();
		setBounds(100, 100, 700, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblImage = new JLabel(); // 이미지 Label
		lblImage.setBounds(40, 25, 100, 100);
		lblImage.setIcon(new ImageIcon(IMAGES[index]));
		contentPane.add(lblImage);

		JScrollPane scrollPane = new JScrollPane(); // 조회 출력
		scrollPane.setBounds(222, 101, 450, 450);
		contentPane.add(scrollPane);

		tableModel = new DefaultTableModel(colNames, 0); // 조회 출력
		table = new JTable(tableModel);
		table.getColumn("cid").setWidth(0);
		table.getColumn("cid").setMinWidth(0);
		table.getColumn("cid").setMaxWidth(0);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// 테이블 모델 클릭시 그 행의 정보 가져오기
				int row = table.getSelectedRow();
				selectNo = (int) tableModel.getValueAt(row, 0);
				String id = logMain.id;
				String date_ = (String) tableModel.getValueAt(row, 1);
				String incomeType = (String) tableModel.getValueAt(row, 2);
				String assetType = (String) tableModel.getValueAt(row, 3);
				String detail = (String) tableModel.getValueAt(row, 4);
				int money = (Integer) tableModel.getValueAt(row, 5);
				int cid = (int) tableModel.getValueAt(row, 6);
				selectVO = new AccountBookVO(id, date_, incomeType, assetType, detail, money, cid);
			}

		});
		scrollPane.setViewportView(table);

		btnDatesearch = new JButton("날짜 검색"); // 입력한 날짜 검색
		btnDatesearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectDateAccountBook();
			}
		});
		btnDatesearch.setBounds(20, 160, 90, 30);
		contentPane.add(btnDatesearch);

		btnAllSearch = new JButton("전체 검색"); // 전체검색
		btnAllSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectAllAccountBook();
			}
		});
		btnAllSearch.setBounds(120, 160, 90, 30);
		contentPane.add(btnAllSearch);

		JLabel lblDate = new JLabel("날짜");
		lblDate.setFont(new Font("휴먼편지체", Font.PLAIN, 20));
		lblDate.setBounds(12, 200, 60, 30);
		contentPane.add(lblDate);

		String today = String.format("%d-%02d-%02d", year, month, date); // 오늘 날짜 변환
		textdate = new JTextField(today);
		textdate.setBounds(70, 200, 140, 30);
		contentPane.add(textdate);
		textdate.setColumns(10);

		JLabel lblIncome = new JLabel("\uC218\uC785/\uC9C0\uCD9C");
		lblIncome.setFont(new Font("휴먼편지체", Font.PLAIN, 20));
		lblIncome.setBounds(12, 240, 80, 30);
		contentPane.add(lblIncome);

		JLabel lblAsset = new JLabel("\uC790\uC0B0\uD615\uD0DC");
		lblAsset.setFont(new Font("휴먼편지체", Font.PLAIN, 20));
		lblAsset.setBounds(12, 280, 80, 30);
		contentPane.add(lblAsset);

		JLabel lblDetail = new JLabel("\uB0B4\uC5ED");
		lblDetail.setFont(new Font("휴먼편지체", Font.PLAIN, 20));
		lblDetail.setBounds(12, 320, 60, 30);
		contentPane.add(lblDetail);

		JLabel lblMoney = new JLabel("\uAE08\uC561");
		lblMoney.setFont(new Font("휴먼편지체", Font.PLAIN, 20));
		lblMoney.setBounds(12, 360, 60, 30);
		contentPane.add(lblMoney);

		textDetail = new JTextField("식비");
		textDetail.setColumns(10);
		textDetail.setBounds(110, 320, 100, 30);
		contentPane.add(textDetail);

		textMoney = new JTextField();
		textMoney.setColumns(10);
		textMoney.setBounds(110, 360, 100, 30);
		contentPane.add(textMoney);

		areaLog = new JTextArea(); // 로그출력 textArea
		areaLog.setBounds(12, 446, 202, 64);
		contentPane.add(areaLog);

		JComboBox<String> incomCombo = new JComboBox<String>();
		incomCombo.setBounds(110, 240, 100, 30);
		ArrayList<String> incomList = new ArrayList<String>();
		incomList.add("수입");
		incomList.add("지출");
		DefaultComboBoxModel<String> incomCModel = new DefaultComboBoxModel<String>();
		for (int i = 0; i < incomList.size(); i++) {
			incomCModel.addElement(incomList.get(i));
		}
		incomCombo.setModel(incomCModel);
		contentPane.add(incomCombo);

		JComboBox<String> assetCombo = new JComboBox<String>();
		assetCombo.setBounds(110, 280, 100, 30);
		DefaultComboBoxModel<String> assetCModel = new DefaultComboBoxModel<String>();
		ArrayList<String> assetList = new ArrayList<String>();
		assetList.add("카드");
		assetList.add("현금");
		for (int i = 0; i < assetList.size(); i++) {
			assetCModel.addElement(assetList.get(i));
		}
		assetCombo.setModel(assetCModel);
		contentPane.add(assetCombo);

		JComboBox<String> setCombo = new JComboBox<String>();
		setCombo.setBounds(12, 400, 106, 30);
		DefaultComboBoxModel<String> setCModel = new DefaultComboBoxModel<String>();
		ArrayList<String> setList = new ArrayList<String>();
		setList.add("추가");
		setList.add("수정");
		setList.add("삭제");
		for (int i = 0; i < setList.size(); i++) {
			setCModel.addElement(setList.get(i));
		}
		setCombo.setModel(setCModel);
		contentPane.add(setCombo);

		confirmbtn = new JButton("확인");

		confirmbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (setCombo.getSelectedItem().equals("추가")) { // 입출금
					String date = textdate.getText();
					if (datePattern(date) == false) {
						return;
					}
					String id = logMain.id; // 회원 아이디 값
					String incomType = (String) incomCombo.getSelectedItem();
					String assetType = (String) assetCombo.getSelectedItem();
					String detail = textDetail.getText();
					if(detail.length() > 10) {
						JOptionPane.showMessageDialog(null, "10자 이내로 입력해주세요.", "Error", JOptionPane.WARNING_MESSAGE);
						return;
					}
					int money = textMoney(textMoney.getText());
					if (money <= 0) { // money = 0 이면 메소드 종료
						return;
					}
					
					AccountBookVO vo = new AccountBookVO(id, date, incomType, assetType, detail, money, 0);
					// TODO : CID 번호 처리
					int result = logMain.accountDAO.insertAccountBook(vo);

					if (result == 1) {
						String str = date + " " + incomType + " " + assetType + " " + detail + " \n 금액 : " + money
								+ "원 \n 입력완료";
						areaLog.setText(str);

					} else {
						JOptionPane.showMessageDialog(null, "올바른 형식으로 입력하세요.", "Error", JOptionPane.WARNING_MESSAGE);
						return;
					}
					clearTextFields();
				} // end if(setCombo.getSelectedItem().equals("추가"))

				else if (setCombo.getSelectedItem().equals("수정")) {
					String date = textdate.getText();
					if (datePattern(date) == false) {
						return;
					}
					String id = logMain.id; // 회원 아이디 값
					String incomType = (String) incomCombo.getSelectedItem();
					String assetType = (String) assetCombo.getSelectedItem();
					String detail = textDetail.getText();
					int money = textMoney(textMoney.getText());					
					AccountBookVO vo = new AccountBookVO(id, date, incomType, assetType, detail, money, 0);
					if (selectVO == null) {
						JOptionPane.showMessageDialog(null, "수정 할 테이블을 선택해 주세요.", "Error", JOptionPane.WARNING_MESSAGE);
						return;
					}
					int result = logMain.accountDAO.updateAccountBook(vo, selectVO.getcid());
//					
					if(result == 1) {
						selectList.set(selectNo,
								new AccountBookVO(id, date, incomType, assetType, detail, money, selectVO.getcid()));
						tableRePrint();
						clearTextFields();
						areaLog.setText("수정 완료");						
					} else {
						JOptionPane.showMessageDialog(null, "수정 할 테이블을 다시 선택해 주세요.", "Error", JOptionPane.WARNING_MESSAGE);
					}
				} // end else if(setCombo.getSelectedItem().equals("수정"))

				else if (setCombo.getSelectedItem().equals("삭제")) {
					if (selectVO == null) {
						JOptionPane.showMessageDialog(null, "삭제 할 테이블을 선택해 주세요.", "Error", JOptionPane.WARNING_MESSAGE);
						return;
					}
					logMain.accountDAO.deleteAccountBook(selectVO);
					selectList.remove(selectNo);
					tableRePrint();
					areaLog.setText("삭제 완료");
				} // end else if(setCombo.getSelectedItem().equals("삭제"))

				selectVO = null; // 수정 삭제 후 vo 초기화
			}
			// TODO : 입출금 버튼 클릭시 마다 달력 출력과 잔액 재출력

		});
		confirmbtn.setBounds(130, 400, 80, 30);
		contentPane.add(confirmbtn);

		
	} // end initialize

	public void selectAllAccountBook() { // 전체검색
		tableModel.setNumRows(0); // 테이블 초기화
		selectList = null;
		selectList = logMain.accountDAO.selectAccountBook();
		for (int i = 0; i < selectList.size(); i++) {
			records[0] = i;
			records[1] = selectList.get(i).getDate_();
			records[2] = selectList.get(i).getIncomeType();
			records[3] = selectList.get(i).getAssetType();
			records[4] = selectList.get(i).getDetail();
			records[5] = selectList.get(i).getMoney();
			records[6] = selectList.get(i).getcid();
			tableModel.addRow(records);
			areaLog.setText("전체 검색 완료");
		}
	} // and selectAllAccountBook()

	public void selectDateAccountBook() { // 해당날짜 검색
		tableModel.setNumRows(0); // 테이블 초기화
		String date = textdate.getText();
		if (datePattern(date) == false) {
			return;
		}
		selectList = null;
		selectList = logMain.accountDAO.selectAccountBook(date);
		for (int i = 0; i < selectList.size(); i++) {
			records[0] = i;
			records[1] = selectList.get(i).getDate_();
			records[2] = selectList.get(i).getIncomeType();
			records[3] = selectList.get(i).getAssetType();
			records[4] = selectList.get(i).getDetail();
			records[5] = selectList.get(i).getMoney();
			records[6] = selectList.get(i).getcid();
			tableModel.addRow(records);
			areaLog.setText("날짜 검색 완료");
		}
	} // and selectDateAccountBook()

	public boolean datePattern(String date) {  // 날짜 입력시 패턴 매치 메소드
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");		
		try {
			dateFormat.setLenient(false);
			dateFormat.parse(date);			
		} catch (ParseException e) {			
			JOptionPane.showMessageDialog(null,"올바른형식의 날짜를 입력해 주세요." + "\n ex)2018-02-14", "Error",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		String[] dateSplite = date.split("-",3); // 입력받은 날짜
		int y = Integer.parseInt(dateSplite[0]); // 연도
		int m = Integer.parseInt(dateSplite[1]); // 월
		int d = Integer.parseInt(dateSplite[2]); // 일
		
		Calendar cal = Calendar.getInstance();		
		cal.set(y, m - 1, 1); // 입력받은 날짜의 1일로 설정		
		int lastDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);	 // 그 달의 마지막 날		
		
		if(lastDate < d) {	// 입력받은 날짜가 그 달의 마지막 달보다 크면 		
			JOptionPane.showMessageDialog(null,"존재하지 않는 날짜 입니다." , "Error",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		
		return true;
	}; // end datePatton()

	public int textMoney(String textMoney) { // textMoney 에러 처리 및 Integer 로 형변환 메소드
		int money = 0;
		try {
			money = Integer.parseInt(textMoney);
		} catch (NumberFormatException e2) {
			JOptionPane.showMessageDialog(null, "금액을 숫자로 입력해 주세요.", "Error", JOptionPane.WARNING_MESSAGE);
			return money;

		}
		if (textMoney.equals("")) {
			JOptionPane.showMessageDialog(null, "금액을 입력해 주세요.", "Error", JOptionPane.WARNING_MESSAGE);
			return money;
		} else if (money <= 0) {
			JOptionPane.showMessageDialog(null, "금액을 양수로 입력해 주세요.", "Error", JOptionPane.WARNING_MESSAGE);
			return money;
		}

		return money;
	} // end textMoney()

	private void tableRePrint() { // 수정 삭제후 테이블 재 출력
		tableModel.setNumRows(0);
		for (int i = 0; i < selectList.size(); i++) {
			records[0] = i;
			records[1] = selectList.get(i).getDate_();
			records[2] = selectList.get(i).getIncomeType();
			records[3] = selectList.get(i).getAssetType();
			records[4] = selectList.get(i).getDetail();
			records[5] = selectList.get(i).getMoney();
			records[6] = selectList.get(i).getcid();
			tableModel.addRow(records);
		}

	} // end tableRePrint()

	// 텍스트 필드 초기화 함수
	private void clearTextFields() {
		textMoney.setText("");
	} // end clearTextFields()

		
	
	
	
	
	
	
} // end InputData
