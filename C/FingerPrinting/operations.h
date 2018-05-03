#include <stdio.h>
#include <string.h>
#include <math.h>
#include <dirent.h>
#include <stdlib.h>

#define base 3							//k-gram value

long sum(long list_hash_values[], int n){						//to find sum of all hash values;
	long sum=0;for(int i=0;i<n;i++){sum+=list_hash_values[i];}
	return sum;
}
long max(long list_hash_values[], int n){						//to find the maximum values among all the hash values
	long max=0;for(int i=0;i<n;i++){if(list_hash_values[i]>max){max=list_hash_values[i];}}
	return max;
}

int primeof(long x){						//to find whether a number is prime or not
	long i=2;int count=0;
	while(i<sqrt(x)+1){
		if(x%i==0){count++;break;}
		else{i++;}
	}
	return count;
}

long primefrom(long x){						//to find the 1st prime number from the maximum values in the has list
	if(primeof(x)==0){return x;}
	else{return primefrom(x+1);}
}

long hashList(long list_hash_values[],char text[]){						//to find the hash values

	char list[strlen(text)-base+1][base];
	for(int i=0;i<strlen(text)-base+1;i++){
		for(int j=i;j<i+base;j++){
			list[i][j]=text[j];
		}
	}

	int k=base;
	long hash=0;

	for(int i=0;i<strlen(text)-base+1;i++){
		hash=0;k=base;
		for(int j=i;j<i+base;j++){
			k--;
			hash+=(list[i][j])*pow(10,k);
		}list_hash_values[i]=hash;
	}
	list_hash_values[strlen(text)-base+1]='\0';

	long mod;
	if(strlen(text)-base+1>0){mod=primefrom(max(list_hash_values,strlen(text)-base+1));}
	else{mod=2;}

	return mod;

}

void fileset(char text[]){						//sets the file by removing the extra spaces and special characters

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
			if(count>=1){
				for(int j=i-count+1;j<strlen(text);j++){
					text[j]=text[j+count-1];
				}
			}count=0;
		}
	}
	count=0;
	for(int i=0;i<strlen(text);i++){if(text[i]==' '){count++;}}
	for(int i=0;i<strlen(text);i++){
		if(text[i]==' '){
			for(int j=i;j<strlen(text)-1;j++){
				text[j]=text[j+1];
			}
		}
	}
	text[strlen(text)-count]='\0';
}
