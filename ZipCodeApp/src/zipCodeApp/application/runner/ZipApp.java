package zipCodeApp.application.runner;

import static zipCodeApp.application.utility.ZipcodeUtility.merge;
import static zipCodeApp.application.utility.ZipcodeUtility.validateInput;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import zipCodeApp.application.exception.InvalidInputException;
import zipCodeApp.application.model.Range;
import zipCodeApp.application.utility.ZipcodeUtility;

/**
* ZipApp implements an application that
* merges overlapping zipcode ranges and gives a simplified range.
*
* @author  Rekha Eruvaram
* @version 1.0
* @since   2020-11-20 
*/
public class ZipApp {
	
	public static void main(String... args) throws InvalidInputException{
		System.out.println("Provide zipcode ranges in the format [12345,67890] [56789, 98765]");
		
		//try-with-resources to autoclose the scanner
		try(Scanner scanner = new Scanner(System.in);){
			//scan the given input from console
			String inputLine = scanner.nextLine();
			if(inputLine == null || inputLine.trim().isEmpty()){
				throw new InvalidInputException("No input provided");
			}
			//split the ranges separately
			String[] rangesInString = inputLine.split(" ");
			//validate each of the ranges
			validateInput(rangesInString);
			//transform the strings to Range model objects
			List<Range> inputRanges = Arrays.stream(rangesInString).map(ZipcodeUtility::transform).collect(Collectors.toList());
			//sort the objects for easier manipulation later
			inputRanges.sort((r1,r2)->r1.getStart().compareTo(r2.getEnd()));
			//merge overlapping ranges and display output
			List<Range> mergedRanges = merge(inputRanges);
			mergedRanges.stream().forEach(System.out::print);
		}catch(InvalidInputException ie){
			System.out.println(ie.getMessage());
			throw ie;
		} 
		
	}
	
}
