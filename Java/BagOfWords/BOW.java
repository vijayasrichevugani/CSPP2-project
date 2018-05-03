//plagiarism using Bag of words techniques

import java.util.*;
import java.io.*;
import java.lang.Math.*;
import javax.swing.JOptionPane;

public class BOW{

	public static void main(String[] args) {

		System.out.println("Plagiarism check by BagOfWords technique is done");

		BOW_operations o = new BOW_operations();						//all the following used methods are defined in BOW_operations and an object is created for that class
		
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

	        			String[] list1=s11.split(" ");						//lists list1 and list2 contain all the words i.e., which are seperated by spaces
						String[] list2=s22.split(" ");

						Map <String,Integer> dict1 = new HashMap<>();						//hashmaps dict1 and dict2 contain a unique word along with its frequency of occurance in respective files
						Map <String,Integer> dict2 = new HashMap<>();

						dict1=o.dictonary(dict1,list1);
						dict2=o.dictonary(dict2,list2);

						int common=0;

						for(String str1 : dict1.keySet()){
							for(String str2 : dict2.keySet()){
								if(str1.equals(str2)){
									common+=(dict1.get(str1))*(dict2.get(str2));
								}
							}
						}						//in int common, all the common words magnitude in two files is available
						
						int mag1=o.magnitude(dict1);						//mag1 and mag2 contain square of magniude of respective file
						int mag2=o.magnitude(dict2);

						double temp1 = (common*100.0)/((Math.sqrt(mag1*mag2)));int temp2=(int)temp1*100;temp1=temp2/100.0;						//temp1 contains the float percentage of plagiarism
						temp.add(temp1+"\t\t");
					}
			    }
			}
			percentage.add(temp);
    	}
    	o.writeOutput(fileNames,percentage);						//writeOutput method in BOW_operations is called
	}
}