package desdelamesa.online.notifyme.usecase;

import desdelamesa.online.notifyme.repository.postgres.EmailJpaPostgresRepository;
import desdelamesa.online.notifyme.repository.postgres.entity.EmailEntity;
import desdelamesa.online.notifyme.repository.redis.EmailRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class GetAllEmailsToNotifyUseCase {

	private final EmailRedisRepository emailRedisRepository;

	private final EmailJpaPostgresRepository emailJpaPostgresRepository;

	@Autowired
	public GetAllEmailsToNotifyUseCase(final EmailRedisRepository emailRedisRepository,
			final EmailJpaPostgresRepository emailJpaPostgresRepository) {

		this.emailRedisRepository = emailRedisRepository;
		this.emailJpaPostgresRepository = emailJpaPostgresRepository;
	}

	public Collection<String> getAllEmailsToNotify() {

		final var cachedEmails = this.emailRedisRepository.getAllEmails();

		if (!cachedEmails.isEmpty()) {
			return cachedEmails;
		}

		final var databaseEmails = this.emailJpaPostgresRepository.findAll().stream().map(EmailEntity::getEmail)
				.collect(Collectors.toUnmodifiableSet());

		this.emailRedisRepository.saveAllEmails(databaseEmails);

		return databaseEmails;
	}
}
