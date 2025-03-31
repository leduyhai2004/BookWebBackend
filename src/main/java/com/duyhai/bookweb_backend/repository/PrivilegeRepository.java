package com.duyhai.bookweb_backend.repository;


import com.duyhai.bookweb_backend.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "privileges")
public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {
    public Privilege findByName(String name);
}
