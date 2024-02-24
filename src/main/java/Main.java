import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        /*test 1*/
      /*  int[] nodeValues1= {27,37};
        int[] nodeValues2={10,15,20,25};
        int[] nodeValues3={30,35};
        int[] nodeValues4={40,45};*/
        List nodeValues1 = Arrays.asList(27,37);
        List nodeValues2 = Arrays.asList(10,15,20,25);
        List nodeValues3 = Arrays.asList(30,35);
        List nodeValues4 = Arrays.asList(40,45);

        //feuilles
        BTreeNode b2 = new BTreeNode(5, nodeValues2);
        BTreeNode b3 = new BTreeNode(5,nodeValues3);
        BTreeNode b4 = new BTreeNode(5,nodeValues4);

        List children= Arrays.asList(b2,b3,b4);
        //racien
        BTreeNode racine = new BTreeNode(5,nodeValues1,children);

        System.out.println(racine.search(27));
        System.out.println(racine.search(37));
        System.out.println(racine.search(10));
        System.out.println(racine.search(15));

        System.out.println(racine.search(30));
        System.out.println(racine.search(35));
        System.out.println(racine.search(40));
        System.out.println(racine.search(45));


        System.out.println("--------------------------------");
        System.out.println(racine.search(16).getKeys());
        System.out.println("--------------------------------");
        System.out.println(racine.search(46).getKeys());
        System.out.println("--------------------------------");
        System.out.println (racine.search(31).getKeys());
        System.out.println("--------------------------------");
        System.out.println (racine.search(77).getKeys());

        /*test 2*/

    }
}
