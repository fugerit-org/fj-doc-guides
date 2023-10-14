package org.fugerit.java.doc.guides.helloworld.fm.facade;

import java.io.OutputStream;

import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.doc.base.config.DocException;
import org.fugerit.java.doc.base.config.DocOutput;
import org.fugerit.java.doc.base.config.DocTypeHandler;
import org.fugerit.java.doc.base.process.DocProcessContext;
import org.fugerit.java.doc.freemarker.process.FreemarkerDocProcessConfig;
import org.fugerit.java.doc.freemarker.process.FreemarkerDocProcessConfigFacade;

public class HelloWorldFreeMarkerFacade {

	// in the simple config, there is a default method mapping any chainId to a template with the path ${chainId}.ftl
	private static final FreemarkerDocProcessConfig SIMPLE_CONFIG = 
			// SafeFunction.get() will execute the configuration code and re-throw a ConfigRuntimeException if any exception is thrown
			SafeFunction.get( () -> FreemarkerDocProcessConfigFacade.newSimpleConfig( "A004-sample-config" , "/fj-freemarker-sample-template/" ) );
	
	private static final String CHAIN_ID = "hello-world";	// it will map to /fj-freemarker-sample-template/hello-world.ftl
	
	public void generateDocument( OutputStream os, DocTypeHandler handler, String docTitle ) throws DocException {
		// wraps any exception as a DocException
		DocException.apply( () -> {
			DocProcessContext context = DocProcessContext.newContext( "docTitle", docTitle );
			SIMPLE_CONFIG.fullProcess( CHAIN_ID, context, handler, DocOutput.newOutput(os) );
		} );
	}
	
}
