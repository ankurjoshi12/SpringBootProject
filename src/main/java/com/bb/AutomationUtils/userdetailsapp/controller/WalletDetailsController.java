package com.bb.AutomationUtils.userdetailsapp.controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bigbasket.automation.api.giftcardutil.GiftCardUtils;
import com.bigbasket.automation.api.paymentutil.PaymentUtil;
import com.bigbasket.automation.api.walletutil.WalletUtil;
import com.bigbasket.automation.reports.AutomationReport;
import com.bigbasket.automation.reports.AutomationReportListener;
import com.bigbasket.automation.reports.DescriptionProvider;
import com.bigbasket.automation.utilities.monolith.GiftCardDbQueries;

import io.restassured.response.Response;

@Controller
public class WalletDetailsController {
	
	AutomationReportListener obj = new AutomationReportListener();

	@GetMapping("wallet-details")
    public String walletDetails(Model model) {
        model.addAttribute("view", "wallet-details :: content");
        return "wallet-details";
    }

    @GetMapping("/get-wallet-details")
    public String getWalletDetails(Model model) {
//        model.addAttribute("view", "get-wallet-details :: content");
        return "get-wallet-details";
    }

//    @GetMapping("/update-wallet-details")
//    public String updateWalletDetails(Model model) {
//        model.addAttribute("view", "update-wallet-details :: content");
//        return "wallet-details";
//    }
    
    @GetMapping("/update-wallet-details")
    public String updateWalletDetails(Model model) {
//        model.addAttribute("view", "update-wallet-details :: content");
        return "update-wallet-details";
    }
    
    @DescriptionProvider(slug = "Test ",description = "test desc ", author = "Ankur")
	@PostMapping("/update-wallet-details")
	public String getWalletDetailsForm(@RequestParam String entryContext , @RequestParam String mobileNumber , 
			@RequestParam String email , @RequestParam String walletaction , @RequestParam String walletAmount , Model model) throws IOException {
		String[] creds = {mobileNumber ,email};
		AutomationReport report = obj.getInitializedReport(this.getClass() , "Get Wallet details" , String.class , String.class ,String.class ,String.class ,String.class,Model.class);
		WalletUtil utils = new WalletUtil(report, entryContext ,creds);
		Response res = utils.updateWallet(walletAmount, walletaction);
		model.addAttribute("message", "Member Wallet update API resposne "+res.asPrettyString());
		return "update-wallet-details";
	}
    
    @DescriptionProvider(slug = "Test ",description = "test desc ", author = "Ankur")
	@PostMapping("/create-gift-card")
	public String createGiftCard(@RequestParam String entryContext ,@RequestParam String mobileRecepient 
			,@RequestParam String mobile, @RequestParam String emailRecepient , @RequestParam String email 
			, @RequestParam String amount , Model model ) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		AutomationReport report = obj.getInitializedReport(this.getClass(), String.class, String.class , String.class ,String.class ,String.class,String.class,Model.class);
		String[] creds = {mobile ,email};
		PaymentUtil util = new PaymentUtil(report, creds ,entryContext);
		Response getSdkParams = util.getSdkParams("gift_card" ,false ,amount);
		model.addAttribute("message", "Get Sdk Parmas resposne  - "+getSdkParams );
		String bb_txn_id = getSdkParams.path("response.bb_txn_id").toString();
		report.log("bb_txn_id - "+bb_txn_id);
		model.addAttribute("message", "Transaction ID  - "+bb_txn_id );

		GiftCardUtils utils = new GiftCardUtils(report, entryContext ,creds );
		utils.createGiftCard(amount , "QA" 
				, bb_txn_id,"bot"
				,mobileRecepient
				,emailRecepient);
		
		String refId = utils.getgiftCardId(bb_txn_id);
		utils.updateGiftCardPayment(bb_txn_id,refId);
				
		GiftCardDbQueries giftCardDnQuery = new GiftCardDbQueries(report);
		String codeGc = giftCardDnQuery.getGiftCardCode(refId);
		String pinGc = giftCardDnQuery.getGiftCardPin(refId);
		
		String saltValue = "badmin@)(*basket";
		String decodedCode = utils.deCoder(codeGc, saltValue);
		String decodedPin = utils.deCoder(pinGc, saltValue);
		
		model.addAttribute("message", "Gift Card Code - "+decodedCode + " : Gift card Pin - "+decodedPin);
		
		return "create-gift-card";
	}
}
