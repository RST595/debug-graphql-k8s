FROM bellsoft/liberica-openjdk-alpine-musl
COPY build/libs/debug-graphql-k8s-1.0.0.jar .
CMD ["java","-jar","debug-graphql-k8s-1.0.0.jar"]

# docker build -t debug-graphql-k8s-app:latest .