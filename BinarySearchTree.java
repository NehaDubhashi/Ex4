import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree<K extends Comparable<K>, V> implements OrderedDeletelessDictionary<K,V> {

    // Fields were made public to assist with autograding
    // Do not change the visibility of these fields :)
    public TreeNode<K, V> root;
    public int size;

    public BinarySearchTree(){
        root = null;
        size = 0;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public V find(K key){
        if(root == null){
            throw new IllegalStateException();
        }
        return find(key, root);
    }

    //recursive helper
    private V find(K key, TreeNode<K,V> curr){
        if(curr == null){
            return null;
        }
        int currMinusNew = curr.key.compareTo(key); // negative if curr is smaller
        if(curr.key.equals(key)){
            return curr.value;
        } else if(currMinusNew < 0){
            // curr is smaller so key must be to the right.
            return find(key, curr.right);
        } else{
            return find(key, curr.left);
        }
    }

    // Returns the smallest key which is greater than the given key
    // Returns null if the given key is greater than or equal to the largest
    // key present
    public K findNextKey(K key){
        return findNextKeyHelper(key, root);
    }

    // Helper method to find the smallest key which is greater than the given key.
    // Returns null if the given key is greater than or equal to the largest
    // key present.
    public K findNextKeyHelper(K key, TreeNode<K,V> root) {
        if (root == null){
            return null;
        }
         else if (root.key.compareTo(key) == 0) {
            if (root.right != null) {
                TreeNode<K, V> smallestMax = root.right; // go right once
                while (smallestMax.left != null) { // keep going left till smallest is found
                    smallestMax = smallestMax.left;
                } 
                return smallestMax.key;
            } else {
                return null;
            }
        } else if (key.compareTo(root.key) > 0) { // right subtree
            return findNextKeyHelper(key, root.right);
        } else if (key.compareTo(root.key) < 0) { // left subtree
            K leftResult = findNextKeyHelper(key, root.left);
            return (leftResult != null) ? leftResult : root.key; // return leftResult if it exists, else root.key
        }
        return null;
    }

    // Returns the largest key which is less than the given key
    // Returns null if the given key is less than or equal to the smallest
    // key present
    public K findPrevKey(K key){
        return findPrevKeyHelper(key, root);
    }
    // Helper method to find the largest key which is less
    // than the given key. Returns null if the given key is 
    // less than or equal to the smallest key present.
    public K findPrevKeyHelper(K key, TreeNode<K,V> root) {
        if (root == null){
            return null;
        }
         else if (root.key.compareTo(key) == 0) {
            if (root.left != null) {
                TreeNode<K, V> largestMin = root.left; // go left once
                while (largestMin.right != null) { // keep going right till smallest is found
                    largestMin = largestMin.right;
                } 
                return largestMin.key;
            } else {
                return null;
            }
        } else if (key.compareTo(root.key) < 0) { // left subtree
            return findPrevKeyHelper(key, root.left);
        } else if (key.compareTo(root.key) > 0) { // right subtree
            K rightResult = findPrevKeyHelper(key, root.right);
            return (rightResult != null) ? rightResult : root.key; // return rightResult if it exists, else root.key
        }
        return null;
    }

    public V insert(K key, V value){
        V answer = find(key, root);
        if(answer == null){
            size++;
        }
        root = insert(key, value, root);
        root.updateHeight();
        return answer; 
    }

    private TreeNode<K,V> insert(K key, V value, TreeNode<K,V> curr){
        if (curr == null){
            return new TreeNode<>(key, value);
        }
        int currMinusNew = curr.key.compareTo(key);
        if(currMinusNew==0){
            curr.value = value;
        } else if(currMinusNew < 0){
            curr.right = insert(key, value, curr.right);
        } else{
            curr.left = insert(key, value, curr.left);
        }
        curr.updateHeight();
        return curr;
    }

    public List<V> getValues(){
        List<V> values = new ArrayList<>();
        inorderFillValues(values, root);
        return values;
    }

    private void inorderFillValues(List<V> values, TreeNode<K,V> curr){
        if(curr != null){
            inorderFillValues(values, curr.left);
            values.add(curr.value);
            inorderFillValues(values, curr.right);
        }
    }

    public List<K> getKeys(){
        List<K> keys = new ArrayList<K>();
        inorderFillKeys(keys, root);
        return keys;
    }

    private void inorderFillKeys(List<K> keys, TreeNode<K,V> curr){
        if(curr != null){
            inorderFillKeys(keys, curr.left);
            keys.add(curr.key);
            inorderFillKeys(keys, curr.right);
        }
    }

    public void printSideways() {
        printSideways(root, 0);
    }

    private void printSideways(TreeNode<K,V> root, int level) {
        if (root != null) {
            printSideways(root.right, level + 1);
            for (int i = 0; i < level; i++) {
                System.out.print("    ");
            }
            System.out.println(root.key);
            printSideways(root.left, level + 1);
        }
    }
}
