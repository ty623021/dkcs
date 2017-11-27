package com.rt.zgloan.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.rt.zgloan.dbentity.UserAction;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/11/26.
 */
public class UserActionDao {
    private Dao<UserAction, Integer> userActionIntegerDao;
    private DatabaseHelper helper;
    private  static UserActionDao userActiondao;

    private UserActionDao(Context context){
        try
        {
            if (helper==null){
                helper = DatabaseHelper.getHelper(context);
            }
            if (userActionIntegerDao==null){
                userActionIntegerDao = helper.getDao(UserAction.class);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static UserActionDao getInstance(Context context){
        if (userActiondao==null){
            userActiondao=new UserActionDao(context);
        }
        return userActiondao;
    }

    /**
     * 添加
     * */
    public void add(UserAction userAction)
    {
        try
        {
            userActionIntegerDao.create(userAction);

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    /**
     * 清除table
     * */
    public void clear() {
        try {
            DeleteBuilder<UserAction, Integer> deleteBuilder = userActionIntegerDao
                    .deleteBuilder();
            userActionIntegerDao.delete(deleteBuilder.prepare());

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 查询所有
     * */
    public List<UserAction> qureAll() {
        try {
            return userActionIntegerDao.queryForAll();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
