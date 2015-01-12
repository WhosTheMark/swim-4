package com.swim.messaging;

public class ProducerBehaviour {
	
	private int begin;
	private int end;
	private int datasize;
	private int processingTime;

	public ProducerBehaviour(int begin, int end, int datasize, int processingTime) {
		this.begin = begin;
		this.end = end;
		this.datasize = datasize;
		this.processingTime = processingTime;
	}
	
	public int getBegin() {
		return begin;
	}

	public int getEnd() {
		return end;
	}

	public int getDatasize() {
		return datasize;
	}

	public int getProcessingTime() {
		return processingTime;
	}

}
