package fongff.controller;

import fongff.model.SysFunc;
import fongff.model.User;
import fongff.security.UserDetialsServiceImpl;
import fongff.service.SysFuncService;
import fongff.service.SysModuleService;
import fongff.serviceImpl.UserServiceImpl;
import fongff.util.CommonUtil;
import fongff.util.UploadUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/action")
public class Action {
    @Autowired
    private UploadUtil uploadUtil;
    @Value("${news.defaultPath}")
    private String newsDefultImg;

    @Autowired
    private SysFuncService sysFuncService;
    @Autowired
    private SysModuleService sysModuleService;
    @Autowired
    private UserDetialsServiceImpl userDetialsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation("登入")
    @PostMapping("/login")
    public String login(@RequestParam String userName, @RequestParam String userPassword, Model model) {
        Authentication authAfterSuccessLogin = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        SecurityContextHolder.getContext().setAuthentication(authAfterSuccessLogin);
        return "redirect:/dashboard/module/index";
    }

    @ApiOperation("註冊")
    @PostMapping("/register")
    public String register(@RequestParam String userName, @RequestParam String userPassword, @RequestParam String nickName,
                           @RequestParam String userRole, HttpSession session) {
        List<String> validateErrors = new ArrayList<>();

        if (userServiceImpl.isUserExist(userName)) {
            validateErrors.add("userName '" + userName + "' is exist");
        }

        if("".equals(nickName)){
            validateErrors.add("nickName is empty");
        }
        List<String> allUserRoles = Stream.of(User.Role.values()).map(role -> role.toString())
                .collect(Collectors.toList());

        if (!validateErrors.isEmpty()) {
            Map<String, Object> errorMsg = new LinkedHashMap<>();
            errorMsg.put("validateErrors", validateErrors);
            return "404";
        }

        User user = new User();
        user.setName(userName);
        user.setPassword(passwordEncoder.encode(userPassword));
        user.setRole(User.Role.valueOf(userRole));
        user.setNickname(nickName);
        userServiceImpl.addUser(user);
        return "redirect:/dashboard/module/members";
    }

    @ApiOperation("新增物件內容")
    @PostMapping("/addContent")
    public String addContent(@RequestParam String module,
                             @RequestParam String title, @RequestParam String category,
                             @RequestParam String states, @RequestParam String content,
                             @RequestParam String url, @RequestParam String remark,
                             @RequestParam(required = false) MultipartFile file, Model model, Principal principal) throws IOException {

        Integer indexR = sysFuncService.getLastIndexR();
        SysFunc sysFunc = new SysFunc();
        sysFunc.setIndexR(indexR);
        sysFunc.setModule(module);
        String filePath = "";
        if (file.getBytes().length > 0) {
            /* 檔案上傳 */
            filePath = uploadUtil.uploadFile(indexR, module, file);
        } else {
            /* 如果前端沒有上傳file就使用前一張圖 */
            SysFunc sysFuncOld = sysFuncService.findOne(indexR);
            if (category.equals("news")) {
                filePath = CommonUtil.isNull(sysFuncOld.getImage()) ? sysFuncOld.getImage() : newsDefultImg;
            }
        }
        sysFunc.setImage(filePath);
        sysFunc.setUrl(url);
        sysFunc.setTitle(title);
        sysFunc.setContent(content);
        sysFunc.setStates(Integer.valueOf(states));
        sysFunc.setAuth(principal.getName());
        sysFunc.setPostDate(new Date());
        sysFunc.setCategory(category);
        sysFunc.setRemark(remark);
        sysFuncService.save(sysFunc);

        return "redirect:/dashboard/module/" + module;
    }

    @ApiOperation("更改物件內容")
    @PostMapping("/updateContent")
    public String updateContent(@RequestParam String module, @RequestParam Integer indexR,
                                @RequestParam String title, @RequestParam String category,
                                @RequestParam String states, @RequestParam String content,
                                @RequestParam String url, @RequestParam String remark,
                                @RequestParam(required = false) MultipartFile file, Model model, Principal principal) throws IOException {

        SysFunc sysFunc = new SysFunc();
        sysFunc.setModule(module);
        sysFunc.setIndexR(indexR);
        String filePath = "";
        if (file.getBytes().length > 0) {
            /* 檔案上傳 */
            filePath = uploadUtil.uploadFile(indexR, module, file);
        } else {
            /* 如果前端沒有上傳file就使用前一張圖 */
            SysFunc sysFuncOld = sysFuncService.findOne(indexR);
            filePath = sysFuncOld.getImage();
        }
        sysFunc.setImage(filePath);
        sysFunc.setUrl(url);
        sysFunc.setTitle(title);
        sysFunc.setContent(content);
        sysFunc.setStates(Integer.valueOf(states));
        sysFunc.setAuth(principal.getName());
        sysFunc.setPostDate(new Date());
        sysFunc.setCategory(category);
        sysFunc.setRemark(remark);
        sysFuncService.save(sysFunc);

        return "redirect:/dashboard/module/" + module;
    }

    @ApiOperation("刪除物件內容")
    @PostMapping("/deleteContent")
    public String deleteContent(@RequestParam Integer indexR, @RequestParam String module, HttpServletRequest request) throws IOException {
        SysFunc sysFunc = sysFuncService.findOne(indexR);
        sysFuncService.delete(sysFunc);
        return "redirect:/dashboard/module/" + module;
    }
}
