package com.sgbd.tp2_impair;

import java.util.ArrayList;
import java.util.List;

class BTreeNode {
    List<Integer> keys; //Key Value
    int m; // comme l'ordre
    List<BTreeNode> children; // les enfants
    ParentEntry parentEntry; // pointeurs vers le parent

    //boolean leaf; // vrai si il est feuille

    public BTreeNode(int m) {
        this.m =m;
    }

    public BTreeNode(List<Integer> keys, int m, List<BTreeNode> children, ParentEntry parentEntry) {
        this.keys = keys;
        this.m = m;
        this.children = children;
        this.parentEntry = parentEntry;
    }


    /**Trouvez la clé donnée
     * @param key clé donné
     * @return nœud de feuille où l'on peut insérer des clés， si  nœud est null, c'est à dire, on a bien trouvé la clé
     */
    public BTreeNode search(int key) {
        // if k < K0
        //if (key < keys.get(0) && this.leaf!=true)
        if (key < keys.get(0) ) {
            if(isLeaf())
                return this; //if is leaf, the research is unsuccessful
            return children.get(0).search(key); // if not , do the resarch in children 0
        }

        //if ki<k<ki+1
        for(int i=0;i<keys.size()-1;i++){
            if(key == keys.get(i))
                return null; // on a bien trouvé , the search is unsuccessful

            if(key>keys.get(i) && key< keys.get(i + 1)) ////if ki<k<ki+1
            {
                if(this.isLeaf())
                    return this;//if is leaf, the research is unsuccessful
                return children.get(i + 1).search(key); // if not , do the resarch in children i+1
            }
        }

        //le loop est fini,c'est à dire K>K_num ou K=K_(num-1), on a bien trouvé a clé
        if(key == keys.get(keys.size()-1))
            return null;
        if(isLeaf())
            return this;//if is leaf, the research is unsuccessful
        return children.get(keys.size()).search(key);// if not , do the resarch in children num+1
    }

    /**
     * @param key
     * @return  BTreeNode[] La longueur est de trois,
     * le premier est le nœud racine nouvellement généré, s'il est nul, cela signifie qu'aucun nouveau nœud racine n'est généré cette fois-ci.
     * Le deuxième est le nœud de gauche après la séparation, et le troisième est le nœud de droite après la séparation.
     */
    public BTreeNode[] insert(int key){
        BTreeNode[]  nodes = new BTreeNode[3];
        // this node n'est pleine
        if(!(this.getKeys().size()>=m-1))
        {
            insertKey(key);

        }
        else{ //target est plein
           nodes = splitChild(key);
        }

        return nodes;
    }


    /**Insérez la clé au bon endroit dans le nœud pour préparer la séparation.
     * @param key clé donée
     */
    public void insertKey(int key) {
        //trouver une place pour insérer la valeur
        int i= 0;
        while(i<keys.size()&&keys.get(i)<key){
            i++;
        }
        keys.add(i,key);
        if(!this.isLeaf()&&keys.size()!=m+1){  //S'il ne s'agit pas d'un nœud feuille, on change l'ordre de ses enfant
            int j = 0;
            while(j<this.children.size()&&children.get(j)!=null){
                children.get(j).getParentEntry().setIndex(j);
                j++;
            }
        }

    }


    /**générer la nouvelle racine
     * @return la nouvelle racine
     */
    public BTreeNode initialRoot(){
        BTreeNode newRoot = null;
        List rootKey = new ArrayList<Integer>();
        newRoot = new BTreeNode(rootKey, m,new ArrayList<>(),new ParentEntry());
        //return newRoot
        this.setParentEntry(0,newRoot);
        newRoot.children.add(this);
        return newRoot;
    }


    /**Générer le nœud de gauche après la séparation
     * @return le nœud de gauche après la séparation
     */
    public BTreeNode initialLeftNode(){

        BTreeNode leftNode = new BTreeNode(m);
        //1.keys
        List<Integer> leftKeys = new ArrayList<>(keys.subList(0,m/2));
        leftNode.setKeys(leftKeys);
        //2.enfant
        List<BTreeNode> leftChildren;
        if(children==null){
            leftChildren = null;
        }
        else {
            leftChildren = new ArrayList<>(children.subList(0,(m+1)/2));
        }
        leftNode.setChildren(leftChildren);
        //3.parentEntry ne change pas
        ParentEntry leftParentEntry = new ParentEntry(this.parentEntry.getIndex(),this.parentEntry.getParent());
        leftNode.setParentEntry(leftParentEntry);
        return leftNode;
    }

    /**Générer le nœud de droite après la séparation
     * @return le nœud de droite après la séparation
     */
    public BTreeNode initialRightNode(){
        BTreeNode rightNode = new BTreeNode(m);
        //1.keys
        List<Integer> rightKeys = new ArrayList<>(keys.subList(m/2+1,keys.size()));
        rightNode.setKeys(rightKeys);
        //2.enfant
        List<BTreeNode> rightChildren;
        if(children==null){
            rightChildren = null;
        }
        else{
            rightChildren = new ArrayList<>(children.subList((m+1)/2,m));
        }
        rightNode.setChildren(rightChildren);
        //3.parentEntry （index + 1）
        ParentEntry rightparentEntry = new ParentEntry(this.parentEntry.getIndex()+1,this.parentEntry.getParent());
        rightNode.setParentEntry(rightparentEntry);
        return rightNode;
    }

    public BTreeNode[] splitChild(int key) {
        BTreeNode[] nodes  =new BTreeNode[3];
        //Trouvez le nombre qui se trouve au milieu.
        insertKey(key);
        //2.Insérer le nombre du milieu dans le nœud parent.
        //2.1 Déterminez si le nœud parent est nul, s'il s'agit d'une racine et commencez à créer une nouvelle racine.
        if(this.parentEntry.getParent()==null){
            nodes[0] = initialRoot();
        }
        boolean inMiddle =false;
        BTreeNode parentLeftNode = null;
        BTreeNode parentRightNode = null;
        //2.2 Insérer le numéro du milieu dans le nœud parent
        if(this.parentEntry.getParent().keys.size()<m-1){
            //Le nœud parent n'est pas plein, insertion directe
            this.parentEntry.getParent().insertKey(keys.get(m/2));
        }
        else{
            //Lorsque le nœud parent est plein, appeler splitChild dans  le nœud parent
            nodes = this.parentEntry.getParent().splitChild(keys.get(m/2));
            //Choisir un nouveau nœud parent parmi les nouveaux nœuds gauche et droit après la séparation du nœud parent.
            int location = getParentEntry().getIndex();
            //如果节点在便向右侧
            if(location<(m+1)/2-1){
                this.setParentEntry(location,nodes[1]);
            }

            //如果节点在便向左侧
            if(location>(m+1)/2-1){
                this.setParentEntry(location-((m+1)/2),nodes[2]);
            }
            //如果节点在正中间
            if(location==(m+1)/2-1){
                inMiddle = true;
                this.setParentEntry(location,nodes[1]);
                parentLeftNode = nodes[0];
                parentRightNode = nodes[1];
            }
        }

        //Création du nœud de gauche après la séparation
        nodes[1] = initialLeftNode();
        //Création du nœud de droit après la séparation
        nodes[2] = initialRightNode();

        if(inMiddle){
            //左节点位于父亲左节点的最后一个
            nodes[1].setParentEntry((m+1)/2-1,parentLeftNode);
            //右节点位于父亲右节点的第一个
            nodes[2].setParentEntry(0,parentRightNode);
        }

        //Les nœuds de gauche et de droite sont maintenant de nouveaux enfants du parent d'origine,
        //Traitement du nœud parent par rapport aux nœuds gauche et droit

        //Ajouter le nœud à gauche comme nœud enfant au nœud parent.
        this.parentEntry.getParent().children.add(nodes[1].parentEntry.getIndex(),nodes[1]);
        //Ajouter le nœud à droit comme nœud enfant au nœud parent.
        this.parentEntry.getParent().children.add(nodes[2].parentEntry.getIndex(),nodes[2]);
        //Supprimer les enfants redondants du nœud parent
        this.parentEntry.getParent().children.remove(nodes[2].parentEntry.getIndex()+1);
        return nodes;
    }
    private void setParentEntry(int i, BTreeNode parent) {
        this.parentEntry.setParent(parent);
        this.parentEntry.setIndex(i);
    }

    public List<Integer> getKeys() {
        return keys;
    }

    public int getM() {
        return m;
    }

    public List<BTreeNode> getChildren() {
        return children;
    }

    public void setKeys(List<Integer> keys) {
        this.keys = keys;
    }

    public void setM(int m) {
        this.m = m;
    }

    public void setChildren(List<BTreeNode> children) {
        this.children = children;
    }

    public ParentEntry getParentEntry() {
        return parentEntry;
    }

    public void setParentEntry(ParentEntry parentEntry) {
        this.parentEntry = parentEntry;
    }


    public boolean isLeaf() {
        return (this.children==null||this.children.size()==0);
    }
}
