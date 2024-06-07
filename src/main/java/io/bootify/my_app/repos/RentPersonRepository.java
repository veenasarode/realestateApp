package io.bootify.my_app.repos;

import io.bootify.my_app.domain.RentPerson;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RentPersonRepository extends JpaRepository<RentPerson, Integer> {
}
