package io.bootify.my_app.repos;

import io.bootify.my_app.domain.RentPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentPersonRepository extends JpaRepository<RentPerson, Integer> {
}
