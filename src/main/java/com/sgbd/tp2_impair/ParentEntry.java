package com.sgbd.tp2_impair;

/**Cette classe est utilisée pour représenter la relation entre un nœud et son nœud parent,il y a deux élément
 * <P>
 * où BTreeNode représente le nœud parent du nœud
 * et l'index représente la position dans le nœud parent,
 *
 *</P>
 */
public class ParentEntry {
    /**
     * représente la position dans le nœud parent,
     */
    private int index;
    /**
     *  le nœud parent
     */
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
