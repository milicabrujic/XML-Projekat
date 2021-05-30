package com.main.app.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class OrderMailContentBuilder {

    private static final String TLT_TEMPLATE_NAME = "orderConfirmationTemplate";

    private static final String TLT_TEMPLATE_MESSAGE_USER_NAME_SURNAME = "messageUserNameSurname";

    private static final String TLT_TEMPLATE_MESSAGE_POSSIBLE_DELIVERY = "itemsCanDelivery";

    private static final String TLT_TEMPLATE_MESSAGE_UN_POSSIBLE_DELIVERY = "itemsCantDelivery";

    private static final String TLT_TEMPLATE_MESSAGE_SELF_TRANSPORT = "itemsSelfTransport";

    private static final String TLT_TEMPLATE_MESSAGE_DATE_CREATED = "orderDateCreated";

    private static final String TLT_LINK_ORDER_PRICE = "orderPrice";

    private TemplateEngine templateEngine;

    @Autowired
    public OrderMailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildOrderContentWithLink(String messageUserNameSurname,String itemsCanDelivery, String itemsCantDelivery, String itemsSelfTransport,String orderDateCreated,String orderPrice) {
        Context context = new Context();
        context.setVariable(TLT_TEMPLATE_MESSAGE_POSSIBLE_DELIVERY, itemsCanDelivery);
        context.setVariable(TLT_TEMPLATE_MESSAGE_USER_NAME_SURNAME, messageUserNameSurname);
        context.setVariable(TLT_TEMPLATE_MESSAGE_UN_POSSIBLE_DELIVERY, itemsCantDelivery);
        context.setVariable(TLT_TEMPLATE_MESSAGE_SELF_TRANSPORT, itemsSelfTransport);
        context.setVariable(TLT_TEMPLATE_MESSAGE_DATE_CREATED, orderDateCreated);
        context.setVariable(TLT_LINK_ORDER_PRICE, orderPrice);
        return templateEngine.process(TLT_TEMPLATE_NAME, context);
    }

}
