package com.mtpb.smarttodo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mtpb.smarttodo.entity.ActivityList;

public interface ActivityRepository extends JpaRepository<ActivityList, Long>{
		@Query("FROM ActivityList WHERE username = :user")
		List<ActivityList> findActivityByUser(String user);
		
}
