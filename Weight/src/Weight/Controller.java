package Weight;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;

import java.util.*;
import java.util.List;

import javax.swing.JOptionPane;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;

public class Controller extends JFrame implements ControllerI {
	private WeightDAO dao;
	
	private JFrame frame;
	private JTable table;
	
	public Controller(){
		try {
			dao = new WeightDAO();
			frame = new JFrame("Your Weight Data");
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
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Font font = new Font("Calibri", Font.BOLD, 16);
	Font fontH = new Font("Calibri", Font.BOLD, 20);
	Color blue = new Color(0,181,236);
	Color grey = new Color(50, 50, 50);
	JPanel upperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel lowerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	Label dateLabel = new Label("Enter date:");
	dateLabel.setFont(font);
	dateLabel.setForeground(blue);;
	
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	MaskFormatter mask = new MaskFormatter("****-**-**");
	
	JFormattedTextField textField = new JFormattedTextField(mask);
	textField.setBackground(grey);
	textField.setForeground(blue);
	textField.setFont(font);
	textField.setCaretColor(blue);
	textField.setPreferredSize(new Dimension(200, 30));
	
	StyledButtonUI niceButtonUI = new StyledButtonUI();
	JButton searchButton = new JButton("Search");
	searchButton.setBackground(blue);
	searchButton.setUI(niceButtonUI);
	
	JButton allButton = new JButton("Show all");
	allButton.setBackground(blue);
	allButton.setUI(niceButtonUI);
	upperPanel.add(dateLabel);
	upperPanel.add(textField);
	upperPanel.add(searchButton);
	upperPanel.add(allButton);
	
	JButton addButton = new JButton("Add");
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
	
	try {
		List<Weight> weights = dao.getAllWeights();
		WeightTableModel model = new WeightTableModel(weights);
		table.setModel(model);
	} catch (Exception ex) {
		popUp fail = new popUp(ex.getMessage(), "Error", true);
		fail.setVisible(true);
	}
	
	showAll();
	allButton.addActionListener(e->{// When pressing show all
		showAll();
	}
	);
	
	searchButton.addActionListener(e->{// When pressing search
		try {
			if(textField.getText().equals("    " + "-" + "  " + "-" + "  ")) {//show all dates
				List<Weight> weights = dao.getAllWeights();
				WeightTableModel model = new WeightTableModel(weights);
				table.setModel(model);
			}else if(textField.getText().matches("[0-9]{4}-[0-9]{2}-  ")) {
				List<Weight> weights = null;
				String s = textField.getText().trim();
				s = s.substring(0, s.length() - 1);
				
				YearMonth ym = YearMonth.parse(s);
				weights = dao.searchWeightByDate(null, ym, null);
	
				WeightTableModel model = new WeightTableModel(weights);
				table.setModel(model);
			}else if(textField.getText().matches("[0-9]{4}-  -  ")){
				List<Weight> weights = null;
				String s = textField.getText();
				s = s.substring(0, s.length() - 6);
				
				Year y = Year.parse(s);
				weights = dao.searchWeightByDate(null, null, y);
	
				WeightTableModel model = new WeightTableModel(weights);
				table.setModel(model);
			}else {
				List<Weight> weights = null;
				LocalDate ld = LocalDate.parse(textField.getText());
				weights = dao.searchWeightByDate(ld, null, null);
	
				WeightTableModel model = new WeightTableModel(weights);
				table.setModel(model);
			}
		} catch (Exception ex) {
				String message = "<html>Wrong input. You must use one of the formats:<br/> YYYY-MM-DD <br/> YYYY-MM- <br/>YYYY </html>";
				popUp fail = new popUp(message, "Error", true);
				fail.setVisible(true);
		}
	}
	);
	
	textField.addActionListener(searchButton.getActionListeners()[0]); // when searching, but pressing enter
	
	removeButton.addActionListener(e->{
		try {
			// get the selected row
			int row = table.getSelectedRow();

			// make sure a row is selected
			if (row < 0) {
				popUp error = new popUp("You must select a weight", "Error", true);
				error.setVisible(true);
			}else {
				//prompt the user
				JDialog removeDialog = new RemoveDialog(dao, this, table, null, "Delete this weight?");
				removeDialog.setVisible(true);
			}

		} catch (Exception exc) {
			popUp fail = new popUp("deleting employee:" + exc.getMessage(), "Error", true);
			fail.setVisible(true);
		}
	});
	
	addButton.addActionListener(e->{
		AddWeightDialog dialog = new AddWeightDialog(this, dao);
		dialog.setVisible(true);
	});
	
	table.addMouseListener(new MouseAdapter() {
	    public void mousePressed(MouseEvent mouseEvent) {
	        JTable table =(JTable) mouseEvent.getSource();
	        Point point = mouseEvent.getPoint();
	        int row = table.rowAtPoint(point);
	        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
	            new FoodController((LocalDate) table.getValueAt(row, 0), Controller.this);
	        }
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
	
	private void showAll() {
		try {
			List<Weight> weights = dao.getAllWeights();
			WeightTableModel model = new WeightTableModel(weights);
			table.setModel(model);
		} catch (Exception ex) {
			popUp fail = new popUp(ex.getMessage(), "Error", true);
			fail.setVisible(true);
		}
	
	}
	
	public WeightDAO getDao() {
		return dao;
	}
	
	public void refreshView() {
		try {
			List<Weight> weights = dao.getAllWeights();

			// create the model and update the "table"
			WeightTableModel model = new WeightTableModel(weights);
			table.setModel(model);
		} catch (Exception exc) {
			popUp fail = new popUp(exc.getMessage(), "Error", true);
			fail.setVisible(true);
		}
	}

}
