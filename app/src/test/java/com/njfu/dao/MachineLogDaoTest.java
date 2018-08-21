package com.njfu.dao;

import com.njfu.dao.impl.MachineLogDaoImpl;
import com.njfu.entity.MachineLog;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**MachineLogDao接口测试
 * Created by lvxz on 2018/6/1.
 */
public class MachineLogDaoTest {

    private MachineLogDao dao = new MachineLogDaoImpl();

    @Test
    public void queryMachineLogOne() {
        MachineLog machineLog = dao.queryMachineLogOne("MH-ZZ-001");
        Assert.assertEquals("008",machineLog.getPrcd());
    }

}