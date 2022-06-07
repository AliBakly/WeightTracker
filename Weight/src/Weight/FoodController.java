package Weight;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;

public class FoodController extends JFrame implements ControllerI {
	private FoodDAO dao;
	private JFrame frame;
	private JTable table;
	private LocalDate date;
	private Controller weightController;
	public FoodController(LocalDate date, Controller weightController){
		this.date = date;
		this.weightController = weightController;
		try {
			dao = new FoodDAO();
			frame = new JFrame("Calorie Count for " + date.toString());
			table = new JTable();
			} catch (Exception e1) {
				popUp fail = new popUp(e1.getMessage(), "Error", true);
				fail.setVisible(true);
			}
		
		SwingUtilities.invokeLater(() -> {
			try {
				createWindow();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	

	public void createWindow() throws ParseException {
	
	
	frame.setPreferredSize(new Dimension(600,600));
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	Font font = new Font("Calibri", Font.BOLD, 16);
	Font fontH = new Font("Calibri", Font.BOLD, 20);
	Color blue = new Color(0,181,236);
	Color grey = new Color(50, 50, 50);
	JPanel upperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel lowerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	Label dateLabel = new Label("Date: " + date.toString());
	dateLabel.setFont(font);
	dateLabel.setForeground(blue);
	StyledButtonUI niceButtonUI = new StyledButtonUI();
	
	JButton allButton = new JButton("Show all");
	allButton.setBackground(blue);
	allButton.setUI(niceButtonUI);
	upperPanel.add(dateLabel);
	upperPanel.add(allButton);
	
	JButton addButton = new JButton("Add Food");
	addButton.setBackground(blue);
	addButton.setUI(niceButtonUI);
	lowerPanel.add(addButton);
	
	JButton removeButton = new JButton("Remove");
	removeButton.setBackground(blue);
	removeButton.setUI(niceButtonUI);
	lowerPanel.add(removeButton);
	
	table = new JTable();
	table.setRowHeight(table.getRowHeight() + 10);
	table.setFont(font);
	table.setBackground(grey);
	table.setForeground(blue);
	table.getTableHeader().setBackground(grey);
	table.getTableHeader().setForeground(blue);
	table.getTableHeader().setFont(fontH);
	
	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	DefaultTableCellRenderer centerRenderer2 = new DefaultTableCellRenderer();
	centerRenderer.setHorizontalAlignment( JLabel.CENTER );
	centerRenderer2.setVerticalAlignment(JLabel.CENTER);
	table.setDefaultRenderer(LocalDate.class, centerRenderer);
	table.setDefaultRenderer(String.class, centerRenderer);
	table.setDefaultRenderer(Double.class, centerRenderer);
	table.setDefaultRenderer(Integer.class, centerRenderer);


	JScrollPane scroll = new JScrollPane(table);
	scroll.setBackground(grey);
	scroll.getVerticalScrollBar().setBackground(blue);
	scroll.getHorizontalScrollBar().setBackground(blue);
	scroll.getViewport().getView().setBackground(grey);
	scroll.getViewport().setBackground(grey);
	scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
	    @Override
	    protected void configureScrollBarColors() {
	        this.thumbColor = grey;
	    }
	});
	
	upperPanel.setBackground(grey);
	lowerPanel.setBackground(grey);
	
	showAll();
	allButton.addActionListener(e->{
		showAll();
	}
	);
	
	addButton.addActionListener(e->{
		AddFoodDialog afd = new AddFoodDialog(this, dao, date, weightController);
		afd.setPreferredSize(new Dimension(700,400));
		afd.pack();
		afd.setVisible(true);
	});
	
	removeButton.addActionListener(e->{
		try {
			// get the selected row
			int row = table.getSelectedRow();

			// make sure a row is selected
			if (row < 0) {
				popUp error = new popUp("You must select a food item", "Error", true);
				error.setVisible(true);
			}else {
				JDialog removeDialog = new RemoveDialog(dao, this, table, weightController, "Delete this food item?");
				removeDialog.setVisible(true);
			}
		} catch (Exception exc) {
			popUp fail = new popUp("deleting food item:" + exc.getMessage(), "Error", true);
			fail.setVisible(true);
		}
	});
	
	
	
	Container pane = frame.getContentPane();
	pane.add(upperPanel, BorderLayout.NORTH);
	pane.add(scroll, BorderLayout.CENTER);
	pane.add(lowerPanel, BorderLayout.SOUTH);
	frame.setContentPane(pane);
	frame.pack();
	frame.setVisible(true);
	}
	
	public void refreshView() {
		try {
			List<Food> foods = dao.getAllFoods(date);
			List<Integer> ids = dao.getAllids(date);
			FoodTableModel model = new FoodTableModel(foods, ids);
			table.setModel(model);
		} catch (Exception exc) {
			popUp fail = new popUp(exc.getMessage(), "Error", true);
			fail.setVisible(true);
		}
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	private void showAll() {
		try {
			List<Food> foods = dao.getAllFoods(date);
			List<Integer> ids = dao.getAllids(date);
			FoodTableModel model = new FoodTableModel(foods, ids);
			table.setModel(model);
		} catch (Exception ex) {
			popUp fail = new popUp(ex.getMessage(), "Error", true);
			fail.setVisible(true);
		}
	}
}

