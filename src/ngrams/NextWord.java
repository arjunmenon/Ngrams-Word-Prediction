package ngrams;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class NextWord {

	public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("cProbs.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        HashMap<String, Double> cProb = new HashMap<String, Double>();
		try {
			cProb = (HashMap) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        ois.close();
        
        System.out.println(cProb.get("a backup"));
        
        FileInputStream fis1 = new FileInputStream("bigrams.ser");
        ObjectInputStream ois1 = new ObjectInputStream(fis1);
        ArrayList<String[]> bigrams = new ArrayList<String[]>();
		try {
			bigrams = (ArrayList) ois1.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        ois1.close();

        System.out.println(bigrams.size());
        
        String checkForThisBigram = "ice";
        
        ArrayList<String> matchedBigrams = new ArrayList<String>();
        
        for (String[] bigram: bigrams) {
        	if (checkForThisBigram.equals(bigram[1])) {        		
        		matchedBigrams.add(bigram[1]+" "+bigram[2]);
        	}
        }
        
        System.out.println(matchedBigrams.toString());
        
        HashMap<String, Double> topDict = new HashMap<String, Double>();
        
        for (String singleBigram : matchedBigrams) {
        	topDict.put(singleBigram, cProb.get(singleBigram));
        }
        
        System.out.println(topDict.toString());
        
        topDict.entrySet().stream()
        .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))
        .forEach(k -> System.out.println(k.getKey() + ": " + k.getValue()));
        
	}

}
