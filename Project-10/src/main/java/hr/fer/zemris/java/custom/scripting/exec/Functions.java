package hr.fer.zemris.java.custom.scripting.exec;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class that is used to define all the functions
 * the {@link SmartScriptEngine} has
 * @author Tomislav Kurtović
 *
 */
public class Functions {
	/**
	 * Stack used to store different objects from {@link SmartScriptEngine}
	 */
	private ObjectMultistack stack;
	/**
	 * Key of the stack
	 */
	private String key;
	/**
	 * Map of functions that have their string representation 
	 */
	private Map<String, FunctionsStrategy> functions = new HashMap<>();
	/**
	 * Output
	 */
	private RequestContext context;
	
	/**
	 * Constructor of the Functions object
	 * @param stack stack of the {@link SmartScriptEngine}
	 * @param key key from stack
	 * @param context output
	 */
	public Functions(ObjectMultistack stack, String key, RequestContext context) {
		this.context = context;
		this.stack = stack;
		this.key = key;
		populateMap();
	}
	
	/**
	 * Used to instantiate all the functions 
	 */
	private void populateMap() {
		functions.put("sin", SIN);
		functions.put("decfmt", DEC_FMT);
		functions.put("dup", DUP);
		functions.put("swap", SWAP);
		functions.put("setMimeType", SETMIMETYPE);
		functions.put("paramGet", PARAM_GET);
		functions.put("pparamGet", PPARAM_GET);
		functions.put("pparamSet", PPARAM_SET);
		functions.put("pparamDel", PPARAM_DEL);
		functions.put("tparamGet", TPARAM_GET);
		functions.put("tparamSet", TPARAM_SET);
		functions.put("tparamDel", TPARAM_DEL);
	}

	/**
	 * Executes a given fuction
	 * @param function function to execute
	 * @param left peek of stack
	 * @param right argument below peek, if exists
	 */
	public void executeFunction(String function, Object left, Object right) {
		functions.get(function).executeFunction(left, right);
	}

	/**
	 * {@link FunctionsStrategy} that calculates the sin 
	 * of the given number
	 */
	private final FunctionsStrategy SIN = new FunctionsStrategy() {
		@Override
		public void executeFunction(Object left, Object right) {
			if(right != null) {
				stack.push(key, new ValueWrapper(right));
			}
			Double val = Double.valueOf(String.valueOf(left));
			stack.push(key, new ValueWrapper(Math.sin(val *Math.PI/180)));
		}
	};
	/**
	 * {@link FunctionsStrategy} that formats the given number
	 * by the pattern it is given
	 */
	private final FunctionsStrategy DEC_FMT = new FunctionsStrategy() {
		@Override
		public void executeFunction(Object left, Object right) {
			String formatPattern = (String)left;
			DecimalFormat format = new DecimalFormat();
			format.applyPattern((formatPattern));
			stack.push(key, new ValueWrapper(format.format((double)right)));
		}
	};
	/**
	 * {@link FunctionsStrategy} that duplicates peek of stack
	 */
	private final FunctionsStrategy DUP = new FunctionsStrategy() {
		@Override
		public void executeFunction(Object left, Object right) {
			if(right != null) {
				stack.push(key, new ValueWrapper(right));
			}
			stack.push(key, new ValueWrapper(left));
			stack.push(key, new ValueWrapper(left));
		}
	};
	
	/**
	 * {@link FunctionsStrategy} that swaps peek and object
	 * below peek 
	 */
	private final FunctionsStrategy SWAP = new FunctionsStrategy() {
		@Override
		public void executeFunction(Object left, Object right) {
			stack.push(key, new ValueWrapper(right));
			stack.push(key, new ValueWrapper(left));
		}
	};
	/**
	 * {@link FunctionsStrategy} that sets myme type of 
	 * the context
	 */
	private final FunctionsStrategy SETMIMETYPE = new FunctionsStrategy() {
		@Override
		public void executeFunction(Object left, Object right) {
			stack.push(key, new ValueWrapper(right));
			context.setMimeType((String) left);
		}
	};
	
	/**
	 * {@link FunctionsStrategy} that gets the parameters from 
	 * the context
	 */
	private final FunctionsStrategy PARAM_GET = new FunctionsStrategy() {
		@Override
		public void executeFunction(Object left, Object right) {
			String value = context.getParameter(right.toString());
			stack.push(key, new ValueWrapper(value == null ? (String) left : value));
		}
	};
	
	/**
	 * {@link FunctionsStrategy} that gets the permanent parameters from 
	 * the context
	 */
	private final FunctionsStrategy PPARAM_GET = new FunctionsStrategy() {
		@Override
		public void executeFunction(Object left, Object right) {
			String value = context.getPersistentParameter(right.toString());
			stack.push(key, new ValueWrapper(value == null ? (String) left : value));
		}
	};
	/**
	 * {@link FunctionsStrategy} that sets the permanent parameters from 
	 * the context
	 */
	private final FunctionsStrategy PPARAM_SET = new FunctionsStrategy() {
		@Override
		public void executeFunction(Object left, Object right) {
			context.setPersistentParameter((String)left, String.valueOf(right));
		}
	};
	/**
	 * {@link FunctionsStrategy} that deletes the permanent parameters from 
	 * the context
	 */
	private final FunctionsStrategy PPARAM_DEL = new FunctionsStrategy() {
		@Override
		public void executeFunction(Object left, Object right) {
			if(right != null) {
				stack.push(key, new ValueWrapper(right));
			}
			context.removePersistentParameter((String) left);
		}
	};
	/**
	 * {@link FunctionsStrategy} that gets the temporary parameters from 
	 * the context
	 */
	private final FunctionsStrategy TPARAM_GET = new FunctionsStrategy() {
		@Override
		public void executeFunction(Object left, Object right) {
			String value = context.getTemporaryParameter(right.toString());
			stack.push(key, new ValueWrapper(value == null ? (String) left : value));
		}
	};
	/**
	 * {@link FunctionsStrategy} that sets the temporary parameters from 
	 * the context
	 */
	private final FunctionsStrategy TPARAM_SET = new FunctionsStrategy() {
		@Override
		public void executeFunction(Object left, Object right) {
			context.setTemporaryParameter((String)left, String.valueOf(right));
		}
	};
	/**
	 * {@link FunctionsStrategy} that deletes the temporary parameters from 
	 * the context
	 */
	private final FunctionsStrategy TPARAM_DEL = new FunctionsStrategy() {
		@Override
		public void executeFunction(Object left, Object right) {
			if(right != null) {
				stack.push(key, new ValueWrapper(right));
			}
			context.removeTemporaryParameter((String) left);
		}
	};
}
