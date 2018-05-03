//plagiarism using LCS ChByCh technique

import java.util.*;
import java.io.*;
import java.lang.Math.*;
import javax.swing.JOptionPane;

public class LCS_ChByCh{

	public static void main(String[] args) {

		System.out.println("Plagiarism check by Longest Common Substring technique is done");

		LCS_ChByCh_operations o = new LCS_ChByCh_operations();						//all the following used methods are defined in LCS_ChByCh_operations and an object is created for that class
		
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
    	ArrayList<String> firstLine = new ArrayList<>();
    	firstLine.add("\t\t");						//for first line of the matrix
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

	        			int lcs = o.LCS(s11,s22);						//length of longest common substring is calculated
	        			
	        			int len1=s11.length();						//lengths of two strings are calculated
						int len2=s22.length();

						double temppercent = (lcs*200.0)/(len1+len2);int temp2=(int)temppercent*100;temppercent=temp2/100.0;						//temp1 contains the float percentage of plagiarism
						temp.add(temppercent+"\t\t");
					}
				}
			}
			percentage.add(temp);
		}
		o.writeOutput(fileNames,percentage);						//writeOutput method in FP_operations is called		
	}
}