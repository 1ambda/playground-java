<template>
  <div class="home">
    <img src="../assets/main.png"
         width="30%" height="30%"
         style="margin-top: 20px;">
    <h1>Domain Driven Design in Spring</h1>
    <p>
      To get or customize this project,<br>
      check out the
      <a href="https://github.com/1ambda/domain-driven-design-spring" target="_blank">repository</a>.
    </p>
    <h3 style="margin-top: 30px;">Infrastructure Layer</h3>
    <ul>
      <li>
        <el-tooltip placement="top">
          <div slot="content">Cloud Platform</div>
          <a href="https://cloud.google.com/" target="_blank">GCP</a>
        </el-tooltip>
      </li>
      <li>
        <el-tooltip placement="top">
          <div slot="content">Container Cluster</div>
          <a href="https://kubernetes.io/" target="_blank">Kubernetes</a>
        </el-tooltip>
      </li>
      <li>
        <el-tooltip placement="top">
          <div slot="content">Service Mesh</div>
          <a href="https://istio.io/" target="_blank">Istio</a>
        </el-tooltip>
      </li>
      <li>
        <el-tooltip placement="top">
          <div slot="content">Continuous Delivery</div>
          <a href="https://www.spinnaker.io/" target="_blank">Spinnaker</a>
        </el-tooltip>
      <li>
        <el-tooltip placement="top">
          <div slot="content">Infrastructure as Code</div>
          <a href="http://terraform.io/" target="_blank">Terraform</a>
        </el-tooltip>
      </li>
    </ul>
    <h3 style="margin-top: 20px;">Monitoring Layer</h3>
    <ul>
      <li>
        <el-tooltip placement="top">
          <div slot="content">Application Persistent Metrics</div>
          <a href="https://prometheus.io/" target="_blank">Prometheus</a>
        </el-tooltip>
      </li>
      <li>
        <el-tooltip placement="top">
          <div slot="content">Distributed Tracing</div>
          <a href="https://github.com/jaegertracing/jaeger" target="_blank">Jagger</a>
        </el-tooltip>
      </li>
      <li>
        <el-tooltip placement="top">
          <div slot="content">Application Snapshot Metrics</div>
          <a href="https://github.com/codecentric/spring-boot-admin" target="_blank">Spring Boot Admin</a>
        </el-tooltip>
      </li>
    </ul>
    <h3 style="margin-top: 20px;">Storage Layer</h3>
    <ul>
      <li>
        <el-tooltip placement="top">
          <div slot="content">RDB</div>
          <a href="https://github.com/mysql/mysql-server" target="_blank">MySQL</a>
        </el-tooltip>
      </li>
      <li>
        <el-tooltip placement="top">
          <div slot="content">Cache</div>
          <a href="https://github.com/antirez/redis" target="_blank">Redis</a>
        </el-tooltip>
      </li>
      <li>
        <el-tooltip placement="top">
          <div slot="content">Queue</div>
          <a href="https://github.com/apache/kafka" target="_blank">Kafka</a>
        </el-tooltip>
      </li>
    </ul>
    <h3 style="margin-top: 20px;">Application Layer</h3>
    <ul>
      <li>
        <a href="https://github.com/spring-projects/spring-boot" target="_blank">Spring Boot 2.0</a>
      </li>
      <li>
        <a href="https://swagger.io/tools/swagger-codegen/" target="_blank">Swagger Codegen</a>
      </li>
      <li>
        <a href="" target="_blank">Websocket</a>
      </li>
      <li>
        <a href="https://grpc.io/" target="_blank">gRPC</a>
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator'

  @Component({
    components: {},
  })
  export default class Home extends Vue {
    public $refs: any
    public $notify: any
    public $router: any
    public $store: any
  }
</script>

<style scoped lang="scss">
  .home {
    text-align: center;
  }

  h3 {
    margin: 40px 0 0;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    display: inline-block;
    margin: 0 10px;
  }

  a {
    color: #42b983;
  }
</style>
