package com.yangyang.tp2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BTree {
    private static final int DEFAULT_T = 2;

    /**
     * B树的根节点
     */
    private BTreeNode root;
    /**
     * 根据B树的定义，B树的每个非根节点的关键字数n满足(t - 1) <= n <= (2t - 1)
     */
    private int t = DEFAULT_T;
    /**
     * 非根节点中最小的键值数
     */
    private int minKeySize = t - 1;
    /**
     * 非根节点中最大的键值数
     */
    private int maxKeySize = 2 * t - 1;

    public BTree() {
        root = new BTreeNode();
        root.setLeaf(true);
    }

    public BTree(int t) {
        this();
        this.t = t;
        minKeySize = t - 1;
        maxKeySize = 2 * t - 1;
    }

    /**
     * 搜索给定的<code>key</code>。
     * <p/>
     * TODO 需要重新定义返回结果，应该返回
     * <code>key</code>对应的值。
     *
     * @param key - 给定的键值
     * @return TODO 得返回值类型
     */
    public int search(Integer key) {
        return search(root, key);
    }

    /**
     * 在以给定节点为根的子树中，递归搜索
     * 给定的<code>key</code>
     *
     * @param node - 子树的根节点
     * @param key  - 给定的键值
     * @return TODO
     */
    private static int search(BTreeNode node, Integer key) {
        SearchResult result = node.searchKey(key);
        if (result.getResult())
            return result.getIndex();
        else {
            if (node.isLeaf())
                return -1;
            else
                search(node.childAt(result.getIndex()), key);

        }
        return -1;
    }

    /**
     * 分裂一个满子节点<code>childNode</code>。
     * <p/>
     * 你需要自己保证给定的子节点是满节点。
     *
     * @param parentNode - 父节点
     * @param childNode  - 满子节点
     * @param index      - 满子节点在父节点中的索引
     */
    private void splitNode(BTreeNode parentNode, BTreeNode childNode, int index) {
        assert childNode.size() == maxKeySize;

        BTreeNode siblingNode = new BTreeNode();
        siblingNode.setLeaf(childNode.isLeaf());
        // 将满子节点中索引为[t, 2t - 2]的(t - 1)个关键字插入新的节点中
        for (int i = 0; i < minKeySize; ++i)
            siblingNode.addKey(childNode.keyAt(t + i));
        // 提取满子节点中的中间关键字，其索引为(t - 1)
        Integer key = childNode.keyAt(t - 1);
        // 删除满子节点中索引为[t - 1, 2t - 2]的t个关键字
        for (int i = maxKeySize - 1; i >= t - 1; --i)
            childNode.removeKey(i);
        if (!childNode.isLeaf()) // 如果满子节点不是叶节点，则还需要处理其子节点
        {
            // 将满子节点中索引为[t, 2t - 1]的t个子节点插入新的节点中
            for (int i = 0; i < minKeySize + 1; ++i)
                siblingNode.addChild(childNode.childAt(t + i));
            // 删除满子节点中索引为[t, 2t - 1]的t个子节点
            for (int i = maxKeySize; i >= t; --i)
                childNode.removeChild(i);
        }
        // 将key插入父节点
        parentNode.insertKey(key, index);
        // 将新节点插入父节点
        parentNode.insertChild(siblingNode, index + 1);
    }

    /**
     * 在一个非满节点中插入给定的<code>key</code>。
     *
     * @param node - 非满节点
     * @param key  - 给定的键值
     */
    private void insertNotFull(BTreeNode node, Integer key) {
        assert node.size() < maxKeySize;

        if (node.isLeaf()) // 如果是叶子节点，直接插入
            node.insertKey(key);
        else {
            /* 找到key在给定节点应该插入的位置，那么key应该插入
             * 该位置对应的子树中
             */
            SearchResult result = node.searchKey(key);
            BTreeNode childNode = node.childAt(result.getIndex());
            if (childNode.size() == 2 * t - 1) // 如果子节点是满节点
            {
                // 则先分裂
                splitNode(node, childNode, result.getIndex());
                /* 如果给定的key大于分裂之后新生成的键值，则需要插入该新键值的右边，
                 * 否则左边。
                 */
                if (key > node.keyAt(result.getIndex()))
                    childNode = node.childAt(result.getIndex() + 1);
            }
            insertNotFull(childNode, key);
        }
    }

    /**
     * 在B树中插入给定的<code>key</code>。
     *
     * @param key - 给定的键值
     */
    public void insert(Integer key) {
        if (root.size() == maxKeySize) // 如果根节点满了，则B树长高
        {
            BTreeNode newRoot = new BTreeNode();
            newRoot.setLeaf(false);
            newRoot.addChild(root);
            splitNode(newRoot, root, 0);
            root = newRoot;
        }
        insertNotFull(root, key);
    }

    /**
     * 从B树中删除一个给定的<code>key</code>。
     *
     * @param key - 给定的键值
     */
    public void delete(Integer key) {
        // root的情况还需要做一些特殊处理
        delete(root, key);
    }

    /**
     * 从以给定<code>node</code>为根的子树中删除指定的<code>key</code>。
     * <p/>
     * 删除的实现思想请参考《算法导论》第二版的第18章。
     * <p/>
     * TODO 需要重构，代码太长了
     *
     * @param node - 给定的节点
     * @param key  - 给定的键值
     */
    public void delete(BTreeNode node, Integer key) {
        // 该过程需要保证，对非根节点执行删除操作时，其关键字个数至少为t。
        assert node.size() >= t || node == root;

        SearchResult result = node.searchKey(key);
        /*
         * 因为这是查找成功的情况，0 <= result.getIndex() <= (node.size() - 1)，
         * 因此(result.getIndex() + 1)不会溢出。
         */
        if (result.getResult()) {
            // 1.如果关键字在节点node中，并且是叶节点，则直接删除。
            if (node.isLeaf())
                node.removeKey(result.getIndex());
            else {
                // 2.a 如果节点node中前于key的子节点包含至少t个关键字
                BTreeNode leftChildNode = node.childAt(result.getIndex());
                if (leftChildNode.size() >= t) {
                    // 使用leftChildNode中的最后一个键值代替node中的key
                    node.removeKey(result.getIndex());
                    node.insertKey(leftChildNode.keyAt(leftChildNode.size() - 1), result.getIndex());
                    delete(leftChildNode, leftChildNode.keyAt(leftChildNode.size() - 1));
                    // node.
                } else {
                    // 2.b 如果节点node中后于key的子节点包含至少t个关键字
                    BTreeNode rightChildNode = node.childAt(result.getIndex() + 1);
                    if (rightChildNode.size() >= t) {
                        // 使用rightChildNode中的第一个键值代替node中的key
                        node.removeKey(result.getIndex());
                        node.insertKey(rightChildNode.keyAt(0), result.getIndex());
                        delete(rightChildNode, rightChildNode.keyAt(0));
                    } else // 2.c 前于key和后于key的子节点都只包含t-1个关键字
                    {
                        node.removeKey(result.getIndex());
                        node.removeChild(result.getIndex() + 1);
                        // 将key和rightChildNode中的键值合并进leftChildNode
                        leftChildNode.addKey(key);
                        for (int i = 0; i < rightChildNode.size(); ++i)
                            leftChildNode.addKey(rightChildNode.keyAt(i));
                        // 将rightChildNode中的子节点合并进leftChildNode，如果有的话
                        if (!rightChildNode.isLeaf()) {
                            for (int i = 0; i <= rightChildNode.size(); ++i)
                                leftChildNode.addChild(rightChildNode.childAt(i));
                        }
                        delete(leftChildNode, key);
                    }
                }
            }
        } else {
            /*
             * 因为这是查找失败的情况，0 <= result.getIndex() <= node.size()，
             * 因此(result.getIndex() + 1)会溢出。
             */
            if (node.isLeaf()) // 如果关键字不在节点node中，并且是叶节点，则什么都不做，因为该关键字不在该B树中
            {
                System.out.println("The key: " + key + " isn't in this BTree.");
                return;
            }
            BTreeNode childNode = node.childAt(result.getIndex());
            if (childNode.size() >= t)
                delete(childNode, key); // 递归删除
            else // 3
            {
                // 先查找右边的兄弟节点
                BTreeNode siblingNode = null;
                int siblingIndex = -1;
                if (result.getIndex() < node.size()) // 存在右兄弟节点
                {
                    if (node.childAt(result.getIndex() + 1).size() >= t) {
                        siblingNode = node.childAt(result.getIndex() + 1);
                        siblingIndex = result.getIndex() + 1;
                    }
                }
                // 如果右边的兄弟节点不符合条件，则试试左边的兄弟节点
                if (siblingNode == null) {
                    if (result.getIndex() > 0) // 存在左兄弟节点
                    {
                        if (node.childAt(result.getIndex() - 1).size() >= t) {
                            siblingNode = node.childAt(result.getIndex() - 1);
                            siblingIndex = result.getIndex() - 1;
                        }
                    }
                }
                // 3.a 有一个相邻兄弟节点至少包含t个关键字
                if (siblingNode != null) {
                    if (siblingIndex < result.getIndex()) // 左兄弟节点满足条件
                    {
                        childNode.insertKey(node.keyAt(siblingIndex), 0);
                        node.removeKey(siblingIndex);
                        node.insertKey(siblingNode.keyAt(siblingNode.size() - 1), siblingIndex);
                        siblingNode.removeKey(siblingNode.size() - 1);
                        // 将左兄弟节点的最后一个孩子移到childNode
                        if (!siblingNode.isLeaf()) {
                            childNode.insertChild(siblingNode.childAt(siblingNode.size()), 0);
                            siblingNode.removeChild(siblingNode.size());
                        }
                    } else // 右兄弟节点满足条件
                    {
                        childNode.insertKey(node.keyAt(result.getIndex()), childNode.size() - 1);
                        node.removeKey(result.getIndex());
                        node.insertKey(siblingNode.keyAt(0), result.getIndex());
                        siblingNode.removeKey(0);
                        // 将右兄弟节点的第一个孩子移到childNode
                        // childNode.insertChild(siblingNode.childAt(0), childNode.size() + 1);
                        if (!siblingNode.isLeaf()) {
                            childNode.addChild(siblingNode.childAt(0));
                            siblingNode.removeChild(0);
                        }
                    }
                    delete(childNode, key);
                } else // 3.b 如果其相邻左右节点都包含t-1个关键字
                {
                    if (result.getIndex() < node.size()) // 存在右兄弟
                    {
                        BTreeNode rightSiblingNode = node.childAt(result.getIndex() + 1);
                        childNode.addKey(node.keyAt(result.getIndex()));
                        node.removeKey(result.getIndex());
                        node.removeChild(result.getIndex() + 1);
                        for (int i = 0; i < rightSiblingNode.size(); ++i)
                            childNode.addKey(rightSiblingNode.keyAt(i));
                        if (!rightSiblingNode.isLeaf()) {
                            for (int i = 0; i <= rightSiblingNode.size(); ++i)
                                childNode.addChild(rightSiblingNode.childAt(i));
                        }
                    } else // 存在左节点
                    {
                        BTreeNode leftSiblingNode = node.childAt(result.getIndex() - 1);
                        childNode.addKey(node.keyAt(result.getIndex() - 1));
                        node.removeKey(result.getIndex() - 1);
                        node.removeChild(result.getIndex() - 1);
                        for (int i = leftSiblingNode.size() - 1; i >= 0; --i)
                            childNode.insertKey(leftSiblingNode.keyAt(i), 0);
                        if (!leftSiblingNode.isLeaf()) {
                            for (int i = leftSiblingNode.size(); i >= 0; --i)
                                childNode.insertChild(leftSiblingNode.childAt(i), 0);
                        }
                    }
                    // 如果node是root并且node不包含任何关键字了
                    if (node == root && node.size() == 0)
                        root = childNode;
                    delete(childNode, key);
                }
            }
        }
    }

    /**
     * 一个简单的层次遍历B树实现，用于输出B树。
     * <p/>
     * TODO 待改进，使显示更加形象化。
     */
    public void output() {
        Queue<BTreeNode> queue = new LinkedList<BTreeNode>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            BTreeNode node = queue.poll();
            for (int i = 0; i < node.size(); ++i)
                System.out.print(node.keyAt(i) + " ");
            System.out.println();
            if (!node.isLeaf()) {
                for (int i = 0; i <= node.size(); ++i)
                    queue.offer(node.childAt(i));
            }
        }
    }



}
