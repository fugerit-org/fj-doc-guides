package test.org.fugerit.java.doc.guides.helloworld.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.fugerit.java.doc.base.config.DocException;
import org.fugerit.java.doc.guides.helloworld.pdf.HelloWorldPdf;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHelloWorldPdf {

	private static final Logger log = LoggerFactory.getLogger( TestHelloWorldPdf.class );
	
	@Test
	public void testHelloWorldPdf() throws IOException, DocException {
		File outputFile = new File(  "target", "hello-world.pdf" ); // output file
		try ( OutputStream os = new FileOutputStream( outputFile ) ) {
			HelloWorldPdf helloWorldPdf = new HelloWorldPdf(); // the facade to generate the document
			helloWorldPdf.generateHelloWorld(os); // actual document generation
			log.info( "Generated hello world pdf file in path : {}", outputFile.getCanonicalPath() );
			Assert.assertTrue( outputFile.exists() );
		}
	}
	
}
