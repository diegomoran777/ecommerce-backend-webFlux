package com.webFlux.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadopago.resources.Preference;
import com.mercadopago.resources.Preference.AutoReturn;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;
import com.webFlux.models.Purchase;
import com.webFlux.models.ResponseIPN;
import com.webFlux.models.Sale;
import com.webFlux.models.UserProgram;

import reactor.core.publisher.Mono;

@Service
public class MPService {
	
	@Autowired
	private GeneralService service;
	
	private final String APROVED = "approved";
	private final String SUCCESS = "marmolfenix.netlify.app/purchase";
	private final String PENDING = "marmolfenix.netlify.app/cart-pay";
	private final String FAILURE = "marmolfenix.netlify.app/cart-pay";
	
	public void setBackUrls(Preference preference) {
		BackUrls backUrls = new BackUrls();
		preference.setBackUrls(
				backUrls.setSuccess(SUCCESS)
						.setPending(PENDING)
						.setFailure(FAILURE));
		preference.setAutoReturn(AutoReturn.approved);
	}
	
	public void addItems(Preference preference, String userName) {
		service.getProductsByUserCart(userName)
		.map(p -> {
				Item item = new Item();
				item.setCategoryId(String.valueOf(p.getCategoryId()))
					.setId(String.valueOf(p.getId()))
					.setDescription(p.getDescription())
					.setPictureUrl(p.getPhoto())
					.setQuantity(p.getAmount())
					.setTitle(p.getName())
					.setUnitPrice(p.getPrice())
					.setCurrencyId("ARS");
				return preference.appendItem(item);
			});
		
	    //Shipments cost
	    /*Shipments ship = new Shipments();
	    ship.setCost((float) 1000);
	    preference.setShipments(ship);*/
	}
	
	public void setPayer(Preference preference, String userName) {
		Mono<UserProgram> user = service.findUserByUserName(userName);
		user.doOnNext(u -> {
			Payer payer = new Payer();
			payer.setName(u.getUserName());
			payer.setEmail(u.getEmail());
			preference.setPayer(payer);
		});
	}
	
	public boolean isAproved(String status) {
		return status.equals(this.APROVED);
	}
	
	
	public void fillPurchase(ResponseIPN responseIpn) {
		String details = "";
		
		Purchase purchase = new Purchase();
		purchase.setDate_approved(responseIpn.getDate_approved());
		purchase.setInstallments(responseIpn.getInstallments());
		purchase.setStatus(responseIpn.getStatus());
		purchase.setPayment_method_id(responseIpn.getPayment_method_id());
		purchase.setPayment_type_id(responseIpn.getPayment_type_id());
        purchase.setPurchase_detail(fillDetails(responseIpn, details));
		purchase.setTotal(responseIpn.getTransaction_amount());
		UserProgram user =  service.findUserByEmail(responseIpn.getPayer().getEmail());
		user.addPurchase(purchase);
	}
	
	
	public String fillDetails(ResponseIPN responseIpn, String details) {
		UserProgram user =  service.findUserByEmail(responseIpn.getPayer().getEmail());
		service.getProductsByUserCart(user.getUserName())
		.reduce(details, (detail, p) -> {
					detail += "PRODUCTO: ".concat(p.getName()).concat("\n")
					.concat("NRO DE PRODUCTO: ").concat(p.getNumberExtern()).concat("\n")
					.concat("CANTIDAD: ").concat(String.valueOf(p.getAmount())).concat("\n")
					.concat("PRECIO: ").concat(String.valueOf(p.getPrice())).concat("\n");
					return detail;
		});
		return details;
	}
	
	public String contentEmail(ResponseIPN responseIpn) {
		String detail = "";
		String content = "Le informamos la confirmacion de su compra en MARMOL FENIX \n".concat("\n")
				.concat("DETALLE DE LA COMPRA:\n")
				.concat(fillDetails(responseIpn, detail)).concat("TOTAL: ")
				.concat(String.valueOf(responseIpn.getTransaction_amount()))
				.concat("\n").concat("\n")
				.concat("Proximamente nos comunicaremos con usted \n")
				.concat("GRACIAS POR SU COMPRA!!! \n")
				.concat("MARMOL FENIX \n")
				.concat("marmolfenix@hotmail.com\n")
				.concat("marmolfenix.com");
		return content;
	}
	
	public void fillSale(ResponseIPN responseIpn) {
		UserProgram user =  service.findUserByEmail(responseIpn.getPayer().getEmail());
		String details = "";
		Sale sale = new Sale();
		sale.setDate_approved(responseIpn.getDate_approved());
		sale.setDelivered(true);
		sale.setInstallments(responseIpn.getInstallments());
		sale.setPayment_method_id(responseIpn.getPayment_method_id());
		sale.setPayment_type_id(responseIpn.getPayment_type_id());
		sale.setSale_detail(fillDetails(responseIpn, details));
		sale.setStatus(responseIpn.getStatus());
		sale.setTotal(responseIpn.getTransaction_amount());
		sale.setUserMail(responseIpn.getPayer().getEmail());
		sale.setUserName(user.getUserName());
		service.saveUpdateSale(sale);
	}
	
	public void deleteCartList(ResponseIPN responseIpn) {
		UserProgram user =  service.findUserByEmail(responseIpn.getPayer().getEmail());
		service.deleteAllProductsCartByUserName(user.getUserName());
	}
	

}
