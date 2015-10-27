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

import time.Time;
import error.Error;

public interface TimeFlags {
	
	
	public Time[] parsedTimeVal = new Time[1];
	
	public Error[] globalError = new Error[1];
	
	public enum TM_LANGUAGE {   TM_ENGLISH,  TM_GREEK   };
	
	public int centuryVal 	= 0;
	
	public int decadeVal 	= 0;

	public int Begin 		= 0;
	
	public int End 			= 0;
	
	public static final int SIS_DATE 	= 1;
	
	public static final int YEAR 		= 2;
	
	public static final int DECADE      = 3;
	
	public static final int IMPL_DECADE = 4;
	
	public static final int Century    	= 5;
	
	public static final int PERIOD_EXPR = 6;
	
	public static final int IMPL_PERIOD = 7;
	
	public static final int Circa       = 8;

	public static final int UNDEFINED   = -1;

	public static final int UNUSED      = -1;
	
	public static final int T_lower     = 1;
	
	public static final int T_upper     = 2;
	
	public static final int bce         = 1;
	
	public static final int ce          = 2;
	
	public static final int ERROR 		= -1;
	
	public static final int	SIS_ERROR	= -1;
	
	public static final int	SIS_OK		= 0;
	
	public static final int BCEcentury  = 16;
	
	public static final int BCEyear     = 17;
	
	public static final int BCEdate     =   18;
	
	public static final int BCEdecade   =   19;
	
	public static final int BCEidecade  =   20;
	
	public static final int BCECirca 	=	14;
	
	
	
	public static final int  NEGATIVE_INF		= 0x80000000;
	
	public static final int POSITIVE_INF		= 0x7fffffff;
	
	public static final int RESET_DAY  			= 0xfffff07f;
	
	public static final int RESET_TIME			= 0x00000000;
	
	public static final int RESET_YEAR 			= 0x0000ffff;
	
	public static final int RESET_MONTH 		= 0xffff0fff;
	
	public static final int BCE_FLAG     		= 0x80000000;
	
	public static final int UPPER_FLAG   		= 0x00000040;
	
	public static final int CLEAR_DATE   		= 0x8000007f;
	
	public static final int CLEAR_FLAGS  		= 0xffffffa0;
	
	public static final int GET_YEAR     		= 0xffff0000;
	
	public static final int GET_MONTH   	 	= 0x0000f000;
	
	public static final int GET_DAY      		= 0x00000f80;
	
	
	public static final int EXPR_BITS  			= 0x0000003e;
	
	public static final int PERIOD_EXPR_BIT 	= 0x00000001;
	
	public static final int IMPL_PERIOD_BIT 	= 0x00000003;
	
	public static final int MODE_BIT 			= 0x00000002;
	
	public static final int EXPL_DEC_BIT    	= 0x00000004;
	
	public static final int IMPL_DEC_BIT   		= 0x0000000c;
	
	public static final int DATE_BIT    		= 0x00000008;
	
	public static final int CENTURY_BIT 		= 0x00000010;
	
	public static final int CIRCA_BIT 			= 0x00000020;
	
	public static final int AAT_BIT    			= 0x00000018;
	
	
	public static final String DECADE_ERROR = "\nDefinitions for decades must be multiples of 10.\nMaybe the right decade is %d rather than %d\n";

	public static final String PERIOD_YEAR_ERROR  = "\nThe beginning year (%d) of the given period\nis greater than the ending one (%d).\nThis is not a valid period expression.\n";

	public static final String PERIOD_MONTH_ERROR = "\nThe beginning month (%s) of the given period\nis greater than the ending one (%s).\nThis is not a valid period expression.\n";

	public static final String PERIOD_DAY_ERROR = "\nThe beginning date of the given period is greater than the ending one.\nThis is not a valid period expression.\n";

	public static final String BCE_YEAR_ERROR = "\nYou are trying to define a BCE period.\nThe starting year (%d) should be greater than the ending one (%d).\n";

	public static final String ILLEGAL_YEAR = "\nYear value (%d) is an invalid value for dates.\nYou should provide a non zero, positive  year value.\nThe upper limit is year 32767.\n";

	public static final String ILLEGAL_MONTH = "\nMonth value (%d) is an invalid value for dates.\nYou should provide a month value in the range [1,12]\n";

	public static final String ILLEGAL_DAY = "\nDay value (%d) is an invalid value for dates.\nYou should provide a positive day value instead.\n";

	public static final String ILLEGAL_MONTH_DAY = "\nDay value (%d) is an illegal value for month (%s).\nYou should provide a day value in the range of [1-%d]\n";
}
