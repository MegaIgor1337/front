package forwarding.agent.service.impl;

import forwarding.agent.api.exceptions.ContactsDoNotExistException;
import forwarding.agent.persistense.entity.ContactsOfCompany;
import forwarding.agent.persistense.repository.ContactsOfCompanyRepository;
import forwarding.agent.service.ContactsOfCompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactsOfCompanyServiceImpl implements ContactsOfCompanyService {
    private final ContactsOfCompanyRepository contactsOfCompanyRepository;
    @Override
    public List<String> getListOfEmailForContact() {
        List<String> emails = contactsOfCompanyRepository.findAll().stream()
                .map(ContactsOfCompany::getEmail).toList();
        log.info("Getting list of emails in service - {}", emails);
        if (emails.isEmpty()) {
            throw new ContactsDoNotExistException("Emails for contact do not exist");
        } else {
            return emails;
        }
    }
}
