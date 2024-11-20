package com.donation.donation_system.controller;

import com.donation.donation_system.model.Foundation;
import com.donation.donation_system.repository.FoundationRepository;
import com.donation.donation_system.service.FoundationService;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/admin/Foundation")
public class FoundationController {
    @Autowired
    private FoundationService foundationService;
    @PostMapping("/addFoundation")
    public ResponseEntity<String>SaveFoundation(@RequestBody List<Foundation> foundation)
    {
        foundationService.SaveFoundation(foundation);
        return ResponseEntity.ok("Them thanh cong");
    }
    @GetMapping("/Information")
    @ResponseBody
    public ResponseEntity<List<Foundation>> getFoundation()
    {
        List<Foundation> foundationList = foundationService.findAll();
        return ResponseEntity.ok(foundationList);
    }

}
