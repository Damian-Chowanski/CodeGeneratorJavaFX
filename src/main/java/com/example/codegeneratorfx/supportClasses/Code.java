package com.example.codegeneratorfx.supportClasses;

public class Code {
    String code;
    boolean isWinning = false;
    boolean isUsed = false;

    public Code(String code, boolean isWinning, boolean isUsed) {
        this.code = code;
        this.isWinning = isWinning;
        this.isUsed = isUsed;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean getIsWinning() {
        return isWinning;
    }

    public void setWinning(boolean winning) {
        isWinning = winning;
    }

    public boolean getIsUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
