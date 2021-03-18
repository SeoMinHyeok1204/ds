package ir;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class kuir {

	public static void main(String[] args) throws ParserConfigurationException, IOException, TransformerException {
		// TODO Auto-generated method stub
		
		makeCollection.makeXml("src/data");
		makeKeyword.makeKkma("src/collection.xml");
	}

}
