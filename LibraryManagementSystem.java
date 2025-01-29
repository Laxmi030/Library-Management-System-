import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

class Book {
    String title;
    String author;
    boolean isAvailable;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    // Borrow the book
    public void borrowBook() {
        if (isAvailable) {
            isAvailable = false;
            System.out.println("You borrowed: " + title);
        } else {
            System.out.println("Sorry, the book is currently unavailable.");
        }
    }

    // Return the book
    public void returnBook() {
        if (!isAvailable) {
            isAvailable = true;
            System.out.println("You returned: " + title);
        } else {
            System.out.println("This book was not borrowed.");
        }
    }

    @Override
    public String toString() {
        return title + " by " + author + (isAvailable ? " (Available)" : " (Borrowed)");
    }
}

public class LibraryManagementSystem {

    static ArrayList<Book> library = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadBooks();
        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add New Book");
            System.out.println("2. Display Books");
            System.out.println("3. Search Book");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    displayBooks();
                    break;
                case 3:
                    searchBook();
                    break;
                case 4:
                    borrowBook();
                    break;
                case 5:
                    returnBook();
                    break;
                case 6:
                    saveBooks();
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // Add a new book to the library
    public static void addBook() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        Book newBook = new Book(title, author);
        library.add(newBook);
        System.out.println("Book added: " + newBook);
    }

    // Display all books in the library
    public static void displayBooks() {
        if (library.isEmpty()) {
            System.out.println("No books available.");
        } else {
            for (int i = 0; i < library.size(); i++) {
                System.out.println((i + 1) + ". " + library.get(i));
            }
        }
    }

    // Search for a book by title or author
    public static void searchBook() {
        System.out.print("Enter book title or author: ");
        String query = scanner.nextLine().toLowerCase();
        boolean found = false;
        for (Book book : library) {
            if (book.title.toLowerCase().contains(query) || book.author.toLowerCase().contains(query)) {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No books found matching that search.");
        }
    }

    // Borrow a book from the library
    public static void borrowBook() {
        System.out.print("Enter the book number to borrow: ");
        int bookNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (bookNumber > 0 && bookNumber <= library.size()) {
            library.get(bookNumber - 1).borrowBook();
        } else {
            System.out.println("Invalid book number.");
        }
    }

    // Return a borrowed book
    public static void returnBook() {
        System.out.print("Enter the book number to return: ");
        int bookNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (bookNumber > 0 && bookNumber <= library.size()) {
            library.get(bookNumber - 1).returnBook();
        } else {
            System.out.println("Invalid book number.");
        }
    }

    // Load books from a file
    public static void loadBooks() {
        try {
            File file = new File("library.txt");
            if (file.exists()) {
                Scanner fileScanner = new Scanner(file);
                while (fileScanner.hasNextLine()) {
                    String title = fileScanner.nextLine();
                    String author = fileScanner.nextLine();
                    library.add(new Book(title, author));
                }
                fileScanner.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error loading books from file.");
        }
    }

    // Save books to a file
    public static void saveBooks() {
        try {
            FileWriter writer = new FileWriter("library.txt");
            for (Book book : library) {
                writer.write(book.title + "\n" + book.author + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving books to file.");
        }
    }
}
