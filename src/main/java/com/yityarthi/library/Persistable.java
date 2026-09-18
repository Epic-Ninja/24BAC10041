package com.yityarthi.library;

import java.io.IOException;

public interface Persistable {
    void saveToFile(String path) throws IOException;
    void loadFromFile(String path) throws IOException;
}
