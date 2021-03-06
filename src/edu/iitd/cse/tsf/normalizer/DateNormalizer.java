package edu.iitd.cse.tsf.normalizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.iitd.cse.tsf.constants.PatternConstantEnumKBP;


/**
 * @author abhishek
 *
 */
public class DateNormalizer {

	private String monthRegex = "([Jj]an\\.?(uary)?|[Ff]eb\\.?(ruary)?|[Mm]ar\\.?(ch)?|[Aa]pr\\.?(il)?|May\\.?|[Jj]un\\.?e?|[Jj]ul\\.?y?|[Aa]ug\\.?(ust)?|[Ss]ept?\\.?(ember)?|[Oo]ct\\.?(ober)?|[Nn]ov\\.?(ember)?|[Dd]ec\\.?(ember)?)";
	
	private String dayRegex = "(first|second|third|fourth|fifth|sixth|seventh|eighth|ninth|tenth|eleventh|twelfth|thirteenth|fourteenth|fifteenth|sixteenth|seventeenth|eighteenth|nineteenth|twentieth|twenty first|twenty second|twenty third|twenty fourth|twenty fifth|twenty sixth|twenty seventh|twenty eighth|twenty ninth|thritieth|thirty first|1|1st|2|2nd|3|3rd|4|4th|5|5th|6|6th|7|7th|8|8th|9|9th|10|10th|11|11th|12|12th|13|13th|14|14th|15|15th|16|16th|17|17th|18|18th|19|19th|20|20th|21|21st|22|22nd|23|23rd|24|24th|25|25th|26|26th|27|27th|28|28th|29|29th|30|30th|31|31st)";
	
	private String yearRegex = "([0-9][0-9][0-9][0-9])";
	
	private List<String> dateRegex;
	
	
	
	public List<String> getDateRegex() {
		return dateRegex;
	}


	public DateNormalizer() {
		super();
		// TODO Auto-generated constructor stub
		dateRegex= new ArrayList<String>();
		dateRegex.add("[0-3][0-9]?/[0-3][0-9]?(?:/[0-9][0-9][0-9][0-9])");
		dateRegex.add("[0-3][0-9]?-[0-3][0-9]?(?:-[0-9][0-9][0-9][0-9])");
		dateRegex.add(monthRegex+"\\s*"+dayRegex+"?\\s*"+yearRegex);
		dateRegex.add(monthRegex+"\\s*"+dayRegex+"?,?\\s*"+yearRegex);
		dateRegex.add(dayRegex+"\\s+"+"(of\\s+)?"+monthRegex);
	}
	
	public String normalizeDate(String text, Map<String,List<String>> normalizationValue) {
		
		int patternConstantCount = 1;
		for (String string : dateRegex) {
			
			Pattern pattern = Pattern.compile(string);
			Matcher matcher = pattern.matcher(text);
			
			while(matcher.find()) {
				System.out.println(string);
				text = matcher.replaceFirst(PatternConstantEnumKBP.DATE.toString()+"#"+patternConstantCount);
				//System.out.println("----"+matcher.group());
				
				//Storing the value
				if(normalizationValue.containsKey(PatternConstantEnumKBP.DATE.toString())) {
					
					List<String> valueList = normalizationValue.get(PatternConstantEnumKBP.DATE.toString());
					valueList.add(matcher.group());

				}else {
					//The Map does not contain the Date constant
					List<String> valueList = new ArrayList<String>();
					valueList.add(matcher.group());
					normalizationValue.put(PatternConstantEnumKBP.DATE.toString(), valueList);
					System.out.println(matcher.group());
				}
				
				matcher = pattern.matcher(text);
				patternConstantCount++;
			}
		}
		
		return text;
	}


	/**
	 * @param args
	 */
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub

		DateNormalizer2 dateNormalizer = new DateNormalizer2();
		String text = "I was born on 23rd of Jun and 5th July ";
		
		String normalizeDate = dateNormalizer.normalizeDate(text,new HashMap<String, List<String>>());
		System.out.println(normalizeDate);
		
	}*/

}
