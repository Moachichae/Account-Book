package edu.java.accountBook;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JTextField;

import edu.java.dao.MemberDAO;
import edu.java.dao.MemberDAOImple;
import edu.java.vo.MemberVO;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class EditMember extends JDialog {

	private JPanel contentPane;
	private JTextField textPw;
	private JTextField textName;
	private JTextField textPhone;
	private JTextField textEmail; 
	static MemberDAO dao;
	static MemberVO vo;

	public EditMember() {
		dao = MemberDAOImple.getInstance();
		initialize();
	}

	private void initialize() {
		setModal(true);
		contentPane = new JPanel();
		setBounds(100, 100, 200, 350);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitle = new JLabel("회원정보조회");
		lblTitle.setBounds(50, 25, 85, 15);
		contentPane.add(lblTitle);

		JLabel lblID = new JLabel("\u25B6 ID");
		lblID.setBounds(12, 50, 120, 15);
		contentPane.add(lblID);

		JLabel lblPW = new JLabel("\u25B6 PW");
		lblPW.setBounds(12, 96, 57, 15);
		contentPane.add(lblPW);

		textPw = new JTextField();
		textPw.setColumns(10);
		textPw.setBounds(12, 109, 85, 21);
		contentPane.add(textPw);

		JButton btnNewButton = new JButton("조회");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO : ID검색버튼
				searching();
			}

		});
		btnNewButton.setBounds(112, 63, 60, 25);
		contentPane.add(btnNewButton);

		JLabel lblName = new JLabel("\u25B6 이름");
		lblName.setBounds(12, 140, 57, 15);
		contentPane.add(lblName);

		textName = new JTextField();
		textName.setColumns(10);
		textName.setBounds(12, 153, 85, 21);
		contentPane.add(textName);

		JLabel lblPhone = new JLabel("\u25B6 연락처");
		lblPhone.setBounds(12, 184, 57, 15);
		contentPane.add(lblPhone);

		textPhone = new JTextField();
		textPhone.setColumns(10);
		textPhone.setBounds(12, 197, 85, 21);
		contentPane.add(textPhone);

		JLabel lblEmail = new JLabel("\u25B6 E-Mail");
		lblEmail.setBounds(12, 228, 57, 15);
		contentPane.add(lblEmail);

		textEmail = new JTextField();
		textEmail.setColumns(10);
		textEmail.setBounds(12, 241, 85, 21);
		contentPane.add(textEmail);

		JButton btnRegEdit = new JButton("수정");
		btnRegEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO : 수정버튼
				updateMember();
			}
		});
		btnRegEdit.setBounds(112, 276, 60, 25);
		contentPane.add(btnRegEdit);

		JLabel textId = new JLabel(logMain.id);
		textId.setBounds(12, 65, 85, 21);
		contentPane.add(textId);
	} // end initialize()

	private void searching() { // 회원정보수정 > 정보검색
		String id = logMain.id;
//		System.out.println("id : " + id);
		MemberVO editMember = logMain.memberDAO.selectMember(id);

		String pw = editMember.getPw();
		String name = editMember.getName();
		String phone = editMember.getPhone();
		String email = editMember.getEmail();

//		textId.setText(id);
		textPw.setText(pw);
		textName.setText(name);
		textPhone.setText(phone);
		textEmail.setText(email);

	} // end searching()

	private void updateMember() { // 회원정보수정 > 업데이트
		String id = logMain.id;
		String pw = textPw.getText();
		String name = textName.getText();
		String phone = textPhone.getText();
		String email = textEmail.getText();

		if (pw.equals("")) {
			JOptionPane.showMessageDialog(null, "빈 칸 없이 입력해 주세요.", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		} else if (name.equals("")) {
			JOptionPane.showMessageDialog(null, "빈 칸 없이 입력해 주세요.", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		} else if (phone.equals("")) {
			JOptionPane.showMessageDialog(null, "빈 칸 없이 입력해 주세요.", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		} else if (email.equals("")) {
			JOptionPane.showMessageDialog(null, "빈 칸 없이 입력해 주세요.", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}

		MemberVO vo = new MemberVO(id, pw, name, phone, email, 0);
		int result = dao.updateMember(vo);

		if (result == 1) {
//			System.out.println("회원 정보 수정 완료");	
			JOptionPane.showMessageDialog(null, "회원 정보 수정 완료", "Confirm", JOptionPane.CLOSED_OPTION);
			dispose();
		}

	} // end updateMember()

} // end EditMember
