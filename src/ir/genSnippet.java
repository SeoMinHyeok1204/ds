package ir;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class genSnippet {
	public static void midterm(String txt, String key) throws IOException {
		//실습시간에 절대경로를 사용해도 된다고 하셔서 절대경로를 썻습니다.
		//실행인자로 잘 실행이 안되면 kuir에서 직접 실행해 주세요.
		File html = new File("C:\\Users\\82103\\SimpleIR\\src\\ir\\"+txt);
		FileReader fr = new FileReader(html);
		String str = "";
		int ch = fr.read();
		while (ch != -1) {
			str += (char) ch;
			ch = fr.read();
		}
		String[] input = str.split("\n");
		int line = input.length;
		
		String[] keyword = key.split(" ");
		int[] cnt = new int[line];
		for (int i=0; i<cnt.length; i++)
			cnt[i] = 0;
		
		for(int i=0; i<line; i ++) {
			String[] tmp = input[i].split(" ");
			for(int k=0; k<tmp.length; k++) {
				for (int j=0; j<keyword.length; j++) {
					if (tmp[k].equals(keyword[j]))
						cnt[i]++;
				}
			}
			//System.out.println(cnt[i]);
		}
		
		int best = 0;
		for (int i=0; i<cnt.length; i++) {
			if(cnt[i] > best)
				best = i;
		}
		
		System.out.println(input[best]);
	}
}
