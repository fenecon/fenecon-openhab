package org.openhab.ui.femsui.internal;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;
import org.eclipse.smarthome.io.rest.RESTResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// http://localhost:8080/rest/fems
@Path("fems")
public class FemsUIResource implements RESTResource {
    private static final java.nio.file.Path rulePath = Paths.get("/opt/fems/openhab/conf/rules", "fems.rules");
    private final static Charset ENCODING = StandardCharsets.UTF_8;

    private final Logger logger = LoggerFactory.getLogger(FemsUIResource.class);

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("rule")
    public Response getRuleFile() {
        try {
            List<String> lines = Files.readAllLines(rulePath, ENCODING);
            String text = StringUtils.join(lines.toArray(), '\n');
            return Response.ok(text).build();
        } catch (Exception e) {
            logger.error("Error loading rule: " + rulePath);
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Fehler: " + e.getClass().getSimpleName() + " " + e.getMessage()).build();
        }
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("rule")
    public Response newTodo(@FormParam("text") String text) {
        try {
            Files.write(rulePath, text.getBytes());
            logger.info("Saved rule: " + rulePath);
            return Response.ok().build();
        } catch (IOException e) {
            logger.error("Error saving rule: " + rulePath);
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Fehler: " + e.getClass().getSimpleName() + " " + e.getMessage()).build();
        }
    }
}
