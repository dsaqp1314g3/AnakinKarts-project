package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceUsers {

	 private DataSource dataSource;
		private static DataSourceUsers instance;

		private DataSourceUsers() {
			super();
			Context envContext = null;
			try {
				envContext = new InitialContext();
				Context initContext = (Context) envContext.lookup("java:/comp/env");
				dataSource = (DataSource) initContext.lookup("jdbc/anakinkartsdb");//Cambiar el nobre de la base de datos, por la nuestra. Añadirlo en el src->main->webapp-META-INF->context
			} catch (NamingException e1) {
				e1.printStackTrace();
			}
		}

		public final static DataSourceUsers getInstance() {
			if (instance == null)
				instance = new DataSourceUsers();
			return instance;
		}

		public DataSource getDataSource() {
			return dataSource;
		}
	
	
}
