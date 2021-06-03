package com.main.app.service.email;

import com.main.app.domain.dto.order_item.OrderItemDto;
import com.main.app.domain.model.order.CustomerOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.List;

@Service
public class OrderConfirmEmailServiceImpl implements OrderConfirmEmailService{

    private static final String EMAIL_SUBJECT = "Narudžbina primljena-Informacije";

    private EmailClient emailClient;
    private OrderMailContentBuilder orderMailContentBuilder;
    @Autowired
    public OrderConfirmEmailServiceImpl(EmailClient emailClient, OrderMailContentBuilder orderMailContentBuilder) {
        this.emailClient = emailClient;
        this.orderMailContentBuilder = orderMailContentBuilder;
    }

    @Override
    @Async("processExecutor")
    public void sendEmail(CustomerOrder order, String emailFrom, String emailTo, List<OrderItemDto> canDelivery, List<OrderItemDto> cantDelivery, List<OrderItemDto> selfTransportDelivery) {
        String itemsCanDelivery = "";
        String itemsCantDelivery = "";
        String itemsSelfTransport = "";
        String shopDelivery = "";
        String homeDelivery = "";
        if(order.getShop() != null){
            shopDelivery = "Poručeni proizvodi koje preuzimate u našoj poslovnici u periodu od 5 do 10 dana:";
        }else{
            homeDelivery = "-Poručeni proizvodi koji će biti isporučeni na adresu u periodu od 5 do 10 dana:";
        }

        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        DateTimeFormatter DATE_TIME_FORMATTER_2 = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                .withZone(ZoneId.systemDefault());

        for (OrderItemDto item : canDelivery) {
            itemsCanDelivery += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "*&nbsp;Naziv:"  +  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ "<b>" + item.getName() + "</b>" + "<br/>"  +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "-&nbsp;Cena: " +  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"  + item.getPrice() + "0 RSD" +  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "-&nbsp;Količina: " + item.getQuantity() + " kom." + "<br/><br/>";
        }
        for (OrderItemDto item : cantDelivery) {
            Instant instantOd = Instant.parse(item.getVremeIsporukeOd());
            Instant instantDo = Instant.parse(item.getVremeIsporukeDo());
            itemsCantDelivery += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "*&nbsp;Naziv:"  +  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "<b>" + item.getName() + "</b>" +  "<br/>"  +  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "-&nbsp;Cena: " +  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"  + item.getPrice() + "0 RSD" +  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "-&nbsp;Količina: " + item.getQuantity() + " kom." + "<br/> " +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "-&nbsp;Moguće preuzeti u periodu:" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "od" + "&nbsp;&nbsp;&nbsp;" + DATE_TIME_FORMATTER_2.format(instantOd) + "&nbsp;&nbsp;&nbsp;" + "do" + "&nbsp;&nbsp;&nbsp;" + DATE_TIME_FORMATTER_2.format(instantDo) + "<br/><br/>";
        }
        for (OrderItemDto item : selfTransportDelivery) {
            itemsSelfTransport +="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "*&nbsp;Naziv:"  +  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "<b>" + item.getName() + "</b>" +  "<br/>"  + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "-&nbsp;Cena: " +  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"  + item.getPrice() + "0 RSD" +  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "-&nbsp;Količina: " + item.getQuantity() + " kom." + "<br/><br/>";
        }

        String MESSAGE_USER_NAME_SURNAME = "Pozdrav, " + order.getBuyerName() + " " + order.getBuyerSurname() + "!";


        String ORDER_DATE_CREATED = "-Datum kreiranja porudžbine:" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + DATE_TIME_FORMATTER.format(order.getDateCreated());;

        String ORDER_PRICE = "-Ukupna cena:" +  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + order.getTotalPrice().toString() + ",00 RSD.";

        String content = orderMailContentBuilder.buildOrderContentWithLink(MESSAGE_USER_NAME_SURNAME,itemsCanDelivery, itemsCantDelivery,itemsSelfTransport,shopDelivery,homeDelivery,ORDER_DATE_CREATED,ORDER_PRICE);
        String emailToSend = "stefan.roganovic@lilly021.com";
        emailClient.sendMimeEmail(emailFrom,emailToSend,EMAIL_SUBJECT,content);
    }
}

