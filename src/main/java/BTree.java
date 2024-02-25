
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

    // BTreeNode类的辅助方法
    private void splitChild(int i, BTreeNode y) {
        // 此方法和insertNonFull需要在BTreeNode类中实现
    }

    // BTreeNode类的辅助方法
    private void insertNonFull(int key) {
        // 此方法需要在BTreeNode类中实现
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

    public void show() {
        if (root == null) {
            System.out.println("The B-tree is empty");
            return;
        }
        Queue<BTreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // 当前层的节点数
            for (int i = 0; i < levelSize; i++) {
               BTreeNode node = queue.poll();
                System.out.print(node.keys + " ");
                queue.addAll(node.children); // 将当前节点的所有子节点加入队列
            }
            System.out.println(); // 当前层的所有节点已打印，换行到下一层
        }

    }



}
