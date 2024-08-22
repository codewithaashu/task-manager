package com.codewithaashu.task_manager.enums;

public enum ActivityType {
    assigned("assigned"), started("started"), in_progress("in progress"), bug("bug"), completed("completed"),
    commented("commented");

    private final String toString;

    private ActivityType(String toString) {
        this.toString = toString;
    }

    public String toString() {
        return toString;
    }
}
