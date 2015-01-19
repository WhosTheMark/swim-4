package results;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;

import messaging.Message;
import messaging.MessageResult;
import model.*;

public class ScenarioResults {
	
	private static List<MessageResult> allResults = new ArrayList<MessageResult> () ;
	
	public ScenarioResults() {
		allResults = Message.getMessageResults();
	}
	
	public void generateXMLresult () throws PropertyException, JAXBException {
		Results results = createResultsObject();
		ResultsXML.createXMLresults(results);
	}

	private GeneralResults createGeneralResultsObject () {
		BigDecimal cpu = this.getCPUUsed();
		BigDecimal memory = this.getMemoryUsed();
		ResponseTimeT averageResponseTime = this.getAverageResponseTime();
		BigDecimal lostMsg = this.getLostMsg();
		ResponseTimeT max = this.getMaxResponseTime();
		ResponseTimeT min = this.getMinResposneTime();
		
		return ResultsXML.createGeneralResults(cpu, memory, lostMsg, averageResponseTime, max, min);
	}
	
	private ResponseTimeT getMinResposneTime() {
		long min = allResults.get(0).getResponseTime() ;
		for (MessageResult messageResult : allResults) {
			if (messageResult.getResponseTime() < min) {
				min = messageResult.getResponseTime() ;
			}
		}
		ResponseTimeT minResponseTime = new ResponseTimeT();
		minResponseTime.setValue(new BigInteger(String.valueOf(min)));
		
		return minResponseTime;
	}

	private ResponseTimeT getMaxResponseTime() {
		long max = allResults.get(0).getResponseTime() ;
		for (MessageResult messageResult : allResults) {
			if (messageResult.getResponseTime() > max) {
				max = messageResult.getResponseTime() ;
			}
		}
		ResponseTimeT maxResponseTime = new ResponseTimeT();
		maxResponseTime.setValue(new BigInteger(String.valueOf(max)));
		
		return maxResponseTime;
	}

	private BigDecimal getLostMsg() {
		int cpt = 0 ;
		for (MessageResult messageResult : allResults) {
			if (messageResult.getStatus().equals("STATUS_TIMEOUT")) {
				cpt++;
			}
		}
		
		return new BigDecimal(String.valueOf(cpt));
	}

	private ResponseTimeT getAverageResponseTime() {
		long nbMessage = 0 ;
		long responseTimeSum = 0;
		for (MessageResult messageResult : allResults ) {
			responseTimeSum = responseTimeSum + messageResult.getResponseTime();
			nbMessage ++;
		}
		long averageResponseTime = responseTimeSum / nbMessage ;
		ResponseTimeT average = new ResponseTimeT() ;
		average.setValue(new BigInteger(String.valueOf(averageResponseTime)));
		
		return average ;
	}

	private BigDecimal getMemoryUsed() {
		// TODO Auto-generated method stub
		return null;
	}

	private BigDecimal getCPUUsed() {
		// TODO Auto-generated method stub
		return null;
	}

	private Exchanges createExchangesObject () {
		Exchanges exchanges = new Exchanges();
		
		for (MessageResult messageResult : allResults) {
			String responseTimeValue = null;
			String consumerId = null;
			String producerId = null;
			boolean receiveValue = false;
			ExchangeT exchange ;
			
			if (messageResult.getStatus().equals("STATUS_TIMEOUT")) {
				exchange = ResultsXML.createExchange(responseTimeValue, consumerId, producerId, receiveValue);
			} else {
				responseTimeValue = String.valueOf(messageResult.getResponseTime());
				consumerId = messageResult.getConsumerId();
				producerId = messageResult.getProducerId();
				receiveValue = true;
				
				exchange = ResultsXML.createExchange(responseTimeValue, consumerId, producerId, receiveValue);
			}
			exchanges.getExchange().add(exchange);
		}
		
		return exchanges ;
	}
	
	private BusEnvironmentUsage createBusEnvironmentUsageObject () {
		// TODO
		return null ;
	}
	
	private Results createResultsObject() {
		GeneralResults generalResults = createGeneralResultsObject();
		Exchanges exchanges = createExchangesObject();
		BusEnvironmentUsage bus = createBusEnvironmentUsageObject();
		Results results = ResultsXML.createResults(generalResults, exchanges, bus) ;
		
		return results;
	}
}
