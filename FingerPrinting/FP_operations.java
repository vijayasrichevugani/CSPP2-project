import java.util.*;
import java.io.*;
import java.lang.Math.*;

public class FP_operations{

	static int base =3;

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
		String str = swsp.replaceAll(" ","");
		return str;
	}

	public static String[] hashSet(String s){						//hash substring set is generated

		String list[] = new String[s.length()-base+1];
		for(int i=0;i<s.length()-base+1;i++){
			list[i]=s.substring(i,i+base);
		}
		return list;
	}

	public static long[] hashValuesList(String set[]){						//hash values for generated hash substring set is computed

		long hashvalues[] = new long[set.length];
		for(int i=0;i<set.length;i++){
			int k=base;long value=0;
			for(int j=0;j<base;j++){
				k--;
				value+=(int)(set[i].charAt(j))*(Math.pow(10,k));
			}hashvalues[i]=value;
		}return hashvalues;
	}

	public static long mod(long hashvalues[]){						//mod for the hashvalueslist is computed
		long max=0;
		for(int i=0;i<hashvalues.length;i++){if(hashvalues[i]>max){max=hashvalues[i];}}
		return primefrom(max);
	}
	
	public static int primeof(long x){						//to find whether a number is prime or not
		long i=2;int count=0;
		while(i<Math.pow(x,0.5)+1){
			if(x%i==0){count++;break;}
			else{i++;}
		}
		return count;
	}

	public static long primefrom(long x){						//to find the 1st prime number from the maximum values in the has list
		if(primeof(x)==0){return x;}
		else{return primefrom(x+1);}
	}

	public static long sum(long hashvalues[]){						//sum of all elements in hash list is computed
		long sumOfValues=0;
		for(int i=0;i<hashvalues.length;i++){sumOfValues+=hashvalues[i];}
		return sumOfValues;
	}

	public static void writeOutput(ArrayList<String> fileNames,ArrayList<ArrayList<String>> percentage){						//output is generated in a text file
		
		try{
			FileWriter f = new FileWriter("output.txt");						//f is a file writer object to wite output into
			f.write("Plagiarism check using FingerPrinting\n");
			for(ArrayList<String> arr: percentage){
				ArrayList<String> arr1 = (ArrayList<String>)arr.clone();
				for(String str: arr1){
					f.write(str);
				}f.write("\r\n");
			}f.close();
		}catch (Exception ex){System.out.println("File Not Found !!");}

		try{ProcessBuilder output = new ProcessBuilder("open","/Users/vijayasri/Desktop/MSIT_Tasks/CSPP-2/Project/Plagiarism/Java/FP/output.txt");output.start();}catch (Exception ex){System.out.println("error!");}						//process builder object "output" is created to open output.txt
	}
}
