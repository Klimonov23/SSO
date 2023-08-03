package com.sdi.klimonov.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sdi.klimonov.dao.entity.SystemOauth2Client;

@Repository
public interface SystemOauth2ClientRepository extends JpaRepository<SystemOauth2Client, Long> {

    SystemOauth2Client getByClientId(String clientId);
}
