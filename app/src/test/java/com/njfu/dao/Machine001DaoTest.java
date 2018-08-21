package com.njfu.dao;

import com.njfu.dao.impl.Machine001DaoImpl;
import com.njfu.entity.Machine001;
import com.njfu.entity.Staff;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**机器记录对象Machine001数据库接口测试
 * Created by lvxz on 2018/6/2.
 */
public class Machine001DaoTest {

    Machine001Dao dao = new Machine001DaoImpl();

    @Ignore
    @Test
    public void insertMachine001One() throws Exception {
        Machine001 machine001 = new Machine001("MH-ZZ-001","10002522","150804202","1994-10-14 19:59:59");
        int flag = dao.insertMachine001One(machine001);
        Assert.assertEquals(1,flag);
    }

}