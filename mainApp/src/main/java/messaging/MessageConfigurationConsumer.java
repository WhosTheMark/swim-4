package messaging;

public class MessageConfigurationConsumer extends Message {

	public MessageConfigurationConsumer() {
		super();
	}
	private String producerId;
	private int begin;
	private int end;
	private int frequency;
	private int processingTime;
	private int dataSize;

	public MessageConfigurationConsumer(String from, String to,
			String producerId, int begin, int end, int frequency,
			int processingTime, int dataSize) {
		super(from, to);
		this.producerId = producerId;
		this.begin = begin;
		this.end = end;
		this.frequency = frequency;
		this.processingTime = processingTime;
		this.dataSize = dataSize;
	}
	
	public String getProducerId() {
		return producerId;
	}
	public void setProducerId(String producerId) {
		this.producerId = producerId;
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
	public int getProcessingTime() {
		return processingTime;
	}
	public void setProcessingTime(int processingTime) {
		this.processingTime = processingTime;
	}
	public int getDataSize() {
		return dataSize;
	}
	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}
}
