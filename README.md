### Remote debug

Run service in minikube with debug-graphql-k8s-dp.yml

(Debug options in lines 23-28)

Connect to service with command: minikube service debug-graphql-k8s-svc

Map pod port with command: kubectl port-forward podName 5005

Add Remote JVM Debug in Intellij IDEA config, host: localhost, port: 5005

Run debug


### GraphQL

Run app, test queries at: http://localhost:8080/graphiql?path=/graphql

Queries example: queries_example.graphql