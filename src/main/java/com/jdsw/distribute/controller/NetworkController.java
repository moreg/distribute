package com.jdsw.distribute.controller;

import com.jdsw.distribute.model.*;
import com.jdsw.distribute.service.*;

import com.jdsw.distribute.util.*;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
    private NetworkService networkService;
    @Resource
    private TelemarkeService telemarkeService;
    @Resource
    private DevelopService developService;
    /**
     * 分发池
     */
    @RequestMapping("/airForcePoolList")
    public Message airForcePoolList(int pageNum, int limit, String content, String strtime, String endtime, Distribute distribute, HttpServletRequest request, HttpServletResponse response){
        String name = (String) request.getAttribute("name");
        String username = (String) request.getAttribute("username");
        Map mapl = new HashMap();
        mapl.put("pageNum",pageNum);
        mapl.put("limit",limit);
        mapl.put("content",content);
        mapl.put("strtime",strtime);
        mapl.put("endtime",endtime);
        mapl.put("distribute",distribute);
        mapl.put("username",username);
        mapl.put("name",name);
        return Message.success("操作成功",networkService.airForcePoolList(mapl),0);
    }

    /**
     * 抢单池
     * @return
     */
    @RequestMapping("/grabbingPool")
    public Message grabbingPool(int pageNum, int limit, String content, String strtime, String endtime, HttpServletRequest request, HttpServletResponse response){
        String name = (String) request.getAttribute("name");
        String username = (String) request.getAttribute("username");
        Map mapl = new HashMap();
        mapl.put("pageNum",pageNum);
        mapl.put("limit",limit);
        mapl.put("content",content);
        mapl.put("strtime",strtime);
        mapl.put("endtime",endtime);
        mapl.put("name",name);
        mapl.put("username",username);
        return Message.success("查询成功",networkService.grabbingPool(mapl));
    }

    /**
     * 跟单池
     * @param pageNum
     * @param limit
     * @param content
     * @param strtime
     * @param endtime
     * @param request
     * @return
     */
    @RequestMapping("/withPool")
    public Message withPool(int pageNum, int limit, String content, String strtime, String endtime,Integer flag,HttpServletRequest request){
        String name = (String) request.getAttribute("name");
        String username = (String) request.getAttribute("username");
        Map mapl = new HashMap();
        mapl.put("pageNum",pageNum);
        mapl.put("limit",limit);
        mapl.put("content",content);
        mapl.put("strtime",strtime);
        mapl.put("endtime",endtime);
        mapl.put("username",username);
        mapl.put("name",name);
        mapl.put("flag",flag);
        return Message.success("查询成功",networkService.withPool(mapl));
    }
    /**
     * 新增
     * @param distribute
     * @return
     */
    @RequestMapping("/insertNetwoork")
    public Message insertNetwoork(@RequestBody Distribute distribute, HttpServletRequest request){
        String username = (String) request.getAttribute("username");
        String name = (String) request.getAttribute("name");
        Map map1 = new HashMap();
        map1.put("name",name);
        map1.put("username",username);
        map1.put("distribute",distribute);
        return Message.success("新增成功",networkService.insertNetwoork(map1));

    }
    /**
     * 导入网销线索
     * @param file
     * @return
     */
    @RequestMapping("/excelNetwork")
    public Message excelNetwork(@RequestParam("file") MultipartFile file,HttpServletRequest request) throws Exception {
        String username = (String) request.getAttribute("username");
        String name = (String) request.getAttribute("name");
        Map map1 = new HashMap();
        map1.put("name",name);
        map1.put("username",username);
        int i = networkService.excelNetwork(file,username,name);
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
        int i = networkService.deleteNetwork(distribute);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }

    /**
     * 编辑
     * @param
     * @return
     */
    @RequestMapping(value = "/updateNetwork",method = RequestMethod.POST,produces="application/json")
    public Message updateNetwork(@RequestBody Distribute distribute){
        int i = networkService.updateNetwork(distribute);
        if (i > 0){
            return  Message.success();
        }
        return  Message.fail();
    }
    /**
     * 编辑弹窗
     * @param
     * @return
     */
    @RequestMapping(value = "/updateNetworkPop")
    public Message updateNetworkPop(Integer id){
        return Message.success("查询成功",networkService.updateNetworkPop(id));
    }
    /**
     * 抢单接口
     * @return
     */
    @RequestMapping(value = "/orderTaking",method = RequestMethod.POST,produces="application/json")
    public Message orderTaking(HttpServletRequest request,@RequestBody Distribute distribute){
        String username = (String) request.getAttribute("username");
        String lastFollowName = (String) request.getAttribute("name");
        String role = (String) request.getAttribute("role");
        distribute.setLastFollowName(lastFollowName);
        distribute.setFirstFollowName(lastFollowName);
        int i = networkService.orderTaking(distribute,username,lastFollowName,role);
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
        int i = networkService.appoint(network,name);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }
    /**
     * 客户池
     * @return
     */
    @RequestMapping("/queryNetworkByLastName")
    public Message queryNetworkByLastName(HttpServletRequest request,int pageNum, int limit,String content, String strtime, String endtime) throws Exception{
        String lastFollowName = (String) request.getAttribute("name");
        String username = (String) request.getAttribute("username");
        Map mapl = new HashMap();
        mapl.put("pageNum",pageNum);
        mapl.put("limit",limit);
        mapl.put("content",content);
        mapl.put("strtime",strtime);
        mapl.put("endtime",endtime);
        mapl.put("lastFollowName",lastFollowName);
        mapl.put("username",username);
        return Message.success("操作成功",networkService.queryNetworkByLastName(mapl),0);
    }

    /**
     * 企业池
     * @return
     */
    @RequestMapping("/enterprisePoolList")
    public Message enterprisePoolList(HttpServletRequest request,int pageNum, int limit,String content, String strtime, String endtime){
        String name = (String) request.getAttribute("name");
        String username = (String) request.getAttribute("username");
        Map mapl = new HashMap();
        mapl.put("pageNum",pageNum);
        mapl.put("limit",limit);
        mapl.put("content",content);
        mapl.put("strtime",strtime);
        mapl.put("endtime",endtime);
        mapl.put("name",name);
        mapl.put("username",username);
        return Message.success("操作成功",networkService.enterprisePoolList(mapl));
    }
    /**
     * 写跟进
     * @return
     */
    @RequestMapping(value = "/followupNetwork",method = RequestMethod.POST,produces="application/json")
    public Message followupNetwork(@RequestBody DistributeFollow networkFollow,HttpServletRequest request){
        String strid = networkFollow.getTrackId().substring(0,1);
        String strid2 = networkFollow.getTrackId().substring(0,2);
        String name = (String) request.getAttribute("name");
        String username = (String) request.getAttribute("username");
        networkFollow.setFollowName(name);
        int i = 0;
        if ("XK".equals(strid2) || "K".equals(strid)){
             i = networkService.followupNetwork(networkFollow,username);
        }else if ("XL".equals(strid2)|| "L".equals(strid)){
            i = telemarkeService.followupNetwork(networkFollow);
        }else if ("XZ".equals(strid2)|| "Z".equals(strid)){
            i = developService.followupDevelop(networkFollow);
        }
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }

    /**
     * 激活客户
     * @param request
     * @return
     */
    @RequestMapping("/activation")
    public Message activation(HttpServletRequest request, @RequestBody Distribute distribute){
        String name = (String) request.getAttribute("name");
        String username = (String) request.getAttribute("username");
        Map mapl = new HashMap();
        mapl.put("username",username);
        mapl.put("distribute",distribute);
        mapl.put("name",name);
        int i = networkService.activation(mapl);
        if (i == 1){
            return  Message.success();
        }else if (i == 2){
            return Message.warn("客户已被激活");
        }
        return Message.success();
    }
    /**
     * 确认
     * @param request
     * @return
     */
    @RequestMapping("/confirm")
    public Message confirm(HttpServletRequest request, @RequestBody Distribute distribute){
        String name = (String) request.getAttribute("name");
        String username = (String) request.getAttribute("username");
        Map mapl = new HashMap();
        mapl.put("username",username);
        mapl.put("distribute",distribute);
        mapl.put("name",name);
        networkService.confirm(mapl);
        return Message.success();
    }
    /**
     * 上传图片
     * @param img
     * @param request
     * @return
     */
    @RequestMapping("/uploadImg")
    public Message  uploadImg(@RequestParam("img") MultipartFile[] img,HttpServletRequest request,@RequestParam("trackId")String trackId){
        String tid = trackId.substring(0,2);
        String uploadPathDB=null;
        Map map=new HashMap();
        try {
            uploadPathDB= ImageUtil.saveImage(trackId,img,tid);
        }catch (IOException e){
            e.printStackTrace();
            return Message.fail("上传失败");
        }
        map.put("imgUrl",uploadPathDB);
        return Message.success("上传成功",map);
    }
    /**
     * 新建上传图片
     * @param img
     * @param request
     * @return
     */
    @RequestMapping("/uploadImgNew")
    public Message  uploadImgNew(@RequestParam("img") MultipartFile[] img,HttpServletRequest request){
        return Message.success("操作成功",networkService.uploadImgNew(img));
    }
    /**
     * 退单图片上传
     * @param img
     * @param
     * @return
     */
    @RequestMapping("/uploadImgPool")
    public Message  uploadImgPool(@RequestParam("img") MultipartFile[] img,@Param("trackId") String trackId){
        String uploadPathDB=null;
        Map map=new HashMap();
        try {
            uploadPathDB= ImageUtil.saveImage(trackId,img,"XK");
        }catch (IOException e){
            e.printStackTrace();
            return Message.fail("上传失败");
        }
        map.put("imgUrl",uploadPathDB);
        map.put("trackId",trackId);
        return Message.success("上传成功",map);
    }
    /**
     * 上传音频
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/uploadFile")
    public Message  uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("trackId")String trackId){
        Map map=new HashMap();
        String uploadPathDB=null;
        try {
            uploadPathDB= VideoUtil.saveVideo(trackId,file,"XK");
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
        int i = networkService.submitRecordingNetwork(network);
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
    public Message recordingNetwork(@RequestBody Distribute distribute,HttpServletRequest request){
        String name = (String) request.getAttribute("name");
        String username = (String) request.getAttribute("username");
        Map mapl = new HashMap();
        mapl.put("username",username);
        mapl.put("distribute",distribute);
        mapl.put("name",name);
        int i = networkService.updateRecordingNetwork(mapl);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }
    /**
     * 财务我的客户
     * @return
     */
    @RequestMapping("/cashierCompleteLis")
    public Message cashierCompleteLis(int pageNum, int limit, String content, String strtime, String endtime,Integer orderState,HttpServletRequest request){
        String username = (String) request.getAttribute("username");
        String name = (String) request.getAttribute("name");
        Map mapl = new HashMap();
        mapl.put("pageNum",pageNum);
        mapl.put("limit",limit);
        mapl.put("content",content);
        mapl.put("strtime",strtime);
        mapl.put("endtime",endtime);
        mapl.put("username",username);
        mapl.put("name",name);
        mapl.put("orderState",orderState);
        return Message.success("操作成功",networkService.cashierCompleteLis(mapl),0);
    }

    /**
     * 录入弹窗
     * @param distribute
     * @return
     */
    @RequestMapping("/recordingPop")
    public Message recordingPop(@RequestBody Distribute distribute){
        return Message.success("查询成功",networkService.recordingPop(distribute));
    }
    /**
     * 成交订单
     * @param pageNum
     * @param limit
     * @param content
     * @param strtime
     * @param endtime
     *
     * @return
     */
/*
    @RequestMapping("/dealListNetwork")
    public Message dealListNetwork(int pageNum, int limit, String content, String strtime, String endtime,Integer status,HttpServletRequest request){
        String name = (String) request.getAttribute("name");
        String username = (String) request.getAttribute("username");
        Map mapl = new HashMap();
        mapl.put("pageNum",pageNum);
        mapl.put("limit",limit);
        mapl.put("content",content);
        mapl.put("strtime",strtime);
        mapl.put("endtime",endtime);
        mapl.put("username",username);
        mapl.put("name",name);
        mapl.put("status",status);
        return Message.success("操作成功",networkService.dealListNetwork(mapl),0);
    }
*/

    /**
     * 下属客户
     * @param pageNum
     * @param limit
     * @param content
     * @param strtime
     * @param endtime
     * @param request
     * @return
     */
    @RequestMapping("/subordinateList")
    public Message subordinateList(int pageNum, int limit, String content, String strtime, String endtime,String lastFollowName,HttpServletRequest request){
        Map mapl = new HashMap();
        mapl.put("pageNum",pageNum);
        mapl.put("limit",limit);
        mapl.put("content",content);
        mapl.put("strtime",strtime);
        mapl.put("endtime",endtime);
        mapl.put("lastFollowName",lastFollowName);
        return Message.success("操作成功",networkService.subordinateList(mapl),0);
    }
    /**
     * 转交
     * @param distribute
     * @return
     */
    @RequestMapping("/transferNetwork")
    public Message transferNetwork(@RequestBody List<Distribute> distribute,HttpServletRequest request){
        String name = (String) request.getAttribute("name");
        String username = (String) request.getAttribute("username");
        int i = networkService.transferNetwork(distribute,name);
        if ( i > 0){
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
        int i = networkService.setOverdueTime(distribute);
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
    public Message qureyFollowList(Integer id,String trackId)throws IOException{
        return Message.success("操作成功",networkService.qureyFollowList(id,trackId));
    }
    /**
     * 查询客户信息
     * @return
     * @throws IOException
     */
    @RequestMapping("/qureyCustomer")
    public Message qureyCustomer(Integer id,String trackId)throws IOException{
        return Message.success("操作成功",networkService.qureyCustomer(id,trackId));
    }
    /**
     * 查询客户信息
     * @return
     * @throws IOException
     */
    @RequestMapping("/qureyCustomer2")
    public Message qureyCustomer2(Integer id,String customerNo)throws IOException{
        return Message.success("操作成功",networkService.qureyCustomer2(id,customerNo));
    }

    /**
     * 关联企业
     * @param
     * @param
     * @return
     */
    @RequestMapping("/enterpriseList")
    public Message enterpriseList(@Param("corporatePhone") String corporatePhone, String corporatePhone2, String corporatePhone3){
        Map map = new HashMap();
        map.put("corporatePhone",corporatePhone);
        map.put("corporatePhone2",corporatePhone2);
        map.put("corporatePhone3",corporatePhone3);
        return Message.success("查询成功",networkService.enterpriseList(map));
    }
    /**
     * 增加关联企业
     * @param
     * @param
     * @return
     */
    @RequestMapping("/addEnterprise")
    public Message addEnterprise(@RequestBody Enterprise enterprise, HttpServletRequest request){
        String name = (String) request.getAttribute("name");
        String username = (String) request.getAttribute("username");
        Map mapl = new HashMap();
        mapl.put("name",name);
        mapl.put("enterprise",enterprise);
        int i = networkService.addEnterprise(mapl);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }
    /**
     * 办理业务
     * @param corporatePhone
     * @param corporatePhone2
     * @param corporatePhone3
     * @return
     */
    @RequestMapping("/businessList")
    public Message business(String corporatePhone,String corporatePhone2,String corporatePhone3){
        Map map = new HashMap();
        map.put("corporatePhone",corporatePhone);
        map.put("corporatePhone2",corporatePhone2);
        map.put("corporatePhone3",corporatePhone3);
        return Message.success("查询成功",networkService.business(map));
    }
    /**
     * 增加办理业务
     * @param
     * @param
     * @param
     * @return
     */
    @RequestMapping("/addBusiness")
    public Message addBusiness(@RequestBody DealOrder dealOrder,HttpServletRequest request){
        String name = (String) request.getAttribute("name");
        String username = (String) request.getAttribute("username");
        Map mapl = new HashMap();
        mapl.put("name",name);
        mapl.put("dealOrder",dealOrder);
        int i = networkService.addBusiness(mapl);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }
    /**
     * 申诉
     * @param distribute
     * @return
     */
    @RequestMapping("/returnPool")
    public Message returnPool(@RequestBody Distribute distribute,HttpServletRequest request){
        String name = (String) request.getAttribute("name");
        String username = (String) request.getAttribute("username");
        Map map1 = new HashMap();
        map1.put("name",name);
        map1.put("distribute",distribute);
        int i = networkService.returnPool(map1);
        if (i > 0) {
            return Message.success("操作成功");
        }
        return Message.fail();
    }
    /**
     * 不通过申诉
     * @param distribute
     * @return
     */
    @RequestMapping("/chargeback")
    public Message chargeback(@RequestBody Distribute distribute,HttpServletRequest request) {
        String name = (String) request.getAttribute("name");
        String username = (String) request.getAttribute("username");
        Map map1 = new HashMap();
        map1.put("name",name);
        map1.put("distribute",distribute);
        map1.put("username",username);
        int i = networkService.chargeback(map1);
        if (i > 0) {
            return Message.success("操作成功");
        }
        return Message.fail();
    }

    /**
     * 停止跟进
     * @param distribute
     * @param request
     * @return
     */
    @RequestMapping("/applyStop")
    public Message applyStop(@RequestBody Distribute distribute,HttpServletRequest request){
        String name = (String) request.getAttribute("name");
        String username = (String) request.getAttribute("username");
        Map map = new HashMap();
        map.put("name",name);
        map.put("username",username);
        map.put("distribute",distribute);
        int i =networkService.applyStop(map);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }
    /**
     * 通过申诉
     * @param
     * @param
     * @param
     * @param
     * @return
     */
    @RequestMapping("/adopt")
    public Message adopt(@RequestBody Distribute distribute,HttpServletRequest request){
        String name = (String) request.getAttribute("name");
        String username = (String) request.getAttribute("username");
        Map map1 = new HashMap();
        map1.put("name",name);
        map1.put("distribute",distribute);
        int i = networkService.adopt(map1);
        if (i > 0 ){
            return Message.success();
        }
        return  Message.fail();
    }
    @RequestMapping("/updateCustomer")
    public Message updateCustomer(@RequestBody Distribute distribute){
        int i = networkService.updateCustomer(distribute);
        if (i > 0){
            return Message.success();
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
    /*@RequestMapping("/statusList")
    public Message statusList(int pageNum, int limit,Integer status,HttpServletRequest request){
        String name = (String) request.getAttribute("name");
        String username = (String) request.getAttribute("username");
        return Message.success("查询成功",networkService.statusList(pageNum,limit,status,name));
    }*/
    /**
     * 强制超时
     */
   /* @RequestMapping("/setOvertime")
    public Message setOvertime(@RequestBody Distribute distribute){
        distributeService.setOvertime(distribute);
        return Message.success();
    }*/

    /**
     * 客服转交
     * @param distribute
     * @return
     */
   /* @RequestMapping("/customerTransfer")
    public Message customerTransfer(@RequestBody List<Distribute> distribute,HttpServletRequest request){
        String name = (String) request.getAttribute("name");
        String username = (String) request.getAttribute("username");
        int i = distributeService.customerTransfer(distribute,name);
        if (i > 0) {
            return Message.success();
        }
        return Message.fail();
    }*/
    /*
     * 超时接口
     * @param
     * @return
     */
    /*@RequestMapping("/overTime")
    public Message overTime(@RequestBody Distribute network){
        int i = networkService.overTime(network);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }*/
    /**
     * 通过停止申请
     * @param distribute
     * @param request
     * @return
     *//*
    @RequestMapping("/adoptStop")
    public Message adoptStop(@RequestBody Distribute distribute,HttpServletRequest request){
        String name = (String) request.getAttribute("name");
        String username = (String) request.getAttribute("username");
        Map map1 = new HashMap();
        map1.put("name",name);
        map1.put("distribute",distribute);
        int i = networkService.adoptStop(map1);
        if (i > 0 ){
            return Message.success();
        }
        return  Message.fail();
    }*/
    /* *//**
     * 驳回停止跟进
     * @param distribute
     * @param request
     * @return
     *//*
    @RequestMapping("/chargebackStop")
    public Message chargebackStop(@RequestBody Distribute distribute,HttpServletRequest request) {
        String name = (String) request.getAttribute("name");
        String username = (String) request.getAttribute("username");
        Map map1 = new HashMap();
        map1.put("name",name);
        map1.put("distribute",distribute);
        int i = networkService.chargebackStop(map1);
        if (i > 0) {
            return Message.success("操作成功");
        }
        return Message.fail();
    }*/
}
