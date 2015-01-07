package fr.insa.toulouse.tp2g2.mainApp;

import static org.junit.Assert.*;


import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import model.*;
import results.ResultsXML;

public class ResultsXMLtest {

	@Test
	public void testCreateGeneralResults () {
		int lostMsg = 17;
		String averageResponseTimeValue = "9";
		String minValue = "6";
		String maxValue = "14";
		int memoryValue = 17;
		int cpuValue = 56;
		
		ResultsXML results = new ResultsXML() ;
		
		BigDecimal cpu = new BigDecimal(cpuValue);
		BigDecimal memory = new BigDecimal(memoryValue);
		BigDecimal lostMessages = new BigDecimal(lostMsg);
		ResponseTimeT averageResponseTime = results.createResponseTime(averageResponseTimeValue);
		ResponseTimeT max = results.createResponseTime(maxValue);
		ResponseTimeT min = results.createResponseTime(minValue);

		GeneralResults generalResults = results.createGeneralResults(cpu, memory, lostMessages,
				averageResponseTime, max, min);
		
		assertEquals(17, generalResults.getLostMessages().intValue());
		assertEquals(9, generalResults.getAverageResponseTime().getValue().intValue());
		assertEquals(6, generalResults.getMinResponseTime().getValue().intValue());
		assertEquals(14, generalResults.getMaxResponseTime().getValue().intValue());
		assertEquals(17, generalResults.getAverageMemoryUsed().intValue());
		assertEquals(56, generalResults.getAverageCPUUsed().intValue());
		
	}
	
	@Test
	public void testCreateExchange () {
		String responseTimeValue = "14";
		String consumerId = "c1";
		String producerId = "p1";
		boolean receiveValue = true;
		
		ResultsXML results = new ResultsXML() ;
		
		ExchangeT exchange = results.createExchange(responseTimeValue, consumerId,
				producerId, receiveValue);
		
		Assert.assertEquals(14, exchange.getResponseTime().getValue().intValue());
		Assert.assertEquals("c1", exchange.getConsumerId());
		Assert.assertEquals("p1", exchange.getProducerId());
		Assert.assertTrue(exchange.isReceived());		
	}
	
	@Test
	public void  testCreateScreenshot() {
		int cu = 69;
		int mu = 22;

		ResultsXML results = new ResultsXML() ;

		ScreenshotT screenshot = results.createScreenshot(cu, mu);
		
		Assert.assertEquals(69, screenshot.getCpuUsed().intValue());
		Assert.assertEquals(22, screenshot.getMemoryUsed().intValue());
	}
	
	

}
