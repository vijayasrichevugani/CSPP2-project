#include <stdio.h>
#include <string.h>
#include <math.h>
#include <dirent.h>
#include <stdlib.h>

int LCS(char text1[],char text2[]){						//to find Longest Common Substring
				
	int cmax=0,c=0,temp;
	for(int i=0;i<strlen(text1);i++){int j=0;
		while(j<strlen(text2)){
			temp=i;c=0;
			if(text1[temp]==text2[j]){
				while((text1[temp]==text2[j])&&j<strlen(text2)&&temp<strlen(text1)){c++;j++;temp++;}
				if(c>cmax){cmax=c;}
			}
			else{j++;}
		}
	}
	return cmax;
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
	// printf("%s\n",text);
}

