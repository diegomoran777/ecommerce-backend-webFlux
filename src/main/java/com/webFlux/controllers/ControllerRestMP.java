package com.webFlux.controllers;


import java.util.Collections;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.webFlux.models.ResponseIPN;
import com.webFlux.models.UserProgram;
import com.webFlux.service.EmailService;
import com.webFlux.service.GeneralService;
import com.webFlux.service.MPService;

@RestController
@RequestMapping("/api/mp")
@CrossOrigin(origins = {"*"})
public class ControllerRestMP {

	@Autowired
	MPService serviceMP;
	
	@Autowired
	GeneralService service;
	
	@Autowired
	EmailService emailService;
	
	/*@Value("${text.token}")
	private String tokenAccess;*/
	
	private final String SUBJECT = "Compra Marmol Fenix";
	
	@PostMapping(value="/go-mp", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String,String> goMp(@RequestBody UserProgram user) throws MPException {
			Preference preference = new Preference();
			serviceMP.setBackUrls(preference);
			serviceMP.setPayer(preference, user.getUserName());
			serviceMP.addItems(preference, user.getUserName());
			return Collections.singletonMap("response", preference.save().getInitPoint());
	}
	
	
	@PostMapping("ipn-response")
	public ResponseEntity<?> responseIPN(@RequestParam("topic") String topic, @RequestParam("id") String id) {
		try {
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseIPN responseIpn = restTemplate.getForObject("https://api.mercadopago.com/v1/payments/"
					.concat(id)
					.concat("?access_token=")
					.concat("APP_USR-4047535620856855-071412-9fedaeb127c19a881a8db81495ef03ff-179043659"), ResponseIPN.class);
			
			if(serviceMP.isAproved(responseIpn.getStatus())) {
				//ENVIAR MAIL...
				String userEmail = responseIpn.getPayer().getEmail();
				emailService.sendEmail(userEmail, SUBJECT, serviceMP.contentEmail(responseIpn));
				
				//LLENAR COMRA CLIENTE, COMPRAS, BORRAR CAR LIST...
				serviceMP.fillPurchase(responseIpn);
				serviceMP.fillSale(responseIpn);
				serviceMP.deleteCartList(responseIpn);
			}
			
		} catch (Exception e) {
			System.out.println("PEDIDO NO ENCONTRADO");
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
