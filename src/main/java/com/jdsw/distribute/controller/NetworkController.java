package com.jdsw.distribute.controller;

import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.DistributeFollow;
import com.jdsw.distribute.service.NetworkService;

import com.jdsw.distribute.service.UserService;
import com.jdsw.distribute.util.ImageUtil;
import com.jdsw.distribute.util.Message;

import com.jdsw.distribute.util.VideoUtil;
import com.jdsw.distribute.vo.AirForcePool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/distribute")
public class NetworkController {

    @Resource
    private NetworkService distributeService;
    @Autowired
    private UserService userService;


    /**
     * 空军池列表
     */
    @RequestMapping("/airForcePoolList")
    public Message airForcePoolList(int pageNum, int limit, String content, String strtime, String endtime, Distribute network, HttpServletRequest request, HttpServletResponse response){
       // String username = (String) session.getAttribute("username");
        String username = (String) request.getAttribute("username");
        return Message.success("操作成功",distributeService.airForcePoolList(pageNum,limit,network,content,strtime,endtime,username),0);
    }

    /**
     * 客服待处理
     * @param pageNum
     * @param limit
     * @param content
     * @param strtime
     * @param endtime
     * @param network
     * @param session
     * @return
     */
    @RequestMapping("/pendingPoolList")
    public Message pendingPoolList(int pageNum, int limit,String content, String strtime, String endtime, Distribute network,HttpSession session,HttpServletRequest request){
        request.getHeader("token");
        String username = (String) request.getAttribute("username");
        return Message.success("操作成功",distributeService.pendingPoolList(pageNum,limit,network,content,strtime,endtime,username),0);
    }
    /**
     * 新增
     * @param distribute
     * @return
     */
    @RequestMapping("/insertNetwoork")
    public Message insertNetwoork(@RequestBody Distribute distribute,HttpServletRequest request){
        String username = (String) request.getAttribute("username");
        int i = distributeService.insertNetwoork(distribute,username);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }
    /**
     * 导入网销线索
     * @param file
     * @return
     */
    @RequestMapping("/excelNetwork")
    public Message excelNetwork(@RequestParam("file") MultipartFile file,HttpServletRequest request,@RequestParam("userName")String username) throws Exception {
        int i = distributeService.excelNetwork(file,username);
        if (i > 0){
            return Message.success("导入成功");
        }
        return Message.fail();
    }
    /**
     * 删除
     * @param
     * @return
     */
    @RequestMapping(value = "/deleteNetwork",method = RequestMethod.POST,produces="application/json")
    public Message deleteNetwork(@RequestBody Distribute distribute){
        int i = distributeService.deleteNetwork(distribute);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }

    /**
     * 编辑
     * @param distribute
     * @return
     */
    @RequestMapping(value = "/updateNetwork",method = RequestMethod.POST,produces="application/json")
    public Message updateNetwork(@RequestBody Distribute distribute){
        int i = distributeService.updateNetwork(distribute);
        if (i > 0){
            return  Message.success();
        }
        return  Message.fail();
    }

    /**
     * 编辑获取客户资料接口
     * @param id
     * @return
     */
    @RequestMapping("/qureyNetwork")
    public Message qureyNetwork(Integer id){
        return Message.success("操作成功",distributeService.qureyNetwork(id));
    }

    /**
     * 抢单接口
     * @return
     */
    @RequestMapping(value = "/orderTaking",method = RequestMethod.POST,produces="application/json")
    public Message orderTaking(HttpServletRequest request,@RequestBody Distribute distribute){
        String lastFollowName = (String) request.getAttribute("name");
        distribute.setLastFollowName(lastFollowName);
        distribute.setFirstFollowName(lastFollowName);
        int i = distributeService.orderTaking(distribute);
        if (i == 1){
            return Message.success("抢单成功");
        }else if (i == 2){
            return Message.success("抢单成功","",1);
        }
        return Message.fail("抢单失败");
    }

    /**
     * 线索分发
     * @param
     * @return
     */
    @RequestMapping(value = "/appoint" ,method = RequestMethod.POST,produces="application/json")
    public Message appoint(@RequestBody List<Distribute> network,HttpServletRequest request){
        String name = (String) request.getAttribute("name");
        String username = (String) request.getAttribute("username");
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
    public Message queryNetworkByLastName(HttpServletRequest request,int pageNum, int limit,String content, String strtime, String endtime) throws Exception{
        String lastFollowName = (String) request.getAttribute("name");
        return Message.success("操作成功",distributeService.queryNetworkByLastName(pageNum,limit,content,strtime,endtime,lastFollowName),0);
    }

    /**
     * 我的客户待处理
     * @param
     * @param pageNum
     * @param limit
     * @param content
     * @param strtime
     * @param endtime
     * @return
     * @throws Exception
     */
    @RequestMapping("/pendingNetworkList")
    public Message pendingNetworkList(HttpServletRequest request,int pageNum, int limit,String content, String strtime, String endtime) throws Exception{
        String lastFollowName = (String) request.getAttribute("name");

        return Message.success("操作成功",distributeService.pendingNetworkList(pageNum,limit,content,strtime,endtime,lastFollowName),0);
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
    @RequestMapping(value = "/followupNetwork",method = RequestMethod.POST,produces="application/json")
    public Message followupNetwork(@RequestBody DistributeFollow networkFollow,HttpServletRequest request){
        String name = (String) request.getAttribute("name");
        String username = (String) request.getAttribute("username");
        networkFollow.setFollowName(name);
        int i = distributeService.followupNetwork(networkFollow,username);
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
    public Message  uploadImg(@RequestParam("img") MultipartFile[] img,HttpServletRequest request,@RequestParam("id")Integer id){
        String uploadPathDB=null;
        Map map=new HashMap();
        try {
            uploadPathDB= ImageUtil.saveImage(id,img,"WL");
        }catch (IOException e){
            e.printStackTrace();
            return Message.fail("上传失败");
        }
        map.put("imgUrl",uploadPathDB);
        return Message.success("上传成功",map);
    }
    /**
     * 上传音频
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/uploadFile")
    public Message  uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("id")Integer id){
        Map map=new HashMap();
        String uploadPathDB=null;
        try {
            uploadPathDB= VideoUtil.saveVideo(id,file,"WL");
        }catch (IOException e) {
            e.printStackTrace();
            return Message.fail("上传失败");
        }
        map.put("record",uploadPathDB);
       return Message.success("上传成功",map);
    }

    /**
     * 业务员提交录单
     * @param network
     * @return
     */
    @RequestMapping(value = "/submitRecordingNetwork",method = RequestMethod.POST,produces="application/json")
    public Message submitRecordingNetwork(@RequestBody List<Distribute> network){
        int i = distributeService.SubmitRecordingNetwork(network);
        if (i > 0){
            return Message.success("提交成功");
        }
        return Message.fail("提交失败");
    }
    /**
     * 财务录单
     * @return
     */
    @RequestMapping(value = "/recordingNetwork",method = RequestMethod.POST,produces="application/json")
    public Message recordingNetwork(@RequestBody Distribute network){
        int i = distributeService.UpdateRecordingNetwork(network);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }

    /**
     * 财务列表
     * @param pageNum
     * @param limit
     * @param content
     * @param strtime
     * @param endtime
     *
     * @return
     */
    @RequestMapping("/cashierListNetwork")
    public Message cashierListNetwork(int pageNum, int limit, String content, String strtime, String endtime,HttpServletRequest request){
        String username = (String) request.getAttribute("username");
        return Message.success("操作成功",distributeService.cashierListNetwork(pageNum,limit,content,strtime,endtime,username),0);
    }

    /**
     * 主管转交
     * @param distribute
     * @return
     */
    @RequestMapping("/transferNetwork")
    public Message transferNetwork(@RequestBody List<Distribute> distribute){
        int i = distributeService.transferNetwork(distribute);
        if ( i > 0){
            return Message.success();
        }
        return Message.fail();
    }

    /**
     * 客服转交
     * @param distribute
     * @return
     */
    @RequestMapping("/customerTransfer")
    public Message customerTransfer(@RequestBody List<Distribute> distribute){
        int i = distributeService.customerTransfer(distribute);
        if (i > 0) {
            return Message.success();
        }
        return Message.fail();
    }
    /**
     * 设置超时时间
     * @param
     * @return
     */
    @RequestMapping("/setOverdueTime")
    public Message setOverdueTime(@RequestBody Distribute distribute){
        int i = distributeService.setOverdueTime(distribute);
        if (i > 0){
           return Message.success();
        }
        return Message.fail();
    }
    /**
     * 查询跟进列表
     * @return
     * @throws IOException
     */
    @RequestMapping("/qureyFollowList")
    public Message qureyFollowList(Integer id)throws IOException{
        return Message.success("操作成功",distributeService.qureyFollowList(id));
    }

    /**
     * 通知接口
     * @return
     */
    @RequestMapping("/notice")
    public Message notice() throws Exception{
        return Message.success("操作成功",distributeService.notice());
    }
    /**
     * 退回给业务员
     * @param distribute
     * @return
     */
    @RequestMapping("/chargeback")
    public Message chargeback(@RequestBody Distribute distribute) {
        int i = distributeService.chargeback(distribute);
        if (i > 0) {
            return Message.success("操作成功");
        }
        return Message.fail();
    }

    /**
     * 状态查询
     * @param pageNum
     * @param limit
     * @param status
     * @param
     * @return
     */
    @RequestMapping("/statusList")
    public Message statusList(int pageNum, int limit,Integer status,HttpServletRequest request){
        String name = (String) request.getAttribute("name");
        return Message.success("查询成功",distributeService.statusList(pageNum,limit,status,name));
    }
    /**
     * 主动设置订单超时
     */
    @RequestMapping("/setOvertime")
    public Message setOvertime(@RequestBody Distribute distribute){
        distributeService.setOvertime(distribute);
        return Message.success();
    }

}
