package erp.mds.socialmediaback.audit;

import erp.mds.socialmediaback.services.impl.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
@RequiredArgsConstructor
public class AuditAwareImpl implements AuditorAware<String> {

    private final UsersService usersService;

    @Override
    public Optional<String> getCurrentAuditor() {

        return Optional.of(usersService.getUser().isPresent()?usersService.getUser().get().getUserName():"Guest");
    }
}
