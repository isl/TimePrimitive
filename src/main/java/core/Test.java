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

import java.io.IOException;

import timeprimitve.SISdate;

public class Test {

    public static void main(String args[]) throws IOException {

        String[] inputValues = new String[] {"1821 Ιανουάριος 2","1996 Φεβρουάριος","1945","δεκαετία του 1970","έβδομη δεκαετία του 20ου αιώνα","20ος αιώνας","1920 - 1950",
        "3ος αιώνας - 5ος αιώνας","δεκαετία του 1920 - δεκαετία του 1950","18ος αιώνας - δεκαετία του 1850","Αρχές 16ου αιώνα","Μέσα 20ου αιώνα","Τέλη 19ου αιώνα",
        "α' μισό 4ου αιώνα","γ' τέταρτο 1ου αιώνα","ca. 1920","1500 π.Χ.","23ος αιώνας π.Χ.","αρχές 4ου αιώνα π.Χ.","α' μισό 3ου αιώνα π.Χ.","1800 - 1500 π.Χ.","300 π.Χ. - 300 μ.Χ.",
        "7ος αιώνας - 5ος αιώνας π.Χ.","3ος αιώνας π.Χ. - 1ος αιώνας μ.Χ.","3ος αιώνας - 5ος αιώνας","2021/07/03","03/07/2021","2021/07","1991","1990 bce"};
    
        for (String input : inputValues) {
            int from, to;

            System.out.println("\n--------------------------------------------------------\n");
            System.out.println("Input: " + input + "\n");

            SISdate timePrimitive = new SISdate(input);

            from = timePrimitive.getFrom();
            to = timePrimitive.getTo();
            System.out.println("From: " + from + ", To: " + to);

            timePrimitive = new SISdate(from, to, SISdate.Language.ENGLISH);
            System.out.println("Output: " + timePrimitive.getFullText());

            timePrimitive = new SISdate(from, to, SISdate.Language.GREEK);
            System.out.println("Output (in greek): " + timePrimitive.getFullText());
        }
    }

}
