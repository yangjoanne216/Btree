public class Main {
    public static void main(String[] args) {
        /*test 1*/
        int[] nodeValues1= {27,37};
        int[] nodeValues2={10,15,20,25};
        int[] nodeValues3={30,35};
        int[] nodeValues4={40,45};

        //feuilles
        BTreeNode b2 = new BTreeNode(5,nodeValues2);
        BTreeNode b3 = new BTreeNode(5,nodeValues3);
        BTreeNode b4 = new BTreeNode(5,nodeValues4);
        //racien
        BTreeNode[] children ={ b2,b3,b4};
        BTreeNode racine = new BTreeNode(5,nodeValues1,children);

        System.out.println(racine.search2(27));
        System.out.println(racine.search2(37));
        System.out.println(racine.search2(10));
        System.out.println(racine.search2(15));

        System.out.println(racine.search2(30));
        System.out.println(racine.search2(35));
        System.out.println(racine.search2(40));
        System.out.println(racine.search2(45));


        System.out.println("--------------------------------");
        System.out.println(racine.search2(16).getKeys());
        System.out.println("--------------------------------");
        System.out.println(racine.search2(46).getKeys());
        System.out.println("--------------------------------");
        System.out.println (racine.search2(31).getKeys());
        System.out.println("--------------------------------");
        System.out.println (racine.search2(77).getKeys());

        /*test 2*/

    }
}
