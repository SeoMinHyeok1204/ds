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
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

public class makeKeyword {
	public static void makeKkma(String xmlName) throws IOException, ParserConfigurationException, TransformerException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		org.w3c.dom.Document kkma = docBuilder.newDocument();

		org.w3c.dom.Element docs = kkma.createElement("docs");
		kkma.appendChild(docs);

		File xml = new File(xmlName);
		FileReader fr = new FileReader(xml);

		String str = "";
		int ch = fr.read();
		while (ch != -1) {
			str += (char) ch;
			ch = fr.read();
		}

		Document hp = Jsoup.parse(str);
		int num = hp.getElementsByTag("doc").size();
				
		for (int i = 0; i < num; i++) {
			org.w3c.dom.Element doc = kkma.createElement("doc");
			docs.appendChild(doc);
			doc.setAttribute("id", String.valueOf(i));

			org.w3c.dom.Element title = kkma.createElement("title");
			title.appendChild(kkma.createTextNode(hp.getElementById(String.valueOf(i)).getElementsByTag("title").text()));
			doc.appendChild(title);

			String bodyText = hp.getElementById(String.valueOf(i)).getAllElements().get(0).ownText();
			KeywordExtractor ke = new KeywordExtractor();
			KeywordList kl = ke.extractKeyword(bodyText, true);
			
			org.w3c.dom.Element body = kkma.createElement("body");
			for (int j = 0; j < kl.size(); j++) {
				Keyword kwrd = kl.get(j);
				body.appendChild(kkma.createTextNode(kwrd.getString() + ":" + kwrd.getCnt() + "#"));
			}
			doc.appendChild(body);
		}
		
		TransformerFactory tff = TransformerFactory.newInstance();
		Transformer tf = tff.newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		DOMSource kkmaSource = new DOMSource(kkma);
		StreamResult kkmaResult = new StreamResult(new FileOutputStream(new File("C:\\Users\\82103\\SimpleIR\\src\\index.xml")));
		tf.transform(kkmaSource, kkmaResult);
		System.out.println("index.xml 생성");
	}
}
