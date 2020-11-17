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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import main.common.MyButton;


public abstract class SingleDatabaseDialog extends Dialog {

	// �����ͺ��̽� ����� ���̾˷α� �߻� Ŭ����
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static final String[] HEADERS = {"Rank", "Date" , "Name", "Score"};	// ���̾�α� ���̺��� ����
	protected int[] my_rank = new int[] {0};	// �÷��̾��� ��ŷ�� ������ �迭 (�Ű������� �־ ������ �޼ҵ忡�� ��ŷ�� ��� ���ؼ�)
	
	protected final JTextField textField = new JTextField();	// ��ŷ�� �̸��� ���� �ؽ�Ʈ �ʵ�
	protected final MyButton button = new MyButton(350, 550, "Upload Rank", null);	// ��ŷ ��� ��ư
	
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
	protected void setTables(String[][] scores) {
		JTable table = new JTable(scores, HEADERS) {	// ���̺�
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {	// �ش� ���̺��� �� ���� ������ �ϴ�, �Ͼ������ ������ ���鼭 ����
				JComponent component = (JComponent) super.prepareRenderer(renderer, row, column);

				component.setBackground(row == my_rank[0] ? Color.CYAN : Color.WHITE);

				return component;
			}

			@Override
			public void setRowHeight(int rowHeight) {	// ���� ���̸� 23���� ����
				super.setRowHeight(23);
			}
			
			@Override
			public boolean isCellEditable(int row, int column) {	// ���� ��ġ�� ���ϵ��� ��
				return false;
			}
		};
		JScrollPane scroll = new JScrollPane(table);	// ���̺��� Ŭ ��� ���ڷ� ���ο� �־� ��ũ���� �����ϵ��� ����
		scroll.setBounds(new Rectangle(10, 40, 580, 508));
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = table.getColumnModel();
		
		for (int i = 0; i < tcm.getColumnCount(); i++) {	// ���̺� �÷� ���� �����ͼ� ���� ���ڸ� �߾ӿ� ��ġ
			tcm.getColumn(i).setCellRenderer(dtcr);
		}
		
		add(scroll);
	}
}
