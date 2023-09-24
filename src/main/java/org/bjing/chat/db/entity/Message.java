package org.bjing.chat.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne(targetEntity = Chat.class)
    @JoinColumn(name = "chat_id", nullable = false)
    Chat chat;

    @Column(columnDefinition = "VARCHAR(1024)", length = 1024)
    String content;

    @OneToMany(mappedBy = "message", cascade = CascadeType.PERSIST)
    private Set<Media> mediaFiles = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created", nullable = false, updatable = false)
    private Date created;

    @UpdateTimestamp
    @Column(name = "updated")
    private Date updated;
}
