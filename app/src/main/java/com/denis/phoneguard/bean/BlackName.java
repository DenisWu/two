package com.denis.phoneguard.bean;

/**
 * 黑名单
 * @auther o
 * @date 2016/9/19.
 */
public class BlackName {
    private String phone;
    private int mode;

    public BlackName() {
        super();
    }

    public BlackName(String phone, int mode) {
        this.phone = phone;
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "BlackName{" +
                "phone='" + phone + '\'' +
                ", mode=" + mode +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
