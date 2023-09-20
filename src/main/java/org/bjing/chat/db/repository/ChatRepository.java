package org.bjing.chat.db.repository;

import org.bjing.chat.db.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, String> {
}
