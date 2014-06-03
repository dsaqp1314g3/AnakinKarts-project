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

import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model.User;


@Path("/events")
public class InvitacionResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	
	
	@POST
	@Path("/{username}/{idevent}/invitacion/{idinvitacion}")
	@Consumes(MediaType.ANAKINKARTS_API_INVITACION)
	@Produces(MediaType.ANAKINKARTS_API_INVITACION)
	public User updateProfile(@PathParam("username") String username, @PathParam("idevent") String idevent, @PathParam("idinvitacion") String idinvitacion, User user){
		
		System.out.println("Dentro de invitar a evento");
		
		User userquery = new User();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
		System.out.println("BD establecida");
		PreparedStatement stmt = null;
		//Pasamos a integer para poder decir que es null
		Integer phone = pasarInteger(user.getNphone());
		System.out.println("Phone-> Int: "+user.getNphone()+" Integer: "+ phone);
		Integer portal = pasarInteger(user.getNumportal());
		System.out.println("Portal-> Int: "+user.getNumportal()+" Integer: "+ portal);
		Integer piso = pasarInteger(user.getPiso());
		System.out.println("Piso-> Int: "+user.getPiso()+" Integer: "+ piso);
		Integer puerta = pasarInteger(user.getNumpuerta());
		System.out.println("Puerta-> Int: "+user.getNumpuerta()+" Integer: "+ puerta);
		Integer cp = pasarInteger(user.getCp());
		System.out.println("CP-> Int: "+user.getCp()+" Integer: "+ cp);
		
		
		try{
			String sql = buildUpdateUser();
			System.out.println("Query escrita");
			stmt = conn.prepareStatement(sql);
			System.out.println("Query cargada");
			stmt.setString(1, user.getEmail());
			stmt.setString(2, user.getName());
			stmt.setInt(3, phone);
			stmt.setInt(4, phone);
			stmt.setString(5, user.getCiudad());
			stmt.setString(6, user.getCalle());
			stmt.setInt(7, portal);
			stmt.setInt(8, portal);
			stmt.setInt(9, piso);
			stmt.setInt(10, piso);
			stmt.setInt(11, puerta);
			stmt.setInt(12, puerta);
			stmt.setInt(13, cp);
			stmt.setInt(14, cp);
			stmt.setString(15, username);
			//stmt.setString(10, security.getUserPrincipal().getName());
			System.out.println("Query completa");
			int row = stmt.executeUpdate();
			System.out.println("Query ejecutada");
			if (row !=0 ) {
				stmt.close();
				System.out.println("se ha actualizado correctamente.");
			}			
			System.out.println("Creando la segunda query");
			stmt = conn.prepareStatement(buildSelectUser());
			System.out.println("Query cargada");
			stmt.setString(1, username);
			//stmt.setString(1, security.getUserPrincipal().getName());
			System.out.println("Query 2 completa");
			ResultSet rs = stmt.executeQuery();
			System.out.println("Query 2 ejecutada");

			
			if (rs.next()) {
				System.out.println("Miramos contestacion query");

				userquery.setEmail(rs.getString("email"));
					System.out.println("Email: "+userquery.getEmail());
				userquery.setName(rs.getString("name"));
					System.out.println("Name: "+userquery.getName());
				userquery.setNphone(rs.getInt("phone"));
					System.out.println("Phone: "+userquery.getNphone());
				userquery.setCiudad(rs.getString("ciudad"));
					System.out.println("Ciudad: "+userquery.getCiudad());
				userquery.setCalle(rs.getString("calle"));
					System.out.println("Calle: "+userquery.getCalle());
				userquery.setPiso(rs.getInt("piso"));
					System.out.println("Piso: "+userquery.getPiso());
					userquery.setNumportal(rs.getInt("numero"));
					System.out.println("Numportal: "+userquery.getNumportal());
				userquery.setNumpuerta(rs.getInt("puerta"));
					System.out.println("Puerta: "+userquery.getNumpuerta());
				userquery.setCp(rs.getInt("cp"));
					System.out.println("CP: "+userquery.getCp());
				userquery.setUsername(rs.getString("username"));
					System.out.println("Username: "+username);
			} else {
				throw new BadRequestException("Can't view the Review");
			}
			
			
			
			
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
		
		
		return userquery;
	}
	
	private Integer pasarInteger(int numeroint){
		
		Integer numinteger = new Integer(numeroint);
		return numinteger;
	}

	private String buildSelectUser() {
		return "select email, name, phone, ciudad, calle, numero, piso , puerta, cp, username from users where username=?;";
	}

	private String buildUpdateUser() {
		return "update users set email=ifnull(?, email), name=ifnull(?, name), phone=if(?<>0, ?, phone), ciudad=ifnull(?, ciudad), calle=ifnull(?, calle), numero=if(?<>0, ?, numero), piso=if(?<>0, ?, piso), puerta=if(?<>0, ?, puerta), cp=if(?<>0, ?, cp) where username=?;";
	}


}
