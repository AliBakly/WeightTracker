package Weight;
import java.util.List;

import javax.swing.table.AbstractTableModel;
public class WeightTableModel extends AbstractTableModel{

	private static final int DATE_COL = 0;
	private static final int WEIGHT_COL = 1;
	private static final int KCAL_COL = 2;
	private static final int GYM_COL = 3;

	private String[] columnNames = { "Date", "Weight (KG)", "Kcal",
			"Gym (yes/no)" };
	private List<Weight> weights;

	public WeightTableModel(List<Weight> weights) {
		this.weights = weights;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return weights.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		Weight theWeight = weights.get(row);

		switch (col) {
		case DATE_COL:
			return theWeight.getDate();
		case WEIGHT_COL:
			return theWeight.getWeight();
		case KCAL_COL:
			return theWeight.getKcal();
		case GYM_COL:
			return theWeight.getGym();
		default:
			System.out.println(theWeight.getDate());
			return theWeight;
		}
	}

	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

}
