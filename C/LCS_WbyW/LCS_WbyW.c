/*Longest Common Substring when computed word by word*/

#include <stdio.h>
#include <string.h>
#include <math.h>
#include <dirent.h>
#include <stdlib.h>
#include "operations.h"

int main(int argc, char const *argv[]){						//main takes command line argument, i.e., path of test files
	printf("Plagiarism check by LCS WbyW technique is done\n");
	DIR *d; struct dirent *dir;
	char s[1000];
	strcpy(s,argv[1]);						//test files path from command line is now copied to s
	d=opendir(s);						//d is the directory variable containing the test files directory
	char filename[100][100];int i=0,j=0;
	if(d){
		while((dir=readdir(d))!=NULL){
			int k=strlen(dir->d_name);
			if((dir->d_name[0]!='o')&&(dir->d_name[1]!='u')&&(dir->d_name[2]!='t')&&(dir->d_name[3]!='p')&&(dir->d_name[4]!='u')&&(dir->d_name[5]!='t')&&(dir->d_name[k-1]=='t')&&(dir->d_name[k-2]=='x')&&(dir->d_name[k-3]=='t')){
				j=0;
				while(dir->d_name[j]!='\0'){
					filename[i][j]=dir->d_name[j];j++;
				}filename[i][j]='\0';i++;
			}

		}closedir(d);
	}
	int noOfFiles=i;						//noOfFiles contain the number of test files excluding "output.txt" and all other non-text files
	
	int checkstatus[noOfFiles][noOfFiles];float percentage[noOfFiles][noOfFiles];						//check status is the matrix which reduces the time and space complexity and percentage is the final matrix which is o be printed
	for(i=0;i<noOfFiles;i++){for(j=0;j<noOfFiles;j++){checkstatus[i][j]=0;percentage[i][j]=0.0;}}

	for(i=0;i<noOfFiles;i++){
		int z;
		long lSize1;
		char text1[10000],a;
		char x[1000]="";
		strcat(x,s);strcat(x,"/");strcat(x,filename[i]);						//x is a string containing the file to be opened along with the path
		FILE *fp1=fopen(x,"rb");if(!fp1){perror("test1.txt"),exit(1);}
		a=fgetc(fp1);z=0;
		while(a!=EOF){if(a=='\n'){text1[z]=' ';z++;}else{text1[z]=a;z++;}a=fgetc(fp1);}text1[z]='\0';lSize1=z;						//text1 is a string containing all the characters available in the test file
		fclose(fp1);
			
		for(j=0;j<noOfFiles;j++){

			if(checkstatus[i][j]==0){
				if(i==j){percentage[i][j]=150.0;checkstatus[i][j]=1;}
				else{
					long lSize2;
					char text2[10000];
					char y[1000]="";
					strcat(y,s);strcat(y,"/");strcat(y,filename[j]);						//y is a string containing the file to be opened along with the path
					FILE *fp2=fopen(y,"rb");if(!fp2){perror("test2.txt"),exit(1);}
					a=fgetc(fp2);z=0;
					while(a!=EOF){if(a=='\n'){text2[z]=' ';z++;}else{text2[z]=a;z++;}a=fgetc(fp2);}text2[z]='\0';lSize2=z;						//text2 is a string containing all the characters available in the test file
					fclose(fp2);
					if(strlen(text1)==0||strlen(text2)==0){percentage[i][j]=200;checkstatus[i][j]=1;percentage[j][i]=200;checkstatus[j][i]=1;}
					else{
						fileset(text1);						//fileset removes all the special charcters, converts capital cases to lower case, removes additional number of spaces between words
						fileset(text2);

						int lcs=LCS(text1,text2);						//Longest Common Substring in terms of words is computed

						int len1=numberOfWords(text1);//printf("l1: %d\n",len1);						//no.of words is coputed
						int len2=numberOfWords(text2);//printf("l2: %d\n",len2);

						percentage[i][j]=(lcs*200.0)/(len1+len2);checkstatus[i][j]=1;						//calculate is a function containing all the required calculations
						percentage[j][i]=percentage[i][j];checkstatus[j][i]=1;						//when ith file is compared with jth file the result will be same as jth file compared with ith file, this assignment will reduce time of computation
					}
				}
			}
		}
	}
	char z[1000]="";						//to print the output matrix in the reequired form into output.txt
	strcat(z,s);strcat(z,"/");strcat(z,"output.txt");
	FILE *fp;fp=fopen(z,"w+");
	fprintf(fp,"Plagiarism check by LCS WbyW technique\n\n");
	for(i=-1;i<noOfFiles;i++){
		if(i==-1){fprintf(fp,"\t\t");for(int k=0;k<noOfFiles;k++){fprintf(fp,"%s\t",filename[k]);}fprintf(fp,"\n\n");}
		else{
			fprintf(fp,"%s\t",filename[i]);
			for(j=0;j<noOfFiles;j++){
				if(percentage[i][j]==150.0){fprintf(fp,"**x**\t\t");}
				else if(percentage[i][j]==200.0||i==j){fprintf(fp,"empty\t\t");}
				else{fprintf(fp,"%.2f\t\t",percentage[i][j]);}
			}fprintf(fp,"\n\n");
		}
	}
}