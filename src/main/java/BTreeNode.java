import java.security.Key;
import java.util.ArrayList;
import java.util.List;

class BTreeNode {
    //int[] keys; // Key value
   List<Integer> keys; //Key Value
    int m; // comme le dégre

    BTreeNode[] children; // pointeurs vers les enfants

    ParentEntry parentEntry; // pointeurs vers le parent
    int num; //le nombre de la valeur dans ce Node
    boolean leaf; // vrai si il est feuille



    //测试constructor feuille
    public BTreeNode(int m, List keys){
        this.m = m;
        this.keys = keys;
        this.children = null;
        this.leaf = true;
        this.num=keys.size();
        this.parentEntry =parentEntry;
    }
    //测试  constructor racine
    public BTreeNode(int m,List keys,BTreeNode[] children){
        this.m = m;
        this.keys = keys;
        this.children = null;
        this.leaf = false;
        this.children=children;
        this.num =keys.size();
    }

    public BTreeNode(int m, boolean leaf) {
        this.m = m;
        this.leaf = leaf;
        this.keys = new ArrayList<>(); // chaque node a au plus m-1 valeurs
        this.children = new BTreeNode[m]; // chaque node a au plus m enfants
        this.num =keys.size(); //
    }

    // 搜索键
    public Boolean search(int key) {
        for(int i = 0; i<num;i++){
            //On a bien trouvé la valeur
            if(key==keys.get(i))
            {
                return true;
            }
            if(key<keys.get(i)){
                //Key est supérieur que Key[i], on prend l'enfant à gauche
                //index d'enfant à gauche est i, index d'enfant à droit est i+1
                return children[i].search(key);
            }
            //if key > keys.get(i), on va continuer de chercher
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
        if (key < keys.get(0) && this.leaf!=true) {
            if(this.leaf)
                return this; //if is leaf, the research is unsuccessful
            return children[0].search2(key); // if not , do the resarch in children 0
        }

        //if ki<k<ki+1
        for(int i=0;i<num-1;i++){
            if(key == keys.get(i))
                return null; // on a bien trouvé , the search is unsuccessful

            if(key>keys.get(i) && key< keys.get(i + 1)) ////if ki<k<ki+1
            {
                if(this.leaf)
                    return this;//if is leaf, the research is unsuccessful
                return children[i+1].search2(key); // if not , do the resarch in children i+1
            }
        }

        //le loop est fini,c'est à dire K>K_num ou K=K_(num-1)
        if(key == keys.get(num-1))
            return null;
        if(this.leaf)
            return this;//if is leaf, the research is unsuccessful
        return children[num].search2(key);// if not , do the resarch in children num+1

    }
    //插入父节点部分的
    public void insert(int key, int i){


        //sinon on ajout et il y a deux cas
        // target node n'est pleine
        if(!(this.getKeys().size()>=m-1))
        {
            keys.add(i,key);
        }
        else{
            splitChild(key, parentEntry.getParent());
        }

    }

    //插入feuiilton里面的
    public void insert(int key){
        BTreeNode target = search2(key);
        //si on trouve le key dans le tree, on fait rien, on return
        if(target == null){
            System.out.println(key + "est déjà dans le tree!\n");
            return;
        }
        //sinon on ajout et il y a deux cas
        // target node n'est pleine
        if(!(target.getKeys().size()>=m-1))
        {
            insertNonFull(key);
        }
        else{ //target est plein

        }
    }


    public void insertNonFull(int key) {
        //trouver une place pour insérer la valeur
        int i= 0;
        while(i<keys.size()&&keys.get(i)<key){
            i++;
        }
        keys.add(i,key);
    }

    public void splitChild(int key, BTreeNode target) {
        //trouver une place pour insérer la valeur
        int i= 0;
        while(i<keys.size()&&keys.get(i)<key){
            i++;
        }
        //现在数组的长度是比 m-1要大一位
        keys.add(i,key);
        //找到中间的那个数
        int middle = m/2;
        //把中间的那个数插入父节点
        target.parentEntry.getParent().insert(keys.get(middle),parentEntry.getIndex());
        //剩下的分裂成两个
        //左侧的BtreeNode


        //右侧的BtreeNode


        //父节点的所有右侧小孩 顺序+1

    }

    public List<Integer> getKeys() {
        for (int i=0;i<this.keys.size();i++){
            System.out.println(keys.get(i));
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
