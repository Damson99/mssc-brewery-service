package mssc.beer.service.order;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mssc.beer.config.JmsConfig;
import mssc.model.event.ValidateBeerOrderRequest;
import mssc.model.event.ValidateOrderBeerResult;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class BeerOrderValidationListener
{
    private final BeerOrderValidator beerOrderValidator;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ORDER_VALIDATION_QUEUE)
    public void listen(ValidateBeerOrderRequest validateBeerOrderRequest)
    {
        Boolean isValid = beerOrderValidator.validateBeerOrder(validateBeerOrderRequest.getBeerOrderDto());
        ValidateOrderBeerResult result = ValidateOrderBeerResult.builder()
                .id(validateBeerOrderRequest.getBeerOrderDto().getId())
                .isValid(isValid)
                .build();

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE, result);
    }
}
