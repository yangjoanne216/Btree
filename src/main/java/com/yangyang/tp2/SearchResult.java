package com.yangyang.tp2;

public class SearchResult {
    /**
     * 在B树节点中搜索给定键值的返回结果。
     * <p/>
     * 该结果有两部分组成。第一部分表示此次查找是否成功，
     * 如果查找成功，第二部分表示给定键值在B树节点中的位置，
     * 如果查找失败，第二部分表示给定键值应该插入的位置。
     */
        private boolean result;
        private int index;

        public SearchResult(boolean result, int index) {
            this.result = result;
            this.index = index;
        }

        public boolean getResult() {
            return result;
        }

        public int getIndex() {
            return index;
        }
}
