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

	Calendar now; // 현재 날짜는 다른 클래스에서 사용하므로 package 설정
	int year, month, date;
	int balance; // 잔고

	public CalendarUI() {
		initialize();
	} // end MyPage

	public void initialize() {
		setModal(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 700, 800);
		getContentPane().setLayout(null);

		now = Calendar.getInstance(); // 현재 날짜

		year = now.get(Calendar.YEAR);
		month = now.get(Calendar.MONTH) + 1;
		date = now.get(Calendar.DATE);

		// start north
		topPane = new JPanel();
		topPane.setBounds(0, 0, 684, 35);
		topPane.setLayout(null);

		prevBtn = new JButton("◀");
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
		yearCombo.setSelectedItem(year); // 현재 년도 선택
		topPane.add(yearCombo);

		yearLbl = new JLabel("년");

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
		monthCombo.setSelectedItem(month); // 현재 월 선택
		topPane.add(monthCombo);

		monthLbl = new JLabel("월");
		monthLbl.setBounds(220, topUiY, 20, 20);
		topPane.add(monthLbl);

		nextBtn = new JButton("▶");
		nextBtn.setBounds(240, topUiY, topBtnWidth, topBtnHeight);
		nextBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comClickChange(e);
			}
		});

		topPane.add(nextBtn);

		balLbl = new JLabel("잔고 : ");
		balLbl.setBounds(345, topUiY, topBtnWidth, topBtnHeight);
		topPane.add(balLbl);

		balArea = new JTextArea(); // 잔액
		balancePrint(balArea); // 잔고 출력

		returnBtn = new JButton("새로고침"); // TODO : 새로고침 버튼 클릭시 MyPage 잔액 입금액 출금핵 다시 출력
		returnBtn.setBounds(450, topUiY, topBtnWidth + 30, topBtnHeight);
		returnBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				refresh(); // 새로고침

			}
		});
		topPane.add(returnBtn);

		setBtn = new JButton("설정");
		setBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SetMember prefer = new SetMember();
				prefer.setVisible(true);
			}
		});
		setBtn.setBounds(550, topUiY, topBtnWidth, topBtnHeight);
		topPane.add(setBtn);

		addBtn = new JButton("추가"); // 추가 UI로 이동
		addBtn.setBounds(620, topUiY, topBtnWidth, topBtnHeight);
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputData plus = new InputData(year, month, date);
				// PlusUI 로 dao,오늘 날짜 넘겨주기
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

		String titleStr[] = { "일", "월", "화", "수", "목", "금", "토" };
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
		// 날짜 출력
		printCalendar(year, month);

		underPane.add(titlePane, "North");
		underPane.add(calPane, "Center");

		getContentPane().add(underPane, BorderLayout.SOUTH);
		// end underPanel

	} // end initialize()

	public void printCalendar(int y, int m) { // 달력 출력 메소드

		Calendar cal = Calendar.getInstance(); // 현재 날짜 인스턴스 생성

		cal.set(y, m - 1, 1); // 출력할 첫날의 객체 만든다.

		int week = cal.get(Calendar.DAY_OF_WEEK); // 1일에 대한 요일 , 일요일 : 1


		int lastDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 그 달의 마지막 날

		for (int i = 1; i < week; i++) { // 날짜 출력 전까지의 공백 출력
			JLabel lbl = new JLabel();
			lbl.setBorder(new LineBorder(new Color(240, 240, 240)));
			calPane.setBackground(Color.white);
			calPane.add(lbl);
		}

		JLabel inArea = null;
		JLabel exArea = null;
		JLabel dateArea = null;

		for (int i = 1; i <= lastDate; i++) { // 날짜 출력

			cal.set(y, m - 1, i); // Calender 클래스에선 1월이 0 이기 때문에 m-1 로 기입
			int income = dateMoney("수입", y, m, i);
			int expenditure = dateMoney("지출", y, m, i);

			String inString = "수입 : " + income;
			String exString = "지출 : " + expenditure;
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

			inArea.setOpaque(true); // label 배경 설정
			exArea.setOpaque(true);
			dateArea.setOpaque(true);

			inArea.setBackground(Color.WHITE); // label 배경 설정
			exArea.setBackground(Color.WHITE);
			dateArea.setBackground(Color.WHITE);

			inArea.setFont(new Font("돋움", Font.PLAIN, 11)); // label 폰트설정
			exArea.setFont(new Font("돋움", Font.PLAIN, 11));
			dateArea.setFont(new Font("돋움", Font.PLAIN, 13));

			inArea.setForeground(new Color(0, 0, 200));

			exArea.setForeground(new Color(230, 0, 0));

			datePane.add(dateArea);
			datePane.add(inArea);
			datePane.add(exArea);

			int outWeek = cal.get(Calendar.DAY_OF_WEEK);

			if (outWeek == 1) { // 토요일 일요일 날짜 색 변경

				dateArea.setForeground(Color.red);

			} else if (outWeek == 7) {

				dateArea.setForeground(Color.BLUE);

			}

			boolean current = // 오늘 날짜일경우 true
					year == (Integer) yearCombo.getSelectedItem() && month == (Integer) monthCombo.getSelectedItem()
							&& date == i;

			if (current) {

				dateArea.setBackground(new Color(230, 230, 230)); // 오늘 날짜 색 지정
				inArea.setBackground(new Color(230, 230, 230));
				exArea.setBackground(new Color(230, 230, 230));
			}

			calPane.add(datePane);

		}

		cal.set(y, m - 1, lastDate);
		int lastWeek = cal.get(Calendar.DAY_OF_WEEK);

		for (int i = lastWeek + 1; i < 8; i++) { // 날짜 출력 전까지의 공백 출력

			JLabel lbl2 = new JLabel();
			lbl2.setBorder(new LineBorder(new Color(240, 240, 240)));
			calPane.setBackground(Color.white);
			calPane.add(lbl2);
		}

	} // end printCalendar()

	public void balancePrint(JTextArea balArea) { // 잔액 출력
		balArea.setBounds(380, topUiY, topBtnWidth, topBtnHeight);
		balance = logMain.memberDAO.selectMember(logMain.id).getTotalAsset(); // 잔고
		if (balance < 0) {
			balArea.setForeground(Color.red);
		} else {
			balArea.setForeground(Color.BLUE);
		}
		balArea.setText(Integer.toString(balance));
		balArea.setEditable(false);
		topPane.add(balArea);
	} // end balancePrint()

	public void comClickChange(ActionEvent e) { // 콤보버튼 클릭시 변경
		Object click = e.getSource();
		int yy = (Integer) yearCombo.getSelectedItem(); // 현재 연도 2020
		int mm = (Integer) monthCombo.getSelectedItem(); // 현재 월 02

		JButton eventBtn = (JButton) click;
		if (eventBtn.equals(prevBtn)) { // prevBtn 발생시
			mm--;
			if (mm == 0) {
				mm = 12;
				yy--;
			}
		} else if (eventBtn.equals(nextBtn)) { // nextBtn 발생시
			mm++;
			if (mm == 13) {
				mm = 1;
				yy++;
			}
		}
		yearCombo.setSelectedItem(yy); // 바뀐 연도
		monthCombo.setSelectedItem(mm); // 바뀐 월

		refresh(); // 새로고침

	} // comboClickChange()

	public void refresh() { // 날짜 삭제 및 잔고 삭제
		calPane.setVisible(false);
		calPane.removeAll(); // 날짜 삭제
		calPane.setVisible(true);

		topPane.setVisible(false);
		topPane.remove(balArea);
		topPane.setVisible(true);

		printCalendar((Integer) yearCombo.getSelectedItem(), (Integer) monthCombo.getSelectedItem());// 바뀐 날짜 출력
		balancePrint(balArea); // 잔고 출력
	} // refresh()

	public int dateMoney(String incomeType, int year, int month, int day) {
		// 해당 날짜의 incomType 금액을 가져오는 메소드

		ArrayList<AccountBookVO> list = logMain.accountDAO.selectAccountBook();
		// 로그인한 id의 AccBook table 전체 조회
		int dateMoney = 0;
		for (int i = 0; i < list.size(); i++) {
			String theDate = String.format("%d-%02d-%02d", year, month, day); // 해당 날짜

			if (theDate.equals(list.get(i).getDate_())) { // 해당 날짜

				if (list.get(i).getIncomeType().equals(incomeType)) {
					dateMoney = dateMoney + list.get(i).getMoney();
				}
			}

		}


		return dateMoney;
	} // end dateMoney()

} // end CalendarUI
