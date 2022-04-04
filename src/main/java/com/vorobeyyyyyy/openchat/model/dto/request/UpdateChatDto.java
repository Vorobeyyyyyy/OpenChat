package com.vorobeyyyyyy.openchat.model.dto.request;

import com.vorobeyyyyyy.openchat.model.enumerated.ChatType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateChatDto {

    private UUID uuid;

    @NotNull(message = "Chat type can't be null")
    private ChatType type;

    @NotNull(message = "Chat name can't be null")
    private String name;

    @NotNull(message = "Chat description can't be null")
    private String description;

    private List<UUID> users;

    private UUID imageMediaUuid;

    @AssertTrue(message = "Private chat must have 1 user")
    private boolean validatePrivate() {
        return type != ChatType.PRIVATE || users.size() == 1;
    }

    @AssertTrue(message = "Group chat must have at least 1 users")
    private boolean validateGroup() {
        return type != ChatType.MULTI_USER || users.size() >= 1;
    }

    @AssertTrue(message = "Channel chat must not have users")
    private boolean validateChannel() {
        return type != ChatType.CHANNEL || CollectionUtils.isEmpty(users);
    }

}
