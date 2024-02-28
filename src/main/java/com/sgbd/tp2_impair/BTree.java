package com.sgbd.tp2_impair;

import java.util.*;

/**
 * Btree (m est impair)
 */
class BTree {
    //racine de BTree
    private BTreeNode root;
    // l'ordre de BTree
    private int m;
    //l'hauteur de BTree
    private int height;

    public BTree(int m){
        if(m%2==0){
            System.out.println("Nous recommandons que m soit un nombre impair. Lorsque m est un nombre pair, veuillez utiliser Btree d'un autre package.");
            System.exit(1);
        }
        if(m <3){
            System.out.println("m est au moins 3");
            System.exit(1);
        }
        this.root = null;
        this.m=m;
        this.height = 0;
    }

    /**Trouvez la clé donnée
     * @param k clé donné
     * @return nœud de feuille où l'on peut insérer des clés， si  nœud est null, c'est à dire, on a bien trouvé la clé
     */
    // 搜索键
    public BTreeNode search(int k) {
        return (root == null) ? null : root.search(k);
    }

    /**
     * inserer une clé, si on trouve pas, on insère, sinon, on fait rien
     * @param key clé donné
     */
    // 插入键
    public void insert(int key) {
        // Si la racine est nulle, il suffit de créer un nouveau noeud qui sera la racine.
        if (root == null) {
            List rootKey = new ArrayList<Integer>();
            rootKey.add(key);
            root = new BTreeNode(rootKey, m,null,new ParentEntry());
            setRoot(root);
            setHeight(1);
        } else {
            // Si la racine n'est pas nulle, cherchez à partir de la racine jusqu'à ce que vous trouviez le nodeTarget.
            BTreeNode nodeTarget=this.search(key);
            //si on trouve le key dans le tree, on fait rien, on return
            if(nodeTarget == null){
                System.out.println(key + " est déjà dans le tree!\n");
                return;
            }
            //utiliser la médhde insert dans la class BTreeNode
            BTreeNode[] nodes = nodeTarget.insert(key);
            if(nodes[0] !=null){
                setRoot(nodes[0]);
                this.height = this.height+1;
            }
        }
    }

    public BTreeNode getRoot() {
        return root;
    }

    public void setRoot(BTreeNode root) {
        this.root = root;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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
                node.getKeys().forEach(key -> System.out.print(key + " "));
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
