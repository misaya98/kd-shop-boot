package com.shop.controller;


import com.shop.ex.PhoneNotFoundException;
import com.shop.ex.ServiceException;
import com.shop.ex.UsernameTakenException;
import com.shop.pojo.Address;
import com.shop.pojo.Comments;
import com.shop.pojo.CommentsExtend;
import com.shop.pojo.Goods;
import com.shop.pojo.GoodsExtend;
import com.shop.pojo.Image;
import com.shop.pojo.Orders;
import com.shop.pojo.OrdersExtend;
import com.shop.pojo.User;
import com.shop.pojo.Wanted;
import com.shop.pojo.WantedExtend;
import com.shop.service.AddressService;
import com.shop.service.CommentsService;
import com.shop.service.GoodsService;
import com.shop.service.ImageService;
import com.shop.service.OrdersService;
import com.shop.service.UserService;
import com.shop.service.WantedService;
import com.shop.util.MD5;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private CommentsService commentsService;

    @Resource
    private WantedService wantedService;

    @Resource
    private com.shop.controller.HomeController homeController;

    @Resource
    private GoodsService goodsService;

    @Resource
    private ImageService imageService;

    @Resource
    private UserService userService;

    @Resource
    private AddressService addressService;

    @Resource
    private OrdersService ordersService;

    //??????????????????????????????
    private String loginReferer;

    /**
     * ??????????????????????????????
     *
     * @return ??????????????????????????????
     */
    @RequestMapping(value = "/forget_password")
    public String forget() {
        return "/user/forget";
    }

    /**
     * ????????????????????????
     *
     * @param session      ?????????????????????
     * @param phone_number ?????????????????????
     * @param password     ?????????????????????????????????
     * @param captchaCode  ???????????????
     * @return
     */
    @RequestMapping(value = "/forget")
    public @ResponseBody
    Map<String, Object> forgetPassword(HttpSession session,
                                       @RequestParam(value = "phone_number", required = false) String phone_number,
                                       @RequestParam(value = "password", required = false) String password,
                                       @RequestParam(value = "captchaCode", required = false) String captchaCode) {
        System.out.println("phone_number:" + phone_number + ", password:" + password + ", captchaCode:" + captchaCode);
        String captchaCheckCode = (String) session.getAttribute("number");
        User user = userService.getUserByPhone(phone_number);
        Map<String, Object> map = new HashMap<>();
        //???????????????
        if (captchaCheckCode.equals(captchaCode)) {
            //???????????????????????????
            if (user != null) {
                //???????????????????????????????????????
                userService.updatePasswordByPrimaryKey(user.getId(), MD5.md5(password));
                //???????????????
                map.put("success", true);
                map.put("msg", "????????????");
            } else {
                map.put("success", false);
                map.put("msg", "??????????????????");
            }
        } else {
            map.put("success", false);
            map.put("msg", "??????????????????");
        }
        return map;
    }

    /**
     * ???????????????????????????
     *
     * @param session  ??????????????????
     * @param phonenum ??????????????????????????????
     * @param captcha  ???????????????
     * @param type     ???????????????????????????????????????
     * @return ????????????
     */
    @RequestMapping(value = "/get_verify_code", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getVerifyCode(HttpSession session,
                                      @RequestParam(value = "phonenum") String phonenum,
                                      @RequestParam(value = "captcha") String captcha,
                                      @RequestParam(value = "type") String type) {
        String captCode = (String) session.getAttribute("number");
        Map<String, Object> map = new HashMap<String, Object>();
        if (captcha.equals(captcha) && type.equals("1")) {
            //????????????????????????
            Integer count = userService.checkUserByPhone(phonenum);
            if (count > 0) {
                System.out.println("phonenum:" + phonenum + ", captcha:" + captcha + ", type:" + type);

                //???????????????


                //??????????????????
                map.put("success", true);
                map.put("msg", "????????????");
            } else {
                map.put("success", false);
                map.put("msg", "???????????????");
            }
        } else {
            //
            map.put("success", false);
            map.put("msg", "??????????????????");
        }
        return map;
    }

    /**
     * ????????????????????????
     *
     * @param request ????????????
     * @return
     */
    @GetMapping("/toLogin")
    public String toLogin(HttpServletRequest request) {

        System.out.println(request.getHeader("Referer"));
        //??????????????????????????????????????????????????????
        if ("http://localhost:8088/user/forget_password".equals(request.getHeader("Referer"))
                || "http://localhost:8088/user/register".equals(request.getHeader("Referer"))
                || "http://localhost:8088/user/toRegister".equals(request.getHeader("Referer"))) {
            loginReferer = "http://localhost:8080/goods/index";
        } else {
            loginReferer = request.getHeader("Referer");
        }
        return "/user/login";
    }

    /**
     * ??????????????????
     *
     * @param session ???????????????????????????session??????
     * @return ????????????
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public Map<String, Object> checkLogin(
            HttpSession session,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "password", required = false) String password) {
        Map<String, Object> map = new HashMap<>();
        try {
            //?????????????????????????????????
            User cur_user = userService.getUserByPhone(phone);
            //??????????????????
            if (cur_user != null) {
                //??????????????????
                String word = MD5.md5(password);
                if (cur_user.getPassword().equals(word)) {
                    //????????????????????????????????????
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cur_user.setLastLogin(sdf.format(new Date()));
                    userService.updateLastLoginByPrimaryKey(cur_user);
                    //???session???????????????????????????
                    session.setAttribute("cur_user", cur_user);
                    //????????????
                    map.put("success", true);
                    if(StringUtils.hasText(loginReferer)) {
                        map.put("reurl", loginReferer);
                    }
                    map.put("msg", "????????????");
                } else {
                    //???????????????
                    map.put("err", true);
                    map.put("msg", "????????????,????????????");
                }
            }
        } catch (PhoneNotFoundException e) {
            //???????????????
            //??????????????????
            map.put("success", false);
            map.put("msg", e.getMessage());
            return map;
        }
        return map;
    }

    /**
     * ????????????????????????????????????????????????
     *
     * @param phone ????????????????????????????????????
     * @return ???????????????????????????????????????
     */
    @RequestMapping(value = "/checkPhone")
    public @ResponseBody
    Map<String, Object> checkPhone(
            @RequestParam(value = "phone", required = false) String phone) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            User user = userService.getUserByPhone(phone);
            map.put("message", "??????????????????");
            return map;
        } catch (ServiceException e) {
            map.put("message", e.getMessage());
            return map;
        }

    }

    /**
     * @param session   ???????????????????????????session
     * @param checkcode ??????????????????????????????
     * @return ??????????????????
     */
    @RequestMapping(value = "/checkCode")
    @ResponseBody
    public ModelMap checkCode(HttpSession session, String checkcode) {
        String checkCode = (String) session.getAttribute("number");

        ModelMap map = new ModelMap();
        if (checkCode.equals(checkcode)) {
            map.put("success", true);
            map.put("msg", "???????????????");
        } else {
            map.put("success", false);
            map.put("msg", "??????????????????");
        }
        return map;
    }


    /**
     * ????????????????????????
     *
     * @return ????????????????????????
     */
    @RequestMapping(value = "/toRegister")
    public String register() {
        return "/user/register";
    }

    /**
     * ??????????????????
     *
     * @param user      ??????????????????
     * @param session   session
     * @param checkcode ?????????
     * @return ??????????????????
     */
    @RequestMapping(value = "/register")
    @ResponseBody
    public ModelMap handleRegister(User user, HttpSession session, String checkcode) {

        String checkCode = (String) session.getAttribute("number");
        ModelMap map = new ModelMap();
        //???????????????
        if (checkCode.equals(checkcode)) {
            try {
                //???????????????????????????
                user.setUsername(user.getPhone());
                user.setPassword(MD5.md5(user.getPassword()));
                //??????????????????
                userService.insert(user);
                //????????????????????????
                map.addAttribute("success", true);
                map.addAttribute("msg", "????????????");
                return map;
            } catch (UsernameTakenException e) {
                //????????????
                map.addAttribute("error", true);
                map.addAttribute("msg", e.getMessage());
                return map;
            }
        } else {
            //?????????????????????????????????
            map.addAttribute("error", true);
            map.addAttribute("msg", "???????????????");
            return map;
        }


    }

    /**
     * ????????????????????????????????????
     *
     * @return
     */
    @RequestMapping(value = "/userinfo")
    public String userinfo() {

        return "/user/userinfo";
    }

    /**
     * ????????????????????????????????????
     *
     * @return
     */
    @RequestMapping(value = "/message")
    public ModelAndView message(HttpSession session) {
        User user = (User) session.getAttribute("cur_user");
        ModelAndView modelAndView = new ModelAndView();
        if (user == null) {
            modelAndView.setViewName("redirect:/user/login");
            return modelAndView;
        }
        modelAndView.setViewName("/user/message");
        return modelAndView;
    }

    /**
     * ???????????????????????????
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "received")
    public @ResponseBody
    Map<String, Object> received(HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = (User) session.getAttribute("cur_user");
        //???????????? ???????????????
        List<Goods> goods = goodsService.getGoodsByUserId(user.getId());
        List<Comments> commentsList = new ArrayList<Comments>();
        for (Goods good : goods) {
            List<Comments> comments1 = commentsService.selectByGoodsId(good.getId());
            if (comments1 != null) {
                commentsList.addAll(comments1);
            }
        }
        //???????????????????????????????????????????????????
        List<CommentsExtend> commentsExtendList = new ArrayList<CommentsExtend>();
        if (commentsList.size() > 0) {
            for (Comments comment : commentsList) {
                CommentsExtend commentsExtend = new CommentsExtend();
                User user1 = userService.selectByPrimaryKey(comment.getUserId());
                Goods goods1 = goodsService.selectByPrimaryKey(comment.getGoodsId());
                commentsExtend.setGoods(goods1);
                commentsExtend.setUser(user1);
                commentsExtend.setComments(comment);
                commentsExtendList.add(commentsExtend);
            }
        }
        map.put("success", true);
        map.put("result", commentsExtendList);
        return map;
    }

    /**
     * ?????????????????????
     *
     * @param session
     * @return
     */
    @RequestMapping("/published")
    public @ResponseBody
    Map<String, Object> published(HttpSession session) {
        User user = (User) session.getAttribute("cur_user");
        Map<String, Object> map = new HashMap<String, Object>();
        //???????????????????????????????????????????????????????????????
        List<CommentsExtend> commentsExtends = new ArrayList<CommentsExtend>();
        //????????????
        List<Comments> comments = commentsService.selectByUserKeyWithContent(user.getId());
        for (Comments comment : comments) {
            //???????????????CommentsExtend(?????????????????????????????????????????????)
            CommentsExtend commentsExtend = new CommentsExtend();
            User user1 = userService.selectByPrimaryKey(comment.getUserId());
            Integer goodsId = comment.getGoodsId();
            Goods goods1 = goodsService.selectByPrimaryKey(goodsId);
            commentsExtend.setGoods(goods1);
            commentsExtend.setUser(user1);
            commentsExtend.setComments(comment);
            //????????????commentsExtend??????List??????
            commentsExtends.add(commentsExtend);
        }
        map.put("success", true);
        map.put("result", commentsExtends);
        return map;
    }


    /**
     * ????????????
     *
     * @return
     */
    @RequestMapping(value = "/want")
    public ModelAndView want(HttpSession session) {
        User user = (User) session.getAttribute("cur_user");
        List<Wanted> wantedList = wantedService.selectWantByUserId(user.getId());
        List<WantedExtend> wantedExtendList = new ArrayList<WantedExtend>();
        for (Wanted wanted : wantedList) {
            WantedExtend wantedExtend = new WantedExtend();
            GoodsExtend goodsExtend = new GoodsExtend();
            //??????????????? ????????????
            Goods good = goodsService.selectByPrimaryKey(wanted.getGoodId());
            if (good != null) {
                List<Image> images = imageService.selectByGoodsPrimaryKey(good.getId());
                goodsExtend.setImages(images);
                goodsExtend.setGoods(good);
                //????????????????????????
                wantedExtend.setGoodsExtend(goodsExtend);
                wantedExtend.setWanted(wanted);
                wantedExtendList.add(wantedExtend);
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("wantedList", wantedExtendList);
        modelAndView.setViewName("/user/want");
        return modelAndView;
    }

    /**
     * ????????????????????????
     *
     * @return ????????????
     */
    @RequestMapping(value = "/help")
    public String help() {

        return "/user/help";
    }

    @RequestMapping(value = "/feedback")
    public @ResponseBody
    Map<String, Object> feedback(HttpSession session, @RequestParam(value = "content") String content) {
        System.out.println("content:" + content);
        /*
        ??????????????????
         */
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("msg", "????????????");
        return map;
    }

    /**
     * ????????????
     *
     * @param keywords ?????????
     * @return
     */
    @RequestMapping(value = "/search", produces = "text/html;charset=UTF-8")
    public ModelAndView search(@RequestParam(value = "keywords", required = false) String keywords) {

        //???????????????????????????????????????????????????
        List<Goods> goodsList = goodsService.searchGoods(keywords, keywords, (byte) 1);

        List<GoodsExtend> goodsExtendList = homeController.handlerGoodsAndView(goodsList);
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("keywords", keywords);
        modelAndView.addObject("goodsExtendList", goodsExtendList);

        modelAndView.setViewName("/search/search");
        return modelAndView;
    }

    /**
     * ????????????
     *
     * @param session session??????
     * @return
     */
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        //??????????????????????????????
        User user = (User) session.getAttribute("cur_user");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        user.setLastLogin(sdf.format(new Date()));
        userService.updateLastLoginByPrimaryKey(user);
        //????????????????????????
        session.setAttribute("cur_user", null);
        //??????????????????
        return "redirect:/goods/index";
    }

    /**
     * ??????????????????????????????
     * ???????????????????????? ???????????? ?????????????????????
     *
     * @return ?????????model??? goodsAndImage??????,??????????????????goods ??? images?????????????????????
     */
    @RequestMapping(value = "/index")
    public ModelAndView handleUserGoods(HttpServletRequest request) {
        User cur_user = (User) request.getSession().getAttribute("cur_user");
        Integer userId = cur_user.getId();
        List<Goods> goodsList = goodsService.getGoodsByUserId(userId);
        List<GoodsExtend> goodsAndImage = homeController.handlerGoodsAndView(goodsList);

        //???????????? ?????????????????????id??????
        Double income = ordersService.getIncomeByUserId(userId);
        //???????????? ??????????????????id??????
        Double spend = ordersService.getSpendByUserId(userId);

        ModelAndView mv = new ModelAndView();
        mv.addObject("goodsAndImage", goodsAndImage);
        mv.addObject("income", income);
        mv.addObject("spend", spend);
        mv.setViewName("/user/index");
        return mv;
    }


    /**
     * ???????????????????????????
     *
     * @param session  session??????
     * @param fileName ??????
     * @param id       ??????id
     * @return ????????????
     * @throws IllegalStateException
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/avatar_upload/{id}", produces = "application/json")
    public Map<String, Object> avatarUpload(HttpSession session, HttpServletRequest request,
                                            @RequestParam("avatar") MultipartFile fileName,
                                            @PathVariable("id") Integer id) throws IllegalStateException, IOException {

        User user = (User) session.getAttribute("cur_user");
        String oldFileName = fileName.getOriginalFilename(); //???????????????????????????

        //???????????????????????????
        File rootPath = new File(request.getServletContext().getRealPath("/")).getParentFile();
        File uploadFile = new File(rootPath.getPath() + "/images/user/");

        if (!uploadFile.exists()) {
            uploadFile.mkdirs();
        }


        //????????????
        if (fileName != null && oldFileName != null && oldFileName.length() > 0) {
            //??????????????????
            String newFileName = UUID.randomUUID() + oldFileName.substring(oldFileName.lastIndexOf("."));
            //????????????????????????
            userService.updateImgUrl(id, newFileName);

            User user_new = userService.selectByPrimaryKey(id);
            //?????????????????????????????????
            session.setAttribute("cur_user", user_new);

            if (user_new.getImgUrl() != null) {
                //?????????
                String url = rootPath.getPath() + "/images/user/" + newFileName;
                //?????????????????????????????????
                fileName.transferTo(new File(url));
                //?????????????????????????????????
                Map<String, Object> map = new HashMap<String, Object>();
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("src", newFileName);
                //????????????????????????????????????
                map.put("success", true);
                map.put("msg", "??????????????????");
                map.put("data", data);

                return map;
            }
            //????????????????????????
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("error", "??????????????????");
            return map;

        } else {
            //????????????????????????
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("error", "??????????????????");
            return map;
        }
    }

    /**
     * ??????????????????
     *
     * @param session
     * @param user    ????????????
     * @return
     */
    @RequestMapping(value = "/save_userinfo", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> saveUserInfo(HttpSession session, User user) {
        User sessionUser = (User) session.getAttribute("cur_user");
        Map<String, Object> map = new HashMap<String, Object>();
        if (sessionUser != null) {
            try {
                String url = user.getImgUrl();
                //????????????
                String newUrl = url.substring(url.lastIndexOf("/") + 1);
                user.setImgUrl(newUrl);
                user.setId(sessionUser.getId());
                userService.updateByPrimaryKeySelective(user);
                User cur_user = userService.selectByPrimaryKey(sessionUser.getId());
                //????????????????????????
                session.setAttribute("cur_user", cur_user);
                //???????????????????????????
                map.put("success", true);
                map.put("msg", "????????????");
            } catch (Exception e) {
                map.put("success", false);
                map.put("msg", "???????????????????????????");
                return map;
            }
        } else {
            map.put("success", false);
            map.put("msg", "????????????");
        }
        return map;
    }


    /**
     * ????????????
     *
     * @param session ????????????
     * @param gid     ??????ID
     * @return
     */
    @RequestMapping(value = "/collect")
    public @ResponseBody
    Map<String, Object> collect(HttpSession session, Integer gid) {
        User user = (User) session.getAttribute("cur_user");
        Map<String, Object> map = new HashMap<String, Object>();
        //????????????????????????
        if (user != null) {
            Wanted wanted = wantedService.selectWant(user.getId(), gid);
            System.out.println(wanted);
            if (wanted == null) {
                //??????????????????????????????????????????
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Wanted newWanted = new Wanted(1, user.getId(), gid, sdf.format(new Date()));
                wantedService.insert(newWanted);
                map.put("success", true);
                map.put("msg", "????????????");
            }
        } else {
            //???????????????
            map.put("err", true);
            map.put("msg", "????????????");
        }
        return map;
    }

    /**
     * ?????????????????????????????????
     *
     * @param gid ??????ID
     * @return
     */
    @RequestMapping(value = "/collect_delete")
    public @ResponseBody
    Map<String, Object> collectDelete(HttpSession session, @RequestParam(value = "gid") Integer gid) {
        User user = (User) session.getAttribute("cur_user");
        Map<String, Object> map = new HashMap<String, Object>();
        if (user != null) {
            //????????????id??????????????????
            wantedService.deleteWantedByGoodsId(gid);
            map.put("success", true);
            map.put("msg", "????????????");
        }
        return map;
    }

    /**
     * ???????????????????????????
     *
     * @param session
     * @param gid     ?????????ID??????
     * @param content ????????????????????????
     * @return
     */
    @RequestMapping(value = "/comment")
    public @ResponseBody
    Map<String, Object> commentPublish(HttpSession session, Integer gid, String content) {
        Map<String, Object> map = new HashMap<String, Object>();
        //??????????????????
        User user = (User) session.getAttribute("cur_user");
        try {
            //??????????????????
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Comments comments = new Comments(1, user.getId(), gid, sdf.format(new Date()), content);
            Goods goods = goodsService.selectByPrimaryKey(gid);
            goodsService.updateCommentNumByPrimaryKey(gid, goods.getCommetNum() + 1);
            commentsService.insert(comments);
            //????????????

            map.put("success", true);
            map.put("msg", "????????????");
            return map;
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "??????????????????????????????");
            return map;
        }

    }

    /**
     * ??????????????????????????????
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/buy-good/{id}")
    public ModelAndView buyGood(@PathVariable("id") Integer id, HttpSession session) {
        Goods goods = goodsService.selectByPrimaryKey(id);
        List<Image> images = imageService.selectByGoodsPrimaryKey(id);
        User user = (User) session.getAttribute("cur_user");
        List<Address> list = addressService.getAllAddressByUid(user.getId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("good", goods);
        modelAndView.addObject("image", images);
        modelAndView.addObject("listAddress", list);
        modelAndView.setViewName("/order/orderConfirm");
        return modelAndView;
    }


    /**
     * ????????????????????????
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/address")
    public ModelAndView showAddress(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) session.getAttribute("cur_user");
        System.out.println("ShowAddress-User:" + user);
        List<Address> list = addressService.getAllAddressByUid(user.getId());
        System.out.println("ShowAddress:" + list);
        modelAndView.addObject("listAddress", list);
        modelAndView.setViewName("/user/address");
        return modelAndView;
    }

    @RequestMapping(value = "/order")
    public ModelAndView order() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/user/order");
        return modelAndView;
    }

    /**
     * ????????????????????????
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/order_buy")
    public @ResponseBody
    Map<String, Object> order_buy(HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<OrdersExtend> ordersExtends = new ArrayList<OrdersExtend>();
        User user = (User) session.getAttribute("cur_user");
        List<Orders> orders = ordersService.selectOrdersByUserId(user.getId());
        if (orders.size() > 0) {
            for (Orders order : orders) {
                if (order != null) {
                    OrdersExtend ordersExtend = new OrdersExtend();
                    Goods good = goodsService.selectByPrimaryKey(order.getGoodId());
                    List<Image> images = imageService.selectByGoodsPrimaryKey(good.getId());
                    Address address = addressService.getAddressById(order.getAddressId());
                    ordersExtend.setGood(good);
                    ordersExtend.setImages(images);
                    ordersExtend.setUser(user);
                    ordersExtend.setAddress(address);
                    ordersExtend.setOrders(order);
                    ordersExtends.add(ordersExtend);
                }
            }
        }


        map.put("success", true);
        map.put("result", ordersExtends);

        return map;
    }


    /**
     * ???????????????
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/published_order")
    public @ResponseBody
    Map<String, Object> published_order(HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();

        List<OrdersExtend> ordersExtends = new ArrayList<OrdersExtend>();

        User user = (User) session.getAttribute("cur_user");

        List<Goods> goods = goodsService.getGoodsByUserId(user.getId());

        List<Orders> orders = new ArrayList<Orders>();

        for (Goods good : goods) {
            Orders order = ordersService.selectOrdersByGoodId(good.getId());
            orders.add(order);
        }
        if (orders.size() > 0) {
            for (Orders order : orders) {
                if (order != null) {
                    OrdersExtend ordersExtend = new OrdersExtend();
                    Goods good = goodsService.selectByPrimaryKey(order.getGoodId());
                    List<Image> images = imageService.selectByGoodsPrimaryKey(good.getId());
                    Address address = addressService.getAddressById(order.getAddressId());
                    ordersExtend.setGood(good);
                    ordersExtend.setImages(images);
                    ordersExtend.setUser(user);
                    ordersExtend.setAddress(address);
                    ordersExtend.setOrders(order);
                    ordersExtends.add(ordersExtend);
                }

            }
        }


        map.put("success", true);
        map.put("result", ordersExtends);

        return map;
    }

}