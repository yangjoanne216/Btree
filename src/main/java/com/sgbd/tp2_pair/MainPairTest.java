package com.sgbd.tp2_pair;

import java.util.Arrays;
import java.util.List;

public class MainPairTest {
    public static void main(String[] args) {

        System.out.println("---Attention: c'est un test pour un Btree avec m est paire---");
        BTree bTree0 = new BTree(4);
        System.out.println("this is a test 0");
        for (int i = 1; i < 8; i++) {
            System.out.println("ajoter " + i);
            bTree0.insert(i);
            bTree0.show();
            System.out.println("------------------------");
        }

        List<Integer> test1 = Arrays.asList(10, 34, 67, 67, 99, 23, 45, 46, 78, 83,100);
        BTree bTree1 = new BTree(4);
        System.out.println("this is a test 1");
        for (int i : test1) {
            System.out.println("ajoter " + i);
            bTree1.insert(i);
            bTree1.show();
            System.out.println("------------------------");
        }


        List<Integer> test2 = Arrays.asList(10, 34, 67, 67, 99, 23, 45, 46, 78, 83,100,98,43,67);
        BTree bTree2 = new BTree(6);
        System.out.println("this is a test 1");
        for (int i : test2) {
            System.out.println("ajoter " + i);
            bTree2.insert(i);
            bTree2.show();
            System.out.println("------------------------");
        }






    }

}
