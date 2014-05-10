package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api;

import javax.sql.DataSource;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.DataSourceUsers;

@Path("/users")
public class UserResource {
	
	private DataSource ds = DataSourceUsers.getInstance().getDataSource();
	
	@Context
	private SecurityContext security;// Variable
	
	

}
