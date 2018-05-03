#include <stdio.h>
#include <string.h>
#include <math.h>
#include <dirent.h>
#include <stdlib.h>

struct dictonary{						//dictonary containing unique words and corresponding word count
	char uniqueword[9999];
	int uniquewordcount;
}dict[1000];

int dictonaryMaker(char text[], struct dictonary dict[],int no){
	

	//seperating word by word

	char word[no][25];
	int l,m,n,k,len=strlen(text),wordcount=0,count=0;

	int i=0,j=0;
	for(k=0;k<len;k++){
		if(text[k]!=' '){word[i][j]=text[k];j++;}
		else{word[i][j]='\0';i++;j=0;wordcount++;}
	}
	word[i][j]='\0';wordcount++;

	/*to print word by word
	 for(int k=0;k<wordcount;k++){printf("%s ",word[k]);}printf("\n");*/
	
	//to isolate unique words
	m=0;n=0;k=0;l=0;
	
	for(i=0;i<wordcount;i++){
		int flag=0;
		for(j=0;j<m;j++){
			if(strcmp(word[i],dict[j].uniqueword)==0){flag=1;count++;break;}
		}
		if(flag==0){
			while(word[m][n]!='\0'){
				dict[m].uniqueword[n]=word[m][n];n++;
			}n=0;k++;
		}m++;
	}
	k=0;
	for(i=0;i<m;i++){
		if(strcmp(dict[i].uniqueword,"\0")!=0){
			*dict[k].uniqueword=*dict[i].uniqueword;k++;
		}
	}m=k;
	
	//to calculate unique word count
	for(j=0;j<m;j++){
		dict[j].uniquewordcount=0;
		for(i=0;i<wordcount;i++){
			if(strcmp(word[i],dict[j].uniqueword)==0){dict[j].uniquewordcount++;}
		}
	}

	return m;

}

int magnitude(int m,struct dictonary dict[]){						//magnitude of each fileset is computed

	int mag=0;
	for(int i=0;i<m;i++){
		mag+=pow((dict[i].uniquewordcount),2);
	}
	// printf("mag: %d\n",mag);
	return mag;

}

int commonWords(int m1,struct dictonary dict1[],int m2,struct dictonary dict2[]){						//magnitude of common unique words is computed

	int common=0;
	for(int i=0;i<m1;i++){
		for(int j=0;j<m2;j++){
			if(strcmp(dict1[i].uniqueword,dict2[j].uniqueword)==0){common+=(dict1[i].uniquewordcount)*(dict2[j].uniquewordcount);}
		}
	}
	// printf("common: %d\n",common);
	return common;
}

float calculate(int m1,struct dictonary dict1[],int m2,struct dictonary dict2[]){						//calculates percentage using specified formulae
	
	float f1mag=magnitude(m1,dict1);
	float f2mag=magnitude(m2,dict2);

	int s=commonWords(m1,dict1,m2,dict2);

	float p = s*100.0/(sqrt(f1mag*1.0)*sqrt(f2mag*1.0));

	// printf("%f\n",p);
	return p;

}

void fileset(char text[]){						////fileset removes all the special charcters, converts capital cases to lower case, removes additional number of spaces between words

	for(int i=0;i<strlen(text);i++){
		if((text[i]>64&&text[i]<92)){text[i]=text[i]+32;}
		if((text[i]>96&&text[i]<123)||(text[i]>47&&text[i]<58)){continue;}
		else{text[i]=' ';}	
	}
	int count=0;
	for(int i=0;i<strlen(text);i++){
		if(text[i]==' '){
			count++;
		}
		else{
			if(count>1){
				for(int j=i-count+1;j<strlen(text);j++){
					text[j]=text[j+count-1];
				}
			}count=0;
		}
	}
	// printf("%s\n",text);
}

int numberOfWords(char text[]){						//no. of words is computed i.e., no.of total spaces after fileset

	int count=0;
	for(int i=0;i<strlen(text);i++){if(text[i]==' '){count++;}}
	return count;
}
