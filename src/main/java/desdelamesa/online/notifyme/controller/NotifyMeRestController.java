package desdelamesa.online.notifyme.controller;

import desdelamesa.online.notifyme.domain.Email;
import desdelamesa.online.notifyme.exception.InvalidApiKeyHeaderException;
import desdelamesa.online.notifyme.usecase.AddEmailToNotifyListUseCase;
import desdelamesa.online.notifyme.usecase.GetAllEmailsToNotifyUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class NotifyMeRestController {

	private final AddEmailToNotifyListUseCase addEmailToNotifyListUseCase;

	private final GetAllEmailsToNotifyUseCase getAllEmailsToNotifyUseCase;

	private final String apiKey;

	@Autowired
	public NotifyMeRestController(final AddEmailToNotifyListUseCase addEmailToNotifyListUseCase,
			final GetAllEmailsToNotifyUseCase getAllEmailsToNotifyUseCase,
			@Value("${api.key.value:0000}") final String apiKey) {

		this.addEmailToNotifyListUseCase = addEmailToNotifyListUseCase;
		this.getAllEmailsToNotifyUseCase = getAllEmailsToNotifyUseCase;
		this.apiKey = apiKey;
	}

	@PostMapping("/api/v1/notifyme/email")
	public void sendEmail(@RequestBody final Email email) {

		this.addEmailToNotifyListUseCase.addEmailToNotifyList(email);
	}

	@GetMapping("/api/v1/getall/email")
	public ResponseEntity<Collection<String>> fetchAllEmails(@RequestHeader(name = "X-API-KEY") final String apiKey) {

		if (!this.apiKey.equals(apiKey)) {
			throw new InvalidApiKeyHeaderException();
		}

		final var emails = this.getAllEmailsToNotifyUseCase.getAllEmailsToNotify();
		return ResponseEntity.ok(emails);
	}
}
