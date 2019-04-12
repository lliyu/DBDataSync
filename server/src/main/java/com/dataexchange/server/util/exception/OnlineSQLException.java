package com.dataexchange.server.util.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: Administrator
 * @Date: 2019-01-30 15:41
 * @Description:
 */
@Component
public class OnlineSQLException implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse, Object o, Exception e) {

        MappingJackson2JsonView json = new MappingJackson2JsonView();

        ModelAndView view = new ModelAndView(json);
        view.addObject("success", false);
        view.addObject("msg", e.getMessage());
        view.addObject("code", 500);

        return view;
    }
}
