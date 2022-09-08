package com.chalmers.group30.data.service;

import com.chalmers.group30.data.entity.SampleAddress;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleAddressRepository extends JpaRepository<SampleAddress, UUID> {

}