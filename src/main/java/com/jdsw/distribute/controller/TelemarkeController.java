package com.jdsw.distribute.controller;

import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.DistributeFollow;
import com.jdsw.distribute.service.TelemarkeService;
import com.jdsw.distribute.util.ImageUtil;
import com.jdsw.distribute.util.Message;
import com.jdsw.distribute.util.VideoUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    public Message armyListPoolList(int pageNum, int limit, String content, String strtime, String endtime){
        return Message.success("操作成功",telemarkService.armyListPoolList(pageNum,limit,content,strtime,endtime),0);
    }
    /**
     * 新增
     * @param distribute
     * @return
     */
    @RequestMapping("/insertTelemarke")
    public Message insertTelemarke(@RequestBody Distribute distribute){
        int i = telemarkService.insertTelemarke(distribute);
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
    public Message excelTelemarke(@RequestParam("file") MultipartFile file) throws Exception {
        int i = telemarkService.excelTelemarke(file);
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
     * 编辑获取客户资料接口
     * @param id
     * @return
     */
    @RequestMapping("/qureyTelemarke")
    public Message qureyTelemarke(Integer id){
        return Message.success("操作成功",telemarkService.qureyTelemarke(id));
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
     * 线索分发
     * @param
     * @return
     */
    @RequestMapping("/appoint")
    public Message appoint(@RequestBody List<Distribute> network, HttpSession session){
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
     * 上传图片
     * @param img
     * @param request
     * @return
     */
    @RequestMapping("/uploadImg")
    public Message  uploadImg(@RequestParam("img") MultipartFile[] img, HttpServletRequest request, @RequestParam("id")Integer id){
        String uploadPathDB=null;
        Map map=new HashMap();
        try {
            uploadPathDB= ImageUtil.saveImage(id,img,"DX");
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
            uploadPathDB= VideoUtil.saveVideo(id,file,"DX");
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
    @RequestMapping("/transferTelemarke")
    public Message assign(@RequestBody List<Distribute> distribute){
        int i= telemarkService.assign(distribute);
        if(i > 0){
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
        return Message.success("操作成功",telemarkService.customerTransfer(distribute),0);
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
        return Message.success("操作成功",telemarkService.cashierListNetwork(pageNum,limit,content,strtime,endtime),0);
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
