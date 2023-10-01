package org.bjing.chat.common.enums;

public enum FriendRequestStatus {
    PENDING("PENDING"),
    DECLINED("DECLINED"),
    ACCEPTED("ACCEPTED");

    public final String value;

    FriendRequestStatus(String value) {
        this.value = value;
    }
}
