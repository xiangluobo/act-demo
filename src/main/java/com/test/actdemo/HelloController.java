package com.test.actdemo;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: XiaoXiangLong
 * @Desc:
 * @Date: 2020-03-10 22:28
 * @Modified By:
 */
@Controller
public class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello";
    }

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/start")
    @ResponseBody
    public String start() {

        //流程ID
        String pid = runtimeService.startProcessInstanceByKey("myProcess_1").getId();

        //查询当前流程节点
        Task task = taskService.createTaskQuery().processInstanceId(pid).singleResult();

        System.out.println("当前流程节点:" + task.getName());

        return task.getId();
    }

    @GetMapping("/complete/{taskId}")
    @ResponseBody
    public String complete(@PathVariable String taskId) {

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String pid = task.getProcessInstanceId();

        //任务往前走
        taskService.complete(task.getId());

        //查询当前流程节点
        Task currTask = taskService.createTaskQuery().processInstanceId(pid).singleResult();
        System.out.println("当前流程节点:" + currTask.getName());

        return "success";

    }

}
