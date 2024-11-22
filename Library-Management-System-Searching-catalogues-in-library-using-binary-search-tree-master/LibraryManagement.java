import java.util.*;

class Book {
    String name;
    int totalQuantity;
    int availableQuantity;

    Book(String name, int quantity) {
        this.name = name;
        this.totalQuantity = quantity;
        this.availableQuantity = quantity;
    }
}

class Student {
    String name;
    int id_no;
    int book_no; // Number of books borrowed
    String book1; // First book borrowed
    String book2; // Second book borrowed

    Student(String name, int id_no) {
        this.name = name;
        this.id_no = id_no;
        this.book_no = 0;
        this.book1 = null;
        this.book2 = null;
    }
}

public class LibraryManagement {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        HashMap<String, Book> books = new HashMap<>();
        ArrayList<Student> students = new ArrayList<>();

        // Adding some books and students
        books.put("Harry Potter", new Book("Harry Potter", 5));
        books.put("The Alchemist", new Book("The Alchemist", 3));
        books.put("Game of Thrones", new Book("Game of Thrones", 4));

        students.add(new Student("Rajvi", 1741078));
        students.add(new Student("Aarav", 1741079));

        boolean exit = false;

        while (!exit) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Librarian Login");
            System.out.println("2. User Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = input.nextInt();

            switch (choice) {
                case 1: // Librarian Login
                    boolean librarianExit = false;
                    while (!librarianExit) {
                        System.out.println("\nLibrarian Panel");
                        System.out.println("1. Add Book");
                        System.out.println("2. View Books");
                        System.out.println("3. Exit");
                        System.out.print("Enter your choice: ");
                        int librarianChoice = input.nextInt();

                        switch (librarianChoice) {
                            case 1: // Add Book
                                System.out.print("Enter book name: ");
                                input.nextLine(); // Consume newline
                                String bookName = input.nextLine();
                                System.out.print("Enter quantity: ");
                                int quantity = input.nextInt();

                                if (books.containsKey(bookName)) {
                                    Book existingBook = books.get(bookName);
                                    existingBook.totalQuantity += quantity;
                                    existingBook.availableQuantity += quantity;
                                } else {
                                    books.put(bookName, new Book(bookName, quantity));
                                }
                                System.out.println("Book added successfully.");
                                break;

                            case 2: // View Books
                                System.out.println("\nAvailable Books:");
                                for (Book book : books.values()) {
                                    System.out.println(book.name + " (Total: " + book.totalQuantity + ", Available: " + book.availableQuantity + ")");
                                }
                                break;

                            case 3: // Exit
                                librarianExit = true;
                                break;

                            default:
                                System.out.println("Invalid choice. Try again.");
                        }
                    }
                    break;

                case 2: // User Login
                    System.out.print("Enter your ID: ");
                    int userId = input.nextInt();
                    Student currentUser = null;

                    // Validate user
                    for (Student student : students) {
                        if (student.id_no == userId) {
                            currentUser = student;
                            break;
                        }
                    }

                    if (currentUser == null) {
                        System.out.println("Invalid User ID. Try again.");
                        break;
                    }

                    boolean userExit = false;
                    while (!userExit) {
                        System.out.println("\nWelcome, " + currentUser.name + "!");
                        System.out.println("1. Borrow Book");
                        System.out.println("2. Return Book");
                        System.out.println("3. View Borrowed Books");
                        System.out.println("4. Exit");
                        System.out.print("Enter your choice: ");
                        int userChoice = input.nextInt();

                        switch (userChoice) {
                            case 1: // Borrow Book
                                if (currentUser.book_no >= 2) {
                                    System.out.println("You have already borrowed the maximum number of books (2).");
                                    break;
                                }
                                System.out.print("Enter book name to borrow: ");
                                input.nextLine(); // Consume newline
                                String borrowBookName = input.nextLine();
                                if (books.containsKey(borrowBookName)) {
                                    Book book = books.get(borrowBookName);
                                    if (book.availableQuantity > 0) {
                                        book.availableQuantity--;
                                        currentUser.book_no++;
                                        if (currentUser.book1 == null) {
                                            currentUser.book1 = borrowBookName;
                                        } else if (currentUser.book2 == null) {
                                            currentUser.book2 = borrowBookName;
                                        }
                                        System.out.println("Book borrowed successfully!");
                                    } else {
                                        System.out.println("Sorry, the book is currently unavailable.");
                                    }
                                } else {
                                    System.out.println("Book not found in the library.");
                                }
                                break;

                            case 2: // Return Book
                                System.out.println("Books you have borrowed:");
                                if (currentUser.book1 != null) System.out.println("1. " + currentUser.book1);
                                if (currentUser.book2 != null) System.out.println("2. " + currentUser.book2);

                                if (currentUser.book1 == null && currentUser.book2 == null) {
                                    System.out.println("You have not borrowed any books.");
                                    break;
                                }

                                System.out.print("Enter the name of the book to return: ");
                                input.nextLine(); // Consume newline
                                String returnBookName = input.nextLine();

                                if (returnBookName.equalsIgnoreCase(currentUser.book1)) {
                                    books.get(returnBookName).availableQuantity++;
                                    currentUser.book1 = null;
                                    currentUser.book_no--;
                                    System.out.println("Book returned successfully.");
                                } else if (returnBookName.equalsIgnoreCase(currentUser.book2)) {
                                    books.get(returnBookName).availableQuantity++;
                                    currentUser.book2 = null;
                                    currentUser.book_no--;
                                    System.out.println("Book returned successfully.");
                                } else {
                                    System.out.println("You did not borrow this book.");
                                }
                                break;

                            case 3: // View Borrowed Books
                                System.out.println("Books you have borrowed:");
                                if (currentUser.book1 != null) {
                                    System.out.println("- " + currentUser.book1);
                                }
                                if (currentUser.book2 != null) {
                                    System.out.println("- " + currentUser.book2);
                                }
                                if (currentUser.book1 == null && currentUser.book2 == null) {
                                    System.out.println("You have not borrowed any books.");
                                }
                                break;

                            case 4: // Exit
                                userExit = true;
                                break;

                            default:
                                System.out.println("Invalid choice. Try again.");
                        }
                    }
                    break;

                case 3: // Exit
                    exit = true;
                    System.out.println("Exiting the system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        input.close();
    }
}
