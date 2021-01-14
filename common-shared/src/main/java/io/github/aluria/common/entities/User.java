package io.github.aluria.common.entities;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class User {
	
	private final UUID id;
	
	private final String username;
	private final String realName;
	private final String lastAddress;
	
	private final Timestamp firstLoginDate;
	private final Timestamp lastLoginDate;
	
	private final Set<UUID> friends;
	
	private long discordId;
	
	public boolean isFriend(UUID id) {
		return friends.contains(id);
	}
	
	public boolean isDiscordSynchronized() {
		return discordId != 0L;
	}
}
