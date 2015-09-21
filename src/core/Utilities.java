package core;

import time.Time;
import date.Date;

public class Utilities implements TimeFlags{

	/**
	 * findNumOfDays
	 * @param month
	 * @param year
	 * @return
	 */
	public static int findNumOfDays(int month,int year){
		
		 int num_of_days;
	
		 switch (month){
		        case 1  :
		        case 3  :
		        case 5  :
		        case 7  :
		        case 8  :
		        case 10 :
		        case 12 : num_of_days = 31;break;
		        case 4  : 
		        case 6  :
		        case 9  :
		        case 11 : num_of_days = 30;
		                  break;
	
		        case 2 :  if (year % 4 != 0) 
		                     num_of_days = 28; 
	                    else
		                     num_of_days = 29;
		                  break;
		        
		        default : num_of_days = -1;
		                  break;
		 }
		 
		 return num_of_days;	 
	}
	
	
	/**
	 * finMonth
	 * @param month
	 * @param language
	 * @return
	 */
	public static String findMonth(int month, TM_LANGUAGE language){

		if (language == TM_LANGUAGE.TM_GREEK){    //Greek language is used
			 
		 	switch(month){
		        case 1  : return "Ιανουάριος";
		        case 2  : return "Φεβρουάριος"; 
		        case 3  : return "Μάρτιος";
		        case 4  : return "Απρίλιος";
		        case 5  : return "Μάιος";
		        case 6  : return "Ιούνιος";
		        case 7  : return "Ιούλιος";
		        case 8  : return "Αύγουστος";
		        case 9  : return "Σεπτέμβριος";
		        case 10 : return "Οκτώβριος";
		        case 11 : return "Νοέμβριος";
		        case 12 : return "Δεκέμβριος";
		        default : 
		                  break;
		 	}
		}
		else{
			   
		 	switch(month){
		        case 1  : return "January";
		        case 2  : return "February"; 
		        case 3  : return "March";
		        case 4  : return "April";
		        case 5  : return "May";
		        case 6  : return "June";
		        case 7  : return "July";
		        case 8  : return "August";
		        case 9  : return "September";
		        case 10 : return "October";
		        case 11 : return "November";
		        case 12 : return "December";
		        default : 
		                  break;
		 		}
		}
	
		return null;
	}
	
	
	/**
	 * findArithmetic
	 * @param number
	 * @param language
	 * @return
	 */
	public static String findArithmetic(int number, TM_LANGUAGE language){
		
		if (language == TM_LANGUAGE.TM_GREEK){
			
	 		switch(number){
	    		case 1  : return "Πρώτη";
	    		case 2  : return "Δεύτερη"; 
	    		case 3  : return "Τρίτη";
	    		case 4  : return "Τέταρτη";
	    		case 5  : return "Πέμπτη";
	    		case 6  : return "Εκτη";
	    		case 7  : return "Εβδομη";
	    		case 8  : return "Ογδοη";
	    		case 9  : return "Ενατη";
	    		case 10 : return "Τελευταία";
	    		default : return "Λάθος";
	 		}
		}
		else{
	 		
			switch(number){
	    		case 1  : return "first";
	    		case 2  : return "second"; 
	    		case 3  : return "third";
	    		case 4  : return "fourth";
	    		case 5  : return "fifth";
	    		case 6  : return "sixth";
	    		case 7  : return "seventh";
	    		case 8  : return "eighth";
	    		case 9  : return "nineth";
	    		case 10 : return "last";
	    		default : return "Wrong";
			}
		}
	}
	
	/**
	 * FindSuffix
	 * @param number
	 * @return
	 */
	public static String findSuffix(int number){
		switch(number){
			case 1  : return "st";
		    case 2  : return "nd"; 
		    case 3  : return "rd";
		    default : return "th";
		}
	}
	
	/**
	 * Integer to alphanumeric
	 * @param number
	 * @return
	 */
	public static String intToAlnum(int number){

		switch(number){
		    case 1  : return "α";
		    case 2  : return "β"; 
		    case 3  : return "γ";
		    case 4  : return "δ";
			default : return "Λάθος";
	 	}
	}
	
	
	/**
     * prints Integer from hex value
     * @param value
     */
    public static void printInt(int value){
    	 
    	int i , counter;
    	
    	for(i = counter = 0; i < 32; i++, value <<= 1){
    		
    		System.out.print((value & 0x80000000) >> 31);
    		
    		counter = (counter+1) % 8;
            if (counter == 0) 
            	System.out.print (" ");
    	}
    	
    	System.out.println();
    }
    
    /**
	 * checks if the given year is valid
	 * @param year
	 * @return
	 */
	public static int checkYear(int year){
		
		if(year <= 0 || year > 32767 )
			return (SIS_ERROR);		
		return (1 - SIS_ERROR);
		
	}
	
	/**
	 * checks if the given month is valid
	 * @param month
	 * @return
	 */
	public static int checkMonth(int month){
		
		if(month > 12 || month < 1)
			return SIS_ERROR;	
		return ( 1 - SIS_ERROR);
	}
	
	/**
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static int checkDay (int year, int month, int day){
		
		if(day <= 0)
			return SIS_ERROR;
		
		if( day > Utilities.findNumOfDays(month,year))
			return(SIS_ERROR);
		
		return (1 - SIS_ERROR);
	}
	
	/**
	 * checkDates
	 * @param l_date
	 * @param u_date
	 * @param period
	 * @return
	 */
	public static int checkDates(Date l_date, Date u_date, int period){
		
		if (period == ce) {		
			if (l_date.year > u_date.year) {
				
				/*sprintf(buffer, PERIOD_YEAR_ERROR ,l_date.year,u_date.year);
				globalError.putMessage( buffer);
				globalError.printMessage();*/
				return(SIS_ERROR);
			}
		}
		else{
			if (l_date.year < u_date.year) {
				
			/*	sprintf(buffer, BCE_YEAR_ERROR ,l_date.year,u_date.year);
				globalError.putMessage( buffer);
				globalError.printMessage();*/
				return(SIS_ERROR);
			}
		}
			
		if ((l_date.year == u_date.year) && (l_date.month > u_date.month)) {
			
		/*	sprintf(buffer, PERIOD_MONTH_ERROR, FindMonth(l_date.month),FindMonth(u_date.month));
			globalError.putMessage( buffer);
			globalError.printMessage();*/
			return(SIS_ERROR);
		}

		if ((l_date.month == u_date.month) && (l_date.day > u_date.day)){
			
			/*sprintf(buffer,PERIOD_DAY_ERROR);
			globalError.putMessage( buffer);
			globalError.printMessage();*/
			return(SIS_ERROR);
		}

		return (1-SIS_ERROR);
	}
	
	public static Time store(Time time, int where,int flag,int year,int month,int day){
		
		int period;
		
		switch(flag){
		
			case SIS_DATE:   
	        case BCEdate: 
	        	
				period =  (flag == SIS_DATE) ? ce : bce;
				
				if (flag == BCEdate) 
					flag = SIS_DATE;
				
	            if (day == UNDEFINED){
	            		
	            	if (where == T_lower) 
	            		day = 1;
	            	else if (where == T_upper) 
	            		day = findNumOfDays(month,year);
	            	
	            	time.store(where,year,month,day,period,PERIOD_EXPR,flag);
	            }
	            else
	            	time.store(where,year,month,day,period,PERIOD_EXPR,flag);
	  
	            break;
	
	        case YEAR: 
	        case BCEyear: 
	        	
				period =  (flag == YEAR) ? ce : bce;
				System.out.println("YEAR BCEyear");	
				if (where == T_lower)
					time.store(where,year,1,1,period,PERIOD_EXPR);
	            else
	                time.store(where,year,12,31,period,PERIOD_EXPR);

				break;
	
	        case DECADE:
	        case IMPL_DECADE: 
	        	
	        	if (where == T_lower){
	 	
	        		if (year == 0)  
	        			++year; 
	        		time.store(where,year,1,1,ce,PERIOD_EXPR,flag);
	        	}
	        	else
	        		time.store(where,year+9,12,31,ce,PERIOD_EXPR,flag);
					   
				break;  
	
			case Circa:
			case BCECirca:
				
				period =  (flag == Circa) ? ce : bce;
				if (where == T_lower){
					// (flag == Circa) ? year -= 10 : year += 10;
					// The previous line had problem with HP
					if (flag == Circa) 
						year-= 10 ;
			 		else 
						year += 10;
                    
					time.store(where,year,1,1,period,PERIOD_EXPR,Circa);
				}
	            else{
	            	//(flag == Circa) ? year += 10 : year -= 10;
					// The previous line had problem with HP
					if (flag == Circa) 
						year += 10 ;
					else 
 		 			  	year -= 10;
					
	                time.store(where,year,12,31,period,PERIOD_EXPR,Circa);
	             }
				
	             break;
	             
	        case BCEdecade: 
	        case BCEidecade: 
	
	        	if (where == T_lower)
	        		time.store(where,year+9,1,1,bce,PERIOD_EXPR,convert(flag));
	            else{
	            	
	            	if (year == 0)  
	            		++year;
	            	
	            	time.store(where,year,12,31,bce,PERIOD_EXPR,convert(flag));
	            }
	            break;
	            
	        case Century: 
	        	
	        	if (where == T_lower){
	        	
	        		year = (year != 0) ? year : 1;
	                time.store(where,year,1,1,ce,PERIOD_EXPR,flag);
	            }
	            else{
	            	
	            	year += 99;
	                time.store(where,year,12,31,ce,PERIOD_EXPR,flag);
	            }
	            break;
	            
	        case BCEcentury: 
	        	if (where == T_lower){
	        	
	        		year += 99;
	                time.store(where,year,1,1,bce,PERIOD_EXPR,Century);
	            }
	            else{
	            	
	                year = (year!=0) ? year : 1;
	                time.store(where,year,12,31,bce,PERIOD_EXPR,Century);
	            }
	            break;
	            
	        default: break;
		}
		return time;
	}
	
	/**
	 * convert
	 * @param flag
	 * @return
	 */
	public static int convert(int flag){
		int newflag;
	   
		switch (flag){
		
			case SIS_DATE: 
				newflag = BCEdate;
		        break;
		        
		    case YEAR: 
		    	newflag = BCEyear;
		        break;
		        
		    case IMPL_DECADE: 
		    	newflag = BCEidecade;
				break;
				
		    case DECADE: 
		    	newflag = BCEdecade;
		        break;
		        
		    case Century: 
		    	newflag = BCEcentury;                      
		        break;
		        
			case Circa:	
				newflag = BCECirca;
				break;
				
			case BCEdecade: 
				newflag = DECADE;
				break;
				
			case BCEidecade : 
				newflag = IMPL_DECADE;
				break;
				
		    default: 
		    	newflag =ERROR;
		    	break;
		}
		return newflag;
	}
}
