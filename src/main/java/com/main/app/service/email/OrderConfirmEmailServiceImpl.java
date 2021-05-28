package com.main.app.service.email;

import com.main.app.domain.dto.order_item.OrderItemDto;
import com.main.app.domain.model.order.CustomerOrder;
import com.main.app.domain.model.order_item.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderConfirmEmailServiceImpl implements OrderConfirmEmailService{

    private static final String EMAIL_SUBJECT = "Narudžbina primljena-Informacije";

    private EmailClient emailClient;
    private MailContentBuilder mailContentBuilder;
    @Autowired
    public OrderConfirmEmailServiceImpl(EmailClient emailClient, MailContentBuilder mailContentBuilder) {
        this.emailClient = emailClient;
        this.mailContentBuilder = mailContentBuilder;
    }

    @Override
    @Async("processExecutor")
    public void sendEmail(CustomerOrder order, String emailFrom, String emailTo, List<OrderItemDto> canDelivery, List<OrderItemDto> cantDelivery, List<OrderItemDto> selfTransportDelivery) {
        String itemsCanDelivery = "";
        String itemsCantDelivery = "";
        String itemsSelfTransport = "";

        for (OrderItemDto item : canDelivery) {
            itemsCanDelivery += item.getName() + "  Cena: " + item.getPrice() + "  Količina: " + item.getQuantity() + " Kom.\n";
        }
        for (OrderItemDto item : cantDelivery) {
            itemsCantDelivery += item.getName() + "  Cena: " + item.getPrice() + "  Količina: " + item.getQuantity() + " Kom.\n";
        }
        for (OrderItemDto item : selfTransportDelivery) {
            itemsSelfTransport += item.getName() + "  Cena: " + item.getPrice() + "  Količina: " + item.getQuantity() + " Kom.\n";
        }

        String MESSAGE_BEFORE = "Poštovani " + order.getBuyerName() + " " + order.getBuyerSurname() + "  u sledećem tekstu nalaze se osnovne informacije o vašoj porudžbini."
                                + "Molimo vas pročitajte kako biste znali i bili informisani o tačnom vremenu isporuke i načinu preuzimanja proizvoda iz našeg vrta.";

        String MESSAGE_AFTER = "-Poručeni proizvodi koji će biti isporučeni u periodu od 5 do 10 dana: \n"
                                + itemsCanDelivery + "\n"
                                + " -Poručeni proizvodi koji će biti isporučeni sa zakašnjenjem: \n"
                                + itemsCantDelivery + "\n"
                                + " -Poručeni proizvodi koje preuzimate lično u našoj poslovnici: \n"
                                + itemsSelfTransport;

        String content = mailContentBuilder.buildContentWithLink("", MESSAGE_BEFORE, MESSAGE_AFTER);
        String emailToSend = "stefan.roganovic@lilly021.com";
        emailClient.sendMimeEmail(emailFrom,emailToSend,EMAIL_SUBJECT,content);
    }
}

