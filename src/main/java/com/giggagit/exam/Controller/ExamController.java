package com.giggagit.exam.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.giggagit.exam.Model.ScoreModel;
import com.giggagit.exam.Model.TopicModel;
import com.giggagit.exam.Service.TopicService;
import com.giggagit.exam.Service.UserService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ExamController
 */
@Controller
@RequestMapping("/exam")
public class ExamController {

    private final TopicService topicService;
    private final UserService userService;

    public ExamController(TopicService topicService, UserService userService) {
        this.topicService = topicService;
        this.userService = userService;
    }

    @ModelAttribute("pageNav")
    public String pageNav() {
        return "exam";
    }

    // Exam page
    @GetMapping({ "", "/" })
    public String examList(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
            Authentication authentication, Model model) {
        List<TopicModel> topicModels = new ArrayList<>();
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<TopicModel> topicPage = topicService.findAllByStatus(true, pageable);

        for (TopicModel topicModel : topicPage.getContent()) {
            TopicModel topicChecked = topicService.examDoneFilter(topicModel, authentication.getName());
            topicModels.add(topicChecked);
        }

        Page<TopicModel> pageModel = new PageImpl<>(topicModels, pageable, topicPage.getTotalElements());

        model.addAttribute("pageModel", pageModel);
        model.addAttribute("pageLink", "exam");

        return "user/exam/index";
    }

    // Do excerise
    @GetMapping("/topic/{topicId}")
    public String doExam(@PathVariable("topicId") int topicId, Authentication authentication, Model model) {
        TopicModel topicModel = topicService.examDoneFilter(topicService.findById(topicId), authentication.getName());

        if (topicService.examFilter(topicModel)) {
            return "redirect:/exam?error";
        }

        model.addAttribute("topicModel", topicModel);
        return "user/exam/do-exam";
    }

    // Excerise score
    @PostMapping("/topic/{topicId}/result")
    public String result(@PathVariable("topicId") int topicId, @RequestParam Map<String, String> userAnswer,
            Authentication authentication, Model model) {
        TopicModel topicModel = topicService.examDoneFilter(topicService.findById(topicId), authentication.getName());

        if (topicService.examFilter(topicModel)) {
            return "redirect:/exam?error";
        }

        ScoreModel scoreModel = userService.submitExam(topicId, authentication.getName(), userAnswer,
                topicService.findById(topicId));
        model.addAttribute("scoreModel", scoreModel);
        return "user/exam/result";
    }

}