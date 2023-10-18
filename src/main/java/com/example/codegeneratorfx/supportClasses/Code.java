package com.example.codegeneratorfx.supportClasses;

public class Code {
    int codeID;
    String code;
    boolean isWinning = false;
    boolean isUsed = false;

    public Code(int codeID, String code, boolean isWinning, boolean isUsed) {
        this.codeID = codeID;
        this.code = code;
        this.isWinning = isWinning;
        this.isUsed = isUsed;
    }

    public int getCodeID() {
        return codeID;
    }

    public void setCodeID(int codeID) {
        this.codeID = codeID;
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
