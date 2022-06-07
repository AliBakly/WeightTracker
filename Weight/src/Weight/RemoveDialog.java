package Weight;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class RemoveDialog extends JDialog {

	private DAO dao;
	private ControllerI controller;
	private JTable table;
	private Controller weightController;
	private String text;
	
	public RemoveDialog(DAO dao, ControllerI controller, JTable table, Controller weightController, String text) {
		this(text);
		this.dao = dao;
		this.controller = controller;
		this.weightController = weightController;
		this.table = table;
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RemoveDialog dialog = new RemoveDialog("kn");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RemoveDialog(String text) {
		this.text=text;
		StyledButtonUI niceUi = new StyledButtonUI();
		setBounds(100, 100, 250, 130);
		setResizable(false);
		setTitle("Delete weight");
		Color blue = new Color(0,181,236);
		Color grey = new Color(50, 50, 50);
		
		Font fontLabel = new Font("Calibri", Font.BOLD, 22);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel panel = new JPanel();
			panel.setBackground(grey);
			getContentPane().add(panel, BorderLayout.NORTH);
			{
				JLabel lblDeleteThisWeight = new JLabel(text);
				lblDeleteThisWeight.setFont(fontLabel);
				lblDeleteThisWeight.setForeground(blue);
				panel.add(lblDeleteThisWeight);
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBackground(grey);
			getContentPane().add(panel, BorderLayout.CENTER);
			{
				JButton yesButton = new JButton("Yes");
				yesButton.setUI(niceUi);
				yesButton.setBackground(blue);
				panel.add(yesButton);
				yesButton.addActionListener(e->{
					remove();
				});
			}
			{
				JButton noButton = new JButton("No");
				noButton.setUI(niceUi);
				noButton.setBackground(blue);
				panel.add(noButton);
				noButton.addActionListener(e->{
					setVisible(false);
				});
			}
		}
	}

	private void remove() {
		int row = table.getSelectedRow();
		// delete the food/weight
		try {
			if(dao instanceof WeightDAO) {
				LocalDate ld = (LocalDate) table.getValueAt(row, 0);
				dao.remove(ld);
			} else if (dao instanceof FoodDAO) {
				LocalDate ld = ((FoodController) controller).getDate();
				Integer id = (Integer) table.getModel().getValueAt(row, 6);
				WeightDAO weightDao = new WeightDAO();
				dao.remove(id);
				weightDao.updateKcal(ld, ((FoodDAO) dao).getSumKcalDate(ld));
				weightController.refreshView();
			}
			setVisible(false);
			popUp success = new popUp("Weight deleted succesfully.", "Weight Deleted", false);
			success.setVisible(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// refresh GUI
		controller.refreshView();
		
	}

}
