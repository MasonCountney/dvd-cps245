public interface CustomerAPI{

  // first name
  // last name
  // account number
  // ArrayList of checked out movies

  public void setFirstName(String firstName);       // - Sets the first name of customer
  public void setLastName(String lastName);         // - Sets the Last name of customer
  public String getName();                          // - returns Full name. First Last
  public void setAccountNumber(int AccountNumber);  // - Sets account number
  public int getAccountNumber();                    // - returns account number
  public boolean rentDVD(DVD title);                // - Adds an element to the ArrayList
                                                    //   of DVDs a customer has checked out

  public void returnDVD(String x);                  // - Takes the movie title as a param
                                                    //   deletes item from ArrayList
  public String toString();                         // - Returns Firstname Lastname, Account #
  public String rented();                           // - Returns a list of the movies rented by
                                                    //   that customer

}
