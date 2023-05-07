package com.acceleron.spendly.core.dto.notification.data;

import com.acceleron.spendly.core.dto.UserGroupDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupInvitation extends NotificationData {

    private UserGroupDto group;
    private boolean isAccepted;

    public GroupInvitation(UserGroupDto group) {
        this.group = group;
        this.isAccepted = false;
    }
}
