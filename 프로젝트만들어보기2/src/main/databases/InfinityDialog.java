package main.databases;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class InfinityDialog extends SingleDatabaseDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InfinityDialog(JFrame frame, String name, String score) {
		super(frame, name, score);
	}
	
	@Override
	protected void getRank(String score) {
		JTable table = new JTable(InfinityDAO.getInstance().getInfinity(score, my_rank), HEADERS) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				JComponent component = (JComponent) super.prepareRenderer(renderer, row, column);

				component.setBackground(row == my_rank[0] ? Color.CYAN : Color.WHITE);

				return component;
			}

			@Override
			public void setRowHeight(int rowHeight) {
				super.setRowHeight(23);
			}
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(new Rectangle(10, 40, 580, 508));
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = table.getColumnModel();
		
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);
		}
		
		add(scroll);
	}
	
	@Override
	protected void insertRank(String score) {
		button.addActionListener(e -> {
			InfinityDAO.getInstance().insert(textField.getText(), score);
			dispose();
		});
	}
}
