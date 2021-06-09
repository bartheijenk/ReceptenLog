package org.bartheijenk.recepten.api.util;

import javax.ws.rs.BadRequestException;

import static java.lang.String.format;

public final class Response {
    private Response() {
    }

    public static Runnable throwBadRequest(Long id) {
        return () -> {
            throw badRequest(id);
        };
    }

    public static BadRequestException badRequest(Long id) {
        return new BadRequestException(format("Record with id %s not found.", id));
    }
}
