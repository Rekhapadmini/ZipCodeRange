package zipCodeApp.test;

import static org.junit.Assert.*;

import org.junit.Test;

import zipCodeApp.application.exception.InvalidInputException;
import zipCodeApp.application.model.Range;
import zipCodeApp.application.utility.ZipcodeUtility;
import static zipCodeApp.application.utility.ZipcodeUtility.*;
import static zipCodeApp.application.utility.ZipcodeUtility.validateInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
* ZipCodeUtilityTest to test all the utility methods used in the app
* @author  Rekha Eruvaram
* @version 1.0
* @since   2020-11-20 
*/
public class ZipCodeUtilityTest {

	@Test
	public void multiple_params_test() {
		
		try {
		      String[] inputRanges = "[85254,85259,86023] [90001,99999]".split(" ");
		      validateInput(inputRanges);
		      } catch (InvalidInputException e) {
		      assertEquals("InvalidInputException", e.getClass().getSimpleName());
		      assertEquals(true,e.getMessage().contains("Found More than 2 elements in range."));
		    }
	}
	
	@Test
	public void alphanumeric_params_test() {
		
		try {
		      String[] inputRanges = "[85254,85a59] [90001,99999]".split(" ");
		      validateInput(inputRanges);
		      } catch (InvalidInputException e) {
		      assertEquals("InvalidInputException", e.getClass().getSimpleName());
		      assertEquals(true,e.getMessage().contains("Zipcode must be numeric: "));
		    }
	}
	
	@Test
	public void null_or_empty_params_test() {
		
		try {
		      String[] inputRanges = "[,85259] [90001,99999]".split(" ");
		      validateInput(inputRanges);
		      } catch (InvalidInputException e) {
		      assertEquals("InvalidInputException", e.getClass().getSimpleName());
		      assertEquals(true,e.getMessage().contains("Zipcode must be exactly 5 digits: "));
		    }
	}
	
	@Test
	public void zipcode_morethan_5_digit_test() {
		
		try {
		      String[] inputRanges = "[85001,85259] [900001,99999]".split(" ");
		      validateInput(inputRanges);
		      } catch (InvalidInputException e) {
		      assertEquals("InvalidInputException", e.getClass().getSimpleName());
		      assertEquals(true,e.getMessage().contains("Zipcode must be exactly 5 digits: "));
		    }
	}
	
	@Test
	public void zipcode_range_order_test() {
		
		try {
		      String[] inputRanges = "[85260,85259] [90001,99999]".split(" ");
		      validateInput(inputRanges);
		      } catch (InvalidInputException e) {
		      assertEquals("InvalidInputException", e.getClass().getSimpleName());
		      assertEquals(true,e.getMessage().contains("range is not in order: "));
		    }
	}
	
	@Test
	public void zipcode_number_test() {
		String zip = "85260";
		assertEquals(true,checkNumber(zip));
	}
	
	@Test
	public void input_transform_test() {
		String zip = "[85260,85262]";
		Range r = transform(zip);
		assertEquals("85260",r.getStart());
		assertEquals("85262",r.getEnd());
	}
	
	@Test
	public void zipcode_range_merge_test() {
		List<Range> inputRanges = new ArrayList<>();
		inputRanges.add(transform("[85001,85259]"));
		inputRanges.add(transform("[85260,85260]"));
		inputRanges.add(transform("[85262,85264]"));
		inputRanges.add(transform("[85264,85265]"));
		
		List<Range> mergedRanges = merge(inputRanges);
		
		assertEquals(3,mergedRanges.size());
	}

}
