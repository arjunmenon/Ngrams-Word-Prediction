package ngrams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Readfile {

	public static void readLines(File f) throws IOException {
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String line;
		String[] singleLine;
		ArrayList<String[]> bigramsList = new ArrayList<String[]>();
		HashMap<String, Integer> unigramsDict = new HashMap<String, Integer>();
		
		while( (line = br.readLine()) != null) {
			line.replaceAll("\r", "");
			line.replaceAll("\n", "");
			singleLine = line.split("\t");
//			bigramsList.add(Arrays.toString(singleLine));
			bigramsList.add(singleLine);
			
			if (unigramsDict.containsKey(singleLine[1])) {
				int tempValue = Integer.parseInt(singleLine[0]);
				tempValue = unigramsDict.get(singleLine[1]) + tempValue;
				unigramsDict.put(singleLine[1], tempValue);
			} else {
				unigramsDict.put(singleLine[1], Integer.parseInt(singleLine[0]));
			}
		}
		
		br.close();
		fr.close();
		
		ArrayList<String> unigramsList = new ArrayList<String>();
		
		unigramsList.addAll(unigramsDict.keySet());
		
		HashMap<String, Double> conditionalProbabilityDict = new HashMap<String, Double>();
		
		for(String[] bigram: bigramsList) {
			String firstWord = bigram[1];
			String secondWord = bigram[2];
			int count = Integer.parseInt(bigram[0]);
			double cProb = count*1.0 / unigramsDict.get(firstWord);
			conditionalProbabilityDict.put(firstWord + " " + secondWord, cProb);			
		}
		
		
		FileOutputStream fos = new FileOutputStream("cProbs.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(conditionalProbabilityDict);
        oos.close();
        
        FileOutputStream fos1 = new FileOutputStream("bigrams.ser");
        ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
        oos1.writeObject(bigramsList);
        oos1.close();
        
	}
	
	public static void main(String[] args) {
		File f = new File("ng.txt");
		try {
			readLines(f);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
