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
package error;

public class Error {
	
	private static final int maxMessLen = 3*1024;
	
	private static final int maxMessPos = maxMessLen - 1;

	private String message;
	
	public int flag;
	
	/**
	 * Default constructor
	 */
	public Error(){
		message = new String();
		flag = 0;
	}
	
	/**
	 * returns private field message
	 * @return
	 */
	public String getMessage(){
		return this.message;
	}
	
	/**
	 * Prints message
	 */
	public void printMessage(){
		System.out.println(this.message);
	}
	
	/**
	 * Puts message
	 * @param message
	 */
	public void putMessage(String message){
		
		this.message += message; //+ subString;
		this.flag = 1;
	}
	
	/**
	 * checkError
	 * @param s
	 * @return
	 */
	public short checkError(String s){
		
		if( flag == 1){
			putMessage(s);
			return -1;
		}
		else
			return 0;
	}
	
	/**
	 * resets the object
	 */
	public void reset() { 
		this.message = new String(); 
		flag = 0; 
	} 
	
}
