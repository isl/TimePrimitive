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
package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import timeprimitve.SISdate;

public class Test{
	
	public static void main(String args[]) throws IOException{
		
		  System.out.println("\nParsing primitive values from file:"+ args[0]+ ", results in file:"+ args[1]+"\n");
		  
		  BufferedReader in = new BufferedReader(new FileReader(args[0]));
		  
		  Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "utf-8"));
		  
		  String expr = null;
		  while((expr = in.readLine()) != null){
			  int from , to;
			  
			  writer.write("\n--------------------------------------------------------\n");
			  writer.write("Input : "+ expr +"\n");
			  
			  SISdate timePrimitive = new SISdate(expr);
			  
			  from = timePrimitive.getFrom();
			  to = timePrimitive.getTo();
			  
			  timePrimitive = new SISdate(from,to,SISdate.Language.ENGLISH);
			  writer.write("Output : "+timePrimitive.getFullText()+"\n");
			  
			  timePrimitive = new SISdate(from,to,SISdate.Language.GREEK);
			  writer.write("Output (in greek): "+timePrimitive.getFullText()+"\n");
		  }
		  
		  writer.close();
		  in.close();
	}
	
}
