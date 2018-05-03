#include <stdio.h>
#include <string.h>
#include <math.h>
#include <dirent.h>
#include <stdlib.h>

int LCS(char text1[],char text2[]){						//to find Longest Common Substring

	int cmax=0,c=0,temp;
	char word1[999][50],word2[999][50];
	int wordcount1=0,wordcount2=0;

	int i=0,j=0,k=0;
	for(k=0;k<strlen(text1);k++){
		if(text1[k]!=' '){word1[i][j]=text1[k];j++;}
		else{word1[i][j]='\0';i++;j=0;wordcount1++;}
	}
	word1[i][j]='\0';//wordcount1++;
	// printf("w1: %d\n",wordcount1);

	i=0,j=0,k=0;
	for(k=0;k<strlen(text2);k++){
		if(text2[k]!=' '){word2[i][j]=text2[k];j++;}
		else{word2[i][j]='\0';i++;j=0;wordcount2++;}
	}
	word2[i][j]='\0';//wordcount2++;
	// printf("w2: %d\n",wordcount2);

	for(i=0;i<wordcount1;i++){temp=i;j=0;
		while(j<wordcount2){
			if((strcmp(word1[temp],word2[j])==0)&&(j<wordcount2)&&(temp<wordcount1)){c++;j++;temp++;}
			else{j++;}
			if(c>cmax){cmax=c;c=0;}
		}
	}

	return cmax+1;
}

void fileset(char text[]){						//fileset removes all the special charcters, converts capital cases to lower case, removes additional number of spaces between words

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
}

int numberOfWords(char text[]){						//to find the no.of words

	int count=0;
	for(int i=0;i<strlen(text);i++){if(text[i]==' '){count++;}}
	return count+1;
}
