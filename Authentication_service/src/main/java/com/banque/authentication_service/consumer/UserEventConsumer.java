package com.banque.authentication_service.consumer;

import com.banque.authentication_service.entity.AuthUser;
import com.banque.events.UserEvent;
import com.banque.authentication_service.repository.AuthUserRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserEventConsumer {

    private final AuthUserRepository authUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserEventConsumer(AuthUserRepository authUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.authUserRepository = authUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
                    authUser.setPassword(bCryptPasswordEncoder.encode(event.getPassword()));
                    authUser.setRole(event.getRole());
                    authUserRepository.save(authUser);
                }
                break;

            case "UPDATED":
                authUserOptional.ifPresent(user ->{
                    if(!user.getUsername().equals(event.getUsername()) || !user.getPassword().equals(event.getPassword())){
                        user.setUsername(event.getUsername());
                        user.setPassword(bCryptPasswordEncoder.encode(event.getPassword()));
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
