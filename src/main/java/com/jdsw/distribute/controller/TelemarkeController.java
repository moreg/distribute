package com.jdsw.distribute.controller;

import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.DistributeFollow;
import com.jdsw.distribute.service.TelemarkeService;
import com.jdsw.distribute.util.Message;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/telemark")
public class TelemarkeController {
    @Resource
    private TelemarkeService telemarkService;


    /**
     * 陆军池列表
     * @param pageNum
     * @param limit
     * @param content
     * @param strtime
     * @param endtime
     * @return
     */
    @RequestMapping("/armyListPoolList")
    public Message armyListPoolList(int pageNum, int limit, String content, String strtime, String endtime){
        return Message.success("操作成功",telemarkService.armyListPoolList(pageNum,limit,content,strtime,endtime),0);
    }

    /**
     * 抢单列表
     * @param pageNum
     * @param limit
     * @param content
     * @param strtime
     * @param endtime
     * @return
     */
    @RequestMapping("/grabbingOrdersList")
    public Message grabbingOrdersList(int pageNum, int limit, String content, String strtime, String endtime){
        return Message.success("操作成功",telemarkService.grabbingOrdersList(pageNum,limit,content,strtime,endtime),0);
    }
    /**
     * 放客户线索到空军池
     * @param distribute
     * @return
     */
    @RequestMapping("/putarmyPoll")
    public Message putarmyPoll(@RequestBody Distribute distribute){
        int i = telemarkService.putarmyPoll(distribute);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }
    /**
     * 指定接单人
     * @param
     * @return
     */
    @RequestMapping("/appoint")
    public Message appoint(@RequestBody Distribute network, HttpSession session){
        String name = (String) session.getAttribute("name");
        int i = telemarkService.appoint(network,name);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }
    /**
     * 查询我的客户
     * @return
     */
    @RequestMapping("/queryTelemarkeByLastName")
    public Message queryTelemarkeByLastName(HttpSession session,int pageNum, int limit,String content, String strtime, String endtime){
        String lastFollowName = (String) session.getAttribute("name");
        return Message.success("操作成功",telemarkService.queryTelemarkeByLastName(pageNum,limit,content,strtime,endtime,lastFollowName),0);
    }
    /**
     * 抢单接口
     * @return
     */
    @RequestMapping("/orderTaking")
    public Message orderTaking(HttpSession session,@RequestBody Distribute distribute){
        String lastFollowName = (String) session.getAttribute("name");
        distribute.setLastFollowName(lastFollowName);
        int i = telemarkService.orderTaking(distribute);
        if (i > 0){
            return Message.success("抢单成功");
        }
        return Message.fail("抢单失败");
    }
    /**
     * 写跟进和激活
     * @return
     */
    @RequestMapping("/followupNetwork")
    public Message followupNetwork(@RequestBody DistributeFollow networkFollow, HttpSession session){
        String name = (String) session.getAttribute("name");
        networkFollow.setFollowName(name);
        int i = telemarkService.followupNetwork(networkFollow);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }
    /**
     * 业务员提交录单
     * @param network
     * @return
     */
    @RequestMapping("/submitRecordingNetwork")
    public Message submitRecordingNetwork(@RequestBody Distribute network){
        int i = telemarkService.SubmitRecordingNetwork(network);
        if (i > 0){
            return Message.success("提交成功");
        }
        return Message.fail("提交失败");
    }
    /**
     * 弹出录单页面
     * @return
     */
    @RequestMapping("/recordingShowNetwork")
    public Message recordingShowNetwork(Integer id){
        return Message.success("操作成功",telemarkService.RecordingShowNetwork(id),0);
    }
    /**
     * 财务录单
     * @return
     */
    @RequestMapping("/recordingNetwork")
    public Message recordingNetwork(@RequestBody Distribute network){
        int i = telemarkService.UpdateRecordingNetwork(network);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }

    /**
     * 主管分配
     * @param distribute
     * @return
     */
    @RequestMapping("/assign")
    public Message assign(@RequestBody Distribute distribute){
        telemarkService.assign(distribute);
        return Message.success();
    }
    /**
     * 让订单超时
     * @param distribute
     * @return
     */
    @RequestMapping("/setOvertime")
    public Message setOvertime(@RequestBody Distribute distribute){
        telemarkService.setOvertime(distribute);
        return Message.success();
    }
}
