package com.jdsw.distribute.controller;

import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.DistributeFollow;
import com.jdsw.distribute.service.DevelopService;
import com.jdsw.distribute.util.ImageUtil;
import com.jdsw.distribute.util.JwtUtil;
import com.jdsw.distribute.util.Message;
import com.jdsw.distribute.util.Rand;
import com.jdsw.distribute.vo.InsertVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自开发
 *
 */
@RestController
@RequestMapping("/develop")
public class DevelopController {
    @Resource
    private DevelopService developService;
    /**
     * 自开发新增
     * @param
     * @param request
     * @return
     */
    @RequestMapping("insertDevelop")
    public Message insertDevelop(@RequestBody InsertVo insertVo, HttpServletRequest request){
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        Map map1 = new HashMap();
        map1.put("name",name);
        map1.put("username",username);
        map1.put("insertVo",insertVo);
        int i = developService.insertDevelop(map1);
        if (i > 0){
            return Message.success();
        }
        return Message.fail();
    }
    /**
     * 新建上传图片
     * @param img
     * @param request
     * @return
     */
    @RequestMapping("/uploadImg")
    public Message  uploadImgNew(@RequestParam("img") MultipartFile[] img, HttpServletRequest request){
        String uploadPathDB=null;
        String trackId = Rand.getTrackId("Z");//获得跟踪单号
        Map map=new HashMap();
        try {
            uploadPathDB= ImageUtil.saveImage(trackId,img,"Z");
        }catch (IOException e){
            e.printStackTrace();
            return Message.fail("上传失败");
        }
        map.put("imgUrl",uploadPathDB);
        map.put("trackId",trackId);
        return Message.success("上传成功",map);
    }

    /**
     * 自开发客户列表
     * @param request
     * @param pageNum
     * @param limit
     * @param content
     * @param strtime
     * @param endtime
     * @return
     */
    @RequestMapping("/developList")
    public Message developList(HttpServletRequest request,int pageNum, int limit,String content, String strtime, String endtime ){
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        Map map1 = new HashMap();
        map1.put("name",name);
        map1.put("username",username);
        map1.put("pageNum",pageNum);
        map1.put("limit",limit);
        map1.put("content",content);
        map1.put("strtime",strtime);
        map1.put("endtime",endtime);
        return Message.success("查询成功",developService.developList(map1));
    }
    /**
     * 编辑
     * @param distribute
     * @return
     */
    @RequestMapping(value = "/updateDevelop",method = RequestMethod.POST,produces="application/json")
    public Message updateNetwork(@RequestBody Distribute distribute){
        int i = developService.updateDevelop(distribute);
        if (i > 0){
            return  Message.success();
        }
        return  Message.fail();
    }
    /**
     * 删除
     * @param
     * @return
     */
    @RequestMapping(value = "/deleteDevelop",method = RequestMethod.POST,produces="application/json")
    public Message deleteNetwork(@RequestBody Distribute distribute){
        int i = developService.deleteDevelop(distribute);
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
        return Message.success("查询成功",developService.qureyFollowList(id));
    }
    /**
     * 写跟进
     * @return
     */
    @RequestMapping(value = "/followupDevelop",method = RequestMethod.POST,produces="application/json")
    public Message followupDevelop(@RequestBody DistributeFollow networkFollow, HttpServletRequest request){
        //String name = (String) request.getAttribute("name");
        //String username = (String) request.getAttribute("username");
        String token = request.getHeader("token"); // 获取头中token
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        networkFollow.setFollowName(name);
        int i = developService.followupDevelop(networkFollow);
        if (i > 0){
            return Message.success();
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
        return Message.success("操作成功",developService.selectDeveolpById(id));
    }
    /**
     * 业务员提交录单
     * @param network
     * @return
     */
    @RequestMapping(value = "/submitRecordingNetwork",method = RequestMethod.POST,produces="application/json")
    public Message submitRecordingNetwork(@RequestBody List<Distribute> network){
        int i = developService.submitRecordingNetwork(network);
        if (i > 0){
            return Message.success("提交成功");
        }
        return Message.fail("提交失败");
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
        System.out.println(token);
        Map<String, Object> map = JwtUtil.parseJWT(token);
        String username = (String) map.get("userName");
        String name = (String) map.get("name");
        int i = developService.transferNetwork(distribute,name);
        if ( i > 0){
            return Message.success();
        }
        return Message.fail();
    }

}
