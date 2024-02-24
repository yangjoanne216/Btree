import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            root = new BTreeNode(rootKey, m,null,new ParentEntry(),1,true);
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
            BTreeNode newRoot=nodeTarget.insert(key);
            if(newRoot !=null){
                setRoot(newRoot);
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

}
