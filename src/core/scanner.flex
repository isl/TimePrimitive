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

%%

%public

%standalone

%unicode

%caseless

%byaccj

%{

	private Parser yyparser;

  	public Yylex(java.io.Reader r, Parser yyparser) {
	    this(r);
	    this.yyparser = yyparser;
  	}

	int INTEGEROBJ 	= 257;
	int DASHED_INTEGER = 258;
	int IDENTIFIER = 259;
	int CE = 260;
	int BCE = 261;
	int AIWNA = 262;
	int AIWNAS = 263;
	int CENTURY = 264;
	int DECAETIA = 265;
	int TOY = 266;
	int AAT = 267;
	int ULAN = 268;
	int ARXES =  269;
	int MESA = 270;
	int TELH = 271;
	int MISO = 272;
	int TETARTO = 273;
	int CA = 274;
	int LETTER = 275;
	int NOW = 276;
	int ALLTIME = 277;
	int ANTE = 278;
	int POST = 279;
	int QUEM = 280;
	int TO = 281;
	int PRIOR = 282;
	int LATER = 283;
	int THAN = 284;
	int PROIMOTERO = 285;
	int YSTEROTERO = 286;
	int PRIN = 287;
	int APO = 288;
	int META = 289;

	int arithmetic = 290;
	int Month = 291;
	int genitive_number = 292;
	int nominative_number = 293;
%}

%x nominative_number_mode
%x genitive_number_mode
%x english_number_mode
%x dash_mode
%x first_mode
%x lower_mode

Sign		=	[-+]
Digit		=	[0-9]
Integer		=	[0-9]+
Letter		=	[a-zA-Z]
Force  		=   [ \t\n]
RegularId	=   {Letter}({Letter}|{Digit}|_|("`"{Letter})|("`"_)|("`"{Digit}))*

//SuperForm   =	([^ \t\n\(\)] | "^")+
//SuperId     =	\({SuperForm}\)
//Id          =	({RegularId}|{SuperId})


%%
<YYINITIAL>{

	{Sign}?{Integer}	{
							yyparser.yylval = new ParserVal(Integer.parseInt(yytext()));
							return(INTEGEROBJ);
						}

	{Integer}/-		{
						yybegin(dash_mode);
						yyparser.yylval = new ParserVal(Integer.parseInt(yytext()));
					}
}

<dash_mode>{
	"-"	{
				yybegin(YYINITIAL);
				return( DASHED_INTEGER);
			}
}

"ca."					{ return CA; }

AAT						{ return AAT; }
ULAN					{ return ULAN; }


												/**********ENGLISH***************/
/*------------------------------------------------------------------------------------------------------------------------------------------*/



[cC][eE][nN][tT][uU][rR][yY]			{ return CENTURY; }
[dD][eE][cC][aA][dD][eE]				{ return DECAETIA; }
[oO][fF]                            	{ return TOY;  }
[eE][aA][rR][lL][yY]                    { return ARXES; }
[mM][iI][dD]                        	{ return MESA;  }
[lL][aA][tT][eE]                        { return TELH;  }
[nN][oO][wW]							{ return NOW;	}
[aA][lL][lL][tT][iI][mM][eE]			{ return ALLTIME; }
[hH][aA][lL][fF]						{ return MISO; }
[qQ][uU][aA][rR][tT][eE][rR]            { return TETARTO; }
[pP][oO][sS][tT]						{ return POST; }
[aA][nN][tT][eE]						{ return ANTE; }
[qQ][uU][eE][mM]						{ return QUEM; }
[pP][rR][iI][oO][rR]					{ return PRIOR; }
[tT][oO]								{ return TO; }
[lL][aA][tT][eE][rR]					{ return LATER; }
[tT][hH][aA][nN]						{ return THAN; }
[cC][eE]                                { return CE; }
[bB][cC][eE]							{ return BCE; }


[fF][iI][rR][sS][tT]                    { yyparser.yylval = new ParserVal(1); return arithmetic; }
[sS][eE][cC][oO][nN][dD]            	{ yyparser.yylval = new ParserVal(2); return arithmetic; }
[tT][hH][iI][rR][dD]                    { yyparser.yylval = new ParserVal(3); return arithmetic; }
[fF][oO][uU][rR][tT][hH]            	{ yyparser.yylval = new ParserVal(4); return arithmetic; }
[fF][iI][fF][tT][hH]                	{ yyparser.yylval = new ParserVal(5); return arithmetic; }
[sS][iI][xX][tT][hH]					{ yyparser.yylval = new ParserVal(6); return arithmetic; }
[sS][eE][vV][eE][nN][tT][hH]			{ yyparser.yylval = new ParserVal(7); return arithmetic; }
[eE][iI][gG][hH][tT][hH]				{ yyparser.yylval = new ParserVal(8); return arithmetic; }
[nN][iI][nN][tT][hH]					{ yyparser.yylval = new ParserVal(9); return arithmetic; }
[tT][eE][nN][tT][hH]                	{ yyparser.yylval = new ParserVal(10); return arithmetic; }
[lL][aA][sS][tT]    					{ yyparser.yylval = new ParserVal(10); return arithmetic; }


[jJ][aA][nN][uU][aA][rR][yY]			{ yyparser.yylval = new ParserVal(1); return Month; }
[fF][eE][bB][rR][uU][aA][rR][yY]  		{ yyparser.yylval = new ParserVal(2); return Month; }
[mM][aA][rR][cC][hH]                  	{ yyparser.yylval = new ParserVal(3); return Month; }
[aA][pP][rR][iI][lL]              		{ yyparser.yylval = new ParserVal(4); return Month; }
[mM][aA][yY]                          	{ yyparser.yylval = new ParserVal(5); return Month; }
[jJ][uU][nN][eE]                  		{ yyparser.yylval = new ParserVal(6); return Month; }
[jJ][uU][lL][yY]                  		{ yyparser.yylval = new ParserVal(7); return Month; }
[aA][uU][gG][uU][sS][tT]          		{ yyparser.yylval = new ParserVal(8); return Month; }
[sS][eE][pP][tT][eE][mM][bB][eE][rR]  	{ yyparser.yylval = new ParserVal(9); return Month; }
[oO][cC][tT][oO][bB][eE][rR]          	{ yyparser.yylval = new ParserVal(10); return Month; }
[nN][oO][vV][eE][mM][bB][eE][rR]        { yyparser.yylval = new ParserVal(11); return Month; }
[dD][eE][cC][eE][mM][bB][eE][rR]      	{ yyparser.yylval = new ParserVal(12); return Month; }			






											/****************GREEK******************/
/*---------------------------------------------------------------------------------------------------------------------------------------------------*/

/*	Α			α		Ι			ι		Ω		ω			ώ		Ώ			Ν		ν		Α			α*/
["\u0391" "\u03B1"] ["\u0399" "\u03B9"] ["\u03A9" "\u03C9" "\u03CE" "\u038F"] ["\u03BD" "\u039D"] ["\u0391" "\u03B1"] 
{
	return AIWNA;
}


/*	Α			α		Ι			ι		Ω		ω			ώ		Ώ			Ν		ν		Α			α  		space*/
["\u0391" "\u03B1"] ["\u0399" "\u03B9"] ["\u03A9" "\u03C9" "\u03CE" "\u038F"] ["\u03BD" "\u039D"] ["\u0391" "\u03B1"]  [  "\u0020" ]
{
	return AIWNA;
}

/*	Α			α		Ι			ι		Ω		ω			ώ		Ώ			Ν		ν		Α			α			Σ		ς*/
["\u0391" "\u03B1"] ["\u0399" "\u03B9"] ["\u03A9" "\u03C9" "\u03CE" "\u038F"] ["\u03BD" "\u039D"] ["\u0391" "\u03B1"] ["\u03A3" "\u03C2"]
{
	return AIWNAS;
}

/*	Δ			δ		Ε			ε		Κ		κ			Α		α			Ε		ε			Τ		τ	*/
["\u0394" "\u03B4"] ["\u0395" "\u03B5"] ["\u039A" "\u03BA"]	["\u0391" "\u03B1"] ["\u0395" "\u03B5"] ["\u03A4" "\u03C4"]	
																/*Ι			ι		ί		Ί			Α		α*/
												 			["\u0399" "\u03B9" "\u03AF" "\u038A"] ["\u0391" "\u03B1"]	
{ 
	return DECAETIA; 
}

/*	T 			τ			Ο		ο		Υ			υ*/
["\u03A4" "\u03C4"]	["\u039F" "\u03BF"] ["\u03A5" "\u03C5"]								
{ 
	return TOY;	
}

/*	Α 			α		Ρ			ρ		Χ			χ		Ε 		ε		έ			Έ		Σ			ς*/
["\u0391" "\u03B1"] ["\u03A1" "\u03C1"] ["\u03A7" "\u03C7"]["\u0395" "\u03B5" "\u0388" "\u03AD"] ["\u03A3" "\u03C2"]					
{ 
	return ARXES; 
}

/*	Μ		μ			Ε 			ε		έ		Έ			Σ		σ			Α 			α*/
["\u039C" "\u03BC"] ["\u0395" "\u03B5" "\u0388" "\u03AD"] ["\u03A3" "\u03C3"] ["\u0391" "\u03B1"] 				
{ 
	return MESA;  
}

/*	Τ 			τ		Ε 			ε		έ 		Έ			Λ 		λ 			Η 		η*/
["\u03A4" "\u03C4"]	["\u0395" "\u03B5" "\u0388" "\u03AD"] ["\u03BB" "\u039B"] ["\u0397" "\u03B7"]							
{ 
	return TELH;  
}

/*	Τ 			τ		Ω			ω		ώ		Ώ			Ρ	 	ρ			Α        α*/
["\u03A4" "\u03C4"] ["\u03A9" "\u03C9" "\u03CE" "\u038F"] ["\u03A1" "\u03C1"]  ["\u0391" "\u03B1"] 
{ 
	return NOW;
}

/*Μ			μ			Ι 			ι			Σ		σ			Ο		ο 		ό 		Ό*/
["\u039C" "\u03BC"]  ["\u0399" "\u03B9"] ["\u03A3" "\u03C3"]  ["\u039F" "\u03BF" "\u03CC" "\u038C"]
{ 
	return MISO;  
}

/*	Τ 		 Τ 			Ε 			ε 		Έ		έ  			Τ 		τ 			Α 		α 			Ρ		ρ*/
["\u03A4" "\u03C4"]	["\u0395" "\u03B5" "\u0388" "\u03AD"] ["\u03A4" "\u03C4"] ["\u0391" "\u03B1"] ["\u03A1" "\u03C1"] 	
																			/*	Τ 			τ 		Ο 			ο*/		/*lf 	   space*/
																			["\u03A4" "\u03C4"]	 ["\u039F" "\u03BF"] ["\u000A" "" "\u0020"]
{
	return TETARTO; 
}

/*	Π		π			Ρ			ρ		Ο			ο		Ι 			ι 		Ϊ		ϊ 			Μ			μ		Ο		ο			ό		Ό*/
["\u03A0" "\u03C0"] ["\u03A1" "\u03C1"] ["\u039F" "\u03BF"]  ["\u0399" "\u03B9" "\u03AA" "\u03CA"] ["\u039C" "\u03BC"]  ["\u039F" "\u03BF" "\u03CC" "\u038C"]
															/*	Τ  			τ		Ε 			ε 		Ρ 			ρ 		Ο	 		ο*/
															["\u03A4" "\u03C4"]	["\u0395" "\u03B5"]	["\u03A1" "\u03C1"] ["\u039F" "\u03BF"]		
{ 
	return PROIMOTERO; 
}

/*	Υ 		υ 			Σ 			σ 			Τ 		τ 			Ε 		ε 		Ρ 			ρ 			Ο 		ο 		ό 		Ό*/
["\u03A5" "\u03C5"]	 ["\u03A3" "\u03C3"] ["\u03A4" "\u03C4"] ["\u0395" "\u03B5"] ["\u03A1" "\u03C1"] ["\u039F" "\u03BF" "\u03CC" "\u038C"]
															/*	Τ  			τ		Ε 			ε 		Ρ 			ρ 		Ο	 		ο*/
															["\u03A4" "\u03C4"]	["\u0395" "\u03B5"]	["\u03A1" "\u03C1"] ["\u039F" "\u03BF"]	
{ 
	return YSTEROTERO; 
}

/*	Π			π		Ρ		ρ			Ι 			ι			Ν		ν*/
["\u03A0" "\u03C0"] ["\u03A1" "\u03C1"] ["\u0399" "\u03B9"]	["\u03BD" "\u039D"] 						
{ 
	return PRIN; 
}

/*	Μ 			μ	 	Ε 			ε 			Τ 		τ 		Α 		 α			ά 			Ά*/
["\u039C" "\u03BC"] ["\u0395" "\u03B5"] ["\u03A4" "\u03C4"]	 ["\u0391" "\u03B1" "\u0386" "\u03AC"] 
{ 
	return META; 
}

/*	Α 		α 			Π 			π 		Ο 			ο		ό 		Ό*/
["\u0391" "\u03B1"] ["\u03A0" "\u03C0"] ["\u039F" "\u03BF" "\u03CC" "\u038C"]							
{ 
	return APO; 
}

/*	Μ			μ		.			Χ			χ		.	*/
["\u039C" "\u03BC"] ["\u002E"] ["\u03A7" "\u03C7"] ["\u002E"]                              
{ 
	return CE; 
}

/*	Π			π 		.			Χ			χ		.	*/
["\u03A0" "\u03C0"] ["\u002E"] ["\u03A7" "\u03C7"]["\u002E"]                                	
{ 
	return BCE; 
}






/* 		Π 		π		Ρ 			ρ		Ω 		ω		ώ		Ώ			T 			τ		H 			η	*/
["\u03A0" "\u03C0"]["\u03A1" "\u03C1"]  ["\u03A9" "\u03C9" "\u03CE" "\u038F"] ["\u03A4" "\u03C4"]["\u0397" "\u03B7"]	
{ 
	yyparser.yylval = new ParserVal(1); 
	return arithmetic; 
}

/*	Δ		 δ 			Ε 			ε 		Υ 			υ 		ύ		Ύ			Τ 			τ 		Ε 			ε 		Ρ 			ρ  			Η		η*/
["\u0394" "\u03B4"] ["\u0395" "\u03B5" ] ["\u03A5" "\u03C5" "\u03CD" "\u038E"] ["\u03A4" "\u03C4"] ["\u0395" "\u03B5"] ["\u03A1" "\u03C1"] ["\u0397" "\u03B7"]	
{
	yyparser.yylval = new ParserVal(2); 
	return arithmetic; 
}

/*  Τ 			τ 		Ρ 			ρ 		Ι 			ι 		ί		Ί			Τ		τ			Η		η*/	
["\u03A4" "\u03C4"] ["\u03A1" "\u03C1"] ["\u0399" "\u03B9" "\u03AF" "\u038A"] ["\u03A4" "\u03C4"] ["\u0397" "\u03B7"]	
{
	yyparser.yylval = new ParserVal(3); 
	return arithmetic; 
}

 /*  Τ			Τ		Ε		ε 			έ		Έ		Τ			τ		Α			α		Ρ			ρ			Τ		τ			Η			η*/        
["\u03A4" "\u03C4"] ["\u0395" "\u03B5" "\u03AD" "\u0388"] ["\u03A4" "\u03C4"] ["\u0391" "\u03B1"] ["\u03A1" "\u03C1"]  ["\u03A4" "\u03C4"]  ["\u0397" "\u03B7"]	        	
{
	 yyparser.yylval = new ParserVal(4);
	 return arithmetic; 
}

/* Π		π			Ε		ε			έ		Έ			Μ			μ		Π			π		Τ		τ			Η		η*/
["\u03A0" "\u03C0"] ["\u0395" "\u03B5" "\u03AD" "\u0388"]  ["\u039C" "\u03BC"] ["\u03A0" "\u03C0"] ["\u03A4" "\u03C4"] ["\u0397" "\u03B7"]	
{ 
	yyparser.yylval = new ParserVal(5);  
	return arithmetic; 
}

/*	Ε		ε			έ		Έ			Κ		κ		Τ			τ		Η			η*/
["\u0395" "\u03B5" "\u03AD" "\u0388"] ["\u039A","\u03BA"] ["\u03A4" "\u03C4"] ["\u0397" "\u03B7"]	
{ 
	yyparser.yylval = new ParserVal(6);   
	return arithmetic; 
}

/*	Ε			ε		έ		Έ			Β		β		Δ		δ				Ο		ο			Μ		μ		Η			η*/
["\u0395" "\u03B5" "\u03AD" "\u0388"] ["\u0392","\u03B2"] ["\u0394" "\u03B4"] ["\u039F" "\u03BF"] ["\u039C" "\u03BC"] ["\u0397" "\u03B7"]	
{ 
	yyparser.yylval = new ParserVal(7);   
	return arithmetic; 
}

/*	Ο			ο		Ό		ό		Γ			γ			Δ		δ		Ο			ο			Η			η*/
["\u039F" "\u03BF" "\u038C" "\u03CC"]  ["\u0393" , "\u03B3"] ["\u0394" "\u03B4"] ["\u039F" "\u03BF"] ["\u0397" "\u03B7"]	
{ 
	yyparser.yylval = new ParserVal(8);  
	return arithmetic; 
}

	/* Ε		ε		έ		Έ			Ν		ν			Α		α			Τ		τ			Η		η*/
["\u0395" "\u03B5" "\u03AD" "\u0388"] ["\u039D" "\u03BD"] ["\u0391" "\u03B1"] ["\u03A4" "\u03C4"] ["\u0397" "\u03B7"]	
{ 
	yyparser.yylval = new ParserVal(9);   
	return arithmetic; 
}

/*	Δ			δ		Ε			ε		έ		Έ		Κ			κ			Α		α			Τ		τ			Η		η*/
["\u0394" "\u03B4"] ["\u0395" "\u03B5" "\u03AD" "\u0388"] ["\u039A" "\u03BA"]  ["\u0391" "\u03B1"] ["\u03A4" "\u03C4"] ["\u0397" "\u03B7"]	
{ 
	yyparser.yylval = new ParserVal(10);  	
	return arithmetic; 
}

/*	Τ		τ			Ε		ε			Λ			λ		Ε		ε			Υ			υ		Τ		τ			Α		α*/
["\u03A4" "\u03C4"] ["\u0395" "\u03B5"] ["\u039B" "\u03BB"] ["\u0395" "\u03B5"] ["\u03A5" "\u03C5"] ["\u03A4" "\u03C4"] ["\u0391" "\u03B1"] 
															/* Ι			ι		ί		Ί			Α 		α*/
															["\u0399" "\u03B9" "\u03AF" "\u038A"]  ["\u0391" "\u03B1"] 
{ 
	yyparser.yylval = new ParserVal(10);  	
	return arithmetic; 
}








/*	Ι			ι		Α			α		Ν			ν		Ο			ο		Υ			υ		Α		α			ά		Ά*/
["\u0399" "\u03B9"] ["\u0391" "\u03B1"] ["\u039D" "\u03BD"] ["\u039F" "\u03BF"] ["\u03A5" "\u03C5"] ["\u0391" "\u03B1" "\u03AC" "\u0386"]
															/*	Ρ		ρ			Ι			ι		Ο		ο			Σ		ς*/
															["\u03A1" "\u03C1"] ["\u0399" "\u03B9"] ["\u039F" "\u03BF"] ["\u03A3" "\u03C2"]
{ 
	yyparser.yylval = new ParserVal(1);  
	return Month; 
}

/*	Φ		Φ			Ε		ε			Υ			υ		Ρ		ρ			Ο			ο		Υ			υ			Α		α		ά		Ά*/
["\u03A6" "\u03C6"] ["\u0395" "\u03B5"] ["\u0392" "\u03B2"] ["\u03A1" "\u03C1"]  ["\u039F" "\u03BF"] ["\u03A5" "\u03C5"] ["\u0391" "\u03B1" "\u03AC" "\u0386"]
															/*	Ρ		ρ			Ι			ι		Ο		ο			Σ		ς*/
															["\u03A1" "\u03C1"] ["\u0399" "\u03B9"] ["\u039F" "\u03BF"] ["\u03A3" "\u03C2"]
{ 
	yyparser.yylval = new ParserVal(2);  
	return Month; 
}
                 
/*	Μ		μ			Α		α			ά		Ά			Ρ		ρ		Τ			τ			Ι		ι			Ο		ο		Σ			ς*/
["\u039C" "\u03BC"] ["\u0391" "\u03B1" "\u03AC" "\u0386"] ["\u03A1" "\u03C1"] ["\u03A4" "\u03C4"] ["\u0399" "\u03B9"] ["\u039F" "\u03BF"] ["\u03A3" "\u03C2"]
{ 
	yyparser.yylval = new ParserVal(3);  
	return Month; 
}

/*		Α	α			Π			π		Ρ			ρ		Ι		ι			ί		Ί			Λ		λ			Ι		ι			Ο		ο			Σ		ς*/
["\u0391" "\u03B1"] ["\u03A0" "\u03C0"] ["\u03A1" "\u03C1"] ["\u0399" "\u03B9" "\u03AF" "\u038A"]  ["\u03BB" "\u039B"] ["\u0399" "\u03B9"] ["\u039F" "\u03BF"] ["\u03A3" "\u03C2"]
{ 
	yyparser.yylval = new ParserVal(4);  
	return Month; 
}

/*	Μ		μ			Α			α		ά		Ά		Ι			ι		Ϊ		ϊ			Ο			ο		Σ			ς*/
["\u039C" "\u03BC"] ["\u0391" "\u03B1" "\u03AC" "\u0386"] ["\u0399" "\u03B9" "\u03AA" "\u03CA"]  ["\u039F" "\u03BF"] ["\u03A3" "\u03C2"]
{ 
	yyparser.yylval = new ParserVal(5); 
	return Month; 
}

/*	Ι		ι			Ο			ο		Υ			υ		Ύ		ύ		Ν			ν			Ι			ι		Ο		ο			Σ		ς*/
["\u0399" "\u03B9"] ["\u039F" "\u03BF"] ["\u03A5" "\u03C5" "\u038E" "\u03CD"] ["\u039D" "\u03BD"] ["\u0399" "\u03B9"] ["\u039F" "\u03BF"] ["\u03A3" "\u03C2"]
{ 
	yyparser.yylval = new ParserVal(6);  
	return Month; 
}

/*	Ι		ι			Ο			ο		Υ		υ		Υ			ύ		λ			Λ		Ι			ι			Ο		ο			Σ		ς*/
["\u0399" "\u03B9"] ["\u039F" "\u03BF"] ["\u03A5" "\u03C5" "\u038E" "\u03CD"] ["\u03BB" "\u039B"] ["\u0399" "\u03B9"] ["\u039F" "\u03BF"] ["\u03A3" "\u03C2"]
{ 
	yyparser.yylval = new ParserVal(7); 
	return Month; 
}


/*	Α		Α			Υ		υ		Υ			ύ			Γ			γ			Ο		ο		Υ		υ			Σ			σ	*/
["\u0391" "\u03B1"] ["\u03A5" "\u03C5" "\u038E" "\u03CD"]  ["\u0393" , "\u03B3"] ["\u039F" "\u03BF"] ["\u03A5" "\u03C5"]  ["\u03A3" "\u03C3"]
																					/*	Τ			τ		Ο			ο		Σ		ς	*/
															 						["\u03A4" "\u03C4"] ["\u039F" "\u03BF"] ["\u03A3" "\u03C2"]
{ 
	yyparser.yylval = new ParserVal(8); 	
	return Month; 
}	

/*	Σ			σ		Ε		ε			Π			π		Τ		τ			Ε		ε		'Ε			έ			Μ		μ		*/
["\u03A3" "\u03C3"] ["\u0395" "\u03B5"] ["\u03A0" "\u03C0"] ["\u03A4" "\u03C4"] ["\u0395" "\u03B5" "\u0388" "\u03AD"]  ["\u039C" "\u03BC"] 
										/*	Β			β		Ρ		ρ			Ι			ι		Ο			ο		Σ		ς*/
										["\u0392" "\u03B2"] ["\u03A1" "\u03C1"] ["\u0399" "\u03B9"] ["\u039F" "\u03BF"] ["\u03A3" "\u03C2"]
{
	yyparser.yylval = new ParserVal(9);  
	return Month; 
}

/*	Ο		ο			Κ			κ		Τ		τ				Ω		ω		ώ		Ώ			Β			β		Ρ		ρ	*/
["\u039F" "\u03BF"] ["\u039A" "\u03BA"]  ["\u03A4" "\u03C4"]  ["\u03A9" "\u03C9" "\u03CE" "\u038F"] ["\u0392" "\u03B2"] ["\u03A1" "\u03C1"] 
																/*	Ι			ι		Ο			ο		Σ		ς	*/
																["\u0399" "\u03B9"] ["\u039F" "\u03BF"] ["\u03A3" "\u03C2"]
{ 
	yyparser.yylval = new ParserVal(10);  
	return Month; 
}

/*	Ν			ν			Ο		ο		Ε		ε			έ 		Έ			Μ		μ*/
["\u039D" "\u03BD"]  ["\u039F" "\u03BF"] ["\u0395" "\u03B5" "\u0388" "\u03AD"]  ["\u039C" "\u03BC"] 
										/*	Β			β		Ρ		ρ			Ι			ι		Ο			ο		Σ		ς*/
										["\u0392" "\u03B2"] ["\u03A1" "\u03C1"] ["\u0399" "\u03B9"] ["\u039F" "\u03BF"] ["\u03A3" "\u03C2"]

{
	yyparser.yylval = new ParserVal(11);  
    return Month; 
}

/*	Δ			δ		Ε		ε			Κ			κ		Ε		ε			έ		Έ			Μ		μ*/
["\u0394" "\u03B4"] ["\u0395" "\u03B5"] ["\u039A" "\u03BA"] ["\u0395" "\u03B5" "\u0388" "\u03AD"] ["\u039C" "\u03BC"] 
										/*	Β			β		Ρ		ρ			Ι			ι		Ο			ο		Σ		ς*/
										["\u0392" "\u03B2"] ["\u03A1" "\u03C1"] ["\u0399" "\u03B9"] ["\u039F" "\u03BF"] ["\u03A3" "\u03C2"]
{
	yyparser.yylval = new ParserVal(12);  
	return Month; 
}




"["		{ return('['); }
"]"		{ return(']'); }
"^"		{ return('^'); }
"`"		{ return('`'); }
"-"		{ return('-'); }
"&"		{ return('&'); }
","		{ return(','); }
";"		{ return(';'); }
":"		{ return(':'); }
"$"		{ return('$'); }
"."		{ return('.'); }
"!"		{ return('!'); }
">"		{ return('>'); }
"<"		{ return('<'); }
"/"		{ return('/'); }
"?"		{ return('?'); }
"*"		{ return('*'); }


{Force} {}
			/*	ο		ς*/
{Integer}/"\u03BF""\u03C2"	{						
					yybegin(nominative_number_mode);
					yyparser.yylval = new ParserVal(Integer.parseInt(yytext()));
				}

<nominative_number_mode>{	
	/*	ο		ς*/									
	"\u03BF""\u03C2"	{										
				yybegin(YYINITIAL);
				return( nominative_number);
			}
}
			/*	ο		υ*/
{Integer}/"\u03BF""\u03C5"	{ 			
					yybegin(genitive_number_mode);
					yyparser.yylval = new ParserVal(Integer.parseInt(yytext())); 
				}

<genitive_number_mode>{	
	/*	ο		υ*/									
	"\u03BF""\u03C5"	{										
				yybegin(YYINITIAL);
				return ( genitive_number );
			}
}

	/*	α		β		γ		δ	*/
["\u03B1" "\u03B2" "\u03B3" "\u03B4"]/"'"    {
                yybegin(lower_mode);
                String text = yytext();

                if(text.equals("\u03B1")) //α
                	yyparser.yylval = new ParserVal(1);
                else if(text.equals("\u03B2")) //β
                	yyparser.yylval = new ParserVal(2);
                else if(text.equals("\u03B3")) //γ
                	yyparser.yylval = new ParserVal(3);
                else	//δ
                	yyparser.yylval = new ParserVal(4);             
            }

<lower_mode>{
	"'"	{
			yybegin(YYINITIAL);
			return( LETTER);
		}
}

[1]/st 			{   
					yybegin(first_mode);
					yyparser.yylval = new ParserVal(Integer.parseInt(yytext()));
                }

[2]/nd			{   
					yybegin(first_mode);
					yyparser.yylval = new ParserVal(Integer.parseInt(yytext()));
                }

[3]/rd			{   
					yybegin(first_mode);
					yyparser.yylval = new ParserVal(Integer.parseInt(yytext()));
                }

<first_mode>{
	("st"|"nd"|"rd")	{
								yybegin(YYINITIAL);
								return( nominative_number);
							}
}

{Integer}/th	{
					yybegin(english_number_mode);
					yyparser.yylval = new ParserVal(Integer.parseInt(yytext()));
				}

<english_number_mode>{
	"th"	{
								yybegin(YYINITIAL);
								return( nominative_number);
							}
}

{RegularId}     {
                    return(IDENTIFIER);
                }
