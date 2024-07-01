package com.hexagonal.client.infrastructure.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexagonal.client.infrastructure.entities.PersonEntity;

@Repository
public interface JpaPersonRepository extends JpaRepository<PersonEntity, UUID> {

}
