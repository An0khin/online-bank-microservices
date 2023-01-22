package com.home.model.primitive;

public class Flag {
    public Boolean flag;

    public Flag() {
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Flag{" +
                "flag=" + flag +
                '}';
    }
}
