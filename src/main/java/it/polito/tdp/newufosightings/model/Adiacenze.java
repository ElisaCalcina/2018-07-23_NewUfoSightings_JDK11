package it.polito.tdp.newufosightings.model;

public class Adiacenze {
	State s1;
	State s2;
	Integer peso;
	
	public Adiacenze(State s1, State s2, Integer peso) {
		super();
		this.s1 = s1;
		this.s2 = s2;
		this.peso = peso;
	}

	public State getS1() {
		return s1;
	}

	public void setS1(State s1) {
		this.s1 = s1;
	}

	public State getS2() {
		return s2;
	}

	public void setS2(State s2) {
		this.s2 = s2;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return  s1 +  " " + peso;
	}

	
	
	

}
