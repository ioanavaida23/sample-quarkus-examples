package org.sample.health;

import java.time.Instant;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class HealthResource implements HealthCheck{

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("Is it up?")
        .withData("Time now", Instant.now().toString())
        .up()
        .build();
    }

}