import java.util.ArrayList;
/**
 * A customer object represents a customer with an account at the store. 
 * A customer object contains the customers information as well as a list 
 * of DVDs the customer currently has rented.
 * 
 * @author Mason Countney
 */
public class Customer implements CustomerAPI{

  private String firstName;
  private String lastName;
  private int AccountNumber;
  public ArrayList<DVD> RentedList = new ArrayList<DVD>();

  Customer(){}
  /**
   * This is the main constructor and the one used by the main method.
   * @param firstName 
   * @param lastName
   * @param AccountNumber
   */
  Customer(String firstName, String lastName, int AccountNumber){
    this.firstName = firstName;
    this.AccountNumber = AccountNumber;
    this.lastName = lastName;
  }

  public void setFirstName(String firstName) { this.firstName = firstName; }
  public void setLastName(String lastName) { this.lastName = lastName; }
  public String getName(){ return this.firstName + " " + this.lastName; }
  public String getFirstName() { return this.firstName; }
  public String getLastName() { return this.lastName; }
  public void setAccountNumber(int AccountNumber){ this.AccountNumber = AccountNumber; }
  public int getAccountNumber(){ return AccountNumber;}
  /**
   * Adds a DVD to the list of rented DVDs for the customer.
   * @param x Must be a Object of type DVD.
   */
  public boolean rentDVD(DVD x){ this.RentedList.add(x); return true; }
 /**
  * Finds a DVD from the list of rented DVDs and removes it.
  * @param x the title of the movie to be removed.
  */
  public void returnDVD(String x){
    for(int i = 0; i < RentedList.size(); ++i){
      if(RentedList.get(i).title.equals(x))
        RentedList.remove(i);
    }
   }

  public String toString(){ return (firstName + " " + lastName + ", " + AccountNumber); }
  /**
   * @return A String that contains a list of all DVDs rented by a customer.
   */
  public String rented(){
    String temp = "";
    for(int i = 0; i < RentedList.size(); ++i){
      temp += RentedList.get(i).title;
      if(i != RentedList.size()-1)
        temp += "\n";
    }
    return temp;
  }

  public int compareTo(Customer that) {
    if(this.getName().compareTo(that.getName()) != 0)
      return this.getName().compareTo(that.getName());
    else if(this.getAccountNumber() < that.getAccountNumber())
      return -1;
    else if(this.getAccountNumber() > that.getAccountNumber())
      return 1;
    else
      return 0;
  }

}