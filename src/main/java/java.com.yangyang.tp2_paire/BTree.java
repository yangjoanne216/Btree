package java.com.yangyang.tp2_paire;

import java.util.*;

class BTree {
    private BTreeNode root;
    private int m;

    private int height;

    public BTree(int m){
        BTree root = null;
        this.m=m;
        this.height = 0;
    }

    // 搜索键
    public BTreeNode search(int k) {
        return (root == null) ? null : root.search(k);
    }

    // 插入键
    public void insert(int key) {
        //如果root是null的话，就新建一个点成为 root就好
        if (root == null) {
            List rootKey = new ArrayList<Integer>();
            rootKey.add(key);
            root = new BTreeNode(rootKey, m,null,new ParentEntry());
            setRoot(root);
            setHeight(1);
        } else {
            //如果root不是null的话，就从root开始search直到找到那个nodeTarget
            BTreeNode nodeTarget=this.search(key);
            //si on trouve le key dans le tree, on fait rien, on return
            if(nodeTarget == null){
                System.out.println(key + "est déjà dans le tree!\n");
                return;
            }
            //让节点去调用insert方法
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
                node.getKeys().forEach(key -> System.out.print(key + " "));
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
