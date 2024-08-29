package com.bb.AutomationUtils.userdetailsapp.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
public class JenkinsController {

	@GetMapping("/jenkins")
	public String viewJenkinsPage() {
		return "jenkins"; // Returns the name of the HTML template (jenkins.html)
	}

	@GetMapping("/jenkins/data")
	@ResponseBody
	public Map<String, Double> getJenkinsData() {
		//API Token for Spring Boot - API Token for Spring Boot - 11f66122ddd7405dddc014850d2db32822
		String jenkinsUrl = "http://mobilelab.bigbasket.com//api/json?tree=jobs[name,color]";
		String auth = "Basic " + Base64.getEncoder().encodeToString("ankur@bigbasket.com:11f66122ddd7405dddc014850d2db32822".getBytes());

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		headers.set("Authorization", auth);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<Map> response = restTemplate.exchange(jenkinsUrl, HttpMethod.GET, entity, Map.class);
		System.out.println("Resposne -"+response.getBody().toString());
		List<Map<String, String>> jobs = (List<Map<String, String>>) response.getBody().get("jobs");

		long totalBuildsAllJobs = 0;
		long passedBuildsAllJobs = 0;
		Map<String, Double> result = new HashMap<>();

		for (Map<String, String> job : jobs) {

			String jobName = job.get("name");
			System.out.println("********* JOb Name in Execution "+jobName);
			//			String jobUrl = "http://mobilelab.bigbasket.com/job/" + jobName + "/api/json?tree=builds[result]{0,10}";
			try {
				String jobUrl = "http://mobilelab.bigbasket.com/job/" + "Automated-Job-Creation-Utility" + "/api/json?tree=builds[result]{0,10}";

				System.out.println("jobUrl "+jobUrl);

				ResponseEntity<Map> jobResponse = restTemplate.exchange(jobUrl, HttpMethod.GET, entity, Map.class);
				List<Map<String, String>> builds = (List<Map<String, String>>) jobResponse.getBody().get("builds");

				if (builds == null || builds.size() == 0) {
					// Skip jobs with no builds
					continue;
				}

				long totalBuilds = Math.min(builds.size(), 10); // Get at most 10 builds
				long passedBuilds = builds.stream()
						.limit(10) // Consider only the first 10 builds
						.filter(build -> "SUCCESS".equals(build.get("result")))
						.count();

				totalBuildsAllJobs += totalBuilds;
				passedBuildsAllJobs += passedBuilds;

				double passPercentage = totalBuilds > 0 ? (double) passedBuilds / totalBuilds * 100 : 0;

				result.put(jobName + " Pass Percentage", passPercentage);
				result.put(jobName + " Fail Percentage", 100 - passPercentage);
			} catch (HttpClientErrorException e) {
				// Skip jobs that cause errors
				System.out.println("Error fetching builds for job: " + jobName);
			}
		}

		double totalPassPercentage = totalBuildsAllJobs > 0 ? (double) passedBuildsAllJobs / totalBuildsAllJobs * 100 : 0;
		result.put("All Jobs Pass Percentage", totalPassPercentage);
		result.put("All Jobs Fail Percentage", 100 - totalPassPercentage);

		return result;
	}

	//        long totalJobs = jobs.size();
	//        long passedJobs = jobs.stream().filter(job -> "blue".equals(job.get("color"))).count();
	//        double passPercentage = (double) passedJobs / totalJobs * 100;
	//        Map<String, Double> result = new HashMap<>();
	//        result.put("passPercentage", passPercentage);
	//        result.put("failPercentage", 100 - passPercentage);

}

