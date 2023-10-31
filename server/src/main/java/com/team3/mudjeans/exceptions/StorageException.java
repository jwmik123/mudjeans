package com.team3.mudjeans.exceptions;

import java.io.IOException;

public class StorageException extends RuntimeException {
    public StorageException(String s, IOException e) {
        super(s, e);
    }
}
