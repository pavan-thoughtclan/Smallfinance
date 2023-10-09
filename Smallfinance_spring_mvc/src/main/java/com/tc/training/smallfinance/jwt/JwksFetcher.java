//package com.tc.training.smallfinance.jwt;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import java.security.interfaces.RSAPublicKey;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.Map;
//
//public class JwksFetcher {
//    public static void main(String[] args) {
//        String jwksUri = "http://localhost:8081/.well-known/jwks.json";
//        RestTemplate restTemplate = new RestTemplate();
//
//        ResponseEntity<String> response = restTemplate.getForEntity(jwksUri, String.class);
//        String jwksJson = response.getBody();
//
//        // Parse the JWKS JSON and extract the keys
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.readTree(jwksJson);
//
//            // Extract keys from the JSON
//            JsonNode keysNode = jsonNode.get("keys");
//            Map<String, RSAPublicKey> rsaPublicKeys = new HashMap<>();
//
//            for (JsonNode keyNode : keysNode) {
//                String kid = keyNode.get("kid").asText();
//                String n = keyNode.get("n").asText();
//                String e = keyNode.get("e").asText();
//
//                // Decode Base64 encoded values for 'n' and 'e'
//                byte[] modulus = Base64.getUrlDecoder().decode(n);
//                byte[] exponent = Base64.getUrlDecoder().decode(e);
//
//                // Create RSAPublicKey
//                java.security.spec.RSAPublicKeySpec publicKeySpec = new java.security.spec.RSAPublicKeySpec(new java.math.BigInteger(1, modulus), new java.math.BigInteger(1, exponent));
//                RSAPublicKey publicKey = (RSAPublicKey) java.security.KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);
//
//                rsaPublicKeys.put(kid, publicKey);
//            }
//
//            // Now you have a map of RSA public keys with their KID as the key
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
