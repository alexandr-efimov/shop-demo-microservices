package com.example.controller;

import com.example.client.RestServiceClient;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by alexandr.efimov on 3/7/2017.
 */
@Controller
@Slf4j
public class OrderController {

    private static final String REFERER = "Referer";
    private static final String REDIRECT = "redirect:";
    @Autowired
    private RestServiceClient<Product> productClient;
    @Autowired
    private RestServiceClient<User> userClient;

    @Autowired
    private OrderRepository orderRepository;
    private Random random = new Random();

    @RequestMapping("/")
    public ModelAndView orderList() {
        Iterable<Order> orders = orderRepository.findAll();
        log.info("Get orders: " + orders);
        ModelAndView modelAndView = new ModelAndView("orderList", "orders", orders);
        modelAndView.addObject("order", new Order());
        modelAndView.addObject("availableProducts", productClient.findAll());
        modelAndView.addObject("availableUsers", userClient.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String post(@PathVariable("id") long id, HttpServletRequest request) {
        log.info("Delete order, id: " + id);
        orderRepository.delete(id);

        String referrer = request.getHeader(REFERER);
        return REDIRECT + referrer;
    }

    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    public String addOrder(HttpServletRequest request, @ModelAttribute Order order) {

        String[] productIds = request.getParameterValues("productId");
        String userId = request.getParameter("userId");
        log.info("Products ids: " + Arrays.toString(productIds));
        log.info("User id: " + userId);
        List<Product> productsForOrder = Arrays.stream(productIds).map(Long::parseLong).map(productClient::getOne)
                .peek(e -> e.setCurrentOrder(order)).peek(e -> e.setId(null)).collect(Collectors.toList());
        order.setOrderProducts(productsForOrder);
        order.setOrderDateTime(LocalDateTime.now());
        User user = userClient.getOne(Long.valueOf(userId));
        user.setId(null);
        order.setUser(user);
        log.info("Add order: " + order);
        orderRepository.save(order);

        String referrer = request.getHeader(REFERER);
        return REDIRECT + referrer;
    }

    /****************************************************************************************************
     Stubs - test communication between microservices and so on
     ****************************************************************************************************/

    @RequestMapping(value = "/test/phone/")
    @ResponseBody
    public Collection<Product> testAll() {
        log.info("get all products");
        return productClient.findAll();
    }

    @RequestMapping("/test/phone/{id}")
    @ResponseBody
    public Product testOne(@PathVariable long id) {
        log.info("Get by id: " + id);
        return productClient.getOne(id);
    }

    @RequestMapping(value = "/addRandomOrder", method = RequestMethod.POST)
    public String testMakeOrder(HttpServletRequest request) {
        log.info("Add random order");
        Order order = new Order();
        order.setOrderDateTime(LocalDateTime.now());
        User user = userClient.findAll().stream().findFirst().orElseThrow(IllegalArgumentException::new);
        user.setId(null);
        user.setOrders(Arrays.asList(order));
        order.setUser(user);
        List<Product> products = (List<Product>) productClient.findAll();
        Collections.shuffle(products);
        List<Product> productsForOrder = products.stream().limit(random.nextInt(products.size() + 1)).collect(Collectors.toList());
        productsForOrder.forEach(e -> e.setCurrentOrder(order));
        productsForOrder.forEach(e -> e.setId(null));

        order.setOrderProducts(productsForOrder);
        log.info("Save order: \n" + order);
        orderRepository.save(order);

        String referrer = request.getHeader(REFERER);
        return REDIRECT + referrer;
    }
}
