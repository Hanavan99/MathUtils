package mathutils.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import mathutils.core.DrawProperties;
import mathutils.expression.Add;
import mathutils.expression.Constant;
import mathutils.expression.Divide;
import mathutils.expression.Exponent;
import mathutils.expression.Integral;
import mathutils.expression.MathExpression;
import mathutils.expression.Multiply;
import mathutils.expression.Variable;
import mathutils.logic.LogicExpression;
import mathutils.logic.Proof;
import mathutils.logic.SequentProver;
import mathutils.logic.operators.And;
import mathutils.math.matrix.Matrix;
import mathutils.number.Number;
import mathutils.number.WholeNumber;
import mathutils.parser.ParserOperator;

public class Test {

	public static void main(String[] args) {
		// testLogic();
		// testMath();
		// testMatrix();

		testParser();
	}

	public static void testMatrix() {
		Matrix matrix = new Matrix(4, 3);
		System.out.println(matrix);
		matrix.set(0, 0, new WholeNumber(3));
		matrix.set(1, 1, new WholeNumber(-2));
		System.out.println(matrix);
		matrix.multiply(new WholeNumber(7));
		System.out.println(matrix);
		matrix.add(matrix);
		System.out.println(matrix);
	}

	public static void testParser() {
		ParserOperator<LogicExpression> and = new ParserOperator<LogicExpression>("^") {

			@Override
			public LogicExpression assemble(LogicExpression left, LogicExpression right) {
				return new And(left, right);
			}

		};

		SequentProver sp = new SequentProver();
		sp.setMode(Proof.MODE_HTML);

		Font f = new Font("Courier New", Font.PLAIN, 12);
		JFrame frame = new JFrame("testParser()");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setSize(500, 600);

		JTextField input = new JTextField();
		input.setBounds(10, 10, 460, 20);
		input.setFont(f);
		frame.add(input);

		JEditorPane output = new JEditorPane();
		output.setContentType("text/html");
		output.setEditable(false);
		output.setBackground(new Color(32, 32, 32));
		JScrollPane outputPane = new JScrollPane(output);
		outputPane.setBounds(10, 40, 460, 510);
		frame.add(outputPane);

		ActionListener al = (e) -> {
			String inputText = input.getText();
			if (!inputText.contains("|-")) {
				output.setText("Input is missing a turnstile (\"|-\").");
			} else if (inputText.trim().endsWith("|-")) {
				output.setText("Input is missing a conclusion.");
			} else {
				String[] sequents = split(inputText, "|-");
				String[] premises = sequents[0].split(",");
				LogicExpression[] exs = new LogicExpression[premises.length];
				for (int i = 0; i < premises.length; i++) {
					try {
						if (!premises[i].equals("")) {
							exs[i] = sp.parse(premises[i]);
						}
					} catch (Throwable t) {
						output.setText("Error parsing premise \"" + premises[i] + "\": " + t.getMessage());
						t.printStackTrace();
						return;
					}
				}
				try {
					sp.set(sp.parse(sequents[1]), exs);
				} catch (Throwable t) {
					output.setText("Error parsing conclusion \"" + sequents[1] + "\": " + t.getMessage());
					t.printStackTrace();
				}
				try {
					output.setText("<html><font face='Courier New' color='#AAAAAA'>" + sp.getProof() + "</html>");
				} catch (NullPointerException npe) {
					output.setText("No proof could be found.");
				} catch (Throwable t) {
					output.setText("Error finding proof: " + t.getMessage());
					t.printStackTrace();
				}
			}
		};

		input.addActionListener(al);

		frame.setVisible(true);
	}

	public static void testMath() {
		HashMap<Character, Number> vars = new HashMap<Character, Number>();

		MathExpression sum = new Add(new Constant(3), new Multiply(new Constant(2), new Variable('x')));
		System.out.println(sum);
		// System.out.println(sum.simplify());
		sum = new Integral(new Add(new Exponent(new Variable('x'), new Constant(2)),
				new Add(new Variable('x'), new Divide(new Constant(1), new Constant(2)))), 'x');

		BufferedImage img = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
		DrawProperties props = new DrawProperties();
		props.setDefaultFont(new Font("Cambria Math", Font.PLAIN, 64));
		props.setSmallFont(new Font("Cambria Math", Font.PLAIN, 48));
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(Color.BLACK);

		// g.drawString("TEST", 100, 100);
		sum.draw(g, props, 50, 256);
		try {
			ImageIO.write(img, "png", new File("expression.png"));
		} catch (Exception e) {

		}
		System.out.println(sum = sum.simplify());
		vars.put('x', new WholeNumber(5));
		System.out.println(sum.evaluate(vars));

	}

	public static String[] split(String str, String delim) {
		ArrayList<String> strings = new ArrayList<String>();
		String cur = "";
		int delimIndex = 0;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == delim.charAt(delimIndex)) {
				if (delimIndex == delim.length() - 1) {
					strings.add(cur);
					cur = "";
					delimIndex = 0;
				} else {
					delimIndex++;
				}
			} else {
				cur += c;
			}
		}
		strings.add(cur);
		return strings.toArray(new String[0]);
	}

}
