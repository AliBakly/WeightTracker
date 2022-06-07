package Weight;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.text.MaskFormatter;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.JLabel;
import javax.swing.JList;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import java.awt.Font;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddFoodDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final JPanel panel = new JPanel();
	private JTextField foodTextField;
	private JTextField portionTextField;
	private StyledButtonUI niceButtonUi;

	private FoodDAO dao;
	private FoodController controller;
	private ArrayList<String> name;
	private Controller weightController;
	
	private LocalDate date;
	private JTextField textField;
	private HashMap<String, Integer> nameAndId;
	private ArrayList<String> portion;
	private HashMap<Integer, ArrayList<Entry<String, Double>>> mapIdPortion;
	private int id;
	private double[] nutrition;

	public AddFoodDialog(FoodController controller, FoodDAO dao, LocalDate date, Controller weightController) {
		this(date);
		this.controller = controller;
		this.weightController = weightController;
		this.dao = dao;
	}
	

	/**
	 * Create the dialog.
	 * @wbp.parser.constructor
	 */
	public AddFoodDialog(LocalDate date) {
		id = -1;
		this.date = date;
		Color blue = new Color(0,181,236);
		Color grey = new Color(50, 50, 50);
		Font fontTextField = new Font("Calibri", Font.BOLD, 14);
		Font fontLabel = new Font("Calibri", Font.BOLD, 16);
		
		setTitle("Add Food for "+ date.toString());
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());

		getContentPane().add(contentPanel, BorderLayout.WEST);
		getContentPane().add(panel, BorderLayout.NORTH);
		niceButtonUi = new StyledButtonUI();
		
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(68dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(44dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(100dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(104dlu;default):grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(16dlu;default):grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(17dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(18dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(4dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(6dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(12dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		{

			panel.setBackground(grey);
			panel.setForeground(blue);
			{
				JLabel addLabel = new JLabel("Add New Food for " + date.toString());
				addLabel.setFont(new Font("Calibri", Font.BOLD, 22));
				panel.add(addLabel);
				addLabel.setBackground(grey);
				addLabel.setForeground(blue);
			}
		}
		JLabel foodLabel = new JLabel("Food:");
		foodLabel.setFont(fontLabel);
		contentPanel.add(foodLabel, "2, 2, center, default");
		foodLabel.setForeground(blue);
		{
			foodTextField = new JTextField();
			foodTextField.setFont(fontTextField);
			contentPanel.add(foodTextField, "4, 2, left, default");
			foodTextField.setColumns(10);
		}
		foodTextField.setBackground(grey);
		foodTextField.setForeground(blue);
		foodTextField.setCaretColor(blue);
		JButton searchButton = new JButton("Search");
		contentPanel.add(searchButton, "6, 2, left, default");
		searchButton.setUI(niceButtonUi);
		
		JComboBox foodList = new JComboBox();
		foodList.setBackground(blue);
		foodList.setForeground(grey);
		foodList.setRenderer(new PromptComboBoxRenderer("Food Suggestion"));
		AutoCompleteDecorator.decorate(foodList);
		foodList.setEditable(false);
		
		ApiConnection connect = new ApiConnection();
		searchButton.addActionListener(e->{
			String text = foodTextField.getText().replaceAll("\\s+","%20");
			connect.search(text);
			name = connect.getNameList();
			nameAndId = connect.getNameAndIdMap();
			foodList.setModel(new ArrayListComboBoxModel(name));
		});
		foodTextField.addActionListener(searchButton.getActionListeners()[0]);
		
		contentPanel.add(foodList,"8, 2, fill, default");
		{
			JLabel portionLabel = new JLabel("Portion (g):");
			portionLabel.setFont(fontLabel);
			contentPanel.add(portionLabel, "2, 6, center, default");
			portionLabel.setForeground(blue);
		}
		
		contentPanel.setBackground(grey);
		{
			portionTextField = new JTextField();
			portionTextField.setFont(fontTextField);
			contentPanel.add(portionTextField, "4, 6, left, default");
			portionTextField.setColumns(10);
		}
		portionTextField.setBackground(grey);
		portionTextField.setForeground(blue);
		portionTextField.setCaretColor(blue);
		
		JComboBox portionList = new JComboBox();
		portionList.setRenderer(new PromptComboBoxRenderer("Portion Suggestion"));
		portionList.setForeground(grey);
		portionList.setEditable(false);
		portionList.setBackground(blue);
		contentPanel.add(portionList, "8, 6, fill, default");
		
		foodList.addActionListener(e->{
			if(!(foodList.getSelectedItem().toString().equals("Custom"))){
				String foodName = foodList.getSelectedItem().toString();
				id = nameAndId.get(foodName).intValue();
				portion = connect.getPortionList(id);
				foodTextField.setText(foodName);
				portionList.setModel(new ArrayListComboBoxModel(portion));
				nutrition = connect.getNutritionValues(id);
			}
		});
		
		portionList.addActionListener(e->{
			if(!portionList.getSelectedItem().equals("Custom")) {
				mapIdPortion = connect.getIdPortionMap();
				ArrayList<Entry<String, Double>> list = mapIdPortion.get(id);
				double grams = 0;
				for(int i = 0; i<list.size(); i++) {
					if(list.get(i).getKey().equals(portionList.getSelectedItem().toString())) {
						grams = list.get(i).getValue();
						portionTextField.setText(String.valueOf(grams)); 
					}
				}
				double kcal = (nutrition[0]*grams)/100; 
				int kcalInt = (int) kcal;
				textField.setText(String.valueOf(kcalInt));
			} else {
				textField.setText("");
				portionTextField.setText("");
			}
		});
		
		portionTextField.addActionListener(e->{
			boolean a = false;
			boolean b = false;
			boolean c = false;
			if(foodList.getSelectedItem() != null){
				a = !(foodList.getSelectedItem().toString().equals("Custom"));
				b = true; 
			}
			
			if(portionList.getSelectedItem() != null) {
				c = portionList.getSelectedItem().toString().equals("Custom");
			}else {
				c = true;
			}
			
			if(a && b && c) {
				double kcal = (Double.parseDouble(portionTextField.getText())*nutrition[0])/100; 
				int kcalInt = (int) kcal;
				textField.setText(String.valueOf(kcalInt));
			}
		});
		
		JLabel lblKcal = new JLabel("Kcal:");
		lblKcal.setForeground(blue);
		lblKcal.setFont(fontLabel);
		contentPanel.add(lblKcal, "2, 8, center, default");
		
		textField = new JTextField();
		textField.setForeground(blue);
		textField.setFont(fontTextField);
		textField.setColumns(10);
		textField.setBackground(grey);
		textField.setCaretColor(blue);
		contentPanel.add(textField, "4, 8, left, default");
		
		JButton btnCalculate = new JButton("Calculate");
		btnCalculate.setUI(niceButtonUi);
		btnCalculate.addActionListener(portionTextField.getActionListeners()[0]);
		contentPanel.add(btnCalculate, "6, 8, left, default");

		{
			JPanel addPanel = new JPanel();
			contentPanel.add(addPanel, "6, 16, fill, fill");
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
				saveFood();
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
		
	protected void saveFood() {

		try {
			// get the Food info from gui
			Food food = null;
			String foodName = foodTextField.getText();
			double amount = Double.parseDouble(portionTextField.getText());
			int kcal = Integer.parseInt(textField.getText());
			if(id!=-1) {
				double protein = nutrition[1]*((double) amount/100);
				double carbs = nutrition[2]*((double) amount/100);
				double fat = nutrition[3]*((double) amount/100);
				food = new Food(foodName, amount, kcal, protein, carbs, fat);
			} else {
				food = new Food(foodName, amount, kcal);
			}
			// Save to food database
			dao.addFood(food, date);
			// Update Kcal amount in weight database
		    weightController.getDao().updateKcal(date, dao.getSumKcalDate(date));
			weightController.refreshView();

			// close dialog
			setVisible(false);
			dispose();
			// refresh gui list
			controller.refreshView();

			// show success message
			popUp success = new popUp("Food saved succesfully.", "Food Saved", false);
			success.setVisible(true);
		} catch (Exception exc) {
			popUp fail = new popUp("Saving food. Please try again with valid input", "Error", true);
			fail.setVisible(true);
		}

	}
	
}
