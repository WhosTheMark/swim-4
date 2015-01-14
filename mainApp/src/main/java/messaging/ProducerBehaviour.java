package messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProducerBehaviour {

	private int begin;
	private int end;
	private int processingTime;

	public ProducerBehaviour(){
	}
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
	
	public boolean equals(Object o) {
		if(o.getClass() == ProducerBehaviour.class) {
			ProducerBehaviour aux = (ProducerBehaviour) o;
			return begin == aux.getBegin()
				&& end == aux.getEnd()
				&& processingTime == aux.getProcessingTime();
		}
		return false;
	}

	@Override
	public String toString(){
		return "(begin:"+this.begin+","
				+"end:"+this.end+","
				+"processingTime:"+this.processingTime+")";

	}
}
