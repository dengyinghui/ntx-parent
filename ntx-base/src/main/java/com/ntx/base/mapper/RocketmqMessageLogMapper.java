package com.ntx.base.mapper;

import com.ntx.base.view.RocketmqMessageView;
import com.ntx.base.vo.RocketmqMessageVo;
import org.apache.ibatis.annotations.Param;

public interface RocketmqMessageLogMapper extends BaseMapper<RocketmqMessageVo> {

    RocketmqMessageView findRocketmqMessageByMsgId(@Param("msgId") String msgId);

    int updateStatus(@Param("msgId") String msgId, @Param("status") int status);

}
