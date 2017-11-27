package com.rt.zgloan.bean;

import java.util.List;

/**
 * Created by hjy on 2017/8/24.
 */

public class LoanListInfo {

    private List<HomeListInfo> relute;
    private int totalPages;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<HomeListInfo> getRelute() {
        return relute;
    }

    public void setRelute(List<HomeListInfo> relute) {
        this.relute = relute;
    }
}
