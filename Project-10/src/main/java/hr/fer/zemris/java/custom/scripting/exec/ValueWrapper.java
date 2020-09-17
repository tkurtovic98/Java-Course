package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.hw07.demo3.scripting.strategies.ArithmeticStrategy;
import hr.fer.zemris.java.hw07.demo3.scripting.strategies.DoubleValue;
import hr.fer.zemris.java.hw07.demo3.scripting.strategies.IntValue;
import hr.fer.zemris.java.hw07.demo3.scripting.strategies.NullValue;
import hr.fer.zemris.java.hw07.demo3.scripting.strategies.StringValue;

/**
 * Class used to represent values stored in the {@link ObjectMultistack}
 * 
 * @author Tomislav Kurtović
 *
 */
public class ValueWrapper {
	/**
	 * Current value of the wrapper
	 */
	private Object value;
	
	/**
	 * used to determine null values
	 */
	private ArithmeticStrategy nullValue;
	/**
	 * used to determine string values
	 */
	private ArithmeticStrategy stringValue;
	/**
	 * used to operate on double values
	 */
	private ArithmeticStrategy doubleValue;
	/**
	 * used to operate on int values
	 */
	private ArithmeticStrategy intValue;
	
	/**
	 * Constructor that instantiates instance of
	 * this class with current value set to the 
	 * passed value
	 * @param value initital value
	 */
	public ValueWrapper(Object value) {
		this.value = value;
		initializeStrategies();
	}
	
	/**
	 * Used to initialize the strategies
	 */
	private void initializeStrategies() {
		nullValue = new NullValue();
		stringValue = new StringValue();
		doubleValue = new DoubleValue();
		intValue = new IntValue();
	}

	/**
	 * Adds the given object to the current value
	 * of this class if given object is valid
	 * @param incValue value to add to current value
	 */
	public void add(Object incValue) {
		value = checkInstance(incValue,"+");
	}

	/**
	 * Subtracts the given object to the current value
	 * of this class if given object is valid
	 * @param decValue value to add to current value
	 */
	public void subtract(Object decValue) {
		value = checkInstance(decValue,"-");
	 }

	/**
	 * Multiplies the given object to the current value
	 * of this class if given object is valid
	 * @param mulValue value to add to current value
	 */
	 public void multiply(Object mulValue) {
		 value = checkInstance(mulValue,"*");
	 }

	/**
	 * Divides the given object to the current value
	 * of this class if given object is valid
	 * @param divValue value to add to current value
	 */
	 public void divide(Object divValue) {
		 value  = checkInstance(divValue,"/");
	 }

	 /**
	  * Compares the given object with the current by subtracting the 
	  * value from the current value
	  * @param withValue value to compare with current value
	  * @return positive number if current value is greater than the passed
	  * value, negative number if current value is smaller than the given value and 0
	  * if the values are the same
	  */
	 public int numCompare(Object withValue) {
		 if(withValue == null && value == null) {
			 return 0;
		 }
		 Number r = checkInstance(withValue,"-");
		 return (r instanceof Double ) ? ((Double) r).intValue() : (int) r ;
	 }
	 /**
	  * Sets current value to passed value
	  * @param i
	  */
	 public void setValue(Object i) {
			value = i;
	}
	 /**
	  * Gets current value
	  * @return current value
	  */
	 public Object getValue() {
		return value;
	}
	 
	 /**
	  * Checks what the passed argument of the wrappers operation is 
	  * and also checks the type of the current value to determine if
	  * the argument is valid and if the given operation can or can not
	  * be performed
	  * @param argument value of passed argument
	  * @param operation operation to perform if argumen and current value are valid
	  * @return Double or Integer, depending of the types of the current value and 
	  * passed argument
	  */
	 private Number checkInstance(Object argument,String operation) {
		 if(argument == null) {
			 argument = nullValue.execute(value,argument,operation);
		 }
		 if(value == null) {
			 value = nullValue.execute(value,argument,operation);
		 }
			 
		 if(argument instanceof String) {
			argument = stringValue.execute(null,argument,operation); 
		 }
		 if(value instanceof String) {
			 value = stringValue.execute(null,value,operation);
		 }
		 
		 if(argument instanceof Double || value instanceof Double) {
			return (Double) doubleValue.execute(value,argument,operation);
		 }
		 if(argument instanceof Integer && value instanceof Integer) {
			return (Integer)intValue.execute(value,argument,operation);
		 }  
		 throw new RuntimeException ("Type mismatch found ");
	}
}
