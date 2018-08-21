package com.njfu.dao;

import com.njfu.dao.impl.StaffDaoImpl;
import com.njfu.entity.Staff;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**StaffDao接口测试
 * Created by lvxz on 2018/5/13.
 */
public class StaffDaoTest {

    private StaffDao dao = new StaffDaoImpl();

    @Test
    public void findUserIdAndTccom001AndTirou001ByAccount() throws Exception {

        Staff staff = dao.findUserIdAndTccom001AndTirou001ByAccount("150804201");
        Assert.assertEquals("排钻组",staff.getDsca());

    }

    @Test
    public void findTipcs001_praaByPrcd() throws Exception {

        String str = dao.findTipcs001_praaByPrcd("008");
        Assert.assertEquals("组装",str);

    }

}