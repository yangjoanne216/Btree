package com.sgbd.tp2_paire;

public class MainPaireTest {

    public static void main(String[] args) {

        BTree bTree = new BTree(4);
        bTree.insert(1);
        bTree.show();
        bTree.insert(2);
        bTree.show();
        bTree.insert(3);
        bTree.show();
        bTree.insert(4);
        bTree.show();
        bTree.insert(5);
        bTree.show();
        bTree.insert(6);
        bTree.show();
        bTree.insert(7);
        bTree.show();
        bTree.insert(3);
        bTree.show();

        bTree.insert(0);
        bTree.show();

        for (int i =8;i<8;i++){
            System.out.println("ajouter" + i);
            bTree.insert(i);
            bTree.show();
        }

        /*test2*/
        /*java.com.yangyang.tp2_paire.BTree bTree = new java.com.yangyang.tp2_paire.BTree(5);
        int[] intergers = new int[]{10, 15, 30, 27, 35, 40, 45, 37, 20, 50, 55, 46, 71, 66, 74, 85, 90, 25, 81, 68, 60, 65};
        /*for (int integer : intergers
             ) {
            bTree.insert(integer);
        }*/

        /*bTree.insert(10);
        bTree.show();
        bTree.insert(15);
        bTree.show();
        bTree.insert(30);
        bTree.show();
        bTree.insert(27);
        bTree.show();
        bTree.insert(35);
        bTree.show();
        bTree.insert(40);
        bTree.show();
        bTree.insert(45);
        bTree.show();
        bTree.insert(37);
        bTree.show();
        bTree.insert(20);
        bTree.show();
        bTree.insert(50);
        bTree.show();
        bTree.insert(55);
        bTree.show();
        bTree.insert(46);
        bTree.show();
        bTree.insert(71);
        bTree.show();
        bTree.insert(66);
        bTree.show();
        bTree.insert(74);
        bTree.show();
        bTree.insert(85);
        bTree.show();
        bTree.insert(90);
        bTree.show();
        bTree.insert(79);
        bTree.show();
        bTree.insert(78);
        bTree.show();
        bTree.insert( 95);
        bTree.show();
        bTree.insert(25);
        bTree.show();
        bTree.insert(81);
        bTree.show();
        bTree.insert(68);
        bTree.show();
        bTree.insert(60);
        bTree.show();
        bTree.insert(65);
        bTree.show();
        /*--------------------*/
    }

}
