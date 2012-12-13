package es.udc.ws.events.xml;

import java.io.IOException;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import es.udc.ws.util.servlet.ResponseEntityWriter;

public class XmlEntityResponseWriter implements ResponseEntityWriter {

	private Document document;

	public XmlEntityResponseWriter(Document document) {
		this.document = document;
	}

	@Override
	public void write(OutputStream ouputStream) throws IOException {
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		outputter.output(document, ouputStream);
	}

	@Override
	public String getContentType() {
		return "application/xml";
	}

}
