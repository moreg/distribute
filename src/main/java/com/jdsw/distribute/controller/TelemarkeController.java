package com.jdsw.distribute.controller;

import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.DistributeFollow;
import com.jdsw.distribute.service.TelemarkeService;
import com.jdsw.distribute.util.ImageUtil;
import com.jdsw.distribute.util.JwtUtil;
import com.jdsw.distribute.util.Message;
import com.jdsw.distribute.util.VideoUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public Message armyListPoolList(int pageNum, int limit, String content, String strtime, String endtime,HttpServletRequest request){
        //String username = (String) request.getAttribute("username");
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        return Message.success("操作成功",telemarkService.armyListPoolList(pageNum,limit,content,strtime,endtime,username),0);
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
        return Message.success("查询成功",telemarkService.grabbingPool(mapl));
    }
    /**
     * 新增
     * @param distribute
     * @return
     */
    @RequestMapping("/insertTelemarke")
    public Message insertTelemarke(@RequestBody Distribute distribute,HttpServletRequest request){
        //String username = (String) request.getAttribute("username");
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        int i = telemarkService.insertTelemarke(distribute,username,name);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }
    /**
     * 导入电销线索
     * @param file
     * @return
     */
    @RequestMapping("/excelTelemarke")
    public Message excelTelemarke(@RequestParam("file") MultipartFile file,HttpServletRequest request,@RequestParam("userName")String username) throws Exception {
        int i = telemarkService.excelTelemarke(file,username);
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
    @RequestMapping(value = "/deleteTelemarke",method = RequestMethod.POST,produces="application/json")
    public Message deleteTelemarke(@RequestBody Distribute distribute){
        int i = telemarkService.deleteTelemarke(distribute);
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
    @RequestMapping(value = "/updateTelemarke",method = RequestMethod.POST,produces="application/json")
    public Message updateTelemarke(@RequestBody Distribute distribute){
        int i = telemarkService.updateTelemarke(distribute);
        if (i > 0){
            return  Message.success();
        }
        return  Message.fail();
    }
    /**
     * 线索分发
     * @param
     * @return
     */
    @RequestMapping("/appoint")
    public Message appoint(@RequestBody List<Distribute> network,HttpServletRequest request){
        //String name = (String) request.getAttribute("name");
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
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
    public Message queryTelemarkeByLastName(HttpServletRequest request,int pageNum, int limit,String content, String strtime, String endtime){
        //String lastFollowName = (String) request.getAttribute("name");
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String lastFollowName = (String) map.get("name");
        return Message.success("操作成功",telemarkService.queryTelemarkeByLastName(pageNum,limit,content,strtime,endtime,lastFollowName,username),0);
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
        //String lastFollowName = (String) request.getAttribute("name");
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String lastFollowName = (String) map.get("name");
        return Message.success("操作成功",telemarkService.pendingNetworkList(pageNum,limit,content,strtime,endtime,lastFollowName),0);
    }
    /**
     * 抢单接口
     * @return
     */
    @RequestMapping("/orderTaking")
    public Message orderTaking(HttpServletRequest request,@RequestBody Distribute distribute){
        //String lastFollowName = (String) request.getAttribute("name");
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String lastFollowName = (String) map.get("name");
        distribute.setLastFollowName(lastFollowName);
        int i = telemarkService.orderTaking(distribute,lastFollowName);
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
    public Message followupNetwork(@RequestBody DistributeFollow networkFollow,HttpServletRequest request){
        //String name = (String) request.getAttribute("name");
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        networkFollow.setFollowName(name);
        int i = telemarkService.followupNetwork(networkFollow);
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
    public Message  uploadImg(@RequestParam("img") MultipartFile[] img, HttpServletRequest request, @RequestParam("trackId")String trackId){
        String uploadPathDB=null;
        Map map=new HashMap();
        try {
            uploadPathDB= ImageUtil.saveImage(trackId,img,"LJ");
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
    public Message  uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("trackId")String trackId){
        Map map=new HashMap();
        String uploadPathDB=null;
        try {
            uploadPathDB= VideoUtil.saveVideo(trackId,file,"LJ");
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
    @RequestMapping("/submitRecordingNetwork")
    public Message submitRecordingNetwork(@RequestBody List<Distribute> network){
        int i = telemarkService.SubmitRecordingNetwork(network);
        if (i > 0){
            return Message.success("提交成功");
        }
        return Message.fail("提交失败");
    }
    /**
     * 主管转发
     * @param distribute
     * @return
     */
    @RequestMapping("/transferTelemarke")
    public Message assign(@RequestBody List<Distribute> distribute,HttpServletRequest request){
        //String username = (String) request.getAttribute("username");
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        int i= telemarkService.assign(distribute,username,name);
        if(i > 0){
            return Message.success();
        }
        return Message.fail();
    }
    /**
     * 客服转交
     * @param
     * @return
     */
    @RequestMapping("/customerTransfer")
    public Message customerTransfer(@RequestBody List<Distribute> distribute,HttpServletRequest request){
        //String username = (String) request.getAttribute("username");
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        int i = telemarkService.customerTransfer(distribute,username,name);
        if (i > 0 ){
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
        return Message.success("操作成功",telemarkService.qureyFollowList(id));
    }

    /**
     * 强制超时
     * @param distribute
     * @return
     */
    @RequestMapping("/setOvertime")
    public Message setOvertime(@RequestBody Distribute distribute){
        telemarkService.setOvertime(distribute);
        return Message.success();
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
        return Message.success("查询成功",telemarkService.statusList(pageNum,limit,status,name));
    }

}
