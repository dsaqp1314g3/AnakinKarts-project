package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api;

import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import java.util.Enumeration;
import java.util.ResourceBundle;



public class AnakinKartsApplication extends ResourceConfig{
	public AnakinKartsApplication () {
		super();
		register(MultiPartFeature.class);
		ResourceBundle bundle = ResourceBundle.getBundle("application");

		Enumeration<String> keys = bundle.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			property(key, bundle.getObject(key));
	}
		//super () ;
		//register (DeclarativeLinkingFeature.class ) ;
		}
	
	

}
