package com.netcracker.courses.oop.console.utils;

import java.util.List;

public interface Printer {
    <T> void print(T sth);
    <T> void printAll(List<T> allSth);
    <T> void printSelectionList(List<T> allSth);
}
