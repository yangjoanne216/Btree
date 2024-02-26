package com.sgbd.tp2_paire;

import java.util.LinkedList;
import java.util.Queue;

public class BTree {
    private static final int DEFAULT_T = 2;

    private BTreeNode root;
    /**
     * Selon la définition de l'arbre B, le nombre de mots-clés n de chaque nœud non racine de l'arbre B est égal à (t - 1) <= n <= (2t - 1).
     */
    /*m必须为奇数*/
    private int m;
    private int minKeySize = m/2-1;
    private int maxKeySize=m-1;
    /*private int t = DEFAULT_T;
    private int minKeySize = t - 1;
    private int maxKeySize = 2 * t - 1;*/

    public BTree() {
        root = new BTreeNode();
        root.setLeaf(true);
    }

    public BTree(int m) {
        this();
        this.m = m;
        this.minKeySize = m%2==0?m/2-1:m/2;
        this.maxKeySize=m-1;
    }

    /**
     *
     *Recherche de la <code>clé</code> donnée
     *Chaque recherche commence au nœud racine
     * @param key - clé
     * @return  -1 n'est pas trouvé, les autre valeur c'est l'index de clé
     */
    public int search(Integer key) {
        return search(root, key);
    }

    /**
     * Recherche récursive d'un nœud donné
     *
     * @param node - un nœud donné
     * @param key  - une clé donné
     * @return TODO -1 n'est pas trouvé, les autre valeur c'est l'index de clé
     */
    private static int search(BTreeNode node, Integer key) {
        ResearchOutcome result = node.searchKey(key);
        if (result.getResult()) //bien trouvé
            return result.getIndex(); //  Renvoie index de clé
        else {
            if (node.isLeaf())
                return -1;   // si on n'est dans un feuille, pas d'enfant, renvoie -1
            else
                search(node.getChildren().get(result.getIndex()), key); //sinon, on le trouve dans l'enfant correspondant

        }
        return -1;
    }

    /**
     *
     * Diviser un nœud complet
     *
     * @param parentNode - parent
     * @param childNode  - un nœud complet
     * @param index      - L'index du nœud enfant complet dans le nœud parent
     */
    private void splitNode(BTreeNode parentNode, BTreeNode childNode, int index) {
        BTreeNode siblingNode = new BTreeNode();
        siblingNode.setLeaf(childNode.isLeaf()); //Si le nœud d'origine est un nœud feuille, le nœud scindé est également un nœud feuille.
        //middle = t-1
        int middle =(m+1)/2 -1;
        /*// 将满子节点中索引为[t, 2t - 2]的(t - 1)个关键字插入新的节点中
        for (int i = 0; i < minKeySize; ++i)
            siblingNode.getKey().add(childNode.getKey().get(t + i));*/
        // 将满子节点中索引为[(m+1)/2, m - 2]的(t - 1)个关键字插入新的节点中
        for (int i = (m+1)/2; i < m - 1; ++i)
            siblingNode.getKey().add(childNode.getKey().get(i));
        /*// 提取满子节点中的中间关键字，其索引为(t - 1)
        Integer key = childNode.getKey().get(t - 1);*/
        // 提取满子节点中的中间关键字，其索引为(m+1)/2 -1
        Integer key = childNode.getKey().get(middle);
        /*// 删除满子节点中索引为[t - 1, 2t - 2]的t个关键字
        for (int i = maxKeySize - 1; i >= t - 1; --i)
            childNode.getKey().remove(i);*/
        // 删除满子节点中索引为[(m+1)/2 -1, m-2]的t个关键字
        for (int i = maxKeySize - 1; i >= middle; --i)
            childNode.getKey().remove(i);
        // 如果满子节点不是叶节点，则还需要处理其子节点
        if (!childNode.isLeaf())
        {
           /* // 将满子节点中索引为[t, 2t - 1]的t个子节点插入新的节点中
            for (int i = 0; i < minKeySize + 1; ++i)
                siblingNode.getChildren().add(childNode.getChildren().get(t + i));*/
            //将满子节点中索引为[(m+1)/2, maxKeysize]的t个子节点插入新的节点中
            for (int i = (m+1)/2; i <= maxKeySize ; ++i)
                siblingNode.getChildren().add(childNode.getChildren().get(i));
            /*// 删除满子节点中索引为[t, 2t - 1]的t个子节点
            for (int i = maxKeySize; i >= t; --i)
                childNode.getChildren().remove(i);*/
            // 删除满子节点中索引为[(m+1)/2 , m-2]的t个子节点
            for (int i = maxKeySize; i >= (m+1)/2; --i)
                childNode.getChildren().remove(i);
        }
        // 将key插入父节点
        parentNode.insertKey(key, index);
        // 将新节点插入父节点
        parentNode.insertChild(siblingNode, index + 1);
    }

    /**
     * Insère la clé donnée dans un nœud non complet
     *
     * @param node -  un nœud non complet
     * @param key  -  clé
     */
    private void insertNotFull(BTreeNode node, Integer key) {
        //assert node.size() < maxKeySize;

        if (node.isLeaf())  // S'il s'agit d'un nœud feuille, l'insérer directement
            node.insertKey(key);
        else {
            /* 找到key在给定节点应该插入的位置，那么key应该插入
             * 该位置对应的子树中
             */
            ResearchOutcome result = node.searchKey(key);
            BTreeNode childNode = node.getChildren().get(result.getIndex());
            if (childNode.size() == maxKeySize) // 如果子节点是满节点
            {
                // 则先分裂
                splitNode(node, childNode, result.getIndex());
                /* 如果给定的key大于分裂之后新生成的键值，则需要插入该新键值的右边，
                 * 否则左边。
                 */
                if (key > node.getKey().get(result.getIndex()))
                    childNode = node.getChildren().get(result.getIndex() + 1);
            }
            //如果子节点不是满节点
            insertNotFull(childNode, key);
        }
    }

    /**
     * 在B树中插入给定的<code>key</code>。
     *
     * @param key - 给定的键值
     */
    public void insert(Integer key) {
        //主要作用是判断是否要升级racine
        if (root.size() == maxKeySize) // Si le nœud racine est plein, l'arbre B est augmenté d'un niveau.
        {
            BTreeNode newRoot = new BTreeNode();
            newRoot.setLeaf(false);
            newRoot.getChildren().add(root);
            splitNode(newRoot, root, 0);
            root = newRoot;
        }
        //从root开始尝试插入
        insertNotFull(root, key);
    }

    // 使用队列按层打印B树
    public void show() {
        System.out.println();
        Queue<BTreeNode> queue = new LinkedList<>();
        queue.add(root); // 将根节点加入队列

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // 当前层的节点数
            for (int i = 0; i < levelSize; i++) {
                BTreeNode node = queue.poll(); // 从队列中取出一个节点
                // 打印当前节点的所有键
                node.getKey().forEach(key -> System.out.print(key + " "));
                System.out.print(" | "); // 同一层的节点之间用"|"分隔

                // 如果节点不是叶子节点，将其子节点加入队列
                if (!node.isLeaf()) {
                    node.getChildren().forEach(child -> queue.add(child));
                }
            }
            System.out.println(); // 完成一层的打印后换行，开始打印下一层
        }
    }



}

