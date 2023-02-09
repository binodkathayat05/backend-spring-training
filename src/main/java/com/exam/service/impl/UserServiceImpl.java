package com.exam.service.impl;

import com.exam.controller.UserFoundException;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    //creating user
    @Override
    public User createUser(User user, Set<UserRole> userRoles) throws Exception {
       User local= this.userRepository.findByUsername(user.getUsername());
       if (local!=null)
       {
           System.out.println("User is already there !!");
           throw new UserFoundException();
       }else {
           //User create
           for(UserRole ur:userRoles){
               roleRepository.save(ur.getRole());
           }
           user.getUserRoles().addAll(userRoles);
           local=this.userRepository.save(user);
       }
        return local;
    }
// getting user by username
    @Override
    public User getUser(String username) {
        return  this.userRepository.findByUsername(username);
    }
//delete user
    @Override
    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }
//update user
    @Override
    public User updateUser(User user) throws Exception {

        User localup= this.userRepository.findByid(user.getId());
        if (localup==null)
        {
            System.out.println("User is not present there !!");
            throw new Exception("User not present");
        }else {
            //update create
//            localup.setLastName(user.getLastName());
//            localup.setEmail(user.getEmail());
//            localup.setFirstName(user.getFirstName());
//            localup.setPhone(user.getPhone());
//            this.userRepository.save(localup);
            this.userRepository.save(user);
        }
        return localup;
    }

    @Override
    public User updateUserUpdatePhone(String userName,String userPhone) throws Exception {
        User localPhoneUpdate= this.userRepository.findByUsername(userName);
        if (localPhoneUpdate==null)
        {
            System.out.println("User is not present there !!");
            throw new Exception("User not present");
        }else {
            System.out.println(userPhone+" "+userName);
            this.userRepository.updateUserUpdatePhones(userName,userPhone);
        }
        return localPhoneUpdate;
    }

}
