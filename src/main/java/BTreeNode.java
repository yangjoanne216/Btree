import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

class BTreeNode {
    List<Integer> keys; //Key Value
    int m; // comme le dégre
    List<BTreeNode> children; // pointeurs vers les enfants
    ParentEntry parentEntry; // pointeurs vers le parent

    //boolean leaf; // vrai si il est feuille

    public BTreeNode(int m){
        this.m =m;
    }

    public BTreeNode(int m, List keys){
        this.m = m;
        this.keys = keys;
        this.children = null;

    }

    public BTreeNode(int m,List keys,List children){
        this.m = m;
        this.keys = keys;
        this.children = children;
        this.children=children;
    }

    public BTreeNode(int m, boolean leaf) {
        this.m = m;
        this.keys = new ArrayList<>(); // chaque node a au plus m-1 valeurs
        this.children = new ArrayList<>(); // chaque node a au plus m enfants
    }

    public BTreeNode(List<Integer> keys, int m, List<BTreeNode> children, ParentEntry parentEntry) {
        this.keys = keys;
        this.m = m;
        this.children = children;
        this.parentEntry = parentEntry;
    }

    public BTreeNode(List<Integer> keys, int m, List<BTreeNode> children, ParentEntry parentEntry, int num, boolean leaf) {
        this.keys = keys;
        this.m = m;
        this.children = children;
        this.parentEntry = parentEntry;
    }

    public BTreeNode() {

    }

    // 搜索键
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

        //le loop est fini,c'est à dire K>K_num ou K=K_(num-1)
        if(key == keys.get(keys.size()-1))
            return null;
        if(isLeaf())
            return this;//if is leaf, the research is unsuccessful
        return children.get(keys.size()).search(key);// if not , do the resarch in children num+1
    }

    public BTreeNode[] insert(int key){
        BTreeNode[]  nodes = new BTreeNode[3];
        // this node n'est pleine
        if(!(this.getKeys().size()>=m-1))
        {
            insertNonFull(key);
        }
        else{ //target est plein
           nodes = splitChild(key);
        }
        return nodes;
    }


    public void insertNonFull(int key) {
        //trouver une place pour insérer la valeur
        int i= 0;
        while(i<keys.size()&&keys.get(i)<key){
            i++;
        }
        keys.add(i,key);
    }


    /*public BTreeNode splitChild(int key) {
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
        if(this.parentEntry.getParent().keys.size()<m-1){
            //没有满，直接插入
            this.parentEntry.getParent().insert(keys.get(middle));
        }
        else{
            //满了，调用splitChild
           newRoot= this.parentEntry.getParent().splitChild(keys.get(middle));
        }

        //左侧的BtreeNode
        BTreeNode leftNode = new BTreeNode(m);
        //1.左侧的kyes
        List<Integer> leftKeys = new ArrayList<>(keys.subList(0,middle));
        leftNode.setKeys(leftKeys);
        //2.之前有m个children，现在有 m+1
        List<BTreeNode> leftChildren;
        if(children==null){
            leftChildren = null;
            leftNode.setLeaf(true);
        }
        else {
            leftChildren = new ArrayList<>(children.subList(0,(m+1)/2));
        }
        leftNode.setChildren(leftChildren);
        //3.parentEntry 左侧的序号不变
        ParentEntry leftParentEntry = new ParentEntry(this.parentEntry.getIndex(),this.parentEntry.getParent());
        leftNode.setParentEntry(leftParentEntry);
        //left = new BTreeNode(leftKeys,this.m,leftChildren,leftparentEntry,middle+1);

        //右侧的BtreeNode
        BTreeNode rightNode = new BTreeNode(m);
        //1.右侧的BtreeNode
        List<Integer> rightKeys = new ArrayList<>(keys.subList(middle+1,m));
        rightNode.setKeys(rightKeys);
        //2.之前有m个children，现在有 m+1
        List<BTreeNode> rightChildren;
        if(children==null){
            rightChildren = null;
            rightNode.setLeaf(true);
        }
        else{
            rightChildren = new ArrayList<>(children.subList((m+1)/2,m));
        }
        rightNode.setChildren(rightChildren);
        //3.parentEntry 右侧的序号 +1
        ParentEntry rightparentEntry = new ParentEntry(this.parentEntry.getIndex()+1,this.parentEntry.getParent());
        rightNode.setParentEntry(rightparentEntry);
        //right = new BTreeNode(rightKeys,this.m,rightChildren,rightparentEntry,middle+1);

        //往父节点中添加leftnode
        this.parentEntry.getParent().children.add(leftNode.parentEntry.getIndex(),leftNode);
        //往父节点中添加rightnode
        this.parentEntry.getParent().children.add(rightNode.parentEntry.getIndex(),rightNode);
        //把父节点中的冗余node删除
        this.parentEntry.getParent().children.remove(rightNode.parentEntry.getIndex()+1);

        return newRoot;
    }*/

    public void insertFull(int key){
        //trouver une place pour insérer la valeur
        int i= 0;
        while(i<keys.size()&&keys.get(i)<key){
            i++;
        }
        //现在数组的长度是比 m-1要大一位
        keys.add(i,key);
    }

    public BTreeNode initialRoot(){
        BTreeNode newRoot = null;
        //return 一个父节点加1，就是要changeRoot了
        List rootKey = new ArrayList<Integer>();
        //children要等后面分成两个小孩之后再慢慢的去set
        newRoot = new BTreeNode(rootKey, m,new ArrayList<>(),new ParentEntry());
        //return newRoot
        this.setParentEntry(0,newRoot);
        newRoot.children.add(this);
        return newRoot;
    }


    public BTreeNode initialLeftNode(){
        //左侧的BtreeNode
        BTreeNode leftNode = new BTreeNode(m);
        //1.左侧的kyes
        List<Integer> leftKeys = new ArrayList<>(keys.subList(0,m/2));
        leftNode.setKeys(leftKeys);
        //2.之前有m个children，现在有 m+1
        List<BTreeNode> leftChildren;
        if(children==null){
            leftChildren = null;
        }
        else {
            leftChildren = new ArrayList<>(children.subList(0,(m+1)/2));
        }
        leftNode.setChildren(leftChildren);
        //3.parentEntry 左侧的序号不变
        ParentEntry leftParentEntry = new ParentEntry(this.parentEntry.getIndex(),this.parentEntry.getParent());
        leftNode.setParentEntry(leftParentEntry);
        return leftNode;
    }

    public BTreeNode initialRightNode(){
        BTreeNode rightNode = new BTreeNode(m);
        //1.右侧的BtreeNode
        List<Integer> rightKeys = new ArrayList<>(keys.subList(m/2+1,keys.size()));
        rightNode.setKeys(rightKeys);
        //2.之前有m个children，现在有 m+1
        List<BTreeNode> rightChildren;
        if(children==null){
            rightChildren = null;
        }
        else{
            rightChildren = new ArrayList<>(children.subList((m+1)/2,m));
        }
        rightNode.setChildren(rightChildren);
        //3.parentEntry 右侧的序号 +1
        ParentEntry rightparentEntry = new ParentEntry(this.parentEntry.getIndex()+1,this.parentEntry.getParent());
        rightNode.setParentEntry(rightparentEntry);
        return rightNode;
    }

    public BTreeNode[] splitChild(int key) {
        //返回值，分别返回 是否有newRacine，leftNode，以及 rightNode
        BTreeNode[] nodes  =new BTreeNode[3];
        //1.找到中间的那个数，
        insertFull(key);

        //2.把中间的那个数插入父节点
        //2.1 判断如果父节点为null，就说明是一个root,要开始设置新的root了
        if(this.parentEntry.getParent()==null){
            nodes[0] = initialRoot();
        }

        //2.2把中间的数插入到父节点中
        if(this.parentEntry.getParent().keys.size()<m-1){
            //没有满，直接插入
            this.parentEntry.getParent().insertFull(keys.get(m/2));
        }
        else{
            //满了，调用splitChild
            nodes = this.parentEntry.getParent().splitChild(keys.get(m/2));
        }

        //创建左侧的BTreeNode
        nodes[1] = initialLeftNode();
        //创建右侧的BTreeNode
        nodes[2] = initialRightNode();



        //现在left和right节点是原来parent新的子节点了，而之前的老节点从父节点中删除
        //处理父节点与左node和右node的关系

        //往父节点中添加leftnode
        this.parentEntry.getParent().children.add(nodes[1].parentEntry.getIndex(),nodes[1]);
        //往父节点中添加rightnode
        this.parentEntry.getParent().children.add(nodes[2].parentEntry.getIndex(),nodes[2]);
        //把父节点中的冗余node删除 就是那个冗余node(多一个key的node)
        this.parentEntry.getParent().children.remove(nodes[2].parentEntry.getIndex()+1);

        return nodes;
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
