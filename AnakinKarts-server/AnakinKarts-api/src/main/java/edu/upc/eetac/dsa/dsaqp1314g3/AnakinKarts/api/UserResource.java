package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model.User;

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
			stmt.setString(4, user.getCiudad());
			stmt.setString(5, user.getCalle());
			stmt.setInt(6, portal);
			stmt.setInt(7, piso);
			stmt.setInt(8, puerta);
			stmt.setInt(9, cp);
			stmt.setString(10, username);
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
		return "update users set email=ifnull(?, email), name=ifnull(?, name), phone=ifnull(?, phone), ciudad=ifnull(?, ciudad), calle=ifnull(?, calle), numero=ifnull(?, numero), piso=ifnull(?, piso), puerta=ifnull(?, puerta), cp=ifnull(?, cp) where username=?;";

@Path("/users")
public class UserResource {

	private DataSource ds = DataSourceSPA.getInstance().getDataSource();

	@Context
	private SecurityContext security;// Variable

	// sqluser login
	// @Consumes(MediaType.ANAKINKARTS_API_USER)
	@GET
	@Path("/login")
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

		try {
			stmt = conn.prepareStatement(buildLoginQuery());
			stmt.setString(1, user);
			stmt.setString(2, pass);
			ResultSet rs = stmt.executeQuery();
			boolean confirm = false;

			if (rs.next()) {

				User usuario = new User();
				// not necessary(?)
				usuario.setUsername(rs.getString("username"));
				usuario.setUserpass(rs.getString("userpass"));
				String username = usuario.getUsername();
				String userpass = usuario.getUserpass();
				if (user == username && pass == userpass) {

					confirm = true;
				}

			} else {
				throw new NotFoundException("you are not registered");
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

		return null;
	}

	private String buildLoginQuery() {
		return "select username, userpass from users where username = ? and userpass = ?";
	}

	@POST
	@Path("/register")
	@Consumes(MediaType.ANAKINKARTS_API_USER)
	@Produces(MediaType.ANAKINKARTS_API_USER)
	public User createUser(User user) {

		System.out.println("hemos llegado aquiiiiiiiiiiiii");
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			System.out.println(e);
			throw new ServerErrorException("Could not connect to the database"
					+ e, Response.Status.SERVICE_UNAVAILABLE);
		}
		System.out.println("hemos llegado aqui1");
		PreparedStatement stmt = null;

		try {
			String sql = buildInsertUser();
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, user.getName());
			stmt.setString(2, user.getUsername());
			// stmt.setString(3, user.getUserpass());
			stmt.setString(3, user.getEmail());
			stmt.setInt(4, user.getNphone());
			stmt.setString(5, user.getCiudad());
			stmt.setString(6, user.getCp());
			stmt.setString(7, user.getCalle());
			stmt.setInt(8, user.getNumportal());
			stmt.setInt(9, user.getPiso());
			stmt.setInt(10, user.getNumpuerta());
			System.out.println("hemos llegado aqui");

			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {

			} else {
				throw new BadRequestException("Can't create a User");
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
		return "insert into users (email,username,name,phone,ciudad,calle,numero,piso,puerta,cp ) values(?,?,?,?,?,?,?,?,?,?); ";
	}

}
