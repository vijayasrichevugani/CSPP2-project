/*Finger Printing*/

#include <stdio.h>
#include <string.h>
#include <math.h>
#include <dirent.h>
#include <stdlib.h>
#include "operations.h"

#define base 3							//k-gram value

int main(int argc, char const *argv[]){						//main takes command line argument, i.e., path of test files
	printf("Plagiarism check by FingerPrinting technique is done\n");
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
	
	int checkstatus[noOfFiles][noOfFiles];float percentage[noOfFiles][noOfFiles];
	for(i=0;i<noOfFiles;i++){for(j=0;j<noOfFiles;j++){checkstatus[i][j]=0;percentage[i][j]=0.0;}}

	for(i=0;i<noOfFiles;i++){
		int z;
		long lSize1;
		char text1[10000],a;
		char x[1000]="";
		strcat(x,s);strcat(x,"/");strcat(x,filename[i]);						//x is a string containing the file to be opened along with the path
		FILE *fp1=fopen(x,"rb");if(!fp1){perror("test1.txt"),exit(1);}
		a=fgetc(fp1);z=0;
		while(a!=EOF){if(a=='\n'){text1[z]=' ';z++;}else{text1[z]=a;z++;}a=fgetc(fp1);}text1[z]='\0';lSize1=z;
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
					while(a!=EOF){if(a=='\n'){text2[z]=' ';z++;}else{text2[z]=a;z++;}a=fgetc(fp2);}text2[z]='\0';lSize2=z;
					fclose(fp2);
					
					if(strlen(text1)==0||strlen(text2)==0){percentage[i][j]=200;checkstatus[i][j]=1;percentage[j][i]=200;checkstatus[j][i]=1;}
					else{
						fileset(text1);						//fileset removes all the special charcters, converts capital cases to lower case, removes additional number of spaces between words
						fileset(text2);

						long list_hash_values1[strlen(text1)-base+1];						//hash values list is generated
						long mod1=hashList(list_hash_values1,text1);						//mod is calculated based on hash values list
						long list_hash_values2[strlen(text2)-base+1];
						long mod2=hashList(list_hash_values2,text2);

						for(int i=0;i<strlen(text1)-base+1;i++){list_hash_values1[i]%=mod1;}						//true hash values are computed
						for(int i=0;i<strlen(text2)-base+1;i++){list_hash_values2[i]%=mod2;}

						long sum1=sum(list_hash_values1,strlen(text1)-base+1);						//sum of all the hash values is computed
						long sum2=sum(list_hash_values2,strlen(text2)-base+1);

						long SH=0;
						for(int i=0;i<strlen(text1)-base+1;i++){						//sum of common hash values is calculated
							for(int j=0;j<strlen(text2)-base+1;j++){
								if(list_hash_values1[i]==list_hash_values2[j]){SH+=list_hash_values1[i];}
							}
						}

						percentage[i][j]=(2*SH)*100.0/(sum1+sum2);checkstatus[i][j]=1;						//percentage is computed and stored into a matrix
						percentage[j][i]=percentage[i][j];checkstatus[j][i]=1;
					}
				}
			}
		}
	}
	percentage[0][1]=percentage[1][0];
	char z[1000]="";						//to print the output matrix
	strcat(z,s);strcat(z,"/");strcat(z,"output.txt");
	FILE *fp;fp=fopen(z,"w+");
	fprintf(fp,"Plagiarism check by FingerPrinting technique\n\n");
	for(i=-1;i<noOfFiles;i++){
		if(i==-1){fprintf(fp,"\t\t");for(int k=0;k<noOfFiles;k++){fprintf(fp,"%s\t",filename[k]);}fprintf(fp,"\n\n");}
		else{
			fprintf(fp,"%s\t",filename[i]);
			for(j=0;j<noOfFiles;j++){
				if(percentage[i][j]==150.0||i==j){fprintf(fp,"**x**\t\t");}
				else if(percentage[i][j]==200.0||i==j){fprintf(fp,"empty\t\t");}
				else if(percentage[i][j]>100){fprintf(fp,"100.0\t\t");}
				else{fprintf(fp,"%.2f\t\t",percentage[i][j]);}
			}fprintf(fp,"\n\n");
		}
	}
}