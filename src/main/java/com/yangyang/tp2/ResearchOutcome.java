package com.yangyang.tp2;

public class ResearchOutcome {
    /**
     * Recherche dans le nœud de l'arbre B le résultat retourné pour la valeur clé donnée.
     * <p/>
     * Ce résultat se compose de deux parties. La première partie indique si la recherche a abouti ou non.
     * Si la recherche a réussi, la deuxième partie indique la position de la valeur clé donnée dans le nœud de l'arbre B. Si la recherche a échoué, la deuxième partie indique la position de la valeur clé donnée dans le nœud de l'arbre B.
     * Si la recherche a échoué, la deuxième partie indique l'endroit où la valeur clé donnée doit être insérée.
     */
        private boolean result;
        private int index;

        public ResearchOutcome(boolean result, int index) {
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
