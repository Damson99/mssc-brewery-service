package mssc.beer.service.inventory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mssc.beer.service.inventory.model.BeerInventoryDto;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Profile("local-discovery")
@Service
public class BeerInventoryServiceFeign implements BeerInventoryService
{
    private final InventoryServiceFeignClient serviceClientFeign;

    @Override
    public Integer getOnhandInventory(UUID beerId)
    {
        log.debug("Calling inventory service - BeerId: " + beerId);
        ResponseEntity<List<BeerInventoryDto>> responseEntity = serviceClientFeign.getOnhandInventory(beerId);

        return Objects.requireNonNull(responseEntity.getBody()).stream()
                .mapToInt(BeerInventoryDto::getQuantityOnHand).sum();
    }
}
