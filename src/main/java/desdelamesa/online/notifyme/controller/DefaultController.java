package desdelamesa.online.notifyme.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

	@GetMapping("/error")
	public ResponseEntity<Void> error() {
		return ResponseEntity.status(444).build();
	}
}
