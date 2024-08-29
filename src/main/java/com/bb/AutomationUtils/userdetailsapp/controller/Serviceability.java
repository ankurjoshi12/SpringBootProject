package com.bb.AutomationUtils.userdetailsapp.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bigbasket.automation.api.microservice.serviceability.ServiceabilityService;
import com.bigbasket.automation.api.microservice.skugrouping.SkuGroupingService;
import com.bigbasket.automation.reports.AutomationReport;
import com.bigbasket.automation.reports.AutomationReportListener;
import com.bigbasket.automation.reports.DescriptionProvider;
import com.bigbasket.automation.utilities.Libraries;

import io.restassured.response.Response;
import io.vertx.core.json.JsonArray;
import jakarta.validation.Valid;

@Controller
public class Serviceability extends Libraries{
	AutomationReportListener obj = new AutomationReportListener();

	@GetMapping("/serviceability")
	public String getUserDetails() {
		return "serviceability-details";
	}
	
	
	@GetMapping("/getSkusPickSubType")
	public String getSkusPickSubType() {
		return "sku-grouping";
	}
	
	
	@DescriptionProvider(slug = "Test ",description = "test desc ", author = "Ankur")
	@PostMapping("/serviceability")
	public String getWalletDetailsForm(@RequestParam String saId , Model model) throws IOException {
		AutomationReport report = obj.getInitializedReport(this.getClass() , "Get Wallet details" ,String.class,Model.class);
		ServiceabilityService serviceability = new ServiceabilityService(report);
		Response res = serviceability.getSADetails(Long.parseLong(saId));
		model.addAttribute("message", "Member Wallet update API resposne "+res.asPrettyString());
		return "serviceability-details";
	}
	
	@DescriptionProvider(slug = "Test ",description = "test desc ", author = "Ankur")
	@PostMapping("/getSkusPickSubType")
	public String getSkusPickSubType(@Valid @RequestParam String fcId , @RequestParam String Json, Model model) throws IOException {
		AutomationReport report = obj.getInitializedReport(this.getClass() , "Get Wallet details" ,String.class,String.class, Model.class);
		String [] json = Json.split(",");
		JsonArray skuArrayList = new JsonArray();
		for(String sku : json) {
			skuArrayList.add(sku);
		}
		
		SkuGroupingService serviceability = new SkuGroupingService(report);
		Response res = serviceability.getSkusPickSubType(Integer.parseInt(fcId),skuArrayList);
		model.addAttribute("message", "Get Sku Grouping Resposne "+res.asPrettyString());
		return "sku-grouping";
	}
}