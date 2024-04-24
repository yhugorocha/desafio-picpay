package com.picpaysimplificado.services;

import com.picpaysimplificado.domains.transaction.Transaction;
import com.picpaysimplificado.domains.user.User;
import com.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository repository;
    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;

    public Transaction createTransaction(TransactionDTO transaction){
        User sender = userService.findUseById(transaction.senderId());
        User receiver =userService.findUseById(transaction.receiverId());

        userService.validateTransaction(sender, transaction.value());
        Boolean isAuthorized = this.authorizeTransaction(sender, transaction.value());

        if(!isAuthorized){
            throw new RuntimeException("Unauthorized transaction");
        }

        Transaction t = new Transaction();
        t.setAmount(transaction.value());
        t.setSender(sender);
        t.setReceiver(receiver);
        t.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));
        this.repository.save(t);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);
        return t;
    }

    public boolean authorizeTransaction(User sender, BigDecimal value){
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/cac3e57d-dbc0-4fd4-b525-e79ba27544e0", Map.class);

        if((authorizationResponse.getStatusCode() == HttpStatus.OK)){
            Boolean authorization = (Boolean) authorizationResponse.getBody().get("authorization");
            return authorization;
        }
        return false;
    }

    public List<Transaction> getAllTransactions(){
        return repository.findAll();
    }
}
