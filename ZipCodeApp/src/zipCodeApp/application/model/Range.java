package zipCodeApp.application.model;

/**
* Range class to represent Range of zipcodes provided in input
* @author  Rekha Eruvaram
* @version 1.0
* @since   2020-11-20 
*/
public class Range 
{ 
    private String start;
    private String end; 
    
    public Range(String[] bounds) {
    	this.start = bounds[0];
    	this.end = bounds[1];
    }
    
    public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	Range(String start, String end){
    	this.start = start;
    	this.end = end;
    }
    
   
	@Override
	public String toString() {
		return "["+this.start+","+this.end+"]";
	} 
 }
