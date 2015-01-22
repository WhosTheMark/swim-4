package fr.insa.toulouse.tp2g2.mainApp;

import static org.junit.Assert.*;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.*;
import results.ResultsXML;

public class ResultsXMLtest {

	private ResultsXML results;
	
	@Before
	public void setUp() {
		results = new ResultsXML();
	}
	
	@Test
	public void testCreateGeneralResults () {
		int lostMsg = 17;
		String averageResponseTimeValue = "9";
		String minValue = "6";
		String maxValue = "14";

		BigDecimal lostMessages = new BigDecimal(lostMsg);
		ResponseTimeT averageResponseTime = results.createResponseTime(averageResponseTimeValue);
		ResponseTimeT max = results.createResponseTime(maxValue);
		ResponseTimeT min = results.createResponseTime(minValue);

		GeneralResults generalResults = results.createGeneralResults(lostMessages,
				averageResponseTime, max, min);
		
		assertEquals(17, generalResults.getLostMessages().intValue());
		assertEquals(9, generalResults.getAverageResponseTime().getValue().intValue());
		assertEquals(6, generalResults.getMinResponseTime().getValue().intValue());
		assertEquals(14, generalResults.getMaxResponseTime().getValue().intValue());
	}
	
	@Test
	public void testCreateExchange () {
		String responseTimeValue = "14";
		String consumerId = "c1";
		String producerId = "p1";
		boolean receiveValue = true;
		String date = (new Date(Calendar.getInstance().getTimeInMillis())).toString();
		
		ExchangeT exchange = results.createExchange(responseTimeValue, consumerId,
				producerId, receiveValue,date);
		
		Assert.assertEquals(14, exchange.getResponseTime().getValue().intValue());
		Assert.assertEquals(consumerId, exchange.getConsumerId());
		Assert.assertEquals(producerId, exchange.getProducerId());
		Assert.assertTrue(exchange.isReceived());		
	}
	
	@Test
	public void generateResults() {
		Results resultsObject = createResultsObject();
		results.createXMLresults(resultsObject);
		String filename = results.getResultReportName();
		String content = TestUtilities.retrieveFileContent(filename);
		String expected = TestUtilities.retrieveFileContent("ressources/reports/expectedResults.xml");
		assertEquals(expected,content);
	}
	
	private Results createResultsObject() {
		Results results = new Results();
		
		GeneralResults generalResults = new GeneralResults();
		ResponseTimeT averageResponseTime = new ResponseTimeT();
		averageResponseTime.setTimeUnit(TimeUnitType.MS);
		averageResponseTime.setValue(new BigInteger("9"));
		generalResults.setAverageResponseTime(averageResponseTime);
		generalResults.setLostMessages(new BigDecimal(17));
		ResponseTimeT maxRT = new ResponseTimeT();
		maxRT.setTimeUnit(TimeUnitType.MS);
		maxRT.setValue(new BigInteger("14"));
		generalResults.setMaxResponseTime(maxRT);
		ResponseTimeT minRT = new ResponseTimeT();
		minRT.setTimeUnit(TimeUnitType.MS);
		minRT.setValue(new BigInteger("6"));
		generalResults.setMinResponseTime(minRT);
		
		results.setGeneralResults(generalResults);
		
		Exchanges exchanges = new Exchanges();
		ExchangeT ex1 = new ExchangeT();
		ex1.setConsumerId("c1"); ex1.setProducerId("p1");
		ex1.setReceived(true);
		ResponseTimeT r1 = new ResponseTimeT();
		r1.setTimeUnit(TimeUnitType.MS); r1.setValue(new BigInteger("5"));
		ex1.setResponseTime(r1); 
		String date = "Wed Jan 21 15:02:52 CET 2015";
		ex1.setDate(date);
		exchanges.getExchange().add(ex1);
		ExchangeT ex2 = new ExchangeT();
		ex2.setConsumerId("c1"); ex1.setProducerId("p1");
		ex2.setReceived(true);
		ResponseTimeT r2 = new ResponseTimeT();
		r2.setTimeUnit(TimeUnitType.MS);
		ex2.setResponseTime(r2); 
		ex2.setDate(date);
		exchanges.getExchange().add(ex2);
		results.setExchanges(exchanges);
		return results;
	}
	
	
	
	

}
