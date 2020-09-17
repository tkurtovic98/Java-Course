package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.gui.layouts.calc.Calculator;

/**
 * Class used to represent a custom layout manager 
 * used by the {@link Calculator} to display 
 * it's components
 * @author Tomislav Kurtović
 *
 */
public class CalcLayout implements LayoutManager2 {
	/**
	 * Number of rows
	 */
	private static final int ROWS = 5;
	/**
	 * Number of columns
	 */
	private static final int COLUMNS = 7;
	/**
	 * used to check if component at position (1,1) was added
	 */
	private static Component first ;
	/**
	 * Used to check if methods for setting size have been called
	 */
	private Dimension dimension = null;
	/**
	 * Gap between components
	 */
	private int gap;
	/**
	 * Map of added components
	 */
	private Map<Component, RCPosition> mapOfComponents;
	
	/**
	 * Constructor that assigns the gap 
	 * @param gap gap between components
	 */
	public CalcLayout(int gap) {
		this.gap = gap;
		mapOfComponents = new LinkedHashMap<>();
	}
	
	/**
	 * Default constructor that sets the gap to 0
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * {@inheritDoc}
	 * not used here
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		mapOfComponents.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		dimension = size(parent, 1);
		return dimension;
	}
	
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		dimension = size(parent, 2);
		return dimension;
	}
	
	@Override
	public Dimension maximumLayoutSize(Container target) {
		dimension = size(target, 3);
		return dimension;
	}

	/**
	 * Used to calculate {@link preferredLayoutSize}, {@link minimumLayoutSize} and
	 * {@link maximumLayoutSize} 
	 * The sizes are assigned the maximum width and height of all the parent components
	 * @param parent container of the components
	 * @param action specifies which size should be calculated
	 * @return preferred dimension based on action
	 */
	private Dimension size(Container parent, int action) {
		synchronized (parent.getTreeLock()) {
			Insets insets = parent.getInsets();
			List<Component> listOfComponents = Arrays.asList(parent.getComponents());
			int width = 0;
			int height = 0;
			int containsFirst = 0;

			for (Component comp : listOfComponents) {
				Dimension d = null;
				switch (action) {
				// Preferred layout size
				case 1:
					d = comp.getPreferredSize();
					break;
				// Minimum layout size
				case 2:
					d = comp.getMinimumSize();
					break;
				case 3:
					d = comp.getMaximumSize();
					break;
				}

				if (d == null)
					continue;
				
				if(comp == first) {
					d.width = d.width/5;
					containsFirst = COLUMNS;
				}
				
				width = Math.max(d.width, width);
				height = Math.max(d.height, height);
			}
			
			return new Dimension(insets.left + (width + gap) * COLUMNS - (gap + containsFirst)  + insets.right ,
					insets.top + (gap +height) * ROWS - gap);
		}
	}

	/**
	 * Used to draw the buttons on the screen of the
	 * {@link Calculator}
	 */
	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		
		double width = parent.getWidth() - insets.left - insets.right;
		double height = parent.getHeight() - insets.top - insets.bottom;
		
		if(dimension != null) {
			width = dimension.getWidth() ;
			height = dimension.getHeight();
			dimension = null;
		}
		
		float compWidth = (float) (width - (gap * (COLUMNS-1)) )/COLUMNS;
		float compHeight = (float) (height - (gap* (ROWS-1)) )/ ROWS ;
		
		float rounder = (float) 0.5;
		
		for(Component comp : parent.getComponents()) {
			RCPosition position = mapOfComponents.get(comp);
			if(position.getRow() == 1 && position.getColumn() == 1) {
				comp.setBounds(insets.left, insets.top,(gap + Math.round(compWidth))*5 - gap, Math.round(compHeight));
				continue;
			}
			comp.setBounds(insets.left + (gap + Math.round(compWidth)) *(position.getColumn()-1),
					insets.top + (gap + Math.round(compHeight)) * (position.getRow()-1),
					Math.round(compWidth + rounder), Math.round(compHeight+rounder));
			rounder = -rounder;
		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(!(constraints instanceof RCPosition)) {
			throw new UnsupportedOperationException("Operation is not supported because constraint is not of type RCPosition!");
		}
		RCPosition position = (RCPosition) constraints;
		
		if(position.getRow() < 1 || position.getRow() > ROWS) {
			throw new CalcLayoutException("Invalid row !");
		}
		if(position.getColumn() < 1 || position.getColumn() > COLUMNS) {
			throw new CalcLayoutException("Invalid column !");
		}
		if(position.getRow() == 1 && position.getColumn() > 1 && position.getColumn() < COLUMNS - 1) {
			throw new CalcLayoutException("Invalid constraint !");
		}
		if(mapOfComponents.containsValue(position)){
			throw new CalcLayoutException("Constraint already exists!");
		}
		if(position.getRow() == 1 && position.getColumn() == 1) {
			first = comp;
		}
		mapOfComponents.put(comp, position);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
	}
}
