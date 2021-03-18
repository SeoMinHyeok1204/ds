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
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class makeCollection {
	public static int checkFiles(File folder) {
		int index = 0;
		if(folder.isDirectory())
			for (File e : folder.listFiles())
				index++;
		return index;
	}

	public static void dirRead(File folder, String[] name) {
		int i = 0;
		if(folder.isDirectory())
			for (File e : folder.listFiles())
				name[i++] = e.getName();
	}

	public static void makeXml(String folderPath)throws IOException, ParserConfigurationException, TransformerException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		org.w3c.dom.Document xml = docBuilder.newDocument();

		org.w3c.dom.Element docs = xml.createElement("docs");
		xml.appendChild(docs);

		File folder = new File(folderPath);
		int fileNum = checkFiles(folder);
		String[] fName = new String[fileNum];
		dirRead(folder, fName);

		for (int i = 0; i < fileNum; i++) {
			fName[i] = "C:\\Users\\82103\\SimpleIR\\src\\data\\" + fName[i];
			File html = new File(fName[i]);
			FileReader fr = new FileReader(html);

			String str = "";
			int ch = fr.read();
			while (ch != -1) {
				str += (char) ch;
				ch = fr.read();
			}

			Document hp = Jsoup.parse(str);

			org.w3c.dom.Element doc = xml.createElement("doc");
			docs.appendChild(doc);
			doc.setAttribute("id", String.valueOf(i));

			org.w3c.dom.Element title = xml.createElement("title");
			title.appendChild(xml.createTextNode(hp.title()));
			doc.appendChild(title);

			org.w3c.dom.Element body = xml.createElement("body");
			body.appendChild(xml.createTextNode(hp.body().text()));
			doc.appendChild(body);
		}
		
		TransformerFactory tff = TransformerFactory.newInstance();
		Transformer tf = tff.newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		DOMSource xmlSource = new DOMSource(xml);
		StreamResult xmlResult = new StreamResult(new FileOutputStream(new File("C:\\Users\\82103\\SimpleIR\\src\\collection.xml")));
		tf.transform(xmlSource, xmlResult);
		System.out.println("collection.xml 생성");
	}
}
