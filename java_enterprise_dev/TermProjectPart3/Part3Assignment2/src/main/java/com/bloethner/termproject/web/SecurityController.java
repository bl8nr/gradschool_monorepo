package com.bloethner.termproject.web;

import com.bloethner.termproject.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import java.util.Locale;

@RequestMapping("/security")
@Controller
public class SecurityController {

    private MessageSource messageSource;

    /**
     * on login failure
     * redirect to the book/list view and display a login failure i18n message
     */
    @RequestMapping("/loginfail")
    public String loginFail(Model uiModel, Locale locale) {
        uiModel.addAttribute("message", new Message("error", messageSource.getMessage("message_login_fail", new Object[]{}, locale)));
        return "books/list";
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
