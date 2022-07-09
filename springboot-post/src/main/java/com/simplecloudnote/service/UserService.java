package com.simplecloudnote.service;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.simplecloudnote.dao.UserDao;
import com.simplecloudnote.entity.User;
import com.simplecloudnote.utils.ReturnResult;
import com.simplecloudnote.utils.UploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**
 * @author 十玖八柒
 * @date 2022
 * @GitHub https://github.com/ooahz
 * @desc 用户注册登录及信息的更新
 */
@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private LogService logService;

    @Value("${upload.enable}")
    private String enable;
    //    读取配置文件设置的地址
    @Value("${upload.path}")
    private String filePath;

    //    头像文件名
    private final String NAME = "avatar.png";

    /**
     * 注册
     *
     * @param getUser
     * @return
     */
    public String register(User getUser, HttpServletRequest request) {
        if (ObjectUtils.isEmpty(getUser)) {
            return ReturnResult.result("false", "信息填写有误");
        }
        String userCode = getUser.getUserCode();
        String username = getUser.getUsername();
        String password = getUser.getPassword();
        Digester digester = new Digester(DigestAlgorithm.SHA256);
        String salt = HexUtil.encodeHexStr(RandomUtil.randomBytes(11));
        String digestHex = digester.digestHex(password + salt);
        LocalDate now = LocalDate.now();

        String path = request.getServletContext().getRealPath("/upload/" + getUser.getUserCode());
//        若配置文件中设置了上传地址，则以配置文件为主
        if (ObjectUtil.isNotEmpty(filePath)) {
            path = filePath;
        }

        User user = new User();
        user.setId(String.valueOf(System.currentTimeMillis()));
        user.setUsername(username);
        user.setPassword(digestHex);
        user.setSalt(salt);
        user.setUserCode(userCode);
        user.setCreateDate(now);
        user.setUpdateDate(now);
        user.setUserStatus("1");
        user.setUserMark("1");
        user.setAvatar(path + File.separator + NAME);

        User userByCode = userDao.getUserByCode(userCode);
        if (ObjectUtil.isNotEmpty(userByCode)) {
            return ReturnResult.result("true", "账号已存在，请直接登录");
        }

        userDao.insertUser(user);
        return ReturnResult.result("true", "注册成功");
    }

    /**
     * 登录
     *
     * @param getUser
     * @param request
     * @return
     */
    public HashMap<String, Object> login(User getUser, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        String result;

        if (ObjectUtils.isEmpty(getUser)) {
            result = ReturnResult.result("false", "信息填写有误");
            map.put("result", result);
            return map;
        }
        String userCode = getUser.getUserCode();
        String password = getUser.getPassword();
        User user = userDao.getUserByCode(userCode);
        if (null == user) {
            result = ReturnResult.result("false", "账号或密码错误");
            map.put("result", result);
            return map;
        }
        if (!user.getUserStatus().equals("1")) {
            logService.save(userCode, "用户状态异常：" + user.getUserStatus());
            result = ReturnResult.result("false", "用户状态异常");
            map.put("result", result);
            return map;
        }
        String relPassword = user.getPassword();
        String salt = user.getSalt();

        Digester digester = new Digester(DigestAlgorithm.SHA256);
        String digestPassword = digester.digestHex(password + salt);

        if (relPassword.equals(digestPassword)) {

            User user1 = new User();
            user1.setUserCode(user.getUserCode());
            user1.setAvatar(user.getAvatar());
            user1.setUsername(user.getUsername());
            user1.setUserStatus(user.getUserStatus());
            user1.setUserMark(user.getUserMark());
            request.getSession().setAttribute("user", user1);

            result = ReturnResult.result("true", "登录成功");
            map.put("user", user1);
            map.put("result", result);
            return map;
        }
        result = ReturnResult.result("false", "账号或密码错误");
        map.put("result", result);
        return map;
    }

    /**
     * 用户信息更新
     *
     * @param user
     * @return
     */
    public String update(User user, HttpServletRequest request) {
        if (!ObjectUtil.isEmpty(user) && null != user.getUserCode()) {
            String path = request.getServletContext().getRealPath("/upload/" + user.getUserCode());
//        若配置文件中设置了上传地址，则以配置文件为主
            if (ObjectUtil.isNotEmpty(filePath)) {
                path = filePath;
            }

            String password = user.getPassword();
            Digester digester = new Digester(DigestAlgorithm.SHA256);
            String salt = HexUtil.encodeHexStr(RandomUtil.randomBytes(11));
            String digestHex = digester.digestHex(password + salt);
            user.setPassword(digestHex);
            user.setSalt(salt);
            user.setUpdateDate(LocalDate.now());
            user.setAvatar(path + File.separator + NAME);

            userDao.updateUser(user);

            User user1 = new User();
            user1.setUserCode(user.getUserCode());
            user1.setAvatar(path + File.separator + NAME);
            user1.setUsername(user.getUsername());
            user1.setUserStatus(user.getUserStatus());
            user1.setUserMark(user.getUserMark());
            request.getSession().setAttribute("user", user1);

            logService.save(user.getUserCode(), "更新用户信息成功");
            return ReturnResult.result("true", "更新成功！");
        }
        logService.save(user.getUserCode(), "更新用户信息失败");
        return ReturnResult.result("false", "更新失败！");
    }

    /**
     * 修改指定账户状态
     * @param tempUser
     * @param request
     * @return
     */
    public String changeState(User tempUser,HttpServletRequest request) {
        String userCode = tempUser.getUserCode();
        User user = userDao.getUserByCode(userCode);
        String userStatus = "1".equals(user.getUserStatus()) ? "0" : "1";
        user.setUserStatus(userStatus);
        User loginUser = (User) request.getSession().getAttribute("user");
        logService.save(loginUser.getUserCode(), "更新用户 "+user.getUserCode()+" 状态信息成功");
        return ReturnResult.result("true", "更新成功！");
    }

    /**
     * 获取用户列表
     *
     * @return
     */
    public List<User> findList() {
        List<User> userList = userDao.findUserList();
        return userList;
    }

    public void logout(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (ObjectUtil.isNotEmpty(user)) {
            request.getSession().removeAttribute("user");
            logger.info("用户：{}登出", user.getUserCode());
        }
    }

    /**
     * 文件上传
     *
     * @param file
     * @param request
     * @return
     */
    public String upload(MultipartFile file, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");

        String path = request.getServletContext().getRealPath("/upload/" + user.getUserCode());
//        若配置文件中设置了上传地址，则以配置文件为主
        if ("true".equals(enable)) {
            path = filePath;
        }
        String result = UploadUtil.toUpload(file, path, NAME);
        return result;
    }

}
