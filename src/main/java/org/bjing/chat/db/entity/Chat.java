package org.bjing.chat.db.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToMany(mappedBy = "chat")
    private Set<Message> messages = new HashSet<>();

    @ManyToMany()
    @JoinTable(name = "user_chat", joinColumns = {@JoinColumn(name = "chat_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> users = new HashSet<>();

    @ManyToOne()
    @JoinColumn(name = "creator")
    private User creator;

    @OneToMany(mappedBy = "chat")
    private Set<Media> mediaFiles = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created", nullable = false, unique = false)
    private Date created;

    @UpdateTimestamp
    @Column(name = "updated")
    private Date updated;

    @Override
    public boolean equals(Object obj) {
        Chat secondChat = (Chat) obj;
        return obj.getClass().getName().equals(Chat.class.getName()) && secondChat.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
