FROM openliberty/raspberrypi

ARG VERSION=1.0
ARG REVISION=SNAPSHOT

COPY --chown=1001:0 api/src/main/liberty/config/ /config/
COPY --chown=1001:0 api/target/*.war /config/apps/
COPY --chown=1001:0 api/target/recipe-api/WEB-INF/lib/mysql-connector-java-8.0.22.jar /opt/ol/wlp/lib/

RUN configure.sh