package com.sgbd.tp2_impaire;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainImpaireTest {

    public static void main(String[] args) {
        /*test 0: le même exercice de cours
        List<Integer> test0 = Arrays.asList(1,2,3,4,5);
        BTree bTree0 = new BTree(3);
        System.out.println("this is a test 0");
        for (int i:test0) {
            System.out.print("ajoter " + i);
            bTree0.insert(i);
            bTree0.show();
            System.out.println("------------------------");
        }*/


        /*test 2 : le même exercice de cours
        List<Integer> test2 = Arrays.asList(50, 55, 66, 68, 70, 71, 72, 73, 79, 81, 85, 90, 95);
        BTree bTree2 = new BTree(5);
        System.out.println("this is a test 2");
        for (int i:test2) {
            System.out.print("ajoter " + i);
            bTree2.insert(i);
            bTree2.show();
            System.out.println("------------------------");
        }*/


        /*test 1 :le même exercice de cours*/
        //List<Integer> test1 = Arrays.asList(10, 15, 30, 27, 35, 40, 45, 37, 20, 50, 55, 46, 71, 66, 74, 85, 90, 79, 78, 95, 25, 81, 68, 60);
        List<Integer> test1 = Arrays.asList(10, 15, 30, 27, 35, 40, 45, 37, 20, 50, 55);
        BTree bTree1 = new BTree(5);
        System.out.println("this is a test 1");
        for (int i:test1) {
            System.out.print("ajoter " + i);
            bTree1.insert(i);
            bTree1.show();
            System.out.println("------------------------");
        }

        System.out.print("ajoter 46");
        bTree1.insert(46);
        bTree1.show();
        System.out.println("------------------------");


        System.out.print("ajoter 49");
        bTree1.insert(49);
        bTree1.show();
        System.out.println("------------------------");

        System.out.print("ajoter 56");
        bTree1.insert(56);
        bTree1.show();
        System.out.println("------------------------");

        System.out.print("ajoter 77");
        bTree1.insert(77);
        bTree1.show();
        System.out.println("------------------------");

        System.out.print("ajoter 38");
        bTree1.insert(38);
        bTree1.show();
        System.out.println("------------------------");

        System.out.print("ajoter 39");
        bTree1.insert(39);
        bTree1.show();
        System.out.println("------------------------");

        System.out.print("ajoter 39");
        bTree1.insert(42);
        bTree1.show();
        System.out.println("------------------------");




    }

}
