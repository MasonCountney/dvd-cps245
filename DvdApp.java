import java.io.*;
import java.util.*;
import java.io.FileNotFoundException;
/**
 * Welcome to the DVD Store Management System.
 * This is the application class and contains the main method.
 * Most of this class deals with the various functions required
 * to be performed on the Lists of DVD and Customer type objects.
 *
 * @author Brent Manteuffel, Mason Countney, Ezekiel Krisp
 */
public class DvdApp {

	public static void main(String[] args){
		// TODO Auto-generated method stub
		Scanner console = new Scanner(System.in);

		String check;
		String DVDData = "DvdFile.txt";
		System.out.print("Correct Dvd database file? \"" + DVDData + "\" yes/no: ");

		check = console.nextLine();
		if(!check.equals("yes") && !check.equals("Yes")){
			System.out.print("Please enter a path for the Dvd database: ");
			DVDData = console.nextLine();
		}


		String CustomerData = "CustomerFile.txt";
		System.out.print("Correct Customer database file? \"" + CustomerData + "\" yes/no: ");

		check = console.nextLine();
		if(!check.equals("yes") && !check.equals("Yes")){
			System.out.print("Please enter a path for the Customer database: ");
			CustomerData = console.nextLine();
		}

		File DVDFile = new File(DVDData);
		File cusFile = new File(CustomerData);

		int selection = 0;
		char choice = 'y';
		ArrayList<DVD> stock = new ArrayList<DVD>();
		ArrayList<Customer> customers = new ArrayList<Customer>();

		fillDVDs(stock, DVDFile);
		fillCustomers(customers, cusFile);
		sortCustomerList(customers, 0, customers.size()-1);

		System.out.println("\nWelcome to The DVD Store\n");
		printMenu();
		while(choice == 'y') {
			System.out.print("\n[selection] -> ");
			selection = console.nextInt();
			console.nextLine();

			switch(selection) {
				case 1: //search stock for a DVD title
				{
					System.out.print("Please enter a title: ");
					String tempTitle = console.nextLine();
					int index = searchDVDList(stock, tempTitle);

					if(index == -1){
						System.out.println("Could not find dvd: " + tempTitle);
					} else {
						if(stock.get(index).isAvailable()){
							System.out.println(tempTitle + " found and available");
						} else {
							System.out.println(tempTitle + " found but not available");
						}
					}

				}	break;

				case 2: //find dvd and call its checkout() method
				{
					Customer temp = new Customer();
					System.out.print(" First name: ");
					temp.setFirstName(console.nextLine());

					System.out.print(" Last name: ");
					temp.setLastName(console.nextLine());

					System.out.print(" account number: ");
					temp.setAccountNumber(console.nextInt());

					int customerIndex = searchCustomerList(customers, temp);

					if(customerIndex == -1){
						System.out.println("No customer found.");
					} else {
						System.out.print("Enter dvd title: ");
						int dvdIndex = searchDVDList(stock, console.nextLine());

						if(dvdIndex == -1){
							System.out.println("No DVD found.");
						} else {
							if(stock.get(dvdIndex).isAvailable()){
								customers.get(customerIndex).rentDVD(stock.get(dvdIndex));
								stock.get(dvdIndex).checkOut();
							} else {
								System.out.println("Dvd is out of stock");
							}
						}
					}

				}	break;

				case 3: //find dvd and call its checkIn() method
				{
					Customer temp = new Customer();
					System.out.print(" First name: ");
					temp.setFirstName(console.nextLine());

					System.out.print(" Last name: ");
					temp.setLastName(console.nextLine());

					System.out.print(" account number: ");
					temp.setAccountNumber(console.nextInt());

					int customerIndex = searchCustomerList(customers, temp);

					if(customerIndex == -1){
						System.out.println("No customer found.");
					} else {
							System.out.print("Enter dvd title: ");
							customers.get(customerIndex).returnDVD(console.nextLine());
					}


				}		break;

				case 4:
				{//see if a DVD is in Stock
					System.out.print("Please enter a title: ");
					String temp = console.nextLine();

					int dvdIndex = searchDVDList(stock, temp);
					if(dvdIndex == -1){
						System.out.println("Dvd not found");
					} else {
						if(stock.get(dvdIndex).isAvailable()){
							System.out.println("Dvd is currently available");
							System.out.println(stock.get(dvdIndex).numberOfCopies()+ " number of copies available");
						} else {
							System.out.println("Dvd is out of stock");
						}
					}

				}		break;

				case 5: printTitles(stock); break;
				case 6: printDVDs(stock); break;
				case 7: printCustomers(customers); break;
				case 8: //print all the rented movies of one customer
					Customer temp = new Customer();
					System.out.print(" First name: ");
					temp.setFirstName(console.nextLine());

					System.out.print(" Last name: ");
					temp.setLastName(console.nextLine());

					System.out.print(" account number: ");
					temp.setAccountNumber(console.nextInt());

					int customerIndex = searchCustomerList(customers, temp);

					customers.get(customerIndex).rented();

					break;
				case 9: //print all the rented movies
					printAllRented(customers);
					break;
				case 10: printMenu(); break;
				case 100: choice = 'n';
					System.out.println("Thank you. Good-Bye.");
					break;
				default: System.out.println("Entry not recognized. Please try again");

			}
		}
	}// end main

	public static void printMenu() {
		System.out.println("Please make a selection");
		System.out.println("1: Check for DVD");
		System.out.println("2: Check out DVD");
		System.out.println("3: Return a DVD");
		System.out.println("4: Check if DVD is in stock");
		System.out.println("5: Print all DVD titles");
		System.out.println("6: Print all DVD's");
		System.out.println("7: Print all customers");
		System.out.println("8: Print list of rented DVD's for a customer");
		System.out.println("9: Print all rented DVDs");
		System.out.println("10: Print Menu.");
		System.out.println("100: Exit");
	}//end printMenu


	/**
	 * Fills the ArrayList with DVD data from a file inputed
	 * by the user in the main method.
	 * @param stock the ArrayList of DVDs.
	 * @param DVDFile the file that contains all of the DVD information.
	 * @throws FileNotFoundException if the file cannot be read.
	 */
	public static void fillDVDs(ArrayList<DVD> stock, File DVDFile){
		if(DVDFile.exists()) {
			if(DVDFile.isFile() && DVDFile.canRead()) {
				try {
					Scanner inputDVD = new Scanner(DVDFile);
					while (inputDVD.hasNextLine()) {
						stock.add(new DVD(inputDVD.nextLine(),		//title
											inputDVD.nextLine(),	//stars
											inputDVD.nextLine(),	//producer
											inputDVD.nextLine(),	//director
											inputDVD.nextLine(),	//production company
											inputDVD.nextInt()));	//copies
						if(inputDVD.hasNextLine())inputDVD.nextLine();
						//System.out.println("*********************");
					}
				}
				catch (FileNotFoundException e) {
					System.out.println("Error reading DVD database");
				}
			}
		}
	}//end fillDVDs


	/**
	 * Fills the ArrayList with Customer data from a file inputed
	 * by the user in the main method.
	 * @param customers the ArrayList of customers.
	 * @param cusFile the file that contains all of the Customer information.
	 * @throws FileNotFoundException if the file cannot be read.
	 */
	public static void fillCustomers(ArrayList<Customer> customers, File cusFile){
		DVD dvd;
		int counter = 0;
		if(cusFile.exists()) {
			if(cusFile.isFile() && cusFile.canRead())
				try {
					Scanner inputCus = new Scanner(cusFile);
					while (inputCus.hasNextLine()) {
						customers.add(new Customer(inputCus.nextLine(),		//First name
																			 inputCus.nextLine(),	//Last name
																			 inputCus.nextInt()));	//Account number
						boolean end = false;
						inputCus.nextLine(); 	// clear buffer
						while(inputCus.hasNextLine() && !end) {
							String temp = inputCus.nextLine();
							dvd = new DVD(temp);
							if(!temp.equals("***")) {
								customers.get(counter).rentDVD(dvd);
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
	}


	//printing methods
	public static void printDVDs(ArrayList<DVD> stock) {
		for(int i=0; i < stock.size(); ++i) {
			System.out.println(stock.get(i).getDVDInfo());
		}
	}//end printDVDs


	public static void printTitles(ArrayList<DVD> stock) {
		String titles = "";
		for(int i =0; i < stock.size(); ++i) {
			titles += (stock.get(i).getDVDTitle() + "\n");
		}
		System.out.println(titles);
	}//end printTitles


	public static void printCustomers(ArrayList<Customer> customers) {
		for(int i=0; i < customers.size(); ++i) {
			System.out.println(customers.get(i));
		}
	}//end printCustomers


	public static void printAllRented(ArrayList<Customer> customers) {
		for(int i=0; i<customers.size(); ++i) {
			System.out.println("-----" + customers.get(i).getName() + ":");
			System.out.println(customers.get(i).rented());
		}
	}//end printAllRented


	/**
	 * Finds the index of a Customer in the ArrayList.
	 * @param customers the ArrayList of Customers.
	 * @param name the name of the customer whose index you need.
	 * @return the index of the Customer in the ArrayList.
	 */
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


		public static int searchDVDList(ArrayList<DVD> stock, String key){

			for(int i = 0; i < stock.size(); ++i){
				stock.get(i).getDVDTitle().equals(key);
				return i;
			}

		 return -1;
	 } // end searchDVDList()


}//end class
