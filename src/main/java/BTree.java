class BTree {
    private BTreeNode root;
    private int m; // 最小度数

    public BTree(int m) {
        this.root = null;
        this.m = m;
    }

    public BTree(int m,BTreeNode root) {
        this.root = root;
        this.m = m;
    }

    // 搜索键
    public Boolean search(int k) {
        return (root == null) ? null : root.search(k);
    }

    // 插入键
    public void insert(int key) {
        if (root == null) {
            root = new BTreeNode(m, true);
            root.keys[0] = key;
            root.num = 1;
        } else {
            if (root.num == 2 * m - 1) {
                BTreeNode s = new BTreeNode(m, false);
                s.children[0] = root;
                s.splitChild(0, root);

                int i = 0;
                if (s.keys[0] < key) {
                    i++;
                }
                s.children[i].insertNonFull(key);

                root = s;
            } else {
                root.insertNonFull(key);
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
}
