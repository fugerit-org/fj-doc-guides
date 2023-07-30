package org.fugerit.java.doc.guides.helloworld.pdf;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.doc.base.config.DocInput;
import org.fugerit.java.doc.base.config.DocOutput;
import org.fugerit.java.doc.base.config.DocTypeHandler;
import org.fugerit.java.doc.mod.fop.PdfFopTypeHandler;

public class HelloWorldPdf {

	public void generateHelloWorld( OutputStream os ) throws Exception {
		// reader for the xml source docuement
		try ( Reader xmlReader = new InputStreamReader( ClassHelper.loadFromDefaultClassLoader( "doc-xml/hello-world.xml" ) ) ) {
			DocTypeHandler handler = PdfFopTypeHandler.HANDLER;						// handler for pdf output
			DocInput docInput = DocInput.newInput( handler.getType(), xmlReader );	// handler input
			DocOutput docOutput = DocOutput.newOutput( os );						// handler output
			handler.handle(docInput, docOutput);									// generation
		} 
	}
	
}
