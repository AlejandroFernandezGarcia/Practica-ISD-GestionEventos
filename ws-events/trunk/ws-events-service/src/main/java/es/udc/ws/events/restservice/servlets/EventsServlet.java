package es.udc.ws.events.restservice.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import es.udc.ws.util.servlet.ServletUtils;

@SuppressWarnings("serial")
public class EventsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long eventId = new Long(0L);

        String eventURL = req.getRequestURL().append("/").append(eventId)
                .toString();
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Location", eventURL);

        ServletUtils.writeServiceResponse(resp, HttpStatus.SC_CREATED, null,
                headers);

        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        outputter.output(getEventDocument(), resp.getOutputStream());

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ServletUtils.writeServiceResponse(resp, HttpStatus.SC_OK,
                "application/xml", null, null);

        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        outputter.output(getEventDocument(), resp.getOutputStream());
    }

    private Document getEventDocument() {

        Element eventElement = new Element("event");

        Element identifierElement = new Element("eventId");
        identifierElement.setText(new Long(0L).toString());
        eventElement.addContent(identifierElement);

        Element nameElement = new Element("name");
        nameElement.setText("event name");
        eventElement.addContent(nameElement);

        Document document = new Document(eventElement);
        return document;

    }
}
