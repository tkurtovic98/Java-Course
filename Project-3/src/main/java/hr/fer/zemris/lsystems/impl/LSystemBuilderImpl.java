package hr.fer.zemris.lsystems.impl;

import java.awt.Color;


import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;


/**
 * 
 * Class used to construct a new LSystem 
 * which can then generate new 
 * fractals using the LSystemViewer.
 * 
 * 
 * @author Tomislav Kurtović
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	/**
	 * array to store more than one symbol in axiom
	 */
	char[] axiomQueue;
	
	/**
	 * COllection that is used to store
	 * productions for the wanted
	 * fractal
	 */
	private Dictionary<Character, String> productions ;
	/**
	 * COllection that is used to store
	 * commands for the wanted
	 * fractal
	 */
	private Dictionary<Character,Command> command ;
	/**
	 * Represents unit length used by LSystem implementation
	 */
	private double unitLength;
	/**
	 * Represents unit length scaler used by LSystem implementation
	 */
	private double unitLengthDegreeScaler;
	/**
	 * Represents starting point
	 */
	private Vector2D origin;
	/**
	 * Represents  starting angle 
	 */
	private double angle;
	/**
	 * Represents starting symbols or states
	 */
	private String axiom;
	
	/**
	 * Represents starting turtle color
	 */
	private String color = null;
	
	/**
	 * Constructor used to set the fields to default values and
	 * also instantiate a new LSystemBuilder
	 */
	public LSystemBuilderImpl() {
		super();
		this.unitLength = 0.1;
		this.unitLengthDegreeScaler = 1;
		this.origin = new Vector2D(0,0);
		this.angle = 0;
		this.axiom = "";
		productions = new Dictionary<Character, String>();
		command = new Dictionary<Character, Command>();
	}

	/**
	 * Used to build a new LSystem
	 */
	@Override
	public LSystem build() {
		return new MyLSystem();
	}

	/**
	 * Used to set the fields by reading information
	 * from a string array that defines the variable values
	 * 
	 * @param string array that contains info about how the LSystem should look like 
	 * 
	 */
	@Override
	public LSystemBuilder configureFromText(String[] text) {
		if(text == null) {
			return null;
		}
		for(int i = 0, len = text.length; i < len; i++) {
			if(text[i].isBlank()) {
				continue;
			}
			String[] operation = text[i].split("\\s+");
			evaluateText(operation);
		}
		return this;
	}
	
	/**
	 * Helping method used to update the fields of the class
	 * given that an instance of this object is constructed 
	 * from a string array

	 * @param string array that contains info about how the LSystem should look like 
	 */
	private void evaluateText(String[] operation) {
		String nameOf = operation[0];
		
		switch(nameOf) {
			case "unitLength":
				evaluateUnitLength(operation);
				break;
			case "unitLengthDegreeScaler":
				evaluateUnitLengthDegreeScaler(operation);
				break;
			case "origin":
				evaluateOrigin(operation);
				break;
			case "angle":
				evaluateAngle(operation);
				break;
			case "command":
				evaluateCommand(operation);
				break;
			case "production":
				evaluateProduction(operation);
				break;
			case "axiom":
				evaluateAxiom(operation);
				break;
			default:
				throw new IllegalArgumentException("Argument in text is invalid");
		}
	}

	
	/**
	 * Evaluates the axiom
	 * @param operation string array that contains info about how the LSystem should look like 
	 */
	private void evaluateAxiom(String[] operation) {
		if(operation.length == 2){
			setAxiom(operation[1]);
			return;
		}
		throw new IllegalArgumentException("Illegal axiom");
	}

	/**
	 * Evaluates the production/s
	 * @param operation string array that contains info about how the LSystem should look like 
	 */
	private void evaluateProduction(String[] operation) {
		if(operation.length == 3) {
			registerProduction(operation[1].charAt(0), operation[2]);
			return;
		}
		throw new IllegalArgumentException("Illegal production");
	}

	/**
	 * Evaluates the command/s
	 * @param operation string array that contains info about how the LSystem should look like 
	 */
	private void evaluateCommand(String[] operation) {
		if(operation.length == 3) {
			registerCommand(operation[1].charAt(0), operation[2]);
			return;
		}
		if(operation.length == 4) {
			registerCommand(operation[1].charAt(0), operation[2] + " " + operation[3]);
			return;
		}
		
		throw new IllegalArgumentException("Illeagal command");
	}

	/**
	 * Evaluates the angle
	 * @param operation string array that contains info about how the LSystem should look like 
	 */
	private void evaluateAngle(String[] operation) {
		if(operation.length == 2) {
			try {
				setAngle(Double.parseDouble(operation[1]));
			} catch(NumberFormatException ex) {
				System.out.println("Invalid angle!");
			}
			return;
		}
		
		throw new IllegalArgumentException("Illegal number of arguments");
	}

	/**
	 * Evaluates the origin
	 * @param operation string array that contains info about how the LSystem should look like 
	 */
	private void evaluateOrigin(String[] operation) {
		if(operation.length == 3) {
			try {
				setOrigin(Double.parseDouble(operation[1]), Double.parseDouble(operation[2]));
			} catch(NumberFormatException ex) {
				System.out.println("Origin invalid!");
			}
		}
	}

	/**
	 * Evaluates the unit length
	 * @param operation string array that contains info about how the LSystem should look like 
	 */
	private void evaluateUnitLength(String[] operation) {
		try {
			setUnitLength(Double.parseDouble(operation[1]));
		} catch(NumberFormatException ex){
			System.out.println("Unit length invalid!");
		}
	}

	/**
	 * Evaluates the unit length scaler
	 * It checks how the scaler is written (eg. 2.0/2.3, 2.0/ 3.4, 2.0 / 4.3)
	 * @param operation string array that contains info about how the LSystem should look like 
	 */
	private void evaluateUnitLengthDegreeScaler(String[] operation) {
		if(operation.length == 2) {
			setUnitLengthDegreeScaler(Double.parseDouble(operation[1]));
			return;
		}
		
		if(operation.length == 3) {
			operation[1] = operation[1].replace("/","");
			operation[2] = operation[2].replace("/","");
			
			if(operation[1].equals("0") || operation[2].equals("0")) {
				throw new IllegalArgumentException ("UnitLengthDegreeScaler invalid!");
			}
			try {
				double value = Double.parseDouble(operation[1]) / Double.parseDouble(operation[2]);
				setUnitLengthDegreeScaler(value);
			} catch(NumberFormatException ex) {
				System.out.println("UnitLengthDegreeScaler invalid!");
			}
			return;
		}
			
		if(operation.length == 4) {
			if(operation[3].equals("0")) {
				throw new IllegalArgumentException ("UnitLengthDegreeScaler invalid!");
			}
			try {
				double value = Double.parseDouble(operation[1]) / Double.parseDouble(operation[3]);
				setUnitLengthDegreeScaler(value);
			} catch(NumberFormatException ex) {
				System.out.println("UnitLengthDegreeScaler invalid!");
			}
			return;
		}
		
		throw new IllegalArgumentException("Illegal argument Found");
	}

	/**
	 * puts the key value command pair into the dictionaries
	 */
	@Override
	public LSystemBuilder registerCommand(char key, String com) {
		populateCommands(key, com);
		return this;
	}


	/**
	 * Populates the command dictionary with appropriate given 
	 * commands  for later retrieval
	 * 
	 * @param key symbol of command
	 * @param com value of command and additional arguments
	 */
	private void populateCommands(char key, String com) {
		String[] commandToArray = com.split("\\s+");
		if(com != null) {
			String commandValue = commandToArray[0];
			switch(commandValue) {
					case "draw":
						command.put(key, drawCommand(commandToArray[1]));
						break;
					case "skip":
						command.put(key, skipCommand(commandToArray[1]));	
						break;
					case "scale":
						command.put(key, scaleCommand(commandToArray[1]));	
						break;
					case "push":
						command.put(key,pushCommand());
						break;
					case "pop":
						command.put(key, popCommand());
						break;
					case "color":
						color = commandToArray[1];
						break;
					case "rotate":
						command.put(key, rotateCommand(commandToArray[1]));
						break;
			}
		}
	}

	/**
	 * puts the key value production pair into the dictionaries
	 */
	@Override
	public LSystemBuilder registerProduction(char key, String prod) {
		productions.put(key, prod);
		return this;
	}

	/**
	 * Sets the angle and returns this class with the updated field
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}


	/**
	 * Sets the axiom and returns this class with the updated field
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}


	/**
	 * Sets the origin and returns this class with the updated field
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		origin = new Vector2D(x,y);
		return this;
	}


	/**
	 * Sets the unit length and returns this class with the updated field
	 */
	@Override
	public LSystemBuilder setUnitLength(double length) {
		unitLength = length;
		return this;
	}


	/**
	 * Sets the unit length scaler and returns this class with the updated field
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double scaler) {
		unitLengthDegreeScaler = scaler;
		return this;
	}
	

	/**
	 * creates new draw command
	 *  
	 */
	private DrawCommand drawCommand(String value) {
		return new DrawCommand(Double.valueOf(value));
	}
	
	/**
	 * creates new skip command
	 *  
	 */
	private SkipCommand skipCommand(String value) {
		return new SkipCommand(Double.valueOf(value));
	}
	
	/**
	 * creates new scale command
	 *  
	 */
	private ScaleCommand scaleCommand(String value) {
		return new ScaleCommand(Double.valueOf(value));
	}
	
	/**
	 * creates new rotate command
	 *  
	 */
	private RotateCommand rotateCommand(String value ) {
		return new RotateCommand(Double.valueOf(value));
	}
	
	/**
	 * creates new push command
	 *  
	 */
	private PushCommand pushCommand() {
		return new PushCommand();
	}
	
	/**
	 * creates new pop command
	 *  
	 */
	private PopCommand popCommand( ) {
		return new PopCommand();
	}
	
	/**
	 * creates new color command
	 *  
	 */
	private ColorCommand colorCommand(String value) {
		return new ColorCommand(Color.decode("#" + value));
	}
	
	
	/**
	 * Inner class that represents an instance of a {@link}LSystem}
	 */
	private  class MyLSystem implements LSystem {
		private Context context;
		private TurtleState turtleState;
		
		/**
		 * Default constructor for the class
		 */
		public MyLSystem() {
			if(axiom.length() > 1) {
				axiomQueue = axiom.toCharArray();
			}
		}
		
		/**
		 * Used to draw the fractal at the specified level
		 * with the specified painter
		 * It makes a new context and then adds the new state 
		 * to the memory
		 * It checks if the axiom has more symbols and then 
		 * executes the commands given the generatet sequence
		 * for the level
		 */
		@Override
		public void draw(int level, Painter painter) {
			context = new Context();
			Vector2D direction = new Vector2D(Math.cos(angle*Math.PI/180.),  Math.sin(angle*Math.PI/180.));
			
			turtleState = new TurtleState(origin, direction, Color.BLACK, unitLength * Math.pow(unitLengthDegreeScaler, level));
			context.pushState(turtleState);
			
			if(color != null) {
				colorCommand(color).execute(context, painter);
			}
			
			executeCommands( generate(level) , painter );
		}

		/**
		 * Used to execute the commands given the generated
		 * sequence.
		 * It goes through the sequence and then calls 
		 * the appropriate commands
		 *  
		 */
		private void executeCommands(String generatedSequence , Painter painter) {
			char[] symbols = generatedSequence.toCharArray();
			
			for(int i = 0, length = symbols.length; i< length; i++) {
				if(command.get(symbols[i]) != null) {
					command.get(symbols[i]).execute(context, painter);
				}
			}
		}

		/**
		 * It generates the sequence used to execute 
		 * the commands
		 * @param level how high the sequence is
		 */
		@Override
		public String generate(int level) {
			return generateProd(level, axiom);
		}
		
		/**
		 * Method used to generate productions used in drawing of fractals
		 * @param level level of fractal to draw
		 * @param axiom
		 * @return
		 */
		private String generateProd(int level, String axiom) {
			if(axiomQueue != null) {
				for(int i = 0; i < axiomQueue.length; i++) {
					if(productions.get(axiomQueue[i]) != null) {
						axiom = String.valueOf(axiomQueue[i]);
						break;
					}
				}
			}
			if(level == 0) {
				return axiom;
			}

			if(level == 1) {
				return productions.get(axiom.charAt(0));
			}
			
			StringBuilder builder = new StringBuilder();
			String rule = productions.get(axiom.charAt(0));
			
			for(int i = 0; i < rule.length(); i++) {
				if(productions.get(rule.charAt(i)) != null) {
					builder.append(generateProd(level-1, String.valueOf(rule.charAt(i))));
				} else {
					builder.append(rule.charAt(i));
				}
			}			
			return builder.toString();
		}			
		
	}
}
