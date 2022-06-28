package ink.vor.ruedocto.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ink.vor.ruedocto.model.order.OrderInfo;
import ink.vor.ruedocto.vo.order.OrderCountQueryVo;
import ink.vor.ruedocto.vo.order.OrderCountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author muquanrui
 * @date 2022/6/24 11:21
 */
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
    List<OrderCountVo> selectOrderCount(@Param("vo") OrderCountQueryVo orderCountQueryVo);
}
