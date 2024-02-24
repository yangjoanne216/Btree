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
        /*for (int integer : intergers
             ) {
            bTree.insert(integer);
        }*/

        bTree.insert(10);
        bTree.insert(15);
        bTree.insert(30);
        bTree.insert(27);
        bTree.insert(35);
        bTree.insert(40);
        bTree.insert(45);
        bTree.insert(37);
        bTree.insert(20);
        bTree.insert(50);
        bTree.insert(55);
        bTree.insert(46);
        bTree.insert(71);
        bTree.insert(66);
        bTree.insert(74);
        bTree.insert(85);
        bTree.insert(90);
        bTree.insert(25);
        bTree.insert(81);

        System.out.println(bTree);
    }



}
