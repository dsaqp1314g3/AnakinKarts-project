package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;

import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;



import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.mysql.jdbc.Statement;

import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model.User;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model.UserCollection;

@Path("/users")
public class UserResource {
	
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	
	@Context
	private SecurityContext security;// Variable
	
	@PUT
	@Path("/{username}")
	@Consumes(MediaType.ANAKINKARTS_API_USER)
	@Produces(MediaType.ANAKINKARTS_API_USER)
	public User updateProfile(@PathParam("username") String username, User user){
		
		System.out.println("Dentro de updateProfile");
		/*Peta aquí*/
		/*System.out.println(" Nombre de la cabecera de seguridad: "+ security.getUserPrincipal().getName()+ " username cogido del path");
		
		if (security.getUserPrincipal().getName()!=username)
			throw new ForbiddenException("You are not allowed to modify this profile.");
		System.out.println("Eres el user indicado");*/
		
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
		
		try{
			String sql = buildUpdateUser();
			System.out.println("Query escrita");
			stmt = conn.prepareStatement(sql);
			System.out.println("Query cargada");
			stmt.setString(1, user.getEmail());
			stmt.setString(2, user.getName());
			stmt.setInt(3, user.getNphone());
			stmt.setInt(4, user.getNphone());
			stmt.setString(5, user.getCiudad());
			stmt.setString(6, user.getCalle());
			stmt.setInt(7, user.getNumportal());
			stmt.setInt(8, user.getNumportal());
			stmt.setInt(9, user.getPiso());
			stmt.setInt(10, user.getPiso());
			stmt.setInt(11, user.getNumpuerta());
			stmt.setInt(12, user.getNumpuerta());
			stmt.setInt(13,user.getCp());
			stmt.setInt(14, user.getCp());
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


	@GET
	@Path("/login")
	@Consumes (MediaType.ANAKINKARTS_API_USER)
	@Produces (MediaType.ANAKINKARTS_API_USER)
	public User Login(@QueryParam("user") String user,
			@QueryParam("pass") String pass) {

		Connection conn = null;
		try {
			conn = ds.getConnection();// Conectamos con la base de datos
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		User usuario = new User();
		try {
			stmt = conn.prepareStatement(buildLoginQuery());
			stmt.setString(1, user);
			stmt.setString(2, pass);
			ResultSet rs = stmt.executeQuery();
			boolean confirm = false;

			if (rs.next()) {

//				User usuario = new User();
//				// not necessary(?)
				//System.out.println(rs.getString("username"));
				usuario.setUsername(rs.getString("username"));
				usuario.setUserpass(rs.getString("userpass"));

//				String username = usuario.getUsername();
//				String userpass = usuario.getUserpass();
//				if (user == username && pass == userpass) {

					confirm = true;
					System.out.println("autenticat");

				}

			 else {
				throw new ForbiddenException("you are not registered");
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

		return usuario;
	}

	private String buildLoginQuery() {
		return "select username, userpass from users where username = ? and userpass = ?";
	}

	
	
	//Faltaría mirar si hay parametros obligatorios y opcionales
	@POST
	@Path("/register")
	@Consumes(MediaType.ANAKINKARTS_API_USER)
	@Produces(MediaType.ANAKINKARTS_API_USER)
	public User createUser(User user) {


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
			
			String sql = buildInsertUser();
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, user.getName());
			stmt.setString(2, user.getUsername());
			stmt.setString(3, user.getUserpass());
			stmt.setString(4, user.getEmail());
			stmt.setInt(5, user.getNphone());
			stmt.setString(6, user.getCiudad());
			stmt.setInt(7, user.getCp());
			stmt.setString(8, user.getCalle());
			stmt.setInt(9, user.getNumportal());
			stmt.setInt(10, user.getPiso());
			stmt.setInt(11, user.getNumpuerta());

			System.out.println("hemos llegado aqui");

			stmt.executeUpdate();
			System.out.println("Miramos contestacion query jajaldjfla");
			ResultSet rs = stmt.getGeneratedKeys();
		
			if (rs.next()) {
				System.out.println(" se ha podido crear el usuario");
				
			} else {
				
				System.out.println("No se ha podido crear el usuario");
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

		return user;

	}

	private String buildInsertUser() {

		
		return "insert into users (name, username, userpass, email,   phone, ciudad, cp, calle, numero, piso, puerta) value (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		
	}
	
	
	

	//Falta poner las restricciones de quien puede borrar
	@DELETE
	@Path("/{username}")
	public void deleteUser(@PathParam("username") String username){

		

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt=null;

		try{
			String sql = buildDeleteUser();
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, username);

			int rows = stmt.executeUpdate();

			if (rows == 0) {
				throw new NotFoundException("There's no user with username="
						+ username);
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


		
	}

	private String buildDeleteUser() {
		return "delete from users where username=?;";
	}

	
	
	
	
	
	
	
	
}



	
