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
	private String[] colNames = { "No", "��¥", "���� / ����", "�ڻ�����", "����", "�ݾ�", "cid" };
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
		this.year = year; // ���� ��¥
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

		JLabel lblImage = new JLabel(); // �̹��� Label
		lblImage.setBounds(40, 25, 100, 100);
		lblImage.setIcon(new ImageIcon(IMAGES[index]));
		contentPane.add(lblImage);

		JScrollPane scrollPane = new JScrollPane(); // ��ȸ ���
		scrollPane.setBounds(222, 101, 450, 450);
		contentPane.add(scrollPane);

		tableModel = new DefaultTableModel(colNames, 0); // ��ȸ ���
		table = new JTable(tableModel);
		table.getColumn("cid").setWidth(0);
		table.getColumn("cid").setMinWidth(0);
		table.getColumn("cid").setMaxWidth(0);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// ���̺� �� Ŭ���� �� ���� ���� ��������
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

		btnDatesearch = new JButton("��¥ �˻�"); // �Է��� ��¥ �˻�
		btnDatesearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectDateAccountBook();
			}
		});
		btnDatesearch.setBounds(20, 160, 90, 30);
		contentPane.add(btnDatesearch);

		btnAllSearch = new JButton("��ü �˻�"); // ��ü�˻�
		btnAllSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectAllAccountBook();
			}
		});
		btnAllSearch.setBounds(120, 160, 90, 30);
		contentPane.add(btnAllSearch);

		JLabel lblDate = new JLabel("��¥");
		lblDate.setFont(new Font("�޸�����ü", Font.PLAIN, 20));
		lblDate.setBounds(12, 200, 60, 30);
		contentPane.add(lblDate);

		String today = String.format("%d-%02d-%02d", year, month, date); // ���� ��¥ ��ȯ
		textdate = new JTextField(today);
		textdate.setBounds(70, 200, 140, 30);
		contentPane.add(textdate);
		textdate.setColumns(10);

		JLabel lblIncome = new JLabel("\uC218\uC785/\uC9C0\uCD9C");
		lblIncome.setFont(new Font("�޸�����ü", Font.PLAIN, 20));
		lblIncome.setBounds(12, 240, 80, 30);
		contentPane.add(lblIncome);

		JLabel lblAsset = new JLabel("\uC790\uC0B0\uD615\uD0DC");
		lblAsset.setFont(new Font("�޸�����ü", Font.PLAIN, 20));
		lblAsset.setBounds(12, 280, 80, 30);
		contentPane.add(lblAsset);

		JLabel lblDetail = new JLabel("\uB0B4\uC5ED");
		lblDetail.setFont(new Font("�޸�����ü", Font.PLAIN, 20));
		lblDetail.setBounds(12, 320, 60, 30);
		contentPane.add(lblDetail);

		JLabel lblMoney = new JLabel("\uAE08\uC561");
		lblMoney.setFont(new Font("�޸�����ü", Font.PLAIN, 20));
		lblMoney.setBounds(12, 360, 60, 30);
		contentPane.add(lblMoney);

		textDetail = new JTextField("�ĺ�");
		textDetail.setColumns(10);
		textDetail.setBounds(110, 320, 100, 30);
		contentPane.add(textDetail);

		textMoney = new JTextField();
		textMoney.setColumns(10);
		textMoney.setBounds(110, 360, 100, 30);
		contentPane.add(textMoney);

		areaLog = new JTextArea(); // �α���� textArea
		areaLog.setBounds(12, 446, 202, 64);
		contentPane.add(areaLog);

		JComboBox<String> incomCombo = new JComboBox<String>();
		incomCombo.setBounds(110, 240, 100, 30);
		ArrayList<String> incomList = new ArrayList<String>();
		incomList.add("����");
		incomList.add("����");
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
		assetList.add("ī��");
		assetList.add("����");
		for (int i = 0; i < assetList.size(); i++) {
			assetCModel.addElement(assetList.get(i));
		}
		assetCombo.setModel(assetCModel);
		contentPane.add(assetCombo);

		JComboBox<String> setCombo = new JComboBox<String>();
		setCombo.setBounds(12, 400, 106, 30);
		DefaultComboBoxModel<String> setCModel = new DefaultComboBoxModel<String>();
		ArrayList<String> setList = new ArrayList<String>();
		setList.add("�߰�");
		setList.add("����");
		setList.add("����");
		for (int i = 0; i < setList.size(); i++) {
			setCModel.addElement(setList.get(i));
		}
		setCombo.setModel(setCModel);
		contentPane.add(setCombo);

		confirmbtn = new JButton("Ȯ��");

		confirmbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (setCombo.getSelectedItem().equals("�߰�")) { // �����
					String date = textdate.getText();
					if (datePattern(date) == false) {
						return;
					}
					String id = logMain.id; // ȸ�� ���̵� ��
					String incomType = (String) incomCombo.getSelectedItem();
					String assetType = (String) assetCombo.getSelectedItem();
					String detail = textDetail.getText();
					if(detail.length() > 10) {
						JOptionPane.showMessageDialog(null, "10�� �̳��� �Է����ּ���.", "Error", JOptionPane.WARNING_MESSAGE);
						return;
					}
					int money = textMoney(textMoney.getText());
					if (money <= 0) { // money = 0 �̸� �޼ҵ� ����
						return;
					}
					
					AccountBookVO vo = new AccountBookVO(id, date, incomType, assetType, detail, money, 0);
					// TODO : CID ��ȣ ó��
					int result = logMain.accountDAO.insertAccountBook(vo);

					if (result == 1) {
						String str = date + " " + incomType + " " + assetType + " " + detail + " \n �ݾ� : " + money
								+ "�� \n �Է¿Ϸ�";
						areaLog.setText(str);

					} else {
						JOptionPane.showMessageDialog(null, "�ùٸ� �������� �Է��ϼ���.", "Error", JOptionPane.WARNING_MESSAGE);
						return;
					}
					clearTextFields();
				} // end if(setCombo.getSelectedItem().equals("�߰�"))

				else if (setCombo.getSelectedItem().equals("����")) {
					String date = textdate.getText();
					if (datePattern(date) == false) {
						return;
					}
					String id = logMain.id; // ȸ�� ���̵� ��
					String incomType = (String) incomCombo.getSelectedItem();
					String assetType = (String) assetCombo.getSelectedItem();
					String detail = textDetail.getText();
					int money = textMoney(textMoney.getText());					
					AccountBookVO vo = new AccountBookVO(id, date, incomType, assetType, detail, money, 0);
					if (selectVO == null) {
						JOptionPane.showMessageDialog(null, "���� �� ���̺��� ������ �ּ���.", "Error", JOptionPane.WARNING_MESSAGE);
						return;
					}
					int result = logMain.accountDAO.updateAccountBook(vo, selectVO.getcid());
//					
					if(result == 1) {
						selectList.set(selectNo,
								new AccountBookVO(id, date, incomType, assetType, detail, money, selectVO.getcid()));
						tableRePrint();
						clearTextFields();
						areaLog.setText("���� �Ϸ�");						
					} else {
						JOptionPane.showMessageDialog(null, "���� �� ���̺��� �ٽ� ������ �ּ���.", "Error", JOptionPane.WARNING_MESSAGE);
					}
				} // end else if(setCombo.getSelectedItem().equals("����"))

				else if (setCombo.getSelectedItem().equals("����")) {
					if (selectVO == null) {
						JOptionPane.showMessageDialog(null, "���� �� ���̺��� ������ �ּ���.", "Error", JOptionPane.WARNING_MESSAGE);
						return;
					}
					logMain.accountDAO.deleteAccountBook(selectVO);
					selectList.remove(selectNo);
					tableRePrint();
					areaLog.setText("���� �Ϸ�");
				} // end else if(setCombo.getSelectedItem().equals("����"))

				selectVO = null; // ���� ���� �� vo �ʱ�ȭ
			}
			// TODO : ����� ��ư Ŭ���� ���� �޷� ��°� �ܾ� �����

		});
		confirmbtn.setBounds(130, 400, 80, 30);
		contentPane.add(confirmbtn);

		
	} // end initialize

	public void selectAllAccountBook() { // ��ü�˻�
		tableModel.setNumRows(0); // ���̺� �ʱ�ȭ
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
			areaLog.setText("��ü �˻� �Ϸ�");
		}
	} // and selectAllAccountBook()

	public void selectDateAccountBook() { // �ش糯¥ �˻�
		tableModel.setNumRows(0); // ���̺� �ʱ�ȭ
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
			areaLog.setText("��¥ �˻� �Ϸ�");
		}
	} // and selectDateAccountBook()

	public boolean datePattern(String date) {  // ��¥ �Է½� ���� ��ġ �޼ҵ�
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");		
		try {
			dateFormat.setLenient(false);
			dateFormat.parse(date);			
		} catch (ParseException e) {			
			JOptionPane.showMessageDialog(null,"�ùٸ������� ��¥�� �Է��� �ּ���." + "\n ex)2018-02-14", "Error",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		String[] dateSplite = date.split("-",3); // �Է¹��� ��¥
		int y = Integer.parseInt(dateSplite[0]); // ����
		int m = Integer.parseInt(dateSplite[1]); // ��
		int d = Integer.parseInt(dateSplite[2]); // ��
		
		Calendar cal = Calendar.getInstance();		
		cal.set(y, m - 1, 1); // �Է¹��� ��¥�� 1�Ϸ� ����		
		int lastDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);	 // �� ���� ������ ��		
		
		if(lastDate < d) {	// �Է¹��� ��¥�� �� ���� ������ �޺��� ũ�� 		
			JOptionPane.showMessageDialog(null,"�������� �ʴ� ��¥ �Դϴ�." , "Error",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		
		return true;
	}; // end datePatton()

	public int textMoney(String textMoney) { // textMoney ���� ó�� �� Integer �� ����ȯ �޼ҵ�
		int money = 0;
		try {
			money = Integer.parseInt(textMoney);
		} catch (NumberFormatException e2) {
			JOptionPane.showMessageDialog(null, "�ݾ��� ���ڷ� �Է��� �ּ���.", "Error", JOptionPane.WARNING_MESSAGE);
			return money;

		}
		if (textMoney.equals("")) {
			JOptionPane.showMessageDialog(null, "�ݾ��� �Է��� �ּ���.", "Error", JOptionPane.WARNING_MESSAGE);
			return money;
		} else if (money <= 0) {
			JOptionPane.showMessageDialog(null, "�ݾ��� ����� �Է��� �ּ���.", "Error", JOptionPane.WARNING_MESSAGE);
			return money;
		}

		return money;
	} // end textMoney()

	private void tableRePrint() { // ���� ������ ���̺� �� ���
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

	// �ؽ�Ʈ �ʵ� �ʱ�ȭ �Լ�
	private void clearTextFields() {
		textMoney.setText("");
	} // end clearTextFields()

		
	
	
	
	
	
	
} // end InputData
