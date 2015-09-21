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

Author: Giannis Agathangelos, Konsolaki Konstantina 

This file is part of the TimePrimitive project.


 

TimePrimitive
====

TimePrimitive is a Java API used by [FIMS](https://github.com/isl/FIMS). It implements a primitive time data type. An interval-based time model is adopted.  Users declare temporal elements through certain temporal expressions
that follow the rules of the Art and Architecture Thesaurus. These expressions are parsed and converted into two integers, the lower and the upper boundaries of the corresponding arithmetic interval. Upon recall of a temporal 
element the system reconstructs the exact temporal expression.


Build - Run
====
Folder src contain all the files needed to build and create a jar file.

Usage
====
Conversion of time expression into two integers:

- public SISdate(String expr) : Constructor with the time expression as parameter

- public int[] getFromTo(): Returns an integer array with lower value in position 0 and upper value in position 1

- public int getFrom(): Returns lower value

- public int getTo(): Returns upper value
	
	
Reconstruct the exact temporal expression from two integers:

- public SISdate (int from,int to,Language lang): Reconstructs temporal expression based on from/to values and lang (Greek or English)

- public String getFullText(): Return’s  a string representation of the reconstructed temporal expression



