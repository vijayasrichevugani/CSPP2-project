import java.util.*;
import java.io.*;
import java.lang.Math.*;

public class LCS_ChByCh_operations{

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

	public static int LCS(String text1,String text2){						//to calculate LongestCommonSubstring
		
		int cmax=0,c=0,temp;
		for(int i=0;i<text1.length()-1;i++){int j=0;
			while(j<text2.length()-1){
				temp=i;c=0;
				if(text1.charAt(temp)==text2.charAt(j)){
					while(text1.charAt(temp)==text2.charAt(j)&&j<text2.length()-1&&temp<text1.length()-1){c++;j++;temp++;}
					if(c>cmax){cmax=c;}
				}
				else{j++;}
			}
		}
		return cmax+1;
	}

	public static void writeOutput(ArrayList<String> fileNames,ArrayList<ArrayList<String>> percentage){						//output is generated in a text file
		
		try{
			FileWriter f = new FileWriter("output.txt");						//f is a file writer object to wite output into
			f.write("Plagiarism check using LCS\n");
			for(ArrayList<String> arr: percentage){
				ArrayList<String> arr1 = (ArrayList<String>)arr.clone();
				for(String str: arr1){
					f.write(str);
				}f.write("\r\n");
			}f.close();
		}catch (Exception ex){System.out.println("File Not Found !!");}

		try{ProcessBuilder output = new ProcessBuilder("open","/Users/prudhvikchirunomula/Desktop/MSIT_Tasks/CSPP-2/Project/Plagiarism/Java/LCS_ChByCh/output.txt");output.start();}catch (Exception ex){System.out.println("error!");}						//process builder object "output" is created to open output.txt
	}
}