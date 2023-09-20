package org.bjing.chat.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "media")
public class Media {
    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String link;

    @ManyToOne
    @JoinColumn(name = "message_id" )
    private Message message;

    @ManyToOne
    @JoinColumn(name = "chat_id" )
    private Chat chat;

    @CreationTimestamp
    @Column(name = "created",nullable = false,updatable = false)
    private Date created;

    @UpdateTimestamp
    @Column(name = "updated")
    private Date updated;
}
