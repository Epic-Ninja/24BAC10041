package com.yityarthi.library;

public class Member extends Person {
    public Member(int id, String name) {
        super(id, name);
    }

    @Override
    public String getRole() { return "Member"; }

    @Override
    public String toString() {
        return getId() + " | " + getName() + " | " + getRole();
    }
}
