package com.codewithaashu.task_manager.enums;

public enum Stage {
    todo("TODO"), in_progress("IN PROGRESS"), completed("COMPLETED");

    private final String toString;

    private Stage(String toString) {
        this.toString = toString;
    }

    public String toString() {
        return toString;
    }
}