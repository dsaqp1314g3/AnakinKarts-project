package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;


import javax.sql.DataSource;


import javax.sql.DataSource;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.DataSourceSPA;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model.Evento;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model.EventoCollection;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model.User;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.MediaType;


@Path("/events")
@Produces(MediaType.ANAKINKARTS_API_EVENTO_COLLECTION)
public class EventoResource {

	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	@Context
	private SecurityContext security;
	private Application app;// Variable

	@GET
	@Produces(MediaType.ANAKINKARTS_API_EVENTO_COLLECTION)
	public EventoCollection getEventosPub(@QueryParam("length") int length,
			@QueryParam("after") int after) {

		System.out.println("Dentro de getEventosub");
		EventoCollection eventos = new EventoCollection();

		Connection conn = null;
		try {
			conn = ds.getConnection();// Conectamos con la base de datos
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		System.out.println("BD establecida");

		PreparedStatement stmt = null;
		try {
			boolean updateFromLast = after > 0;
			stmt = conn.prepareStatement(buildGetEventsQuery(updateFromLast));
			if (updateFromLast) {
				if (length == 0) {
					stmt.setInt(1, after);
					stmt.setInt(2, 3);
				} else {
					stmt.setInt(1, after);
					stmt.setInt(2, length);
				}
			} else {

				if (length == 0)
					stmt.setInt(1, 3);
				else
					stmt.setInt(1, length);
			}

			System.out.println("La query es: " + stmt);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.println("Evento cogido");
				Evento evento = new Evento();

				evento.setEventoid(rs.getInt("eventoid"));
				evento.setFecha(rs.getString("fecha"));
				evento.setGanador(rs.getString("ganador"));
				evento.setMejorvuelta(rs.getInt("mejorvuelta"));
				evento.setNumpersonas(rs.getInt("participantes"));
				evento.setOrganizador(rs.getString("organizador"));
				evento.setPista(rs.getInt("pista"));

				System.out.println("Evento cogido todo");

				// Nos encargamos de ahora de los jugadores
				PreparedStatement stmtr = null;
				stmtr = conn.prepareStatement(buildGetPlayersFromEvent());
				stmtr.setInt(1, evento.getEventoid());

				ResultSet rsr = stmtr.executeQuery();

				while (rsr.next()) {
					System.out.println("Jugador recogido");
					evento.addJugadores(rsr.getString("username"));
					System.out.println("jugador añadido");
				}

				eventos.addEvento(evento);
				System.out.println("evento añadido");
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

		return eventos;
	}

	private String buildGetPlayersFromEvent() {
		return "SELECT username FROM relacion WHERE eventoid=? AND invitacion='aceptada';";
	}

	private String buildGetEventsQuery(boolean updateFromLast) {
		if (updateFromLast)
			return "SELECT * FROM evento  WHERE privacidad='publico' AND eventoid > ?limit ?;";
		else
			return "SELECT * FROM evento  WHERE privacidad='publico' limit ?;";
	}

	@GET
	// Finalizado
	@Path("/{eventoid}")
	@Produces(MediaType.ANAKINKARTS_API_EVENTO)
	public Response getEvento(@PathParam("eventoid") String eventoid,
			@Context Request request) {

		System.out.println("Estamos dentro del metodo getEvento");

		// Create CacheControl
		CacheControl cc = new CacheControl();

		Evento evento = getEventoFromDatabase(eventoid);
		System.out.println("Evento recogido");

		// Creamos un String para poder hascerleuna funcion de hash
		String funcion = evento.getEventoid() + evento.getFecha();
		System.out.println("String cogido");

		// Calculate the ETag on last modified date of user resource
		EntityTag eTag = new EntityTag(Long.toString(funcion.hashCode()));
		System.out.println("Hash creado");

		// Comparamos el eTag creado con el que viene de la peticiOn HTTP
		Response.ResponseBuilder rb = request.evaluatePreconditions(eTag);// comparamos

		System.out.println("Ya hemos hecho el eTag");
		if (rb != null) {// Si el resultado no es nulo, significa que no ha sido
							// modificado el contenido ( o es la 1º vez )
			return rb.cacheControl(cc).tag(eTag).build();
		}

		// Si es nulo construimos la respuesta de cero.
		rb = Response.ok(evento).cacheControl(cc).tag(eTag);
		System.out.println("Ya hemos hmirado el cache2");

		return rb.build();
	}

	private Evento getEventoFromDatabase(String eventoid) {

		System.out.println("Dentro del getEvenoFromDataBase");
		Evento evento = new Evento();

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		System.out.println("Connexion Base Datos establecida");
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try {

			stmt = conn.prepareStatement(buildGetEventogByIdQuery());
			System.out.println("Query escrita");
			stmt.setInt(1, Integer.valueOf(eventoid));
			System.out.println("Query completa: " + stmt);
			ResultSet rs = stmt.executeQuery();
			System.out.println("Query ejecutada");
			if (rs.next()) {
				System.out.println("Cogiendo datos");
				evento.setEventoid(rs.getInt("eventoid"));
				evento.setNumpersonas(rs.getInt("participantes"));
				evento.setFecha(rs.getString("fecha"));
				evento.setPista(rs.getInt("pista"));
				evento.setGanador(rs.getString("ganador"));
				evento.setMejorvuelta(rs.getInt("mejorvuelta"));
				System.out.println("Evento cogido");
				evento.addJugadores(rs.getString("username"));
				while (rs.next()) {
					System.out.println("Mirando jugadores");
					User user = new User();
					user.setUsername(rs.getString("username"));
					System.out.println("Jugador visto: " + user.getUsername());
					evento.addJugadores(user.getUsername());
					System.out.println("Jugador metido");
				}
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
		return evento;
	}

	private String buildGetEventogByIdQuery() {

		return "select e.*,r.username from evento e, relacion r where r.eventoid=e.eventoid and r.eventoid=?;";
	}

	// Faltaría mirar si hay parametros obligatorios y opcionales
	@POST
	@Consumes(MediaType.ANAKINKARTS_API_EVENTO)
	@Produces(MediaType.ANAKINKARTS_API_EVENTO)
	public Evento createEvent(Evento evento) {

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

			String sql = buildInsertEvent();
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setInt(1, evento.getNumpersonas());

			stmt.setString(2,evento.getFecha());
			stmt.setInt(3, evento.getPista());
			stmt.setString(4, evento.getOrganizador());
			stmt.setString(5, evento.getNombre());

			System.out.println("hemos llegado aqui: stmt"+stmt);
			stmt.executeUpdate();

			System.out.println("Miramos contestacion query jajaldjfla");
			ResultSet rs = stmt.getGeneratedKeys();

			if (rs.next()) {
				System.out.println("Evento creado correctamente");

			} else {
				throw new BadRequestException("No se ha podido crear el evento");
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

		return evento;

	}

	private String buildInsertEvent() {
		System.out.println("insertamos");
		return "insert into evento (participantes, fecha, pista, organizador, privacidad, nombre ) values (?, ?, ?, ?, 'privado', ?) ;";

	}

	@DELETE
	@Path("/{eventoid}")
	// Mirar la validación de la acción de eliminar
	public void deleteEvento(@PathParam("eventoid") int eventoid) {
		System.out.println("Dentro del deleteEvento");
		if (!security.isUserInRole("admin"))
			throw new ForbiddenException(
					"You are not allowed to delete a event");
		Connection conn = null;
		// validateUser(stingid);
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(buildDeleteEvento());
			stmt.setInt(1, eventoid);

			int rows = stmt.executeUpdate();
			if (rows == 0)
				throw new NotFoundException("There's no sting with eventoid="
						+ eventoid);

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

	private String buildDeleteEvento() {
		return "delete from evento where eventoid=?";
	}
	
	

	@GET
	@Path ("/{username}/priv")
	@Produces(MediaType.ANAKINKARTS_API_EVENTO_COLLECTION)
	public EventoCollection getEventospriv(@QueryParam ("username") String username, @QueryParam("length") int length,
			@QueryParam("after") int after) {
		
	EventoCollection privados = new EventoCollection();

		Connection conn = null;
		try {
			conn = ds.getConnection();// Conectamos con la base de datos
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;

		try {
			boolean updateFromLast = after > 0;
			System.out.println("1");
			stmt = conn.prepareStatement(buildGetEventroprivQuery(updateFromLast));
			System.out.println("2");
			if (updateFromLast) {
				if (length == 0) {
					stmt.setInt(1, after);
					stmt.setInt(2, 5);
				} else {
					stmt.setInt(1, after);
					stmt.setInt(2, length);
				}
			} else {

				if (length == 0)
					stmt.setInt(1, 5);
				else
					stmt.setInt(1, length);
			}

			ResultSet rs = stmt.executeQuery();
			System.out.println("3");
			while (rs.next()) {
				Evento evento = new Evento();

				evento.setEventoid(rs.getInt("eventoid"));
				evento.setFecha(rs.getString("fecha"));
				evento.setGanador(rs.getString("ganador"));
				evento.setMejorvuelta(rs.getInt("mejorvuelta"));
				evento.setNumpersonas(rs.getInt("participantes"));
				evento.setOrganizador(rs.getString("organizador"));
				evento.setPista(rs.getInt("pista"));

				
				privados.addEvento(evento);


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

		return privados;
	}

	private String buildGetEventroprivQuery(boolean updateFromLast) {
		
		if (updateFromLast)
			return "select e.* from evento e, relacion r where r.eventoid = e.eventoid and r.username = ? and e.privacidad = 'privado'";
		else
			return "select e.* from evento e, relacion r where r.eventoid = e.eventoid and r.username = ? and e.privacidad = 'privado'";
	}
	
	
	
	

	
	@PUT
	@Path("/{eventoid}")
	@Consumes(MediaType.ANAKINKARTS_API_EVENTO)
	@Produces(MediaType.ANAKINKARTS_API_EVENTO)
	public Evento updateEvent( Evento evento){
		//@PathParam("eventoid") String eventoid,@FormDataParam("image") InputStream image,
		//@FormDataParam("image") FormDataContentDisposition fileDisposition,
		System.out.println("Dentro de updateEvent");
		//UUID uuid = writeAndConvertImage(image);
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
			String sql = buildUpdateEvent();
			System.out.println("Query escrita");
			stmt = conn.prepareStatement(sql);
			System.out.println("Query cargada");
			stmt.setString(1, evento.getNombre());
			stmt.setString(2, evento.getOrganizador());
			stmt.setString(3, evento.getGanador());
			stmt.setInt(4, evento.getMejorvuelta());
			//stmt.setString(5, uuid.toString());
			//stmt.setString(6, evento.getTitle());
			System.out.println("Query completa");
			int row = stmt.executeUpdate();
			System.out.println("Query ejecutada");
			if (row !=0 ) {
				stmt.close();
				System.out.println("se ha actualizado correctamente.");
			}			
			//System.out.println("Creando la segunda query");
			//stmt = conn.prepareStatement(buildSelectUser());
			//System.out.println("Query cargada");
			//stmt.setString(1, username);
			
			//System.out.println("Query 2 completa");
			//ResultSet rs = stmt.executeQuery();
			//System.out.println("Query 2 ejecutada");

			
			//if (rs.next()) {
				//System.out.println("Miramos contestacion query");

				//userquery.setEmail(rs.getString("email"));
					//System.out.println("Email: "+userquery.getEmail());
				//userquery.setName(rs.getString("name"));
					//System.out.println("Name: "+userquery.getName());
				//userquery.setNphone(rs.getInt("phone"));
					//System.out.println("Phone: "+userquery.getNphone());
				//userquery.setCiudad(rs.getString("ciudad"));
					//System.out.println("Ciudad: "+userquery.getCiudad());
				//userquery.setCalle(rs.getString("calle"));
					//System.out.println("Calle: "+userquery.getCalle());
				//userquery.setPiso(rs.getInt("piso"));
					//System.out.println("Piso: "+userquery.getPiso());
					//userquery.setNumportal(rs.getInt("numero"));
					//System.out.println("Numportal: "+userquery.getNumportal());
				//userquery.setNumpuerta(rs.getInt("puerta"));
					//System.out.println("Puerta: "+userquery.getNumpuerta());
				//userquery.setCp(rs.getInt("cp"));
					//System.out.println("CP: "+userquery.getCp());
				//userquery.setUsername(rs.getString("username"));
					//System.out.println("Username: "+username);
			//} 
			else {
				throw new BadRequestException("Can't view this event");
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
		
		//evento.setFilename(uuid.toString() + ".png");
		//evento.setTitle(title);
		//evento.setImageURL(app.getProperties().get("imgBaseURL")
				//+ evento.getFilename());

		
		
		return evento;
	}
	

	

	private String buildUpdateEvent() {
		return "update users set nombre=ifnull(?, nombre), organizador=ifnull(?, organizador), ganador=ifnull(?, ganador), mejorvuelta=if(?<>0, ?, mejorvuelta), ?,  where eventoid=?;";
	}

	
	
	
	
	@POST
	@Consumes(MediaType.ANAKINKARTS_API_EVENTO)
	public Evento uploadImage(@FormDataParam("title") String title,
			@FormDataParam("image") InputStream image,
			@FormDataParam("image") FormDataContentDisposition fileDisposition) {
		UUID uuid = writeAndConvertImage(image);

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("insert into evento values (?)");
			stmt.setString(1, uuid.toString());
			
			stmt.executeUpdate();
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
		Evento imageData = new Evento();
		imageData.setFilename(uuid.toString() + ".png");
		
		imageData.setImageURL(app.getProperties().get("imgBaseURL")
				+ imageData.getFilename());

		return imageData;
	}

	

	private UUID writeAndConvertImage(InputStream file) {

		BufferedImage image = null;
		try {
			image = ImageIO.read(file);

		} catch (IOException e) {
			throw new InternalServerErrorException(
					"Something has been wrong when reading the file.");
		}
		UUID uuid = UUID.randomUUID();
		String filename = uuid.toString() + ".png";
		try {
			ImageIO.write(
					image,
					"png",
					new File(app.getProperties().get("uploadFolder") + filename));
		} catch (IOException e) {
			throw new InternalServerErrorException(
					"Something has been wrong when converting the file.");
		}

		return uuid;
	}
	


	
	

}
