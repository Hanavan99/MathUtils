package com.mathutils.vertex;

public class Vertex2<E> {

    private E x;
    private E y;
    
    public Vertex2(E x, E y) {
	this.x = x;
	this.y = y;
    }
    
    public E getX() {
	return x;
    }
    
    public E getY() {
	return y;
    }
    
}
