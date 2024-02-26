package java.com.yangyang.tp2_paire;

public class ParentEntry {
    private int index; //是parent的第几个小孩
    private BTreeNode parent;

    public ParentEntry(int index, BTreeNode parent) {
        this.index = index;
        this.parent = parent;
    }

    public ParentEntry() {
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public BTreeNode getParent() {
        return parent;
    }

    public void setParent(BTreeNode parent) {
        this.parent = parent;
    }
}
