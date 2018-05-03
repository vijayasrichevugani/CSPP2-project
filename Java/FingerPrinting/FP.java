//plagiarism using Bag of words techniques

import java.util.*;
import java.io.*;
import java.lang.Math.*;
import javax.swing.JOptionPane;

public class FP{

	static int base = 3;
	public static void main(String[] args) {

		System.out.println("Plagiarism check by FingerPrinting technique is done");

		FP_operations o = new FP_operations();						//all the following used methods are defined in FP_operations and an object is created for that class
		
		Scanner input = new Scanner(System.in);
		
		String path = JOptionPane.showInputDialog("Enter path:");						//an option pane is created to open a window

		File folder = new File(path);						//the folder of test cases is opened
		File[] listOfFiles = folder.listFiles();						//File array consists of file names of test cases

		ArrayList<String> fileNamesWithPath = new ArrayList<>();						//an array list of files with path is initialised
		ArrayList<String> fileNames = new ArrayList<>();						//an array list of file names is initialised

    	for (int i = 0; i < listOfFiles.length; i++){						//file names ending with ".txt" and not containing "output" are stored in filenames array
    		if (listOfFiles[i].isFile()) {
    			String temp=listOfFiles[i].getName();
        		if(temp.contains(".txt")&&!(temp.contains("output"))){
        			fileNamesWithPath.add(path+"/"+temp);
        			fileNames.add(temp);
        		}
      		}
    	}

    	ArrayList<ArrayList<String>> percentage = new ArrayList<>();						//percentage array list is the final array list
    	ArrayList<String> firstLine = new ArrayList<>();						//for first line of the matrix
    	firstLine.add("\t\t");
    	for(int i=0;i<fileNames.size();i++){firstLine.add(fileNames.get(i)+"\t");}
    	percentage.add(firstLine);

    	for(int i=0;i<fileNamesWithPath.size();i++){						//opening file 1
    		ArrayList<String> temp = new ArrayList<>();
    		temp.add(fileNames.get(i)+"\t");
    		for(int j=0;j<fileNamesWithPath.size();j++){						//opening file 2
    			if(i==j){temp.add("X\t\t");}						//if same files are compared "X" will be printed
    			else{
    				String f1 = fileNamesWithPath.get(i);
    				File file1 = new File(f1);
    				String s1 = "";
					try{
						Scanner input1 = new Scanner(file1);
						while(input1.hasNextLine()){
							s1+=input1.nextLine();
							s1+=" ";
						}
						input1.close();						//in string s1 all the data in file 1 is available
					}
        			catch (Exception ex){System.out.println(ex);}
					String f2 = fileNamesWithPath.get(j);
    				File file2 = new File(f2);
    				String s2 = "";
					try{
						Scanner input2 = new Scanner(file2);
						while(input2.hasNextLine()){
							s2+=input2.nextLine();
							s2+=" ";
						}
						input2.close();						//in string s2 all the data in file 2 is available
					}
        			catch (Exception ex){System.out.println(ex);}
					
					if(s1.length()==0||s2.length()==0){temp.add("empty\t\t");}
					else{
	        			String s11 = o.fileset(s1);						//strings s1 and s2 are set by removing special characters and additional spaces
	        			String s22 = o.fileset(s2);

	        			String set1[] = new String[s11.length()-base+1];						//String lists containing substrings
	        			String set2[] = new String[s22.length()-base+1];

	        			set1 = o.hashSet(s11);
	        			set2 = o.hashSet(s22);

	        			long hashValues1[] = new long[s11.length()-base+1];						//hashvalues list is prepared
	        			long hashValues2[] = new long[s22.length()-base+1];

	        			hashValues1=o.hashValuesList(set1);
	        			hashValues2=o.hashValuesList(set2);

	        			long mod1=o.mod(hashValues1);						//mod values for 2 lists are genetared
	        			long mod2=o.mod(hashValues2);

	        			for(int m=0;m<hashValues1.length;m++){hashValues1[m]=hashValues1[m]%mod1;}						//final hashvalues are prepared
	        			for(int m=0;m<hashValues2.length;m++){hashValues2[m]=hashValues2[m]%mod2;}

	        			long sum1 = o.sum(hashValues1);						//sum of hash values is computed
	        			long sum2 = o.sum(hashValues2);

	        			long SH=0;
	        			for(int m=0;m<hashValues1.length;m++){
	        				for(int n=0;n<hashValues2.length;n++){
	        					if(hashValues1[m]==hashValues2[n]){
	        						SH+=hashValues1[m];
	        					}
	        				}
	        			}						//sum of common hash values is computed

	        			double temp1 = ((SH*200.0)/(sum1+sum2));int temp2=(int)temp1*100;temp1=temp2/100.0;						//temp1 contains the float percentage of plagiarism
	        			if(temp1>100){temp.add("100.0\t\t");}
						else{temp.add(temp1+"\t\t");}
    				}
        		}
        	}
        		percentage.add(temp);
    	}
    	o.writeOutput(fileNames,percentage);						//writeOutput method in FP_operations is called	
    }
}