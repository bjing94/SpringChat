package org.bjing.chat.db.repository;

import org.bjing.chat.db.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {
}
