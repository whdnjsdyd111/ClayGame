package main.databases;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;


public abstract class SingleDatabaseDialog extends Dialog {
	
	protected static final String[] HEADERS = {"rank", "date" , "name", "score"};
	protected int[] my_rank = new int[] {0};
	
	protected final JTextField textField = new JTextField();
	protected final JButton button = new JButton("��ŷ ���");
	
	public SingleDatabaseDialog(JFrame frame, String name, String score) {
		super(frame, name, true);
		setLayout(null);
		setBounds(0, 0, 600, 600);
		setResizable(false);
		
		Dimension frameSize = this.getSize(); // ������ ������
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // ����� ������
		
		this.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2); // ȭ�� �߾�
		
		button.setBounds(new Rectangle(350, 550, 200, 30));
		add(button);
		
		textField.setBounds(new Rectangle(30, 550, 300, 30));
		textField.setDocument(new JTextFieldLimit(10));
		textField.setText("???");
		add(textField);
		
		getRank(score);
		insertRank(score);
		
		setVisible(true);
	}
	
	protected abstract void getRank(String score);
	protected abstract void insertRank(String score);
}
