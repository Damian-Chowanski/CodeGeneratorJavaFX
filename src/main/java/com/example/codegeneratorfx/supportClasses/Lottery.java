package com.example.codegeneratorfx.supportClasses;

import java.util.ArrayList;

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

    public void generateCodes(){
        //Random class with chars equals to {a...z} or {A...Z} or {0...9}
        RandomInRanges rir = new RandomInRanges(48, 57);
        rir.addRange(65, 90);
        rir.addRange(97, 122);



    }


}
