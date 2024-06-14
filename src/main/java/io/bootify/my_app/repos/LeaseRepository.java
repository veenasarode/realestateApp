package io.bootify.my_app.repos;

import io.bootify.my_app.domain.Lease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaseRepository extends JpaRepository<Lease, Integer> {
}
