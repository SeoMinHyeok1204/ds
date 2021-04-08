package ir;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

public class search {
	@SuppressWarnings("unchecked")
	public static void CalcSim(String postPath, String query) throws IOException, ClassNotFoundException{
		FileInputStream fin = new FileInputStream(postPath);
		ObjectInputStream ois = new ObjectInputStream(fin);
		Object object = ois.readObject();
		ois.close();

		HashMap<String, String> hashmap = (HashMap<String, String>) object;
		Iterator<String> it = hashmap.keySet().iterator();

		String input = query;
		
		KeywordExtractor ke = new KeywordExtractor();
		KeywordList kl = ke.extractKeyword(input, true);
		int[] TF = new int[kl.size()];
		double[] similar = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		double[] CosSimilar = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		double length = 0.0;
		double tmpWeight;
		
		for (int i = 0; i < kl.size(); i++) {
			Keyword kwrd = kl.get(i);
			System.out.println(i+1+"번 형태소 : "+kwrd.getString());
			TF[i] = 1;
		}
		
		for (int i = 0; i < similar.length-1; i++) {
			System.out.println("========"+i+"번 문서========");
			for (int j = 0; j < kl.size(); j++) {
				String value = hashmap.get(kl.get(j).getString());
				if (value != null) {
					String[] weight = value.split(" ");
					similar[i] += TF[j] * Double.valueOf((weight[2 * i + 1]));
					tmpWeight = Double.valueOf(weight[2*i+1]);
					length += tmpWeight * tmpWeight;
					System.out.println(j+1+"번 형태소의 가중치 : "+weight[2*i+1]);
				}
			}
			if(similar[i] == 0.0)
				CosSimilar[i] = 0.0;
			else
				CosSimilar[i] = similar[i] / (Math.sqrt(kl.size()) * Math.sqrt(length));
			length = 0.0;
		}
		
		int[] zero = new int[5];
		int cnt=0;
		System.out.println("========유사도========");
		for(int i=0; i<5; i++) {
			System.out.println(i+"번 문서와의 코사인 유사도 : "+CosSimilar[i]);
			if(CosSimilar[i] == 0.0)
				zero[cnt++] = i;
		}
		System.out.println("========유사도 상위 문서========");
		
		int first = 5;
		int second = 5;
		int third = 5;

		for (int i = 0; i < CosSimilar.length-1; i++)
			if (CosSimilar[first] < CosSimilar[i])
				first = i;

		for (int i = 0; i < CosSimilar.length-1; i++)
			if (CosSimilar[second] < CosSimilar[i])
				if (i != first)
					second = i;

		for (int i = 0; i < CosSimilar.length-1; i++)
			if (CosSimilar[third] < CosSimilar[i])
				if (i != first && i != second)
					third = i;
		
		if(first == 5 && second == 5 && third == 5) {
			first = zero[0];
			second =zero[1];
			third =zero[2];
		}
		else if(second == 5 && third == 5) {
			second = zero[0];
			third = zero[1];
		}
		else if(third == 5)
			third = zero[0];
		
		File xml = new File("C:\\Users\\82103\\SimpleIR\\src\\collection.xml");
		FileReader fr = new FileReader(xml);

		String str = "";
		int ch = fr.read();
		while (ch != -1) {
			str += (char) ch;
			ch = fr.read();
		}

		Document hp = Jsoup.parse(str);
		
		System.out.println("유사도 1위 문서의 제목 : "+hp.getElementById(String.valueOf(first)).getElementsByTag("title").text());
		System.out.println("유사도 2위 문서의 제목 : "+hp.getElementById(String.valueOf(second)).getElementsByTag("title").text());
		System.out.println("유사도 3위 문서의 제목 : "+hp.getElementById(String.valueOf(third)).getElementsByTag("title").text());
	}

}
