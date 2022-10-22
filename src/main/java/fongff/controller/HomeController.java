package fongff.controller;

import fongff.model.SysFunc;
import fongff.model.SysModule;
import fongff.model.User;
import fongff.service.SysFuncService;
import fongff.service.SysModuleService;
import fongff.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dashboard")
public class HomeController {
    @Autowired
    private SysFuncService sysFuncService;
    @Autowired
    private SysModuleService sysModuleService;
    @Autowired
    private UserServiceImpl userService;
    @GetMapping("/module")
    String index( Model model) {
        List<SysFunc> sysFuncList = sysFuncService.findByModule("A01");
        List<SysModule> sysModuleList = sysModuleService.findAll();
        model.addAttribute("sysFuncList", sysFuncList);
        model.addAttribute("sysModuleList", sysModuleList);
        return "fongff-content";
    }

    @GetMapping("/module/{moduleId}")
    String getModule(Model model , @PathVariable String moduleId) {
        List<SysFunc> sysFuncList = sysFuncService.findByModule(moduleId);
        List<SysModule> sysModuleList = sysModuleService.findAll();
        SysModule sysModule = sysModuleService.findOne(moduleId);

        model.addAttribute("sysModule", sysModule);
        model.addAttribute("sysFuncList", sysFuncList);
        model.addAttribute("sysModuleList", sysModuleList);
        return "fongff-content";
    }

    @GetMapping("/login")
    String login(Model model ,Principal principal) {
        return "login";
    }

    @GetMapping("/logout")
    String logout() {
        SecurityContextHolder.clearContext();
        return "login";
    }

    @GetMapping("/module/register")
    String register(Model model) {
        List<SysModule> sysModuleList = sysModuleService.findAll();
        model.addAttribute("sysModuleList", sysModuleList);
        return "register";
    }
    @GetMapping("/module/members")
    String members(Model model) {
        List<SysModule> sysModuleList = sysModuleService.findAll();
        List<User> allUsers = userService.findAllUsers();

        model.addAttribute("memberList", allUsers);
        model.addAttribute("sysModuleList", sysModuleList);
        return "members";
    }
}