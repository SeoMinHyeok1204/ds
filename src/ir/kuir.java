package ir;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class kuir {

	public static void main(String[] args)
			throws ParserConfigurationException, IOException, TransformerException, ClassNotFoundException {
		// TODO Auto-generated method stub
		if (args.length == 0) {
			//makeCollection.makeXml("C:\\Users\\82103\\SimpleIR\\src\\data");
			//makeKeyword.makeKkma("C:\\Users\\82103\\SimpleIR\\src\\collection.xml");
			//indexer.makePost("C:\\Users\\82103\\SimpleIR\\src\\index.xml");
			search.CalcSim("C:\\Users\\82103\\SimpleIR\\src\\index.post", "떡 감자 찹쌀 아이스크림 파스타");
		} else {
			if (args[0].equals("-c")) {
				String dirPath = "C:\\Users\\82103\\SimpleIR\\src\\" + args[1];
				makeCollection.makeXml(dirPath);
			} else if (args[0].equals("-k")) {
				makeKeyword.makeKkma(args[1]);
			} else if (args[0].equals("-i")) {
				indexer.makePost(args[1]);
			} else if (args[0].equals("-s") && args[2].equals("-q")) {
				search.CalcSim(args[1], args[3]);
			}
		}
	}

}
