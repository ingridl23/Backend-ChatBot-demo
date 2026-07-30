package com.chat.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chat.demo.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.organization LEFT JOIN FETCH u.area LEFT JOIN FETCH u.roles r LEFT JOIN FETCH r.permissions WHERE u.userName = :username")
    Optional<User> findByUserName(@Param("username") String username);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.area LEFT JOIN FETCH u.roles WHERE u.organization.id = :organizationId")
    List<User> findByOrganizationId(@Param("organizationId") Long organizationId);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.organization LEFT JOIN FETCH u.roles WHERE u.area.id = :areaId")
    List<User> findByAreaId(@Param("areaId") Long areaId);
}
