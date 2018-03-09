package mathutils.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import mathutils.logic.BoolInput;
import mathutils.logic.SequentProver;

public class LogicTest {
	
	BoolInput p = new BoolInput('p');
	BoolInput q = new BoolInput('q');
	BoolInput r = new BoolInput('r');
	BoolInput s = new BoolInput('s');
	SequentProver sp = new SequentProver();
	
	@Test
	public void testAndIntro() {
		sp.set(p.and(q), p, q);
		assertEquals(true, sp.hasProof());
	}
	
	@Test
	public void testAndElim1() {
		sp.set(p.and(q), p);
		assertEquals(true, sp.hasProof());
	}
	
	@Test
	public void testAndElim2() {
		sp.set(p.and(q), q);
		assertEquals(true, sp.hasProof());
	}
	
	@Test
	public void testOrIntro1() {
		sp.set(p.or(q), p);
		assertEquals(true, sp.hasProof());
	}
	
	@Test
	public void testOrIntro2() {
		sp.set(p.or(q), q);
		assertEquals(true, sp.hasProof());
	}
	
	@Test
	public void testOrElim() {
		sp.set(p.and(r).or(q.and(r)), p.or(q));
		assertEquals(true, sp.hasProof());
	}
	
	@Test
	public void testImpliesIntro() {
		sp.set(p.implies(q), p.and(q));
		assertEquals(true, sp.hasProof());
	}
	
	@Test
	public void testImpliesElim() {
		sp.set(q, p, p.implies(q));
		assertEquals(true, sp.hasProof());
	}
	
	@Test
	public void testNotIntro() {
		sp.set(p.not(), p.implies(p.not()));
		assertEquals(true, sp.hasProof());
	}
	
	@Test
	public void testNotElim() {
		sp.set(q, p, p.not());
		assertEquals(true, sp.hasProof());
	}
	
	@Test
	public void testBottomElim() {
		sp.set(q, p.and(p.not()));
		assertEquals(true, sp.hasProof());
	}
	
	@Test
	public void testPbc() {
		sp.set(p, p.not().implies(p));
		assertEquals(true, sp.hasProof());
	}

}
