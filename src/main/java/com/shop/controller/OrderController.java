package com.shop.controller;

import com.shop.pojo.Goods;
import com.shop.pojo.Orders;
import com.shop.pojo.User;
import com.shop.service.AddressService;
import com.shop.service.GoodsService;
import com.shop.service.OrdersService;
import com.shop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

    @Resource
    private GoodsService goodsService;

    @Resource
    private AddressService addressService;

    @Resource
    private UserService userService;

    @Resource
    private OrdersService ordersService;

    @RequestMapping(value = "/orderConfirm")
    public ModelAndView orderConfirm(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("/user/orderConfirm");
        return modelAndView;
    }

    @RequestMapping(value = "/payment")
    public ModelAndView payment(Integer gid,Integer aid,HttpSession session){
//        System.out.println("gid: "+gid+", addressId: " + aid);
        ModelAndView modelAndView = new ModelAndView();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(aid != null){
            Goods good = goodsService.selectByPrimaryKey(gid);
            User user = (User)session.getAttribute("cur_user");
            User seller = userService.selectByPrimaryKey(good.getUserId());
            String payId = getOrderIdByTime();
            modelAndView.addObject("good",good);
            modelAndView.addObject("aid",aid);
            modelAndView.addObject("payId",payId);
            modelAndView.addObject("seller",seller);
            modelAndView.setViewName("/order/payment");
        }else{
            modelAndView.setViewName("/user/address");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/payresult")
    public String payresult(Integer gid,Integer aid,HttpSession session,String payid,Model model){
        System.out.println("gid: "+gid+", addressId: " + aid+",payid: "+payid);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        User user = (User)session.getAttribute("cur_user");
        Orders order = new Orders();
        order.setUserId(user.getId());
        order.setGoodId(gid);
        order.setAddressId(aid);
        order.setPayId(payid.trim());
        order.setStatus((byte)1);
        order.setCreateAt(sdf.format(new Date()));
        order.setOrderId(getOrderIdByTime().trim());
        System.out.println(order);

        ordersService.insert(order);

        goodsService.updateStatusByPrimaryKey(gid,(byte)3);
        Goods good = goodsService.selectByPrimaryKey(gid);

//        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("order",order);
        model.addAttribute("good",good);
        /*modelAndView.addObject("order",order);
        modelAndView.addObject("good",good);*/
       /* modelAndView.setViewName("/order/pay_success");*/
        return "/order/pay_success";
    }


    public static String getOrderIdByTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<3;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }


}
