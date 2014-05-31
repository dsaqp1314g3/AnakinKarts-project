package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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

//				User usuario = new User();
//				// not necessary(?)
//				usuario.setUsername(rs.getString("username"));
//				usuario.setUserpass(rs.getString("userpass"));
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
