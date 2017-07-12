package com.example.xiaojie.poem.Model;

import java.util.List;

/**
 * Created by xiaojie on 2017/7/11.
 */

public class PoemBean {

    private int poemIdx;
    private List<List<String>> poems;

    public int getPoemIdx() {
        return poemIdx;
    }

    public void setPoemIdx(int poemIdx) {
        this.poemIdx = poemIdx;
    }

    public List<List<String>> getPoems() {
        return poems;
    }

    public void setPoems(List<List<String>> poems) {
        this.poems = poems;
    }

}
