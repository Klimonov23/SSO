package com.sdi.klimonov.dao.repository;

import com.sdi.klimonov.dao.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query("select r from RoleEntity r where r.code = 'USER_SSO'")
    RoleEntity getDefaultRole();
}
