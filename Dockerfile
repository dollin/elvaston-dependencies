# Stage 1: explode the Spring Boot jar so the runtime classpath is stable (required for CDS)
FROM eclipse-temurin:26-jre-alpine AS builder
WORKDIR /builder
COPY api/build/libs/api.jar api.jar
RUN java -Djarmode=tools -jar api.jar extract --destination extracted

# Stage 2: runtime image, primed with a class-data-sharing archive
FROM eclipse-temurin:26-jre-alpine
WORKDIR /app
COPY --from=builder /builder/extracted/ ./
# Training run: boot the context, dump the CDS archive, then exit before serving traffic.
# Lazy initialization (application.properties) keeps this from needing a database at build time.
RUN java -XX:ArchiveClassesAtExit=application.jsa \
  -Dspring.context.exit=onRefresh \
  -jar api.jar
EXPOSE 8080
ENTRYPOINT ["java", \
  "-XX:SharedArchiveFile=application.jsa", \
  "-Xmx512m", \
  "-XX:MaxMetaspaceSize=150m", \
  "-XX:ReservedCodeCacheSize=64m", \
  "-XX:+UseSerialGC", \
  "-XX:TieredStopAtLevel=1", \
  "-jar", "api.jar"]
