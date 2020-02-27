package edu.java.accountBook;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class WithdrawalMember extends JDialog {

	private JPanel contentPane;
	private JTextField textID;
	private JTextField textPW;
	private JButton btnRegOut;

	public WithdrawalMember() {
		initialize();
	}

	private void initialize() { 
		setModal(true);
		contentPane = new JPanel();
		setBounds(100, 100, 200, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitle = new JLabel("ȸ�������Է�");
		lblTitle.setBounds(50, 25, 85, 15);
		contentPane.add(lblTitle);

		JLabel lblID = new JLabel("\u25B6 ID");
		lblID.setBounds(12, 50, 57, 15);
		contentPane.add(lblID);

		textID = new JTextField();
		textID.setBounds(12, 65, 85, 21);
		contentPane.add(textID);
		textID.setColumns(10);

		JLabel lblPW = new JLabel("\u25B6 PW");
		lblPW.setBounds(12, 96, 57, 15);
		contentPane.add(lblPW);

		textPW = new JTextField();
		textPW.setBounds(12, 109, 85, 21);
		contentPane.add(textPW);
		textPW.setColumns(10);

		btnRegOut = new JButton("Ż��");
		btnRegOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO : RegOut
				regOut();
			}

		});
		btnRegOut.setBounds(115, 128, 60, 25);
		contentPane.add(btnRegOut);

	} // end initialize()

	private void regOut() { // ȸ������Ż��
		String id = textID.getText();
		String pw = textPW.getText();
		if (id.equals("") || pw.equals("")) {
			JOptionPane.showMessageDialog(null, "�� ĭ ���� �Է��� �ּ���.", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}
		int result = logMain.memberDAO.deleteMember(id, pw);
		if (result == 1) {
			JOptionPane.showMessageDialog(null, "ȸ��Ż�� �Ϸ�Ǿ����ϴ�.", "ȸ��Ż��", JOptionPane.CLOSED_OPTION);
			System.exit(0);
		} else {
			JOptionPane.showMessageDialog(null, "�Է��� ������ �ùٸ��� �ʽ��ϴ�.", "ȸ��Ż��", JOptionPane.WARNING_MESSAGE);
		}

	} // end regOut()

} // end WithdrawalMember
