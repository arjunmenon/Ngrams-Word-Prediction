package ngrams;

import java.util.ArrayList;
import java.util.Arrays;

public class Ngrams {
	
	// https://github.com/DanielJohnBenton/Ngrams.java

	public static void main(String[] args) {
		ArrayList<String> words = sanitiseToWords("the cat is here");
		ArrayList<String> ngrams = ngrams(words, 3);
		
		System.out.println(ngrams.toString());

	}
	
	public static ArrayList<String> ngrams(ArrayList<String> words, int n) {
			if(n <= 1) {
				return new ArrayList<String>(words);
			}
			
			ArrayList<String> ngrams = new ArrayList<String>();
			
			int c = words.size();
			for(int i = 0; i < c; i++) {
				if((i + n - 1) < c) {
					int stop = i + n;
					String ngramWords = words.get(i);
					
					for(int j = i + 1; j < stop; j++) {
						ngramWords +=" "+ words.get(j);
					}
					
					ngrams.add(ngramWords);
				}
			}
			return ngrams;
	}
	
	public static ArrayList<String> sanitiseToWords(String text)
		{
			String[] characters = text.split("");
			
			String sanitised = "";
			
			boolean onSpace = true;
			
			int xLastCharacter = text.length() - 1;
			for(int i = 0; i <= xLastCharacter; i++) {
				if(characters[i].matches("^[A-Za-z0-9$£%]$"))
				{
					sanitised += characters[i];
					onSpace = false;
				}
				else if(characters[i].equals("'") && i > 0 && i < xLastCharacter) {
					String surrounding = characters[i - 1] + characters[i + 1];
					if(surrounding.matches("^[A-Za-z]{2}$"))
					{
						sanitised +="'";
						onSpace = false;
					}
				}
				else if(!onSpace && i != xLastCharacter)
				{
					sanitised +=" ";
					onSpace = true;
				}
			}
			
			return new ArrayList<String>(Arrays.asList(sanitised.split("\\s+")));
	}

}
