package brewery.service.service;

import brewery.service.config.JmsConfig;
import brewery.service.domain.Beer;
import brewery.service.event.BrewBeerEvent;
import brewery.service.repository.BeerRepository;
import brewery.service.service.inventory.BeerInventoryService;
import brewery.service.web.mapper.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
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
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST, new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
        });
    }
}
