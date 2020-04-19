package edu.java.accountBook;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import edu.java.vo.AccountBookVO;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class CalendarUI extends JDialog {
	// north
	private JPanel topPane;

	private JButton prevBtn;

	private JButton nextBtn;

	private JLabel yearLbl;

	private JLabel monthLbl;

	private JComboBox<Integer> yearCombo;

	private DefaultComboBoxModel<Integer> yearModel;

	private JComboBox<Integer> monthCombo;

	private DefaultComboBoxModel<Integer> monthModel;

	private JLabel balLbl;
	private JTextArea balArea;
	private JButton setBtn;
	private JButton addBtn;
	private JButton returnBtn;

	// under
	private JPanel underPane;
	private JPanel titlePane;
	private JPanel calPane;

	private int topUiY = 5;
	private int topBtnWidth = 60;
	private int topBtnHeight = 20;

	Calendar now; // ���� ��¥�� �ٸ� Ŭ�������� ����ϹǷ� package ����
	int year, month, date;
	int balance; // �ܰ�

	public CalendarUI() {
		initialize();
	} // end MyPage

	public void initialize() {
		setModal(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 700, 800);
		getContentPane().setLayout(null);

		now = Calendar.getInstance(); // ���� ��¥

		year = now.get(Calendar.YEAR);
		month = now.get(Calendar.MONTH) + 1;
		date = now.get(Calendar.DATE);

		// start north
		topPane = new JPanel();
		topPane.setBounds(0, 0, 684, 35);
		topPane.setLayout(null);

		prevBtn = new JButton("��");
		prevBtn.setBounds(10, topUiY, topBtnWidth, topBtnHeight);
		prevBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comClickChange(e);
			}

		});
		topPane.add(prevBtn);

		// year
		yearCombo = new JComboBox<Integer>();
		yearCombo.setBounds(80, topUiY, topBtnWidth, topBtnHeight);
		yearModel = new DefaultComboBoxModel<Integer>();
		for (int i = year - 100; i <= year + 50; i++) {
			yearModel.addElement(i);
		}
		yearCombo.setModel(yearModel);
		yearCombo.setSelectedItem(year); // ���� �⵵ ����
		topPane.add(yearCombo);

		yearLbl = new JLabel("��");

		yearLbl.setBounds(145, topUiY, 20, 20);
		topPane.add(yearLbl);
		// month
		monthCombo = new JComboBox<Integer>();
		monthCombo.setBounds(165, topUiY, topBtnWidth - 10, topBtnHeight);
		monthModel = new DefaultComboBoxModel<Integer>();
		for (int i = 1; i <= 12; i++) {
			monthModel.addElement(i);
		}
		monthCombo.setModel(monthModel);
		monthCombo.setSelectedItem(month); // ���� �� ����
		topPane.add(monthCombo);

		monthLbl = new JLabel("��");
		monthLbl.setBounds(220, topUiY, 20, 20);
		topPane.add(monthLbl);

		nextBtn = new JButton("��");
		nextBtn.setBounds(240, topUiY, topBtnWidth, topBtnHeight);
		nextBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comClickChange(e);
			}
		});

		topPane.add(nextBtn);

		balLbl = new JLabel("�ܰ� : ");
		balLbl.setBounds(345, topUiY, topBtnWidth, topBtnHeight);
		topPane.add(balLbl);

		balArea = new JTextArea(); // �ܾ�
		balancePrint(balArea); // �ܰ� ���

		returnBtn = new JButton("���ΰ�ħ"); // TODO : ���ΰ�ħ ��ư Ŭ���� MyPage �ܾ� �Աݾ� ����� �ٽ� ���
		returnBtn.setBounds(450, topUiY, topBtnWidth + 30, topBtnHeight);
		returnBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				refresh(); // ���ΰ�ħ

			}
		});
		topPane.add(returnBtn);

		setBtn = new JButton("����");
		setBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SetMember prefer = new SetMember();
				prefer.setVisible(true);
			}
		});
		setBtn.setBounds(550, topUiY, topBtnWidth, topBtnHeight);
		topPane.add(setBtn);

		addBtn = new JButton("�߰�"); // �߰� UI�� �̵�
		addBtn.setBounds(620, topUiY, topBtnWidth, topBtnHeight);
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputData plus = new InputData(year, month, date);
				// PlusUI �� dao,���� ��¥ �Ѱ��ֱ�
				plus.setVisible(true);
			}
		});
		topPane.add(addBtn);

		getContentPane().add(topPane, "NORTH");

		// underPanel
		underPane = new JPanel(new BorderLayout());
		underPane.setBounds(0, 35, 684, 726);
		titlePane = new JPanel(new GridLayout(1, 7));
		titlePane.setBackground(Color.white);

		String titleStr[] = { "��", "��", "ȭ", "��", "��", "��", "��" };
		for (int i = 0; i < titleStr.length; i++) {
			JLabel lbl = new JLabel(titleStr[i], JLabel.CENTER);
			topPane.setBackground(Color.cyan);

			if (i == 0) {
				lbl.setForeground(new Color(230, 0, 0));
			} else if (i == 6) {

				lbl.setForeground(new Color(0, 0, 200));
			}
			titlePane.add(lbl);
		}

		calPane = new JPanel(new GridLayout(0, 7));
		// ��¥ ���
		printCalendar(year, month);

		underPane.add(titlePane, "North");
		underPane.add(calPane, "Center");

		getContentPane().add(underPane, BorderLayout.SOUTH);
		// end underPanel

	} // end initialize()

	public void printCalendar(int y, int m) { // �޷� ��� �޼ҵ�

		Calendar cal = Calendar.getInstance(); // ���� ��¥ �ν��Ͻ� ����

		cal.set(y, m - 1, 1); // ����� ù���� ��ü �����.

		int week = cal.get(Calendar.DAY_OF_WEEK); // 1�Ͽ� ���� ���� , �Ͽ��� : 1


		int lastDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // �� ���� ������ ��

		for (int i = 1; i < week; i++) { // ��¥ ��� �������� ���� ���
			JLabel lbl = new JLabel();
			lbl.setBorder(new LineBorder(new Color(240, 240, 240)));
			calPane.setBackground(Color.white);
			calPane.add(lbl);
		}

		JLabel inArea = null;
		JLabel exArea = null;
		JLabel dateArea = null;

		for (int i = 1; i <= lastDate; i++) { // ��¥ ���

			cal.set(y, m - 1, i); // Calender Ŭ�������� 1���� 0 �̱� ������ m-1 �� ����
			int income = dateMoney("����", y, m, i);
			int expenditure = dateMoney("����", y, m, i);

			String inString = "���� : " + income;
			String exString = "���� : " + expenditure;
			if (income == 0) {
				inArea = new JLabel();
			} else {
				inArea = new JLabel(inString);
			}
			if (expenditure == 0) {
				exArea = new JLabel();
			} else {
				exArea = new JLabel(exString);
			}

			JPanel datePane = new JPanel();
			datePane.setBorder(new LineBorder(new Color(240, 240, 240)));
			datePane.setLayout(new GridLayout(3, 1));
			dateArea = new JLabel(Integer.toString(i));

			inArea.setOpaque(true); // label ��� ����
			exArea.setOpaque(true);
			dateArea.setOpaque(true);

			inArea.setBackground(Color.WHITE); // label ��� ����
			exArea.setBackground(Color.WHITE);
			dateArea.setBackground(Color.WHITE);

			inArea.setFont(new Font("����", Font.PLAIN, 11)); // label ��Ʈ����
			exArea.setFont(new Font("����", Font.PLAIN, 11));
			dateArea.setFont(new Font("����", Font.PLAIN, 13));

			inArea.setForeground(new Color(0, 0, 200));

			exArea.setForeground(new Color(230, 0, 0));

			datePane.add(dateArea);
			datePane.add(inArea);
			datePane.add(exArea);

			int outWeek = cal.get(Calendar.DAY_OF_WEEK);

			if (outWeek == 1) { // ����� �Ͽ��� ��¥ �� ����

				dateArea.setForeground(Color.red);

			} else if (outWeek == 7) {

				dateArea.setForeground(Color.BLUE);

			}

			boolean current = // ���� ��¥�ϰ�� true
					year == (Integer) yearCombo.getSelectedItem() && month == (Integer) monthCombo.getSelectedItem()
							&& date == i;

			if (current) {

				dateArea.setBackground(new Color(230, 230, 230)); // ���� ��¥ �� ����
				inArea.setBackground(new Color(230, 230, 230));
				exArea.setBackground(new Color(230, 230, 230));
			}

			calPane.add(datePane);

		}

		cal.set(y, m - 1, lastDate);
		int lastWeek = cal.get(Calendar.DAY_OF_WEEK);

		for (int i = lastWeek + 1; i < 8; i++) { // ��¥ ��� �������� ���� ���

			JLabel lbl2 = new JLabel();
			lbl2.setBorder(new LineBorder(new Color(240, 240, 240)));
			calPane.setBackground(Color.white);
			calPane.add(lbl2);
		}

	} // end printCalendar()

	public void balancePrint(JTextArea balArea) { // �ܾ� ���
		balArea.setBounds(380, topUiY, topBtnWidth, topBtnHeight);
		balance = logMain.memberDAO.selectMember(logMain.id).getTotalAsset(); // �ܰ�
		if (balance < 0) {
			balArea.setForeground(Color.red);
		} else {
			balArea.setForeground(Color.BLUE);
		}
		balArea.setText(Integer.toString(balance));
		balArea.setEditable(false);
		topPane.add(balArea);
	} // end balancePrint()

	public void comClickChange(ActionEvent e) { // �޺���ư Ŭ���� ����
		Object click = e.getSource();
		int yy = (Integer) yearCombo.getSelectedItem(); // ���� ���� 2020
		int mm = (Integer) monthCombo.getSelectedItem(); // ���� �� 02

		JButton eventBtn = (JButton) click;
		if (eventBtn.equals(prevBtn)) { // prevBtn �߻���
			mm--;
			if (mm == 0) {
				mm = 12;
				yy--;
			}
		} else if (eventBtn.equals(nextBtn)) { // nextBtn �߻���
			mm++;
			if (mm == 13) {
				mm = 1;
				yy++;
			}
		}
		yearCombo.setSelectedItem(yy); // �ٲ� ����
		monthCombo.setSelectedItem(mm); // �ٲ� ��

		refresh(); // ���ΰ�ħ

	} // comboClickChange()

	public void refresh() { // ��¥ ���� �� �ܰ� ����
		calPane.setVisible(false);
		calPane.removeAll(); // ��¥ ����
		calPane.setVisible(true);

		topPane.setVisible(false);
		topPane.remove(balArea);
		topPane.setVisible(true);

		printCalendar((Integer) yearCombo.getSelectedItem(), (Integer) monthCombo.getSelectedItem());// �ٲ� ��¥ ���
		balancePrint(balArea); // �ܰ� ���
	} // refresh()

	public int dateMoney(String incomeType, int year, int month, int day) {
		// �ش� ��¥�� incomType �ݾ��� �������� �޼ҵ�

		ArrayList<AccountBookVO> list = logMain.accountDAO.selectAccountBook();
		// �α����� id�� AccBook table ��ü ��ȸ
		int dateMoney = 0;
		for (int i = 0; i < list.size(); i++) {
			String theDate = String.format("%d-%02d-%02d", year, month, day); // �ش� ��¥

			if (theDate.equals(list.get(i).getDate_())) { // �ش� ��¥

				if (list.get(i).getIncomeType().equals(incomeType)) {
					dateMoney = dateMoney + list.get(i).getMoney();
				}
			}

		}


		return dateMoney;
	} // end dateMoney()

} // end CalendarUI
