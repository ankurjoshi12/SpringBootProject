package com.bb.AutomationUtils.userdetailsapp.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bigbasket.automation.reports.AutomationReport;
import com.bigbasket.automation.reports.AutomationReportListener;
import com.bigbasket.automation.reports.DescriptionProvider;
import com.bigbasket.automation.utilities.database.HertzDBQueries;

import io.vertx.core.json.JsonArray;
import utility.flows.Picking;

@Controller
public class SlotController {
	AutomationReportListener obj = new AutomationReportListener();
	
//	@GetMapping("/sadmmapping")
//	public String getUserDetails() {
//		return "hertz";
//	}
//	@GetMapping("/getOrderData")
//	public String getOrderData() {
//		return "hertz-order-data";
//	}
//	
//	@DescriptionProvider(slug = "Test ",description = "test desc ", author = "Ankur")
//	@PostMapping("/sadmmapping")
//	public String getWalletDetailsForm(@RequestParam Integer saId ,@RequestParam Integer dmId,@RequestParam Integer sdId, Model model) throws IOException {
//		AutomationReport report = obj.getInitializedReport(this.getClass() , "Sa DM Mapping" ,Integer.class,Integer.class,Integer.class,Model.class); 
//		JsonArray hertzArray =  HertzDBQueries.getSADMSlotConfig(saId,dmId,sdId,report);
//		model.addAttribute("message", "Hertz SA DM Mapping Api Response "+hertzArray);
//		return "hertz";
//	}
//	
//	@DescriptionProvider(slug = "Test ",description = "test desc ", author = "Ankur")
//	@PostMapping("/getOrderData")
//	public String getOrderData(@RequestParam String orderId , Model model) throws IOException {
//		AutomationReport report = obj.getInitializedReport(this.getClass() , "Order Data" ,String.class,Model.class); 
//		JsonArray hertzArray =  HertzDBQueries.getOrderData(orderId, report);
//		
//		model.addAttribute("message", "Hertz Order details "+hertzArray);
//		return "hertz-order-data";
//	}
}
