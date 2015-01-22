package results;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
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
	
	private static List<MessageResult> allResults;
	private static ResultsXML resultsXML;
	
	public ScenarioResults() {
		allResults = new ArrayList<MessageResult> ();
		resultsXML = new ResultsXML() ;
	}
	
	public String getResultReportName() {
		return resultsXML.getResultReportName();
	}
	
	/**
	 * Generate XML Result file
	 */
	public void generateXMLresult() {
		allResults = Message.getMessageResults();
		if(!allResults.isEmpty()) {
			Results results = createResultsObject();
			resultsXML.createXMLresults(results);
		} else {
			System.out.println("Sorry, no results were collected during this execution");
		}

	}

	private GeneralResults createGeneralResultsObject () {
		ResponseTimeT averageResponseTime = getAverageResponseTime();
		BigDecimal lostMsg = getLostMsg();
		ResponseTimeT max = getMaxResponseTime();
		ResponseTimeT min = getMinResponseTime();
		return resultsXML.createGeneralResults(lostMsg, averageResponseTime, max, min);
	}
	
	private ResponseTimeT getMinResponseTime() {
		long min = Long.MAX_VALUE;
		for (MessageResult messageResult : allResults) {
			if (messageResult.getResponseTime() >= 0
			    && messageResult.getResponseTime() < min) {
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
			if (messageResult.hasExchangeBeenLost()) {
				cpt++;
			}
		}
		return new BigDecimal(cpt);
	}

	private ResponseTimeT getAverageResponseTime() {
		long nbMessage = 0 ;
		long responseTimeSum = 0;
		long averageResponseTime = 0;
		for (MessageResult messageResult : allResults ) {
			responseTimeSum = responseTimeSum + messageResult.getResponseTime();
			nbMessage ++;
		}
		if(responseTimeSum > 0) { 
			averageResponseTime = responseTimeSum / nbMessage ;
		}
		ResponseTimeT average = new ResponseTimeT() ;
		average.setValue(new BigInteger(String.valueOf(averageResponseTime)));
		
		return average ;
	}

	private Exchanges createExchangesObject () {
		Exchanges exchanges = new Exchanges();
		
		for (MessageResult messageResult : allResults) {
			String responseTimeValue = null;
			String consumerId =  messageResult.getConsumerId();
			String producerId = messageResult.getProducerId();
			String date = (new Date(messageResult.getRequestTime())).toString();
			System.out.println("date : " + date);	
			boolean receiveValue = false;
			ExchangeT exchange ;
			
			if (!messageResult.hasExchangeBeenLost()) {
				responseTimeValue = String.valueOf(messageResult.getResponseTime());				
				receiveValue = true;
			}
			exchange = resultsXML.createExchange(responseTimeValue, consumerId, producerId, receiveValue, date);
			exchanges.getExchange().add(exchange);
		}
		
		return exchanges ;
	}
	
	private Results createResultsObject() {
		GeneralResults generalResults = createGeneralResultsObject();
		Exchanges exchanges = createExchangesObject();
		Results results = resultsXML.createResults(generalResults, exchanges) ;
		return results;
	}
}
