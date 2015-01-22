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

	private static final String OUTPUTXML = "ressources/reports/results.xml";
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
			exception.printStackTrace();
			throw new SWIMException("ERROR - Problem encountered when creating report\n"
									+ exception.getMessage());
		}
	}

	public String getResultReportName() {
		return OUTPUTXML;
	}
	
	/**
	 * Create the Results object
	 * @author David
	 * @param generalResults
	 * @param exchanges
	 * @return results
	 */
	public Results createResults(GeneralResults generalResults, Exchanges exchanges) {
		
		Results results = new Results();
		results.setExchanges(exchanges);
		results.setGeneralResults(generalResults);

		return results;
	}

	public ExchangeT createExchange(String responseTimeValue, String consumerId, String producerId,
									boolean receiveValue, String date) {		
		ExchangeT exchange = new ExchangeT();
		exchange.setConsumerId(consumerId);
		exchange.setProducerId(producerId);
		exchange.setReceived(receiveValue);
		ResponseTimeT responseTime = createResponseTime(responseTimeValue);
		exchange.setResponseTime(responseTime);	
		exchange.setDate(date);
		return exchange;
	}

	public ResponseTimeT createResponseTime(String responseTime) {
		
		ResponseTimeT averageResponseTime = new ResponseTimeT();
		averageResponseTime.setTimeUnit(MILLISECOND);
		if(responseTime != null) {
			averageResponseTime.setValue(new BigInteger(responseTime));
		}
		
		return averageResponseTime;
	}

	public GeneralResults createGeneralResults(BigDecimal lostMessages, ResponseTimeT averageResponseTime,
											ResponseTimeT max, ResponseTimeT min) {
		
		GeneralResults generalResults = new GeneralResults();
		generalResults.setAverageResponseTime(averageResponseTime);
		generalResults.setLostMessages(lostMessages);
		generalResults.setMaxResponseTime(max);
		generalResults.setMinResponseTime(min);
		
		return generalResults;
	}

}
