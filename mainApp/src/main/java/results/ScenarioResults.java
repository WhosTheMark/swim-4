package results;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import messaging.Message;
import messaging.MessageResult;
import model.*;

/**
 * Handles the results : retrieves them from the database, orders them into coherent objects and creates
 * 						 the xml report
 * @author swim
 */
public class ScenarioResults {

	public static final String STATUS_OK = "STATUS_OK";
	public static final String STATUS_TIMEOUT = "STATUS_TIMEOUT";
	
	private static List<MessageResult> allResults;
	private static ResultsXML resultsXML;
	
	public ScenarioResults() {
		allResults = new ArrayList<MessageResult> ();
		resultsXML = new ResultsXML() ;
	}
	
	/**
	 * Generate XML Result file
	 */
	public void generateXMLresult() {
		allResults = Message.getMessageResults();
		Results results = createResultsObject();
		resultsXML.createXMLresults(results);
	}

	private GeneralResults createGeneralResultsObject () {
		BigDecimal cpu = getCPUUsed();
		BigDecimal memory = getMemoryUsed();
		ResponseTimeT averageResponseTime = getAverageResponseTime();
		BigDecimal lostMsg = getLostMsg();
		ResponseTimeT max = getMaxResponseTime();
		ResponseTimeT min = getMinResponseTime();
		
		return resultsXML.createGeneralResults(cpu, memory, lostMsg, averageResponseTime, max, min);
	}
	
	private ResponseTimeT getMinResponseTime() {
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
			if (STATUS_TIMEOUT.equals(messageResult.getStatus())) {
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
			
			if (STATUS_TIMEOUT.equals(messageResult.getStatus())) {
				exchange = resultsXML.createExchange(responseTimeValue, consumerId, producerId, receiveValue);
			} else {
				responseTimeValue = String.valueOf(messageResult.getResponseTime());
				consumerId = messageResult.getConsumerId();
				producerId = messageResult.getProducerId();
				receiveValue = true;
				
				exchange = resultsXML.createExchange(responseTimeValue, consumerId, producerId, receiveValue);
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
		Results results = resultsXML.createResults(generalResults, exchanges, bus) ;
		
		return results;
	}
}
