import java.util.*;
import java.io.*;
import java.lang.Math.*;

public class BOW_operations{

	public static String fileset(String s){

		String sset="";						//sset an empty string in which all data with duplicates are removed and added
		for(int i=0;i<s.length();i++){
			int k=(int)s.charAt(i);
			if(k>64&&k<92){sset+=(char)(k+32);}
			else if((k>96&&k<123)||(k>47&&k<58)){sset+=(char)(k);}
			else if(s.charAt(i)==' '){sset+=' ';}
		}
		
		String swsp="";int count=0;int flag=1;						//swsp is an array where all the extra spaces are removed from sset
		for(int i=0;i<sset.length();i++){
			if(sset.charAt(i)!=' '){swsp+=sset.charAt(i);count=0;}
			else if(sset.charAt(i)==' '){count++;}
			if(count==flag){
				swsp+=' ';}
		}
		return swsp;
	}

	public static Map<String,Integer> dictonary(Map<String,Integer> dict, String[] list){						//respective hashmap is created
		for(String str : list){
			if(str!=" "){
				if(dict.containsKey(str)){dict.put(str,1+dict.get(str));}
				else{dict.put(str,1);}
			}
		}return dict;		
	}

	public static int magnitude(Map<String,Integer> dict){						//square of magnitude of swsp is computed using its respective hashmap
		int mag=0;
		for(String str : dict.keySet()){mag+=dict.get(str)*dict.get(str);}
		return mag;
	}

	public static void writeOutput(ArrayList<String> fileNames,ArrayList<ArrayList<String>> percentage){						//output is generated in a text file
		
		try{
			FileWriter f = new FileWriter("output.txt");						//f is a file writer object to wite output into
			f.write("Plagiarism check using BagOfWords\n");
			for(ArrayList<String> arr: percentage){
				ArrayList<String> arr1 = (ArrayList<String>)arr.clone();
				for(String str: arr1){
					f.write(str);
				}f.write("\r\n");
			}f.close();
		}catch (Exception ex){System.out.println("File Not Found !!");}

		try{ProcessBuilder output = new ProcessBuilder("open","/Users/prudhvikchirunomula/Desktop/MSIT_Tasks/CSPP-2/Project/Plagiarism/Java/BOW/output.txt");output.start();}catch (Exception ex){System.out.println("error!");}						//process builder object "output" is created to open output.txt
	}
}