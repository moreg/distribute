package com.mys.meisiniu;

import com.mys.meisiniu.model.User;
import com.mys.meisiniu.service.MenuService;
import com.mys.meisiniu.service.UserService;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
    private RepositoryService repositoryService;

    @Test
    public void contextLoads() {
    }
    @Test
    public void create(){

        User u = us.findByUserName("wangwu");
        String password = "123456";
        String hashAlgorithmName = "md5";//加密方式
        ByteSource salt = ByteSource.Util.bytes(u.getUsername());//以账号作为盐值
        int hashIterations = 2;//加密2次
        Object result = new SimpleHash(hashAlgorithmName,password,salt,hashIterations);
        System.out.println(u.getUsername()+":"+result);

    }
    @Test
    public void menu(){
        System.out.println( ms.findTree("user"));

    }
    @Test
    public void findByUsername() {

        System.out.println( us.findByUserName("user"));
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
}
