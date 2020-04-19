package edu.java.accountBook;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class FindPw extends JDialog {

	private JTextField textId;
	private JTextField textEmail;
	private JPanel contentPane;
 
	public FindPw() {
		initialize();
	}

	private void initialize() {
		setModal(true);
		contentPane = new JPanel();
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(contentPane);
		getContentPane().setLayout(null);

		JLabel title = new JLabel("PW ã��");
		title.setFont(new Font("�޸�����ü", Font.PLAIN, 30));
		title.setBounds(159, 10, 110, 60);
		contentPane.add(title);

		JLabel lblID = new JLabel("\u25B7 ID");
		lblID.setFont(new Font("�޸�����ü", Font.PLAIN, 30));
		lblID.setBounds(15, 80, 100, 60);
		contentPane.add(lblID);

		JLabel lblEmail = new JLabel("\u25B6 E-Mail");
		lblEmail.setFont(new Font("�޸�����ü", Font.PLAIN, 30));
		lblEmail.setBounds(15, 150, 130, 60);
		contentPane.add(lblEmail);

		textId = new JTextField();
		textId.setColumns(10);
		textId.setBounds(159, 90, 150, 50);
		contentPane.add(textId);

		textEmail = new JTextField();
		textEmail.setColumns(10);
		textEmail.setBounds(159, 160, 150, 50);
		contentPane.add(textEmail);

		JButton findPw = new JButton("PW ã��");
		findPw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO : FindPW
				findPw();
			}

		});
		findPw.setFont(new Font("�޸�����ü", Font.PLAIN, 15));
		findPw.setBounds(332, 131, 90, 50);
		contentPane.add(findPw);

	} // end initialize()

	private void findPw() { // PWã��

		String id = textId.getText();
		String email = textEmail.getText();
		String pw = logMain.memberDAO.selectPw(id, email);
		if (!pw.equals("")) {
			JOptionPane.showMessageDialog(null, "�˻��� PW�� " + pw + " �Դϴ�.", "PWã��", JOptionPane.CLOSED_OPTION);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, "���ǿ� �ش��ϴ� PW�� ã�� �� �����ϴ�.", "PWã��", JOptionPane.WARNING_MESSAGE);
		}

	} // end findPw()

} // end FindPw
