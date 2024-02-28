package com.sgbd.tp2_pair;

import java.util.LinkedList;
import java.util.Queue;

public class BTree {

    private BTreeNode root;

    /*m必须为奇数*/
    private int m;
    private int minKeySize = m/2-1;
    private int maxKeySize=m-1;

    public BTree() {
        root = new BTreeNode();
        root.setLeaf(true);
    }

    public BTree(int m) {
        this();
        if(m%2==1){
            System.out.println("Nous recommandons que m soit un nombre pair. Lorsque m est un nombre impair, veuillez utiliser Btree d'un autre package.");
            System.exit(1);
        }
        if(m <3){
            System.out.println("m est au moins 3");
            System.exit(1);
        }
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
     * @return l'index ou on insere l
     */
    private static int search(BTreeNode node, Integer key) {
        ResearchOutcome result = node.searchKey(key);
        if (result.getResult()) //bien trouvé
        {
            return result.getIndex(); //  Renvoie index de clé
        }
        else {
            if (node.isLeaf())
                return -1;   // si on n'est dans un feuille, il y pas d'enfant, renvoie -1
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
        //  Insérer le mot-clé avec l'index [(m+1)/2, m - 2] dans le nœud enfant complet dans le nouveau nœud.
        for (int i = (m+1)/2; i < m - 1; ++i)
            siblingNode.getKey().add(childNode.getKey().get(i));

        // // Extraire le mot-clé du milieu dans le nœud enfant complet avec l'index (m+1)/2 -1
        Integer key = childNode.getKey().get(middle);

        // Supprimer les mots-clés avec l'index [(m+1)/2 -1, m-2] dans le nœud enfant complet
        for (int i = maxKeySize - 1; i >= middle; --i)
            childNode.getKey().remove(i);
        // // Si le nœud enfant complet n'est pas un nœud feuille, ses enfants doivent également être traités.
        if (!childNode.isLeaf())
        {
            //Insérer les enfants du nœud enfant complet avec l'index [(m+1)/2, maxKeysize] dans le nouveau nœud.
            for (int i = (m+1)/2; i <= maxKeySize ; ++i)
                siblingNode.getChildren().add(childNode.getChildren().get(i));
            // Supprimer les t enfants du nœud enfant complet avec l'index [(m+1)/2 , m-2].
            for (int i = maxKeySize; i >= (m+1)/2; --i)
                childNode.getChildren().remove(i);
        }
        // Insérer la clé dans le nœud parent
        parentNode.insertKey(key, index);
        // Insérer le nouveau nœud dans le nœud parent
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
            /* Trouver la position où la clé doit être insérée au nœud donné, puis la clé doit être insérée dans le sous-arbre correspondant à cette position.
  dans le sous-arbre correspondant à cette position
             */
            ResearchOutcome result = node.searchKey(key);
            BTreeNode childNode = node.getChildren().get(result.getIndex());
            if (childNode.size() == maxKeySize)
            {
                splitNode(node, childNode, result.getIndex());

                if (key > node.getKey().get(result.getIndex()))
                    childNode = node.getChildren().get(result.getIndex() + 1);
            }
           //Si le nœud enfant n'est pas plein
            insertNotFull(childNode, key);
        }
    }

    /**
     * 在B树中插入给定的<code>key</code>。
     *
     * @param key - 给定的键值
     */
    public void insert(Integer key) {
        //La fonction principale est de déterminer si la racine doit être améliorée ou non.
        if (root.size() == maxKeySize) // Si le nœud racine est plein, l'arbre B est augmenté d'un niveau.
        {
            BTreeNode newRoot = new BTreeNode();
            newRoot.setLeaf(false);
            newRoot.getChildren().add(root);
            splitNode(newRoot, root, 0);
            root = newRoot;
        }
        insertNotFull(root, key);
    }

    /**
     * Impression d'arbres B par couche à l'aide d'une file d'attente
     */
    public void show() {
        System.out.println();
        Queue<BTreeNode> queue = new LinkedList<>();
        queue.add(root); // Ajouter le nœud racine à la file d'attente

        while (!queue.isEmpty()) {
            int levelSize = queue.size();// Nombre de nœuds dans la couche courante
            for (int i = 0; i < levelSize; i++) {
                BTreeNode node = queue.poll(); // Retirer un nœud de la file d'attente
                // 打印当前节点的所有键
                node.getKey().forEach(key -> System.out.print(key + " "));
                System.out.print(" | ");

                // Si le nœud n'est pas un nœud feuille, ajouter ses enfants à la file d'attente
                if (!node.isLeaf()) {
                    node.getChildren().forEach(child -> queue.add(child));
                }
            }
            System.out.println(); // // Lorsque vous avez terminé l'impression d'une couche, changez de ligne et commencez l'impression de la couche suivante.
        }
    }



}

