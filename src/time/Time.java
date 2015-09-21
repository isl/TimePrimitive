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
package time;

import core.TimeFlags;
import core.Utilities;

public class Time implements TimeFlags{

	public int lower;  	//Holds the lower date of expression
	
	public int upper;	//Holds the upper date of expression
	
	
	/**
	 * Default constructor
	 */
	public Time(){
		
	}
	
	/**
	 * Parameterized constructor
	 * @param lower
	 * @param upper
	 */
	public Time(int lower, int upper){
		this.lower = lower;
		this.upper = upper;
	}
	
	public void set_upper_lower(int u, int l){ 
		this.upper = u; 
		this.lower = l; 
	}
	
	public int[] getUpperLower(){ 
		int[] values = new int[2];
		values[0] = this.lower; 
		values[1] = this.upper;
		return values;
	}

	public int   returnLower() { return this.lower; }
	
	public int   returnUpper() { return this.upper; }
	
	/**
	 * 
	 * @param flag
	 */
	public void store(int flag){
		
		clearTime(flag);
		
		if(flag == T_lower)
			this.lower = (this.lower | NEGATIVE_INF);
		
		else
			this.upper = (this.lower | NEGATIVE_INF);
	}
     
	/**
	 * 
	 * @param flag
	 * @param year
	 * @param month
	 * @param day
	 * @param period
	 */
	public void store(int flag,int year,int month,int day,int period){
		
		clearTime(flag);
	    
		if (period == bce)
			year = -year;
	       
	    set_year(flag,year);
	    set_month(flag,month);
	    set_day(flag,day);
	    
		if (flag == T_upper)
			upper = (upper | UPPER_FLAG);

	}
	
	/**
	 * Store
	 * @param flag
	 * @param year
	 * @param month
	 * @param day
	 * @param period
	 * @param expr
	 */
	public void store(int flag,int year,int month,int day,int period,int expr){
		
		store(flag,year,month,day,period);
		setExprBits(flag,expr);
	}
	
	/**
	 * Store
	 * @param flag
	 * @param year
	 * @param month
	 * @param day
	 * @param period
	 * @param per_expr
	 * @param expr
	 */
    public void store(int flag,int year,int month,int day,int period,int per_expr,int expr){
    	
    	store(flag,year,month,day,period);
    	setExprBits(flag,expr);
    	setExprBits(flag,per_expr);
    }
    
    /**
     * Clears time
     * @param flag
     */
    public void clearTime(int flag){
    	if (flag == T_lower)
    		lower = (lower & RESET_TIME);

	    else
	    	upper  = (upper & RESET_TIME);

    }
    
    /**
     * Set day
     * @param flag
     * @param day
     */
    public void set_day(int flag,int day){
    	
    	if (flag == T_lower)
    		this.lower = (this.lower & RESET_DAY) | ((day|0)<<7);
    	
    	else
    		this.upper  = (this.upper & RESET_DAY) | ((day|0)<<7);
    }
    
    /**
     * set Month
     * @param flag
     * @param month
     */
    public void set_month(int flag,int month){
    	
    	if (flag == T_lower)
    		this.lower = (this.lower & RESET_MONTH) | ((month|0)<<12);
       
    	else 
    		this.upper = (this.upper & RESET_MONTH) | ((month|0)<<12);
    }
    
    /**
     * Set year
     * @param flag
     * @param year
     */
    public void set_year(int flag,int year){
    	
    	if (flag == T_lower)
    		this.lower = (this.lower & RESET_MONTH) | ((year|0)<<16);
        
    	else
    		this.upper = (this.upper & RESET_MONTH) | ((year|0)<<16);
    
    }
    
    /**
     * Set Expression bits
     * @param flag
     * @param expr
     */
    public void setExprBits(int flag, int expr){
    	 int operand;

    	    switch (expr)
    	        {
    	        case SIS_DATE:
    	                operand = DATE_BIT;
    	                break;
    	        case DECADE:
    	                operand = EXPL_DEC_BIT;
    	                break;
    	        case IMPL_DECADE:
    	                operand = IMPL_DEC_BIT;
    	                break;
    	        case Century:
    	                operand = CENTURY_BIT;
    	                break;
    	        case Circa:
    	                operand = CIRCA_BIT;
    	                break;
    	        case PERIOD_EXPR:
    	                operand = PERIOD_EXPR_BIT;
    	                break;
    	        case IMPL_PERIOD:
    	                operand = IMPL_PERIOD_BIT;
    	                break;
    	        default :
    	               
    	               	Utilities.printInt(expr);
    	                return;
    	        }
    	    
    	    if(flag == T_lower)  
    	    	this.lower = this.lower | operand;
    	    else 
    	    	this.upper = this.upper | operand;
    }
    
    
    
    /**
     * Find String
     * @param expr
     * @param flag
     * @param year
     * @param string
     * @param language
     */
    public String findString(int expr,int flag,int year,String string,TM_LANGUAGE language){
    	int check_day = -1;
    	int month = -1, day = -1;
    	int decade,century;
    	int bce_flag;

    	switch (expr){
    		case AAT_BIT:
    		case DATE_BIT:
    			switch (flag){
    			
    				case T_lower:
    					
    					day = ( lower & GET_DAY ) >> 7;
    					month = ( lower & GET_MONTH) >> 12;
    					check_day = 1;
    					break;
    					
    				case T_upper:
    					
    					day = ( upper & GET_DAY ) >> 7;
    					month = ( upper & GET_MONTH) >> 12;
    					check_day = Utilities.findNumOfDays(month,year);
    					break;
    					
    				default : System.out.print("UNKNOWN FLAG IN FindString");
    						  break;
    			}
    			
    			if (day == check_day)
    				string = year + " "+Utilities.findMonth(month,language);
    			else 
    				string = year + " "+ Utilities.findMonth(month,language) + " " +day;
    			break;
    			
    		case EXPL_DEC_BIT:    //This is an explicit decade expression
    			
    			if ((year % 10) != 0)
    				year -= 9;
    			
    			if (language == TM_LANGUAGE.TM_GREEK )
    				string = "Δεκαετία του " + year;
    			else
    				string = "Decade of " + year;
       						
    			break;
    			
    		case IMPL_DEC_BIT:    //This is an implicit decade expression

    			if (flag == T_lower) 
    				bce_flag = (lower & BCE_FLAG); 			
    			else 
    				bce_flag = (upper & BCE_FLAG);
    			
    			decade = ((year % 100)/ 10) + 1;
    			century = (year / 100) + 1;
    			
    			if (bce_flag != 0) 
    				decade = 11 - decade;
    			
    			if (language == TM_LANGUAGE.TM_GREEK)
    				string = Utilities.findArithmetic(decade,language) + " δεκαετία του "+ century +"ου αιώνα";
   
    			else
    				string = Utilities.findArithmetic(decade,language) + " decade of "+ century + Utilities.findSuffix(century) +" century";
    				
    			break;
    			
    		case CENTURY_BIT:     //This is a century expression
    			
    			century = (year/100)+1 ;
    			
    			if (language == TM_LANGUAGE.TM_GREEK)
    				string = century+"ος αιώνας";
    			else
    				string = century + Utilities.findSuffix(century) + " century";
    			
    			break;
    			
    		case CIRCA_BIT:       //This is a circa expression
    			
    			if (flag == T_lower) 
    				bce_flag = (lower & BCE_FLAG);
                else 
                	bce_flag = (upper & BCE_FLAG);
    			
    			if (bce_flag != 0) 
    				year = (flag == T_lower) ? (year - 10) : (year + 10);
    			else 
    				year = (flag == T_lower) ? (year + 10) : (year - 10);
    				
    			string = year + " , ca.";
    			break;
    			
    		default: //This is a year expression
    			string = year + " ";
    		}
    	
    	return string;
    }
    
    
    /**
     * present converts Time Primitives into Strings.
     * @param string
     * @param language
     */
    public String present(TM_LANGUAGE language){
    	
    	String string = new String();
    	
    	int l_year,u_year;
    	int l_month,u_month;
    	int l_flag,u_flag,l_day;            
    	int flag,l_period_flag,u_period_flag;
    	
    	l_year = this.lower >> 16;
    	l_flag = (this.lower & BCE_FLAG);
    	if (l_flag != 0) 
    		l_year = -l_year;

    	u_year =  this.upper >> 16;
    	u_flag = (this.upper & BCE_FLAG);
    	if (u_flag != 0) 
    		u_year = -u_year;

    	l_period_flag = (this.lower & PERIOD_EXPR_BIT);
    	u_period_flag = (this.upper & PERIOD_EXPR_BIT);
    	
    	if (l_flag == u_flag){
    		
	    	if (l_period_flag != 0 || u_period_flag != 0) { //This is a period expression
	    		
	    		
	    		int mode;
	    		mode = (this.lower & MODE_BIT);
	    		switch (mode){
	    	   	   	case MODE_BIT:  //This is an implicit period expression
	    	   	   		
	    				int mod,dif,century,year;
	    				
	    				//if l_flag != 0 then this is a bce expression
	    				dif = (l_flag != 0) ? (l_year - u_year) : (u_year - l_year);
	    				year = (l_flag != 0) ? u_year : l_year;
	    				century = (year/100) +1;
	    				
	    				if (dif < 30) {
	    					mod = ((year%100) / 25);
	    					mod = (l_flag != 0) ? (4-mod) : mod+1;
	    					
	    					if (language == TM_LANGUAGE.TM_GREEK)
	    				   		string = Utilities.intToAlnum(mod) + "' τέταρτο "+ century +"ου αιώνα";
	    					else
	    				   		string = mod + Utilities.findSuffix(mod) +" quarter " + century + Utilities.findSuffix(century) +" century" ;
	
	    				} 
	    				
	    				if (dif > 50){
	    					mod = ((year%100) / 40);
	    					mod = (l_flag != 0) ? (2-mod) : mod+1;
	    					
	    					if (language == TM_LANGUAGE.TM_GREEK)
	    				   		string = Utilities.intToAlnum(mod) + "' μισό " + century + "ου αιώνα";
	    					else
	    				   		string = mod + Utilities.findSuffix(mod) + " half " + century +  Utilities.findSuffix(century) + " century";
	    	
	    				}
	    				
	    				if ( (dif > 30) && (dif < 50)) {
	    					
	    					mod = ((year%100) /30);
	    					mod = (l_flag != 0) ? (3-mod) : mod+1;

	    					switch(mod){
	    				    	
	    					   	case 1: 
	    					   		if (language == TM_LANGUAGE.TM_GREEK) 
	    					   			string = "Αρχές " + century + "ου αιώνα"; 
	    					   		else
	    					   			string = "early " + century + Utilities.findSuffix(century)+ " century";
	    							break;
	    							
	    					   	case 2: 
	    					   		if (language == TM_LANGUAGE.TM_GREEK) 
	    					   			string = "Μέσα " + century + "ου αιώνα";
	    					   		else
	    					   			string = "mid " + century + Utilities.findSuffix(century) +" century";  
	    							break;
	    					   	case 3: 
	    					   		if (language == TM_LANGUAGE.TM_GREEK) 
	    					   			string = "Τέλη " + century + "ου αιώνα"; 
	    					   		else 
	    					   			string = "late " + century + Utilities.findSuffix(century) +" century";  
	    							break;
	    				   }
	    				}
	    				break;
	    	   	   default: 	
	    				String tmp1,tmp2;
	    				
	    				tmp1 = null;
	    				tmp2 = null;
	    				
	    				if ((lower^NEGATIVE_INF) == 0) {
	    					
	    					flag = (upper &  EXPR_BITS);
	    					tmp1 = findString(flag,T_upper,u_year,tmp1,language);
	    					
	    					if (language == TM_LANGUAGE.TM_GREEK) 
	    						string = "πρωϊμότερο του "; 
	    					else
	    						string = "ante quem ";
	    					string+= tmp1;
	    				}
	    				else {
	    					
	    					flag = (lower &  EXPR_BITS);
	    					tmp1 = findString(flag,T_lower,l_year,tmp1,language);
	
	    					if ((upper^POSITIVE_INF) == 0){
	    						
	    						if(language == TM_LANGUAGE.TM_GREEK) 
	    							string = "υστερότερο του "; 
	    						else
	    							string = "post quem ";
	    						string+= tmp1;
	    					}
	    					else{
	    						//this is an explicit period expression
	    						flag = (upper &  EXPR_BITS);
	    						tmp2 = findString(flag,T_upper,u_year,tmp2,language);
	    						string+= tmp1;
	    						string = tmp1 + " - " + tmp2;
	    	   	   			}
	    				  }
	    				break;
	    			}
	    		if (l_flag != 0) {
	    			
	    	   		if(language == TM_LANGUAGE.TM_GREEK) 
	    	   			string+=" π.Χ."; 
	    	   		else
	    	   			string+=" BCE";	
	    		}
	    	}
	    	else{   //Definately not a period expression!!
	    		
	    		flag = (lower & EXPR_BITS);
	    		int year;
	    		
	    		l_day = ( lower & GET_DAY) >> 7;
	     		l_month = ( lower & GET_MONTH) >> 12;
	     		u_month = ( upper & GET_MONTH) >> 12;
    		
	    	   	switch (flag){
	    			case AAT_BIT:  /*this is used temporarly for the AAT base.
	    							It must be deleted.*/
	    			case DATE_BIT:
	    				
	                   if ((upper&(~UPPER_FLAG)) == lower)     //This is a date expression
	                        string = l_year +" "+ Utilities.findMonth(l_month,language) +" "+ l_day;
	                   else{
	                	   
	    					if ((flag == AAT_BIT) && (l_month != u_month))
	                        	string = String.valueOf(l_year);
	    					else
	                        	string = l_year +" "+Utilities.findMonth(l_month,language);
	    				}
	    				break;
	    				
	    			default:
	    	   			year = (l_flag != 0 && (flag != CIRCA_BIT)) ? u_year : l_year;
	    	   			string = findString(flag,T_lower,year,string,language);
	    	    }
	    	   	
	    	   	if (l_flag != 0){
	    		   
	    	   		if(language == TM_LANGUAGE.TM_GREEK) 
	    				string +=" π.Χ."; 
	    	   		else
	    			   string += " BCE";
	    		}
	    	}
    	}
    	else{  //(l_flag != u_flag)  Definately a period expression!!
    		
	    	String tmp1,tmp2;
	
	    	tmp1 = null;
			tmp2 = null;
	    	
	    	if ((lower^NEGATIVE_INF) == 0){
	    	
	    		if ((upper^POSITIVE_INF) == 0) 
	    			string = "AllTime";
	    		else {
	    			flag = (upper & EXPR_BITS);
	    			tmp2 = findString(flag,T_upper,u_year,tmp2,language);		
	    			
	    			if(language == TM_LANGUAGE.TM_GREEK)
	    				string = "πρωϊμότερο του "; 
	    			else
	    				string = "ante quem "; 
	    			string+=tmp2;
	    		}
	    	} 
	    	else{
	    		if ((upper^POSITIVE_INF) == 0){
	    			
	    			flag = (lower & EXPR_BITS);
	    			tmp2 = findString(flag,T_lower,l_year,tmp2,language);
	    			
	    			if(language == TM_LANGUAGE.TM_GREEK)
	    				string = "υστερότερο του "; 
	    			else
	    				string = "post quem ";
	    			string+=tmp2;
	    			
	    			if(language == TM_LANGUAGE.TM_GREEK) 
	    				string+=" π.Χ.";
	    			else
	    				string+=" BCE";
	    		}
	    	 	else{
	    	 		
	    			flag = (lower & EXPR_BITS);
	    			tmp1 = findString(flag,T_lower,l_year,tmp1,language);
	    			
	    			flag = (upper & EXPR_BITS);
	    			tmp2 = findString(flag,T_upper,u_year,tmp2,language);		
	
	    			string = tmp1;
	    			
	    			if(language == TM_LANGUAGE.TM_GREEK) 
	    				string+=" π.Χ. - "; 
	    			else
	    				string+=" BCE - ";
	    			string+=tmp2;
	    			
	    			if(language == TM_LANGUAGE.TM_GREEK) 
	    				string+=" μ.Χ."; 
	    			else
	    				string+=" CE";
	    		   }
	    	}
    	}
    	
    	return string;
    }
}	


