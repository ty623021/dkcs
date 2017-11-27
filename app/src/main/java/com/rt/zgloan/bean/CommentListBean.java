package com.rt.zgloan.bean;

import java.util.List;

/**
 * Created by zcy on 2017/11/3 0003.
 */

public class CommentListBean {
    public List<CommentBean> getCommentBeans() {
        return comment;
    }

    public void setCommentBeans(List<CommentBean> comment) {
        this.comment = comment;
    }

    List<CommentBean> comment;

    public static class CommentBean {
        /**
         * id : 1
         * content : 13000001234dasdasdasdsa
         */

        private String id;
        private String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
