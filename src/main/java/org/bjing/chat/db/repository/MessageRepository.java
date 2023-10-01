package org.bjing.chat.db.repository;

import org.bjing.chat.db.entity.Chat;
import org.bjing.chat.db.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM message m WHERE m.chat.id = :chatId")
    Page<Message> findAllByChatId(@Param("chatId") String chatId, Pageable pageable);
}
