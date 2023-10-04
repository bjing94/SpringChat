package org.bjing.chat.repository;

import org.assertj.core.api.Assertions;
import org.bjing.chat.db.entity.User;
import org.bjing.chat.db.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void TestRepository_Save_ReturnSaved() {
//        Arrange
        User user = User.builder()
                .email("test@gmail.com")
                .firstname("Test")
                .lastname("Von Testowic")
                .build();
//        Act
        User savedUser = this.userRepository.save(user);

//        Assert

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isNotNull();
        Assertions.assertThat(savedUser.getEmail()).isEqualTo("test@gmail.com");
    }

    @Test
    public void TestRepository_SaveAll_ReturnMoreThanOneUser() {
        User user1 = User.builder()
                .email("test1@gmail.com")
                .firstname("Test")
                .lastname("Von Testowic")
                .build();
        User user2 = User.builder()
                .email("test2@gmail.com")
                .firstname("Test")
                .lastname("Von Testowic")
                .build();

        this.userRepository.save(user1);
        this.userRepository.save(user2);

        List<User> userList = this.userRepository.findAll();

        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList.size()).isGreaterThan(1);
    }
}
