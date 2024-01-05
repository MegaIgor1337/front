package forwarding.agent.persistense.repository;

import forwarding.agent.persistense.entity.ContactsOfCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsOfCompanyRepository extends JpaRepository<ContactsOfCompany, Long> {
}
