package desdelamesa.online.notifyme.repository.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class EmailRedisRepository extends BaseRedisRepository {

	private static final String EMAIL_CACHE_KEY = "email";

	private final RedisTemplate<String, String> redisTemplate;

	@Autowired
	public EmailRedisRepository(final RedisTemplate<String, String> redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
	}

	public void saveEmail(final String email) {
		this.redisTemplate.opsForValue().set(this.buildKey(email), "1", 30, TimeUnit.DAYS);
	}

	public boolean existsEmail(final String email) {

		final Boolean exists = this.redisTemplate.hasKey(this.buildKey(email));
		return Optional.ofNullable(exists).map(Boolean::valueOf).orElse(false);
	}

	private String buildKey(final String email) {
		return String.join(SEPARATOR, EMAIL_CACHE_KEY, email);
	}
}
