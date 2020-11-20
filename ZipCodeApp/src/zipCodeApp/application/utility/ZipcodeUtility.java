package zipCodeApp.application.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import zipCodeApp.application.exception.InvalidInputException;
import zipCodeApp.application.model.Range;

public class ZipcodeUtility {
	
	/**
	 * method to transform input string to Range object. 
	 * @param s String.
	 * @return Range.
	 * @throws Nothing.
	 * @exception None.
	 */
	public static Range transform (String s) {
		String[] bounds = s.replaceAll("\\]|\\[", "").split(",");
		return new Range(bounds);
	}
	
	/**
	 * method to validate input. 
	 * This checks if input is either null or zipcode is not 5 digits or range is not in order.
	 * @param ranges Array of Input zipcode ranges.
	 * @return Nothing.
	 * @throws InvalidInputException with the corresponding validation message.
	 * @exception InvalidInputException.
	 */
	public static void validateInput(String[] ranges) throws InvalidInputException{

		for(String range : ranges){
			String[] bounds = range.replaceAll("\\]|\\[", "").split(",");
			if(bounds.length !=2) throw new InvalidInputException("Found More than 2 elements in range.");//Input has more than 2 zip codes
			if(!checkNumber(bounds[0]) || !checkNumber(bounds[1])) throw new InvalidInputException("Zipcode must be numeric: "+range); //is zip code a number ?
			if(bounds[0].isEmpty() || bounds[1].isEmpty()) throw new InvalidInputException("Zipcode must be exactly 5 digits: "+range); //is zip code not exactly 5 digits ?
			if(bounds[0].length() != 5 || bounds[1].length() !=5) throw new InvalidInputException("Zipcode must be exactly 5 digits: "+range); //is zip code not exactly 5 digits ?
			if(bounds[1].compareToIgnoreCase(bounds[0]) < 0) throw new InvalidInputException("range is not in order: "+range); //are start and end range in order ?
			
		}
			
	}
	
	/**
	 * method to check if input string contains anything but 0-9 digits. 
	 * @param s String.
	 * @return boolean.
	 * @throws Nothing.
	 * @exception None.
	 */
	public static boolean checkNumber(String s){
		return s.chars().allMatch(Character::isDigit);
	}
	
	/**
	 * method to merge overlapping zipcode ranges. 
	 * Ex: [80000,90000] [85000,92000] gives [80000,92000]
	 * @param ranges ArrayList of Input zipcode ranges.
	 * @return List<Range>.
	 * @throws Nothing.
	 * @exception Nothing.
	 */	
	public static List<Range> merge(List<Range> ranges){
		//If input contains 0 or 1 range, there is nothing to merge.
		if(ranges.size() <= 1){
			return ranges;
		}
		
		/*
		 * Arraylist to hold output/merged ranges. 
		 * At this point, number of ranges in output is unknown.
		 * So, arraylist is a better choice as it scales automatically. 
		 */
		List<Range> output = new ArrayList<>();
		
		/*Take the first range as starting point and compare with other ranges in the input one by one to see if they overlap or exclusive.
		 *Since the input here is sorted, starting element of first element is the lowest zipcode of all ranges. 
		 *So, any expansion needs to be to the right side.
		 */
		Range current_range = ranges.get(0);
		
		/*Add the first range to output. For every comparison, if its determined that the next in the range is overlapping, the current range is updated.
		 *Here, since we are adding only the reference variable to output arraylist, any update outside this list will reflect here.
		 */
		output.add(current_range);
		
		//Check one zip code range at a time.
		for(Range loop_range: ranges){
			
			/*If current range end element is equal to or greater than next element start element, it means overlapping.
			 *So, set the max limit to the highest of both end elements.
			 *Here, using a TreeSet to lexically sort the strings by default.
			 */
			if(current_range.getEnd().compareTo(loop_range.getStart()) >= 0){
				TreeSet<String> set = new TreeSet<>();
				set.add(current_range.getEnd());
				set.add(loop_range.getEnd());
				current_range.setEnd(set.last());
			} else {
				/*If its not overlapping, then leave the previous range in the output as such.
				 *Shift the iteration to the next element and add it to the output array.
				 */
				current_range = loop_range;
				output.add(current_range);
			}
		}
		
		return output;
		
	}

}
