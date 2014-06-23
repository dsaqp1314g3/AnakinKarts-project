package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api;

import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;



public class AnakinKartsApplication extends ResourceConfig{
	public AnakinKartsApplication () {
		super () ;
		register (DeclarativeLinkingFeature.class ) ;
		}

}
