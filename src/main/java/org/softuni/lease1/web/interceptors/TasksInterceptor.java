package org.softuni.lease1.web.interceptors;

import org.softuni.lease1.service.OfferService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TasksInterceptor extends HandlerInterceptorAdapter {
    private final OfferService offerService;

    public TasksInterceptor(OfferService offerService) {
        this.offerService = offerService;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (modelAndView == null){
            modelAndView = new ModelAndView();
        } else {
            Integer newRequests = this.offerService.findAllRequestedOffers().size();
            modelAndView.addObject("newRequests", newRequests);
        }
    }
}
