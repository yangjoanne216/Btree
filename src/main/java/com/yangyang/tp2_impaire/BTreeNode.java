package com.yangyang.tp2_impaire;

import java.util.ArrayList;
import java.util.List;

public class BTreeNode {
        private List<Integer> keys;

        private List<BTreeNode> children;

        private boolean leaf;

        public BTreeNode() {
            keys = new ArrayList<Integer>();
            children = new ArrayList<BTreeNode>();
            leaf = false;
        }

        public boolean isLeaf() {
            return leaf;
        }

        public void setLeaf(boolean leaf) {this.leaf = leaf;
        }
        /**
         * Recherche la clé donnée dans le nœud, si elle existe dans le nœud, la recherche réussit.
         * Si elle n'existe pas, la recherche échoue, étant donné l'endroit où la clé doit être insérée.
         *
         * @param key - Valeur de la clé donnée
         * @return - SearchResult
         */
        public ResearchOutcome searchKey(Integer key) {
            int l = 0;
            int h = keys.size() - 1;
            int mid = 0;
            while (l <= h) {
                mid = (l + h) / 2;
                if (keys.get(mid) == key)
                    break;
                else if (keys.get(mid) > key)
                    h = mid - 1;
                else // if(keys.get(mid) < key)
                    l = mid + 1;
            }
            boolean result;
            int index = 0;
            if (l <= h) //on a bien trouvé
            {
                result = true;
                index = mid; // l'index indique la position de l'élément
            } else {
                result = false;
                index = l; // l'index indique l'endroit où l'élément doit être inséré
            }
            return new ResearchOutcome(result, index);
        }

        /**
         * Insérer la clé donnée dans ce nœud
         *
         *
         */
        public void insertKey(Integer key) {
            ResearchOutcome result = searchKey(key);
            insertKey(key, result.getIndex());
        }

        /**
         *Insérer la clé à la position de l'index donné
         * @param key   - 给定的键值
         * @param index - 给定的索引
         */
        public void insertKey(Integer key, int index) {

            List<Integer> newKeys = new ArrayList<Integer>();
            int i = 0;

            for (; i < index; ++i)
                newKeys.add(keys.get(i));
            newKeys.add(key);
            for (; i < keys.size(); ++i)
                newKeys.add(keys.get(i));
            keys = newKeys;
        }

        /**
         * Insère le noeud enfant donné dans le noeud à l'index donné dans le nœud.
         *
         *
         * @param child - le noeud enfant
         * @param index - l'index donné
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

    public List<Integer> getKey(){
        return keys;
    }


    /**
     * @return Renvoie le nombre de clé.
     */
    public int size() {
        return keys.size();
    }

    public BTreeNode childAt(int index) {
        return children.get(index);
    }

    public List<BTreeNode> getChildren() {
        return children;
    }
}


