package results;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import model.*;

public class ResultsXML {

	private static final String outputXML = "/mainApp/src/main/java/results/results.xml";
	private static final TimeUnitType millisecond = TimeUnitType.MS;
	
	
	/**
	 * @author David
	 * @param results object
	 * @throws JAXB Exception
	 * @throws Marshaller Property Exception
	 */
	public void createXMLresults(Results results) throws JAXBException,
			PropertyException {
		
		JAXBContext jaxbContext = JAXBContext.newInstance(Results.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
		marshaller.marshal(results, new File(outputXML));
	}

	/**
	 * Create the Results object
	 * @author David
	 * @param generalResults
	 * @param exchanges
	 * @param busEnvironment
	 * @return results
	 */
	public Results createResults(GeneralResults generalResults, Exchanges exchanges,
			BusEnvironmentUsage busEnvironment) {
		
		Results results = new Results();
		results.setBusEnvironmentUsage(busEnvironment);
		results.setExchanges(exchanges);
		results.setGeneralResults(generalResults);

		return results;
	}

	/**
	 * @author David
	 * @param cpuUsedValue : cpuUsed during the screenshot
	 * @param memoryUsedValue : memeoryUsed during the screenshot
	 * @return screenshot type
	 */
	public ScreenshotT createScreenshot(int cpuUsedValue, int memoryUsedValue) {
		
		BigDecimal cpuUsed = new BigDecimal(cpuUsedValue);
		BigDecimal memoryUsed = new BigDecimal(memoryUsedValue);
		// TODO define date
		ScreenshotT screenshot = new ScreenshotT();
		screenshot.setCpuUsed(cpuUsed);
		screenshot.setMemoryUsed(memoryUsed);
		
		return screenshot;
	}
	/**
	 * @author David
	 * @param responseTimeValue
	 * @param consumerId
	 * @param producerId
	 * @param receiveValue
	 * @return exchange
	 */
	public ExchangeT createExchange(String responseTimeValue,
			String consumerId, String producerId, boolean receiveValue) {
		
		ExchangeT exchange = new ExchangeT();
		exchange.setConsumerId(consumerId);
		exchange.setProducerId(producerId);
		// TODO define date
		exchange.setReceived(receiveValue);
		ResponseTimeT responseTime = createResponseTime(responseTimeValue);
		exchange.setResponseTime(responseTime);
		
		return exchange;
	}


	/**
	 * @author David
	 * @param responseTime string
	 * @return response time object
	 */
	public ResponseTimeT createResponseTime(String responseTime) {
		
		ResponseTimeT averageResponseTime = new ResponseTimeT();
		averageResponseTime.setTimeUnit(millisecond);
		averageResponseTime.setValue(new BigInteger(responseTime));
		
		return averageResponseTime;
	}

	/**
	 * @author David
	 * @param cpu
	 * @param memory
	 * @param lostMessages
	 * @param averageResponseTime
	 * @param max
	 * @param min
	 * @return general results
	 */
	public GeneralResults createGeneralResults(BigDecimal cpu,
			BigDecimal memory, BigDecimal lostMessages,
			ResponseTimeT averageResponseTime, ResponseTimeT max, ResponseTimeT min) {
		
		GeneralResults generalResults = new GeneralResults();
		generalResults.setAverageCPUUsed(cpu);
		generalResults.setAverageMemoryUsed(memory);
		generalResults.setAverageResponseTime(averageResponseTime);
		generalResults.setLostMessages(lostMessages);
		generalResults.setMaxResponseTime(max);
		generalResults.setMinResponseTime(min);
		
		return generalResults;
	}

}
