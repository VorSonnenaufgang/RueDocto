package ink.vor.ruedocto.sms.receiver;

import com.rabbitmq.client.Channel;
import ink.vor.ruedocto.rabbit.constant.MqConst;
import ink.vor.ruedocto.sms.service.SmsService;
import ink.vor.ruedocto.vo.msm.MsmVo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author muquanrui
 * @date 2022/6/27 07:42
 */
@Component
public class SmsReceiver {
    @Autowired
    private SmsService smsService;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_MSM_ITEM, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_DIRECT_MSM),
            key = {MqConst.ROUTING_MSM_ITEM}
    ))
    public void send(MsmVo msmVo, Message message, Channel channel) {
        smsService.send(msmVo);
    }
}
