package com.invoiceservice.security.login.repository;


import com.invoiceservice.security.login.domain.ERole;
import com.invoiceservice.security.login.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

}
