package com.picpaysimplificado.services;

import com.picpaysimplificado.domains.user.User;
import com.picpaysimplificado.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;


    public void sendNotification(User user, String message){
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);
        ResponseEntity<String>  notificationEntity = restTemplate.postForEntity("https://run.mocky.io/v3/cac3e57d-dbc0-4fd4-b525-e79ba27544e0",notificationRequest, String.class);

        if(!(notificationEntity.getStatusCode() == HttpStatus.OK)){
            throw new RuntimeException("Notification service is down");
        }
    }
}
