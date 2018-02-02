package vouchertracker.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vouchertracker.domain.mapper.EntityMapper;

@Service
public class CsvService {

    private static final String CSV_SEPARATOR = ";";

    @Autowired
    private List<EntityMapper> mappers;

    public ResponseEntity<byte[]> exportCsv() {
        StringBuilder sb = new StringBuilder();
        String now = LocalDate.now() + "";
        String filename = "db_pvtracker_" + now.replaceAll("-", "");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.add("content-disposition", "attachment;filename=" + filename);

        sb
                .append("Prepaid Voucher Tracker data export ")
                .append(now).append("\n");

        mappers.forEach(m -> {
            sb.append(m.writeToCsv(CSV_SEPARATOR));
        });

        byte[] b = sb.toString().getBytes(StandardCharsets.UTF_8);
        return new ResponseEntity<>(b, headers, HttpStatus.CREATED);
    }

}