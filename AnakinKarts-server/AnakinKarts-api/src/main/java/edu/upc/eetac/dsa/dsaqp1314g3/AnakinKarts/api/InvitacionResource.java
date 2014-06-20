package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

import com.mysql.jdbc.Statement;

import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model.Invitacion;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model.User;


@Path("/events")
public class InvitacionResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	
	
	@POST
	@Path("/invitacion/{username}")
	@Consumes(MediaType.ANAKINKARTS_API_INVITACION)
	@Produces(MediaType.ANAKINKARTS_API_INVITACION)
	public Invitacion Invitar(@PathParam("username") String username, Invitacion invitacion){
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			System.out.println(e);
			throw new ServerErrorException("Could not connect to the database"
					+ e, Response.Status.SERVICE_UNAVAILABLE);
		}
		System.out.println("conectados a la base de datos");
		PreparedStatement stmt = null;

		try {
			
			String sql = buildInvitar();
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			
			
			
			stmt.setString(1, username);
			
			stmt.setString(2, invitacion.getNombre());
			
			

			System.out.println("hemos llegado aqui");

			stmt.executeUpdate();
			System.out.println("Miramos contestacion query jajaldjfla");
			ResultSet rs = stmt.getGeneratedKeys();
		
			if (rs.next()) {
				System.out.println(" invitado correctamente");
				
			} else {
				
				System.out.println("No se ha podido invitar");
			}
			
		} catch (SQLException e) {
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

		return invitacion;

	}

	private String buildInvitar() {

		
		return "insert into relacion (username, nombreevento, invitacion ) value (?, ?, 'pendiente') ";
		
	}
	

}
