package com.hugodev.user_login.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hugodev.user_login.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

}
