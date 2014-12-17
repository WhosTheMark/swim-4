package fr.insa.toulouse.testElastic.swim;

import java.io.Serializable;

public class Customer{
	private String name;
	private double frequency;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getFrequency() {
		return frequency;
	}
	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
	public Customer(String name, double frequency) {
		super();
		this.name = name;
		this.frequency = frequency;
	}
	public Customer(){
		this.name = "";
		this.frequency = 0.0;
	}
}
