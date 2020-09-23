package mssc.beer.service.brewing;

import mssc.beer.config.JmsConfig;
import mssc.beer.domain.Beer;
import mssc.model.event.BrewBeerEvent;
import mssc.beer.repository.BeerRepository;
import mssc.beer.service.inventory.BeerInventoryService;
import mssc.beer.web.mapper.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BrewingService
{
    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000)
    public void checkForLowInventory()
    {
        List<Beer> beerList = beerRepository.findAll();
        beerList.forEach(beer ->
        {
            Integer invQQH = beerInventoryService.getOnhandInventory(beer.getId());
            log.debug("Min Onhand is: " + beer.getMinOnHand());
            log.debug("Inventory is: " + invQQH);
            if(beer.getMinOnHand() >= invQQH)
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
        });
    }
}
