# Domain Driven Design Implementation in Spring

Highly opinionated DDD implementation which has

- Shopify like E-commerce Domain
    * Sellers can register their products
    * Customers are able to add items into cart and order them
    * Domains
        * **User**
        * **Catalog**
        * **Cart**
        * **Order**
        * (TODO) **Inventory**: *store*
        * (TODO) **Promotion**: *coupon, Discount*
        * (TODO) **Marketing**: *seller managed campaigns with 3rd parties*
        * (TODO) **Analytics**: *dashboard for sellers* 
        * (TODO) **Advertisement**: *recommendation, curation* 
        * (TODO) **Membership**: *customer loyalty management* 
        * (TODO) **Support**: *customer management such as VOC*
        
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

