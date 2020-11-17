package main.databases;

import javax.swing.JFrame;

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
		setTables(InfinityDAO.getInstance().getInfinity(score, my_rank));
	}
	
	@Override
	protected void insertRank(String score) {
		button.addActionListener(e -> {
			InfinityDAO.getInstance().insert(textField.getText(), score);
			dispose();
		});
	}
}
