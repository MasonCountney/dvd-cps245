public interface CustomerAPI{

  public void setFirstName(String firstName);
  public void setLastName(String lastName);
  public String getName();
  public void setAccountNumber(int AccountNumber);
  public int getAccountNumber();
  public boolean rentDVD(DVD title);
  public void returnDVD(String x);
  public String toString();
  public String rented();

}
