package vouchertracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vouchertracker.domain.entity.Comment;
import vouchertracker.service.CommentService;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/comments", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('USER')")
    public String create(
            @RequestParam("id") String id,
            @RequestParam("src") String src,
            @RequestParam("comment") String comment,
            RedirectAttributes redirectAttrs
    ) {
        Comment c = commentService.saveComment(id, src, comment.trim());

        if (c != null) {
            redirectAttrs.addFlashAttribute("success", "Hooray! Comment added!");
            return "redirect:/" + src + "/" + id;
        } else {
            redirectAttrs.addFlashAttribute("failure", "Oh snap! Something went wrong.");
            return "redirect:/";
        }

    }

}