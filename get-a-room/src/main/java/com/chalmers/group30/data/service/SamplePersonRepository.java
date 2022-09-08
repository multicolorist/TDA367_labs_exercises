package com.chalmers.group30.data.service;

import com.chalmers.group30.data.entity.SamplePerson;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SamplePersonRepository extends JpaRepository<SamplePerson, UUID> {

}