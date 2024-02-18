import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class BTreeTest {

    @Test
    public void testInsertAndSearch() {
        BTree tree = new BTree(5); // 假设这是最小度数为3的B树
        tree.insert(10);
        tree.insert(20);
        tree.insert(5);
        tree.insert(6);
        tree.insert(12);
        tree.insert(30);
        tree.insert(7);
        tree.insert(17);



        // 假设我们有一个可以返回排序后的所有键的方法
       // List<Integer> keys = tree.traverse();
        //List<Integer> expectedKeys = List.of(5, 6, 7, 10, 12, 17, 20, 30);
        //assertEquals(expectedKeys, keys, "The traverse method should return all keys in sorted order");
    }
}
