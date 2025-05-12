package desdelamesa.online.notifyme.repository.postgres;

import desdelamesa.online.notifyme.repository.postgres.entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailJpaPostgresRepository extends JpaRepository<EmailEntity, String> {
}
