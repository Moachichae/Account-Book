package edu.java.accountBook;



import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.java.dao.MemberDAOImple;


import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class SetMember extends JDialog {

	MemberDAOImple dao;
	private JPanel contentPane;

	public SetMember() {
		initialize();
	}

	private void initialize() {
		setModal(true);
		dao = MemberDAOImple.getInstance();
		contentPane = new JPanel();
		setBounds(100, 100, 200, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnEdit = new JButton("È¸¿øÁ¤º¸¼öÁ¤");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditMember editmem = new EditMember();
				editmem.setVisible(true);
			}
		});
		btnEdit.setBounds(35, 35, 120, 30);
		contentPane.add(btnEdit);

		JButton btnDelete = new JButton("È¸¿øÅ»Åð");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WithdrawalMember delmem = new WithdrawalMember();
				delmem.setVisible(true);
			}
		});
		btnDelete.setBounds(35, 85, 120, 30);
		contentPane.add(btnDelete);

	} // end initialize()

} // end SetMember
