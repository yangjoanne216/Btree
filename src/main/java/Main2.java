import java.util.ArrayList;
import java.util.Arrays;

public class Main2 {

    public static void main(String[] args) {
        /*test1
        BTree bTree = new BTree(3);
        bTree.insert(1);
        bTree.insert(2);
        bTree.insert(3);
        bTree.insert(4);
        bTree.insert(5);
        System.out.println(bTree);
        */

        /*test2*/
        BTree bTree = new BTree(5);
        int[] intergers = new int[]{10,15,30,27,35,40,45,37,20,50,55,46,71,66,74,85,90,25,81,68,60,65};
        for (int integer : intergers
             ) {
            bTree.insert(integer);
        }

        System.out.println(bTree);
    }



}
