package desdelamesa.online.notifyme.usecase;

import desdelamesa.online.notifyme.domain.Email;
import desdelamesa.online.notifyme.repository.postgres.EmailJpaPostgresRepository;
import desdelamesa.online.notifyme.repository.postgres.entity.EmailEntity;
import desdelamesa.online.notifyme.repository.redis.EmailRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddEmailToNotifyListUseCase {

	private final EmailRedisRepository emailRedisRepository;

	private final EmailJpaPostgresRepository emailJpaPostgresRepository;

	@Autowired
	public AddEmailToNotifyListUseCase(final EmailRedisRepository emailRedisRepository,
			final EmailJpaPostgresRepository emailJpaPostgresRepository) {

		this.emailRedisRepository = emailRedisRepository;
		this.emailJpaPostgresRepository = emailJpaPostgresRepository;
	}

	public void addEmailToNotifyList(final Email email) {

		if (!this.emailRedisRepository.existsEmail(email.email())) {
			this.emailRedisRepository.saveEmail(email.email());

			final var emailEntity = new EmailEntity();
			emailEntity.setEmail(email.email());
			this.emailJpaPostgresRepository.save(emailEntity);
		}
	}
}
