package com.jdsw.distribute.controller;

import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.DistributeFollow;
import com.jdsw.distribute.service.TelemarkeService;
import com.jdsw.distribute.util.*;
import com.jdsw.distribute.vo.InsertVo;
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
    public Message armyListPoolList(int pageNum, int limit, String content, String strtime, String endtime,HttpServletRequest request,Integer issue){
        String username = (String) request.getAttribute("username");
        String name = (String) request.getAttribute("name");
        Map map1 = new HashMap();
        map1.put("username",username);
        map1.put("pageNum",pageNum);
        map1.put("limit",limit);
        map1.put("content",content);
        map1.put("strtime",strtime);
        map1.put("endtime",endtime);
        map1.put("issue",issue);
        map1.put("name",name);
        return Message.success("操作成功",telemarkService.armyListPoolList(map1),0);
    }
    /**
     * 抢单池
     * @return
     */
    @RequestMapping("/grabbingPool")
    public Message grabbingPool(int pageNum, int limit, String content, String strtime, String endtime, Distribute network, HttpServletRequest request, HttpServletResponse response){
        String username = (String) request.getAttribute("username");
        String name = (String) request.getAttribute("name");
        Map mapl = new HashMap();
        mapl.put("pageNum",pageNum);
        mapl.put("limit",limit);
        mapl.put("content",content);
        mapl.put("strtime",strtime);
        mapl.put("endtime",endtime);
        mapl.put("username",username);
        return Message.success("查询成功",telemarkService.grabbingPool(mapl));
    }
    /**
     * 新增
     * @param
     * @return
     */
    @RequestMapping("/insertTelemarke")
    public Message insertTelemarke(@RequestBody Distribute distribute, HttpServletRequest request){
        String username = (String) request.getAttribute("username");
        String name = (String) request.getAttribute("name");
        return Message.success("新增成功",telemarkService.insertTelemarke(distribute,username,name));

    }
    /**
     * 导入电销线索
     * @param file
     * @return
     */
    @RequestMapping("/excelTelemarke")
    public Message excelTelemarke(@RequestParam("file") MultipartFile file,HttpServletRequest request) throws Exception {
        String username = (String) request.getAttribute("username");
        String name = (String) request.getAttribute("name");
        int i = telemarkService.excelTelemarke(file,username,name);
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
     * @param
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
     * 编辑弹窗
     * @param
     * @return
     */
    @RequestMapping(value = "/updateTelemarkePop")
    public Message updateTelemarkePop(Integer id){

       return Message.success("查询成功",telemarkService.updateTelemarkePop(id));
    }
    /**
     * 线索分发
     * @param
     * @return
     */
    @RequestMapping("/appoint")
    public Message appoint(@RequestBody List<Distribute> network,HttpServletRequest request){
        String username = (String) request.getAttribute("username");
        String name = (String) request.getAttribute("name");
        int i = telemarkService.appoint(network,name);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }
    /**
     * 客户池
     * @return
     */
    @RequestMapping("/queryTelemarkeByLastName")
    public Message queryTelemarkeByLastName(HttpServletRequest request,int pageNum, int limit,String content, String strtime, String endtime){
        String username = (String) request.getAttribute("username");
        String lastFollowName = (String) request.getAttribute("name");
        Map map1 = new HashMap();
        map1.put("username",username);
        map1.put("lastFollowName",lastFollowName);
        map1.put("pageNum",pageNum);
        map1.put("limit",limit);
        map1.put("content",content);
        map1.put("strtime",strtime);
        map1.put("endtime",endtime);
        return Message.success("操作成功",telemarkService.queryTelemarkeByLastName(map1),0);
    }
    /**
     * 抢单接口
     * @return
     */
    @RequestMapping("/orderTaking")
    public Message orderTaking(HttpServletRequest request,@RequestBody Distribute distribute){
        String username = (String) request.getAttribute("username");
        String lastFollowName = (String) request.getAttribute("name");
        String role = (String) request.getAttribute("role");
        distribute.setLastFollowName(lastFollowName);
        int i = telemarkService.orderTaking(distribute,lastFollowName,role);
        if (i > 0){
            return Message.success("抢单成功");
        }
        return Message.fail("抢单失败");
    }
    /**
     * 写跟进
     * @return
     */
    @RequestMapping("/followupNetwork")
    public Message followupNetwork(@RequestBody DistributeFollow networkFollow,HttpServletRequest request){
        String username = (String) request.getAttribute("username");
        String name = (String) request.getAttribute("name");
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
     * 新建上传图片
     * @param img
     * @param request
     * @return
     */
    @RequestMapping("/uploadImgNew")
    public Message  uploadImgNew(@RequestParam("img") MultipartFile[] img,HttpServletRequest request){
        String uploadPathDB=null;
        String trackId = Rand.getTrackId("L");//获得跟踪单号
        Map map=new HashMap();
        try {
            uploadPathDB= ImageUtil.saveImage(trackId,img,"L");
        }catch (IOException e){
            e.printStackTrace();
            return Message.fail("上传失败");
        }
        map.put("imgUrl",uploadPathDB);
        map.put("trackId",trackId);
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
        String username = (String) request.getAttribute("username");
        String name = (String) request.getAttribute("name");
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
        String username = (String) request.getAttribute("username");
        String name = (String) request.getAttribute("name");
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
        String username = (String) request.getAttribute("username");
        String name = (String) request.getAttribute("name");
        return Message.success("查询成功",telemarkService.statusList(pageNum,limit,status,name));
    }

}
