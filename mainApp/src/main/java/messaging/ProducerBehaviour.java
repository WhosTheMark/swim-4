package messaging;

public class ProducerBehaviour {
	
	private int begin;
	private int end;
	private int processingTime;

	public ProducerBehaviour(int begin, int end, int processingTime) {
		this.begin = begin;
		this.end = end;
		this.processingTime = processingTime;
	}
	
	public int getBegin() {
		return begin;
	}

	public int getEnd() {
		return end;
	}

	public int getProcessingTime() {
		return processingTime;
	}

}
