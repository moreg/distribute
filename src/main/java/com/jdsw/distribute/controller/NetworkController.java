package com.jdsw.distribute.controller;

import com.jdsw.distribute.model.*;
import com.jdsw.distribute.service.*;

import com.jdsw.distribute.util.*;

import com.jdsw.distribute.vo.AirForcePool;

import org.apache.ibatis.annotations.Param;
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
    @Resource
    private TelemarkeService telemarkeService;
    @Resource
    private CustomerService customerService;
    @Resource
    private DevelopService developService;
    /**
     * 空军线索
     */
    @RequestMapping("/airForcePoolList")
    public Message airForcePoolList(int pageNum, int limit, String content, String strtime, String endtime, Distribute distribute, HttpServletRequest request, HttpServletResponse response){
        //String username = (String) request.getAttribute("username");
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        Map mapl = new HashMap();
        mapl.put("pageNum",pageNum);
        mapl.put("limit",limit);
        mapl.put("content",content);
        mapl.put("strtime",strtime);
        mapl.put("endtime",endtime);
        mapl.put("distribute",distribute);
        mapl.put("username",username);
        mapl.put("name",name);
        return Message.success("操作成功",distributeService.airForcePoolList(mapl),0);
    }

    /**
     * 抢单池
     * @return
     */
    @RequestMapping("/grabbingPool")
    public Message grabbingPool(int pageNum, int limit, String content, String strtime, String endtime, Distribute network, HttpServletRequest request, HttpServletResponse response){
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        Map mapl = new HashMap();
        mapl.put("pageNum",pageNum);
        mapl.put("limit",limit);
        mapl.put("content",content);
        mapl.put("strtime",strtime);
        mapl.put("endtime",endtime);
        return Message.success("查询成功",distributeService.grabbingPool(mapl));
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
    public Message withPool(int pageNum, int limit, String content, String strtime, String endtime, Integer issue, HttpServletRequest request){
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        Map mapl = new HashMap();
        mapl.put("pageNum",pageNum);
        mapl.put("limit",limit);
        mapl.put("content",content);
        mapl.put("strtime",strtime);
        mapl.put("endtime",endtime);
        mapl.put("username",username);
        mapl.put("name",name);
        mapl.put("issue",issue);
        return Message.success("查询成功",distributeService.withPool(mapl));
    }
    /**
     * 新增
     * @param distribute
     * @return
     */
    @RequestMapping("/insertNetwoork")
    public Message insertNetwoork(@RequestBody Distribute distribute,HttpServletRequest request){
        //String username = (String) request.getAttribute("username");
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        Map map1 = new HashMap();
        map1.put("name",name);
        map1.put("username",username);
        map1.put("distribute",distribute);
        int i = distributeService.insertNetwoork(map1);
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
    public Message excelNetwork(@RequestParam("file") MultipartFile file,HttpServletRequest request) throws Exception {
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        int i = distributeService.excelNetwork(file,username,name);
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
     * 抢单接口
     * @return
     */
    @RequestMapping(value = "/orderTaking",method = RequestMethod.POST,produces="application/json")
    public Message orderTaking(HttpServletRequest request,@RequestBody Distribute distribute){
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String lastFollowName = (String) map.get("name");
        distribute.setLastFollowName(lastFollowName);
        distribute.setFirstFollowName(lastFollowName);
        int i = distributeService.orderTaking(distribute,username,lastFollowName);
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
        //String name = (String) request.getAttribute("name");
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        int i = distributeService.appoint(network,name);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }
    /**
     * 空中客户
     * @return
     */
    @RequestMapping("/queryNetworkByLastName")
    public Message queryNetworkByLastName(HttpServletRequest request,int pageNum, int limit,String content, String strtime, String endtime) throws Exception{
        //String lastFollowName = (String) request.getAttribute("name");
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String lastFollowName = (String) map.get("name");
        Map mapl = new HashMap();
        mapl.put("pageNum",pageNum);
        mapl.put("limit",limit);
        mapl.put("content",content);
        map.put("strtime",strtime);
        map.put("endtime",endtime);
        return Message.success("操作成功",distributeService.queryNetworkByLastName(pageNum,limit,content,strtime,endtime,lastFollowName,username),0);
    }

    /**
     * 企业池
     * @return
     */
    @RequestMapping("/enterprisePoolList")
    public Message enterprisePoolList(HttpServletRequest request,int pageNum, int limit,String content, String strtime, String endtime){
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        Map mapl = new HashMap();
        mapl.put("pageNum",pageNum);
        mapl.put("limit",limit);
        mapl.put("content",content);
        mapl.put("strtime",strtime);
        mapl.put("endtime",endtime);
        mapl.put("name",name);
        return Message.success("操作成功",distributeService.enterprisePoolList(mapl));
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
        String strid = networkFollow.getTrackId().substring(0,1);
        //String name = (String) request.getAttribute("name");
        //String username = (String) request.getAttribute("username");
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        networkFollow.setFollowName(name);
        int i = 0;
        if ("K".equals(strid)){
             i = distributeService.followupNetwork(networkFollow,username);
        }else if ("L".equals(strid)){
            i = telemarkeService.followupNetwork(networkFollow);
        }else if ("Z".equals(strid)){
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
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        Map mapl = new HashMap();
        mapl.put("username",username);
        mapl.put("distribute",distribute);
        mapl.put("name",name);
        distributeService.activation(mapl);
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
        String uploadPathDB=null;
        Map map=new HashMap();
        try {
            uploadPathDB= ImageUtil.saveImage(trackId,img,"K");
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
        String uploadPathDB=null;
        String trackId = Rand.getTrackId("K");//获得跟踪单号
        Map map=new HashMap();
        try {
            uploadPathDB= ImageUtil.saveImage(trackId,img,"K");
        }catch (IOException e){
            e.printStackTrace();
            return Message.fail("上传失败");
        }
        map.put("imgUrl",uploadPathDB);
        map.put("trackId",trackId);
        return Message.success("上传成功",map);
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
            uploadPathDB= ImageUtil.saveImage(trackId,img,"K");
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
            uploadPathDB= VideoUtil.saveVideo(trackId,file,"K");
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
        int i = distributeService.submitRecordingNetwork(network);
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
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        Map mapl = new HashMap();
        mapl.put("username",username);
        mapl.put("distribute",distribute);
        mapl.put("name",name);
        int i = distributeService.updateRecordingNetwork(mapl);
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
        //String username = (String) request.getAttribute("username");
        //String name = (String) request.getAttribute("name");
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        Map mapl = new HashMap();
        mapl.put("pageNum",pageNum);
        mapl.put("limit",limit);
        mapl.put("content",content);
        mapl.put("strtime",strtime);
        mapl.put("endtime",endtime);
        mapl.put("username",username);
        mapl.put("name",name);
        mapl.put("orderState",orderState);
        return Message.success("操作成功",distributeService.cashierCompleteLis(mapl),0);
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
    @RequestMapping("/dealListNetwork")
    public Message dealListNetwork(int pageNum, int limit, String content, String strtime, String endtime,Integer status,HttpServletRequest request){
        //String username = (String) request.getAttribute("username");
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        Map mapl = new HashMap();
        mapl.put("pageNum",pageNum);
        mapl.put("limit",limit);
        mapl.put("content",content);
        mapl.put("strtime",strtime);
        mapl.put("endtime",endtime);
        mapl.put("username",username);
        mapl.put("name",name);
        mapl.put("status",status);
        return Message.success("操作成功",distributeService.dealListNetwork(mapl),0);
    }

    /**
     * 下属客户
     * @param pageNum
     * @param limit
     * @param content
     * @param strtime
     * @param endtime
     * @param status
     * @param pool
     * @param request
     * @return
     */
    @RequestMapping("/subordinateList")
    public Message subordinateList(int pageNum, int limit, String content, String strtime, String endtime,Integer status,String pool,String lastFollowName,HttpServletRequest request){
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        Map mapl = new HashMap();
        mapl.put("pageNum",pageNum);
        mapl.put("limit",limit);
        mapl.put("content",content);
        mapl.put("strtime",strtime);
        mapl.put("endtime",endtime);
        mapl.put("username",username);
        mapl.put("name",name);
        mapl.put("status",status);
        mapl.put("lastFollowName",lastFollowName);
        mapl.put("pool",pool);
        return Message.success("操作成功",distributeService.subordinateList(mapl),0);
    }
    /**
     * 主管转交
     * @param distribute
     * @return
     */
    @RequestMapping("/transferNetwork")
    public Message transferNetwork(@RequestBody List<Distribute> distribute,HttpServletRequest request){
        //String name = (String) request.getAttribute("name");
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        int i = distributeService.transferNetwork(distribute,name);
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
    public Message customerTransfer(@RequestBody List<Distribute> distribute,HttpServletRequest request){
        //String name = (String) request.getAttribute("name");
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        int i = distributeService.customerTransfer(distribute,name);
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
    public Message qureyFollowList(Integer id,String trackId)throws IOException{
        String strid = trackId.substring(0,1);
        if ("K".equals(strid)){
            return Message.success("操作成功",distributeService.qureyFollowList(id,trackId));
        }else if ("L".equals(strid)){
            return Message.success("操作成功",telemarkeService.qureyFollowList(id));
        }else if ("Z".equals(strid)){
            return Message.success("操作成功",developService.qureyFollowList(id));
        }
        return Message.fail();
    }
    /**
     * 查询客户信息
     * @return
     * @throws IOException
     */
    @RequestMapping("/qureyCustomer")
    public Message qureyCustomer(Integer id,String trackId)throws IOException{
        String strid = trackId.substring(0,1);
        return Message.success("操作成功",distributeService.qureyCustomer(id,trackId));
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
        return Message.success("查询成功",distributeService.enterpriseList(map));
    }
    /**
     * 增加关联企业
     * @param
     * @param
     * @return
     */
    @RequestMapping("/addEnterprise")
    public Message addEnterprise(@RequestBody Enterprise enterprise, HttpServletRequest request){
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        Map mapl = new HashMap();
        mapl.put("name",name);
        mapl.put("enterprise",enterprise);
        int i = distributeService.addEnterprise(mapl);
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
        return Message.success("查询成功",distributeService.business(map));
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
        Map mapl = new HashMap();
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        mapl.put("name",name);
        mapl.put("dealOrder",dealOrder);
        int i = distributeService.addBusiness(mapl);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }
    /**
     * 退单
     * @param distribute
     * @return
     */
    @RequestMapping("/returnPool")
    public Message returnPool(@RequestBody Distribute distribute,HttpServletRequest request){
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        Map map1 = new HashMap();
        map1.put("name",name);
        map1.put("distribute",distribute);
        int i = distributeService.returnPool(map1);
        if (i > 0) {
            return Message.success("操作成功");
        }
        return Message.fail();
    }
    /**
     * 退回给业务员
     * @param distribute
     * @return
     */
    @RequestMapping("/chargeback")
    public Message chargeback(@RequestBody Distribute distribute,HttpServletRequest request) {
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        Map map1 = new HashMap();
        map1.put("name",name);
        map1.put("distribute",distribute);
        int i = distributeService.chargeback(map1);
        if (i > 0) {
            return Message.success("操作成功");
        }
        return Message.fail();
    }

    /**
     * 通过
     * @param
     * @param
     * @param
     * @param
     * @return
     */
    @RequestMapping("/adopt")
    public Message adopt(@RequestBody Distribute distribute,HttpServletRequest request){
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        Map map1 = new HashMap();
        map1.put("name",name);
        map1.put("distribute",distribute);
        int i = distributeService.adopt(map1);
        if (i > 0 ){
            return Message.success();
        }
        return  Message.fail();
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
        //String name = (String) request.getAttribute("name");
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        return Message.success("查询成功",distributeService.statusList(pageNum,limit,status,name));
    }
    /**
     * 强制超时
     */
    @RequestMapping("/setOvertime")
    public Message setOvertime(@RequestBody Distribute distribute){
        distributeService.setOvertime(distribute);
        return Message.success();
    }

}
