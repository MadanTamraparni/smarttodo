package com.mtpb.smarttodo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mtpb.smarttodo.entity.RegIDForUser;

public interface registrationRepository extends JpaRepository<RegIDForUser, String> {

}
