package results;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import controller.SWIMException;

import model.*;

public class ResultsXML {

	private static final String OUTPUTXML = "/mainApp/src/main/java/results/results.xml";
	private static final TimeUnitType MILLISECOND = TimeUnitType.MS;
	
	
	/**
	 * Generate the result XML file
	 * @author David
	 * @param results object
	 * @throws JAXB Exception
	 * @throws Marshaller Property Exception
	 */
	public void createXMLresults(Results results)  {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Results.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
			marshaller.marshal(results, new File(OUTPUTXML));
		} catch(JAXBException exception) {
			throw new SWIMException("ERROR - Problem encountered when creating report\n"
									+ exception.getMessage());
		}
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

	public ResponseTimeT createResponseTime(String responseTime) {
		
		ResponseTimeT averageResponseTime = new ResponseTimeT();
		averageResponseTime.setTimeUnit(MILLISECOND);
		averageResponseTime.setValue(new BigInteger(responseTime));
		
		return averageResponseTime;
	}

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
