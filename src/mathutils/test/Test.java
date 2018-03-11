package mathutils.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

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
import mathutils.number.Number;
import mathutils.number.WholeNumber;
import mathutils.parser.ParserOperator;

public class Test {

	public static void main(String[] args) {
		testLogic();
		// testMath();
	}

	public static void testParser() {
		ParserOperator<LogicExpression> and = new ParserOperator<LogicExpression>('^') {

			@Override
			public LogicExpression assemble(LogicExpression left, LogicExpression right) {
				// TODO Auto-generated method stub
				return null;
			}

		};
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
