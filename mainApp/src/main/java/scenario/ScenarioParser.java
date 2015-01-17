package scenario;

import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import model.Scenario;

public class ScenarioParser {
	private static String scenarioXSD = "ressources/xsd/scenario.xsd";
	private static String model = "model";
	
	private Scenario scenarioUnmarshall(File scenarioFile) {
		Scenario scenario = null;
		try {
			JAXBContext jc = JAXBContext.newInstance(model);
			Unmarshaller unmarshaller = setUpUnmarshaller(jc);
			scenario = (Scenario) unmarshaller.unmarshal(scenarioFile);
		} catch (Exception e) {
			throw new ScenarioException("ERROR - file " + scenarioFile.getPath() + " does not correspond to model");
		}
		return scenario;
	}
	
	private Unmarshaller setUpUnmarshaller(JAXBContext jc) throws JAXBException, SAXException {
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = sf.newSchema(new File(scenarioXSD));
		unmarshaller.setSchema(schema);
		return unmarshaller;
	}
	
	public Scenario parseScenario(String scenarioName) {
		File scenarioFile = new File(scenarioName);
		throwExceptionIfNonExistingFile(scenarioFile);
		throwExceptionIfNotXMLFile(scenarioFile);
		return scenarioUnmarshall(scenarioFile);
	}
	
	private void throwExceptionIfNotXMLFile(File scenarioFile) {
		if(!isXMLFile(scenarioFile)) {
			throw new ScenarioException("ERROR - file " + scenarioFile.getPath() + " is not an XML file");
		}
	}

	private void throwExceptionIfNonExistingFile(File scenarioFile) {
		if(!scenarioFile.exists() || scenarioFile.isDirectory()) {
			throw new ScenarioException("ERROR - file " + scenarioFile.getPath() + " does not exist");
		}
	}

	private boolean isXMLFile(File scenarioFile) {
		String name = scenarioFile.getName();
		return name.matches("([A-Za-z0-9])+.xml");
	}
}
