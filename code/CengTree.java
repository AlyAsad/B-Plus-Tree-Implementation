import java.util.ArrayList;

public class CengTree
{
    public CengTreeNode root;
    // Any extra attributes...

    public CengTree(Integer order)
    {
        CengTreeNode.order = order;
        // DONE: Initialize the class
        root = new CengTreeNodeLeaf(null);

    }

    public void addBook(CengBook book)
    {
        // DONE: Insert Book to Tree
        root.addBook(this, book);
    }

    public ArrayList<CengTreeNode> searchBook(Integer bookID)
    {
        // DONE: Search within whole Tree, return visited nodes.
        // Return null if not found.

        ArrayList<CengTreeNode> arr = root.searchBook(bookID);
        if (arr == null) {
            System.out.println("Could not find " + bookID + ".");
            return null;
        }

        int i = 0;
        CengTreeNode curr = arr.get(i);

        String indent = "";
        while (curr.type == CengNodeType.Internal) {
            System.out.print(indent);
            System.out.println("<index>");

            int max = ((CengTreeNodeInternal) curr).keyCount();
            for (int j = 0; j < max; j++) {
                System.out.print(indent);
                System.out.println(((CengTreeNodeInternal) curr).keyAtIndex(j));
            }
            System.out.print(indent);
            System.out.println("</index>");
            indent += "\t";
            curr = arr.get(++i);
        }

        CengTreeNodeLeaf currL = (CengTreeNodeLeaf) curr;
        int max = (currL.books.size());
        for (int j = 0; j < max; j++) {
            CengBook book = currL.books.get(j);
            if (bookID.equals(book.getBookID())) {
                System.out.print(indent);
                System.out.println("<record>" + book.getBookID() + "|" + book.getBookTitle()
                        + "|" + book.getAuthor() + "|" + book.getGenre() + "</record>");
                break;
            }
        }

        return arr;

    }

    public void printTree()
    {
        // DONE: Print the whole tree to console
        root.printTree("");

    }

    // Any extra functions...
}
