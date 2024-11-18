public class AVLTree  <K extends Comparable<K>, V> extends BinarySearchTree<K,V> {
    
    // Creates an empty AVL tree with Binary Search Tree properties. 
    public AVLTree(){
        super();
    }

    /* Function to insert a key-value pair in an AVL tree. We can insert 
     * a duplicate key with a different value and replace the old value,
     * insert a key-value pair into an empty tree, or insert an entirely
     * new key in the tree.
     */ 
    public V insert(K key, V value){
        V answer = null;
        if (root == null) {
            root = new TreeNode<>(key, value);
            size++;
            return null;
        } 
        answer = super.find(key);
        if (answer == null) {
            size++;
        }
        root = recursiveHelper(key, value, root);
        return answer;
    }

    /* Helper to insert a key-value pair in an AVL tree. We find 
     * the place to insert our key if in an already-populated tree, 
     * or find the duplicate key and change its value, then update
     * the height to reflect the new ordering after balancing
     * the tree to match AVL definitions.
     */
    private TreeNode<K, V> recursiveHelper(K key, V value, TreeNode<K, V> root) {
        if (root == null) {
            return new TreeNode<>(key, value);
        }
        if (key.compareTo(root.key) > 0) {  // go right
            root.right = recursiveHelper(key, value, root.right);
        } else if (key.compareTo(root.key) == 0) {
            root.value = value;
        } else { // go left
            root.left = recursiveHelper(key, value, root.left);  
        }      
        root.updateHeight();
        return balance(root, key);
    }

    /* Balances the AVL tree after a new node is inserted. 
     * If there is a difference in the left and right subtrees
     * that exceeds the allowed AVL tree height difference of 1,
     * then we rotate right or left as many times we need to until
     * AVL property is restored. 
    */
    public TreeNode<K, V> balance(TreeNode<K, V> root, K key) {
        if (root == null) {
            return null;
        }
        int rightHeight = -1;
        int leftHeight = -1;

        // Check if the right child exists and get its height
        if (root.right != null) {
            rightHeight = root.right.height;
        }
        // Check if the left child exists and get its height
        if (root.left != null) {
            leftHeight = root.left.height;
        }
        int childHeightDif = rightHeight - leftHeight;
        if (childHeightDif >= 2) { // right is bigger than left
            if (key.compareTo(root.right.key) > 0) { // right of right      
                root = leftRotate(root);
                } else {
                    root.right = rightRotate(root.right); // left of right
                    root = leftRotate(root);
                }
        } else if (childHeightDif <= -2) { // left is bigger than right
            if (key.compareTo(root.left.key) < 0) { // left of left
                root = rightRotate(root);
            } else { // right of left
                root.left = leftRotate(root.left);
                root = rightRotate(root);        
            }
        }
        return root;
    }

    /* Helper method to rotate the given root and its 
     * subtrees right by 90 degrees. 
    */
    public TreeNode<K, V> rightRotate(TreeNode<K, V> root){
        if (root != null && root.left != null) {
            TreeNode<K, V> newRoot = root.left;
            root.left = newRoot.right;
            newRoot.right = root;
            root.updateHeight();
            newRoot.updateHeight();
            return newRoot;
        }
        return null;
    }
    /* Helper method to rotate the given root and its 
     * subtrees left by 90 degrees. 
    */
    public TreeNode<K, V> leftRotate(TreeNode<K, V> root){
        if (root != null && root.right != null) {
            TreeNode<K, V> newRoot = root.right;
            root.right = newRoot.left;
            newRoot.left = root;

            root.updateHeight();
            newRoot.updateHeight();
            return newRoot;
        }
        return null;
    }
}
