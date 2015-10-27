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

%{		
	import java.io.*;
	import java.util.Calendar;	
	import date.Date;
	import time.Time;
	import year.SingleYear;
	import error.Error;

%}


%start  Time_End

%token	<ival>	INTEGEROBJ DASHED_INTEGER IDENTIFIER
%token	<ival>	CE BCE AIWNA AIWNAS CENTURY DECAETIA TOY AAT ULAN
%token	<ival>	ARXES MESA TELH MISO TETARTO CA
%token	<ival>	LETTER NOW ALLTIME ANTE POST QUEM TO
%token	<ival>	PRIOR LATER THAN PROIMOTERO YSTEROTERO PRIN APO META
%token	<ival>	arithmetic Month genitive_number nominative_number
%type	<ival>	century_term_expr century_part_clause english_century century_part
%type	<ival>	quem_clause english_quem greek_quem 

%type	<obj>	time_value period_expression AATperiod_expression 
%type	<obj>	BCEperiod_expression BCEtime_value 
%type	<obj>	AATexpressions ULANexpression 

%type	<obj>	date_expression american_date AATdate_expression now_expression
%type   <obj>   period_clause BCEperiod_clause quem_part_expression
%type	<obj> 	year_expression century_expression decade_expression
%type	<obj> 	circa_expression


%%


	Time_End	:	 Time_Object{}
				;

	Time_Object	:  time_value { System.out.println("time_value"); parsedTimeVal[0] = (Time)$1;}

				|  BCEtime_value {System.out.println("bce_time_value"); 	parsedTimeVal [0]= (Time)$1;}

				;


	time_value	: date_expression{
						Date date = (Date) $1;

						if(date.flag == ERROR)
							return (ERROR);

						Time time = new Time();

						if(date.day == UNDEFINED){

							time.store(T_lower,date.year,date.month,1,ce,date.flag);
							date.day = Utilities.findNumOfDays(date.month,date.year);
							time.store(T_upper,date.year,date.month,date.day,ce,date.flag);
						}
						else{
							time.store(T_lower,date.year,date.month,date.day,ce,date.flag);
							time.store(T_upper,date.year,date.month,date.day,ce,date.flag);
						}

						$$ = time;
				}
				
				| year_expression{

						SingleYear year = (SingleYear)$1;

						Time time = new Time();
						
						if(year.flag == ERROR)
							return (ERROR);

						time.store(T_lower,year.value,1,1,ce);
						time.store(T_upper,year.value,12,31,ce);

						$$ = time;
				}
				
				| decade_expression{

						SingleYear decadeObj = (SingleYear)$1;

						Time time = new Time();

						if(decadeObj.flag == ERROR)
							return (ERROR);

						if(decadeObj.value != 0){
							time.store(T_lower,decadeObj.value,1,1,ce,decadeObj.flag);
						}
						else {
							//System.out.println(" lower 1-1-1");
							time.store(T_lower,1,1,1,ce,decadeObj.flag);
						}
						time.store(T_upper,decadeObj.value+9,12,31,ce,decadeObj.flag);

						$$ = time;

				}  
				
				| century_expression{
						System.out.println("century_expression");
						SingleYear centuryObj = (SingleYear)$1;

						if(centuryObj.flag == ERROR)
							return (ERROR);

						Time time = new Time();

						centuryVal = (centuryObj.value-1)*100;
						if(centuryVal != 0){
							time.store(T_lower,centuryVal,1,1,ce,centuryObj.flag);
						}
						else{
							time.store(T_lower,1,1,1,ce,centuryObj.flag);
						}

						centuryVal+=99;
						time.store(T_upper,centuryVal,12,31,ce,centuryObj.flag);	

						$$ = time;

				}

				| circa_expression{

						SingleYear circa = (SingleYear)$1;

						if(circa.flag == ERROR)
							return (ERROR);

						Time time = new Time();
						time.store(T_lower, circa.value-10,1,1,ce,Circa);
						time.store(T_upper,circa.value+10,12,31,ce,Circa);

						$$ = time;
	           	}
				
				| now_expression{

						Date date = (Date)$1;

						Time time = new Time();
						time.store(T_lower,date.year,date.month,date.day,ce,date.flag);
						time.store(T_upper,date.year,date.month,date.day,ce,date.flag);

						$$ = time;
				}
				
				| ALLTIME{

						Time time = new Time();

						time.store(T_lower);
						time.store(T_upper);

						$$ = time;
				}
				
				| period_expression {$$ = (Time)$1;}
				
				| AATexpressions  	{$$ = (Time)$1;}
				
				| ULANexpression  	{$$ = (Time)$1;}
				;




	date_expression  : INTEGEROBJ  Month  INTEGEROBJ{
							
							Date date = new Date();

							date.flag = SIS_DATE;	
							
							if (Utilities.checkYear($1) == ERROR)
								date.flag = ERROR;

							if (Utilities.checkMonth($2) == ERROR)
								date.flag = ERROR;

							if (Utilities.checkDay($1,$2,$3) == ERROR)
								date.flag = ERROR;

							date.year = $1;
							date.month = $2;
							date.day = $3;

							$$ = date;
						}
					| INTEGEROBJ  Month {
						
							Date date = new Date();

							date.flag = SIS_DATE;	
							
							if (Utilities.checkYear($1) == ERROR)
								date.flag = ERROR;

							if (Utilities.checkMonth($2) == ERROR)
								date.flag = ERROR;

							date.year = $1;
							date.month = $2;
							date.day = UNDEFINED;

							$$ = date;
						}
					| INTEGEROBJ error {
							
							Date date = new Date();
							
							date.flag = ERROR;

							String msg = "*** Syntax Error in Date Expression";
							globalError[0].putMessage (msg);
							globalError[0].printMessage();

							$$ = date;
						}
					;



	year_expression	: INTEGEROBJ {

							SingleYear year = new SingleYear();
							year.flag = YEAR;

							if(Utilities.checkYear($1) == ERROR)
								year.flag = ERROR;
							year.value = $1;

							$$ = year;
					}
					;



	circa_expression	: CA INTEGEROBJ {$$ = circa($2);}
						
						| INTEGEROBJ ',' CA{$$ = circa($1);}
						;


	now_expression  :   NOW{

								Date date = new Date();

								date.year = Calendar.getInstance().get(Calendar.YEAR);
								date.month = Calendar.getInstance().get(Calendar.MONTH);
								date.day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
								date.flag = SIS_DATE;

								$$ = date;
					}
					;

	decade_expression	: DECAETIA TOY INTEGEROBJ {

								int num = $3;
								int mod = num % 10;

								SingleYear decadeObj = new SingleYear();
								
								decadeObj.flag = DECADE;
								if(mod != 0 ){

									String buffer;
									buffer = String.format(DECADE_ERROR ,num - mod ,num); 
									globalError[0].putMessage(buffer);
									globalError[0].printMessage();
									decadeObj.flag = ERROR;

								}

								decadeObj.value = num;
								
								$$ = decadeObj;								
						}

						| arithmetic DECAETIA TOY century_term_expr{
								SingleYear decade = new SingleYear();

								decade.value = (($4-1) * 100)+(($1-1)*10);
								decade.flag = IMPL_DECADE;

								$$ = decade;

						}
						
						;


	century_term_expr	: genitive_number AIWNA { $$ = $1;}


	                    | english_century  { $$ = $1; }

						;

	english_century		: nominative_number CENTURY {$$ = $1; }
						;


	century_expression	: nominative_number AIWNAS	{$$ = century($1);}

						| english_century			{$$ = century($1);}
						;



/*********************** AAT EXPRESSIONS ***********************/

	AATexpressions	:	AATdate_expression{	

								Time time = new Time();

								Date date = (Date)$1;

								if (date.month == 0 && date.day != 0){

									String buffer;
									String error = "Month (%d) is an invalid value, when followed by non zero day (%d). You should provide a non zero month value or a zero valued day value\n";
			 
									buffer = String.format(error ,date.month ,date.day); 
									globalError[0].putMessage(buffer);
									globalError[0].printMessage();

									$$ = time;
									return (ERROR);
								}

								if(date.day == 0){

									if(date.month != 0){

										time.store(T_lower,date.year,date.month,1,ce,SIS_DATE);
										date.day = Utilities.findNumOfDays(date.month,date.year);
										time.store(T_upper,date.year,date.month,date.day,ce,SIS_DATE);
									}
									else{

										time.store(T_lower,date.year,1,1,ce);
										time.store(T_upper,date.year,12,31,ce);
									}
								}
								else{

									time.store(T_lower,date.year,date.month,date.day,ce,SIS_DATE);
									time.store(T_upper,date.year,date.month,date.day,ce,SIS_DATE);
								}

								$$ = time;

					}

					|	AATperiod_expression {$$ = $1;}
					;


	AATdate_expression	: AAT INTEGEROBJ{

								Date date = new Date();

								date.day = $2%100;
								$2 /= 100;
								date.month = $2%100;
								date.year = $2/100;
								date.flag = 0;

								if(Utilities.checkYear(date.year) == ERROR)
									return (ERROR);

								if(date.month == 0){
									date.month = 1;
									date.flag = 1;
								}

								if(Utilities.checkMonth(date.month) == ERROR)
									return (ERROR);

								if(date.day == 0)
									date.day = Utilities.findNumOfDays(date.month,date.year);
								else{

									if(Utilities.checkDay(date.year,date.month,date.day) == ERROR)
										return (ERROR);
								}

								$$ = date;
						}
						
						| AAT error{

							String message = "*** Syntax Error: the format is: [AAT integer]";
							
							globalError[0].putMessage( message);
	                        globalError[0].printMessage();
	                        return(ERROR);						

						}

						;	

	AATperiod_expression	: AATdate_expression':'INTEGEROBJ{

									Time time = new Time();
									
									Date date = (Date)$1;

									int u_day,u_month, u_year;
									int uyear_expr=0;

									if(date.month == 0 && date.day !=0){

										String buffer;
										String error = "Month value (%d) is an invalid value, when followed by non zero day value (%d). You should provide a non zero month value or a zero valued day value\n";
										
										buffer = String.format( error,date.month,date.day);
										globalError[0].putMessage( buffer);
										globalError[0].printMessage();
										return ERROR;
									}

									u_day = $3%100;
									$3/= 100;
									u_month = $3%100;

									if(u_month == 0 &&  u_day != 0){

										String buffer;
										String error = "Month value (%d) is an invalid value, when followed by non zero day value (%d). You should provide a non zero month value or a zero valued day value\n";
										
										buffer = String.format( error,u_month,u_day);
										globalError[0].putMessage( buffer);
										globalError[0].printMessage();

										return ERROR;
									}

									if(u_month == 0){
										u_month = 12;
										uyear_expr = 1;
									}
									else{
										if(Utilities.checkMonth(u_month) == ERROR)
											return ERROR;
									}

									u_year = $3/100;
									if( Utilities.checkYear(u_year) == ERROR)
										return ERROR;

									if(u_day == 0)
										u_day = Utilities.findNumOfDays(u_month,u_year);
									else{

										if(Utilities.checkDay(u_year,u_month,u_day) == ERROR)
											return ERROR;
									}

									if(date.year > u_year){

										String buffer;
										String error = "The beginning year (%d) of the given period is greater than the ending one (%d)\n";
										
										buffer = String.format(error, date.year, u_year);

										globalError[0].putMessage( buffer);
										globalError[0].printMessage();
										
										return ERROR;
									}

									if(date.flag != 0)
										time.store(T_lower,date.year,date.month,date.day,ce,PERIOD_EXPR);
									else
										time.store(T_lower,date.year,date.month,date.day,ce,PERIOD_EXPR,SIS_DATE);

									if(uyear_expr != 0)
										time.store(T_upper,u_year,u_month,u_day,ce,PERIOD_EXPR);
									else
										time.store(T_upper,u_year,u_month,u_day,ce,PERIOD_EXPR,SIS_DATE);

									$$ = time;

							}

							| AATdate_expression error{

									String message = "*** Syntax Error: the format is: [AAT integer:integer]";
									globalError[0].putMessage( message);
		                        	globalError[0].printMessage();

		                        	return(ERROR);
									
							}
							;



	/*********************** ULAN EXPRESSIONS ***********************/

	ULANexpression	: american_date {

							Time time = new Time();

							Date date = (Date)$1;
							int flag = ce;

							if(date.year < 100)
								date.year+= 1900;

							time.store(T_lower,date.year,date.month,date.day,flag,SIS_DATE);
							time.store(T_upper,date.year,date.month,date.day,flag,SIS_DATE);

							$$ = time;
					}
					
					| ULAN '`'american_date'`'INTEGEROBJ'`'INTEGEROBJ{

							Time time = new Time();
							int flag,year;
							int l_year,u_year;

							Date date = (Date)$3;

							if ($7 < 0 || $7 > 1){

								String buffer = String.format("BC value (%d) is an unknown value. Legal values are 0-1\n",$7);
								
								globalError[0].putMessage( buffer);
								globalError[0].printMessage();
								
								return(ERROR);
							}

							flag = ($7 != 0) ? bce : ce;

							switch ($5){

								case 0:		//date...
									time.store(T_lower,date.year,date.month,date.day,flag,SIS_DATE);
									time.store(T_upper,date.year,date.month,date.day,flag,SIS_DATE);
									break;
								case 1:		//month-year
									time.store(T_lower,date.year,date.month,1,flag,SIS_DATE);
									time.store(T_upper,date.year,date.month,Utilities.findNumOfDays(date.month,date.year),SIS_DATE);
									break;
								case 2: 	//only year
									time.store(T_lower,date.year,1,1,flag);
									time.store(T_upper,date.year,12,31,flag);
									break;
								case 3:		//only decade
									year = date.year - (date.year%10); 
									l_year = ($7 != 0 ) ? (year+9) : year;
									u_year = ($7 != 0) ? (year) : (year+9);

									time.store(T_lower,l_year,1,1,flag,DECADE);
									time.store(T_upper,u_year,12,31,flag,DECADE);
									break;
								case 4:		//only century...
									year = date.year - (date.year%100); 
									l_year = ($7 !=0 ) ? (year+99) : year;
									u_year = ($7 !=0 ) ? (year) : (year+99);
									time.store(T_lower,l_year,1,1,flag,Century);
									time.store(T_upper,u_year,12,31,flag,Century);
								default:{
									
									String buffer;
									buffer = String.format("Level value (%d) is an unknown value. Legal values are 0-4\n",$5);
									globalError[0].putMessage( buffer);
									globalError[0].printMessage();
									return(ERROR);
									
								}
							}
							$$ = time;
					}
					
					| ULAN error{

							String message = "*** Syntax Error in ULAN date Expression. The format is: [ULAN `month-day-year`level`bc]";

							globalError[0].putMessage( message);
							globalError[0].printMessage();

		                    return(ERROR);
					}			
					;

	american_date	:	DASHED_INTEGER DASHED_INTEGER INTEGEROBJ{

							Date date = new Date();

							if(Utilities.checkYear($3) == ERROR)
								return ERROR;
							if(Utilities.checkMonth($1) == ERROR)
								return ERROR;
							if(Utilities.checkDay($3,$1,$2) == ERROR)
								return ERROR;

							date.year = $3;
							date.month = $1;
							date.day = $2;
							$$ = date;
					}
					
					| DASHED_INTEGER error{	
							
							String message = "*** Syntax Error in American date ";

							globalError[0].putMessage( message);
							globalError[0].printMessage();
	                        return(ERROR);
							
							
	               }
					;

	/*********************** PERIOD EXPRESSION ***********************/


	period_clause	: date_expression {	}

					| year_expression{
							
							Date date = new Date();

							SingleYear year = (SingleYear)$1;
							date.year = year.value;
							date.flag = year.flag;

							$$ = date;
					}
					
					| decade_expression{

							Date date = new Date();

							SingleYear year = (SingleYear)$1;
							date.year = year.value;
							date.flag = year.flag;

							$$ = date;
					}
					
					| century_expression{

							Date date = new Date();

							SingleYear year = (SingleYear)$1;
							date.year = (year.value-1)*100;
							date.flag = year.flag;

							$$ = date;
					}
					
					| circa_expression{

							Date date = new Date();

							SingleYear year = (SingleYear)$1;
							date.year = year.value;
							date.flag = year.flag;

							$$ = date;
					}
					;


	quem_clause	: english_quem {$$ = $1;}

				| greek_quem	{$$ = $1;}
				;


	english_quem	:	ANTE QUEM	{ $$ = 1; }

					|	POST QUEM	{ $$ = 2; }

					|	PRIOR TO 	{ $$ = 1; }

					|	LATER THAN  { $$ = 2; }
					;


	greek_quem	: PROIMOTERO TOY	{ $$ = 1;}

				| YSTEROTERO TOY	{ $$ = 2;}

				| PRIN APO			{ $$ = 1;}

				| META APO			{ $$ = 2;}
				;


	quem_part_expression	:	period_clause {$$ = $1;}

							|	now_expression {$$ = $1;}
							;


	century_part_clause	: ARXES { $$ = 1; }

						| MESA  { $$ = 2;}

						| TELH  { $$ = 3;}
						;



	century_part	: LETTER {$$ = $1; }

					| nominative_number {$$ = $1;}
					;


	period_expression	: period_clause '-' period_clause{

								if(periodExprChecks((Date) $1,(Date) $3) == ERROR)
									return ERROR;

								$$ = (Time)periodExprStore((Date) $1,(Date) $3);

						}

	                    | period_clause '-' now_expression{

								if(periodExprChecks((Date) $1,(Date) $3) == ERROR)
									return ERROR;

								$$ = periodExprStore((Date) $1,(Date) $3); 
	                    }

	                    | now_expression '-' period_clause{

	                   			if(periodExprChecks((Date) $1,(Date) $3) == ERROR)
									return ERROR;

								$$ = periodExprStore((Date) $1,(Date) $3);

	                    }

						| century_part_clause  century_term_expr{

								$2 = ($2-1) * 100;
								switch($1){

							  		case 1:
										Begin = ($2 != 0) ? $2 : 1;
										End   = $2+40;
										break;

							  		case 2:
										Begin = $2+30;
										End = $2+70;
										break;

							  		case 3:
										Begin = $2+60;
										End = $2+99;
										break;
								}

								Time time = new Time();

								time.store(T_lower,Begin,1,1,ce,IMPL_PERIOD);
								time.store(T_upper,End,12,31,ce,IMPL_PERIOD);

								$$ = time;
						}
						
						| century_part MISO century_term_expr{ 

								$3 = ($3-1) * 100;
								switch($1){

								  case 1:
										Begin = ($3 != 0) ? $3 : 1;
										End   = $3+60;
										break;

								  case 2:
										Begin = $3+40;
										End = $3+99;
										break;
								}

								Time time = new Time();

								time.store(T_lower,Begin,1,1,ce,IMPL_PERIOD);
								time.store(T_upper,End,12,31,ce,IMPL_PERIOD);

								$$ = time;
						}
						
						| century_part TETARTO century_term_expr {

								$3 = ($3-1) * 100;
								switch($1){

								  case 1:
										Begin = ($3 != 0) ? $3 : 1;
										End   = $3+27;
										break;

								  case 2:
										Begin = $3+25;
										End = $3+52;
										break;

								  case 3:
										Begin = $3+50;
										End = $3+77;
										break;

								  case 4:
										Begin = $3+75;
										End = $3+99;
										break;
								}

								Time time = new Time();

								time.store(T_lower,Begin,1,1,ce,IMPL_PERIOD);
								time.store(T_upper,End,12,31,ce,IMPL_PERIOD);

								$$ = time;
						}

						|	quem_clause quem_part_expression{

								Time time = new Time();

								Date date = (Date) $2;

								switch($1){

									case 1:
										time.store(T_lower);
										Utilities.store(time,T_upper,date.flag, date.year, date.month, date.day);
										break;

									case 2:
										Utilities.store(time, T_lower,date.flag, date.year, date.month, date.day);
										time.store(T_upper);
										break;
								}
						}

						| error '-' period_clause{

								String message = "*** Syntax Error in Period Expression ";
				              
				                globalError[0].putMessage( message);
				                globalError[0].printMessage();
				                
				                return(ERROR);								
						}
						;             



	/************************** BCE EXPRESSIONS *******************************/

	BCEtime_value	: BCEperiod_clause{
							System.out.println("BCEperiod_clause");
							Time time = new Time();
							Date date = (Date)$1;

							switch(date.flag){

								case SIS_DATE:

									if(date.day == UNDEFINED){

										time.store(T_lower,date.year,date.month,1,bce,date.flag);
										time.store(T_upper,date.year,date.month,31,bce,date.flag);
									}
									else{										

										time.store(T_lower,date.year,date.month,date.day,bce,date.flag);
										time.store(T_upper,date.year,date.month,date.day,bce,date.flag);
									}
									break;

								case YEAR:

									time.store(T_lower,date.year,1,1,bce);
									time.store(T_upper,date.year,13,31,bce);
									break;

								case DECADE:
								case IMPL_DECADE:

									time.store(T_lower,date.year+9,1,1,bce,date.flag);

									if(date.year != 0)
										time.store(T_upper,date.year,12,31,bce,date.flag);
									else
										time.store(T_upper,1,12,31,bce,date.flag);
									break;

								case Century:

									time.store(T_lower,date.year+99,1,1,bce,date.flag);

									if(date.year != 0)
										time.store(T_upper,date.year,12,31,bce,date.flag);
									else
										time.store(T_upper,1,12,31,bce,date.flag);

									break;

								case Circa:

									time.store(T_lower,date.year+10,1,1,bce,date.flag);
									time.store(T_upper,date.year-10,12,31,bce,date.flag);
									break;

								default:
									return (ERROR);

									
							}

							$$ = time;
					}

					| BCEperiod_expression  {$$ = $1; }
					;


	BCEperiod_clause	: period_clause BCE{

								Date date = new Date();
								Date date_1 = (Date)$1;

								date.flag = date_1.flag;

								if(date_1.flag == IMPL_DECADE){
									decadeVal = date_1.year%100;
									centuryVal = date_1.year/100;
									date_1.year = (centuryVal*100) + ((9 - decadeVal/10)*10);
								}
								date.year = date_1.year;
								date.month = date_1.month;
								date.day = date_1.day;

								$$ = date;
						}
						;


	BCEperiod_expression	: period_clause'-'period_clause BCE{

									Date date_1 = (Date)$1;
									Date date_2 = (Date)$3;

									if(date_1.flag == ERROR || date_2.flag == ERROR)
										return ERROR;

									Time time = new Time();

									if(date_1.flag == IMPL_DECADE)
										date_1.year = ((date_1.year/100)*100) + ((9 - (date_1.year%100)/10)*10);

									if(date_2.flag == IMPL_DECADE)
										date_2.year = ((date_2.year/100)*100) + ((9 - (date_2.year%100)/10)*10);

									if(Utilities.checkDates(date_1,date_2,bce) == ERROR)
										return ERROR;

									date_1.flag = Utilities.convert(date_1.flag);
									date_2.flag = Utilities.convert(date_2.flag);

									time = Utilities.store(time, T_lower,date_1.flag,date_1.year,date_1.month,date_1.day);
									time = Utilities.store(time, T_upper,date_2.flag,date_2.year,date_2.month,date_2.day);

									$$ = time;
							}

							| BCEperiod_clause'-'period_clause CE{

									Date date_1 = (Date)$1;
									Date date_2 = (Date)$3;

									if(date_1.flag == ERROR || date_2.flag == ERROR)
										return ERROR;

									Time time = new Time();

									date_1.flag = Utilities.convert(date_1.flag);

									time = Utilities.store(time, T_lower,date_1.flag,date_1.year,date_1.month,date_1.day);
									time = Utilities.store(time, T_upper,date_2.flag,date_2.year,date_2.month,date_2.day);

									$$ = time;
							}	

							| century_part_clause  century_term_expr BCE{

									Time time = new Time();

									$2 = ($2-1) * 100;
									switch($1){
									  case 1:
											Begin = $2+99;
											End   = $2+60;
											break;
									  case 2:
											Begin = $2+70;
											End = $2+30;
											break;
									  case 3:
											Begin = $2+40;
											End = ($2 != 0) ? $2 : 1;
											break;
									}

									time.store(T_lower,Begin,1,1,bce,IMPL_PERIOD);
									time.store(T_upper,End,12,31,bce,IMPL_PERIOD);

									$$ = time;
								}
							| century_part MISO century_term_expr BCE{

									Time time = new Time();

									$3 = ($3-1) * 100;
									switch($1){
									  case 1:
											Begin = $3+99;
											End   = $3+40;
											break;
									  case 2:
											Begin = $3+60;
											End = ($3 !=0) ? $3 : 1;
											break;
									}

									time.store(T_lower,Begin,1,1,bce,IMPL_PERIOD);
									time.store(T_upper,End,12,31,bce,IMPL_PERIOD);

									$$ = time;
							}

							| century_part TETARTO century_term_expr BCE{

									Time time = new Time();

									$3 = ($3-1) * 100;
									switch($1){
									  case 1:
											Begin = $3+99;
											End = $3+75;
											break;
									  case 2:
											Begin = $3+77;
											End = $3+50;
											break;
									  case 3:
											Begin = $3+52;
											End = $3+25;
											break;
									  case 4:
											Begin = $3+27;
											End = ($3 != 0)? $3 : 1;
											break;
									}

									time.store(T_lower,Begin,1,1,bce,IMPL_PERIOD);
									time.store(T_upper,End,12,31,bce,IMPL_PERIOD);

									$$ = time;

							}
							
							|	quem_clause BCEperiod_clause{

								Time time = new Time();
								Date date = (Date)$2;

								date.flag = Utilities.convert(date.flag);

								switch($1){
									case 1:
										time.store(T_lower);
										time = Utilities.store(time,T_upper,date.flag,date.year,date.month,date.day);
										break;
									case 2:
										time = Utilities.store(time,T_lower,date.flag,date.year,date.month,date.day);
										time.store(T_upper);
								}

							}
							;


%%	


	private int centuryVal 	= 0;
	
	private int decadeVal 	= 0;

	private int Begin 		= 0;
	
	private int End 		= 0;

	int lowerVal ;

	int upperVal ;
	

	/*Period expression checks*/
	private int periodExprChecks(Date date_1, Date date_2){

		if(date_1.flag == ERROR || date_2.flag == ERROR)
			return ERROR;

		if(Utilities.checkDates(date_1,date_2,ce)  == ERROR)
			return ERROR;
		return 1;
	}

	/*Period expression store*/
	private Time periodExprStore(Date date_1, Date date_2){
		
		Time time = new Time();

		time = Utilities.store(time,T_lower,date_1.flag,date_1.year,date_1.month,date_1.day);
		time = Utilities.store(time,T_upper,date_2.flag,date_2.year,date_2.month,date_2.day);

		return time;
	}
	
	/*Helper function that constructs century*/
	private SingleYear century(int num ){

		SingleYear century = new SingleYear();

		century.value = num;
		century.flag = Century;
							
		return century;
	}

	/*Helper function that constructs circa*/
	private SingleYear circa(int num){
		
		SingleYear circa = new SingleYear();

		if(Utilities.checkYear(num) == ERROR)
			circa.flag = ERROR;
		
		circa.value = num;
		circa.flag = Circa;

		return circa;
	}

	/* a reference to lexer object */
	private Yylex lexer;


	/* interface to the lexer */
	private int yylex() {

		int yyl_return = -1;

	    try {
	      yylval = new ParserVal(0);
	      yyl_return = lexer.yylex();
	    }
	    catch (IOException e) {
	      System.err.println("IO error :"+e);
    	}
    	
    	return yyl_return;
	}


	/* error reporting */
	public void yyerror (String error) {
	  	System.err.println ("Error: " + error);
	}


	/* lexer is created in the constructor */
	public Parser(Reader r) {
		lexer = new Yylex(r, this);
	}

	/* returns the error message */
	public String getTimeErrorMessage(){
		String error = globalError[0].getMessage();
		globalError[0].reset();

		return error;
	}

	public int[] timeParse( String parseStr) throws IOException{

		Parser yyparser;

		parsedTimeVal[0] = new Time();

		globalError[0] = new Error();

		int[] timeValues = new int[2];

		//Create temp file 
		String property = "java.io.tmpdir";	
	    String tempDir = System.getProperty(property);

		File tempFile = File.createTempFile("tmp", ".txt", new File(tempDir));
		String filename = tempFile.getPath();

		parsedTimeVal[0].set_upper_lower(0, 0);  //initialize lower and upper values

		
		//write parseStr to file
		Writer writer = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream(filename), "utf-8"));

		writer.write(parseStr);
		writer.close();

		//Parse the file 
		yyparser = new Parser(new FileReader(filename));
		yyparser.yyparse();

		tempFile.delete();	//delete temp file

		timeValues = parsedTimeVal[0].getUpperLower(); //store the lower and upper values

		if(timeValues[0] != 0 || timeValues[1] != 0)
			return timeValues;
		else 
			return null;
	}		