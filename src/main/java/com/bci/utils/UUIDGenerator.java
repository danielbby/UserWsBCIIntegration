package com.bci.utils;
import java.util.UUID;

public final class UUIDGenerator {
	public final static String generateUserId() {
        return UUID.randomUUID().toString();
    }
}
