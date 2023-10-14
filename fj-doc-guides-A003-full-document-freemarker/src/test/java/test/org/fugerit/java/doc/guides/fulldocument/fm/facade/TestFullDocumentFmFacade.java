package test.org.fugerit.java.doc.guides.fulldocument.fm.facade;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.fugerit.java.doc.base.config.DocException;
import org.fugerit.java.doc.base.config.DocTypeHandler;
import org.fugerit.java.doc.base.config.DocTypeHandlerXMLUTF8;
import org.fugerit.java.doc.freemarker.html.FreeMarkerHtmlTypeHandlerEscapeUTF8;
import org.fugerit.java.doc.guides.fulldocument.fm.facade.FullDocumentFmFacade;
import org.fugerit.java.doc.guides.fulldocument.fm.facade.UserData;
import org.fugerit.java.doc.mod.fop.FreeMarkerFopTypeHandler;
import org.fugerit.java.doc.mod.fop.PdfFopTypeHandler;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestFullDocumentFmFacade {
	
	// three formats will be rendered in this example
	private static final DocTypeHandler[] HANDLERS =  { 
			PdfFopTypeHandler.HANDLER, 						// PDF through Apache FOP
			FreeMarkerFopTypeHandler.HANDLER_UTF8,			// XLS-FO format
			FreeMarkerHtmlTypeHandlerEscapeUTF8.HANDLER,	// HTML through Apache FreeMarker
			DocTypeHandlerXMLUTF8.HANDLER };				// XML as source fugerit doc format XML
	
	@Test
	public void testFullDocument() throws IOException, DocException {
		// custom data to be added to the template via Apache FreeMarker
		List<UserData> listUsers = Arrays.asList( 
				new UserData( "Marie" , "Curie", ">> Checmist"),
				new UserData( "Alan" , "Turing", "<IT/> Specialist")
			);
		for ( DocTypeHandler handler : HANDLERS ) {
			File outputFile = new File(  "target", "full-document."+handler.getType() ); // output file
			log.info( "outputFile : {}", outputFile );
			try ( OutputStream os = new FileOutputStream( outputFile ) ) {
				FullDocumentFmFacade facade = new FullDocumentFmFacade(); // the facade to generate the document
				facade.generateFullDocument(os, handler, listUsers); // actual document generation
				log.info( "Generated full documenti pdf file in path : {}", outputFile.getCanonicalPath() );
				Assert.assertTrue( outputFile.exists() );
			}	
		}
	}
	
}
