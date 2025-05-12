package desdelamesa.online.notifyme.controller;

import desdelamesa.online.notifyme.domain.Email;
import desdelamesa.online.notifyme.usecase.AddEmailToNotifyListUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotifyMeRestController {

	private final AddEmailToNotifyListUseCase addEmailToNotifyListUseCase;

	@Autowired
	public NotifyMeRestController(AddEmailToNotifyListUseCase addEmailToNotifyListUseCase) {
		this.addEmailToNotifyListUseCase = addEmailToNotifyListUseCase;
	}

	@PostMapping("/api/v1/notifyme/email")
	public void sendEmail(@RequestBody final Email email) {

		this.addEmailToNotifyListUseCase.addEmailToNotifyList(email);
	}
}
