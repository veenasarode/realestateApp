package io.bootify.my_app.repos;

import io.bootify.my_app.domain.PropertyOwner;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProprtyWonerRepository extends JpaRepository<PropertyOwner, Integer> {
}
