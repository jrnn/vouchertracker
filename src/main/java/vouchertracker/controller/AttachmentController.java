package vouchertracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vouchertracker.service.AttachmentService;

@Controller
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @RequestMapping(value = "/vouchers/files/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<byte[]> serve(@PathVariable("id") String id) {
        try {
            return attachmentService.get(id);
        } catch (Exception e) {
        }

        return null;
    }

    @RequestMapping(value = "/vouchers/{voucherId}/files", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority(#ownerId) OR hasAuthority('ADMIN')")
    public String upload(
            @PathVariable("voucherId") String voucherId,
            @RequestParam("ownerId") String ownerId,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttrs
    ) {
        String result = attachmentService.save(file, voucherId);

        if (result != null) redirectAttrs.addFlashAttribute(
                "failure", result);
        else redirectAttrs.addFlashAttribute(
                "success", "Hooray! Attachment upload successful!");

        return "redirect:/vouchers/" + voucherId;
    }

    @RequestMapping(value = "/vouchers/files/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority(#ownerId) OR hasAuthority('ADMIN')")
    public String delete(
            @PathVariable("id") String id,
            @RequestParam("ownerId") String ownerId
    ) {
        String voucherId = attachmentService.delete(id);

        return "redirect:/vouchers/" + voucherId;
    }

}