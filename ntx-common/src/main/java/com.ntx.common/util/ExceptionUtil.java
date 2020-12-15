package com.ntx.common.util;


import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@Slf4j
public class ExceptionUtil {

    public static String getExceptionDetail(Exception exception){
        String ret = null;
        ByteArrayOutputStream out = null;
        PrintStream pout = null;
        try {
            out = new ByteArrayOutputStream();
            pout = new PrintStream(out);
            exception.printStackTrace(pout);
            ret = new String(out.toByteArray());
            pout.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.info(ExceptionUtil.getExceptionDetail(e));
        } finally {
            try{
                if(pout != null){
                    pout.close();
                }
                if(out != null){
                    out.close();
                }
            } catch (Exception e){
                e.printStackTrace();
                log.info(ExceptionUtil.getExceptionDetail(e));
            }
        }
        log.info(ret);
        return ret;
    }

    public static void getMessage(Throwable throwable){
        log.info(throwable.getLocalizedMessage());
        StackTraceElement[] stackTraceElements = throwable.getStackTrace();
        for(StackTraceElement stackTraceElement : stackTraceElements){
            log.info(stackTraceElement.toString());
        }
    }


}
