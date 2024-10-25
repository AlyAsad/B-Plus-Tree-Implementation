import java.lang.reflect.Array;
import java.util.ArrayList;

public class CengTreeNodeLeaf extends CengTreeNode
{
    protected ArrayList<CengBook> books;
    // DONE: Any extra attributes

    public CengTreeNodeLeaf(CengTreeNode parent)
    {
        super(parent);

        // DONE: Extra initializations
        books = new ArrayList<>();
        type = CengNodeType.Leaf;
    }

    // GUI Methods - Do not modify
    public int bookCount()
    {
        return books.size();
    }
    public Integer bookKeyAtIndex(Integer index)
    {
        if(index >= this.bookCount()) {
            return -1;
        } else {
            CengBook book = this.books.get(index);

            return book.getBookID();
        }
    }

    // Extra Functions


    @Override public void addBook(CengTree t, CengBook book) {

        //inserting into the leaf's array
        int i = 0;
        for (; i < books.size(); i++) {
            if (books.get(i).getBookID() > book.getBookID())
                break;
        }
        books.add(i, book);

        //inserted, now checking for order
        if (books.size() > order*2) {

            CengTreeNodeInternal parent = (CengTreeNodeInternal) getParent();
            CengTreeNodeLeaf sibling = new CengTreeNodeLeaf(parent);

            //creating both split siblings
            sibling.books = new ArrayList<>(books.subList(books.size()/2, books.size()));
            books = new ArrayList<>(books.subList(0, books.size()/2));

            // leaf is not root, leaf has a parent
            if (parent != null) {
                parent.insertKey(sibling.books.get(0).getBookID());
                parent.insertChild(t, sibling, sibling.books.get(0).getBookID());
            }
            else { // if leaf is root
                t.root = new CengTreeNodeInternal(null);

                this.setParent(t.root);
                sibling.setParent(t.root);
                ((CengTreeNodeInternal)t.root).insertChild(t, this, this.books.get(0).getBookID());
                ((CengTreeNodeInternal)t.root).insertKey(sibling.books.get(0).getBookID());
                ((CengTreeNodeInternal)t.root).insertChild(t, sibling, sibling.books.get(0).getBookID());
            }

        }

    }

    @Override public ArrayList<CengTreeNode> searchBook(Integer bookID) {
        if (books.isEmpty())
            return null;

        for (CengBook book : books) {
            if (bookID.equals(book.getBookID())) {
                ArrayList<CengTreeNode> r = new ArrayList<>();
                r.add(this);
                return r;
            }
        }

        return null;

    }

    @Override public void printTree(String indent) {

        // if tree is empty
        if (books.isEmpty())
            return;

        System.out.print(indent);
        System.out.println("<data>");

        for (CengBook book : books) {
            System.out.print(indent);
            System.out.println("<record>" + book.getBookID() + "|" + book.getBookTitle()
                    + "|" + book.getAuthor() + "|" + book.getGenre() + "</record>");
        }
        System.out.print(indent);
        System.out.println("</data>");

    }

}
