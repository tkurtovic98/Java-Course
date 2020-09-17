package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Class used to represent an implementation of a ListModel
 * where new numbers are incrementally generated 
 * @author Tomislav Kurtović
 *
 */
public class PrimDemo  {
	
	 /**
	  * Inner class used as implementation
	  * of the {@link ListModel} interface
	  * @author Tomislav Kurtović
	  *
	  * @param <T>
	  */
	 static class PrimListModel <T> implements ListModel<Integer> {
		 /**
		  * Elements of list model
		  */
		private List<Integer> elements = new ArrayList<>();
		/**
		 * Observers
		 */
		private List<ListDataListener> observers = new ArrayList<>();
		
		/**
		 * Constructor
		 */
		public PrimListModel() {
			elements.add(1);
		}
		
		/**
		 * Used to generate new number and add it to the list
		 * so that it can be reprinted
		 */
		public void next() {
			int position = getSize();
			elements.add(getElementAt(position -1)+1);
			
			ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, position, position);
			for(ListDataListener l : observers) {
				l.intervalAdded(event);
			}
		}
		
		@Override
		public int getSize() {
			return elements.size();
		}

		@Override
		public Integer getElementAt(int index) {
			return  elements.get(index);
		}

		@Override
		public void addListDataListener(ListDataListener l) {
			observers.add(l);
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			observers.remove(l);
		}
	}
	
	public PrimDemo() {
		initGui();
	}
	
	/**
	 * Initializes the gui
	 */
	private void initGui() {
		JFrame frame = new JFrame("PRIM DEMO");
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setLayout(new BorderLayout());
		PrimListModel<Integer> model = new PrimListModel<Integer>();
		
		JList<Integer> list = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		
		JPanel center = new JPanel(new GridLayout(1,0));
		
		center.add(new JScrollPane(list));
		center.add(new JScrollPane(list2));
		
		frame.add(center, BorderLayout.CENTER);
		
		JButton next = new JButton("Next");
		next.addActionListener(e -> {
			model.next();
		});
		frame.add(next, BorderLayout.PAGE_END);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo();
		});
	}
}
