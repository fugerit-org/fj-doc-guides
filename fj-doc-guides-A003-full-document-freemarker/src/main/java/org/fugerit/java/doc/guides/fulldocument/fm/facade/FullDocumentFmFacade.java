package org.fugerit.java.doc.guides.fulldocument.fm.facade;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.util.MapEntry;
import org.fugerit.java.doc.base.config.DocException;
import org.fugerit.java.doc.base.config.DocOutput;
import org.fugerit.java.doc.base.config.DocTypeHandler;
import org.fugerit.java.doc.base.process.DocProcessContext;
import org.fugerit.java.doc.freemarker.process.FreemarkerDocProcessConfig;
import org.fugerit.java.doc.freemarker.process.FreemarkerDocProcessConfigFacade;

public class FullDocumentFmFacade {

	private static final FreemarkerDocProcessConfig SIMPLE_CONFIG = 
			// SafeFunction.get() will execute the configuration code and re-throw a ConfigRuntimeException if any exception is thrown
			SafeFunction.get( () -> FreemarkerDocProcessConfigFacade.newSimpleConfig( "A003-sample-config" , "/fj-freemarker-sample-template/" ) );
	
	private static final List<MapEntry<String, String>> LIST_TESTS = Arrays.asList(
			new MapEntry<>( "ul", "default unordered list"),
			new MapEntry<>( "uld", "dotted unordered list"),
			new MapEntry<>( "ulm", "square unordered list"),
			new MapEntry<>( "ol", "default ordered list"),
			new MapEntry<>( "oll", "letter ordered list"),
			new MapEntry<>( "oln", "numbered ordered list")
		);
	
	public void generateFullDocument( OutputStream os, DocTypeHandler handler, List<PeopleData> listPeople ) throws DocException {
		// wraps any exception as a DocException
		DocException.apply( () -> {
			DocProcessContext context = DocProcessContext.newContext( "listPeople", listPeople )
					.withAtt( "docTitle" , "Full document sample demo" )
					.withAtt(  "listTests", LIST_TESTS);
			SIMPLE_CONFIG.fullProcess( "full-document", context, handler, DocOutput.newOutput(os) );
		} );
	}
	
}
