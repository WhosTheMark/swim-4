package com.swim.messaging;

public class ConsumerBehaviour {
	
	private int begin;
	private int end;
	private int frequency;
	private int datasize;
	
	public ConsumerBehaviour(int begin, int end, int frequency, int datasize) {
		this.begin = begin;
		this.end = end;
		this.frequency = frequency;
		this.datasize = datasize;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getDatasize() {
		return datasize;
	}

	public void setDatasize(int datasize) {
		this.datasize = datasize;
	}
	
	

}
