package com.bb.AutomationUtils.userdetailsapp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bigbasket.automation.utilities.Libraries;

public class GetVoucherTemplate extends Libraries{
	public static Properties configJavelinProperties = new Properties();;
	public JSONObject getVoucherTemplate(String transactionType, String rewardType, String paymentType, boolean autoVoucher, String voucherType) throws IOException {
		configJavelinProperties.load(GetVoucherTemplate.class.getResourceAsStream("/templates/configJavelin.properties"));
		Boolean [] autoVoucherEnums = {autoVoucher};
		String[] transactionFlowEnums = {transactionType};
		String[] voucherTypeEnums = {voucherType};
		String[] paymentMethodEnums = {paymentType};
		String[] rewardTypeEnums = {rewardType};

		// Generate all combinations
//		int size = transactionFlowEnums.length * voucherTypeEnums.length * paymentMethodEnums.length * rewardTypeEnums.length +3;
//		int size = transactionFlowEnums.length * voucherTypeEnums.length * paymentMethodEnums.length * rewardTypeEnums.length;
//		Object[][] combinations = new Object[size][];
		JSONObject json = null ;
//		int index = 0;
		for (boolean autoVouchers :autoVoucherEnums) {
			for (String transactionFlow : transactionFlowEnums) {
				if (!autoVouchers) {
					for (String voucherTypeInner : voucherTypeEnums) {
						for (String paymentMethod : paymentMethodEnums) {
							for (String rewardTypeInner : rewardTypeEnums) {
								json = new JSONObject();
								json.put("auto_voucher", autoVouchers);
								json.put("transaction_flow", transactionFlow);

								JSONObject voucherTypeObj = new JSONObject();
								voucherTypeObj.put("type", voucherTypeInner);
								voucherTypeObj.put("voucher_code", generateRandomString(7));
								if (voucherTypeInner.equals("series")) {
									voucherTypeObj.put("no_of_codes", 1);
								}
								json.put("voucher_type", voucherTypeObj);

								JSONObject purchaseTypeObj = new JSONObject();
								purchaseTypeObj.put("type", configJavelinProperties.getProperty("purchaseType"));
								purchaseTypeObj.put("min_amount", 50);
								json.put("purchase_type", purchaseTypeObj);

								JSONObject paymentModeObj = new JSONObject();
								paymentModeObj.put("payment_method", paymentMethod);
								if (paymentMethod.equals("card")) {
									paymentModeObj.put("card_type", new JSONArray().put(configJavelinProperties.getProperty("cardType")));
									paymentModeObj.put("issuer_bank", new JSONArray().put(configJavelinProperties.getProperty("issuerBank")));
									paymentModeObj.put("card_network", new JSONArray().put(configJavelinProperties.getProperty("cardNetwork")));
									paymentModeObj.put("card_name", configJavelinProperties.getProperty("cardName"));
									paymentModeObj.put("bin_list", configJavelinProperties.getProperty("binList"));
								} else if (paymentMethod.equals("upi")) {
									paymentModeObj.put("payment_app",new JSONArray().put(configJavelinProperties.getProperty("paymentApp")));
								} else if (paymentMethod.equals("wallet")) {
									paymentModeObj.put("wallet_partner", new JSONArray().put(configJavelinProperties.getProperty("walletPartner")));
								} else if (paymentMethod.equals("netbanking")) {
									paymentModeObj.put("bank_name", new JSONArray().put(configJavelinProperties.getProperty("bankName")));
								}
								json.put("payment_mode", paymentModeObj);

								// Generate JSON for optional keys
//								JSONObject incExcObj = new JSONObject();
//								JSONObject inclusionObj = new JSONObject();
//								JSONObject manufacturerObj = new JSONObject();
//								manufacturerObj.put("name", configJavelinProperties.getProperty("manufacturerName"));
//								manufacturerObj.put("slug", configJavelinProperties.getProperty("manufacturerSlug"));
//								manufacturerObj.put("id", Integer.parseInt(configJavelinProperties.getProperty("manufacturerId")));
//								inclusionObj.put("manufacturer", manufacturerObj);
//
//								JSONArray brandsArray = new JSONArray();
//								JSONObject brandObj = new JSONObject();
//								brandObj.put("id", 835);
//								brandObj.put("name", "AIM");
//								brandObj.put("slug", "aim");
//								brandsArray.put(brandObj);
//								inclusionObj.put("brands", brandsArray);
//
//								JSONArray categoryArray = new JSONArray();
//								JSONObject categoryObj1 = new JSONObject();
//								categoryObj1.put("slug", "fruits_vagitables");
//								categoryObj1.put("name", "fruits and vagitables");
//								categoryObj1.put("level", 0);
//								categoryArray.put(categoryObj1);
//								JSONObject categoryObj2 = new JSONObject();
//								categoryObj2.put("slug", "bakery_cake_dairy");
//								categoryObj2.put("name", "bakery cake and dairy");
//								categoryObj2.put("level", 0);
//								categoryArray.put(categoryObj2);
//								inclusionObj.put("category", categoryArray);
//								incExcObj.put("inclusion", inclusionObj);
//								json.put("inc_exc", incExcObj);

								JSONArray platformArray = new JSONArray();
								platformArray.put("app");
								platformArray.put("pwa");
								json.put("platform", platformArray);

								//Reward
								JSONObject rewardObj = new JSONObject();
								rewardObj.put("type", rewardTypeInner);
								if(!rewardTypeInner.equalsIgnoreCase("free_delivery")) {
									rewardObj.put("discount_amount", 50);
									rewardObj.put("discount_type", "percent");
									//Handle Flat Option as well 
									rewardObj.put("max_discount_amount", 100);
								}

								json.put("reward", rewardObj);

								//Redemption Limit
								JSONObject redemptionLimitsObj = new JSONObject();
								redemptionLimitsObj.put("campaign_level", 100);
								redemptionLimitsObj.put("member_level", 10);
								if(paymentMethod.equals("card")) {
									redemptionLimitsObj.put("per_card", 5);
								}
								redemptionLimitsObj.put("per_day", 3);
								redemptionLimitsObj.put("per_hr", 1);
								json.put("redemption_limits", redemptionLimitsObj);

								//Funding Break Up
								JSONObject fundingBreakupObj = new JSONObject();
								fundingBreakupObj.put("category", 30);
								fundingBreakupObj.put("vendor", 30);
								fundingBreakupObj.put("marketing_regional", 15);
								fundingBreakupObj.put("marketing_central", 15);
								if (paymentMethod.equals("card")) {
									fundingBreakupObj.put("issuer_bank", 5);
									fundingBreakupObj.put("card_network", 5);
								} else if (paymentMethod.equals("upi")) {
									fundingBreakupObj.put("upi", 10);
								} else if (paymentMethod.equals("wallet")) {
									fundingBreakupObj.put("wallet", 10);
								} else if (paymentMethod.equals("netbanking")) {
									fundingBreakupObj.put("bank", 10);
								}
								json.put("funding_breakup", fundingBreakupObj);

								JSONObject assetObj = new JSONObject();
								assetObj.put("logo", "s3 link");
								assetObj.put("title", "ABC");
								assetObj.put("description", "ABCD");
								assetObj.put("t_c", "terms and conditions");
								json.put("asset", assetObj);
//								combinations[index++] = new Object[]{json};
							}
						}
					}
				}
				else {
					json = new JSONObject();
					json.put("auto_voucher", autoVouchers);
					json.put("transaction_flow", transactionFlow);

					JSONObject assetObj = new JSONObject();
					assetObj.put("logo", "s3 link");
					assetObj.put("title", "ABC");
					assetObj.put("description", "ABCD");
					assetObj.put("t_c", "terms and conditions");
					json.put("asset", assetObj);
//					combinations[index++] = new Object[]{json};
				}
			}
		}
		return json;
		
	}
	/**
     * This method is used to generate random string used in Login
     * @param length (length of random string)
     */
    public String generateRandomString(int length) {
        final String characters = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder result = new StringBuilder();
        Random rand = new Random();
        while (length-- > 0) {
            result.append(characters.charAt(rand.nextInt(characters.length())));
        }
        return result.toString();
    }
//	public static String[] getECBaisedCredential(String userType) {
//		System.out.println("Usertype "+userType);
//		String[] creds = null;
//		if(userType.contains("bbnow")) {
//			System.out.println("Usertype "+userType);
//			creds = AutomationUtilities.getUniqueLoginCredential(serverName, configServerSpecficJavelinProperties.getProperty("BBNOW_SHEETNAME"));
//		}
//		else if(userType.contains("bb-b2c")) {
//			System.out.println("Usertype "+userType);
//			creds = AutomationUtilities.getUniqueLoginCredential(serverName,configServerSpecficJavelinProperties.getProperty("B2C_SHEETNAME"));
//		}
//		else {
//			logger.info("Unimplemented Entry Context");
//		}
//		return creds;
//	}

	public static int getEcId(String entryContext) {
		if(entryContext.contains("bbnow")) {
			return 10 ;
		}
		else if(entryContext.contains("b2c")) {
			return 100 ;
		}
		else if(entryContext.contains("b2b")) {
			return 103 ;
		}
		else {
			logger.info("unhandled entry context");
		}
		return 0;
	}
	public static Integer getAutovoucherAsInt(Boolean autoVoucher) {
		if(autoVoucher) return 1 ;
		return 0;

	}

	public static HashMap<String, String> getHeaders() {
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("x-project", xProject);
		return headers;
	}

}
