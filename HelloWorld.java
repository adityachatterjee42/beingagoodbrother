//assumption: books are issued for two weeks at a time
//assumption: students issue books one at a time
import java.util.*;
import java.text.SimpleDateFormat;
public class HelloWorld{
    static Book[] books = {
            new Book("The Adventures of Tom Sawyer", "Classic Fiction"), 
            new Book("The Adventures of Huckleberry Finn", "Classic Fiction"),
            new Book("Tintin in Tibet", "Comics"),
            new Book("Tintin in America", "Comics")
        };
    public static void main(String []args){
        Scanner in = new Scanner(System.in);
        while(true){
            System.out.println("What would you like to do?");
            System.out.println("1. Borrow a book");
            System.out.println("2. Return a book and calculate fine");
            System.out.println("3. Exit this program");
            int option = Integer.parseInt(in.nextLine());
            switch(option){
                case 1: {
                    System.out.println("Enter student name:");
                    String name = in.nextLine();
                    System.out.println("Enter name of book to be issued:");
                    String bookName = in.nextLine();
                    System.out.println("Enter date issued (yyyy-MM-dd):");
                    String date = in.nextLine();
                    Book book = isBookAvailable(bookName);
                    if(book==null){
                        System.out.println("Sorry, this book is either unavailable or doesn't exist!");
                    }
                    else{
                        book.issueBook(date, name);
                        System.out.println(book.name+" has been issued to "+name+".");
                    }
                    break;
                }
                case 2: {
                    System.out.println("Enter student name:");
                    String name = in.nextLine();
                    System.out.println("Enter date returned (yyyy-MM-dd):");
                    String date = in.nextLine();
                    Book book = bookIssuedByStudent(name);
                    if(book==null){
                        System.out.println("That student doesn't seem to have issued a book!");
                    }
                    else{
                        book.returnBook(date);
                    }
                    break;
                }
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Looks like you didn't choose one of the options above!");
            }
        }
    }
    static Book isBookAvailable(String bookName){
        System.out.println(bookName);
        Book book = null;
        for(int i=0; i<books.length; i++){
            System.out.println(books[i].name);
            if(books[i].name==bookName && books[i].issuedBy==null) book = books[i];
        }
        return book;
    }
    static Book bookIssuedByStudent(String studentName){
        Book book = null;
        for(int i=0; i<books.length; i++){
            if(books[i].issuedBy==studentName) book = books[i];
        }
        return book;
    }
    
}

class Book {
    String name;
    String genre;
    Date dateIssued;
    String issuedBy;
    Book(String name, String genre){
        this.name = name;
        this.genre = genre;
        this.issuedBy=null;
    }
    void issueBook(String d, String student){
        this.issuedBy = student;
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try{
        this.dateIssued = simpleDateFormat.parse(d);
        }catch(Exception e){
            System.out.println("Invalid date entered!");
        }
    }
    long returnBook(String d){
        Date dateReturned = new Date();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try{
        dateReturned = simpleDateFormat.parse(d);
        }catch(Exception e){
            System.out.println("Invalid date entered!");
        }
        long daysSinceIssued = (dateReturned.getTime() - dateIssued.getTime())/(24 * 60 * 60 * 1000);
        long fine = 0;
        if(daysSinceIssued>14){
            System.out.println("Book returned late!");
            fine = 10*(daysSinceIssued-14);
        }
        else{
            System.out.println("Book returned on time.");
        }
        this.dateIssued = null;
        this.issuedBy = null;
        System.out.println("Fine due: "+fine);
        return fine;
    }
}