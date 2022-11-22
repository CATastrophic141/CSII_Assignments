package Main;

import static java.lang.Integer.*;

class MaxSubArray{
    protected int[] findMaxSubArrayDivideConquer(int[] array, int lowIndex, int highIndex){

        if(array.length == 2) return findMaxSubArrayBruteForce(array);

        int midIndex;
        if (lowIndex==highIndex) return new int[] {lowIndex, highIndex, array[lowIndex]};
        else midIndex = (lowIndex+highIndex)/2;
        int[] leftArr = findMaxSubArrayDivideConquer(array, lowIndex, midIndex);
        int[] rightArr = findMaxSubArrayDivideConquer(array, midIndex+1, highIndex);
        int[] crossArr = findMaxCrossingSubArr(array, lowIndex, midIndex, highIndex);
        if (leftArr[2] >= rightArr[2] && leftArr[2] >= crossArr[2]){
            return new int[] {leftArr[0], leftArr[1], leftArr[2]};
        }
        else if (rightArr[2] >= leftArr[2] && rightArr[2] >= crossArr[2]){
            return new int[] {rightArr[0], rightArr[1], rightArr[2]};
        }
        else return new int[] {crossArr[0], crossArr[1], crossArr[2]};
    }

    private int[] findMaxCrossingSubArr(int[] array, int lowIndex, int midIndex, int highIndex){
        int leftSum = MIN_VALUE;
        int sum = 0;
        int maxLeftIndex = 0;
        for (int i = midIndex; i >= lowIndex; i--) {
            sum = sum + array[i];
            if (sum > leftSum) {
                leftSum = sum;
                maxLeftIndex = i;
            }
        }
        int rightSum = MIN_VALUE;
        sum = 0;
        int maxRightIndex = 0;
        for (int j = midIndex+1; j <= highIndex; j++){
            sum = sum + array[j];
            if (sum > rightSum){
                rightSum = sum;
                maxRightIndex = j;
            }
        }
        return new int[] {maxLeftIndex, maxRightIndex, leftSum+rightSum};
    }

    protected int[] findMaxSubArrayBruteForce(int[] array) {
        int arrayLen = array.length;
        int maxSum = MIN_VALUE;
        int currentSum;
        int rightIndex = 0;
        int leftIndex = 0;

        for (int i = 0; i < arrayLen; i++) {
            currentSum = 0;
            for (int j = i; j < arrayLen; j++) {
                currentSum = currentSum + array[j];
                if (currentSum > maxSum) {
                    maxSum = currentSum;
                    leftIndex = i;
                    rightIndex = j;
                }

            }
        }
        return new int[]{leftIndex,rightIndex};
    }

}

public class CSII_HW2_MaxSubArray {

    public static void main(String[] args){
        MaxSubArray maxSubArr = new MaxSubArray();

        int[] array = new int[]{-1, -5, 1, 3, 4, -5, -7, -7, 5};

        int[] bruteForceResult = maxSubArr.findMaxSubArrayBruteForce(array);

        int[] divideConquerResult = maxSubArr.findMaxSubArrayDivideConquer(array, 0, array.length-1);

        System.out.printf("The maximum subarray found by the brute force method is is array[%d,%d]%n", bruteForceResult[0], bruteForceResult[1]);
        System.out.printf("The maximum subarray found by the divide and conquer method is is array[%d,%d]", divideConquerResult[0], divideConquerResult[1]);
    }
}