package com.yangyang.tp2;

public class mainTest {
    public static void main(String[] args) {
        BTree bTree = new BTree(3);
        bTree.insert(10);
        bTree.output();
        System.out.println("------------------------");
        bTree.insert(15);
        bTree.output();
        System.out.println("------------------------");
        bTree.insert(30);
        bTree.output();
        System.out.println("------------------------");
        bTree.insert(27);
        bTree.output();
        System.out.println("------------------------");
        bTree.insert(35);
        bTree.output();
        System.out.println("------------------------");
        bTree.insert(40);
        bTree.output();
        System.out.println("------------------------");
        bTree.insert(45);
        bTree.output();
        System.out.println("------------------------");
        bTree.insert(37);
        bTree.output();
        System.out.println("------------------------");
        bTree.insert(20);
        bTree.output();
        System.out.println("------------------------");
        bTree.insert(50);
        bTree.output();
        System.out.println("------------------------");
        bTree.insert(55);
        bTree.output();
        System.out.println("------------------------");
        bTree.insert(46);
        bTree.output();
        System.out.println("------------------------");
        bTree.insert(71);
        bTree.output();
        System.out.println("------------------------");
        bTree.insert(66);
        bTree.output();
        System.out.println("------------------------");
        bTree.insert(74);
        bTree.output();
        System.out.println("------------------------");
        bTree.insert(85);
        bTree.output();
        System.out.println("------------------------");
        bTree.insert(90);
        bTree.output();
        System.out.println("------------------------");
        bTree.insert(25);
        bTree.output();
        System.out.println("------------------------");
        bTree.insert(81);
        bTree.output();
        System.out.println("------------------------");
        bTree.delete(90);
        bTree.output();
    }

}
