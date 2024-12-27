package com.banque.authentication_service.consumer;

import com.banque.authentication_service.entity.AuthUser;
import com.banque.events.UserEvent;
import com.banque.authentication_service.repository.AuthUserRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserEventConsumer {

    private final AuthUserRepository authUserRepository;

    public UserEventConsumer(AuthUserRepository authUserRepository){
        this.authUserRepository = authUserRepository;
    }


    @KafkaListener(topics = "user-events", groupId = "auth-service")
    public void consume(UserEvent event) {
        Optional<AuthUser> authUserOptional = authUserRepository.findById(event.getId());
        switch (event.getEventType()) {
            case "CREATED":
                if(authUserOptional.isEmpty()){
                    AuthUser authUser = new AuthUser();
                    authUser.setUser_id(event.getId());
                    authUser.setUsername(event.getUsername());
                    authUser.setPassword(event.getPassword());
                    authUser.setRole(event.getRole());
                    authUserRepository.save(authUser);
                }
                break;

            case "UPDATED":
                authUserOptional.ifPresent(user ->{
                    if(!user.getUsername().equals(event.getUsername()) || !user.getPassword().equals(event.getPassword())){
                        user.setUsername(event.getUsername());
                        user.setPassword(event.getPassword());
                        authUserRepository.save(user);
                    }
                });
                break;

            case "DELETED":
                authUserOptional.ifPresent(user -> {
                    authUserRepository.delete(user);
                });
                break;
        }
        System.out.println("Processed user event: " + event);
    }
}
