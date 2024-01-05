package forwarding.agent.api.controllers;

import forwarding.agent.service.ContactsOfCompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Contact Controller", description = "API for working with public contact of company")
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/getEmailsForContact")
public class ContactsApiController {
    private final ContactsOfCompanyService contactsOfCompanyService;

    @Operation(summary = "Get the list of emails for contact")
    @GetMapping()
    public ResponseEntity<List<String>> get() {
        log.info("Getting the list of emails for contact");
        List<String> emails = contactsOfCompanyService.getListOfEmailForContact();
        return ResponseEntity.ok(emails);
    }
}
