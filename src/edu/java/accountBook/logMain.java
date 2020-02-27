package edu.java.accountBook;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import edu.java.dao.AccountBookDAO;
import edu.java.dao.AccountBookDAOImple;
import edu.java.dao.MemberDAO;
import edu.java.dao.MemberDAOImple;

import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class logMain {

	private JFrame frame;
	private JTextField textId;
	private JTextField textPw;
	private JTextField error;
	public static MemberDAO memberDAO;
	public static AccountBookDAO accountDAO;
	public static String id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					logMain window = new logMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public logMain() {
		memberDAO = MemberDAOImple.getInstance();
		accountDAO = AccountBookDAOImple.getInstance();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel loginLabel = new JLabel("Login");
		loginLabel.setFont(new Font("휴먼편지체", Font.PLAIN, 30));
		loginLabel.setBounds(140, 100, 70, 60);
		frame.getContentPane().add(loginLabel);

		JLabel labelId = new JLabel("\u25B7 ID");
		labelId.setFont(new Font("휴먼편지체", Font.PLAIN, 30));
		labelId.setBounds(12, 190, 100, 60);
		frame.getContentPane().add(labelId);

		JLabel labelPw = new JLabel("\u25B6 PW");
		labelPw.setFont(new Font("휴먼편지체", Font.PLAIN, 30));
		labelPw.setBounds(12, 255, 100, 60);
		frame.getContentPane().add(labelPw);

		textId = new JTextField();
		textId.setColumns(10);
		textId.setBounds(100, 190, 150, 50);
		frame.getContentPane().add(textId);

		textPw = new JTextField();
		textPw.setColumns(10);
		textPw.setBounds(100, 260, 150, 50);
		frame.getContentPane().add(textPw);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				logInCheck();
			}

		});
		btnLogin.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
		btnLogin.setBounds(272, 227, 100, 50);
		frame.getContentPane().add(btnLogin);

		JButton btnReg = new JButton("회원가입");
		btnReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Register reg = new Register();
				reg.setVisible(true);

			}
		});
		btnReg.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
		btnReg.setBounds(125, 400, 120, 50);
		frame.getContentPane().add(btnReg);

		JLabel title = new JLabel("\u2020 행복한 가계부 \u2020");
		title.setFont(new Font("휴먼편지체", Font.PLAIN, 30));
		title.setBounds(60, 35, 250, 60);
		frame.getContentPane().add(title);

		error = new JTextField();
		error.setForeground(Color.RED);
		error.setFont(new Font("휴먼편지체", Font.PLAIN, 20));
		error.setColumns(10);
		error.setBounds(60, 330, 270, 30);
		frame.getContentPane().add(error);

		JButton findId = new JButton("ID 찾기");
		findId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FindId idforgot = new FindId();
				idforgot.setVisible(true);
			}
		});
		findId.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
		findId.setBounds(60, 460, 120, 50);
		frame.getContentPane().add(findId);

		JButton findPw = new JButton("PW 찾기");
		findPw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FindPw pwforgot = new FindPw();
				pwforgot.setVisible(true);
			}
		});
		findPw.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
		findPw.setBounds(190, 460, 120, 50);
		frame.getContentPane().add(findPw);

	} // end initialize()

	private void logInCheck() { // 로그인 true/false

		id = textId.getText();
		String pw = textPw.getText();
		int result = memberDAO.logIn(id, pw);
		if (result == 1) {
			CalendarUI mypage = new CalendarUI();
			mypage.setVisible(true);
		} else {
			error.setText(" ※ 잘못된 ID 또는 PW 입니다.");
		}

	} // end logInCheck()

} // end logMain()
