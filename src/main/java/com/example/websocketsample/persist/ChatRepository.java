package com.example.websocketsample.persist;

import com.example.websocketsample.persist.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

}
