package com.jt28.a6735.modbus;

import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.ip.IpParameters;

/**
 * Created by a6735 on 2017/5/27.
 */

public class ModbusTool {
    public void connectIP() {
        ModbusMaster master = null;
        try {
            IpParameters tcpParameters = new IpParameters();
            tcpParameters.setHost("192.168.1.0");
            tcpParameters.setPort(502);
            ModbusFactory modbusFactory = new ModbusFactory();

            master = modbusFactory.createTcpMaster(tcpParameters, true);
            master.setTimeout(1000);
            master.setRetries(0);
            master.init();
            if (master.isInitialized()) {
//                mConnected = true;
//                new Thread(readTask).start();
            }
        } catch (Exception e) {
            master.destroy();
            e.printStackTrace();
            // L.e(getClass().getSimpleName(), e.getMessage());

            try {
                Thread.sleep(10 * 1000);
            } catch (Exception ex) {
            }
            //connectIP();
        }
    }
}
