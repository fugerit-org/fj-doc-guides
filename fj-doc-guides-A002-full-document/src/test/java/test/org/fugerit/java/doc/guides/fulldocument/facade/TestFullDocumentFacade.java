package test.org.fugerit.java.doc.guides.fulldocument.facade;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.fugerit.java.doc.base.config.DocException;
import org.fugerit.java.doc.base.config.DocTypeHandler;
import org.fugerit.java.doc.freemarker.html.FreeMarkerHtmlTypeHandlerUTF8;
import org.fugerit.java.doc.guides.fulldocument.facade.FullDocumentFacade;
import org.fugerit.java.doc.mod.fop.PdfFopTypeHandler;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFullDocumentFacade {

	private static final Logger log = LoggerFactory.getLogger( TestFullDocumentFacade.class );
	
	private static final DocTypeHandler[] HANDLERS =  { PdfFopTypeHandler.HANDLER, FreeMarkerHtmlTypeHandlerUTF8.HANDLER };
	
	@Test
	public void testFullDocument() throws IOException, DocException {
		for ( DocTypeHandler handler : HANDLERS ) {
			File outputFile = new File(  "target", "full-document."+handler.getType() ); // output file
			log.info( "outputFile : {}", outputFile );
			try ( OutputStream os = new FileOutputStream( outputFile ) ) {
				FullDocumentFacade facade = new FullDocumentFacade(); // the facade to generate the document
				facade.generateFullDocument(os, handler); // actual document generation
				log.info( "Generated full documenti pdf file in path : {}", outputFile.getCanonicalPath() );
				Assert.assertTrue( outputFile.exists() );
			}	
		}
	}
	
}
