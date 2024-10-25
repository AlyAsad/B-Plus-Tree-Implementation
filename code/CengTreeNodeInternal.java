import java.util.ArrayList;

public class CengTreeNodeInternal extends CengTreeNode
{
    private ArrayList<Integer> keys;
    private ArrayList<CengTreeNode> children;

    public CengTreeNodeInternal(CengTreeNode parent)
    {
        super(parent);

        // DONE: Extra initializations, if necessary.
        keys = new ArrayList<>();
        children = new ArrayList<>();
        type = CengNodeType.Internal;
    }

    // GUI Methods - Do not modify
    public ArrayList<CengTreeNode> getAllChildren()
    {
        return this.children;
    }
    public Integer keyCount()
    {
        return this.keys.size();
    }
    public Integer keyAtIndex(Integer index)
    {
        if(index >= this.keyCount() || index < 0)
        {
            return -1;
        }
        else
        {
            return this.keys.get(index);
        }
    }

    // Extra Functions

    public void insertChild(CengTree t, CengTreeNode child, Integer key) {

        //inserting into internal node
        int i = 0;
        for (; i < keys.size(); i++) {
            if (keys.get(i) > key)
                break;
        }
        children.add(i, child);

        //inserted, now checking for order
        if (children.size() - 1 > order*2) {

            CengTreeNodeInternal parent = (CengTreeNodeInternal) getParent();
            CengTreeNodeInternal sibling = new CengTreeNodeInternal(parent);

            //creating both split siblings
            sibling.children = new ArrayList<>(children.subList(keys.size()/2 + 1, keys.size() + 1));
            children = new ArrayList<>(children.subList(0, keys.size()/2 + 1));

            Integer parentKey = keys.get(keys.size()/2);

            sibling.keys = new ArrayList<>(keys.subList(keys.size()/2 + 1, keys.size()));
            keys = new ArrayList<>(keys.subList(0, keys.size() / 2));

            //setting the parent of sibling's children to sibling
            for (i = 0; i < sibling.children.size(); i++)
                sibling.children.get(i).setParent(sibling);

            // curr node is not root, it has a parent
            if (parent != null) {
                parent.insertKey(parentKey);
                parent.insertChild(t, sibling, parentKey);
            }
            else { // if curr node is root
                t.root = new CengTreeNodeInternal(null);

                this.setParent(t.root);
                sibling.setParent(t.root);
                ((CengTreeNodeInternal)t.root).insertChild(t, this, this.keys.get(0));
                ((CengTreeNodeInternal)t.root).insertKey(parentKey);
                ((CengTreeNodeInternal)t.root).insertChild(t, sibling, parentKey);
            }

        }
    }

    public void insertKey(Integer val) {
        int i = 0;
        for (; i < keys.size(); i++) {
            if (keys.get(i) > val)
                break;
        }
        keys.add(i, val);
    }

    @Override public void addBook(CengTree t, CengBook book) {
        int i = 0;
        for (; i < keys.size(); i++) {
            if (keys.get(i) > book.getBookID())
                break;
        }
        children.get(i).addBook(t, book);
    }

    @Override public ArrayList<CengTreeNode> searchBook(Integer bookID) {

        int i = 0;
        for (; i < keys.size(); i++) {
            if (bookID < keys.get(i))
                break;
        }
        ArrayList<CengTreeNode> c = children.get(i).searchBook(bookID);
        if (c == null)
            return null;

        ArrayList<CengTreeNode> r = new ArrayList<>();
        r.add(this);
        r.addAll(c);
        return r;

    }

    @Override public void printTree(String indent) {

        System.out.print(indent);
        System.out.println("<index>");

        for (Integer curr : keys) {
            System.out.print(indent);
            System.out.println(curr);
        }
        System.out.print(indent);
        System.out.println("</index>");

        indent += "\t";
        for (CengTreeNode curr : children) {
            curr.printTree(indent);
        }

    }

}
