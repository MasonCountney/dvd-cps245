import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.io.*;

/*
 * +--------------------------------------------------------------------------------------+
 * |  Updates to main() and additional info                                               |
 * +--------------------------------------------------------------------------------------+
 * | | | | | | |                                                                          |
 * | | | | | | + customers:       main() now holds an ArrayList of class Customer         |
 * | | | | | +-- rented movies:   each element (customer) has list of rented movies       |
 * | | | | +---- sorting:         sorts customers - implementation of quicksort           |
 * | | | +------ searching:       search for customer - implementation of binary search   |
 * | | +-------- Try/catch:       Added try catch blocks to customeFile reading           |
 * | +---------- Save file:       New save customer list function, used try catch         |
 * +--------------------------------------------------------------------------------------+
 *
 */

public class main{

  public static void main(String args[]) throws FileNotFoundException {
    Scanner input = new Scanner(System.in);

    ArrayList<Customer> customers = new ArrayList<Customer>();

    /* +-------------------------------------+
     * | Reads customer file, creates list,  |
     * | populates rented movies in customer |
     * +-------------------------------------+
     */
     File file = new File("CustomerList.txt");
     DVD dvd;
     int counter = 0;

     if (file.exists()) {
      if (file.isFile() && file.canRead())
        try {
          Scanner customerFile = new Scanner(file);
          while (customerFile.hasNextLine()) {
            customers.add(new Customer(customerFile.nextLine(),     // First name
                                       customerFile.nextLine(),     // Last name
                                       customerFile.nextInt()));    // Account number

            boolean end = false;
            customerFile.nextLine();    // clear buffer

            while(customerFile.hasNextLine() && !end){
              String temp = customerFile.nextLine();   /*  - Read next lines containing list of   */
              dvd = new DVD(temp);                     /*    rented movies by each Customer.      */
              if(!temp.equals("***")){                 /*  - End if delimiter "***"               */
                customers.get(counter).rentDVD(dvd);   /*  - Populate internal list of dvds       */
              } else {
                end = true;
              }
            }
            counter++;
          }
        } catch (FileNotFoundException e) {
          System.out.println("Error reading customer database");
        }
      }


    /* +-------------------------------------+
     * | Testing printing/sorting/searching  |
     * +-------------------------------------+
     */
    printCustomerList(customers);
    sortCustomerList(customers, 0, customers.size()-1);
    System.out.println("********************************");

    printCustomerList(customers);

    System.out.println("********************************");


    /* +-------------------------------------+
     * | Testing for search function         |
     * +-------------------------------------+
     *

    Customer temp = new Customer();
    System.out.print(" First name: ");
    temp.setFirstName(input.nextLine());

    System.out.print(" Last name: ");
    temp.setLastName(input.nextLine());

    System.out.print(" account number: ");
    temp.setAccountNumber(input.nextInt());
    //handle -1, no entry found;
    System.out.print("Found at element: ");
    System.out.println(searchCustomerList(customers, temp));
*/

    saveCustomerList(customers, "test.txt");

    input.close();
  } // end main()


  public static void printCustomerList(ArrayList<Customer> list){
    for(int i = 0; i < list.size(); ++i){
      System.out.println(list.get(i));
      //System.out.println(list.get(i).rented());
    }
  } // end printCustomerList()


  public static void sortCustomerList(ArrayList<Customer> list, int start, int end){

    int i = start;
    int j = end;
    Customer pivot = new Customer();
    pivot = list.get(i + (j-i)/2);

    while (i <= j) {
      while (list.get(i).compareTo(pivot) < 0)
        ++i;
      while (list.get(j).compareTo(pivot) > 0)
        --j;

      if (i <= j) {
        Collections.swap(list, i, j);
        ++i;
        --j;
      }
    }

    if (start < j)
      sortCustomerList(list, start, j);
    if (i < end)
      sortCustomerList(list, i, end);
    } // end sortCustomerList()


  public static int searchCustomerList(ArrayList<Customer> list, Customer key){
    int low = 0;
    int high = list.size() - 1;
    int mid;

    while (low <= high) {
        mid = (low + high) / 2;
        if (list.get(mid).compareTo(key) > 0)
            high = mid - 1;
        else if (list.get(mid).compareTo(key) < 0)
            low = mid + 1;
        else
            return mid;
    }
    return -1;
  } // end searchCustomerList()


  public static void saveCustomerList(ArrayList<Customer> list, String filePath){
    try{
      PrintWriter writer = new PrintWriter(filePath, "UTF-8");
      for(int i = 0; i < list.size(); ++i){
        writer.println(list.get(i).getFirstName());
        writer.println(list.get(i).getLastName());
        writer.println(list.get(i).getAccountNumber());
        writer.println(list.get(i).rented());
        writer.println("***");
      }

      writer.close();

    } catch(IOException e) {
      System.out.println("Error writing to file: " + filePath);
    }
  } // end saveCustomerList()


} // end main class
