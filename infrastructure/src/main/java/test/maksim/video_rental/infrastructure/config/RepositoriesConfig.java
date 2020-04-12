package test.maksim.video_rental.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "test.maksim.video_rental.infrastructure")
@EntityScan("test.maksim.video_rental.infrastructure")
public class RepositoriesConfig {
}
