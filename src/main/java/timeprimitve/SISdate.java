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
 *
 * @author jagathan
 */
package timeprimitve;

import java.io.IOException;

import time.Time;
import core.Parser;
import core.TimeFlags.TM_LANGUAGE;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class SISdate {

    public static enum Language {
        ENGLISH, GREEK
    }

    private int from;

    private int to;

    private String str;

    private String error;

    /**
     * Default constructor
     */
    public SISdate() {
        this.str = null;
        this.error = null;
    }

    /**
     * Parameterized constructor
     *
     * @param expr
     */
    public SISdate(String expr) {

        // Patch for YYYY/MM/DD and DD/MM/YYYY values
        Calendar dateExpr = getValidDate(expr);
        if (dateExpr != null) {
            expr = dateExpr.get(Calendar.YEAR) + " " + dateExpr.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " " + dateExpr.get(Calendar.DAY_OF_MONTH);
        }
        
        // Patch for values containing "-" without surounding space(s)
        expr = expr.replace("-", " - ").replaceAll(" +", " ");

        String newExpr = "";
        if (expr.toLowerCase().contains("ca.")) {
            String exprParts[] = expr.split(" ");
            if (exprParts.length == 2) {
                newExpr = exprParts[1] + " , ca.";
            } else {
                newExpr = expr;
            }

        } else {

            String exprParts[] = expr.split(" ");

            for (String part : exprParts) {
                if (part.toLowerCase().equals("1st") || part.toLowerCase().equals("2nd") || part.toLowerCase().equals("3rd")) {
                    newExpr += (part + " ");
                } else {
                    if (Character.isDigit(part.charAt(0)) && part.endsWith("st")) {
                        String newpart = part.replace("st", "th");
                        newExpr += (newpart + " ");
                    } else if (Character.isDigit(part.charAt(0)) && part.endsWith("nd")) {
                        String newpart = part.replace("nd", "th");
                        newExpr += (newpart + " ");
                    } else if (Character.isDigit(part.charAt(0)) && part.endsWith("rd")) {
                        String newpart = part.replace("rd", "th");
                        newExpr += (newpart + " ");
                    } else {
                        newExpr += (part + " ");
                    }
                }
            }
            newExpr = newExpr.trim();
        }

        str = newExpr;
        if (timeParse(str) == 0) {
            this.str = getTimeErrorMessage();
        }
    }

    /**
     * Parameterized constructor
     *
     * @param lower
     * @param upper
     */
    public SISdate(int lower, int upper, Language lang) {
        if (lang == Language.ENGLISH) {
            present(lower, upper, 0);
        } else if (lang == Language.GREEK) {
            present(lower, upper, 1);
        }
    }

    /**
     * getFromTo
     *
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
     *
     * @return
     */
    public int getFrom() {
        return this.from;
    }

    /**
     * returns upper field of class
     *
     * @return
     */
    public int getTo() {
        return this.to;
    }

    /**
     * returns presentedValue field
     *
     * @return
     */
    public String getFullText() {
        return this.str;
    }

    /**
     * returns error field
     *
     * @return
     */
    private String getTimeErrorMessage() {
        return this.error;
    }

    /**
     * timeParse parses the expression and stores the lower and upper value in
     * classes fields lower and upper respectively
     *
     * @param exrp
     * @return
     */
    private int timeParse(String exrp) {

        int[] result = new int[2];
        Parser parser = new Parser();

        result = null;
        try {
            result = parser.timeParse(exrp);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (result == null) {
            this.error = parser.getTimeErrorMessage();
            return 0;
        }

        this.from = result[0];
        this.to = result[1];

        return 1;
    }

    /**
     * Reconstructs the expression based on lower an upper values and stores it
     * in the presentedValue field
     *
     * @param lower
     * @param upper
     * @param lang
     */
    private void present(int lower, int upper, int lang) {

        Time time = new Time(lower, upper);

        if (lang == 0) {
            this.str = time.present(TM_LANGUAGE.TM_ENGLISH);
        } else if (lang == 1) {
            this.str = time.present(TM_LANGUAGE.TM_GREEK);
        }
    }

    /**
     * Check if a String has a valid date value
     *
     * @param inDate The String date
     * @return true if the date is valid, false otherwise
     */
    public static Calendar getValidDate(String inDate) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("[yyyy/MM/dd][yyyy/MM][yyyy][dd/MM/yyyy]");
        try {
            LocalDate localDate = LocalDate.parse(inDate.trim(), dateFormatter);
            Calendar calendar = Calendar.getInstance();
            calendar.set(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth());
            return calendar;
        } catch (Exception ex) {
            return null;
        }
    }
}
