public interface DVDAPI {

    String title = "";
    String star = "";
    String producer = "";
    String director = "";
    String production_company = "";
    boolean available = true;
    public void setDVDInfo(String title, String star, String producer, String director, String production_company, boolean available, int copies);
    public String getDVDInfo();
    public String getDVDTitle();
    public int numberOfCopies();
    public void checkOut();
    public void checkIn();
    public boolean isAvailable();
    public void setAvailability(boolean available);
    
    
}
