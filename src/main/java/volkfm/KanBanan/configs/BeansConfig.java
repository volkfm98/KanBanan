package volkfm.KanBanan.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import volkfm.KanBanan.models.authority.AuthorityRepository;
import volkfm.KanBanan.models.user.UserFactory;

@Configuration
public class BeansConfig {
    @Bean
    public UserFactory getUserFactory(AuthorityRepository authRepo) {
        return new UserFactory(authRepo);
    }
}
