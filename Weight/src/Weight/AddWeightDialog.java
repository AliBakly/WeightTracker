package Weight;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import java.awt.Font;
import java.text.ParseException;
import java.time.LocalDate;

import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.SystemColor;
import java.sql.SQLException;

public class AddWeightDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JFormattedTextField dateTextField;
	private JTextField weightTextField;
	private JTextField kcalTextField;
	private JTextField gymTextField;
	private StyledButtonUI niceButtonUi;
	

	private WeightDAO dao;
	private Controller controller;
	
	public AddWeightDialog(Controller controller, WeightDAO dao) {
		this();
		this.controller = controller;
		this.dao = dao;
	}
	

	/**
	 * Create the dialog.
	 */
	public AddWeightDialog() {
		setTitle("Add Weight");
		Color blue = new Color(0,181,236);
		Color grey = new Color(50, 50, 50);
		Font fontTextField = new Font("Calibri", Font.BOLD, 14);
		Font fontLabel = new Font("Calibri", Font.BOLD, 16);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		niceButtonUi = new StyledButtonUI();
		
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, "4, 2, fill, fill");
			panel.setBackground(grey);
			panel.setForeground(blue);
			{
				JLabel addLabel = new JLabel("Add New Weight");
				addLabel.setFont(new Font("Calibri", Font.BOLD, 22));
				panel.add(addLabel);
				addLabel.setBackground(grey);
				addLabel.setForeground(blue);
			}
		}
		{
			JLabel dateLabel = new JLabel("Date");
			dateLabel.setFont(fontLabel);
			contentPanel.add(dateLabel, "2, 4, left, default");
			dateLabel.setForeground(blue);
		}
		{
			MaskFormatter mask = null;
			try {
				mask = new MaskFormatter("####-##-##");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			dateTextField = new JFormattedTextField(mask);
			dateTextField.setFont(fontTextField);
			contentPanel.add(dateTextField, "4, 4, fill, default");
			dateTextField.setColumns(10);
		}
		{
			JLabel weightLabel = new JLabel("Weight (Kg)");
			weightLabel.setFont(fontLabel);
			contentPanel.add(weightLabel, "2, 6, left, default");
			weightLabel.setForeground(blue);
		}
		{
			weightTextField = new JTextField();
			weightTextField.setFont(fontTextField);
			contentPanel.add(weightTextField, "4, 6, fill, default");
			weightTextField.setColumns(10);
		}
		weightTextField.setBackground(grey);
		weightTextField.setForeground(blue);
		weightTextField.setCaretColor(blue);
		
		{
			JLabel gymLabel = new JLabel("Gym (yes/no)");
			gymLabel.setFont(fontLabel);
			contentPanel.add(gymLabel, "2, 8, right, default");
			gymLabel.setForeground(blue);
		}
		
		contentPanel.setBackground(grey);
		dateTextField.setBackground(grey);
		dateTextField.setForeground(blue);
		dateTextField.setCaretColor(blue);
		{
			gymTextField = new JTextField();
			gymTextField.setFont(fontTextField);
			contentPanel.add(gymTextField, "4, 8, fill, default");
			gymTextField.setColumns(10);
		}
		gymTextField.setBackground(grey);
		gymTextField.setForeground(blue);
		gymTextField.setCaretColor(blue);
		{
			JPanel addPanel = new JPanel();
			contentPanel.add(addPanel, "4, 12, fill, fill");
			{
				addPanel.setBackground(grey);
			}
			JButton addButton = new JButton("Add");
			addPanel.add(addButton);
			addButton.setBackground(blue);
			addButton.setForeground(grey);
			addButton.setOpaque(true);
			addButton.setUI(niceButtonUi);
			addButton.addActionListener(e->{
				saveWeight();
			});
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setBackground(blue);
				cancelButton.setForeground(grey);
				cancelButton.setUI(niceButtonUi);
				addPanel.add(cancelButton);
				cancelButton.addActionListener(e->{
					this.setVisible(false);
				});
				
			}
		}
		
		
	}
	
	protected void saveWeight() {

		try {
			// get the weight info from gui
			String date = dateTextField.getText();
			LocalDate ld = LocalDate.parse(date);
			double weight = Double.parseDouble(weightTextField.getText());
			FoodDAO foodDao = new FoodDAO();
			int kcal = foodDao.getSumKcalDate(ld) ;
			String gym = gymTextField.getText();

			Weight tempWeight = new Weight(ld, weight, kcal, gym);
			// save to the database
			try {
				dao.addWeight(tempWeight);
			}catch(SQLException e) {
				popUp fail = new popUp("saving weight. Duplicate date.", "Error", true);
				fail.setVisible(true);
				return;
			}
			// close dialog
			setVisible(false);
			dispose();
			// refresh gui list
			controller.refreshView();

			// show success message
			popUp success = new popUp("Weight saved succesfully.", "Weight Saved", false);
			success.setVisible(true);
		} catch (Exception exc) {
			popUp fail = new popUp("saving weight. Please try again with valid input", "Error", true);
			fail.setVisible(true);
		}

	}
	
}







