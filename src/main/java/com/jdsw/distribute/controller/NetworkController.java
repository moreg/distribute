package com.jdsw.distribute.controller;

import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.DistributeFollow;
import com.jdsw.distribute.service.NetworkService;

import com.jdsw.distribute.util.Message;

import com.jdsw.distribute.vo.AirForcePool;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/distribute")
public class NetworkController {

    @Resource
    private NetworkService distributeService;



    /**
     * 空军池列表
     */
    @RequestMapping("/airForcePoolList")
    public Message airForcePoolList(int pageNum, int limit, String content, String strtime, String endtime, Distribute network){
        return Message.success("操作成功",distributeService.airForcePoolList(pageNum,limit,network,content,strtime,endtime),0);
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
        return Message.success("操作成功",distributeService.grabbingOrdersList(pageNum,limit,content,strtime,endtime),0);
    }
    /**
     * 放客户线索到空军池
     * @param distribute
     * @return
     */
    @RequestMapping("/putAirForcePoll")
    public Message putAirForcePoll(@RequestBody Distribute distribute){
        int i = distributeService.putAirForcePoll(distribute);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }

    /**
     * 新增
     * @param distribute
     * @return
     */
    @RequestMapping("/insertNetwoork")
    public Message insertNetwoork(@RequestBody Distribute distribute){
        int i = distributeService.insertNetwoork(distribute);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }

    /**
     * 删除
     * @param
     * @return
     */
    @RequestMapping("/deleteNetwork")
    public Message deleteNetwork(@RequestBody Distribute distribute){
        distributeService.deleteNetwork(distribute);
        return Message.success();
    }

    /**
     * 编辑
     * @param distribute
     * @return
     */
    @RequestMapping("/updateNetwork")
    public Message updateNetwork(@RequestBody Distribute distribute){
        distributeService.updateNetwork(distribute);
        return  Message.success();
    }
    @RequestMapping("/qureyNetwork")
    public Message qureyNetwork(Integer id){
        return Message.success("操作成功",distributeService.qureyNetwork(id));
    }
    /**
     * 导入电销线索
     * @param
     * @return
     */
    @RequestMapping("/excelTelemarketing")
    public Message excelTelemarketing(HttpServletRequest request, @RequestParam("file") MultipartFile file, Distribute excels) throws Exception {
        int i = distributeService.ExclDistribute(file);
        if (i > 0){
            return Message.success("导入成功");
        }
        return Message.fail("导入失败");
    }

    /**
     * 抢单接口
     * @return
     */
    @RequestMapping("/orderTaking")
    public Message orderTaking(HttpSession session,@RequestBody Distribute distribute){
        String lastFollowName = (String) session.getAttribute("name");
        distribute.setLastFollowName(lastFollowName);
        int i = distributeService.orderTaking(distribute);
        if (i == 1){
            return Message.success("抢单成功");
        }else if (i == 2){
            return Message.success("抢单成功","",1);
        }
        return Message.fail("抢单失败");
    }

    /**
     * 指定接单人
     * @param
     * @return
     */
    @RequestMapping("/appoint")
    public Message appoint(@RequestBody List<Distribute> network, HttpSession session){
        String name = (String) session.getAttribute("name");
        int i = distributeService.appoint(network,name);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }
    /**
     * 查询我的客户
     * @return
     */
    @RequestMapping("/queryNetworkByLastName")
    public Message queryNetworkByLastName(HttpSession session,int pageNum, int limit,String content, String strtime, String endtime){
        String lastFollowName = (String) session.getAttribute("name");
        return Message.success("操作成功",distributeService.queryNetworkByLastName(pageNum,limit,content,strtime,endtime,lastFollowName),0);
    }
    /**
     * 超时接口
     * @param
     * @return
     */
    @RequestMapping("/overTime")
    public Message overTime(@RequestBody Distribute network){
        int i = distributeService.overTime(network);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }

    /**
     * 写跟进
     * @return
     */
    @RequestMapping("/followupNetwork")
    public Message followupNetwork(@RequestBody DistributeFollow networkFollow, HttpSession session){
        String name = (String) session.getAttribute("name");
        networkFollow.setFollowName(name);
        int i = distributeService.followupNetwork(networkFollow);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }

    /**
     * 上传图片
     * @param img
     * @param request
     * @return
     */
    @RequestMapping("/uploadImg")
    public  @ResponseBody Message  uploadImg(@RequestParam("img") MultipartFile img,HttpServletRequest request,@RequestBody Distribute distribute){
          int i =distributeService.uploadImg(img,request);
          if (i == 1){
              return Message.success("上传成功");
          }
          return Message.fail("上传失败");
    }

    /**
     * 业务员提交录单
     * @param network
     * @return
     */
    @RequestMapping("/submitRecordingNetwork")
    public Message submitRecordingNetwork(@RequestBody Distribute network){
        int i = distributeService.SubmitRecordingNetwork(network);
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
        return Message.success("操作成功",distributeService.RecordingShowNetwork(id),0);
    }
    /**
     * 财务录单
     * @return
     */
    @RequestMapping("/recordingNetwork")
    public Message recordingNetwork(@RequestBody Distribute network){
        int i = distributeService.UpdateRecordingNetwork(network);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }

    /**
     * 财务查询页面
     * @param pageNum
     * @param limit
     * @param content
     * @param strtime
     * @param endtime
     *
     * @return
     */
    @RequestMapping("/cashierListNetwork")
    public Message cashierListNetwork(int pageNum, int limit, String content, String strtime, String endtime){
        return Message.success("操作成功",distributeService.cashierListNetwork(pageNum,limit,content,strtime,endtime),0);
    }

    /**
     * 转发
     * @param distribute
     * @return
     */
    @RequestMapping("/transferNetwork")
    public Message transferNetwork(@RequestBody Distribute distribute){
        return Message.success("操作成功",distributeService.transferNetwork(distribute),0);
    }

    /**
     * 设置过期时间
     * @param
     * @return
     */
    @RequestMapping("/setOverdueTime")
    public Message setOverdueTime(@RequestBody AirForcePool airForcePool){
        distributeService.setOverdueTime(airForcePool);
        return Message.success();
    }

}
