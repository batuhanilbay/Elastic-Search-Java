package com.spring.elasticsearch;

import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;

public class CarTestContainer extends ElasticsearchContainer {
    private static final String ELASTIC_SEARCH_DOCKER = "elasticsearch:8.10.2";
    private static final String DISCOVERY_TYPE = "discovery.type";
    private static final String DISCOVERY_TYPE_SINGLE_NODE = "single-node";
    private static final String XPACK_SECURITY_ENABLED = "xpack.security.enabled";
    private static final String CLUSTER_NAME = "docker-cluster";
    private static final String ELASTIC_SEARCH = "elasticsearch";

    public CarTestContainer() {
        super(DockerImageName.parse(ELASTIC_SEARCH_DOCKER)
                .asCompatibleSubstituteFor("docker.elastic.co/elasticsearch/elasticsearch"));
        addFixedExposedPort(9200, 9200);
        addEnv(DISCOVERY_TYPE, DISCOVERY_TYPE_SINGLE_NODE);
        addEnv(XPACK_SECURITY_ENABLED,Boolean.FALSE.toString());
        addEnv(CLUSTER_NAME, ELASTIC_SEARCH);
    }
}
