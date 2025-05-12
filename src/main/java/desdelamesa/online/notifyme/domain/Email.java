package desdelamesa.online.notifyme.domain;

import java.util.Objects;

public record Email(String email) {

	public Email(String email) {
		this.email = Objects.requireNonNull(email);
	}
}
