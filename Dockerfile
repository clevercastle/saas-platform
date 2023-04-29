FROM quay.io/quarkus/quarkus-micro-image:2.0
WORKDIR /work/
COPY build/saas/portal/portal-1.0-SNAPSHOT-runner /work/application
RUN chmod 775 /work
ENV QUARKUS_PROFILE=dev
EXPOSE 8080
CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]