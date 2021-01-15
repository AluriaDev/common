package io.github.aluria.common.registries;

import io.github.aluria.common.entities.User;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserRegistry {

    private final ConcurrentHashMap<UUID, User> userMap;

    public UserRegistry() {
        this.userMap = new ConcurrentHashMap<>();
    }

    public void registerUser(User user) {
        userMap.put(user.getId(), user);
    }

    public User getUserById(UUID id) {
        return userMap.get(id);
    }

    public User removeUser(UUID id) {
        return userMap.remove(id);
    }

    public Collection<User> getAllUsers() {
        return userMap.values();
    }
}
