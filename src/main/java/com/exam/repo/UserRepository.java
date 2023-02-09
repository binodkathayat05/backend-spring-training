package com.exam.repo;

import com.exam.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User,Long> {
 public User findByUsername(String username);
 public User findByid(Long username);
  @Modifying
  @Query("update User  u set u.phone= :userPhone where u.username = :userName")
  int updateUserUpdatePhones(@Param("userName") String userName,@Param("userPhone") String userPhone);
}
