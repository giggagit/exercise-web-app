package com.giggagit.exam.Controller;

import java.util.List;

import com.giggagit.exam.Model.AnnounceModel;
import com.giggagit.exam.Service.AnnounceService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Announce
 */
@Controller
public class AnnounceController {

    private final AnnounceService announceService;

    public AnnounceController(AnnounceService announceService) {
        this.announceService = announceService;
    }

    @ModelAttribute("pageNav")
    public String pageNav() {
        return "announce";
    }
    
    // Show last 10 announce
    @RequestMapping({"", "/", "/announce"})
    public String announce(Model model) {
        List<AnnounceModel> pageModel = announceService.findTop10O();
        model.addAttribute("pageModel", pageModel);
        return "user/announce/index";
    }

}