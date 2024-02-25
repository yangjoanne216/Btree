package com.yangyang.tp2;

import java.util.ArrayList;
import java.util.List;

public class BTreeNode {
        /**
         * 节点的关键字，以非降序存放
         */
        private List<Integer> keys;
        /**
         * 内节点的子节点
         */
        private List<BTreeNode> children;
        /**
         * 是否为叶子节点
         */
        private boolean leaf;

        public BTreeNode() {
            keys = new ArrayList<Integer>();
            children = new ArrayList<BTreeNode>();
            leaf = false;
        }

        public boolean isLeaf() {
            return leaf;
        }

        public void setLeaf(boolean leaf) {
            this.leaf = leaf;
        }

        /**
         * 返回关键字的个数。如果是非叶子节点，该节点的
         * 子节点个数为({@link #size()} + 1)。
         *
         * @return 关键字的个数
         */
        public int size() {
            return keys.size();
        }

        /**
         * 在节点中查找给定的<code>key</code>，如果节点中存在给定的
         * <code>key</code>，则返回一个<code>SearchResult</code>，
         * 标识此次查找成功，给定<code>key</code>在节点中的索引和给定
         * <code>key</code>对应的值。如果不存在，则返回<code>SearchResult</code>
         * 标识此次查找失败，给定<code>key</code>应该插入的位置，该<code>key</code>
         * 对应的值为null。
         * <p/>
         * 如果查找失败，返回结果中的索引域为[0, {@link #size()}]；
         * 如果查找成功，返回结果中的索引域为[0, {@link #size()} - 1]
         * <p/>
         * 这是一个二分查找算法，可以保证时间复杂度为O(log(t))。
         *
         * @param key - 给定的键值
         * @return - 查找结果
         */
        public SearchResult searchKey(Integer key) {
            int l = 0;
            int h = keys.size() - 1;
            int mid = 0;
            while (l <= h) {
                mid = (l + h) / 2; // 先这么写吧，BTree实现中，l+h不可能溢出
                if (keys.get(mid) == key)
                    break;
                else if (keys.get(mid) > key)
                    h = mid - 1;
                else // if(keys.get(mid) < key)
                    l = mid + 1;
            }
            boolean result = false;
            int index = 0;
            if (l <= h) // 说明查找成功
            {
                result = true;
                index = mid; // index表示元素所在的位置
            } else {
                result = false;
                index = l; // index表示元素应该插入的位置
            }
            return new SearchResult(result, index);
        }

        /**
         * 将给定的<code>key</code>追加到节点的末尾，
         * 一定要确保调用该方法之后，节点中的关键字还是
         * 以非降序存放。
         *
         * @param key - 给定的键值
         */
        public void addKey(Integer key) {
            keys.add(key);
        }

        /**
         * 删除给定索引的键值。
         * <p/>
         * 你需要自己保证给定的索引是合法的。
         *
         * @param index - 给定的索引
         */
        public void removeKey(int index) {
            keys.remove(index);
        }

        /**
         * 得到节点中给定索引的键值。
         * <p/>
         * 你需要自己保证给定的索引是合法的。
         *
         * @param index - 给定的索引
         * @return 节点中给定索引的键值
         */
        public Integer keyAt(int index) {
            return keys.get(index);
        }

        /**
         * 在该节点中插入给定的<code>key</code>，
         * 该方法保证插入之后，其键值还是以非降序存放。
         * <p/>
         * 不过该方法的时间复杂度为O(t)。
         * <p/>
         * TODO 需要考虑键值是否可以重复。
         *
         * @param key - 给定的键值
         */
        public void insertKey(Integer key) {
            SearchResult result = searchKey(key);
            insertKey(key, result.getIndex());
        }

        /**
         * 在该节点中给定索引的位置插入给定的<code>key</code>，
         * 你需要自己保证<code>key</code>插入了正确的位置。
         *
         * @param key   - 给定的键值
         * @param index - 给定的索引
         */
        public void insertKey(Integer key, int index) {
            /* TODO
             * 通过新建一个ArrayList来实现插入真的很恶心，先这样吧
             * 要是有类似C中的reallocate就好了。
             */
            List<Integer> newKeys = new ArrayList<Integer>();
            int i = 0;

            // index = 0或者index = keys.size()都没有问题
            for (; i < index; ++i)
                newKeys.add(keys.get(i));
            newKeys.add(key);
            for (; i < keys.size(); ++i)
                newKeys.add(keys.get(i));
            keys = newKeys;
        }

        /**
         * 返回节点中给定索引的子节点。
         * <p/>
         * 你需要自己保证给定的索引是合法的。
         *
         * @param index - 给定的索引
         * @return 给定索引对应的子节点
         */
        public BTreeNode childAt(int index) {
            if (isLeaf())
                throw new UnsupportedOperationException("Leaf node doesn't have children.");
            return children.get(index);
        }

        /**
         * 将给定的子节点追加到该节点的末尾。
         *
         * @param child - 给定的子节点
         */
        public void addChild(BTreeNode child) {
            children.add(child);
        }

        /**
         * 删除该节点中给定索引位置的子节点。
         * </p>
         * 你需要自己保证给定的索引是合法的。
         *
         * @param index - 给定的索引
         */
        public void removeChild(int index) {
            children.remove(index);
        }

        /**
         * 将给定的子节点插入到该节点中给定索引
         * 的位置。
         *
         * @param child - 给定的子节点
         * @param index - 子节点带插入的位置
         */
        public void insertChild(BTreeNode child, int index) {
            List<BTreeNode> newChildren = new ArrayList<BTreeNode>();
            int i = 0;
            for (; i < index; ++i)
                newChildren.add(children.get(i));
            newChildren.add(child);
            for (; i < children.size(); ++i)
                newChildren.add(children.get(i));
            children = newChildren;
        }
}


