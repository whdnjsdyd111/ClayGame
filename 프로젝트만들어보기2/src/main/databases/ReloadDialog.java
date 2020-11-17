package main.databases;

import javax.swing.JFrame;

public class ReloadDialog extends SingleDatabaseDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReloadDialog(JFrame frame, String name, String score) {
		super(frame, name, score);
	}

	@Override
	protected void getRank(String score) {
		setTables(ReloadDAO.getInstance().getReload(score, my_rank));
	}

	@Override
	protected void insertRank(String score) {
		button.addActionListener(e -> {
			ReloadDAO.getInstance().insert(textField.getText(), score);
			dispose();
		});
	}
}
