package com.sahyog.backend.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

public class ABDMSession {
    private String token;
    public ABDMSession() {}
    public ABDMSession(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
    public String setToken() throws Exception {

        var values = new HashMap<String, String>() {{
            put("clientId", "SBX_002715");
            put ("clientSecret", "fd5636d9-9349-465a-a2f7-9c94895b359b");
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(values);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://dev.abdm.gov.in/gateway/v0.5/sessions"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        this.token = new JSONObject(response.body()).get("accessToken").toString();
        return token;
    }
    public int patchUrl(String uuidCode, String patchUrl) {
        HttpClient client = HttpClient.newHttpClient();
        String requestBody =  "{\n    \"url\": \""+patchUrl+"\"\n}";
        System.out.println(requestBody);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://dev.ndhm.gov.in/devservice/v1/bridges"))
                .method("PATCH",HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+token)
                .build();
        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("RESPONSE : "+response.toString());
            return response.statusCode();

        } catch (IOException e) {
            return 510;
        } catch (InterruptedException e) {
            return 510;
        }
    }

    //CALLS TO ABDM:

    //------------------------PATIENT REGISSTRATION FLOW---------------------
    public int patientInitUsingMobile(String UUIDCode, String healthId) throws Exception {
        String requestBody =  "{\n    \"requestId\": \""+UUIDCode+"\",\n    \"timestamp\": \""+ Instant.now().toString()+"\",\n    \"query\":{\n        \"id\": \""+healthId+"\",\n        \"purpose\": \"KYC_AND_LINK\",\n        \"authMode\": \"MOBILE_OTP\",\n        \"requester\": {\n            \"type\": \"HIP\",\n            \"id\": \"sahyog-hip-facility-iiitb\"\n        }\n    }\n}";
        System.out.println(requestBody);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://dev.abdm.gov.in/gateway/v0.5/users/auth/init"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json")
                .header("X-CM-ID", "sbx")
                .header("Authorization", "Bearer "+token)
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }

    public int confirmMobileOTP(String UUIDCode, String transactionId, String mobileOTP) throws Exception {
        String requestBody =  "{\n    \"requestId\": \""+ UUIDCode+"\",\n    \"timestamp\": \""+ Instant.now()+"\",\n    \"transactionId\": \""+ transactionId +"\",\n    \"credential\" : {\n        \"authCode\": \""+ mobileOTP +"\"\n    }\n}";
        System.out.println(requestBody);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://dev.abdm.gov.in/gateway/v0.5/users/auth/confirm"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json")
                .header("X-CM-ID", "sbx")
                .header("Authorization", "Bearer "+token)
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }

    //--------------------------CARE-CONTEXT LINKING FLOW--------------------
    public int careContextLinking(String patientReferenceNumber, String displayPatientName, String display, String careContextReferenceNumber, String linkToken) throws Exception {
        String requestBody =  "{\n    \"requestId\": \""+ UUID.randomUUID()+"\",\n    \"timestamp\": \""+ Instant.now()+"\",\n  \"link\": {\n" +
                "        \"accessToken\": \""+linkToken+"\",\n" +
                "        \"patient\": {\n" +
                "            \"referenceNumber\": \""+patientReferenceNumber+"P-ID-001\",\n" +
                "            \"display\": \""+displayPatientName+"\",\n" +
                "            \"careContexts\": [\n" +
                "                {\n" +
                "                    \"referenceNumber\": \""+careContextReferenceNumber+"\",\n" +
                "                    \"display\": \""+display+"\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    } \n}";
        System.out.println(requestBody);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://dev.abdm.gov.in/gateway/v0.5/links/link/add-contexts"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json")
                .header("X-CM-ID", "sbx")
                .header("Authorization", "Bearer "+token)
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println("asdfghj  "+response.toString());
        return response.statusCode();
    }


    //-------------------CONSENT AND DATA TRANSFER FLOW----------------------

    public int createConsentRequest(String UUIDCode, String consent) {
        HttpClient client = HttpClient.newHttpClient();
        String requestBody =  "{\n    \"requestId\": \""+ UUIDCode+"\",\n    \"timestamp\": \""+ Instant.now()+"\",\n  \"consent\": " + consent+
                "\n}";

        System.out.println(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://dev.abdm.gov.in/gateway/v0.5/consent-requests/init"))
                .method("POST",HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json")
                .header("X-CM-ID", "sbx")
                .header("Authorization", "Bearer "+token)
                .build();
        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("RESPONSE : "+response.toString());
            return response.statusCode();

        } catch (IOException e) {
            return 510;
        } catch (InterruptedException e) {
            return 510;
        }
    }

    public int fetchAllCareContexts(String consentId)
    {
        String requestId = UUID.randomUUID().toString();

        String requestBody =  "{\n    \"requestId\": \""+ requestId+"\",\n    \"timestamp\": \""+ Instant.now()+"\",\n    \"consentId\": " + consentId+
                "\n}";
        System.out.println(requestBody);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://dev.abdm.gov.in/gateway/v0.5/consents/fetch"))
                .method("POST",HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json")
                .header("X-CM-ID", "sbx")
                .header("Authorization", "Bearer "+token)
                .build();
        HttpResponse<String> response = null;
        HttpClient client = HttpClient.newHttpClient();
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("RESPONSE : "+response.toString());
            return response.statusCode();

        } catch (IOException e) {
            return 510;
        } catch (InterruptedException e) {
            return 510;
        }
    }
    public int sendNotificationAcknowledgement(String consentId, String requestID) {
        HttpClient client = HttpClient.newHttpClient();
        String requestBody =  "{\n    \"requestId\": \""+ UUID.randomUUID()+"\",\n    \"timestamp\": \""+ Instant.now()+"\",\n  \"acknowledgement\": {\n" +
                "    \"status\": \"OK\",\n" +
                "    \"consentId\": \""+consentId+"\"\n" +
                "  },\n" +
                "  \"error\": {\n" +
                "    \"code\": 1000,\n" +
                "    \"message\": \"string\"\n" +
                "  },\n" +
                "  \"resp\": {\n" +
                "    \"requestId\": \""+requestID+"\"\n" +
                "  }\n}";
        System.out.println("Sending to on notify\n"+requestBody);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://dev.abdm.gov.in/gateway/v0.5/consents/hip/on-notify"))
                .method("POST",HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json")
                .header("X-CM-ID", "sbx")
                .header("Authorization", "Bearer "+token)
                .build();
        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("RESPONSE : "+response.toString());
            return response.statusCode();

        } catch (IOException e) {
            return 510;
        } catch (InterruptedException e) {
            return 510;
        }
    }

    public int fetchStatus(String uuidCode, String consentRequestId) {

        String requestBody =  "{\n    \"requestId\": \""+ uuidCode+"\",\n    \"timestamp\": \""+ Instant.now()+"\",\n    \"consentRequestId\": " + consentRequestId+
                "\n}";
        System.out.println(requestBody);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://dev.abdm.gov.in/gateway/v0.5/consent-requests/status"))
                .method("POST",HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json")
                .header("X-CM-ID", "sbx")
                .header("Authorization", "Bearer "+token)
                .build();
        HttpResponse<String> response = null;
        HttpClient client = HttpClient.newHttpClient();
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("RESPONSE : "+response.toString());
            return response.statusCode();

        } catch (IOException e) {
            return 510;
        } catch (InterruptedException e) {
            return 510;
        }
    }
}
