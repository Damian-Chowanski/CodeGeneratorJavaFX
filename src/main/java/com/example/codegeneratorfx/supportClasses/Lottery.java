package com.example.codegeneratorfx.supportClasses;

import java.util.ArrayList;
import java.util.Random;

public class Lottery {
    private ArrayList<Code> codes;

    public Lottery() {
        this.codes = new ArrayList<>();
    }

    public ArrayList<Code> getCodes() {
        return codes;
    }

    public void setCodes(ArrayList<Code> codes) {
        this.codes = codes;
    }

    public void generateCodes(int numberOfCodes, int codeLength){
        Random rand = new Random();
        //Random class with chars equals to {a...z} or {A...Z} or {0...9}
        RandomInRanges rir = new RandomInRanges(48, 57);
        rir.addRange(65, 90);
        rir.addRange(97, 122);

        //Set win and use chance percent:
        double usedChance = 0.45;
        double winChance = 0.05;

        //Generates unique codes to ArrayList<String>

        for (int i = 0; i < numberOfCodes; i++) {
            String code = "";
            for (int j = 0; j < codeLength; j++) {
                code += (char) rir.getRandom();
            }
            if (!isUnique(code)) {
                i--;
            } else codes.add(new Code(codes.size()+1,code,(rand.nextDouble() < usedChance),rand.nextDouble() > winChance));
        }
    }

    private boolean isUnique(String codeToCheck) {
        for (Code code: codes){
            if (code.code.equals(codeToCheck)){
                return false;
            }
        }
        return true;
    }

    private void removeCode(int id){
        codes.remove(id);
    }

    private void addCode(String code, boolean isWon, boolean isUsed){
        codes.add(new Code(codes.size()+1,code,isWon,isUsed));
    }

    private void editCode(int id, String code, boolean isWon, boolean isUsed){
        codes.get(id).setCode(code);
        codes.get(id).setWinning(isWon);
        codes.get(id).setUsed(isUsed);
    }


}
