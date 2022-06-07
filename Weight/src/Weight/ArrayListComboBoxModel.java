package Weight;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class ArrayListComboBoxModel extends AbstractListModel implements ComboBoxModel {
	  private Object selectedItem;
	  private ArrayList<String> names;

	  public ArrayListComboBoxModel(ArrayList<String> names) {
	    this.names = names;
	    this.names.add(0, "Custom");
	  }

	  public Object getSelectedItem() {
	    return selectedItem;
	  }

	  public void setSelectedItem(Object newValue) {
	    selectedItem = newValue;
	  }

	  public int getSize() {
	    return names.size();
	  }

	  public Object getElementAt(int i) {
	    return names.get(i);
	  }
	}
