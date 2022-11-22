package PalindromeSubseq;

import static java.lang.Math.max;

//Result storage object for LPS-specific functions
class LPSdetails {
    int[][] lengthArray;
    int[][] enumeratedPath;
    int palindromeLength;
}
//Result storage object for LCS-specific functions
class LCSdetails {
    int[][] lengthArray;
    int[][] enumPath;
    int subsequenceLength;
}

class Substring {
    public LPSdetails longestPalindrome(char[] sequence, int length) {
        LPSdetails results = new LPSdetails();
        results.lengthArray = new int[length][length];  //Table to store lengths
        results.enumeratedPath = new int[length][length]; //Table of way to find pal
        results.palindromeLength = 0;  //Initialized ax palindrome length
        if (length == 0) return results;
        for (int k = 0; k<length; k++) {
            results.lengthArray[k][k] = 1; //Inits substrings of 1 char to size 1
        }
        for(int s = 2; s <= length; s++) { //Length of substring
            for (int i = 0; i < length-s+1; i++) { //length-s+1 is subseq r bound
                int j = i+s-1;
                if (sequence[i] == sequence[j] && s==2) {
                    results.lengthArray[i][j] = 2;
                    results.enumeratedPath[i][j] = 0;
                }
            else if (sequence[i] == sequence[j]) {
                    results.lengthArray[i][j] = results.lengthArray[i+1][j-1] + 2;
                    results.enumeratedPath[i][j] = 0;
                }
            else {
                    results.lengthArray[i][j] = max(results.lengthArray[i][j - 1], results.lengthArray[i + 1][j]);
                    if (results.lengthArray[i][j] == results.lengthArray[i][j - 1]) results.enumeratedPath[i][j] = 1;
                    else results.enumeratedPath[i][j] = 2;
                }
            if (results.lengthArray[i][j]>results.palindromeLength) results.palindromeLength = results.lengthArray[i][j];
            }
        }
        return results;
    }

    public void generateLPS(char[] sequence, int[][] path, int row, int column, int palindromeLen){
        //Function should begin at row = 0 and column = sequence length -1
        if (row < 0 || column < 0 || row > palindromeLen) return;
        if (path[row][column] == 0) {
            System.out.printf("%s", sequence[row]);
            generateLPS(sequence, path, row + 1, column - 1, palindromeLen);
        }
	    else if (path[row][column] == 1) generateLPS(sequence, path, row, column-1, palindromeLen);
        else if (path[row][column] == 2) generateLPS(sequence, path, row+1, column, palindromeLen);
    }

    LCSdetails leastCommonSubsequence(char[] string1, char[] string2, int size1, int size2) {
        int[][] lengthTable = new int[size1+1][size2+1];
        int[][] path = new int[size1][size2];
        int seqLen = 0;
        for (int i = 0; i <= size1; i++) {
            for (int j = 0; j <= size2; j++) {
                if (i == 0 || j == 0) {
                    lengthTable[i][j] = 0;
                }
                else if (string1[i - 1] == string2[j - 1]) {
                        lengthTable[i][j] = lengthTable[i - 1][j - 1] + 1;
                        seqLen++;
                        path[i-1][j-1] = 0;
                }
                else if (lengthTable[i-1][j] > lengthTable[i][j-1]){
                    lengthTable[i][j] = lengthTable[i-1][j];
                    path[i-1][j-1] = 1; //"Up"
                }
                else {
                    lengthTable[i][j] = lengthTable[i][j-1];
                    path[i-1][j-1] = 2; //"Left"
                }
            }
        }
        LCSdetails results = new LCSdetails();
        results.lengthArray = lengthTable;
        results.subsequenceLength = seqLen;
        results.enumPath = path;
        return results;
    }

    void printPalindromeFromLCS(char[] seq, char[] revSeq, int[][] lenArr, int size1, int size2){
        int index = lenArr[size1][size2];
        char[] subSeq = new char[index+1];
        subSeq[index] = '\0';
        int i = size1, j = size2;
        while (i > 0 && j > 0) {
            if (seq[i-1] == revSeq[j - 1]) {
                subSeq[index-1] = seq[i-1];
                i--;
                j--;
                index--;
            }
            else if (lenArr[i-1][j] > lenArr[i][j - 1])
                i--;
            else
                j--;
        }
        System.out.printf("The palindrome length is:%n%d%n", lenArr[size1][size2]);
        System.out.printf("The palindrome is:%n");
        for (int a = 0; a<lenArr[size1][size2]; a++) System.out.print(subSeq[a]);
    }
}

class mainProg{
    public static void main(String[] args){
        System.out.printf("This program finds the longest palindromic subsequence of a string, only counting letter-based characters%n");

        Substring substring = new Substring();
        LCSdetails results = new LCSdetails();

        String seq = "Are we not pure? “No, sir!” Panama’s moody Noriega brags. “It is garbage!” Irony dooms a man—a prisoner up to new era.";

        double startTime = System.nanoTime();

        seq = seq.replaceAll("[^A-Za-z]+", "").toUpperCase();

        StringBuilder seqReverser = new StringBuilder(seq).reverse();
        String revSeq = seqReverser.toString();
        int seqLen = seq.length();

        results = substring.leastCommonSubsequence(seq.toCharArray(), revSeq.toCharArray(), seqLen, seqLen);
        substring.printPalindromeFromLCS(seq.toCharArray(), revSeq.toCharArray(), results.lengthArray, seqLen, seqLen);

        double endTime = System.nanoTime();

        double totalTime = endTime-startTime;

        System.out.printf("%nThe program execution time in nanoseconds is: %f", totalTime);
    }
}

//System.out.printf("The length of the largest palindrome subsequence is: %d%n", results.lengthArray[seqLen][seqLen]);
        /*LPSdetails results = lps.longestPalindrome(seq.toCharArray(), seqLen);
        System.out.printf("The length of the largest palindrome subsequence is: %d%n", results.palindromeLength);
        lps.generateLPS(seq.toCharArray(), results.enumeratedPath, 0, seqLen-1, results.palindromeLength); */