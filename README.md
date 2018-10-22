# Domain Driven Design Implementation in Spring

Highly opinionated DDD implementation which has

- Shopify like E-commerce Domain
    * Sellers can register their products
    * Customers are able to add items into cart and order them
    * Store, Payment and Event (Discount, ..) domains are TODOs
- Terraformed Infrastructure
    * Google Kubernetes Engine
    * [Google Ingress (GLBC)](https://github.com/kubernetes/ingress-gce) 
    * [Cert Manager](https://github.com/jetstack/cert-manager) 
    * Google Cloud SQL (MySQL)
- Build Pipeline
    * Google Cloud Build
    * Google Container Registry
    * Spinnaker 
- Monitoring
    * Spring Boot Admin
    * Distributed Tracing using Jagger for HTTP, MySQL and Redis
    * Prometheus Operator for Kubernetes and Application Metrics 

