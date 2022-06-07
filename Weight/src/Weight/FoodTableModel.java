package Weight;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class FoodTableModel extends AbstractTableModel{
	private static final int FOOD_COL = 0;
	private static final int AMOUNT_COL = 1;
	private static final int KCAL_COL = 2;
	private static final int PROTEIN_COL = 3;
	private static final int CARBS_COL = 4;
	private static final int FAT_COL = 5;
	private static final int ID_COL = 6;
	
	private String[] columnNames = { "Food", "Amount", "Kcal", "Protein", "Carbs", "Fat" };
	private List<Food> foods;
	private List<Integer> ids;

	public FoodTableModel(List<Food> foods, List<Integer> ids) {
		this.foods = foods;
		this.ids = ids;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return foods.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		Food theFood = foods.get(row);

		switch (col) {
		case FOOD_COL:
			return theFood.getFood();
		case AMOUNT_COL:
			return theFood.getAmount();
		case KCAL_COL:
			return theFood.getKcal();
		case PROTEIN_COL:
			return theFood.getProteinAmount();
		case CARBS_COL:
			return theFood.getCarbsAmount();
		case FAT_COL:
			return theFood.getFatAmount();
		case ID_COL:
			return ids.get(row);
		default:
			System.out.println(theFood.getFood());
			return theFood;
		}
	}

	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

}
