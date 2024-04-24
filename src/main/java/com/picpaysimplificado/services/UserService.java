package com.picpaysimplificado.services;

import com.picpaysimplificado.domains.user.User;
import com.picpaysimplificado.domains.user.UserType;
import com.picpaysimplificado.dtos.UserDTO;
import com.picpaysimplificado.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount){
        if(sender.getUserType() == UserType.MERCHANT){
            throw new RuntimeException("Logist type user is not authorized to make transactions");
        }
        if (sender.getBalance().compareTo(amount) < 0){
            throw new RuntimeException("Insufficient funds");
        }
    }

    public List<User> getAllUsers(){
        return repository.findAll();
    }

    public User findUseById(Long id){
        return repository.findUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User createUser(UserDTO userdto){
        User user = new User(userdto);
        return this.repository.save(user);
    }

    public void saveUser(User user){
        this.repository.save(user);
    }
}
