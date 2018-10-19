import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by Abbas Hadizadeh on 10/19/2018.
 */
public class MultiPatternMatching {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        KeywordTree keywordTree = new KeywordTree();
        String input;
        System.out.println("Add any number of Patterns. Type 0 to finish adding, or -1 to restart.");
        while(true){
            input = scanner.nextLine();
            if(input.equals("0"))
                break;
            if(input.equals("-1")){
                keywordTree = new KeywordTree();
                continue;
            }
            keywordTree.addPattern(input);
        }
        keywordTree.constructKeywordTree();
        TreeMap<Pair, String> matches = new TreeMap();
        while(true){
            System.out.println("Type the text you want to search for current patterns. Type 1 to see the Keyword Tree, 0 to see the patterns, or -1 to terminate.");
            input = scanner.nextLine();
            if(input.equals("-1"))
                break;
            if(input.equals("0")){
                System.out.println(keywordTree.patterns);
                continue;
            }
            if(input.equals("1")) {
                System.out.println(keywordTree);
                continue;
            }
            matches = keywordTree.searchText(input);
            System.out.println(matches);
        }
    }
}