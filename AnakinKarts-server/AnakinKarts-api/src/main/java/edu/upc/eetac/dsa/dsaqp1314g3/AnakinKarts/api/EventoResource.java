package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.util.Date;

import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.sql.DataSource;





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
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;






import com.google.gson.Gson;






import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.DataSourceSPA;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model.Alquiler;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model.Evento;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model.EventoCollection;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model.Factura;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model.EventoCollection;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model.EventoCollectionAndroid;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model.Eventoandroid;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model.Factura;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model.Invitacion;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model.User;
import edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.MediaType;













import javax.ws.rs.core.Application;



@Path("/events")
@Produces(MediaType.ANAKINKARTS_API_EVENTO_COLLECTION)
public class EventoResource {

	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	@Context
	private SecurityContext security;// Variable

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
				evento.setNombre(rs.getString("nombre"));
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
				evento.setNombre(rs.getString("nombre"));
				evento.setEventoid(rs.getInt("eventoid"));
				evento.setNumpersonas(rs.getInt("participantes"));
				evento.setFecha(rs.getString("fecha"));
				evento.setPista(rs.getInt("pista"));
				evento.setOrganizador(rs.getString("organizador"));
				evento.setGanador(rs.getString("ganador"));

				evento.setMejorvuelta(rs.getInt("mejorvuelta"));

				evento.setOrganizador(rs.getString("organizador"));
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

	/*private String buildGetOnlyPlayers() {
		return "select * from relacion where eventoid = ? ;";
	}

	private String buildGetOnlyEvent() {
		return "select * from evento where eventoid = ? ;";
	}*/
	

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
			stmt.setString(2, evento.getFecha());
			stmt.setInt(3, evento.getPista());
			stmt.setString(4, evento.getOrganizador());
			stmt.setString(5, evento.getNombre());

			System.out.println("hemos llegado aqui: stmt"+stmt);

			stmt.executeUpdate();

			System.out.println("Miramos contestacion query jajaldjfla");
			ResultSet rs = stmt.getGeneratedKeys();
			System.out.println(rs);
			if (rs.next()) {
				System.out.println("Evento creado correctamente");
				//int alquilerid = rs.getInt(1);

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
	public EventoCollection getEventospriv(@PathParam ("username") String username, @QueryParam("length") int length,
			@QueryParam("after") int after) {
		
	EventoCollection privados = new EventoCollection();
		Connection conn = null;
		try {
			conn = ds.getConnection();// Conectamos con la base de datos
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
		System.out.println("Conexión establecida con  la BD");
		
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
					stmt.setString(1, username);
				else
					stmt.setInt(1, length);
			}
			ResultSet rs = stmt.executeQuery();
			System.out.println("Query: "+stmt);
			while (rs.next()) {
				Evento evento = new Evento();
				evento.setEventoid(rs.getInt("eventoid"));
				evento.setFecha(rs.getString("fecha"));
				evento.setGanador(rs.getString("ganador"));
				evento.setMejorvuelta(rs.getInt("mejorvuelta"));
				evento.setNumpersonas(rs.getInt("participantes"));
				evento.setOrganizador(rs.getString("organizador"));
				evento.setPista(rs.getInt("pista"));
				
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


		
				System.out.println("Evento: "+ evento);
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
			return "select e.* from evento e, relacion r where r.eventoid = e.eventoid and r.username = ? and e.privacidad = 'privado';";
		else
			return "select e.* from evento e, relacion r where r.eventoid = e.eventoid and r.username = ? and e.privacidad = 'privado';";

	}



	
	
	
	
	
	
	
	
	
	@GET
	@Path("/android")
	@Produces(MediaType.ANAKINKARTS_API_EVENTO_COLLECTION_android)
	public EventoCollectionAndroid getEventosPublicoAndroid(@QueryParam("length") int length,
			@QueryParam("after") int after) {

		System.out.println("Dentro de getEventosub");
		EventoCollectionAndroid eventos = new EventoCollectionAndroid();

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
				Eventoandroid evento = new Eventoandroid();
				evento.setNombre(rs.getString("nombre"));
				evento.setEventoid(rs.getInt("eventoid"));
				evento.setFecha(rs.getString("fecha"));
				evento.setGanador(rs.getString("ganador"));
				evento.setMejorvuelta(rs.getInt("mejorvuelta"));
				evento.setNumpersonas(rs.getInt("participantes"));
				evento.setOrganizador(rs.getString("organizador"));
				evento.setPista(rs.getInt("pista"));

				System.out.println("Evento cogido todo");


				eventos.addEventoandroid(evento);
				System.out.println("Evento añadido");
				

				

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
//		Gson gson = new Gson();
//		String a = gson.toJson(eventos);


		return eventos;
	}
	

	
	
	
	
	
	

	
	
	
	@GET
	@Path ("/android/{username}")
	@Produces(MediaType.ANAKINKARTS_API_EVENTO_COLLECTION_android)
	public EventoCollectionAndroid getEventosPrivadosAndroid(@PathParam ("username") String username, @QueryParam("length") int length,
			@QueryParam("after") int after) {

	EventoCollectionAndroid privados = new EventoCollectionAndroid();
		Connection conn = null;
		try {
			conn = ds.getConnection();// Conectamos con la base de datos
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
		System.out.println("Conexión establecida con  la BD");
		
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
					stmt.setString(1, username);
				else
					stmt.setInt(1, length);
			}
			ResultSet rs = stmt.executeQuery();
			System.out.println("Query: "+stmt);
			while (rs.next()) {
				Eventoandroid evento = new Eventoandroid();
				evento.setEventoid(rs.getInt("eventoid"));
				evento.setFecha(rs.getString("fecha"));
				evento.setGanador(rs.getString("ganador"));
				evento.setMejorvuelta(rs.getInt("mejorvuelta"));
				evento.setNumpersonas(rs.getInt("participantes"));
				evento.setOrganizador(rs.getString("organizador"));
				evento.setNombre(rs.getString("nombre"));
				evento.setPista(rs.getInt("pista"));
				
				
				
				System.out.println("Evento: "+ evento);
				privados.addEventoandroid(evento);
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
	
	@POST
	@Path ("/alquiler")
	@Consumes(MediaType.ANAKINKARTS_API_ALQUILER)
	@Produces(MediaType.ANAKINKARTS_API_FACTURA)
	public Factura createAlquiler (Alquiler alquiler){
		
		System.out.println("Alquilamos");
		Factura factura = new Factura();
		 int alquilerid=0;
		Connection conn = null;
		try {
			conn = ds.getConnection();// Conectamos con la base de datos
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;		
		try{
			stmt = conn.prepareStatement(buildCreateAlquiler());
			stmt.setString(1, alquiler.getOrganizador());
			stmt.setString(2, alquiler.getFecha());
			stmt.setInt(3, alquiler.getPista());
			stmt.setInt(4, alquiler.getNumplayers());
			
			System.out.println("Query: "+ stmt);
			
			int row=stmt.executeUpdate();
			if(row!=0)
				System.out.println("Evento creado");
			else
				throw new BadRequestException("No se ha podido crear el alquiler");
			
			System.out.println(" query ejecutada");
			
			if ( alquiler.getPista()==1)
				factura.setPrecio(40.00);
			if ( alquiler.getPista()==2)
				factura.setPrecio(45.00);
			if ( alquiler.getPista()==3)
				factura.setPrecio(55.00);
			if ( alquiler.getPista()==4)
				factura.setPrecio(35.00);
			
			System.out.println("Pista: "+ alquiler.getPista()+" Precio: "+factura.getPrecio());
			
			alquilerid= searchAlquilerID(alquiler.getOrganizador(),alquiler.getFecha(),alquiler.getPista(), alquiler.getNumplayers());
			
			factura.setAlquilerid(alquilerid);
			factura.setOrganizador(alquiler.getOrganizador());
			
			
			stmt2 = conn.prepareStatement(buildCreateFactura());
			stmt2.setDouble(1, factura.getPrecio());
			stmt2.setInt(2 , factura.getAlquilerid());
			
			System.out.println("Query: "+ stmt2);
			int row2=stmt2.executeUpdate();
			if(row2!=0)
				System.out.println("Evento creado");
			else{
				throw new BadRequestException("No se ha podido crear la factura");
			}
			
			System.out.println(" query ejecutada");
			
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
		
		return factura;
	}

	private int searchAlquilerID(String organizador, String fecha, int pista,
			int numplayers) {
		int alquilerid=0;
		Connection conn = null;
		try {
			conn = ds.getConnection();// Conectamos con la base de datos
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
		PreparedStatement stmt = null;	
		try{
			stmt = conn.prepareStatement(buildSearchAlquiler());
			stmt.setString(1, organizador);
			stmt.setString(2, fecha);
			stmt.setInt(3, pista);
			stmt.setInt(4, numplayers);
			
			System.out.println("Query: "+ stmt);
			ResultSet rs = stmt.executeQuery();
			System.out.println("Query ejecutada");
			if(rs.next()){
				
				alquilerid = rs.getInt("alquilerid");
				
			}else {
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

		return alquilerid;
	}

	private String buildSearchAlquiler() {
		return "select alquilerid from alquiler where organizador=? and fecha=? and pista=? and nplayers=?;";
	}

	private String buildCreateFactura() {
		return "insert into factura (precio,alquilerid) values (?,?); ";
	}

	private String buildCreateAlquiler() {
		return "insert into alquiler (organizador,fecha,pista, nplayers) values (?,?,?,?);";
	}
	
	
	@PUT
	@Path("/{nombre}")
	@Consumes(MediaType.ANAKINKARTS_API_EVENTO)
	@Produces(MediaType.ANAKINKARTS_API_EVENTO)
	public Evento updateEvents(@QueryParam ("nombre") String nombre, Evento evento){
		
		System.out.println("Dentro de updateEvents");
		
		Evento eventoquery = new Evento();
		
		
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
			String sql = buildUpdateEvents();
			System.out.println("Query escrita");
			stmt = conn.prepareStatement(sql);
			System.out.println("Query cargada");
			stmt.setString(1, evento.getOrganizador());
			stmt.setString(4, evento.getNombre());
			stmt.setInt(3, evento.getMejorvuelta());
			stmt.setString(2, evento.getGanador());
			
			
			
		
			
			System.out.println("Query completa");
			System.out.println("La query es: " + stmt);
			int row = stmt.executeUpdate();
			
			System.out.println("Query ejecutada");
			if (row !=0 ) {
				stmt.close();
				System.out.println("se ha actualizado correctamente.");
			}	System.out.println("Creando la segunda query");
			stmt = conn.prepareStatement(buildSelectEventos());
			System.out.println("Query cargada");
			stmt.setString(1, evento.getNombre());
			//stmt.setString(1, security.getUserPrincipal().getName());
			System.out.println("Query 2 completa");
			ResultSet rs = stmt.executeQuery();
			System.out.println("Query 2 ejecutada");

			
			if (rs.next()) {
				System.out.println("Miramos contestacion query");

				eventoquery.setOrganizador(rs.getString("organizador"));
				
					System.out.println("Organizador: "+eventoquery.getOrganizador());
					eventoquery.setGanador(rs.getString("ganador"));
					
					System.out.println("Ganador: "+eventoquery.getGanador());
					eventoquery.setMejorvuelta(rs.getInt("mejorvuelta"));
					
					System.out.println("Mejor vuelta: "+eventoquery.getMejorvuelta());
			}		
			
			else {
				throw new BadRequestException("Can't update this event");
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
		
		
		
		
		return evento;
	}
	

	

	private String buildUpdateEvents() {
		return "update evento set organizador=ifnull(?, organizador), ganador=ifnull(?, ganador), mejorvuelta=if(?, mejorvuelta) where nombre=?;";
	}
	private String buildSelectEventos() {
		return "select nombreevento, ganador, mejorvuelta from evento where nombre=?;";
	}

	
	

}

