package desdelamesa.online.notifyme.repository.postgres.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Table(name = "EMAIL")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class EmailEntity {

	@Id
	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Override
	public final boolean equals(final Object obj) {

		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		final EmailEntity that = (EmailEntity) obj;

		return Objects.equals(this.email, that.email);
	}

	@Override
	public final int hashCode() {

		return Objects.hash(this.email);
	}
}
