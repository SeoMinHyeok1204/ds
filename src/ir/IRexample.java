package ir;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;



public class IRexample {

	public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException {
		// TODO Auto-generated method stub
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance(); 
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		org.w3c.dom.Document xml = docBuilder.newDocument();
		//확인용 주석
		File folder = new File("src/data");
		int fileNum = checkFiles(folder);
		String[] fName = new String[fileNum];
		dirRead(folder, fName);
		
		for (int i=0; i<fileNum; i++) 
			fName[i] = "src/data/" + fName[i];
		
		org.w3c.dom.Element docs = xml.createElement("docs");
		xml.appendChild(docs);
				
		for( int i=0; i<fileNum; i++) 	
			makeXml(fName, i, xml, docs);
			
		TransformerFactory tff = TransformerFactory.newInstance();
		Transformer tf = tff.newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		DOMSource source = new DOMSource(xml);
		StreamResult result = new StreamResult(new FileOutputStream(new File("src/collection.xml")));
		
		tf.transform(source, result);
		System.out.println("실행 완료");
	}

	public static int checkFiles(File folder) {
		int index = 0;
		for(File e : folder.listFiles())
			index++;
		return index;
	}
	
	public static void dirRead(File folder, String[] name) {
		int i=0;
		for(File e : folder.listFiles()) {
			name[i++] = e.getName();
		}
	}
	
	public static void makeXml(String[] name, int num, org.w3c.dom.Document xml, org.w3c.dom.Element docs) throws IOException, ParserConfigurationException, TransformerException {
		
		File html = new File(name[num]);
		FileReader fr = new FileReader(html);
		
		String str = "";
		int ch = fr.read();
		while(ch != -1) {
			str += (char)ch;
			ch = fr.read();
		}
		
		Document hp = Jsoup.parse(str);
				
		org.w3c.dom.Element doc = xml.createElement("doc");
		docs.appendChild(doc);
		doc.setAttribute("id", String.valueOf(num));
		
		org.w3c.dom.Element title = xml.createElement("title");
		title.appendChild(xml.createTextNode(hp.title()));
		doc.appendChild(title);
		
		org.w3c.dom.Element body = xml.createElement("body");
		body.appendChild(xml.createTextNode(hp.body().text()));
		doc.appendChild(body);
		
		
	}
}
