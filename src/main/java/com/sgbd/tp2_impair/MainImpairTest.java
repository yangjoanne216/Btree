package com.sgbd.tp2_impair;


import java.util.Arrays;
import java.util.List;

public class MainImpairTest {

    public static void main(String[] args) {
        System.out.println("Attention: c'est un test pour un Btree avec m est impaire");
        /*test 0: le même exercice de cours*/
        List<Integer> test0 = Arrays.asList(1,2,3,4,5);
        BTree bTree0 = new BTree(3);
        System.out.println("this is a test 0");
        for (int i:test0) {
            System.out.print("ajoter " + i);
            bTree0.insert(i);
            bTree0.show();
            System.out.println("------------------------");
        }

        /*test 1 :le même exercice de cours*/
        List<Integer> test1 = Arrays.asList(10, 15, 30, 27, 35, 40, 45, 37, 20, 50, 55, 46, 71, 66, 74, 85, 90, 79, 78, 95, 25, 81, 68, 60,65);
        BTree bTree1 = new BTree(5);
        System.out.println("this is a test 1");
        for (int i:test1) {
            System.out.print("ajoter " + i);
            bTree1.insert(i);
            bTree1.show();
            System.out.println("------------------------");
        }


        /*test 2 : le même exercice de cours*/
        List<Integer> test2 = Arrays.asList(50, 55, 66, 68, 70, 71, 72, 73, 79, 81, 85, 90, 95);
        BTree bTree2 = new BTree(5);
        System.out.println("this is a test 2");
        for (int i:test2) {
            System.out.print("ajoter " + i);
            bTree2.insert(i);
            bTree2.show();
            System.out.println("------------------------");
        }

        BTree bTree3 = new BTree(1);
        bTree3.show();


    }

}
