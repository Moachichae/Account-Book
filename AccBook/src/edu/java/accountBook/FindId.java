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
public class FindId extends JDialog {

	private JTextField textName;
	private JTextField textPhone;
	private JPanel contentPane;

	public FindId() {
		initialize(); 
	}

	private void initialize() {

		setModal(true);
		contentPane = new JPanel();
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(contentPane);
		getContentPane().setLayout(null);

		JLabel title = new JLabel("ID ã��");
		title.setFont(new Font("�޸�����ü", Font.PLAIN, 30));
		title.setBounds(159, 10, 110, 60);
		contentPane.add(title);

		JLabel lblName = new JLabel("\u25B7 �̸�");
		lblName.setFont(new Font("�޸�����ü", Font.PLAIN, 30));
		lblName.setBounds(15, 80, 100, 60);
		contentPane.add(lblName);

		JLabel lblContact = new JLabel("\u25B6 ����ó");
		lblContact.setFont(new Font("�޸�����ü", Font.PLAIN, 30));
		lblContact.setBounds(15, 150, 130, 60);
		contentPane.add(lblContact);

		textName = new JTextField();
		textName.setColumns(10);
		textName.setBounds(159, 90, 150, 50);
		contentPane.add(textName);

		textPhone = new JTextField();
		textPhone.setColumns(10);
		textPhone.setBounds(159, 160, 150, 50);
		contentPane.add(textPhone);

		JButton findId = new JButton("ID ã��");
		findId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				findId();
			}

		});
		findId.setFont(new Font("�޸�����ü", Font.PLAIN, 15));
		findId.setBounds(330, 125, 90, 50);
		contentPane.add(findId);

	} // end initialize

	private void findId() { // IDã��

		String name = textName.getText();
		String phone = textPhone.getText();
		String id = logMain.memberDAO.selectId(name, phone);
		if (!id.equals("")) {
			JOptionPane.showMessageDialog(null, "�˻��� ID�� " + id + " �Դϴ�.", "IDã��", JOptionPane.CLOSED_OPTION);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, "���ǿ� �ش��ϴ� ID�� �����ϴ�.", "IDã��", JOptionPane.WARNING_MESSAGE);
		}

	} // end findId()

} // end FindId
