package edu.java.accountBook;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.java.vo.MemberVO;


import java.awt.Color;

@SuppressWarnings("serial")
public class Register extends JDialog {

	private JTextField textPw;
	private JTextField textId;
	private JTextField textName;
	private JTextField textPhone;
	private JTextField textEmail;
	private JPanel contentPane;
	private JTextField idCheckarea;

	public Register() {

		initialize();
	}

	private void initialize() {
		setModal(true);
		contentPane = new JPanel();
		setBounds(100, 100, 400, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblReg = new JLabel("ȸ������");
		lblReg.setFont(new Font("�޸�����ü", Font.PLAIN, 30));
		lblReg.setBounds(125, 25, 110, 60);
		contentPane.add(lblReg);

		JLabel lblId = new JLabel("\u25B7 ID");
		lblId.setFont(new Font("�޸�����ü", Font.PLAIN, 30));
		lblId.setBounds(12, 107, 100, 60);
		contentPane.add(lblId);

		JLabel lblPw = new JLabel("\u25B6 PW");
		lblPw.setFont(new Font("�޸�����ü", Font.PLAIN, 30));
		lblPw.setBounds(12, 192, 100, 60);
		contentPane.add(lblPw);

		textPw = new JTextField();
		textPw.setColumns(10);
		textPw.setBounds(110, 200, 150, 50);
		contentPane.add(textPw);

		textId = new JTextField();
		textId.setColumns(10);
		textId.setBounds(110, 105, 150, 50);
		contentPane.add(textId);

		JButton btnIdCheck = new JButton("\uC0AC\uC6A9\uAC00\uB2A5\uCCB4\uD06C");
		btnIdCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				idOverlap();
			}
		});
		btnIdCheck.setFont(new Font("�޸�����ü", Font.PLAIN, 12));
		btnIdCheck.setBounds(272, 106, 100, 50);
		contentPane.add(btnIdCheck);

		JLabel lblName = new JLabel("\u25B7 �̸�");
		lblName.setFont(new Font("�޸�����ü", Font.PLAIN, 30));
		lblName.setBounds(12, 290, 100, 60);
		contentPane.add(lblName);

		JLabel lblContact = new JLabel("\u25B6 ����ó");
		lblContact.setFont(new Font("�޸�����ü", Font.PLAIN, 30));
		lblContact.setBounds(12, 350, 130, 60);
		contentPane.add(lblContact);

		JLabel lblEmail = new JLabel("\u25B7 E-Mail");
		lblEmail.setFont(new Font("�޸�����ü", Font.PLAIN, 30));
		lblEmail.setBounds(12, 410, 130, 60);
		contentPane.add(lblEmail);

		textName = new JTextField();
		textName.setColumns(10);
		textName.setBounds(150, 290, 150, 50);
		contentPane.add(textName);

		textPhone = new JTextField();
		textPhone.setColumns(10);
		textPhone.setBounds(150, 350, 150, 50);
		contentPane.add(textPhone);

		textEmail = new JTextField();
		textEmail.setColumns(10);
		textEmail.setBounds(150, 410, 150, 50);
		contentPane.add(textEmail);

		JButton confirm = new JButton("Ȯ��");
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertMember();
			}
		});
		confirm.setFont(new Font("�޸�����ü", Font.PLAIN, 15));
		confirm.setBounds(150, 480, 100, 50);
		contentPane.add(confirm);

		idCheckarea = new JTextField();
		idCheckarea.setForeground(Color.RED);
		idCheckarea.setFont(new Font("�޸�����ü", Font.PLAIN, 20));
		idCheckarea.setColumns(10);
		idCheckarea.setBounds(58, 163, 270, 30);
		contentPane.add(idCheckarea);
	}

	private void insertMember() { // ȸ������ �޼ҵ�
		String id = textId.getText();
		String pw = textPw.getText();
		String name = textName.getText();
		String phone = textPhone.getText();
		String email = textEmail.getText();
		int totalasset = 0;
		int result = 0;

		if (pw.equals("")) {
			JOptionPane.showMessageDialog(null, "��й�ȣ�� �Է����� �ʾҽ��ϴ�.", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (pw.equals(id)) {
			JOptionPane.showMessageDialog(null, "���̵�� ��й�ȣ�� �����ϴ�.", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (pw.indexOf(" ") >= 0) {
			JOptionPane.showMessageDialog(null, "��й�ȣ�� ������ ����� �� �����ϴ�.", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (pw.length() < 4 || pw.length() > 12) {
			JOptionPane.showMessageDialog(null, "��й�ȣ�� 4~12�ڱ��� �Է����ּ���.", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (name.equals("")) {
			JOptionPane.showMessageDialog(null, "�̸��� �Է����� �ʾҽ��ϴ�.", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (name.indexOf(" ") >= 0) {
			JOptionPane.showMessageDialog(null, "�̸��� ������ ����� �� �����ϴ�.", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (name.length() < 2) {
			JOptionPane.showMessageDialog(null, "�̸��� 2�� �̻� �Է����ֽʽÿ�.", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (phone.equals("")) {
			JOptionPane.showMessageDialog(null, "��ȭ��ȣ�� �Է����� �ʾҽ��ϴ�.", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (phone.indexOf(" ") >= 0) {
			JOptionPane.showMessageDialog(null, "��ȭ��ȣ�� ������ ����� �� �����ϴ�.", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (email.equals("")) {
			JOptionPane.showMessageDialog(null, "�̸����� �Է����� �ʾҽ��ϴ�.", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (email.indexOf(" ") >= 0) {
			JOptionPane.showMessageDialog(null, "�̸��Ͽ� ������ ����� �� �����ϴ�.", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}

		MemberVO vo = new MemberVO(id, pw, name, phone, email, totalasset);
		result = logMain.memberDAO.insertMember(vo);

		if (result == 1) {
//			System.out.println("ȸ�� ���� �Ϸ�");
			JOptionPane.showMessageDialog(null, "ȸ�� ���� �Ϸ�", "Confirm", JOptionPane.CLOSED_OPTION);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, "�ߺ��� �����̰ų�, ������ �� �ִ� ID�� �ƴմϴ�.", "Error", JOptionPane.WARNING_MESSAGE);

		}
	} // end insertMember();

	private void idOverlap() { // ���̵� �ߺ�üũ
		String id = textId.getText();
		int result = logMain.memberDAO.overlapId(id);

		if (id.equals("")) {
			JOptionPane.showMessageDialog(null, "���̵� �Է����� �ʾҽ��ϴ�.", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (id.indexOf(" ") >= 0) {
			JOptionPane.showMessageDialog(null, "���̵� ������ ����� �� �����ϴ�.", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (id.length() < 4 || id.length() > 12) {
			JOptionPane.showMessageDialog(null, "���̵� 4~12�ڱ��� �Է����ּ���.", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}

		for (int i = 0; i < id.length(); i++) {
			id.charAt(i);
			if (!(id.charAt(i) >= '0' && id.charAt(i) <= '9') && !(id.charAt(i) >= 'a' && id.charAt(i) <= 'z')
					&& !(id.charAt(i) >= 'A' && id.charAt(i) <= 'Z')) {
				JOptionPane.showMessageDialog(null, "���̵�� ���� ��ҹ���, ���ڸ� �Է°����մϴ�.", "Error", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}

		if (result == 1) {
			idCheckarea.setText("�ߺ��� ���̵� �Դϴ�.");

		} else {
			idCheckarea.setText("��� ������ ���̵� �Դϴ�.");
		}

	} // end idOverlap()

} // end Register
