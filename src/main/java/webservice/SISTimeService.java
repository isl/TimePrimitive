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
package webservice;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import timeprimitve.SISdate;


@Path("/")
public class SISTimeService {
	
	/**
	 * Handles request in default WebService path
	 * @return
	 */
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response defaultService(){
		return Response.status(200).build(); 
	}
	
	@GET
	@Path("/getIntegerRep")
	@Produces(MediaType.APPLICATION_JSON)
	public Response parseTimeExpr(@QueryParam("expr") String expr){
		
		TimeRepresentation timeRep = new TimeRepresentation();
		
		SISdate sisDate = new SISdate(expr);
		timeRep.setFrom(sisDate.getFrom());
		timeRep.setTo(sisDate.getTo());
		
		return Response.status(200).entity(timeRep).build();
	}

}
