/**
 * A DVD object represents a DVD that is owned by the store.
 * A DVD object contains all the information about a DVD.
 * 
 * @author Ezekiel Krisp
 */
public class DVD implements DVDAPI {

    String title;
    String star;
    String producer;
    String director;
    String production_company;
    private boolean available;
    private int copies;

    
    DVD(){} //Default constructor
    /**
     * This is needed to fill the list of DVDs rented by a customer
     * @param title the title of the DVD.
     */
    DVD(String title){	
    	this.title = title;
    }
    /**
     * The main constructor and the one used by the main method. 
     * The availability is determined by the number of copies and does not need a parameter.
     * @param title 
     * @param star The database only lists three stars and all on the same line 
     * @param producer
     * @param director
     * @param production_company
     * @param copies the number of copies in stock. Must be greater than zero.
     */
    DVD(String title, String star, String producer, String director, String production_company, int copies){
        this.title = title;
        this.star = star;
        this.producer = producer;
        this.director = director;
        this.production_company = production_company;
        this.copies = copies;
        if(copies > 0)			
        available = true;		
    }
    
    @Override
    public void setDVDInfo(String title, String star, String producer, String director, String production_company, boolean available, int copies) {
        // TODO Auto-generated method stub
        
        this.title = title;
        this.star = star;
        this.producer = producer;
        this.director = director;
        this.production_company = production_company;
        this.available = available;
        this.copies = copies;
        
    }

    @Override
    public String getDVDInfo() {
        // TODO Auto-generated method stub
        return title + "\n" + star + "\n" + producer + "\n" + director + "\n" + production_company + "\n" + available + "\n" + copies;
    }

    public String getDVDTitle() {
        
        return title;
    }
    
    @Override
    public int numberOfCopies() {
        // TODO Auto-generated method stub
        
        return copies;
    }

    @Override
    public void checkOut() {
        // TODO Auto-generated method stub
        --copies;
    }

    @Override
    public void checkIn() {
        // TODO Auto-generated method stub
        ++copies;
    }

    @Override
    public boolean isAvailable() {
        // TODO Auto-generated method stub
        return available;
    }
    public void setAvailability(boolean available) {
        
        this.available = available;
    }

}
