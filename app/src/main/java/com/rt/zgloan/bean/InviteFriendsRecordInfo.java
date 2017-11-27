package com.rt.zgloan.bean;

import java.util.List;

/**
 * Created by hjy on 2017/8/31.
 */

public class InviteFriendsRecordInfo {

    private List<InviteFriendsRecordSonInfo> relute;
    private int count;//邀请总人数
    private int totalPages;

    public List<InviteFriendsRecordSonInfo> getRelute() {
        return relute;
    }

    public void setRelute(List<InviteFriendsRecordSonInfo> relute) {
        this.relute = relute;
    }

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


}
