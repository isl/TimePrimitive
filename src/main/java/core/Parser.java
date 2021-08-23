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



//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";



package core;



//#line 7 "parser.y"
		
	import java.io.*;
	import java.util.Calendar;	
	import date.Date;
	import time.Time;
	import year.SingleYear;
	import error.Error;
        import java.nio.charset.StandardCharsets;

//#line 26 "Parser.java"




public class Parser
             implements TimeFlags
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short INTEGEROBJ=257;
public final static short DASHED_INTEGER=258;
public final static short IDENTIFIER=259;
public final static short CE=260;
public final static short BCE=261;
public final static short AIWNA=262;
public final static short AIWNAS=263;
public final static short CENTURY=264;
public final static short DECAETIA=265;
public final static short TOY=266;
public final static short AAT=267;
public final static short ULAN=268;
public final static short ARXES=269;
public final static short MESA=270;
public final static short TELH=271;
public final static short MISO=272;
public final static short TETARTO=273;
public final static short CA=274;
public final static short LETTER=275;
public final static short NOW=276;
public final static short ALLTIME=277;
public final static short ANTE=278;
public final static short POST=279;
public final static short QUEM=280;
public final static short TO=281;
public final static short PRIOR=282;
public final static short LATER=283;
public final static short THAN=284;
public final static short PROIMOTERO=285;
public final static short YSTEROTERO=286;
public final static short PRIN=287;
public final static short APO=288;
public final static short META=289;
public final static short arithmetic=290;
public final static short Month=291;
public final static short genitive_number=292;
public final static short nominative_number=293;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,   26,   26,    8,    8,    8,    8,    8,    8,    8,
    8,    8,    8,   15,   15,   15,   22,   25,   25,   18,
   24,   24,    1,    1,    3,   23,   23,   13,   13,   17,
   17,   10,   10,   14,   14,   14,   16,   16,   19,   19,
   19,   19,   19,    5,    5,    6,    6,    6,    6,    7,
    7,    7,    7,   21,   21,    2,    2,    2,    4,    4,
    9,    9,    9,    9,    9,    9,    9,    9,   12,   12,
   20,   11,   11,   11,   11,   11,   11,
};
final static short yylen[] = {                            2,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    3,    2,    2,    1,    2,    3,    1,
    3,    4,    2,    1,    2,    2,    1,    1,    1,    2,
    2,    3,    2,    1,    7,    2,    3,    2,    1,    1,
    1,    1,    1,    1,    1,    2,    2,    2,    2,    2,
    2,    2,    2,    1,    1,    1,    1,    1,    1,    1,
    3,    3,    3,    2,    3,    3,    2,    3,    1,    1,
    2,    4,    4,    3,    4,    4,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,   56,   57,   58,    0,
   59,   20,   10,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   27,    0,    0,   44,   45,
    2,   11,   29,   70,    3,   12,   13,    0,   34,    0,
    0,    0,    0,    0,    0,    0,    0,    1,    0,   16,
    0,    0,   38,    0,    0,   31,   30,   36,    0,   18,
   46,   47,   48,   49,   50,   51,   52,   53,    0,   26,
   25,    0,    0,    0,   24,    0,    0,    0,   39,   55,
    0,   77,   67,   40,   42,   41,   43,   33,    0,    0,
   71,    0,    0,   68,   14,   19,   37,   21,    0,    0,
   23,   74,    0,    0,   32,   63,   62,    0,    0,    0,
   22,   75,   76,   72,   73,    0,    0,   35,
};
final static short yydgoto[] = {                         24,
   74,   25,   26,   27,   28,   29,   30,   31,   32,   33,
   34,   35,   36,   37,   79,   39,   40,   41,   42,   43,
   83,   84,   85,   86,   87,   48,
};
final static short yysindex[] = {                      -187,
  -28,  -44, -240, -243, -237,  -86,    0,    0,    0, -229,
    0,    0,    0, -244, -233, -225, -242, -223, -222, -247,
 -228, -220, -239,    0, -266,    0, -241, -236,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -49,
   14,  -43,   16,    0,    0,    0,    0,    0, -235,    0,
 -193, -209,    0, -191, -190,    0,    0,    0, -186,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -198,    0,
    0, -189, -179, -168,    0, -266, -266, -239,    0,    0,
 -167,    0,    0,    0,    0,    0,    0,    0, -182, -235,
    0, -236, -235,    0,    0,    0,    0,    0,    9, -266,
    0,    0, -164, -160,    0,    0,    0, -157, -153, -149,
    0,    0,    0,    0,    0,   13, -147,    0,
};
final static short yyrindex[] = {                         0,
    0,    1,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -238,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    4,    0,  111,
  112,    0,  113,    5,    6,    7,    8,    0,    0,    0,
    3,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  114,    0,    0,    0,    0,    0,    0,
  115,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  116,  117,    0,    0,    0,  118,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  -63,    0,  -14,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  119,   61,    0,  -13,  -16,   93,
    0,  122,  123,  124,  125,    0,
};
final static int YYTABLESIZE=269;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         52,
   17,   92,   15,    4,    5,    7,    6,    8,   89,   59,
   75,   81,  103,  104,   80,   53,   49,   54,   56,   57,
    2,    2,   55,   70,   71,   72,   73,   60,    4,    4,
   76,   77,   94,   60,   60,   61,  111,   10,   10,   12,
   67,   64,   65,   66,   69,   17,   62,   15,   39,   40,
   42,   41,   43,   22,   22,   63,   78,   78,   90,   68,
   93,   75,   75,   95,   96,   97,   98,  100,    1,    2,
    3,    3,  101,  106,  105,  108,  109,    4,  107,    5,
    6,    7,    8,    9,   71,   75,   10,   11,   12,   13,
   14,   15,  102,   91,   16,   17,  112,   18,   19,   20,
  113,   21,   22,  114,  110,   23,  115,  116,  117,  118,
   28,    9,   69,   64,   54,   65,   66,   61,   38,   99,
   82,   44,   45,   46,   47,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   58,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   88,    0,    0,    0,
    0,   50,    0,    0,    0,    0,    0,   91,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   51,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   17,   17,   15,   15,   39,   40,   42,   41,   43,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         44,
    0,   45,    0,    0,    0,    0,    0,    0,   58,   96,
   25,   28,   76,   77,   28,  256,   45,  258,  256,  257,
  257,  257,  266,  263,  264,  292,  293,  257,  265,  265,
  272,  273,   49,  272,  273,  280,  100,  274,  274,  276,
  288,  284,  266,  266,  265,   45,  280,   45,   45,   45,
   45,   45,   45,  290,  290,  281,  293,  293,   45,  288,
   45,   76,   77,  257,  274,  257,  257,  266,  256,  257,
  258,  258,  262,   90,  257,   92,   93,  265,   92,  267,
  268,  269,  270,  271,  264,  100,  274,  275,  276,  277,
  278,  279,  261,  261,  282,  283,  261,  285,  286,  287,
  261,  289,  290,  261,   96,  293,  260,  257,   96,  257,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   59,
   28,    0,    0,    0,    0,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  256,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  256,   -1,   -1,   -1,
   -1,  256,   -1,   -1,   -1,   -1,   -1,  261,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  291,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  260,  261,  260,  261,  261,  261,  261,  261,  261,
};
}
final static short YYFINAL=24;
final static short YYMAXTOKEN=293;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,"','",
"'-'",null,null,null,null,null,null,null,null,null,null,null,null,"':'",null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,"'`'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,"INTEGEROBJ","DASHED_INTEGER","IDENTIFIER","CE",
"BCE","AIWNA","AIWNAS","CENTURY","DECAETIA","TOY","AAT","ULAN","ARXES","MESA",
"TELH","MISO","TETARTO","CA","LETTER","NOW","ALLTIME","ANTE","POST","QUEM","TO",
"PRIOR","LATER","THAN","PROIMOTERO","YSTEROTERO","PRIN","APO","META",
"arithmetic","Month","genitive_number","nominative_number",
};
final static String yyrule[] = {
"$accept : Time_End",
"Time_End : Time_Object",
"Time_Object : time_value",
"Time_Object : BCEtime_value",
"time_value : date_expression",
"time_value : year_expression",
"time_value : decade_expression",
"time_value : century_expression",
"time_value : circa_expression",
"time_value : now_expression",
"time_value : ALLTIME",
"time_value : period_expression",
"time_value : AATexpressions",
"time_value : ULANexpression",
"date_expression : INTEGEROBJ Month INTEGEROBJ",
"date_expression : INTEGEROBJ Month",
"date_expression : INTEGEROBJ error",
"year_expression : INTEGEROBJ",
"circa_expression : CA INTEGEROBJ",
"circa_expression : INTEGEROBJ ',' CA",
"now_expression : NOW",
"decade_expression : DECAETIA TOY INTEGEROBJ",
"decade_expression : arithmetic DECAETIA TOY century_term_expr",
"century_term_expr : genitive_number AIWNA",
"century_term_expr : english_century",
"english_century : nominative_number CENTURY",
"century_expression : nominative_number AIWNAS",
"century_expression : english_century",
"AATexpressions : AATdate_expression",
"AATexpressions : AATperiod_expression",
"AATdate_expression : AAT INTEGEROBJ",
"AATdate_expression : AAT error",
"AATperiod_expression : AATdate_expression ':' INTEGEROBJ",
"AATperiod_expression : AATdate_expression error",
"ULANexpression : american_date",
"ULANexpression : ULAN '`' american_date '`' INTEGEROBJ '`' INTEGEROBJ",
"ULANexpression : ULAN error",
"american_date : DASHED_INTEGER DASHED_INTEGER INTEGEROBJ",
"american_date : DASHED_INTEGER error",
"period_clause : date_expression",
"period_clause : year_expression",
"period_clause : decade_expression",
"period_clause : century_expression",
"period_clause : circa_expression",
"quem_clause : english_quem",
"quem_clause : greek_quem",
"english_quem : ANTE QUEM",
"english_quem : POST QUEM",
"english_quem : PRIOR TO",
"english_quem : LATER THAN",
"greek_quem : PROIMOTERO TOY",
"greek_quem : YSTEROTERO TOY",
"greek_quem : PRIN APO",
"greek_quem : META APO",
"quem_part_expression : period_clause",
"quem_part_expression : now_expression",
"century_part_clause : ARXES",
"century_part_clause : MESA",
"century_part_clause : TELH",
"century_part : LETTER",
"century_part : nominative_number",
"period_expression : period_clause '-' period_clause",
"period_expression : period_clause '-' now_expression",
"period_expression : now_expression '-' period_clause",
"period_expression : century_part_clause century_term_expr",
"period_expression : century_part MISO century_term_expr",
"period_expression : century_part TETARTO century_term_expr",
"period_expression : quem_clause quem_part_expression",
"period_expression : error '-' period_clause",
"BCEtime_value : BCEperiod_clause",
"BCEtime_value : BCEperiod_expression",
"BCEperiod_clause : period_clause BCE",
"BCEperiod_expression : period_clause '-' period_clause BCE",
"BCEperiod_expression : BCEperiod_clause '-' period_clause CE",
"BCEperiod_expression : century_part_clause century_term_expr BCE",
"BCEperiod_expression : century_part MISO century_term_expr BCE",
"BCEperiod_expression : century_part TETARTO century_term_expr BCE",
"BCEperiod_expression : quem_clause BCEperiod_clause",
};

//#line 1112 "parser.y"
	


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
                new FileOutputStream(filename), StandardCharsets.UTF_8));

		writer.write(parseStr);
		writer.close();

		//Parse the file 
		yyparser = new Parser(new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8));
		yyparser.yyparse();

		tempFile.delete();	//delete temp file

		timeValues = parsedTimeVal[0].getUpperLower(); //store the lower and upper values

		if(timeValues[0] != 0 || timeValues[1] != 0)
			return timeValues;
		else 
			return null;
	}		
//#line 522 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 42 "parser.y"
{}
break;
case 2:
//#line 45 "parser.y"
{ System.out.println("time_value"); parsedTimeVal[0] = (Time)val_peek(0).obj;}
break;
case 3:
//#line 47 "parser.y"
{System.out.println("bce_time_value"); 	parsedTimeVal [0]= (Time)val_peek(0).obj;}
break;
case 4:
//#line 52 "parser.y"
{
						Date date = (Date) val_peek(0).obj;

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

						yyval.obj = time;
				}
break;
case 5:
//#line 74 "parser.y"
{

						SingleYear year = (SingleYear)val_peek(0).obj;

						Time time = new Time();
						
						if(year.flag == ERROR)
							return (ERROR);

						time.store(T_lower,year.value,1,1,ce);
						time.store(T_upper,year.value,12,31,ce);

						yyval.obj = time;
				}
break;
case 6:
//#line 89 "parser.y"
{

						SingleYear decadeObj = (SingleYear)val_peek(0).obj;

						Time time = new Time();

						if(decadeObj.flag == ERROR)
							return (ERROR);

						if(decadeObj.value != 0){
							time.store(T_lower,decadeObj.value,1,1,ce,decadeObj.flag);
						}
						else {
							/*System.out.println(" lower 1-1-1");*/
							time.store(T_lower,1,1,1,ce,decadeObj.flag);
						}
						time.store(T_upper,decadeObj.value+9,12,31,ce,decadeObj.flag);

						yyval.obj = time;

				}
break;
case 7:
//#line 111 "parser.y"
{
						System.out.println("century_expression");
						SingleYear centuryObj = (SingleYear)val_peek(0).obj;

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

						yyval.obj = time;

				}
break;
case 8:
//#line 135 "parser.y"
{

						SingleYear circa = (SingleYear)val_peek(0).obj;

						if(circa.flag == ERROR)
							return (ERROR);

						Time time = new Time();
						time.store(T_lower, circa.value-10,1,1,ce,Circa);
						time.store(T_upper,circa.value+10,12,31,ce,Circa);

						yyval.obj = time;
	           	}
break;
case 9:
//#line 149 "parser.y"
{

						Date date = (Date)val_peek(0).obj;

						Time time = new Time();
						time.store(T_lower,date.year,date.month,date.day,ce,date.flag);
						time.store(T_upper,date.year,date.month,date.day,ce,date.flag);

						yyval.obj = time;
				}
break;
case 10:
//#line 160 "parser.y"
{

						Time time = new Time();

						time.store(T_lower);
						time.store(T_upper);

						yyval.obj = time;
				}
break;
case 11:
//#line 170 "parser.y"
{yyval.obj = (Time)val_peek(0).obj;}
break;
case 12:
//#line 172 "parser.y"
{yyval.obj = (Time)val_peek(0).obj;}
break;
case 13:
//#line 174 "parser.y"
{yyval.obj = (Time)val_peek(0).obj;}
break;
case 14:
//#line 180 "parser.y"
{
							
							Date date = new Date();

							date.flag = SIS_DATE;	
							
							if (Utilities.checkYear(val_peek(2).ival) == ERROR)
								date.flag = ERROR;

							if (Utilities.checkMonth(val_peek(1).ival) == ERROR)
								date.flag = ERROR;

							if (Utilities.checkDay(val_peek(2).ival,val_peek(1).ival,val_peek(0).ival) == ERROR)
								date.flag = ERROR;

							date.year = val_peek(2).ival;
							date.month = val_peek(1).ival;
							date.day = val_peek(0).ival;

							yyval.obj = date;
						}
break;
case 15:
//#line 201 "parser.y"
{
						
							Date date = new Date();

							date.flag = SIS_DATE;	
							
							if (Utilities.checkYear(val_peek(1).ival) == ERROR)
								date.flag = ERROR;

							if (Utilities.checkMonth(val_peek(0).ival) == ERROR)
								date.flag = ERROR;

							date.year = val_peek(1).ival;
							date.month = val_peek(0).ival;
							date.day = UNDEFINED;

							yyval.obj = date;
						}
break;
case 16:
//#line 219 "parser.y"
{
							
							Date date = new Date();
							
							date.flag = ERROR;

							String msg = "*** Syntax Error in Date Expression";
							globalError[0].putMessage (msg);
							globalError[0].printMessage();

							yyval.obj = date;
						}
break;
case 17:
//#line 235 "parser.y"
{

							SingleYear year = new SingleYear();
							year.flag = YEAR;

							if(Utilities.checkYear(val_peek(0).ival) == ERROR)
								year.flag = ERROR;
							year.value = val_peek(0).ival;

							yyval.obj = year;
					}
break;
case 18:
//#line 250 "parser.y"
{yyval.obj = circa(val_peek(0).ival);}
break;
case 19:
//#line 252 "parser.y"
{yyval.obj = circa(val_peek(2).ival);}
break;
case 20:
//#line 256 "parser.y"
{

								Date date = new Date();

								date.year = Calendar.getInstance().get(Calendar.YEAR);
								date.month = Calendar.getInstance().get(Calendar.MONTH);
								date.day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
								date.flag = SIS_DATE;

								yyval.obj = date;
					}
break;
case 21:
//#line 269 "parser.y"
{

								int num = val_peek(0).ival;
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
								
								yyval.obj = decadeObj;								
						}
break;
case 22:
//#line 292 "parser.y"
{
								SingleYear decade = new SingleYear();

								decade.value = ((val_peek(0).ival-1) * 100)+((val_peek(3).ival-1)*10);
								decade.flag = IMPL_DECADE;

								yyval.obj = decade;

						}
break;
case 23:
//#line 305 "parser.y"
{ yyval.ival = val_peek(1).ival;}
break;
case 24:
//#line 308 "parser.y"
{ yyval.ival = val_peek(0).ival; }
break;
case 25:
//#line 312 "parser.y"
{yyval.ival = val_peek(1).ival; }
break;
case 26:
//#line 316 "parser.y"
{yyval.obj = century(val_peek(1).ival);}
break;
case 27:
//#line 318 "parser.y"
{yyval.obj = century(val_peek(0).ival);}
break;
case 28:
//#line 325 "parser.y"
{	

								Time time = new Time();

								Date date = (Date)val_peek(0).obj;

								if (date.month == 0 && date.day != 0){

									String buffer;
									String error = "Month (%d) is an invalid value, when followed by non zero day (%d). You should provide a non zero month value or a zero valued day value\n";
			 
									buffer = String.format(error ,date.month ,date.day); 
									globalError[0].putMessage(buffer);
									globalError[0].printMessage();

									yyval.obj = time;
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

								yyval.obj = time;

					}
break;
case 29:
//#line 368 "parser.y"
{yyval.obj = val_peek(0).obj;}
break;
case 30:
//#line 372 "parser.y"
{

								Date date = new Date();

								date.day = val_peek(0).ival%100;
								val_peek(0).ival /= 100;
								date.month = val_peek(0).ival%100;
								date.year = val_peek(0).ival/100;
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

								yyval.obj = date;
						}
break;
case 31:
//#line 404 "parser.y"
{

							String message = "*** Syntax Error: the format is: [AAT integer]";
							
							globalError[0].putMessage( message);
	                        globalError[0].printMessage();
	                        return(ERROR);						

						}
case 32:
//#line 416 "parser.y"
{

									Time time = new Time();
									
									Date date = (Date)val_peek(2).obj;

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

									u_day = val_peek(0).ival%100;
									val_peek(0).ival/= 100;
									u_month = val_peek(0).ival%100;

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

									u_year = val_peek(0).ival/100;
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

									yyval.obj = time;

							}
break;
case 33:
//#line 500 "parser.y"
{

									String message = "*** Syntax Error: the format is: [AAT integer:integer]";
									globalError[0].putMessage( message);
		                        	globalError[0].printMessage();

		                        	return(ERROR);
									
							}
case 34:
//#line 515 "parser.y"
{

							Time time = new Time();

							Date date = (Date)val_peek(0).obj;
							int flag = ce;

							if(date.year < 100)
								date.year+= 1900;

							time.store(T_lower,date.year,date.month,date.day,flag,SIS_DATE);
							time.store(T_upper,date.year,date.month,date.day,flag,SIS_DATE);

							yyval.obj = time;
					}
break;
case 35:
//#line 531 "parser.y"
{

							Time time = new Time();
							int flag,year;
							int l_year,u_year;

							Date date = (Date)val_peek(4).obj;

							if (val_peek(0).ival < 0 || val_peek(0).ival > 1){

								String buffer = String.format("BC value (%d) is an unknown value. Legal values are 0-1\n",val_peek(0).ival);
								
								globalError[0].putMessage( buffer);
								globalError[0].printMessage();
								
								return(ERROR);
							}

							flag = (val_peek(0).ival != 0) ? bce : ce;

							switch (val_peek(2).ival){

								case 0:		/*date...*/
									time.store(T_lower,date.year,date.month,date.day,flag,SIS_DATE);
									time.store(T_upper,date.year,date.month,date.day,flag,SIS_DATE);
									break;
								case 1:		/*month-year*/
									time.store(T_lower,date.year,date.month,1,flag,SIS_DATE);
									time.store(T_upper,date.year,date.month,Utilities.findNumOfDays(date.month,date.year),SIS_DATE);
									break;
								case 2: 	/*only year*/
									time.store(T_lower,date.year,1,1,flag);
									time.store(T_upper,date.year,12,31,flag);
									break;
								case 3:		/*only decade*/
									year = date.year - (date.year%10); 
									l_year = (val_peek(0).ival != 0 ) ? (year+9) : year;
									u_year = (val_peek(0).ival != 0) ? (year) : (year+9);

									time.store(T_lower,l_year,1,1,flag,DECADE);
									time.store(T_upper,u_year,12,31,flag,DECADE);
									break;
								case 4:		/*only century...*/
									year = date.year - (date.year%100); 
									l_year = (val_peek(0).ival !=0 ) ? (year+99) : year;
									u_year = (val_peek(0).ival !=0 ) ? (year) : (year+99);
									time.store(T_lower,l_year,1,1,flag,Century);
									time.store(T_upper,u_year,12,31,flag,Century);
								default:{
									
									String buffer;
									buffer = String.format("Level value (%d) is an unknown value. Legal values are 0-4\n",val_peek(2).ival);
									globalError[0].putMessage( buffer);
									globalError[0].printMessage();
									return(ERROR);
									
								}
							}
							yyval.obj = time;
					}
break;
case 36:
//#line 592 "parser.y"
{

							String message = "*** Syntax Error in ULAN date Expression. The format is: [ULAN `month-day-year`level`bc]";

							globalError[0].putMessage( message);
							globalError[0].printMessage();

		                    return(ERROR);
					}
case 37:
//#line 603 "parser.y"
{

							Date date = new Date();

							if(Utilities.checkYear(val_peek(0).ival) == ERROR)
								return ERROR;
							if(Utilities.checkMonth(val_peek(2).ival) == ERROR)
								return ERROR;
							if(Utilities.checkDay(val_peek(0).ival,val_peek(2).ival,val_peek(1).ival) == ERROR)
								return ERROR;

							date.year = val_peek(0).ival;
							date.month = val_peek(2).ival;
							date.day = val_peek(1).ival;
							yyval.obj = date;
					}
break;
case 38:
//#line 620 "parser.y"
{	
							
							String message = "*** Syntax Error in American date ";

							globalError[0].putMessage( message);
							globalError[0].printMessage();
	                        return(ERROR);
							
							
	               }
case 39:
//#line 635 "parser.y"
{	}
break;
case 40:
//#line 637 "parser.y"
{
							
							Date date = new Date();

							SingleYear year = (SingleYear)val_peek(0).obj;
							date.year = year.value;
							date.flag = year.flag;

							yyval.obj = date;
					}
break;
case 41:
//#line 648 "parser.y"
{

							Date date = new Date();

							SingleYear year = (SingleYear)val_peek(0).obj;
							date.year = year.value;
							date.flag = year.flag;

							yyval.obj = date;
					}
break;
case 42:
//#line 659 "parser.y"
{

							Date date = new Date();

							SingleYear year = (SingleYear)val_peek(0).obj;
							date.year = (year.value-1)*100;
							date.flag = year.flag;

							yyval.obj = date;
					}
break;
case 43:
//#line 670 "parser.y"
{

							Date date = new Date();

							SingleYear year = (SingleYear)val_peek(0).obj;
							date.year = year.value;
							date.flag = year.flag;

							yyval.obj = date;
					}
break;
case 44:
//#line 683 "parser.y"
{yyval.ival = val_peek(0).ival;}
break;
case 45:
//#line 685 "parser.y"
{yyval.ival = val_peek(0).ival;}
break;
case 46:
//#line 689 "parser.y"
{ yyval.ival = 1; }
break;
case 47:
//#line 691 "parser.y"
{ yyval.ival = 2; }
break;
case 48:
//#line 693 "parser.y"
{ yyval.ival = 1; }
break;
case 49:
//#line 695 "parser.y"
{ yyval.ival = 2; }
break;
case 50:
//#line 699 "parser.y"
{ yyval.ival = 1;}
break;
case 51:
//#line 701 "parser.y"
{ yyval.ival = 2;}
break;
case 52:
//#line 703 "parser.y"
{ yyval.ival = 1;}
break;
case 53:
//#line 705 "parser.y"
{ yyval.ival = 2;}
break;
case 54:
//#line 709 "parser.y"
{yyval.obj = val_peek(0).obj;}
break;
case 55:
//#line 711 "parser.y"
{yyval.obj = val_peek(0).obj;}
break;
case 56:
//#line 715 "parser.y"
{ yyval.ival = 1; }
break;
case 57:
//#line 717 "parser.y"
{ yyval.ival = 2;}
break;
case 58:
//#line 719 "parser.y"
{ yyval.ival = 3;}
break;
case 59:
//#line 724 "parser.y"
{yyval.ival = val_peek(0).ival; }
break;
case 60:
//#line 726 "parser.y"
{yyval.ival = val_peek(0).ival;}
break;
case 61:
//#line 730 "parser.y"
{

								if(periodExprChecks((Date) val_peek(2).obj,(Date) val_peek(0).obj) == ERROR)
									return ERROR;

								yyval.obj = (Time)periodExprStore((Date) val_peek(2).obj,(Date) val_peek(0).obj);

						}
break;
case 62:
//#line 739 "parser.y"
{

								if(periodExprChecks((Date) val_peek(2).obj,(Date) val_peek(0).obj) == ERROR)
									return ERROR;

								yyval.obj = periodExprStore((Date) val_peek(2).obj,(Date) val_peek(0).obj); 
	                    }
break;
case 63:
//#line 747 "parser.y"
{

	                   			if(periodExprChecks((Date) val_peek(2).obj,(Date) val_peek(0).obj) == ERROR)
									return ERROR;

								yyval.obj = periodExprStore((Date) val_peek(2).obj,(Date) val_peek(0).obj);

	                    }
break;
case 64:
//#line 756 "parser.y"
{

								val_peek(0).ival = (val_peek(0).ival-1) * 100;
								switch(val_peek(1).ival){

							  		case 1:
										Begin = (val_peek(0).ival != 0) ? val_peek(0).ival : 1;
										End   = val_peek(0).ival+40;
										break;

							  		case 2:
										Begin = val_peek(0).ival+30;
										End = val_peek(0).ival+70;
										break;

							  		case 3:
										Begin = val_peek(0).ival+60;
										End = val_peek(0).ival+99;
										break;
								}

								Time time = new Time();

								time.store(T_lower,Begin,1,1,ce,IMPL_PERIOD);
								time.store(T_upper,End,12,31,ce,IMPL_PERIOD);

								yyval.obj = time;
						}
break;
case 65:
//#line 785 "parser.y"
{ 

								val_peek(0).ival = (val_peek(0).ival-1) * 100;
								switch(val_peek(2).ival){

								  case 1:
										Begin = (val_peek(0).ival != 0) ? val_peek(0).ival : 1;
										End   = val_peek(0).ival+60;
										break;

								  case 2:
										Begin = val_peek(0).ival+40;
										End = val_peek(0).ival+99;
										break;
								}

								Time time = new Time();

								time.store(T_lower,Begin,1,1,ce,IMPL_PERIOD);
								time.store(T_upper,End,12,31,ce,IMPL_PERIOD);

								yyval.obj = time;
						}
break;
case 66:
//#line 809 "parser.y"
{

								val_peek(0).ival = (val_peek(0).ival-1) * 100;
								switch(val_peek(2).ival){

								  case 1:
										Begin = (val_peek(0).ival != 0) ? val_peek(0).ival : 1;
										End   = val_peek(0).ival+27;
										break;

								  case 2:
										Begin = val_peek(0).ival+25;
										End = val_peek(0).ival+52;
										break;

								  case 3:
										Begin = val_peek(0).ival+50;
										End = val_peek(0).ival+77;
										break;

								  case 4:
										Begin = val_peek(0).ival+75;
										End = val_peek(0).ival+99;
										break;
								}

								Time time = new Time();

								time.store(T_lower,Begin,1,1,ce,IMPL_PERIOD);
								time.store(T_upper,End,12,31,ce,IMPL_PERIOD);

								yyval.obj = time;
						}
break;
case 67:
//#line 843 "parser.y"
{

								Time time = new Time();

								Date date = (Date) val_peek(0).obj;

								switch(val_peek(1).ival){

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
break;
case 68:
//#line 863 "parser.y"
{

								String message = "*** Syntax Error in Period Expression ";
				              
				                globalError[0].putMessage( message);
				                globalError[0].printMessage();
				                
				                return(ERROR);								
						}
case 69:
//#line 878 "parser.y"
{
							System.out.println("BCEperiod_clause");
							Time time = new Time();
							Date date = (Date)val_peek(0).obj;

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

							yyval.obj = time;
					}
break;
case 70:
//#line 942 "parser.y"
{yyval.obj = val_peek(0).obj; }
break;
case 71:
//#line 946 "parser.y"
{

								Date date = new Date();
								Date date_1 = (Date)val_peek(1).obj;

								date.flag = date_1.flag;

								if(date_1.flag == IMPL_DECADE){
									decadeVal = date_1.year%100;
									centuryVal = date_1.year/100;
									date_1.year = (centuryVal*100) + ((9 - decadeVal/10)*10);
								}
								date.year = date_1.year;
								date.month = date_1.month;
								date.day = date_1.day;

								yyval.obj = date;
						}
break;
case 72:
//#line 967 "parser.y"
{

									Date date_1 = (Date)val_peek(3).obj;
									Date date_2 = (Date)val_peek(1).obj;

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

									yyval.obj = time;
							}
break;
case 73:
//#line 995 "parser.y"
{

									Date date_1 = (Date)val_peek(3).obj;
									Date date_2 = (Date)val_peek(1).obj;

									if(date_1.flag == ERROR || date_2.flag == ERROR)
										return ERROR;

									Time time = new Time();

									date_1.flag = Utilities.convert(date_1.flag);

									time = Utilities.store(time, T_lower,date_1.flag,date_1.year,date_1.month,date_1.day);
									time = Utilities.store(time, T_upper,date_2.flag,date_2.year,date_2.month,date_2.day);

									yyval.obj = time;
							}
break;
case 74:
//#line 1013 "parser.y"
{

									Time time = new Time();

									val_peek(1).ival = (val_peek(1).ival-1) * 100;
									switch(val_peek(2).ival){
									  case 1:
											Begin = val_peek(1).ival+99;
											End   = val_peek(1).ival+60;
											break;
									  case 2:
											Begin = val_peek(1).ival+70;
											End = val_peek(1).ival+30;
											break;
									  case 3:
											Begin = val_peek(1).ival+40;
											End = (val_peek(1).ival != 0) ? val_peek(1).ival : 1;
											break;
									}

									time.store(T_lower,Begin,1,1,bce,IMPL_PERIOD);
									time.store(T_upper,End,12,31,bce,IMPL_PERIOD);

									yyval.obj = time;
								}
break;
case 75:
//#line 1038 "parser.y"
{

									Time time = new Time();

									val_peek(1).ival = (val_peek(1).ival-1) * 100;
									switch(val_peek(3).ival){
									  case 1:
											Begin = val_peek(1).ival+99;
											End   = val_peek(1).ival+40;
											break;
									  case 2:
											Begin = val_peek(1).ival+60;
											End = (val_peek(1).ival !=0) ? val_peek(1).ival : 1;
											break;
									}

									time.store(T_lower,Begin,1,1,bce,IMPL_PERIOD);
									time.store(T_upper,End,12,31,bce,IMPL_PERIOD);

									yyval.obj = time;
							}
break;
case 76:
//#line 1060 "parser.y"
{

									Time time = new Time();

									val_peek(1).ival = (val_peek(1).ival-1) * 100;
									switch(val_peek(3).ival){
									  case 1:
											Begin = val_peek(1).ival+99;
											End = val_peek(1).ival+75;
											break;
									  case 2:
											Begin = val_peek(1).ival+77;
											End = val_peek(1).ival+50;
											break;
									  case 3:
											Begin = val_peek(1).ival+52;
											End = val_peek(1).ival+25;
											break;
									  case 4:
											Begin = val_peek(1).ival+27;
											End = (val_peek(1).ival != 0)? val_peek(1).ival : 1;
											break;
									}

									time.store(T_lower,Begin,1,1,bce,IMPL_PERIOD);
									time.store(T_upper,End,12,31,bce,IMPL_PERIOD);

									yyval.obj = time;

							}
break;
case 77:
//#line 1091 "parser.y"
{

								Time time = new Time();
								Date date = (Date)val_peek(0).obj;

								date.flag = Utilities.convert(date.flag);

								switch(val_peek(1).ival){
									case 1:
										time.store(T_lower);
										time = Utilities.store(time,T_upper,date.flag,date.year,date.month,date.day);
										break;
									case 2:
										time = Utilities.store(time,T_lower,date.flag,date.year,date.month,date.day);
										time.store(T_upper);
								}

							}
break;
//#line 1828 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
