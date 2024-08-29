package com.bb.AutomationUtils.userdetailsapp.controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bb.AutomationUtils.userdetailsapp.model.MemberJsonObject;
import com.bb.AutomationUtils.userdetailsapp.model.UserDetails;
import com.bb.AutomationUtils.userdetailsapp.service.UserService;
import com.bigbasket.automation.JavelinUtil.CampaignApi;
import com.bigbasket.automation.JavelinUtil.JavelinUtilsMethods.JavelinCommonsUtils;
import com.bigbasket.automation.api.giftcardutil.GiftCardUtils;
import com.bigbasket.automation.api.microservice.javelin.vouchers.Endpoints;
import com.bigbasket.automation.api.microservice.javelin.vouchers.VoucherJavelinAdmin;
import com.bigbasket.automation.api.paymentutil.PaymentUtil;
import com.bigbasket.automation.api.walletutil.WalletUtil;
import com.bigbasket.automation.reports.AutomationReport;
import com.bigbasket.automation.reports.AutomationReportListener;
import com.bigbasket.automation.reports.DescriptionProvider;
import com.bigbasket.automation.utilities.Libraries;
import com.bigbasket.automation.utilities.database.OrderDbQueries;
import com.bigbasket.automation.utilities.monolith.GiftCardDbQueries;

import io.restassured.http.Method;
import io.restassured.response.Response;

@Controller
public class UserWebController extends Libraries{
	AutomationReportListener obj = new AutomationReportListener();

	@Autowired
	private UserService userService;
	
	@GetMapping("/create-gift-card")
	public String createGiftCard() {
		return "create-gift-card";
	}
	
	@GetMapping("/get-campaign-info")
	public String getCampaignInfo() {
		return "get-campaign-info";
	}
	
	/*
	 * @DescriptionProvider(slug = "Test ",description = "test desc ", author =
	 * "Ankur")
	 * 
	 * @PostMapping("/get-campaign-info") public String campaignInfo(@RequestParam
	 * String campaignId , Model model ) throws IOException, ParseException,
	 * InterruptedException { AutomationReport report =
	 * obj.getInitializedReport(this.getClass(), String.class , Model.class);
	 * CampaignApi api = new CampaignApi(serverName, report); Response res =
	 * api.getCampaignDetails(campaignId); model.addAttribute("message",
	 * "Campaign Details -"+res.getBody().asPrettyString()); return
	 * "get-campaign-info"; }
	 */
	
	@DescriptionProvider(slug = "Test ",description = "test desc ", author = "Ankur")
	@PostMapping("/get-campaign-info")
	public ModelAndView campaignInfo(@RequestParam String campaignId ) throws IOException, ParseException, InterruptedException {
		AutomationReport report = obj.getInitializedReport(this.getClass(), String.class );
		CampaignApi api = new CampaignApi(serverName, report);
		Response res = api.getCampaignDetails(campaignId);
		ModelAndView mav = new ModelAndView("get-campaign-info");	
        mav.addObject("message",res.getBody().asPrettyString());
        return mav ;		
	}
	
	
	//	@GetMapping("/user-details")
	//    public String getUserDetails(@RequestParam("mobileNumber") String mobileNumber, Model model) {
	//        UserDetails userDetails = userService.getUserDetails(mobileNumber);
	//        model.addAttribute("userDetails", userDetails);
	//        return "user-details";
	//    }
	@GetMapping("/user-details")
	public String getUserDetails() {
		//        UserDetails userDetails = userService.getUserDetails(mobileNumber);
		//        model.addAttribute("userDetails", userDetails);
		return "user-details";
	}
	@PostMapping("/user-details")
	public String getUserDetails(@RequestParam("mobileNumber") String mobileNumber, Model model) {
		UserDetails userDetails = userService.getUserDetails(mobileNumber);
		//		String userDetails = "Recieved the request -"+mobileNumber;
		model.addAttribute("userDetails", userDetails);
		return "user-details";
	}

	@GetMapping("/create-voucher")
	public String showCreateVoucherForm() {
		return "create-voucher";
	}
	
	
	
	@DescriptionProvider(slug = "Test ",description = "test desc ", author = "Ankur")
	@PostMapping("/get-wallet-details")
	public String getWalletForm(@RequestParam String entryContext , @RequestParam String mobileNumber , 
			@RequestParam String email, Model model) throws IOException {
		String[] creds = {mobileNumber ,email};
		AutomationReport report = obj.getInitializedReport(this.getClass() , "Get Wallet details" ,String.class ,String.class ,String.class,Model.class);
		WalletUtil utils = new WalletUtil(report, entryContext ,creds);
		Response res = utils.getWalletCreditDebitLog();
		model.addAttribute("message", "Member Credit Debit log "+res.asPrettyString());
		return "get-wallet-details";
	}
	
//	@GetMapping("/wallet-details")
//	public String showWalletForm() {
//		return "wallet-details";
//	}

	@DescriptionProvider(slug = "Test ",description = "test desc ", author = "Ankur")
	@PostMapping("/create-voucher")
	public String createVoucher(@RequestParam String transactionType, @RequestParam String rewardType, 
			@RequestParam String paymentType, @RequestParam boolean autoVoucher, @RequestParam String voucherType, 
			Model model) throws JSONException, IOException {

		AutomationReport report = obj.getInitializedReport(this.getClass(), "Test" , String.class , String.class ,String.class ,boolean.class ,String.class ,Model.class);
		model.addAttribute("message", "transactionType : "+transactionType);
		model.addAttribute("message", rewardType);
		model.addAttribute("message", paymentType);
		model.addAttribute("message", autoVoucher);
		model.addAttribute("message", voucherType);

		GetVoucherTemplate template = new GetVoucherTemplate();
		JSONObject data =  template.getVoucherTemplate(transactionType, rewardType, paymentType, autoVoucher, voucherType);
		
		JavelinCommonsUtils javelinCommonUtils = new JavelinCommonsUtils();
		javelinCommonUtils.createCampaignAndSegmentation(report,"b2c");
		VoucherJavelinAdmin voucherJavelinAdmin =  new VoucherJavelinAdmin(report);
		Response res = voucherJavelinAdmin.callVoucherApi(data.toString(), String.format(Endpoints.VoucherJavelinAdmin.SAVE_OR_EDIT_VOUCHER, javelinCommonUtils.getCampaignID()),  Method.POST, true, template.getHeaders());

		// Add some message to model to show on the form page
		model.addAttribute("message", "Voucher created successfully! here is the API resposne /n"+res.asPrettyString());
		return "create-voucher";
	}
	@GetMapping("/member-details")
	public String showMemberDetailsForm() {
		return "member-details";
	}

	@DescriptionProvider(slug = "Negative member wallet above threshold ",description = "Check if member wallet is above negative balance threshold , user should not be allowed to place order"
			+ "PO Api should return error code 1028 ", author = "Ankur")
	@PostMapping("/member-details")
	public String getMemberDetails(@RequestParam String memberEmail, Model model) {

		AutomationReport report = obj.getInitializedReport(this.getClass(), "Test" , String.class , Model.class);
		// Simulate getting member details
		//		UserDetails memberDetails = userService.getUserDetailsByEmail(memberEmail);
		//		model.addAttribute("memberDetails", memberDetails);
		OrderDbQueries queries = new OrderDbQueries(report);
		String memberID = queries.getMemberId(memberEmail);
		//		String memberID = OrderDbQueries2.getMemberId(memberEmail);
		MemberJsonObject  memberJsonObject = new MemberJsonObject(memberID);		
		//		String memberIDQuery = "select id from member_member where email='"+memberEmail+"'";
		//		String memberID = AutomationUtilities.getSingleResponseValue("uat3.bigbasket.com", memberIDQuery);
		model.addAttribute("memberJson", memberJsonObject);
		//		model.addAttribute("message", "Member ID: "+memberID);
//		return "member-json";
		return "member-details";
	}
}