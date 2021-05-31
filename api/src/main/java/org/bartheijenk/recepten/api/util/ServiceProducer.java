package org.bartheijenk.recepten.api.util;

import org.bartheijenk.persistence.service.ReceptService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class ServiceProducer {

    @Produces
    public ReceptService createReceptService() {
        return new ReceptService();
    }
}
