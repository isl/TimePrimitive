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
package webservice;

public class TimeRepresentation {
	
	private int from;
	
	private int to;
	
	public TimeRepresentation(){
		
	}
	
	/**
	 * sets from field
	 * @param from
	 */
	public void setFrom(int from){
		this.from = from;
	}
	
	/**
	 * returns from field
	 * @return
	 */
	public int getFrom(){
		return this.from;
	}
	
	/**
	 * sets to field
	 * @param to
	 */
	public void setTo(int to){
		this.to = to;
	}

	/**
	 * returns to field
	 * @return
	 */
	public int getto(){
		return this.to;
	}
}
