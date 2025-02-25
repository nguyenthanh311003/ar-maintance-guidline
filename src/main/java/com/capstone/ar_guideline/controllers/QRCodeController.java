package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.util.UtilService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qrcode")
public class QRCodeController {

  @GetMapping()
  public String getQRCode(@RequestParam String text) {
    return UtilService.generateAndStoreQRCode(text);
  }
}
