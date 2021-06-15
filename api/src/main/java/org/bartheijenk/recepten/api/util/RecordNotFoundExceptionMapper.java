package org.bartheijenk.recepten.api.util;

import org.bartheijenk.persistence.util.RecordNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
public class RecordNotFoundExceptionMapper implements ExceptionMapper<RecordNotFoundException> {
    @Override
    public Response toResponse(RecordNotFoundException ex) {
        return Response.status(BAD_REQUEST)
                .entity("RecordNotFoundExceptionMapper: " + ex.getMessage())
                .build();
    }
}
