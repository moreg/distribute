package com.jdsw.distribute;

import com.jdsw.distribute.dao.UserDao;
import com.jdsw.distribute.model.Distribute;
import com.jdsw.distribute.model.Excel;
import com.jdsw.distribute.model.User;
import com.jdsw.distribute.service.MenuService;
import com.jdsw.distribute.service.UserService;
import com.jdsw.distribute.util.MD5Utils;
import com.jdsw.distribute.util.excelRead;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.beans.IntrospectionException;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MbackstageApplicationTests {
    @Autowired
    UserService us;
    @Autowired
    MenuService ms;
    /*@Autowired
    ActivityConsumerService acs;*/
    @Autowired
    UserDao dao;

    @Autowired
    private RepositoryService repositoryService;

    @Test
    public void contextLoads() {
    }
    @Test
    public void create(){

        //User u = us.findByUserName("pus");
        String password = "123456";
        String oldpwd = MD5Utils.loginMd5(password,"mmbXesF2SwWDChhBoJ9J332l");
        System.out.println(oldpwd);

    }
    @Test
    public void menu(){
        System.out.println( ms.findTree("user"));

    }
    @Test
    public void findByUsername() {

        //System.out.println( us.findByUserName("wangfei"));
        System.out.println(us.findPermissionByUserName("wangfei"));
       // System.out.println(us.);
    }

    /*@Test
    public void activitytest(){
        acs.startActivityDemo();
    }*/
    @Test
    public void liuc(){

    }
    @Test
    public void input_deploy(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("请假流程")
                .addClasspathResource("processes/leave.bpmn")//从classpath的资源中加载，一次只能加载一个文件
                .addClasspathResource("processes/leave.png")
                .deploy();
        System.out.println("key:"+deployment.getKey()+",id:"+deployment.getId()+",name:"+deployment.getName());
    }
    //启动实例
    @Test
    public void deploy()throws Exception{
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = defaultProcessEngine.getRuntimeService();
        ProcessInstance myProcess_1 = runtimeService.startProcessInstanceByKey("myProcess_1");
        System.out.println("流程实例id："+myProcess_1.getId());
        System.out.println("流程定义ID:"+myProcess_1.getProcessDefinitionId());


    }
    //执行
    @Test
    public void complete(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        taskService.complete("27502");

    }
    //判断流程实例是否结束
    @Test
    public void isProcessEnd() {
        String processInstanceId = "20001";
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstance pi = defaultProcessEngine.getRuntimeService()//表示正在执行的流程实例和执行对象
                .createProcessInstanceQuery()//创建流程实例查询
                .processInstanceId(processInstanceId)//使用流程实例ID查询
                .singleResult();
        if (pi == null) {
            System.out.println("流程已经结束");
            //说明流程实例结束了
            HistoricProcessInstance hpi = defaultProcessEngine.getHistoryService()
                    .createHistoricProcessInstanceQuery()
                    .processInstanceId(pi.getId())                                     //使用流程实例ID查询
                    .singleResult();
            System.out.println(hpi.getId()+"    "+hpi.getStartTime()+"   "+hpi.getEndTime()+"   "+hpi.getDurationInMillis());

        } else {
            System.out.println("流程没有结束");
        }


    }
    @Test
    public void tast(){
        // 获取任务
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery().taskAssignee("zhangsan").list();
        System.out.println("任务个数" + list.size());
        if (list != null && list.size() > 0) {
            for (Task t : list) {
                System.out.print(t.getId() + ",");
                System.out.print(t.getName() + ",");
                System.out.print(t.getAssignee() + ",");
                System.out.println(t.getProcessInstanceId());
            }
        }
    }
    //删除（使用流程定义的key）
    @Test
    public void delBykey(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<ProcessDefinition> list = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .processDefinitionKey("myProcess_1")
                .list();
        for(ProcessDefinition pd :list){
            processEngine.getRepositoryService().deleteDeployment(pd.getDeploymentId(),true);
        }
    }
    @Test
    public void random(){
        int random=(int)(Math.random()*10+1);

            //打印随机数
            System.out.println(random);


    }
    @Test
    public void psw(){
        System.out.println(md5("123456","8d78869f470951332959580424d4bf4f"));
    }
    public static final String md5(String password, String salt){
        //加密方式
        String hashAlgorithmName = "MD5";
        //盐：为了即使相同的密码不同的盐加密后的结果也不同
        ByteSource byteSalt = ByteSource.Util.bytes(salt);
        //密码
        Object source = password;
        //加密次数
        int hashIterations = 2;
        SimpleHash result = new SimpleHash(hashAlgorithmName, source, byteSalt, hashIterations);
        return result.toString();
    }

    @Test
    public void role(){
        System.out.println(dao.queryBranch("cz"));

    }
    @Test
    public void dbBackUp() throws Exception {
        //String pathSql = backPath+backName;
        File fileSql = new File("E://sql/");
        //创建备份sql文件
        if (!fileSql.exists()){
            fileSql.createNewFile();
        }
        //mysqldump -hlocalhost -uroot -p123456 db > /home/back.sql
        StringBuffer sb = new StringBuffer();
        sb.append("mysqldump");
        sb.append(" -h10.0.0.242");
        sb.append(" -u"+"root");
        sb.append(" -p"+"jdsw123456");
        sb.append(" "+"distribute"+" >");
        sb.append("E://sql/");
        System.out.println("cmd命令为："+sb.toString());
        Runtime runtime = Runtime.getRuntime();
        System.out.println("开始备份："+"distribute");
        Process process = runtime.exec("cmd /c"+sb.toString());
        System.out.println("备份成功!");
    }

}
