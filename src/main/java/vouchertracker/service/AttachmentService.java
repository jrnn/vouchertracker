package vouchertracker.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.MultipartConfigElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vouchertracker.domain.entity.FileObject;
import vouchertracker.domain.entity.Voucher;
import vouchertracker.repository.FileObjectRepository;
import vouchertracker.repository.VoucherRepository;

@Service
public class AttachmentService {

    private static final List<String> ACCEPTED_MEDIA_TYPES = Arrays.asList(
            "application/pdf", "image/bmp", "image/gif", "image/jpeg", "image/png");

    @Autowired
    private FileObjectRepository fileObjectRepository;
    @Autowired
    private VoucherRepository voucherRepository;

    @Bean // for increasing upload filesize limit
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();

        factory.setMaxFileSize("10MB");
        factory.setMaxRequestSize("10MB");

        return factory.createMultipartConfig();
    }

    public ResponseEntity<byte[]> get(String id) {
        FileObject fo = fileObjectRepository.findByUuid(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fo.getMediaType()));
        headers.setContentLength(fo.getFileSize());
        headers.add("content-disposition", "inline;filename=" + fo.getFileName());

        return new ResponseEntity<>(fo.getContent(), headers, HttpStatus.CREATED);
    }

    public String save(MultipartFile file, String voucherId) {
        Voucher voucher = voucherRepository.findByUuid(voucherId);

        if (file == null || voucher == null)
            return "Oh snap! Something went fundamentally wrong";

        if (file.getSize() == 0 || !ACCEPTED_MEDIA_TYPES.contains(file.getContentType()))
            return "Dang. That filetype isn't supported.";

        try {
            FileObject fo = convertToFileObject(file, voucher);
        } catch (IOException e) {
            return "Oh noes! The file could not be read for some reason";
        }

        return null;
    }

    public String delete(String id) {
        FileObject fo = fileObjectRepository.findByUuid(id);
        String voucherId = fo.getVoucher().getId();
        fileObjectRepository.delete(fo);

        return voucherId;
    }

    @Transactional
    private FileObject convertToFileObject(
            MultipartFile file,
            Voucher voucher) throws IOException
    {
        FileObject fo = new FileObject();

        fo.setFileName(file.getOriginalFilename());
        fo.setFileSize(file.getSize());
        fo.setMediaType(file.getContentType());
        fo.setContent(file.getBytes());
        fo.setVoucher(voucher);

        return fileObjectRepository.save(fo);
    }

}