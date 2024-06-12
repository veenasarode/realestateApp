package io.bootify.my_app.repos;

import io.bootify.my_app.domain.PropertyOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProprtyWonerRepository extends JpaRepository<PropertyOwner, Integer> {
}
