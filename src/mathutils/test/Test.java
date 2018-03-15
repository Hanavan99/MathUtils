package mathutils.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
import mathutils.logic.BoolInput;
import mathutils.logic.LogicExpression;
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
		ParserOperator<LogicExpression> and = new ParserOperator<LogicExpression>('^') {

			@Override
			public LogicExpression assemble(LogicExpression left, LogicExpression right) {
				return new And(left, right);
			}

		};

		SequentProver sp = new SequentProver();

		Font f = new Font("Courier New", Font.PLAIN, 12);
		JFrame frame = new JFrame("testParser()");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setSize(500, 600);

		JTextField input = new JTextField();
		input.setBounds(10, 10, 300, 20);
		input.setFont(f);
		frame.add(input);

		JTextField conclusion = new JTextField();
		conclusion.setBounds(320, 10, 150, 20);
		conclusion.setFont(f);
		frame.add(conclusion);

		JTextArea output = new JTextArea();
		output.setFont(f);
		JScrollPane outputPane = new JScrollPane(output);
		outputPane.setBounds(10, 40, 460, 510);
		frame.add(outputPane);

		ActionListener al = (e) -> {
			String[] premises = input.getText().split(",");
			LogicExpression[] exs = new LogicExpression[premises.length];
			for (int i = 0; i < premises.length; i++) {
				try {
					exs[i] = sp.parse(premises[i]);
				} catch (Throwable t) {
					output.setText("Error parsing premise \"" + premises[i] + "\": " + t.getMessage());
					return;
				}
			}
			try {
				sp.set(sp.parse(conclusion.getText()), exs);
			} catch (Throwable t) {
				output.setText("Error parsing conclusion \"" + conclusion.getText() + "\": " + t.getMessage());
			}
			try {
				output.setText(sp.getProof());
			} catch (NullPointerException npe) {
				output.setText("No proof could be found.");
			} catch (Throwable t) {
				output.setText("Error finding proof: " + t.getMessage());
			}
		};

		input.addActionListener(al);
		conclusion.addActionListener(al);

		frame.setVisible(true);
	}

	public static void testLogic() {
		BoolInput p = new BoolInput('p');
		BoolInput q = new BoolInput('q');
		BoolInput r = new BoolInput('r');
		BoolInput s = new BoolInput('s');

		SequentProver sp = new SequentProver();
		sp.set(r.and(q).and(p), p.and(q.and(r)));
		System.out.println(sp.getProof());
		sp.set(p.or(q).or(r), p);
		System.out.println(sp.getProof());
		sp.set(p.or(q).and(r), p.and(r).or(q.and(r)));
		System.out.println(sp.getProof());
		sp.set(p.or(q.and(r)), p.or(q).and(p.or(r)));
		System.out.println(sp.getProof());
		sp.set(p.implies(q).implies(p.implies(r)), p.implies(q.implies(r)));
		System.out.println(sp.getProof());
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

}
