package biz.ui.filesystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Utility class for expanding all filters to match file extensions in a case insensitive fashion
 * 
 * call the get() method to retrieve all ExtensionFilters
 * @author karottop
 *
 */
public class FriendlyExtensionFilter{
	{filters = new ArrayList<>();}
	
	List<ExtensionFilter> filters;
	
	/**
	 * Utility class for expanding all filters to match file extensions in a case insensitive fashion
	 * 
	 * call the get() method to retrieve all ExtensionFilters
	 * @param filters
	 */
	public FriendlyExtensionFilter(String description, String... filters){
		this.filters.add(new ExtensionFilter(description, getAllPossibleCases(filters)));
	}
	
	/**
	 * Utility class for expanding all filters to match file extensions in a case insensitive fashion
	 * 
	 * call the get() method to retrieve all ExtensionFilters
	 * @param filters
	 */
	public FriendlyExtensionFilter(ExtensionFilter... extensions){
		this.filters.addAll(getAllPossibleCases(extensions));
	}
	
	/**
	 * Utility class for expanding all filters to match file extensions in a case insensitive fashion
	 * 
	 * call the get() method to retrieve all ExtensionFilters
	 * @param filters
	 */
	public FriendlyExtensionFilter(List<ExtensionFilter> filters){
		this.filters.addAll(
				getAllPossibleCases((ExtensionFilter[])filters.toArray()));
	}
	
	/**
	 * expands each filter to include all possible extension permutations
	 * @param filters
	 * @return
	 */
	protected List<ExtensionFilter> getAllPossibleCases(ExtensionFilter... filters){
		List<ExtensionFilter> allFilters = new ArrayList<>();
		for(ExtensionFilter filter : filters){
			allFilters.add(new ExtensionFilter(filter.getDescription(), 
					getAllPossibleCases(filter.getExtensions())));
		}
		return allFilters;
	}
	
	/**
	 * converting each extension to all possible case permutations
	 * @param exts
	 * @return
	 */
	protected List<String> getAllPossibleCases(String... exts){
		Set<String> allExts = new HashSet<>();
		for(String ext : exts){
			allExts.addAll(getAllPossibleCases(ext));
		}
		return new ArrayList<>(allExts);
	}
	
	/**
	 * utility method for converting each extension to all possible permutations
	 * @param exts
	 * @return
	 */
	protected List<String> getAllPossibleCases(List<String> exts){
		return getAllPossibleCases((String[])exts.toArray());
	}
	
	/**
	 * expands filter to include all possible permutations
	 * @param filter
	 * @return
	 */
	protected ExtensionFilter getAllPossibleCases(ExtensionFilter filter){
		List<String> exts = filter.getExtensions();
		for(String ext : exts){
			exts.addAll(getAllPossibleExtensions(ext));
		}
		return new ExtensionFilter(filter.getDescription(), exts);
		
	}
	
	/**
	 * gets all the possible permutations for a 3 letter extension
	 * @param ext
	 * @return
	 */
	private List<String> getAllPossibleExtensions(String ext){
		Set<String> exts = new HashSet<>();
		exts.add(ext.toLowerCase());
		exts.add(ext.toUpperCase());
		String prefix = "";
		for(char c : ext.toCharArray()){
			if(!(Character.isAlphabetic(c) || Character.isDigit(c))) prefix += c;
		}
		
		String extLetters = ext.replace(prefix, "").toLowerCase();
		exts.add(prefix + new String(flipCharsAtIndex(extLetters.toCharArray(), 0)));
		exts.add(prefix + new String(flipCharsAtIndex(extLetters.toCharArray(), 1)));
		exts.add(prefix + new String(flipCharsAtIndex(extLetters.toCharArray(), 2)));
		exts.add(prefix + new String(flipCharsAtIndex(extLetters.toCharArray(), 0, 1)));
		exts.add(prefix + new String(flipCharsAtIndex(extLetters.toCharArray(), 1, 2)));
		exts.add(prefix + new String(flipCharsAtIndex(extLetters.toCharArray(), 0, 2)));
		return new ArrayList<String>(exts);
	}
	
	/**
	 * flips the case of the char at whatever indexes specified
	 * @param chars
	 * @param indexes to flip
	 * @return
	 */
	private char[] flipCharsAtIndex(char[] chars, int... indexes){
		char[] charClone = Arrays.copyOf(chars, chars.length);
		for(int index : indexes){
			char charLetter = charClone[index];
			char oppositeCase = Character.isUpperCase(charLetter) ? Character.toLowerCase(charLetter)
					: Character.toUpperCase(charLetter);
			charClone[index] = oppositeCase;
		}		
		return charClone;
	}
	
	public List<ExtensionFilter> get(){
		return filters;
	}
	

}
