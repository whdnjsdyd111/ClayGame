package main.databases;

import javax.swing.JFrame;

public class TimeDialog extends SingleDatabaseDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TimeDialog(JFrame frame, String name, String score) {
		super(frame, name, score);
	}
	
	@Override
	protected void getRank(String score) {
		setTables(TimeDAO.getInstance().getTime(score, my_rank));
	}
	
	@Override
	protected void insertRank(String score) {
		button.addActionListener(e -> {
			TimeDAO.getInstance().insert(textField.getText(), score);
			dispose();
		});
	}
}
