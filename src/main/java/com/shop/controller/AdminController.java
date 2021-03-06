package com.shop.controller;


import com.shop.ex.PhoneNotFoundException;
import com.shop.pojo.Address;
import com.shop.pojo.Carousel;
import com.shop.pojo.Catelog;
import com.shop.pojo.CatelogExtend;
import com.shop.pojo.Comments;
import com.shop.pojo.CommentsExtend;
import com.shop.pojo.Goods;
import com.shop.pojo.Image;
import com.shop.pojo.Orders;
import com.shop.pojo.OrdersExtend;
import com.shop.pojo.Report;
import com.shop.pojo.ReportExtend;
import com.shop.pojo.User;
import com.shop.service.AddressService;
import com.shop.service.CarouselService;
import com.shop.service.CatelogService;
import com.shop.service.CommentsService;
import com.shop.service.GoodsService;
import com.shop.service.ImageService;
import com.shop.service.OrdersService;
import com.shop.service.ReportService;
import com.shop.service.UserService;
import com.shop.util.MD5;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private UserService userService;

    @Resource
    private ReportService reportService;

    @Resource
    private GoodsService goodsService;

    @Resource
    private ImageService imageService;

    @Resource
    private CommentsService commentsService;

    @Resource
    private CatelogService catelogService;

    @Resource
    private CarouselService carouselService;

    @Resource
    private OrdersService ordersService;

    @Resource
    private AddressService addressService;

    @RequestMapping(value = "/toLogin")
    public String toLogin(HttpSession session) {
        session.setAttribute("admin", null);
        return "/admin/login";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> login(HttpSession session, @RequestParam(value = "phone", required = false) String phone, @RequestParam(value = "password", required = false) String password) {

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            User user = userService.getUserByPhone(phone);
            if (user != null) {
                //??????????????????
                String word = MD5.md5(password);
                if (user.getPassword().equals(word) && user.getPower() > 50) {
                    session.setAttribute("admin", user);
                    map.put("success", true);
                    map.put("reurl", "http://localhost:8088/admin/index");
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


    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.setAttribute("admin", null);
        return "/admin/login";
    }


    /**
     * ??????????????????
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String adminIndex() {
        return "/admin/index";
    }

    /**
     * ??????????????????
     *
     * @return
     */
    @RequestMapping(value = "/person_add")
    public String adminAddUser() {
        return "/admin/person_add";
    }

    /**
     * ??????????????????
     *
     * @return
     */
    @RequestMapping(value = "/main")
    public ModelAndView adminMain() {
        List<User> userList = userService.selectAll();
        ModelAndView map = new ModelAndView();
        map.addObject("result", userList);
        map.setViewName("/admin/main");
        return map;
    }

    /**
     * ????????????????????????????????????
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/person_look/{id}")
    public ModelAndView person_look(@PathVariable("id") Integer id) {
        System.out.println(id);
        User user = userService.selectByPrimaryKey(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/admin/person_look");
        return modelAndView;
    }

    /**
     * ??????????????????????????????
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteUser")
    public @ResponseBody
    Map<String, Object> person_delete(@RequestParam("id") Integer id) {
        /*
        ????????????
         */
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            userService.deleteByPrimaryKey(id);
            map.put("success", true);
            map.put("msg", "????????????");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "????????????");
        }
        return map;
    }

    /**
     * ??????????????????????????????
     *
     * @param id    ??????Id
     * @param power ??????????????????
     * @return
     */
    @RequestMapping(value = "/save_user")
    public @ResponseBody
    Map<String, Object> save_user(Integer id, Integer power) {
        System.out.println(id + ", power:" + power);
        Map<String, Object> map = new HashMap<String, Object>();
        if (power >= 0 && power <= 100) {
            userService.updatePowerByPrimaryKey(id, power);
            map.put("success", true);
            map.put("msg", "????????????");
        } else {
            map.put("success", false);
            map.put("msg", "?????????????????????");
        }
        return map;
    }

    /**
     * ????????????????????????
     *
     * @param username
     * @param password
     * @param phone
     * @param sex
     * @param power
     * @return
     */
    @RequestMapping(value = "/add_user")
    public @ResponseBody
    Map<String, Object> add_user(String username, String password, String phone, String sex, Byte power) {
        System.out.println(username + ", " + password + ", " + phone + ", " + sex + ", " + power);
        Map<String, Object> map = new HashMap<String, Object>();
        /*
        ????????????
         */
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        user.setSex(sex);
        user.setPower(power);

        userService.insert(user);

        map.put("success", true);
        map.put("msg", "????????????");
        return map;
    }


    /*???????????????*/

    /**
     * ?????????????????????????????????
     *
     * @return
     */
    @RequestMapping("/Home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        try {
            List<Carousel> carouselList = carouselService.selectAll();
            modelAndView.addObject("CarouselListResult", carouselList);
            modelAndView.setViewName("/admin/Home");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    /**
     * ?????????????????????????????????
     *
     * @return
     */
    @RequestMapping("/home_add")
    public String home_add() {
        return "/admin/home_add";
    }

    /**
     * ???????????????????????????
     *
     * @param id
     * @return
     */
    @RequestMapping("/home_edit/{id}")
    public ModelAndView home_edit(@PathVariable("id") Integer id) {
        System.out.println("id" + id);

        ModelAndView modelAndView = new ModelAndView();
        Carousel carousel = carouselService.selectByPrimaryKey(id);
        modelAndView.addObject("carouselResult", carousel);
        modelAndView.setViewName("/admin/home_edit");
        return modelAndView;
    }


    /**
     * ?????????????????????????????????
     *
     * @param id     ?????????ID
     * @param status ???????????????
     * @return
     */
    @RequestMapping(value = "/changeCarouselStatus")
    public @ResponseBody
    Map<String, Object> changeCarouselStatus(Integer id, Byte status) {

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            carouselService.updateStatusByPrimaryKey(id, status);
            map.put("success", true);
            map.put("msg", "????????????");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * ???????????????????????????
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteCarousel")
    public @ResponseBody
    Map<String, Object> deleteCarousel(Integer id) {

        System.out.println("deleteCarousel???id = " + id);
        try {
            //????????????
            carouselService.deleteByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("msg", "????????????");
        return map;
    }

    /**
     * ???????????????????????????
     *
     * @param title   ??????
     * @param imgUrl  ????????????
     * @param content ????????????
     * @param link    ???????????????
     * @return
     */
    @RequestMapping(value = "/insert_carousel")
    public @ResponseBody
    Map<String, Object> insertCarousel(String title, String imgUrl, String content, String link) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String url = imgUrl.substring("http://localhost:8088/images/web/".length());

        Carousel carousel = new Carousel(1, title, sdf.format(new Date()), (byte) 1, content, link, url);

        System.out.println("carousel:" + carousel);

        carouselService.insert(carousel);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        return map;
    }

    /**
     * ??????????????????????????????
     *
     * @param id      ?????????ID
     * @param title   ???????????????
     * @param imgUrl  ??????????????????
     * @param content ????????????
     * @param link    ????????????
     * @return
     */
    @RequestMapping(value = "/save_carousel")
    public @ResponseBody
    Map<String, Object> saveCarousel(Integer id, String title, String imgUrl, String content, String link) {
        String url = imgUrl.substring("http://localhost:8088/images/banner/".length());
        Carousel carousel = new Carousel();
        carousel.setId(id);
        carousel.setTitle(title);
        carousel.setImgUrl(url);
        carousel.setDescript(content);
        carousel.setUrl(link);

        carouselService.updateByPrimaryKey(carousel);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        return map;
    }


    /**
     * ????????????????????????????????????
     *
     * @param request
     * @param fileName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/fileUpload", produces = "application/json")
    public Map<String, Object> fileUpload(HttpServletRequest request, @RequestParam("avatar") MultipartFile fileName) {
        //???????????????????????????
        String oldFileName = fileName.getOriginalFilename();
        //???????????????????????????
        File rootPath = new File(request.getServletContext().getRealPath("/")).getParentFile();
        File uploadFile = new File(rootPath.getPath() + "/images/banner/");

        if (!uploadFile.exists()) {
            uploadFile.mkdirs();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        //????????????
        if (fileName != null && oldFileName != null && oldFileName.length() > 0) {
            try {
                //??????????????????
                String newFileName = UUID.randomUUID() + oldFileName.substring(oldFileName.lastIndexOf("."));
                //?????????
                String url = rootPath.getPath() + "/images/banner/" + newFileName;
                //?????????????????????????????????
                fileName.transferTo(new File(url));
                //????????????????????????????????????
                map.put("success", true);
                map.put("msg", "????????????");
                map.put("src", newFileName);
                return map;
            } catch (Exception e) {
                e.printStackTrace();
                map.put("succcess", false);
                map.put("msg", "????????????");
            }
        }
        return map;
    }




    /*????????????*/

    /**
     * ??????????????????????????????
     *
     * @return
     */
    @RequestMapping("/good")
    public ModelAndView good() {
        List<CatelogExtend> catelogExtendList = new ArrayList<CatelogExtend>();
        List<Goods> goodsList = goodsService.selectAllGoods();
        for (Goods goods : goodsList) {
            Catelog catelog = catelogService.selectByPrimaryKey(goods.getCatelogId());
            User user = userService.selectByPrimaryKey(goods.getUserId());
            List<Image> images = imageService.selectByGoodsPrimaryKey(goods.getId());
            CatelogExtend catelogExtend = new CatelogExtend(goods, catelog, user, images);
            catelogExtendList.add(catelogExtend);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("GoodsResult", catelogExtendList);
        modelAndView.setViewName("/admin/good");
        return modelAndView;
    }

    /**
     * ??????????????????????????????
     *
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value = "/changeGoodsStatus")
    public @ResponseBody
    Map<String, Object> changeGoodsStatus(Integer id, Byte status) {


        goodsService.updateStatusByPrimaryKey(id, status);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("msg", "????????????");
        return map;
    }

    /**
     * ??????????????????
     *
     * @param session
     * @param id      ????????????ID
     * @return
     */
    @RequestMapping(value = "/deleteGoods")
    public @ResponseBody
    Map<String, Object> deleteGoods(HttpSession session, Integer id) {

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Goods goods = goodsService.selectByPrimaryKey(id);

            //?????????????????????????????????
            Catelog catelog = catelogService.selectByPrimaryKey(goods.getCatelogId());
            catelogService.updateCatelogNum(goods.getCatelogId(), catelog.getNumber() - 1);

            //?????????????????????????????????
            User user = userService.selectByPrimaryKey(goods.getUserId());
            userService.updateGoodsNum(user.getId(), user.getGoodsNum() - 1);

            //???????????????????????????
            List<Comments> comments = commentsService.selectByGoodsId(goods.getId());
            for (Comments comment : comments) {
                commentsService.deleteByPrimaryKey(comment.getId());
            }

            //???????????????????????????
            List<Image> images = imageService.selectByGoodsPrimaryKey(goods.getId());
            for (Image image : images) {
                String file_path = session.getServletContext().getRealPath("images");
                File file = new File(file_path + "/web/" + image.getImgUrl());
                if (file.exists()) {
                    //????????????
                    file.delete();
                }
                imageService.deleteByPrimaryKey(image.getId());
            }

            //???????????????????????????
            List<Report> reportList = reportService.selectReportByGoodsPrimaryKey(goods.getId());
            for (Report report : reportList) {
                reportService.deleteByPrimaryKey(report.getId());
            }

            //????????????
            goodsService.deleteByPrimaryKey(id);

            map.put("success", true);
            map.put("msg", "????????????");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "????????????");
        }

        return map;
    }



    /*????????????*/

    /**
     * ??????????????????????????????
     *
     * @return
     */
    @RequestMapping("/catelog")
    public ModelAndView catelog() {
        List<Catelog> catelogs = catelogService.selectAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("catelogResult", catelogs);
        modelAndView.setViewName("/admin/catelog");
        return modelAndView;
    }

    /**
     * ??????????????????????????????
     */
    @RequestMapping("/catelog_add")
    public String catelog_add() {
        return "/admin/catelog_add";
    }

    /**
     * ??????????????????????????????
     *
     * @param id
     * @return
     */
    @RequestMapping("/catelog_edit/{id}")
    public ModelAndView catelog_edit(@PathVariable("id") Integer id) {
        Catelog catelog = catelogService.selectByPrimaryKey(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("editResult", catelog);
        modelAndView.setViewName("/admin/catelog_edit");
        return modelAndView;
    }

    /**
     * ????????????????????????
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/save_add_catelog")
    public @ResponseBody
    Map<String, Object> save_add_catelog(String name) {

        Catelog catelog = new Catelog();
        catelog.setName(name);
        catelog.setNumber(0);
        catelog.setStatus((byte) 1);
        catelogService.insert(catelog);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        return map;
    }

    /**
     * ????????????????????????????????????
     *
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "/save_edit_catelog")
    public @ResponseBody
    Map<String, Object> save_edit_catelog(Integer id, String name) {

        Catelog catelog = catelogService.selectByPrimaryKey(id);
        catelog.setName(name);
        catelogService.updateByPrimaryKey(catelog);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        return map;
    }

    /**
     * ????????????????????????????????????
     *
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value = "/change_status")
    public @ResponseBody
    Map<String, Object> change_status(Integer id, Byte status) {
        Catelog catelog = catelogService.selectByPrimaryKey(id);
        catelog.setStatus(status);
        catelogService.updateByPrimaryKey(catelog);
        List<Goods> goods = goodsService.selectGoodsByCatelogStatus(catelog.getId());

        for (Goods good : goods) {
            if (good.getStatus().equals((byte) 1) || good.getStatus().equals((byte) 0)) {
                if (status.equals((byte) 0)) {
                    goodsService.updateStatusByPrimaryKey(good.getId(), (byte) 0);
                } else {
                    goodsService.updateStatusByPrimaryKey(good.getId(), (byte) 1);
                }
            }

        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        return map;
    }

    /**
     * ??????????????????
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete_catelog")
    public @ResponseBody
    Map<String, Object> delete_catelog(Integer id) {
        catelogService.deleteByPrimaryKey(id);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        return map;
    }

    /*????????????*/

    /**
     * ??????????????????????????????
     *
     * @return
     */
    @RequestMapping("/report")
    public ModelAndView report() {
        List<Report> reportList = reportService.selectAll();
        List<ReportExtend> reportExtendList = new ArrayList<ReportExtend>();
        for (Report report : reportList) {
            Goods goods = goodsService.selectByPrimaryKey(report.getGoodId());
            List<Image> images = imageService.selectByGoodsPrimaryKey(goods.getId());
            User user = userService.selectByPrimaryKey(report.getUserId());
            ReportExtend reportExtend = new ReportExtend(report, goods, images, user);
            reportExtendList.add(reportExtend);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("reportResult", reportExtendList);
        modelAndView.setViewName("/admin/report");
        return modelAndView;
    }

    /**
     * ??????????????????????????????
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete_report")
    public @ResponseBody
    Map<String, Object> delete_report(Integer id) {
        reportService.deleteByPrimaryKey(id);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        return map;
    }


    /*????????????*/


    /**
     * ??????????????????????????????
     *
     * @return
     */
    @RequestMapping("/comments")
    public ModelAndView comments() {
        List<Comments> comments = commentsService.selectAll();
        List<CommentsExtend> commentsExtendList = new ArrayList<CommentsExtend>();
        for (Comments comment : comments) {
            CommentsExtend commentsExtend = new CommentsExtend();
            Goods goods = goodsService.selectByPrimaryKey(comment.getGoodsId());
            User user = userService.selectByPrimaryKey(comment.getUserId());
            commentsExtend.setUser(user);
            commentsExtend.setGoods(goods);
            commentsExtend.setComments(comment);
            commentsExtendList.add(commentsExtend);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("commentsResult", commentsExtendList);
        modelAndView.setViewName("/admin/comments");
        return modelAndView;
    }

    /**
     * ??????????????????????????????
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete_comments")
    public @ResponseBody
    Map<String, Object> delete_comments(Integer id) {
        Comments comments = commentsService.selectByPrimaryKey(id);

        Goods goods = goodsService.selectByPrimaryKey(comments.getGoodsId());

        goodsService.updateCommentNumByPrimaryKey(comments.getGoodsId(), goods.getCommetNum() - 1);

        commentsService.deleteByPrimaryKey(id);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        return map;
    }

    /*????????????*/

    /**
     * ????????????????????????
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/order")
    public ModelAndView order(HttpSession session) {
        List<OrdersExtend> ordersExtends = new ArrayList<OrdersExtend>();

        List<Orders> orders = ordersService.selectAll();
        for (Orders order : orders) {
            OrdersExtend ordersExtend = new OrdersExtend();
            Goods goods = goodsService.selectByPrimaryKey(order.getGoodId());
            Address address = addressService.selectByPrimaryKey(order.getAddressId());
            List<Image> images = imageService.selectByGoodsPrimaryKey(goods.getId());
            User user = userService.selectByPrimaryKey(order.getUserId());
            ordersExtend.setUser(user);
            ordersExtend.setOrders(order);
            ordersExtend.setImages(images);
            ordersExtend.setAddress(address);
            ordersExtend.setGood(goods);
            ordersExtends.add(ordersExtend);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/order");
        modelAndView.addObject("ordersExtends", ordersExtends);
        return modelAndView;
    }

}
