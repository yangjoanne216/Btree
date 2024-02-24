import java.util.ArrayList;
import java.util.List;

class BTreeNode {
    //int[] keys; // Key value
   List<Integer> keys; //Key Value
    int m; // comme le dégre

    List<BTreeNode> children; // pointeurs vers les enfants

    ParentEntry parentEntry; // pointeurs vers le parent
    int num; //le nombre de la valeur dans ce Node
    boolean leaf; // vrai si il est feuille

    public BTreeNode(int m){
        this.m =m;
    }


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
    public BTreeNode(int m,List keys,List children){
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
        this.children = new ArrayList<>(); // chaque node a au plus m enfants
        this.num =keys.size(); //
    }

    public BTreeNode(List<Integer> keys, int m, List<BTreeNode> children, ParentEntry parentEntry, int num) {
        this.keys = keys;
        this.m = m;
        this.children = children;
        this.parentEntry = parentEntry;
        this.num = num;
    }

    public BTreeNode(List<Integer> keys, int m, List<BTreeNode> children, ParentEntry parentEntry, int num, boolean leaf) {
        this.keys = keys;
        this.m = m;
        this.children = children;
        this.parentEntry = parentEntry;
        this.num = num;
        this.leaf = leaf;
    }

    public BTreeNode() {

    }

    // 搜索键
    public BTreeNode search(int key) {
        // if k < K0
        //if (key < keys.get(0) && this.leaf!=true)
        if (key < keys.get(0) ) {
            if(this.leaf)
                return this; //if is leaf, the research is unsuccessful
            return children.get(0).search(key); // if not , do the resarch in children 0
        }

        //if ki<k<ki+1
        for(int i=0;i<num-1;i++){
            if(key == keys.get(i))
                return null; // on a bien trouvé , the search is unsuccessful

            if(key>keys.get(i) && key< keys.get(i + 1)) ////if ki<k<ki+1
            {
                if(this.leaf)
                    return this;//if is leaf, the research is unsuccessful
                return children.get(i + 1).search(key); // if not , do the resarch in children i+1
            }
        }

        //le loop est fini,c'est à dire K>K_num ou K=K_(num-1)
        if(key == keys.get(keys.size()-1))
            return null;
        if(this.leaf)
            return this;//if is leaf, the research is unsuccessful
        return children.get(num).search(key);// if not , do the resarch in children num+1
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
            //target est plein
            //splitChild(key, parentEntry.getParent());
        }

    }

    //插入feuiilton里面的
    public BTreeNode insert(int key){
        BTreeNode  newRoot = null;
        // this node n'est pleine
        if(!(this.getKeys().size()>=m-1))
        {
            insertNonFull(key);
        }
        else{ //target est plein
           newRoot= splitChild(key);
        }
        return newRoot;
    }


    public void insertNonFull(int key) {
        //trouver une place pour insérer la valeur
        int i= 0;
        while(i<keys.size()&&keys.get(i)<key){
            i++;
        }
        keys.add(i,key);
    }

    public BTreeNode splitChild(int key) {
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
        //如果父节点为null，就说明是一个root,要开始设置新的root了
        BTreeNode newRoot = null;
        if(this.parentEntry.getParent()==null){
            //return 一个父节点加1，就是要changeRoot了
            List rootKey = new ArrayList<Integer>();
            //children要等后面分成两个小孩之后再慢慢的去set
            newRoot = new BTreeNode(rootKey, m,new ArrayList<>(),new ParentEntry(),1,false);
            //return newRoot
            this.setParentEntry(0,newRoot);
            newRoot.children.add(this);
        }

        //把中间的数插入到父节点中
        this.parentEntry.getParent().insert(keys.get(middle));
        //this.parentEntry.getParent().insert(keys.get(middle),parentEntry.getIndex());

        //剩下的分裂成两个

        //左侧的BtreeNode
        BTreeNode left = new BTreeNode(m);
        //1.左侧的kyes
        List<Integer> leftKeys = new ArrayList<>(keys.subList(0,middle));
        left.setKeys(leftKeys);
        //2.之前有m个children，现在有 m+1
        List<BTreeNode> leftChildren;
        if(children==null){
            leftChildren = null;
            left.setLeaf(true);
        }
        else {
            leftChildren = new ArrayList<>(children.subList(0,(m+1)/2-1));
        }
        left.setChildren(leftChildren);
        //3.parentEntry 左侧的序号不变
        ParentEntry leftparentEntry = new ParentEntry(this.parentEntry.getIndex(),this.parentEntry.getParent());
        left.setParentEntry(leftparentEntry);
        //left = new BTreeNode(leftKeys,this.m,leftChildren,leftparentEntry,middle+1);

        //右侧的BtreeNode
        BTreeNode right = new BTreeNode(m);
        //1.右侧的BtreeNode
        List<Integer> rightKeys = new ArrayList<>(keys.subList(middle+1,m));
        right.setKeys(rightKeys);
        //2.之前有m个children，现在有 m+1
        List<BTreeNode> rightChildren;
        if(children==null){
            rightChildren = null;
            right.setLeaf(true);
        }
        else{
            rightChildren = new ArrayList<>(children.subList((m+1)/2,m));
        }
        right.setChildren(rightChildren);
        //3.parentEntry 右侧的序号 +1
        ParentEntry rightparentEntry = new ParentEntry(this.parentEntry.getIndex()+1,this.parentEntry.getParent());
        right.setParentEntry(rightparentEntry);
        //right = new BTreeNode(rightKeys,this.m,rightChildren,rightparentEntry,middle+1);

        //往父节点中添加leftnode
        this.parentEntry.getParent().children.add(left.parentEntry.getIndex(),left);
        //往父节点中添加rightnode
        this.parentEntry.getParent().children.add(right.parentEntry.getIndex(),right);
        //把父节点中的冗余node删除
        this.parentEntry.getParent().children.remove(right.parentEntry.getIndex()+1);
       /* if(this.parentEntry.getParent().children.size()==right.parentEntry.getIndex()){
            //添加到末尾
            this.parentEntry.getParent().children.add(right);
        }
        else {
            this.parentEntry.getParent().children.add(right.parentEntry.getIndex(),right);
        }*/

        return newRoot;
    }

    private void setParentEntry(int i, BTreeNode parent) {
        this.parentEntry.setParent(parent);
        this.parentEntry.setIndex(i);
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

    public List<BTreeNode> getChildren() {
        return children;
    }

    public int getNum() {
        return num;
    }

    public boolean isLeaf() {
        return leaf;
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

    public void setNum(int num) {
        this.num = num;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
}
