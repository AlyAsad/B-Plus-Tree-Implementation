import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CengTreeParser
{
    public static ArrayList<CengBook> parseBooksFromFile(String filename)
    {
        ArrayList<CengBook> bookList = new ArrayList<CengBook>();

        // You need to parse the input file in order to use GUI tables.
        // DONE: Parse the input file, and convert them into CengBooks

        try {

            FileReader f = new FileReader(filename);
            BufferedReader b = new BufferedReader(f);

            while (b.ready()) {
                String[] attribs = b.readLine().split("\\|");

                bookList.add(new CengBook(
                    Integer.parseInt(attribs[0]),
                    attribs[1],
                    attribs[2],
                    attribs[3]
                ));

            }

        } catch (IOException err) {
            err.printStackTrace();
        }

        return bookList;

    }

    public static void startParsingCommandLine() throws IOException
    {
        // DONE: Start listening and parsing command line -System.in-.
        // There are 4 commands:
        // 1) quit : End the app, gracefully. Print nothing, call nothing, just break off your command line loop.
        // 2) add : Parse and create the book, and call CengBookRunner.addBook(newlyCreatedBook).
        // 3) search : Parse the bookID, and call CengBookRunner.searchBook(bookID).
        // 4) print : Print the whole tree, call CengBookRunner.printTree().

        // Commands (quit, add, search, print) are case-insensitive.

        Scanner s = new Scanner(System.in);
        boolean run = true;

        while (run && s.hasNextLine()) {

            String[] attribs = s.nextLine().split("\\|");

            switch (attribs[0]) {

                case "quit":
                    run = false;
                    break;

                case "add":
                    CengBookRunner.addBook(new CengBook(
                        Integer.parseInt(attribs[1]),
                        attribs[2],
                        attribs[3],
                        attribs[4]
                    ));
                    break;

                case "search":
                    CengBookRunner.searchBook(Integer.parseInt(attribs[1]));
                    break;

                case "print":
                    CengBookRunner.printTree();
                    break;

            }

        }

    }
}
