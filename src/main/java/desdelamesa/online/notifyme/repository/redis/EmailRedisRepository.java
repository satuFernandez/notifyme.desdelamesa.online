package desdelamesa.online.notifyme.repository.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class EmailRedisRepository extends BaseRedisRepository {

	private static final String EMAIL_CACHE_KEY = "email";

	private static final String ALL_EMAILS_CACHE_KEY = "all:emails";

	private final RedisTemplate<String, String> redisTemplate;

	@Autowired
	public EmailRedisRepository(final RedisTemplate<String, String> redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
	}

	public void saveEmail(final String email) {
		this.redisTemplate.opsForValue().set(this.buildKey(email), "", 30, TimeUnit.DAYS);
	}

	public void saveAllEmails(final Collection<String> emails) {

		this.redisTemplate.opsForHash().putAll(ALL_EMAILS_CACHE_KEY,
				emails.stream().collect(Collectors.toMap(email -> email, email -> "")));
		this.redisTemplate.opsForHash().getOperations().expire(ALL_EMAILS_CACHE_KEY, 10, TimeUnit.SECONDS);
	}

	public boolean existsEmail(final String email) {

		final Boolean exists = this.redisTemplate.hasKey(this.buildKey(email));
		return Optional.ofNullable(exists).orElse(false);
	}

	public Collection<String> getAllEmails() {

		return this.redisTemplate.opsForHash().keys(ALL_EMAILS_CACHE_KEY).stream().map(Object::toString)
				.collect(Collectors.toUnmodifiableSet());
	}

	private String buildKey(final String email) {
		return String.join(SEPARATOR, EMAIL_CACHE_KEY, email);
	}
}
