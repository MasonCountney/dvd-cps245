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

	public static void main(String[] args) throws FileNotFoundException {
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

		System.out.println("\nWelcome to The DVD Store\n");
		printMenu();
		while(choice == 'y') {
			System.out.print("\n[select] % ");
			selection = console.nextInt();
			console.nextLine();

			switch(selection) {
				case 1: //search stock for a DVD title
					System.out.print("Please enter a title: ");
					String tempTitle = console.nextLine();
					if(haveDVD(tempTitle, stock)) {
						System.out.println("We carry that title");

						if(!stock.get(findDVD(stock, tempTitle)).isAvailable() ) {
							System.out.println("But it is currently unavailable");
						} else {
							System.out.print("Sorry we dont't carry that title. ");
						}
					} else {
						System.out.print("Sorry we dont't carry that title. ");
					}
					break;
				case 2: //find dvd and call its checkout() method
					System.out.print("Please enter the customer's name: ");
					String tmpName = console.nextLine();

					if(haveCustomer(tmpName, customers)) {
						System.out.print("Please enter the title of the DVD to be checked out: ");
						String tmpTitle = console.nextLine();

						if(haveDVD(tmpTitle, stock)) {
							DVD tmpDVD = stock.get(findDVD(stock, tmpTitle));

							if(tmpDVD.isAvailable()) {
								tmpDVD.checkOut();
								customers.get(findCustomer(customers, tmpName)).rentDVD(tmpDVD);
								System.out.println("DVD checked out successfully ");
							} else {
								System.out.println("Sorry, DVD is currently unavailable.");
							}
						} else {
								System.out.println("Error has occured. Please try again.");
							}
						}

					break;
				case 3: //find dvd and call its checkIn() method
					System.out.print("Please enter the customer's name: ");
					String name = console.nextLine();

					if(haveCustomer(name, customers)) {
						System.out.print("Please enter the DVD's title: ");
						String title = console.nextLine();

						if(haveDVD(title, stock)) {
							if(returnDVD(title, name, stock, customers)) {
								System.out.println("DVD returned successfully");
							}	else {
								System.out.println("Error has occured. Please try again.");
							}
						} else {
							System.out.println("Error has occured. Please try again.");

							}
						} else {
								System.out.println("Error has occured. Please try again.");
						}

					break;
				case 4: //see if a DVD is in Stock
					System.out.println("Please enter a title.");
					String tmpTitle2 = console.nextLine();

					if(haveDVD(tmpTitle2, stock)) {
						DVD tmpDVD = stock.get(findDVD(stock, tmpTitle2));
						if(tmpDVD.isAvailable()) {
							System.out.println("This title is in stock");
						} else
							System.out.println("Sorry, this title is out of stock.");
						} else
							System.out.println("Error has occured. Please try again.");


						break;
				case 5: printTitles(stock); break;
				case 6: printDVDs(stock); break;
				case 7: printCustomers(customers); break;
				case 8: //print all the rented movies of one customer
					System.out.println("Please enter a customer's name");
					String tmpName2 = console.nextLine();
					if(haveCustomer(tmpName2, customers)) {
						printRented(customers, tmpName2);
					} else {
						System.out.println("Sorry, that customer does not have an account here.");
					}
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
	 * Checks to see if a DVD is present in the list.
	 * @param title the title of the DVD to be searched for.
	 * @param stock the ArrayList of all DVDs the store has.
	 * @return true if the store has the DVD.
	 */
	public static boolean haveDVD(String title, ArrayList<DVD> stock) {
		boolean has = true;
		for(int i=0; i < stock.size(); i++){
			if (!stock.get(i).getDVDTitle().equals(title))
				has =  false;
			else {
				has =  true;
				break;
			}
        }
		return has;
	}//end haveDvd
	/**
	 * Checks to see if a Customer has an account at the store.
	 * @param name The name of the Customer to be searched for.
	 * @param customers The ArrayList of all customers that have accounts at the store.
	 * @return true if the customer has an account.
	 */
	public static boolean haveCustomer(String name, ArrayList<Customer> customers) {
		boolean has = true;
		for(int i=0; i < customers.size(); i++) {
			if(!customers.get(i).getName().equals(name))
				has = false;
			else {
				has = true;
				break;
			}
		}
		return has;
	}//end haveCustomer
	/**
	 * Returns a DVD to the store by incrementing the number of copies by one and
	 * removing the title from the list of rented DVDs of the customer.
	 * @param title the title of the DVD to return.
	 * @param name the name of the customer returning the DVD.
	 * @param stock the ArrayList of DVDs
	 * @param customers the ArrayList of Customers
	 * @return true if the DVD is returned successfully.
	 */
	public static boolean returnDVD(String title, String name, ArrayList<DVD> stock, ArrayList<Customer> customers) {
		int i = findDVD(stock, title);
		stock.get(i).checkOut();
		int j = findCustomer(customers, name);
		customers.get(j).returnDVD(title);
		return true;
	}//end returnDVD
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
							}else {
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
		for(int i=0; i<stock.size(); ++i) {
			System.out.println(stock.get(i).getDVDInfo());
		}
	}//end printDVDs
	public static void printTitles(ArrayList<DVD> stock) {
		String titles = "";
		for(int i =0; i<stock.size(); ++i) {
			titles += (stock.get(i).getDVDTitle() + "\n");
		}
		System.out.println(titles);
	}//end printTitles
	public static void printCustomers(ArrayList<Customer> customers) {
		for(int i=0; i<customers.size(); ++i) {
			System.out.println(customers.get(i));
		}
	}//end printCustomers
	public static void printRented(ArrayList<Customer> customers, String name) {
		int i = findCustomer(customers, name);
		System.out.println(customers.get(i).rented());
	}//end printRented
	public static void printAllRented(ArrayList<Customer> customers) {
		for(int i=0; i<customers.size(); ++i) {
			System.out.println("-----" + customers.get(i).getName() + ":");
			System.out.println(customers.get(i).rented());
		}
	}//end printAllRented
	/**
	 * Finds the index of a DVD in the ArrayList.
	 * @param stock the ArrayList of DVDs.
	 * @param title the title of the DVD whose index you need.
	 * @return the index of the DVD in the ArrayList.
	 */
	public static int findDVD(ArrayList<DVD> stock, String title) {
		int index = -1;
		if(haveDVD(title, stock)) {
			for(int i =0; i < stock.size(); ++i)
				if(stock.get(i).title.equals(title)) {
					index = i;
					break;
				}
		}
		else { index = -1;}
		return index;
	}//end findDVD
	/**
	 * Finds the index of a Customer in the ArrayList.
	 * @param customers the ArrayList of Customers.
	 * @param name the name of the customer whose index you need.
	 * @return the index of the Customer in the ArrayList.
	 */
	public static int findCustomer(ArrayList<Customer> customers, String name) {
		int index = -1;
		if(haveCustomer(name, customers)) {
			for(int i = 0; i < customers.size(); ++i)
				if(customers.get(i).getName().equals(name)) {
					index = i;
					break;
				}
		}
		else index = -1;
		return index;
	}//end findCustomer
}//end class
