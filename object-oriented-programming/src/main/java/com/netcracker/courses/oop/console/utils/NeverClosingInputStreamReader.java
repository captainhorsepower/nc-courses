package com.netcracker.courses.oop.console.utils;

import java.io.InputStream;
import java.io.InputStreamReader;

public class NeverClosingInputStreamReader extends InputStreamReader {
    public NeverClosingInputStreamReader(InputStream in) {
        super(in);
    }

    @Override
    public void close(){
        /* do nothing to safely read until i'm done */
    }
}
