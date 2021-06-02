package org.bartheijenk.recepten.api;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;


@RunWith(Arquillian.class)
class ReceptApplicationIT {

    @Deployment
    public static Archive<?> createDeployment() {
        WebArchive warEmpty = ShrinkWrap.create(WebArchive.class, "AppIT.war");
        WebArchive warFilled = warEmpty
                .addPackages(true, ReceptApplication.class.getPackage()) // add all packages in my application
                // .addClass(App.class) // dont forget! // selectively add classes
                // .addClass(ContactsResource.class)
                // .addClass(Contact.class)
                .addAsWebInfResource("test-beans.xml", "beans.xml") // to activate CDI
                // .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsLibraries(pomDependency("org.apache.logging.log4j", "log4j-slf4j-impl"));

        System.out.println(warFilled.toString(true));
        return warFilled;
    }

    @Test
    public void whenReceptisPostedICanGetIt() {
    }

    private static File[] pomDependency(String groupId, String artifactId) {
        return Maven.resolver()
                .loadPomFromFile("pom.xml")
                .resolve(groupId + ":" + artifactId)
                .withTransitivity()
                .asFile();
    }

}