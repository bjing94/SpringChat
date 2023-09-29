package org.bjing.chat.db.repository;

import org.bjing.chat.db.entity.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ChatRepository extends JpaRepository<Chat, String> {

    Optional<Chat> getTopByOrderByCreatedAsc();

    @Query("SELECT c FROM chat c WHERE size(c.messages) > :limit ORDER BY size(c.messages) DESC")
    Page<Chat> findLimitByMessages(@Param("limit") Integer limit, Pageable page);

}
