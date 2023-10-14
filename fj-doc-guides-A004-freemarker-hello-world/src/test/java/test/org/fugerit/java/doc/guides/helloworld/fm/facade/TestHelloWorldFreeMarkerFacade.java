package test.org.fugerit.java.doc.guides.helloworld.fm.facade;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.fugerit.java.doc.base.config.DocException;
import org.fugerit.java.doc.base.config.DocTypeHandler;
import org.fugerit.java.doc.guides.helloworld.fm.facade.HelloWorldFreeMarkerFacade;
import org.fugerit.java.doc.mod.fop.PdfFopTypeHandler;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestHelloWorldFreeMarkerFacade {
	
	@Test
	public void testGenerateDocument() throws IOException, DocException {
		DocTypeHandler handler = PdfFopTypeHandler.HANDLER;	// the handler for desired output, in this case PDF
		String docTitle = "Hello World with Apache FreeMarker Template!";
		File outputFile = new File(  "target", "full-document."+handler.getType() ); // output file
		log.info( "outputFile : {}", outputFile );
		try ( OutputStream os = new FileOutputStream( outputFile ) ) {
			HelloWorldFreeMarkerFacade facade = new HelloWorldFreeMarkerFacade(); // the facade to generate the document
			facade.generateDocument(os, handler, docTitle); // actual document generation
			log.info( "Generated full documenti pdf file in path : {}", outputFile.getCanonicalPath() );
			Assert.assertTrue( outputFile.exists() );
		}
	}
	
}
