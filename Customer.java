import java.util.ArrayList;

public class Customer implements CustomerAPI{

  private String firstName;
  private String lastName;
  private int AccountNumber;
  public ArrayList<DVD> RentedList = new ArrayList<DVD>();

  Customer(){}
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
  public boolean rentDVD(DVD x){ this.RentedList.add(x); return true; }
  public void returnDVD(String x){
    for(int i = 0; i < RentedList.size(); ++i){
      if(RentedList.get(i).title.equals(x))
        RentedList.remove(i);
    }
   }

  public String toString(){ return (firstName + " " + lastName + ", " + AccountNumber); }
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
