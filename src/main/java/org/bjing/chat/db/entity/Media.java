package org.bjing.chat.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    @JsonIgnore
    private Message message;

    @ManyToOne
    @JoinColumn(name = "chat_id" )
    @JsonIgnore
    private Chat chat;

    @CreationTimestamp
    @Column(name = "created",nullable = false,updatable = false)
    private Date created;

    @UpdateTimestamp
    @Column(name = "updated")
    private Date updated;
}
