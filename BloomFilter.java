
import java.util.Objects;

public class BloomFilter {

    private int[] table;

    //getter method
    public int[] getTable() {
        return table;
    }

    /*
    Creates new BloomFilter (constructor)
    Input: size
    Output: none
    */
    public BloomFilter(int size){
        if(size <= 0){
            throw new IllegalArgumentException("size is less than 0");
        }

        //create new table
        table = new int[size];

    }

    /*
    Adds string to BloomFilter, increments frequency if it is already added
    Input: string
    Output: none
    */
    public boolean increment(String str){
        if(str == null){
            return false;
        }

        //use first hash code and increment
        int first = firstIndex(str);
        table[first] += 1;

        //use second hash code and increment
        int second = secondIndex(str);
        table[second] += 1;

        //use third hash code and increment
        int third = thirdIndex(str);
        table[third] += 1;

        return true;
    }

    /*
    Reverses string in order to keep complexity of other code low
    Input: string
    Output: string in reverse
    */
    private String reversedString(String str){
        StringBuilder reverseStr = new StringBuilder();
        reverseStr.append(str);
        reverseStr = reverseStr.reverse();
        return reverseStr.toString();
    }

    /*
    First method of hashing string
    Input: string
    Output: index in table
    */
    private int firstIndex(String str){
        return Math.abs(str.hashCode()) % table.length;
    }

    /*
    Second method of hashing string
    Input: string
    Output: index in table
    */
    private int secondIndex(String str){

        //reverse string
        String temp = reversedString(str);

        return Math.abs(temp.hashCode()) % table.length;
    }

    /*
    Third method of hashing string
    Input: string
    Output: index in table
    */
    private int thirdIndex(String str){
        StringBuilder temp = new StringBuilder();
        String normal = str;
        int index = 0;
        //reverse string
        String reverse = reversedString(str);

        //create combination of two strings using ith letter of string and ith letter of reversed string
        for(int i = 0; i < normal.length(); i++){
            if(i % 2 == 0) {
                temp.append(normal.charAt(index));
            } else{
                temp.append(reverse.charAt(index));
            }
            index++;
        }
        return Math.abs(temp.toString().hashCode())% table.length;
    }

    /*
    Returns rough number of times string has been registered
    Input: string
    Output: estimate of number of hits
    */
    public int count(String str){
        Objects.requireNonNull(str);

        //assign indices
        int first = table[firstIndex(str)];
        int second = table[secondIndex(str)];
        int third = table[thirdIndex(str)];

        //if any element is 0 return 0
        if(isAnyZero(first, second, third)){
            return 0;
        }

        //return average
        int average = ((first + second + third)/3);
        return average;
    }

    /*
    Helper method to see if any of the 3 indices are 0
    Input: 3 numbers
    Output: boolean
     */
    private boolean isAnyZero(int first, int second, int third){
        return first == 0 || second == 0 || third == 0;
    }

    /*
    Clears table, fills every element with 0
    Input: none
    Output: none
    */
    public void clear(){

        //fil   l every element with 0
        for(int element: table){
            table[element] = 0;
        }
    }

    class BloomFilterHook{
        String reversedString(String str){
            String reversedString_test = BloomFilter.this.reversedString(str);
            return reversedString_test;
        }

        int firstIndex(int[] table, String str){
            BloomFilter.this.table = table;
            int firstIndex_test = BloomFilter.this.firstIndex(str);
            return firstIndex_test;
        }

        int secondIndex(int[] table, String str){
            BloomFilter.this.table = table;
            int secondIndex_test = BloomFilter.this.secondIndex(str);
            return secondIndex_test;
        }

        int thirdIndex(int[] table, String str){
            BloomFilter.this.table = table;
            int thirdIndex_test = BloomFilter.this.thirdIndex(str);
            return thirdIndex_test;
        }

        boolean isAnyZero(int first, int second, int third){
            boolean isAnyZero_test = BloomFilter.this.isAnyZero(first, second, third);
            return isAnyZero_test;
        }

    }



}
