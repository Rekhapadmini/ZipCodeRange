package zipCodeApp.application.exception;

/**
* InvalidInputException to create custom exception
* @author  Rekha Eruvaram
* @version 1.0
* @since   2020-11-20 
*/
public class InvalidInputException extends Exception 
{ 
	//version id for Serializable class
	private static final long serialVersionUID = 1234567L;

	public InvalidInputException(String msg) 
    { 
        super(msg); 
    } 
}
