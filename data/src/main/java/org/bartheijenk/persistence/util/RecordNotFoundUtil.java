package org.bartheijenk.persistence.util;

import static java.lang.String.format;

public final class RecordNotFoundUtil {
    private RecordNotFoundUtil() {
    }

    public static Runnable throwRecordNotFound(Long id) {
        return () -> {
            throw recordNotFound(id);
        };
    }

    public static RecordNotFoundException recordNotFound(Long id) {
        return new RecordNotFoundException(format("Record with id %s not found.", id));
    }
}
