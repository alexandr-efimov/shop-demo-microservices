package com.example.client;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

/**
 * Created by alexandr.efimov on 3/9/2017.
 */

@UtilityClass
@Slf4j
public class ClientUtils {
    /**
     * Get link to product microservice instance from {@link LoadBalancerClient}.
     *
     * @param loadBalancer      is {@link LoadBalancerClient} instance
     * @param serviceInstanceId is the service id to look up the LoadBalancer for service instance
     * @return link to service or {@code null} if no instance in LoadBalancer
     */
    public static String getLinkToCLientInstanceFromLoadBalancer(LoadBalancerClient loadBalancer, String serviceInstanceId) {
        ServiceInstance serviceInstance = loadBalancer.choose(serviceInstanceId);
        log.info("Service instance name: " + serviceInstanceId + ",get from load balancer instance: " + serviceInstance);
        if (serviceInstance == null) {
            return null;
        }
        return "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort();
    }
}
