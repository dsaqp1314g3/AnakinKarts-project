package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.DataSourceSPA;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model.Evento;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model.EventoCollection;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.MediaType;

@Path("/events")
public class EventoResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	@Context
	private SecurityContext security;// Variable
	
	@GET//INCOMPLETA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	@Produces(MediaType.ANAKINKARTS_API_EVENTO_COLLECTION)
	public EventoCollection getEventos(){//SIN PAGINAR
		EventoCollection eventos= new EventoCollection();
		
		Connection conn = null;
		try {
			conn = ds.getConnection();// Conectamos con la base de datos
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
		PreparedStatement stmt=null;
		try{
			stmt=conn.prepareStatement(builGetEventsQuery());
			
			
		}catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		
		return eventos;
	}

	private String builGetEventsQuery() {
		
		return null;
	}
	
	@GET
	@Path("/{eventoid}")
	@Produces(MediaType.ANAKINKARTS_API_EVENTO)
	public Response getEvento (@PathParam("eventoid") String eventoid, @Context Request request){
		
		// Create CacheControl
		CacheControl cc = new CacheControl();	
		
		Evento evento=getEventoFromDatabase(eventoid);
		
		return null;
	}

	private Evento getEventoFromDatabase(String eventoid) {
		
		return null;
	}
	
	
	
}
