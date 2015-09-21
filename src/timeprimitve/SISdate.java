/*
Copyright 2015 Institute of Computer Science,
Foundation for Research and Technology - Hellas

Licensed under the EUPL, Version 1.1 or - as soon they will be approved
by the European Commission - subsequent versions of the EUPL (the "Licence");
You may not use this work except in compliance with the Licence.
You may obtain a copy of the Licence at:

http://ec.europa.eu/idabc/eupl

Unless required by applicable law or agreed to in writing, software distributed
under the Licence is distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the Licence for the specific language governing permissions and limitations
under the Licence.

Contact:  POBox 1385, Heraklio Crete, GR-700 13 GREECE
Tel:+30-2810-391632
Fax: +30-2810-391638
E-mail: isl@ics.forth.gr
http://www.ics.forth.gr/isl

Author: Giannis Agathangelos

This file is part of the TimePrimitive project.
*/
/**
 * Wrapper class for the time_primitive parser
 * @author jagathan
 */
package timeprimitve;

import java.io.IOException;

import time.Time;
import core.Parser;
import core.TimeFlags.TM_LANGUAGE;

public class SISdate {
	
	public static enum Language{
		ENGLISH,GREEK
	}

	private int from;
	
	private int to;
	
	private String str;
	
	private String error;
	
	/**
	 * Default constructor
	 */
	public SISdate(){
		this.str = null;
		this.error = null;
	}
	
	/**
	 * Parameterized constructor
	 * @param expr
	 */
	public SISdate(String expr){		
		str = expr;
		if(timeParse(expr) == 0)
			this.str = getTimeErrorMessage();
	}

	/**
	 * Parameterized constructor
	 * @param lower
	 * @param upper
	 */
	public SISdate(int lower,int upper,Language lang){
		if(lang == Language.ENGLISH)
			present(lower,upper,0);
		else if( lang == Language.GREEK)
			present(lower,upper,1);
	}
	
	/**
	 * getFromTo
	 * @return
	 */
	public int[] getFromTo() {
        int[] i = new int[2];
        i[0] = this.from;
        i[1] = this.to;
        return i;
    }
	
	/**
	 * returns lower field of class
	 * @return
	 */
	public int getFrom(){
		return this.from;
	}
	
	/**
	 * returns upper field of class
	 * @return
	 */
	public int getTo(){
		return this.to;
	}
	
	/**
	 * returns presentedValue field
	 * @return
	 */
	public String getFullText(){
		return this.str;
	}
	
	/**
	 * returns error field
	 * @return
	 */
	private String getTimeErrorMessage(){
		return this.error;
	}
	
	/**
	 * timeParse 
	 * parses the expression and stores
	 * the lower and upper value in
	 * classes fields lower and upper respectively
	 * @param exrp
	 * @return
	 */
	private int timeParse(String exrp){
		
		int[] result = new int[2];
		Parser parser = new Parser();
		
		result = null;
		try {
			result = parser.timeParse(exrp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(result == null){
			this.error = parser.getTimeErrorMessage();
			return 0;
		}
		
		this.from = result[0];
		this.to = result[1];
		
		return 1;
	}
	
	/**
	 * Reconstructs the expression
	 * based on lower an upper values
	 * and stores it in the presentedValue field 
	 * @param lower
	 * @param upper
	 * @param lang
	 */
	private void present(int lower, int upper,int lang){
		
		Time time = new Time(lower,upper);
	
		if(lang == 0)
			this.str = time.present(TM_LANGUAGE.TM_ENGLISH);
		else if(lang == 1)
			this.str = time.present(TM_LANGUAGE.TM_GREEK);
	}
}
