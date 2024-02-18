import java.security.Key;

class BTreeNode {
    int[] keys; // Key value
    int m; // comme le dégre
    BTreeNode[] children; // pointeurs vers les enfants
    int num; //le nombre de la valeur dans ce Node
    boolean leaf; // vrai si il est feuille

    BTreeNode parent;//pointeurs vers les parent

    //测试constructor feuille
    public BTreeNode(int m,int[] keys){
        this.m = m;
        this.keys = keys;
        this.children = null;
        this.leaf = true;
        this.num=keys.length;
    }
    //测试  constructor racine
    public BTreeNode(int m,int[] keys,BTreeNode[] children){
        this.m = m;
        this.keys = keys;
        this.children = null;
        this.leaf = false;
        this.children=children;
        this.num =keys.length;
    }

    public BTreeNode(int m, boolean leaf) {
        this.m = m;
        this.leaf = leaf;
        this.keys = new int[m - 1]; // chaque node a au plus m-1 valeurs
        this.children = new BTreeNode[m]; // chaque node a au plus m enfants
        this.num =keys.length; //
    }

    // 搜索键
    public Boolean search(int key) {
        for(int i = 0; i<num;i++){
            //On a bien trouvé la valeur
            if(key==keys[i])
            {
                return true;
            }
            if(key<keys[i]){
                //Key est supérieur que Key[i], on prend l'enfant à gauche
                //index d'enfant à gauche est i, index d'enfant à droit est i+1
                return children[i].search(key);
            }
            //if key > keys[i], on va continuer de chercher
        }

        //Si on sortie de ce loop, c'est à dire, key est supérieur que tous les values dans ce node,
        //on fait un recherche dans un enfant plus droit
        if(!this.leaf) //si c'est pas un feuile (parce que si c'est une feuile,il y a pas d'enfant)
        {
            return children[num].search(key);
        }
        return false;
    }

    public BTreeNode search2(int key) {
        // if k < K0
        if (key < keys[0] && this.leaf!=true) {
            if(this.leaf)
                return this; //if is leaf, the research is unsuccessful
            return children[0].search2(key); // if not , do the resarch in children 0
        }

        //if ki<k<ki+1
        for(int i=0;i<num-1;i++){
            if(key == keys[i])
                return null; // on a bien trouvé , the search is unsuccessful

            if(key>keys[i] && key<keys[i+1]) ////if ki<k<ki+1
            {
                if(this.leaf)
                    return this;//if is leaf, the research is unsuccessful
                return children[i+1].search2(key); // if not , do the resarch in children i+1
            }
        }

        //le loop est fini,c'est à dire K>K_num ou K=K_(num-1)
        if(key == keys[num-1])
            return null;
        if(this.leaf)
            return this;//if is leaf, the research is unsuccessful
        return children[num].search2(key);// if not , do the resarch in children num+1

    }


    public void insertNonFull(int key) {
    }

    public void splitChild(int i, BTreeNode root) {
    }

    public int[] getKeys() {
        for (int i=0;i<this.keys.length;i++){
            System.out.println(keys[i]);
        }
        return keys;
    }

    public int getM() {
        return m;
    }

    public BTreeNode[] getChildren() {
        return children;
    }

    public int getNum() {
        return num;
    }

    public boolean isLeaf() {
        return leaf;
    }


}
