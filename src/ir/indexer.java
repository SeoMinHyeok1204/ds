package ir;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class indexer {
	@SuppressWarnings("unchecked")
	public static void makePost(String path) throws IOException, ClassNotFoundException {
		File html = new File(path);
		FileReader fr = new FileReader(html);

		String str = "";
		int ch = fr.read();
		while (ch != -1) {
			str += (char) ch;
			ch = fr.read();
		}

		Document parser = Jsoup.parse(str);
		HashMap<String, String> post = new HashMap<String, String>();

		for (int index = 0; index < 5; index++) {
			String body = parser.getElementById(String.valueOf(index)).getAllElements().get(0).ownText();
			String[] wordSet = body.split("#");
			String[][] wordTrim = new String[wordSet.length][3];

			for (int i = 0; i < wordSet.length; i++) {
				String[] tmp = wordSet[i].split(":");
				wordTrim[i][0] = tmp[0];
				wordTrim[i][1] = tmp[1];
			}

			double[] weight = new double[wordSet.length];

			for (int i = 0; i < wordSet.length; i++) {
				double df = 0;
				for (int j = 0; j < 5; j++) {
					String text = parser.getElementById(String.valueOf(j)).getAllElements().get(0).ownText();
					String[] tmpSet = text.split("#");
					String[][] tmpTrim = new String[tmpSet.length][2];
					for (int tmp = 0; tmp < tmpSet.length; tmp++)
						tmpTrim[tmp] = tmpSet[tmp].split(":");

					for (int tmp = 0; tmp < tmpSet.length; tmp++) 
						if (wordTrim[i][0].equals(tmpTrim[tmp][0]))
							df++;
				}
				wordTrim[i][2] = String.valueOf(df);
				weight[i] = Integer.parseInt(wordTrim[i][1]) * Math.log10(5 / df);
			}

			for (int i = 0; i < wordSet.length; i++) {
				
				if (!post.containsKey(wordTrim[i][0]))
					post.put(wordTrim[i][0], " " + String.valueOf(index) + " " + weight[i]);
				else {
					String nvalue = post.get(wordTrim[i][0]);
					nvalue += " " + String.valueOf(index) + " " + weight[i];
					post.put(wordTrim[i][0], nvalue);
				}
			}
		}
		FileOutputStream fos = new FileOutputStream("C:\\Users\\82103\\SimpleIR\\src\\index.post");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(post);
		oos.close();

		FileInputStream fin = new FileInputStream("C:\\Users\\82103\\SimpleIR\\src\\index.post");
		ObjectInputStream ois = new ObjectInputStream(fin);
		Object object = ois.readObject();
		ois.close();

		HashMap<String, String> hashmap = (HashMap<String, String>) object;
		Iterator<String> it = hashmap.keySet().iterator();

		while (it.hasNext()) {
			String key = it.next();
			String value = (String) hashmap.get(key);
			System.out.println(key + " - " + value);
		}
	}
}
