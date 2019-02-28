import java.util.*;
import java.io.*;
import java.io.FileNotFoundException;
import java.net.URL;


public class Main {

    private static int counter = 0;
    private static String filepath = "D:\\1. Programming\\GitHub\\asgmt2\\src\\";

    public static void main(String[] args){
        // original command line argument input
        /*Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the file you would like to read from (ie. anagrams.txt)");
        String fileIn = scan.nextLine();
        System.out.println("\nPlease enter the file you would like to write to (ie. anagramsout.txt)");
        String fileOut = scan.nextLine();*/

        // argument passed by program compilation
        String fileIn = args[0];
        String fileOut = args[1];

        // Step 1
        System.out.println("Step 1");
        String listA[] = returnWords(fileIn);
        printWords(listA);

        // Step 2
        System.out.println("Step 2");
        sortAlphabetical(listA, 0, listA.length-1);
        printWords(listA);

        // Step 3
        System.out.println("Step 3");
        SinglyLinkedList listB[] = new SinglyLinkedList[listA.length];
        for (int z = 0; z < listA.length; z++){
            listB[z] = new SinglyLinkedList();
        }
        String listACopy[] = new String[listA.length];

        for (int z = 0; z < listACopy.length; z++){
            listACopy[z] = listA[z];
        }

        int count = 0;
        for (int z = 0; z < listACopy.length; z++){
            if (!listACopy[z].equals("")){
                listB[count].addToBack(listACopy[z]);
                //System.out.println("Adding new head: " + listACopy[z]);
                String tempZ = runSortWord(listACopy[z]);
                for (int y = 0; y < listACopy.length; y++){
                    if (z != y){
                        String tempY = runSortWord(listACopy[y]);
                        //System.out.println(tempZ + ", " + tempY);
                        if (tempY.equals(tempZ)){
                            listB[count].addToBack(listACopy[y]);
                            //System.out.println("Adding new node: " + listACopy[y]);
                            listACopy[y] = "";
                        }
                    }
                }
                count++;
            }
        }
        System.out.println();

        // Step 4
        System.out.println("Step 4");
        printSinglyLinkedListArray(listB);
        for (int z = 0; z < listB.length; z++)
            listB[z].resetCurrentNode();
        printToTextFile(listB, fileOut);
    }

    /*
    * returnWords returns an array of strings referring to each word in the textfile
    *
    * @param filename the string filename to read from
    * @return String[]
     */
    private static String[] returnWords(String filename){
        Scanner in = null;
        //String filepath = "D:\\1. Programming\\GitHub\\asgmt2\\src\\";
        String currentDir = System.getProperty("user.dir");
        System.out.println(currentDir);

        URL path = Main.class.getResource(filename);
        File f = new File(path.getFile());

        // open file the first time
        try{
            in = new Scanner(new File(filename));
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
            System.exit(0);
        }

        // count lines
        int count = 0;
        while (in.hasNextLine()){
            String temp = in.nextLine();
            count++;
        }
        in.close();

        // open file second time
        try{
            in = new Scanner(new File(filename));
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
            System.exit(0);
        }

        // make array size the number of lines and populate
        String words[] = new String[count];
        for(int z = 0; in.hasNextLine(); z++){
            words[z] = in.nextLine();
        }

        return words;
    }

    /*
    * printWords prints an array of strings
    *
    * @return returns an array of strings
     */
    private static void printWords(String arr[]){
        System.out.println("PRINTING WORDS IN ARRAY:");
        for (int z = 0; z < arr.length; z++)
            System.out.println(arr[z]);
        System.out.println();
    }

    /*
    * sortAlphabetical sorts an array of strings alphabetically using quicksort
    *
    * @param arr an array of string words
    * @param left an int referring to the left most index
    * @param right an int referring to the right most index
    *
    * reference: https://stackoverflow.com/questions/25928653/how-can-i-avoid-quick-sort-stack-overflow
    * reference: https://www.geeksforgeeks.org/quick-sort/
     */
    private static void sortAlphabetical(String arr[], int left, int right){
        // for largest size of input file (ie. 15,000 words), recursive calls was causing Stack Overflow therefore changed to reduce stack size
        while (right - left >= 1){
            int part = partition(arr, left, right);
            if ((part - 1) - left <= right - (part + 1)){
                sortAlphabetical(arr, left, part - 1);
                left = part + 1;
            }
            else {
                sortAlphabetical(arr, part + 1, right);
                right = part - 1;
            }
        }
    }

    /*
    * partition is called by sortAlphabetical to sort the appropriate array objects
    *
    * @param arr an array of string words
    * @param left an int referring to the left most index
    * @param right an int referring to the right most index
    * @return returns an int to index the partitions
    *
    * reference: https://www.geeksforgeeks.org/quick-sort/
    */
    private static int partition (String arr[], int left, int right){
        String pivot = arr[right];
        int i = (left - 1);
        for (int z = left; z <= right - 1; z++) {
            boolean next = true;
            for (int y = 0; next && y < arr[z].length() && y < pivot.length(); y++){
                if (arr[z].charAt(y) < pivot.charAt(y)) {
                    i++;
                    swapWords(arr, z, i);
                    next = false;
                }
                else if (arr[z].charAt(y) > pivot.charAt(y))
                    next = false;
            }
        }
        swapWords(arr, i+1, right);
        return (i + 1);
    }

    /*
    * swapWords swaps two strings in an array
    *
    * @param arr an array of of strings
    * @param word1 an int referring to the first word to be swapped
    * @param word2 an int referring to the second word to be swapped
    *
    * reference: https://www.geeksforgeeks.org/quick-sort/
     */
    private static void swapWords(String arr[], int word1, int word2){
        String temp = arr[word1];
        arr[word1] = arr[word2];
        arr[word2] = temp;
    }

    /*
    * runSortWord runs the quicksort word sorting algorithm
    *
    * @param word a string word
    * @return returns an alphabetically sorted word
    *
    * reference: https://www.geeksforgeeks.org/quick-sort/
     */
    private static String runSortWord(String word){
        char run[] = new char[word.length()];
        for (int z = 0; z < word.length(); z++){
            run[z] = word.charAt(z);
        }
        sortAlphabeticalWord(run, 0, word.length()-1);
         return new String(run);
    }

    /*
     * sortAlphabeticalWord sorts string alphabetically using quicksort
     *
     * @param word[] an array of chars
     * @param left an int referring to the left most index
     * @param right an int referring to the right most index
     */
    private static void sortAlphabeticalWord(char word[], int left, int right){
        if (left < right){
            int part = partitionWord(word, left, right);
            sortAlphabeticalWord(word, left, part - 1);
            sortAlphabeticalWord(word, part + 1, right);
        }
    }

    /*
     * partitionWord is called by sortAlphabeticalWord to sort the appropriate array objects
     *
     * @param word an array of chars
     * @param left an int referring to the left most index
     * @param right an int referring to the right most index
     * @return returns an int to index the partition
     *
     * reference: https://www.geeksforgeeks.org/quick-sort/
     */
    private static int partitionWord (char word[], int left, int right){
        char pivot = word[right];
        int i = (left - 1);
        for (int z = left; z <= right - 1; z++) {
            if (word[z] < pivot) {
                i++;
                swapLetters(word, z, i);
            }
        }
        swapLetters(word, i+1, right);
        return (i + 1);
    }

    /*
     * swapLetters swaps two letters in an array
     *
     * @param arr an array of of strings
     * @param word1 an int referring to the first word to be swapped
     * @param word2 an int referring to the second word to be swapped
     *
     * reference: https://www.geeksforgeeks.org/quick-sort/
     */
    private static void swapLetters(char arr[], int word1, int word2){
        char temp = arr[word1];
        arr[word1] = arr[word2];
        arr[word2] = temp;
    }

    /*
    * printSinglyLinkedListArray prints an array of SinglyLinkedLists to the system output
    *
    * @param s an array of SinglyLinkedLists
    *
    * reference: https://www.geeksforgeeks.org/quick-sort/
     */
    private static void printSinglyLinkedListArray(SinglyLinkedList s[]){
        System.out.println("PRINTING ANAGRAMS:");
        for (int z = 0; z < s.length && s[z].getCurrentNode() != null; z++){
            System.out.print(s[z].getNextElement());
            while (s[z].getNextNode() != null){
                s[z].iterateNode();
                System.out.print(" " + s[z].getNextElement());
            }
            System.out.println();
        }
    }

    /*
    * printToTextFile prints an array of SinglyLinkedLists to a text file
    *
    * @param filename the string filename to write to
    * @param s an array of SinglyLinkedLists
     */
    private static void printToTextFile(SinglyLinkedList s[], String filename){
        System.out.println("WRITING ANAGRAMS:");
        //String filename = "anagramsout.txt";
        try{
            PrintWriter writer = new PrintWriter(filename);
            for (int z = 0; z < s.length && s[z].getCurrentNode() != null; z++){
                writer.print(s[z].getNextElement());
                while (s[z].getNextNode() != null){
                    s[z].iterateNode();
                    writer.print(" " + s[z].getNextElement());
                }
                writer.println();
            }
            writer.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not Found");
        }
    }
}
