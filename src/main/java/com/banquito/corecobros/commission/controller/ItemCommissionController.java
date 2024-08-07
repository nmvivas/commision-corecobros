package com.banquito.corecobros.commission.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.corecobros.commission.dto.CommissionDTO;
import com.banquito.corecobros.commission.dto.ItemCommissionDTO;
import com.banquito.corecobros.commission.service.ItemCommissionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE })
@RestController
@RequestMapping("/commission-microservice/api/v1/item-commissions")
@Tag(name = "ItemCommission", description = "Endpoints for managing item-commission")
public class ItemCommissionController {

    private final ItemCommissionService itemCommissionService;

    public ItemCommissionController(ItemCommissionService itemCommissionService) {
        this.itemCommissionService = itemCommissionService;
    }

    @PostMapping
    @Operation(summary = "Create a itemCommission", description = "Create a new itemCommission")
    public ResponseEntity<ItemCommissionDTO> create(@RequestBody ItemCommissionDTO itemCommissionDTO) {
        try {
            ItemCommissionDTO savedItemCommission = itemCommissionService.create(itemCommissionDTO);
            return ResponseEntity.ok(savedItemCommission);
        } catch (RuntimeException rte) {
            rte.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{uniqueId}")
    @Operation(summary = "Get itemCommission by uniqueId", description = "Retrieve a itemCommission by its uniqueId")
    public ResponseEntity<List<ItemCommissionDTO>> getItemCommissionByCommissionUniqueId(
            @PathVariable String uniqueId) {
        try {
            List<ItemCommissionDTO> itemCommission = itemCommissionService.obtainItemCommissionByUniqueId(uniqueId);
            return ResponseEntity.ok(itemCommission);
        } catch (RuntimeException rte) {
            rte.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/item/{itemUniqueId}")
    @Operation(summary = "Get itemCommission by itemCollection", description = "Retrieve a itemCommission by its itemCollection")
    public ResponseEntity<ItemCommissionDTO> getByItemUniqueId(@PathVariable String itemUniqueId) {
        try {
            ItemCommissionDTO itemCommission = itemCommissionService.getByItemUniqueId(itemUniqueId);
            return ResponseEntity.ok(itemCommission);
        } catch (RuntimeException rte) {
            rte.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/get-total-by-order/{orderUniqueId}/{companyUniqueId}")
    @Operation(summary = "Get total commission by orderUniqueId and companyUniqueId", description = "Retrieve a total commission by orderUniqueId and companyUniqueId")
    public ResponseEntity<CommissionDTO> getByItem(@PathVariable String orderUniqueId,
            @PathVariable String companyUniqueId) {
        return ResponseEntity
                .ok(itemCommissionService.sumTotalCommissionValueByOrderUniqueId(orderUniqueId, companyUniqueId));
    }
}
