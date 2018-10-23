# Domain Driven Design Implementation in Spring

Highly opinionated DDD implementation which has

- Shopify like E-commerce Domain
    * Sellers can register their products
    * Customers are able to add items into cart and order them
    * Domains
        * **Catalog**: 
            * (TODO 1): search and basic curation
            * (TODO 2): review and rate
        * **Cart**
        * (WIP) **Order**
        * (WIP) **Payment**: *mocked payment system*
        * **User**:
            * (TODO 3): custom auth such as OAuth, AD
            * (TODO 3): email registration, password reset
        * (TODO 2) **Seller**: *seller specific actions*
        * (TODO 2) **Inventory**: *mocked store system*
        * (TODO 3) **Membership**: *customer loyalty management* 
        * (TODO 3) **Promotion**: *coupon, discount*
        * (TODO 4) **Advertisement**: *recommendation, smart curation* 
        * (TODO 5) **Analytics**: *dashboard for sellers* 
        * (TODO 6) **Support**: *customer management such as VOC*
        * (TODO 7) **Marketing**: *seller managed campaigns with 3rd parties*
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

